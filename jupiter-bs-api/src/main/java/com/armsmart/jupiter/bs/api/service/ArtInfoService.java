package com.armsmart.jupiter.bs.api.service;

//import com.alibaba.fastjson.JSON;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.common.utils.JsonUtil;
import com.armsmart.jupiter.bs.api.assembler.ArtInfoAssembler;
import com.armsmart.jupiter.bs.api.blockchain.BlockChainNftService;
//import com.armsmart.jupiter.bs.api.cache.service.ArtPutOnCacheService;
//import com.armsmart.jupiter.bs.api.constants.ArtType;
import com.armsmart.jupiter.bs.api.dao.ArtInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.ArtInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ArtInfo;
//import com.armsmart.jupiter.bs.api.entity.StoreInfo;
import com.armsmart.jupiter.bs.api.entity.ArtType;
import com.armsmart.jupiter.bs.api.error.NftError;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static com.armsmart.jupiter.bs.api.error.NftError.*;

/**
 * ArtInfo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class ArtInfoService {

    @Autowired(required = false)
    private ArtInfoMapper artInfoMapper;
//    @Autowired
//    private StoreArtInfoService storeArtInfoService;
    @Autowired
    private ArtPicInfoService artPicInfoService;
//    @Autowired
//    private StoreInfoService storeInfoService;
    @Autowired
    private AppNftEthService nftEthService;
    @Autowired
    private BlockChainNftService blockChainNftService;
//    @Autowired
//    private ArtPutOnCacheService artPutOnCacheService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<ArtInfoDetail> selectPage(ArtInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ArtInfo> pageInfo = new PageInfo<>(artInfoMapper.selectList(query));
        List<ArtInfoDetail> dtoList = ArtInfoAssembler.INSTANCE.getArtInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param artInfoAddParam
     * @return ArtInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> insert(ArtInfoAddParam artInfoAddParam) {
        log.info("------------------------insert-----------------:");
        ArtInfo entity = ArtInfoAssembler.INSTANCE.getArtInfo(artInfoAddParam);
//        if (null != artInfoAddParam.getIntoStore() && artInfoAddParam.getIntoStore()) {
//            if (null == artInfoAddParam.getArtPrice()) {
//                DeferredResult deferredResult = new DeferredResult();
//                deferredResult.setResult(CommonResult.failed(IN_STORE_PRICE_MUST_SET));
//                return deferredResult;
//            }
//            if (null == artInfoAddParam.getPutOnTime()) {
//                DeferredResult deferredResult = new DeferredResult();
//                deferredResult.setResult(CommonResult.failed(PUT_ON_TIME_MUST_SET));
//                return deferredResult;
//            }
//
//            if (artInfoAddParam.getPutOnTime() < System.currentTimeMillis()) {
//                DeferredResult deferredResult = new DeferredResult();
//                deferredResult.setResult(CommonResult.failed(PUT_ON_TIME_MUST_GT_CURRENT_TIME));
//                return deferredResult;
//            }
//            CommonResult<StoreInfo> storeInfoCommonResult = storeInfoService.checkHasStore(entity.getUserId());
//            if (storeInfoCommonResult.getCode() != ResultCode.SUCCESS.getCode()) {
//                DeferredResult deferredResult = new DeferredResult();
//                deferredResult.setResult(storeInfoCommonResult);
//                return deferredResult;
//            }
//        }
        /*String md5 = "";
        StringBuilder sb = new StringBuilder();
        artInfoAddParam.getPics().forEach(item -> {
            sb.append(item.getPicMd5());
        });
        if (artInfoAddParam.getPics().size() == 1) {
            md5 = sb.toString();
        } else {
            md5 = MD5.create().digestHex(sb.toString());
        }*/
//        log.info("-------artInfoAddParam:" + JSON.toJSONString(artInfoAddParam));
        ArtInputInfoAddParam artInputInfoAddParam = new ArtInputInfoAddParam();
        artInputInfoAddParam.setContractAddr(artInfoAddParam.getContractAddr());
        artInputInfoAddParam.setArtName(artInfoAddParam.getArtName());
        artInputInfoAddParam.setArtYear(artInfoAddParam.getArtYear());
        artInputInfoAddParam.setArtKind(artInfoAddParam.getArtType().toString()); //ArtType.getByCode(artInfoAddParam.getArtType()).getName()
        //新增字段
        artInputInfoAddParam.setArtSize(artInfoAddParam.getArtSize());
        artInputInfoAddParam.setArtWeight(artInfoAddParam.getArtWeight());
        artInputInfoAddParam.setArtCondition(artInfoAddParam.getArtCondition().toString());
