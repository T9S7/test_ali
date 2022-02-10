package com.armsmart.jupiter.bs.api.blockchain;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
//import com.alibaba.fastjson.JSON;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.service.RandSeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * 区块链服务
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class BlockChainNftService {

    public static final int KEY_LEN = 256;

    private BlockChainProperties blockChainProperties;

    private BlockChainProperties.Topic topic;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RandSeedService randSeedService;

    @Autowired
    private BlockChainNftService(BlockChainProperties blockChainProperties) {
        this.blockChainProperties = blockChainProperties;
        this.topic = blockChainProperties.getTopic();
    }

    /**
     * 获取随机数
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> rand(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
//        jsonObject.set("contractAddr", contractAddr);
        jsonObject.set("tokenId",contractAddr);
        return ywyzSend(contractAddr, BlockChainNftFunction.RAND, jsonObject);
    }


    /**
     * 获取随机数--测试接口
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> randTest(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainNftFunction.RANDTEST, jsonObject);
    }


    /**
     * 获鉴权
     *
     * @param jsonObject
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> applyCheck(JSONObject jsonObject) {
        //JSONObject jsonObject = new JSONObject();
        //jsonObject.set("contractAddr", contractAddr);
        String contractAddr = jsonObject.getStr("contractAddr");
        return send(contractAddr, BlockChainNftFunction.APPLY_CHECK, jsonObject);
    }

    /**
     * 物品信息上链
     *
     * @param thingInputInfoAddParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> thingLoad(ThingInputInfoAddParam thingInputInfoAddParam) {
        log.info("物品上联信息:{}",thingInputInfoAddParam.toString());
        JSONObject artLoadParams = new JSONObject();
        artLoadParams.set("tokenId", thingInputInfoAddParam.getContractAddr());
        artLoadParams.set("name", thingInputInfoAddParam.getArtName());
        artLoadParams.set("year", thingInputInfoAddParam.getArtYear());
        artLoadParams.set("md5", thingInputInfoAddParam.getMd5());
        artLoadParams.set("userPubkeyM",thingInputInfoAddParam.getUserPubkeyM());
        artLoadParams.set("userPubkeyE",thingInputInfoAddParam.getUserPubkeyE());
        artLoadParams.set("randNum", thingInputInfoAddParam.getRandNum());
        artLoadParams.set("messageIn",thingInputInfoAddParam.getMessageIn());
        artLoadParams.set("artSign",thingInputInfoAddParam.getArtSign());
        artLoadParams.set("webStart", thingInputInfoAddParam.getWebStart());
        artLoadParams.set("artistSign", thingInputInfoAddParam.getArtistSign());
        artLoadParams.set("author",thingInputInfoAddParam.getAuthor());
        //新增字段
        artLoadParams.set("thingDesc",thingInputInfoAddParam.getArtDesc());
        artLoadParams.set("thingSize",thingInputInfoAddParam.getArtSize());
        artLoadParams.set("thingWeight",thingInputInfoAddParam.getArtWeight());
        artLoadParams.set("thingCondition",thingInputInfoAddParam.getArtCondition());
        artLoadParams.set("thingElement",thingInputInfoAddParam.getThingElement());
        artLoadParams.set("thingType",thingInputInfoAddParam.getThingType());
        return ywyzSend(thingInputInfoAddParam.getContractAddr(), BlockChainNftFunction.SET_ART_INFO, artLoadParams);
    }

    /**
     * 录入艺术品信息
     *
     * @param artInputInfoAddParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> artLoad(ArtInputInfoAddParam artInputInfoAddParam) {
        JSONObject artLoadParams = new JSONObject();
        artLoadParams.set("contractAddr", artInputInfoAddParam.getContractAddr());
        artLoadParams.set("artName", artInputInfoAddParam.getArtName());
        artLoadParams.set("artYear", artInputInfoAddParam.getArtYear());
        artLoadParams.set("artKind", artInputInfoAddParam.getArtKind());
        artLoadParams.set("photoMd5", artInputInfoAddParam.getMd5());
        artLoadParams.set("artPrice", artInputInfoAddParam.getArtPrice());
        artLoadParams.set("userPubkeyM",artInputInfoAddParam.getUserPubkeyM());
        artLoadParams.set("userPubkeyE",artInputInfoAddParam.getUserPubkeyE());
        artLoadParams.set("randNum", artInputInfoAddParam.getRandNum());
        artLoadParams.set("messageIn",artInputInfoAddParam.getMessageIn());
        artLoadParams.set("artSign",artInputInfoAddParam.getArtSign());
        artLoadParams.set("webStart", artInputInfoAddParam.getWebStart());
        artLoadParams.set("artistSign", artInputInfoAddParam.getArtistSign());

        //新增字段
        String artAddtional = trimToEmpty(artInputInfoAddParam.getArtNfcId()) + "@" + trimToEmpty(artInputInfoAddParam.getArtSize()) + "@" + artInputInfoAddParam.getArtWeight() + "@" + artInputInfoAddParam.getArtCondition()
                + "@" + trimToEmpty(artInputInfoAddParam.getArtManufacturer()) + "@" + trimToEmpty(artInputInfoAddParam.getArtBarcode());
        artLoadParams.set("artAddtional", artAddtional);
//        artLoadParams.set("artSize", artInputInfoAddParam.getArtSize());
//        artLoadParams.set("artWeight", artInputInfoAddParam.getArtWeight());
//        artLoadParams.set("artCondition", artInputInfoAddParam.getArtCondition());
//        artLoadParams.set("artManufacturer", artInputInfoAddParam.getArtManufacturer());
//        artLoadParams.set("artBarcode", artInputInfoAddParam.getArtBarcode());
//        artLoadParams.set("artDesc", artInputInfoAddParam.getArtDesc());
//        log.info("-------artLoadParams:"+ JSON.toJSONString(artLoadParams));
        return send(artInputInfoAddParam.getContractAddr(), BlockChainNftFunction.SET_ART_INFO, artLoadParams);
    }
    /**
     * 变更所有者公钥
     */
    public DeferredResult<CommonResult> modifyOwner(ModifyOwnerAddParam param) {
        JSONObject modifyOwnerParams = new JSONObject();
        modifyOwnerParams.set("authTokenId", param.getAuthTokenId());
        modifyOwnerParams.set("modifyType", param.getModifyType());
        modifyOwnerParams.set("nextDealTokenId",param.getNextDealTokenId());
        modifyOwnerParams.set("ownerPubkeyM",param.getUserPubkeyM());
        modifyOwnerParams.set("ownerPubkeyE",param.getUserPubkeyE());
        modifyOwnerParams.set("messageIn",param.getMessageIn());
        modifyOwnerParams.set("artSign",param.getArtSign());
        modifyOwnerParams.set("webStart",param.getWebStart());
        modifyOwnerParams.set("artistSign",param.getArtistSign());
        modifyOwnerParams.set("termInfo",param.getTermInfo());
        return ywyzSend(param.getAuthTokenId(), BlockChainNftFunction.MODIFYOWNER, modifyOwnerParams);
    }


    /**
     * Jupiter 发布合约
     */
    public DeferredResult<CommonResult> instanceDepoly(InstanceDepolyParam param){
     JSONObject object = new JSONObject();
     object.set("instanceType",param.getInstanceType());
     object.set("isHuaXiaCard",param.getIsHuaXiaCard());
        return ywyzSend("", BlockChainNftFunction.INSTANCEDEPOLY, object);
    }
    /**
     * 设置艺术家公钥
     *
     * @param artistKeyParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> setUserKeys(ArtistKeyParam artistKeyParam) {
        JSONObject params = new JSONObject();
        params.set("contractAddr", artistKeyParam.getContractAddr());
        String userPubkeyE = fillIn(artistKeyParam.getUserPubkeyE());
        params.set("userPubkeyM", artistKeyParam.getUserPubkeyM());
        params.set("userPubkeyE", userPubkeyE);

        params.set("randNum",artistKeyParam.getRandNum());
        params.set("messageIn", artistKeyParam.getMessageIn());
        params.set("artWorkSign", artistKeyParam.getArtWorkSign());

        return send(artistKeyParam.getContractAddr(), BlockChainNftFunction.SET_USER_KEY, params);
    }

    /**
     * 设置艺术品公钥
     *
     * @param artKeyParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> setArtKey(ArtKeyParam artKeyParam) {
        JSONObject params = new JSONObject();
        params.set("contractAddr", artKeyParam.getContractAddr());
        String pubKeyE = fillIn(artKeyParam.getExponent());
        params.set("pubKeyM", artKeyParam.getModulus());
        params.set("pubKeyE", pubKeyE);
        return send(artKeyParam.getContractAddr(), BlockChainNftFunction.SET_ART_PUB_KEY, params);
    }

    /**
     * 申请鉴权
     *
     * @param applyCheckParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
//    public DeferredResult<CommonResult> applyCheck(ApplyCheckParam applyCheckParam) {
//        JSONObject params = new JSONObject();
//        params.set("contractAddr", applyCheckParam.getContractAddr());
//        params.set("signature_In", applyCheckParam.getSignatureIn());
//        params.set("check_Time", applyCheckParam.getCheckTime());
////        params.set("check_Addr", applyCheckParam.getCheckAddr());
//        params.set("term_Info", applyCheckParam.getTermInfo());
//        return send(applyCheckParam.getContractAddr(), BlockChainNftFunction.APPLY_CHECK, params);
//    }
    public DeferredResult<CommonResult> applyCheck(ApplyCheckParam applyCheckParam) {
        JSONObject params = new JSONObject();
        params.set("tokenId", applyCheckParam.getContractAddr());
        params.set("signatureIn", applyCheckParam.getSignatureIn());
        params.set("messageIn", applyCheckParam.getRandHex());
//        params.set("check_Addr", applyCheckParam.getCheckAddr());
        params.set("termInfo", applyCheckParam.getTermInfo());
        return ywyzSend(applyCheckParam.getContractAddr(), BlockChainNftFunction.APPLY_CHECK, params);
    }

    /**
     * 获取艺术品信息
     *
     * @param param
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> getArtInfo(GetArtInfoAddParam param) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("tokenId", param.getContractAddr());
        jsonObject.set("pubKeyM",param.getPubKeyM());
        return ywyzSend(param.getContractAddr(), BlockChainNftFunction.GET_ART_INFO, jsonObject);
    }


    /**
     * 重置艺术品合约
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> resetContract(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainNftFunction.RESET_CONTRACT, jsonObject);
    }


    /**
     * 鉴权记录查询
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> getCount(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainNftFunction.GET_COUNT, jsonObject);
    }


    /**
     * kafka调用区块链
     *
     * @param contractAddr 合约地址
     * @param func         请求调用区块链的函数
     * @param params       请求调用区块链函数的参数
     */
    private DeferredResult<CommonResult> send(String contractAddr, BlockChainNftFunction func, JSONObject params) {
        DeferredResult<CommonResult> deferredResult = new DeferredResult<>(blockChainProperties.getTimeout());
        deferredResult.onTimeout(new BlockChainTimeoutThread(deferredResult));
        String sendTopic = "";
        String resultTopic = "";
        switch (func) {
            case APPLY_CHECK:
            case RAND:
                sendTopic = topic.getAuth();
                resultTopic = topic.getAuthResult();
                break;
            case SET_ART_PUB_KEY:
            case SET_USER_KEY:
            case SET_ART_INFO:
            case RESET_CONTRACT:
            case GET_ART_INFO:
                sendTopic = topic.getIdentify();
                resultTopic = topic.getIdentifyResult();
                break;
            case DEPLOY:
            case ACCESS:
            case MINER:
                sendTopic = topic.getCommon();
                resultTopic = topic.getCommonResult();
                break;
            case RANDTEST:
                sendTopic = topic.getDemo();
                resultTopic = topic.getDemoResult();
                break;
            default:
                log.warn("Unsupported block chain function {}!", func.getFunc());
                deferredResult.setResult(CommonResult.failed());
                return deferredResult;
        }
        String topicKey = getTopicKey(contractAddr, func);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("topic-key", topicKey);
        jsonObject.set("topic-params", params);
        jsonObject.set("topic-func", func.getFunc());
        jsonObject.set("result-topic", resultTopic);
        log.info("准备发送{}消息为：{}", func.getName(), jsonObject);
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(sendTopic, jsonObject.toString());
        log.info("异步结果查看：" + future.toString());
        String finalSendTopic = sendTopic;
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(finalSendTopic + " - 生产者 发送消息失败：" + throwable.getMessage());
                deferredResult.setResult(CommonResult.failed());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                //成功的处理
                log.info(finalSendTopic + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
                BlockChainResult.deferredResultMap.put(topicKey, deferredResult);
            }
        });
        return deferredResult;
    }

    private DeferredResult<CommonResult> ywyzSend(String contractAddr, BlockChainNftFunction func, JSONObject params) {
        params.set("channelName","aszz");
        params.set("chaincodeId","jupiterArt01");
        DeferredResult<CommonResult> deferredResult = new DeferredResult<>(blockChainProperties.getTimeout());
        deferredResult.onTimeout(new BlockChainTimeoutThread(deferredResult));
        String sendTopic = "";
        String resultTopic = "";
        switch (func) {
            case APPLY_CHECK:
            case INSTANCEDEPOLY:
            case RAND:
            case GET_ART_INFO:
                sendTopic = topic.getAuth();
                resultTopic = topic.getAuthResult();
                break;
            case SET_ART_PUB_KEY:
            case SET_USER_KEY:
            case SET_ART_INFO:
            case RESET_CONTRACT:
                sendTopic = topic.getIdentify();
                resultTopic = topic.getIdentifyResult();
                break;
            case MODIFYOWNER:
                sendTopic = topic.getTransaction();
                resultTopic = topic.getTransactionResult();
                break;
            case DEPLOY:
            case ACCESS:
            case MINER:
                sendTopic = topic.getCommon();
                resultTopic = topic.getCommonResult();
                break;
            case RANDTEST:
                sendTopic = topic.getDemo();
                resultTopic = topic.getDemoResult();
                break;
            default:
                log.warn("Unsupported block chain function {}!", func.getFunc());
                deferredResult.setResult(CommonResult.failed());
                return deferredResult;
        }
        String topicKey = getTopicKey(contractAddr, func);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("topic-key", topicKey);
        jsonObject.set("topic-params", params);
        jsonObject.set("topic-func", func.getFunc());
        jsonObject.set("result-topic", resultTopic);
        log.info("准备发送{}消息为：{}", func.getName(), jsonObject);
        //发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(sendTopic, jsonObject.toString());
        log.info("异步结果查看：" + future.toString());
        String finalSendTopic = sendTopic;
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(finalSendTopic + " - 生产者 发送消息失败：" + throwable.getMessage());
                deferredResult.setResult(CommonResult.failed());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                //成功的处理
                log.info(finalSendTopic + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
                BlockChainResult.deferredResultMap.put(topicKey, deferredResult);
            }
        });
        return deferredResult;
    }


    /**
     * 获取topic key
     *
     * @param contractAddr
     * @param func
     * @return java.lang.String
     */
    private String getTopicKey(String contractAddr, BlockChainNftFunction func) {
        return contractAddr + func.getFunc() + System.currentTimeMillis();
    }

    /**
     * 指数补0
     *
     * @param param
     * @return java.lang.String
     */
    public String fillIn(String param) {
        StringBuilder paramSb = new StringBuilder();
        int paramSbLen = param.length();
        if (paramSbLen < KEY_LEN) {
            for (int i = 0; i < KEY_LEN - paramSbLen; i++) {
                paramSb.append("0");
            }
        }
        paramSb.append(param);
        return paramSb.toString();
    }

    public  String trimToEmpty(String str)
    {
        return str == null ? "" : str.trim();
    }

}
