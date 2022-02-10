package com.armsmart.jupiter.bs.api.blockchain;

import cn.hutool.json.JSONObject;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ApplyCheckParam;
import com.armsmart.jupiter.bs.api.dto.request.CompanyKeyParam;
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
public class YwyzBlockChainService {

    public static final int KEY_LEN = 256;

    private BlockChainProperties blockChainProperties;

    private BlockChainProperties.Topic topic;

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private YwyzBlockChainService(BlockChainProperties blockChainProperties) {
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
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainFunction.RAND, jsonObject);
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
        return send(contractAddr, BlockChainFunction.RANDTEST, jsonObject);
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
        return send(contractAddr, BlockChainFunction.APPLY_CHECK, jsonObject);
    }

    /**
     * 鉴定录入
     *
     * @param coinInputInfoAddParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
//    public DeferredResult<CommonResult> coinLoad(CoinInputInfoAddParam coinInputInfoAddParam) {
//        JSONObject coinLoadParams = new JSONObject();
//        coinLoadParams.set("contractAddr", coinInputInfoAddParam.getContractAddr());
//        coinLoadParams.set("coinName", coinInputInfoAddParam.getCoinName());
//        coinLoadParams.set("coinYear", coinInputInfoAddParam.getCoinYear());
//        coinLoadParams.set("coinCondition", coinInputInfoAddParam.getCoinCondition());
//        coinLoadParams.set("coinId", coinInputInfoAddParam.getCoinId());
//        coinLoadParams.set("ticateComp", coinInputInfoAddParam.getTicateComp());
//        coinLoadParams.set("ticateUser", coinInputInfoAddParam.getTicateUser());
//        coinLoadParams.set("coinType", coinInputInfoAddParam.getCoinType());
//        coinLoadParams.set("photoAddr", coinInputInfoAddParam.getMd5());
//        coinLoadParams.set("goodSize", coinInputInfoAddParam.getGoodSize());
//        coinLoadParams.set("goodKG", coinInputInfoAddParam.getGoodKG());
//        coinLoadParams.set("tagTime", coinInputInfoAddParam.getTagTime() + "");
//        coinLoadParams.set("pubkeyM", coinInputInfoAddParam.getModulus());
//        String pubkeyE = fillIn(coinInputInfoAddParam.getExponent());
//        coinLoadParams.set("pubkeyE", pubkeyE);
//        coinLoadParams.set("loadSign", coinInputInfoAddParam.getInputSignature());
//        return send(coinInputInfoAddParam.getContractAddr(), BlockChainFunction.COIN_LOAD, coinLoadParams);
//    }

    /**
     * 设置录入人、鉴定人
     *
     * @param assignParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
//    public DeferredResult<CommonResult> setUserKeys(OrderDetailAssignParam assignParam) {
//        JSONObject params = new JSONObject();
//        params.set("contractAddr", assignParam.getContractAddr());
//        String loadPubkeyE = fillIn(assignParam.getInputUser().getExponent());
//        params.set("loadPubkeyM", assignParam.getInputUser().getModulus());
//        params.set("loadPubkeyE", loadPubkeyE);
//        params.set("checkPubkeyM1", assignParam.getTicateUser().getModulus());
//        String checkPubkeyE1 = fillIn(assignParam.getTicateUser().getExponent());
//        params.set("checkPubkeyE1", checkPubkeyE1);
//        params.set("checkPubkeyM2", "");
//        params.set("checkPubkeyE2", "");
//        params.set("checkPubkeyM3", "");
//        params.set("checkPubkeyE3", "");
//        params.set("checkPubkeyM4", "");
//        params.set("checkPubkeyE4", "");
//        params.set("checkPubkeyM5", "");
//        params.set("checkPubkeyE5", "");
//        params.set("compangSign", assignParam.getSignature());
//        return send(assignParam.getContractAddr(), BlockChainFunction.SETS_USER_KEY, params);
//    }

    /**
     * 设置企业公钥
     *
     * @param companyKeyParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> setCompanyKey(CompanyKeyParam companyKeyParam) {
        JSONObject params = new JSONObject();
        params.set("contractAddr", companyKeyParam.getContractAddr());
        String pubKeyE = fillIn(companyKeyParam.getExponent());
        params.set("pubKeyM", companyKeyParam.getModulus());
        params.set("pubKeyE", pubKeyE);
        return send(companyKeyParam.getContractAddr(), BlockChainFunction.SET_COMPANY_KEY, params);
    }

    /**
     * 申请鉴权
     *
     * @param applyCheckParam
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> applyCheck(ApplyCheckParam applyCheckParam) {
        JSONObject params = new JSONObject();
        params.set("contractAddr", applyCheckParam.getContractAddr());
        params.set("signature_In", applyCheckParam.getSignatureIn());
        params.set("check_Time", applyCheckParam.getCheckTime());
//        params.set("check_Addr", applyCheckParam.getCheckAddr());
        params.set("term_Info", applyCheckParam.getTermInfo());
        return send(applyCheckParam.getContractAddr(), BlockChainFunction.APPLY_CHECK, params);
    }

    /**
     * 获取物品信息
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> getCoinInfo(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainFunction.GET_COIN_INFO, jsonObject);
    }

    /**
     * 鉴定人信息确认接口
     *LOAD_INFO_CHECK
     * @param
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
//    public DeferredResult<CommonResult> loadInfoCheck(LoadInfoCheckParam loadInfoCheckParam) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.set("contractAddr", loadInfoCheckParam.getContractAddr());
//        jsonObject.set("loadInfo",loadInfoCheckParam.getLoadInfo());
//        jsonObject.set("is_true",loadInfoCheckParam.getIs_true());
//        jsonObject.set("checkSign",loadInfoCheckParam.getCheckSign());
//        return send(loadInfoCheckParam.getContractAddr(), BlockChainFunction.LOAD_INFO_CHECK, jsonObject);
//    }

    /**
     * 鉴权记录查询
     *
     * @param contractAddr
     * @return org.springframework.web.context.request.async.DeferredResult<CommonResult>
     */
    public DeferredResult<CommonResult> getCount(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainFunction.GET_COUNT, jsonObject);
    }

    /**
     * 获取鉴定人信息
     * loadInfoCheckParam
     */
    public DeferredResult<CommonResult> getTicateInfo(String contractAddr) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("contractAddr", contractAddr);
        return send(contractAddr, BlockChainFunction.GET_COUNT, jsonObject);
    }
    /**
     * kafka调用区块链
     *
     * @param contractAddr 合约地址
     * @param func         请求调用区块链的函数
     * @param params       请求调用区块链函数的参数
     */
    private DeferredResult<CommonResult> send(String contractAddr, BlockChainFunction func, JSONObject params) {
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
            case COIN_LOAD:
            case SET_COMPANY_KEY:
            case SETS_USER_KEY:
            case LOAD_INFO_CHECK:
                sendTopic = topic.getIdentify();
                resultTopic = topic.getIdentifyResult();
                break;
            case GET_COIN_INFO:
            case GET_TICATE_INFO:
            case GET_COUNT:
            case GET_CHECK_STATUS:
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
    private String getTopicKey(String contractAddr, BlockChainFunction func) {
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

}
