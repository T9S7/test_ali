package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.common.utils.JsonUtil;
import com.armsmart.jupiter.bs.api.blockchain.BlockChainService;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.ApplyCheckParam;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CompanyKeyParam;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author dong.chu
 * @date 2021/4/24
 */
@Slf4j
@Service
public class AppEthService {

    @Autowired
    private BlockChainService blockChainService;

    @Autowired
    private CheckInfoService checkInfoService;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    /**
     * 申请鉴权
     *
     * @param applyCheckParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> applyCheck(ApplyCheckParam applyCheckParam) {
        DeferredResult<CommonResult> deferredResult = blockChainService.applyCheck(applyCheckParam);
        CheckInfoAddParam checkInfoAddParam = new CheckInfoAddParam();
        ThingInfo thingInfo = thingInfoMapper.selectByContract(applyCheckParam.getContractAddr());
        checkInfoAddParam.setContractAddr(applyCheckParam.getContractAddr());
        checkInfoAddParam.setUserId(applyCheckParam.getUserId().intValue());
        checkInfoAddParam.setThingId(thingInfo.getId());
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                checkInfoAddParam.setCheckType(1);

                log.info("鉴权成功！合约地址：{}", applyCheckParam.getContractAddr());
            } else {
                checkInfoAddParam.setCheckType(0);
                checkInfoAddParam.setFileInfo(result.getMsg());
                log.warn("鉴权失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), applyCheckParam.getContractAddr());
            }
            checkInfoService.insert(checkInfoAddParam);
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * rand
     *
     * @param contractAddr
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> rand(String contractAddr) {
        DeferredResult<CommonResult> deferredResult = blockChainService.rand(contractAddr);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("获取随机数成功！合约地址：{}", contractAddr);
            } else {
                log.warn("获取随机数失败！响应结果：{}，合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });

        return deferredResult;
    }


    /**
     * @param companyKeyParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> setCompanyKey(CompanyKeyParam companyKeyParam) {
        DeferredResult<CommonResult> deferredResult = blockChainService.setCompanyKey(companyKeyParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("获取随机数成功！合约地址：{}", companyKeyParam.getContractAddr());
            } else {
                log.warn("获取随机数失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), companyKeyParam.getContractAddr());
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param contractAddr
     * @date 2020/04/20
     */
    public DeferredResult<CommonResult> getCoinInfo(String contractAddr) {
        DeferredResult<CommonResult> deferredResult = blockChainService.getCoinInfo(contractAddr);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("古币信息查询查询成功！合约地址：{}", contractAddr);
            } else {
                log.warn("古币信息查询失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param contractAddr
     * @date 2020/04/20
     */
    public DeferredResult<CommonResult> getTicateInfo(String contractAddr) {
        DeferredResult<CommonResult> deferredResult = blockChainService.getTicateInfo(contractAddr);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("鉴定人信息查询成功！合约地址：{}", contractAddr);
            } else {
                log.warn("鉴定人信息查询失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param contractAddr
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> getCount(String contractAddr) {
        DeferredResult<CommonResult> deferredResult = blockChainService.getCount(contractAddr);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("鉴权记录查询查询成功！合约地址：{}", contractAddr);
            } else {
                log.warn("鉴权记录查询失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }
}