//        artInputInfoAddParam.setArtManufacturer(artInfoAddParam.getArtManufacturer());
        artInputInfoAddParam.setArtDesc(artInfoAddParam.getArtDesc());
//        artInputInfoAddParam.setArtNfcId(artInfoAddParam.getArtNfcId());
//        artInputInfoAddParam.setArtKind(String.valueOf(artInfoAddParam.getArtType()));
        artInputInfoAddParam.setMd5(artInfoAddParam.getMd5());
//        if (artInfoAddParam.getIntoStore()) {
//            artInputInfoAddParam.setArtPrice(artInfoAddParam.getArtPrice());
//        } else {
//            artInputInfoAddParam.setArtPrice(0L);
//        }
        artInputInfoAddParam.setUserPubkeyM(artInfoAddParam.getUserPubkeyM());
        String pubKeyE = blockChainNftService.fillIn(artInfoAddParam.getUserPubkeyE());
        artInputInfoAddParam.setUserPubkeyE(pubKeyE);
        artInputInfoAddParam.setRandNum(artInfoAddParam.getRandNum());
        artInputInfoAddParam.setMessageIn(artInfoAddParam.getMessageIn());
        artInputInfoAddParam.setWebStart(artInfoAddParam.getWebStart());
        artInputInfoAddParam.setArtSign(artInfoAddParam.getArtSign());
        artInputInfoAddParam.setArtistSign(artInfoAddParam.getArtistSign());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.artLoad(artInputInfoAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("录入艺术品信息成功！合约地址：{}", artInputInfoAddParam.getContractAddr());
                ArtInfo artInfo = artInfoMapper.selectByContractAddr(artInputInfoAddParam.getContractAddr());
                if(artInfo == null){
                    artInfoMapper.insert(entity);
                    artInfoAddParam.getPics().forEach(item -> {
                        item.setArtId(entity.getArtId());
                        artPicInfoService.insert(item);
                    });
//                    if (null != artInfoAddParam.getIntoStore() && artInfoAddParam.getIntoStore()) {
//                        CommonResult<StoreInfo> storeInfoCommonResult = storeInfoService.checkHasStore(entity.getUserId());
//                        StoreInfo storeInfo = storeInfoCommonResult.getData();
//                        StoreArtInfoAddParam storeArtInfoAddParam = new StoreArtInfoAddParam();
//                        storeArtInfoAddParam.setArtId(entity.getArtId());
//                        storeArtInfoAddParam.setStoreId(storeInfo.getId());
//                        storeArtInfoAddParam.setArtPrice(artInfoAddParam.getArtPrice());
//                        storeArtInfoAddParam.setPutOnTime(artInfoAddParam.getPutOnTime());
//                        CommonResult insert = storeArtInfoService.insert(storeArtInfoAddParam);
//                        if (insert.getCode() == ResultCode.SUCCESS.getCode()) {
//                            artPutOnCacheService.save((Long) insert.getData(), storeArtInfoAddParam.getPutOnTime());
//                            log.info("艺术品缓存上架信息成功！id={},putOnTime={}", insert.getData(), storeArtInfoAddParam.getPutOnTime());
//                        }
//                    }
                }else{
                    result = CommonResult.failed(NftError.LOAD_ON_REPEAT);
                    deferredResult.setResult(result);
                }

            } else {
                log.warn("录入艺术品信息失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), artInputInfoAddParam.getContractAddr());
            }
        });
        return deferredResult;
    }

    /**
     * 修改数据
     *
     * @param artInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ArtInfoUpdateParam artInfoUpdateParam) {
        ArtInfo entity = ArtInfoAssembler.INSTANCE.getArtInfo(artInfoUpdateParam);
        artInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param artId
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long artId) {
        ArtInfo entity = new ArtInfo();
        entity.setArtId(artId);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        artInfoMapper.updateSelective(entity);
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
     * @param artId 主键ID
     * @return ArtInfoDetail
     * @date 2020/01/01
     */
    public CommonResult<ArtInfoDetail> selectById(Long artId) {
        ArtInfo artInfo = artInfoMapper.selectById(artId);
        ArtInfoDetail artInfoDetail = ArtInfoAssembler.INSTANCE.getArtInfoDetail(artInfo);
        return CommonResult.success(artInfoDetail);
    }

    /**
     * 根据合约地址查询
     *
     * @param contractAddr
     * @return ArtInfo
     */
    public CommonResult<ArtInfoDetail> selectByContractAddr(String contractAddr) {
        ArtInfo artInfo = artInfoMapper.selectByContractAddr(contractAddr);
        ArtInfoDetail artInfoDetail = ArtInfoAssembler.INSTANCE.getArtInfoDetail(artInfo);
        return CommonResult.success(artInfoDetail);
    }

}
