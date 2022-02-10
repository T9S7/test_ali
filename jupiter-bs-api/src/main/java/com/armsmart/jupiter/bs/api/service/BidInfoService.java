package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.BidInfoAssembler;
import com.armsmart.jupiter.bs.api.cache.BidLockCacheService;
import com.armsmart.jupiter.bs.api.cache.ThingSellInfoCacheService;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.cache.model.ThingSellInfoCache;
import com.armsmart.jupiter.bs.api.constants.BidStatesType;
import com.armsmart.jupiter.bs.api.dao.BidInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.BidInfoDetail;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.armsmart.jupiter.bs.api.error.AuctionError.*;

/**
 * BidInfo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class BidInfoService {

    @Autowired(required = false)
    private BidInfoMapper bidInfoMapper;
    @Autowired
    private ThingSellInfoCacheService thingSellInfoCacheService;
    @Autowired
    private BidLockCacheService bidLockCacheService;
    @Autowired
    private ThingSellInfoService thingSellInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<BidInfoDetail> selectPage(BidInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<BidInfo> pageInfo = new PageInfo<>(bidInfoMapper.selectList(query));
        List<BidInfoDetail> dtoList = BidInfoAssembler.INSTANCE.getBidInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 插入数据
     *
     * @param bidInfoAddParam
     * @return BidInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(BidInfoAddParam bidInfoAddParam) throws Exception {
        String sellId = bidInfoAddParam.getSellId();

        ThingSellInfoCache thingSellInfoCache = thingSellInfoCacheService.get(sellId);
        if (null == thingSellInfoCache) {
            log.warn("获取拍卖信息缓存为空！sellId={}", sellId);
            return CommonResult.failed(AUCTION_ITEM_NOT_EXIST);
        }
        //物品拥有者不能参与竞拍
        ThingInfo thingInfo = thingInfoMapper.selectById(thingSellInfoCache.getThingId());
        if (Objects.equals(thingInfo.getUserId(), bidInfoAddParam.getUserId())) {
            log.warn("卖家不能参与竞拍！sellId={}", sellId);
            return CommonResult.failed(OWNER_CAN_NOT_BID);
        }
        try {
            //加锁竞拍，保证同一时间只有一个人出价成功
            int tryCount = 0;
            boolean locked = false;
            while (!locked || tryCount > CacheConstant.LOCK_MAX_TRY_COUNT) {
                locked = bidLockCacheService.lock(sellId);
                if (!locked) {
                    tryCount++;
                    Thread.sleep(50L);
                }
            }
            if (!locked) {
                log.warn("竞拍出价失败，出价加锁失败！sellId={}", sellId);
                return CommonResult.failed(BID_ADD_FAILED);
            }
            //加锁后再次拉取最新缓存，double check
            thingSellInfoCache = thingSellInfoCacheService.get(sellId);
            if (null == thingSellInfoCache) {
                log.warn("获取拍卖信息缓存为空！sellId={}", sellId);
                return CommonResult.failed(AUCTION_ITEM_NOT_EXIST);
            }

            long bidPrice = bidInfoAddParam.getBidPrice();
            BidInfo maxBid = bidInfoMapper.selectMaxBid(Long.valueOf(sellId));
            if (null != maxBid) {
                if (Objects.equals(bidInfoAddParam.getUserId(), maxBid.getUserId())) {
                    log.warn("当前用户已领先，请勿再次出价！sellId={},currentPrice={},bidPrice={}",
                            sellId, maxBid.getBidPrice(), bidPrice);
                    return CommonResult.failed(BID_USER_LEAD);
                }
                if (maxBid.getBidPrice() >= bidPrice) {
                    log.warn("竞拍出价必须高于当前价格！sellId={},currentPrice={},bidPrice={}",
                            sellId, maxBid.getBidPrice(), bidPrice);
                    return CommonResult.failed(BID_PRICE_IS_LOW);
                }
            }
            //其他人出局-继续出价
            bidInfoMapper.updateBidStateBySellId(BidStatesType.OUT_CONTINUE.getCode(), Long.valueOf(sellId), bidInfoAddParam.getUserId());
            //保存出价信息
            BidInfo entity = BidInfoAssembler.INSTANCE.getBidInfo(bidInfoAddParam);
            bidInfoMapper.insert(entity);
            //更新物品当前价格
            thingInfo.setCurrentPrice(bidPrice);
            thingInfoMapper.updateSelective(thingInfo);

        } finally {
            bidLockCacheService.unlock(sellId);
        }
        return CommonResult.success();
    }


    /**
     * 修改数据
     *
     * @param bidInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(BidInfoUpdateParam bidInfoUpdateParam) {
        BidInfo entity = BidInfoAssembler.INSTANCE.getBidInfo(bidInfoUpdateParam);
        bidInfoMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param bidId
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long bidId) {
        BidInfo entity = new BidInfo();
        entity.setBidId(bidId);
        entity.setIsDel(true);
        bidInfoMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
    }

    /**
     * 获取详情
     *
     * @param bidId 主键ID
     * @return BidInfoDetail
     * @date 2020/01/01
     */
    public BidInfoDetail selectById(Long bidId) {
        BidInfo bidInfo = bidInfoMapper.selectById(bidId);
        BidInfoDetail bidInfoDetail = BidInfoAssembler.INSTANCE.getBidInfoDetail(bidInfo);
        return bidInfoDetail;
    }

    /**
     * 查询当前用户所有参拍物品list
     *
     * @param userId
     * @return
     */
    public List<Long> selectSellList(Long userId) {
        List<Long> sellList = bidInfoMapper.selectSellList(userId);
        return sellList;
    }


}
