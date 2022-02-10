package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.common.utils.JsonUtil;
import com.armsmart.jupiter.bs.api.blockchain.BlockChainNftService;
import com.armsmart.jupiter.bs.api.dao.*;
//import com.armsmart.jupiter.bs.api.dao.StoreArtInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.entity.ArtInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import com.armsmart.jupiter.bs.api.error.NftError;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.async.DeferredResult;

import static com.armsmart.jupiter.bs.api.error.NEError.COIN_LOAD_FAILED;

//import com.armsmart.jupiter.bs.api.dto.request.*;

/**
 * @author dong.chu
 * @date 2021/4/24
 */
@Slf4j
@Service
public class AppNftEthService {

    @Autowired(required = false)
    private BlockChainNftService blockChainNftService;
    @Autowired(required = false)
    private ArtInfoMapper artInfoMapper;
    @Autowired(required = false)
    private ArtPicInfoMapper artPicInfoMapper;
//    @Autowired(required = false)
//    private StoreArtInfoMapper storeArtInfoMapper;
    @Autowired(required = false)
    private ArtOrderInfoMapper artOrderInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @Autowired(required = false)
    private CheckInfoService checkInfoService;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;
    /**
     * 申请鉴权
     *
     * @param applyCheckParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> applyCheck(ApplyCheckParam applyCheckParam) {
        DeferredResult<CommonResult> deferredResult = blockChainNftService.applyCheck(applyCheckParam);
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
    public DeferredResult<CommonResult> rand(String contractAddr,Integer nfcType) {
        ArtInfo artInfo = artInfoMapper.selectByContractAddr(contractAddr);
        if(nfcType == 1){
            //在线录入
            if (artInfo != null) {
                DeferredResult<CommonResult> deferredResult = new DeferredResult<CommonResult>();
                CommonResult result = CommonResult.failed(NftError.LOAD_ON_REPEAT);
                deferredResult.setResult(result);
                return deferredResult;
            } else {
                DeferredResult<CommonResult> deferredResult = blockChainNftService.rand(contractAddr);
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
        }else if(nfcType == 2){
            //在线鉴权
            DeferredResult<CommonResult> deferredResult = blockChainNftService.rand(contractAddr);
            deferredResult.onCompletion(() -> {
                CommonResult result = (CommonResult) deferredResult.getResult();
                if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                    log.info("获取随机数成功！合约地址：{}", contractAddr);
                } else {
                    log.warn("获取随机数失败！响应结果：{}，合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
                }
            });

            return deferredResult;
        }else{
            DeferredResult<CommonResult> deferredResult = new DeferredResult<CommonResult>();
            CommonResult result = CommonResult.failed(NftError.NFC_TYPE_NO_EXIST);
            deferredResult.setResult(result);
            return deferredResult;
        }

    }


    /**
     * @param artKeyParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> setArtPubKey(ArtKeyParam artKeyParam) {
        DeferredResult<CommonResult> deferredResult = blockChainNftService.setArtKey(artKeyParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("设置艺术品公钥成功！合约地址：{}", artKeyParam.getContractAddr());
            } else {
                log.warn("设置艺术品公钥失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), artKeyParam.getContractAddr());
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param artistKeyParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> setUserKey(ArtistKeyParam artistKeyParam) {
        DeferredResult<CommonResult> deferredResult = blockChainNftService.setUserKeys(artistKeyParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("设置艺术家公钥成功！合约地址：{}", artistKeyParam.getContractAddr());
            } else {
                log.warn("设置艺术家公钥失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), artistKeyParam.getContractAddr());
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param artInputInfoAddParam
     * @date 2020/04/20
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> artLoad(ArtInputInfoAddParam artInputInfoAddParam) {
        DeferredResult<CommonResult> deferredResult = blockChainNftService.artLoad(artInputInfoAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("录入艺术品信息成功！合约地址：{}", artInputInfoAddParam.getContractAddr());
            } else {
                log.warn("录入艺术品信息失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), artInputInfoAddParam.getContractAddr());
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }


    /**
     * @param contractAddr
     * @date 2020/04/20
     */
    public DeferredResult<CommonResult> getArtInfo(String contractAddr) {
        Object ii = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GetArtInfoAddParam getArtInfoAddParam = new GetArtInfoAddParam();
        getArtInfoAddParam.setContractAddr(contractAddr);
        if ("anonymousUser".equals(ii)) {
            getArtInfoAddParam.setPubKeyM("");
        } else {
            AppUserDetails userDetails = (AppUserDetails) ii;
            UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(userDetails.getUserInfo().getId().toString());
            if(userAuthentication != null && userAuthentication.getIsCert()) {
                getArtInfoAddParam.setPubKeyM(userAuthentication.getPublicKeyM());
            }else {
                getArtInfoAddParam.setPubKeyM("");
            }
        }
        DeferredResult<CommonResult> deferredResult = blockChainNftService.getArtInfo(getArtInfoAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                result = CommonResult.failed(COIN_LOAD_FAILED);
                deferredResult.setResult(result);
                log.info("艺术品信息查询查询成功！合约地址：{}", contractAddr);
            } else {
                log.warn("艺术品信息查询失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * @param contractAddr
     * @date 2020/04/20
     */
    public DeferredResult<CommonResult> resetContract(String contractAddr){
        DeferredResult<CommonResult> deferredResult = blockChainNftService.resetContract(contractAddr);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                ArtInfo artInfo = artInfoMapper.selectByContractAddr(contractAddr);
                if (null != artInfo) {
                    Long artId = artInfo.getArtId();
                    artInfoMapper.physicalDel(artId);
                    artPicInfoMapper.physicalDelByArtId(artId);
//                    storeArtInfoMapper.physicalDelByArtId(artId);
                    artOrderInfoMapper.physicalDelByArtId(artId);
                }
                log.info("重置艺术品合约成功！合约地址：{}", contractAddr);
            } else {
                log.warn("重置艺术品合约失败！响应结果：{},合约地址：{}", JsonUtil.bean2Json(result), contractAddr);
            }
        });
        //差一个区块链结果推送消息推送
        return deferredResult;
    }

    /**
     * 变更所有者
     * @param param
     * @return
     */
    public DeferredResult<CommonResult> modifyOwner(ModifyOwnerAddInfoParam param){
        ModifyOwnerAddParam modifyOwnerAddParam = new ModifyOwnerAddParam();
            modifyOwnerAddParam.setArtistSign(param.getArtistSign());
            modifyOwnerAddParam.setArtSign(param.getArtSign());
            modifyOwnerAddParam.setAuthTokenId(param.getContractAddr());
            modifyOwnerAddParam.setMessageIn(param.getMessageIn());
            modifyOwnerAddParam.setWebStart(param.getMessageIn());
            modifyOwnerAddParam.setModifyType(param.getModifyType());
            modifyOwnerAddParam.setTermInfo(param.getTermInfo());
            UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
            modifyOwnerAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
            modifyOwnerAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.modifyOwner(modifyOwnerAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("变更所有者成功");
            } else {
                log.warn("变更所有者失败");
            }
        });
        return deferredResult;
    }

    /**
     * 发布合约
     * @param param
     * @return
     */
    public DeferredResult<CommonResult> instanceDepoly(InstanceDepolyParam param){
        DeferredResult<CommonResult> deferredResult = blockChainNftService.instanceDepoly(param);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                log.info("合约发布成功：{}", result.getData());
            } else {
                log.warn("合约发布失败，失败原因：", JsonUtil.bean2Json(result));
            }
        });
        return deferredResult;
    }
}
