package com.armsmart.jupiter.bs.api.blockchain;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dto.response.NfcReadDetail;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.error.NftError;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.Optional;

/**
 * 区块链响应结果消费处理
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class BlockChainConsumer {

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    /**
     * 确认结果主题
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = "#{'${block-chain.topic.all-result}'.split(',')}", groupId = "${block-chain.topic.platform-to-chain-group}")
    public void platformConsumer(ConsumerRecord<String, String> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            Optional message = Optional.ofNullable(record.value());
            if (message.isPresent()) {
                String msg = message.get().toString();
                log.info("platformConsumer 消费了： Topic:" + topic + ",Message:" + msg);
                JSONObject jsonObject = JSONUtil.parseObj(msg);
                String topicKey = jsonObject.getStr("topic-key");
                CommonResult commonResult = jsonObject.get("result", CommonResult.class);
                commonResult.setMsg(jsonObject.getJSONObject("result").getStr("message"));
                if (BlockChainResult.deferredResultMap.containsKey(topicKey)) {
                    switch (jsonObject.getStr("topic-func")){
                        case "getArtInfo":
                            JSONObject data = jsonObject.getJSONObject("result").getJSONObject("data");
                            NfcReadDetail nfcReadDetail = new NfcReadDetail();
                            nfcReadDetail.setArtName(data.getStr("name"));
                            nfcReadDetail.setThingDesc(data.getStr("thingDesc"));
                            nfcReadDetail.setArtYear(data.getStr("year"));
                            nfcReadDetail.setAuthor(data.getStr("author"));
                            nfcReadDetail.setContractAddr(jsonObject.getJSONObject("topic-params").getStr("tokenId"));
                            nfcReadDetail.setThingCondition(data.getStr("thingCondition"));
                            nfcReadDetail.setThingElement(data.getStr("thingElement"));
                            nfcReadDetail.setThingSize(data.getStr("thingSize"));
//                            nfcReadDetail.setThingWeight(Integer.valueOf(data.getStr("thingWeight")));
                            nfcReadDetail.setIsSelf(Integer.valueOf(data.getStr("isSelf")));
                            nfcReadDetail.setIsHuaXiaCard(data.getInt("isHuaXiaCard"));
                            nfcReadDetail.setIsChangedRight(data.getInt("isChangedRight"));
                            ThingInfo param = thingInfoMapper.selectByContract(jsonObject.getJSONObject("topic-params").getStr("tokenId"));
                            if(param != null){
                                nfcReadDetail.setId(param.getId());
                                nfcReadDetail.setUserId(param.getUserId());
                                nfcReadDetail.setCurrentState(param.getCurrentState());
                                nfcReadDetail.setCurrentPrice(param.getCurrentPrice());
                                nfcReadDetail.setPics(param.getPics());
                                nfcReadDetail.setCreateTime(param.getCreateTime());
                                nfcReadDetail.setThingType(param.getThingType());
                                nfcReadDetail.setThingWeight(param.getThingWeight());
                                nfcReadDetail.setUploadStatus(param.getUploadStatus());
                            }
                            commonResult = CommonResult.success(nfcReadDetail);
                            break;
                        case "setArtInfo":
                            JSONObject object = jsonObject.getJSONObject("topic-params");
                            ThingInfo thingInfo = new ThingInfo();
                            thingInfo.setUserId(Integer.valueOf("-1"));
                            thingInfo.setCurrentState(99);
                            thingInfo.setCreateTime(System.currentTimeMillis());
                            thingInfo.setIsDel(false);
                            thingInfo.setContractAddr(object.getStr("tokenId"));
                            thingInfo.setThingDesc(object.getStr("thingDesc"));
                            thingInfo.setAuthor(object.getStr("author"));
                            thingInfo.setArtYear(object.getStr("year"));
                            thingInfo.setArtName(object.getStr("name"));
                            thingInfo.setUploaderId(Integer.valueOf(-1));
                            thingInfo.setIsOfficial(false);
                            ThingInfo artInfo = thingInfoMapper.selectByContractAddr(object.getStr("tokenId"));
                            if (commonResult.getCode() == ResultCode.SUCCESS.getCode()) {
                                if (artInfo == null) {
                                    thingInfo.setUploadStatus(true);
                                    thingInfoMapper.insert(thingInfo);
                                    commonResult = CommonResult.success(thingInfo.getId());
                                } else {
                                    commonResult = CommonResult.failed(NftError.LOAD_ON_REPEAT);
                                }
                            } else {
                                thingInfo.setUploadStatus(false);
                                thingInfo.setIsDel(true);
                                thingInfoMapper.insert(thingInfo);
                            }
                            break;
                    }
                    DeferredResult<CommonResult> result = BlockChainResult.deferredResultMap.get(topicKey);
                    result.setResult(commonResult);
                    BlockChainResult.deferredResultMap.remove(topicKey);
                }
            }
        } catch (Exception e) {
            log.error("Kafka消费异常：", e);
        }finally {
            ack.acknowledge();
        }
    }
}
