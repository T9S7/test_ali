package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.ThingSellInfoAssembler;
import com.armsmart.jupiter.bs.api.cache.ThingSellInfoCacheService;
import com.armsmart.jupiter.bs.api.cache.model.ThingSellInfoCache;
import com.armsmart.jupiter.bs.api.constants.SellType;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingSellInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.armsmart.jupiter.bs.api.error.NEError.*;

/**
 * ThingSellInfo service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class ThingSellInfoService {

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    @Autowired
    private ThingSellInfoCacheService thingSellInfoCacheService;

    @Autowired
    private TlOrderService tlOrderService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<ThingSellInfoDetail> selectPage(ThingSellInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ThingSellInfo> pageInfo = new PageInfo<>(thingSellInfoMapper.selectList(query));
        List<ThingSellInfoDetail> dtoList = ThingSellInfoAssembler.INSTANCE.getThingSellInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param thingSellInfoAddParam
     * @return com.armsmart.jupiter.bs.api.entity.ThingSellInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(ThingSellInfoAddParam thingSellInfoAddParam) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return CommonResult.failed(THING_USER_NOT_MATCH);
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            ThingInfo thingInfo1 = thingInfoMapper.selectById(thingSellInfoAddParam.getThingId());
            if (thingInfo1 == null) {
                return CommonResult.failed(THING_NOT_EXIST);
            }
            if (!appUserDetails.getUserInfo().getId().equals(thingInfo1.getUserId())) {
                return CommonResult.failed(THING_USER_NOT_MATCH);
            }
            if (thingInfo1.getCurrentState() == 3) {
                return CommonResult.failed(THING_HAVING_GIVE_NO_SELL);
            }
            ThingSellInfo thingSellInfo = selectSellInfo(thingSellInfoAddParam.getThingId());
            if ((thingInfo1.getCurrentState() == 1 || thingInfo1.getCurrentState() == 2) || (thingSellInfo != null && thingSellInfo.getPutOn())) {
                return CommonResult.failed(THING_SELL_ING);
            }
            ThingSellInfo entity = ThingSellInfoAssembler.INSTANCE.getThingSellInfo(thingSellInfoAddParam);
            //查询当前物品是否存在有效发布状态（有效，已上架）
            entity.setPutOn(true);
            ThingInfo thingInfo = new ThingInfo();
            // 判断发布销售方式   1   一口价    2 拍卖
            if (thingSellInfoAddParam.getSellType() == 1) {
                if (thingSellInfoAddParam.getThingPrice().equals(null)) {
                    return CommonResult.failed(THING_SELL_PRICE_IS_NULL);
                }
                //更新物品价格和状态
                thingInfo.setId(thingSellInfoAddParam.getThingId());
                thingInfo.setCurrentPrice(thingSellInfoAddParam.getThingPrice());
                thingInfo.setCurrentState(1);
                thingInfo.setUpdateTime(System.currentTimeMillis());
                thingInfoMapper.updateSelective(thingInfo);
                thingSellInfoMapper.insert(entity);
            } else {
                if (thingSellInfoAddParam.getSellerPrice().equals(null)
                        || thingSellInfoAddParam.getMargin().equals(null)
                        || thingSellInfoAddParam.getSellStartPrice().equals(null)
                        || thingSellInfoAddParam.getSellAddPrice().equals(null)
                        || thingSellInfoAddParam.getMarginOrderId().equals(null)
                        || thingSellInfoAddParam.getAuctionStartTime().equals(null)
                        || thingSellInfoAddParam.getAuctionEndTime().equals(null)) {
                    return CommonResult.failed(THING_SELL_INFO_IS_NULL);
                }
                CommonResult commonResult = tlOrderService.getOrderDetail(thingSellInfoAddParam.getMarginOrderId());
                if (!commonResult.getData().equals("4")) {
                    return CommonResult.failed(THING_MARGIN_PAY_NO_END);
                }
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis > thingSellInfoAddParam.getAuctionStartTime()) {
                    log.warn("拍卖开始时间必须大于当前时间!sellId={}", thingSellInfoAddParam.getThingId());
                    return CommonResult.failed(SELL_START_TIME_LT_CURRENT_TIME);
                }

                if (thingSellInfoAddParam.getAuctionStartTime() >= thingSellInfoAddParam.getAuctionEndTime()) {
                    return CommonResult.failed(THING_SELL_TIME_IS_FORMAL);
                }
                //更新物品价格和状态
                thingInfo.setId(thingSellInfoAddParam.getThingId());
                thingInfo.setCurrentState(1);
                thingInfo.setCurrentPrice(thingSellInfoAddParam.getSellStartPrice());
                thingInfo.setUpdateTime(System.currentTimeMillis());
                thingInfoMapper.updateSelective(thingInfo);
                thingSellInfoMapper.insert(entity);
                ThingSellInfoCache thingSellInfoCache = ThingSellInfoAssembler.INSTANCE.getThingSellInfoCache(entity);
                thingSellInfoCacheService.set(thingSellInfoCache);
                log.info("物品[{}]拍卖信息缓存成功！进入拍卖状态...", entity.getId());
            }

            return CommonResult.success(entity.getId());
        }
    }

    /**
     * 修改数据
     *
     * @param thingSellInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ThingSellInfoUpdateParam thingSellInfoUpdateParam) {
        ThingSellInfo entity = ThingSellInfoAssembler.INSTANCE.getThingSellInfo(thingSellInfoUpdateParam);
        thingSellInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id) {
        ThingSellInfo entity = new ThingSellInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        thingSellInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail
     * @date 2020/01/01
     */
    public CommonResult<ThingSellInfoDetail> selectById(Long id) {
        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectById(id);
        ThingSellInfoDetail thingSellInfoDetail = ThingSellInfoAssembler.INSTANCE.getThingSellInfoDetail(thingSellInfo);
        return CommonResult.success(thingSellInfoDetail);
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail
     * @date 2020/01/01
     */
    public ThingSellInfo selectByIdInternal(Long id) {
        return thingSellInfoMapper.selectById(id);
    }

    /**
     * 物品下架
     */
    public CommonResult thingPutOff(Long thingId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            log.info("token 失效");
            return CommonResult.failed(THING_USER_NOT_MATCH);
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            ThingInfo thingInfo = thingInfoMapper.selectById(thingId);
            if (thingInfo == null) {
                return CommonResult.failed(THING_NOT_EXIST);
            }
            if (appUserDetails.getUserInfo().getId().equals(thingInfo.getUserId())) {
                ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
                if (null != thingSellInfo && thingSellInfo.getSellType() == SellType.AUCTION.getCode()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (thingSellInfo.getPutOn() && thingSellInfo.getAuctionStartTime() < currentTimeMillis && thingSellInfo.getAuctionEndTime() > currentTimeMillis) {
                        log.warn("物品{}正在拍卖中，请勿下架！sellId={}", thingId, thingSellInfo.getId());
                        return CommonResult.failed(THING_IN_AUCTION);
                    }
                }
                thingSellInfoMapper.thingPutOff(thingId);
                ThingInfo param = new ThingInfo();
                param.setCurrentState(ThingState.PUT_OFF.getCode());
                param.setId(thingInfo.getId());
                thingInfoMapper.updateSelective(param);
                if (thingSellInfo != null) {
                    thingSellInfoCacheService.delete(thingSellInfo.getId());
                }
                return CommonResult.success();

            } else {
                return CommonResult.failed(THING_USER_NOT_MATCH);
            }
        }
    }

    /**
     * 物品上架
     */
    public CommonResult thingPutOn(Long thingId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return CommonResult.failed(THING_USER_NOT_MATCH);
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            ThingInfo thingInfo = thingInfoMapper.selectById(thingId);
            if (thingInfo == null) {
                return CommonResult.failed(THING_NOT_EXIST);
            }
            if (appUserDetails.getUserInfo().getId().equals(thingInfo.getUserId())) {
                return CommonResult.failed(THING_USER_NOT_MATCH);
            }
            thingSellInfoMapper.thingPutOn(thingId);
            ThingInfo param = new ThingInfo();
            param.setCurrentState(1);
            param.setId(thingInfo.getId());
            param.setUpdateTime(System.currentTimeMillis());
            thingInfoMapper.updateSelective(param);
            return CommonResult.success();
        }
    }

    /**
     * 根据物品查询发布信息
     */
    public ThingSellInfo selectSellInfo(Long thingId) {
        return thingSellInfoMapper.selectByThingId(thingId);
    }

    /**
     * 上架物品数
     *
     * @param userId
     * @return
     */
    public CommonResult putOnCount(Long userId) {
        int i = thingSellInfoMapper.putOnCount(userId);
        return CommonResult.success(i);
    }


}
