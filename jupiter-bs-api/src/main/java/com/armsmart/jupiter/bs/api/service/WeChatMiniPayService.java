package com.armsmart.jupiter.bs.api.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.OrderInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingSellInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.MiniPayDetail;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.wxpay.WeChatMiniPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.armsmart.jupiter.bs.api.error.NEError.THING_SELL_ING;

@Slf4j
@Service
public class WeChatMiniPayService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatMiniPayProperties weChatMiniPayProperties;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private TlOrderService tlOrderService;

    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional(rollbackFor = Exception.class)
    public CommonResult pay(WechatMiniPayParam wechatMiniPayParam){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", weChatMiniPayProperties.getAppid());
        requestMap.put("secret", weChatMiniPayProperties.getAppsecret());
        requestMap.put("code", wechatMiniPayParam.getCode());

        log.info("--requestMap:"+ JSON.toJSONString(requestMap));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class,requestMap);
        JSONObject jsonObject=JSONObject.parseObject(responseEntity.getBody());
        log.info("--jsonObject:"+ JSON.toJSONString(jsonObject));
        String openId=jsonObject.getString("openid");
        String session_key=jsonObject.getString("session_key");

        //?????????????????????????????????
//        OrderInfoDetail orderInfoDetail = orderInfoService.selectById(wechatMiniPayParam.getOrderId()).getData();
        DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
        OrderInfo orderInfoDetail = orderInfoMapper.selectById(wechatMiniPayParam.getOrderId());
        AgentCollectApplyParam agentCollectApplyParam = new AgentCollectApplyParam();
        agentCollectApplyParam.setPayMethod(1);
        if(orderInfoDetail.getTlOrderRequest() != null){
            agentCollectApplyParam.setBizOrderNo(orderInfoDetail.getTlOrderRequest());
        }else {
            agentCollectApplyParam.setBizOrderNo(Long.toString(wechatMiniPayParam.getOrderId()));
        }
        if(orderInfoDetail.getSonTradeType() != null && orderInfoDetail.getThingName().equals("?????????????????????")) {
            agentCollectApplyParam.setPayerId(Integer.toString(orderInfoDetail.getSellerId()));
        }else{
            agentCollectApplyParam.setPayerId(Integer.toString(orderInfoDetail.getBuyerId()));
        }
        agentCollectApplyParam.setAmount(orderInfoDetail.getPrice());
        agentCollectApplyParam.setFee(0l);

        //???????????????????????????
//        Date date = new Date(payDeadline);
        agentCollectApplyParam.setOrderExpireDatetime(sdf.format(payDeadline));

        List<RecieverListParam> recieverList = new ArrayList<>();
        RecieverListParam recieverListParam = new RecieverListParam();
        //?????????????????????????????????id
        if(orderInfoDetail.getSonTradeType() != null && orderInfoDetail.getThingName().equals("?????????????????????")){
            recieverListParam.setBizUserId(Integer.toString(orderInfoDetail.getBuyerId()));
        }else{
            recieverListParam.setBizUserId(Integer.toString(orderInfoDetail.getSellerId()));
        }
        recieverListParam.setAmount(orderInfoDetail.getPrice());
        recieverList.add(recieverListParam);

        agentCollectApplyParam.setRecieverList(recieverList);

        TlWxPayParam tlWxPayParam = new TlWxPayParam();
        tlWxPayParam.setSubAppid("");
        tlWxPayParam.setLimitPay("");
        tlWxPayParam.setAcct(openId);
        tlWxPayParam.setAmount(orderInfoDetail.getPrice());

        agentCollectApplyParam.setTlWxPayParam(tlWxPayParam);
        return  tlOrderService.agentCollectApply(agentCollectApplyParam);
    }

  //?????????????????????
    public CommonResult<MiniPayDetail> addOrder(AddOrderParam addOrderParam){
        if(addOrderParam.getThingId() != null && addOrderParam.getBusinessType() ==1){
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(addOrderParam.getThingId());
            if(thingSellInfo != null){
                return CommonResult.failed(THING_SELL_ING);
            }
        }
        OrderInfo orderInfoB = orderInfoMapper.selectBySeller(addOrderParam.getThingId(),addOrderParam.getUserId());
        MiniPayDetail miniPayDetail = new MiniPayDetail();
        if(orderInfoB != null){
            miniPayDetail.setPayAgain(1);
//            Map<String,Object> orderId = new HashMap<>();
            if(orderInfoB.getTlOrderRequest() == null){
                miniPayDetail.setBizOrderNo(orderInfoB.getOrderId().toString());
            }else{
                miniPayDetail.setBizOrderNo(orderInfoB.getTlOrderRequest());
            }
            miniPayDetail.setOrderId(Long.valueOf(-1));
            return CommonResult.success(miniPayDetail);
        }
        miniPayDetail.setPayAgain(0);
        DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
        CommonResult commonResult = null;
//        HashMap<String, Object> orderId = new HashMap<>();
        switch (addOrderParam.getBusinessType()){
            case 1:         //?????????????????????
                OrderInfo orderIn= new OrderInfo();
                orderIn.setOrderSn("XXX");
                orderIn.setThingId(addOrderParam.getThingId());
                orderIn.setOrderCategory(0);
                orderIn.setThingName("?????????????????????");
                orderIn.setThingSellId(-2l);
                orderIn.setPayStatus(0);
                orderIn.setPrice(addOrderParam.getAmount());
                orderIn.setOrderStatus(1);//?????????
                orderIn.setPayType(1);
                orderIn.setSellerId(Integer.valueOf(addOrderParam.getUserId()));
                orderIn.setBuyerId(Integer.valueOf(-1));//?????????????????????   ??????id?????????id????????????
                orderIn.setCreateTime(System.currentTimeMillis());
                orderIn.setIsDel(false);
                orderIn.setBuyerDel(true);
                orderIn.setSellerDel(false);
                orderIn.setTradeType(2);     //??????????????????
                orderIn.setSonTradeType(71); //?????????????????????
                orderIn.setPayDeadline(payDeadline.getTime());
                orderInfoMapper.insert(orderIn);
                miniPayDetail.setOrderId(orderIn.getOrderId());
                miniPayDetail.setBizOrderNo(orderIn.getOrderId().toString());
//                orderId.put("orderId",orderIn.getOrderId());
//                orderId.put("bizOrdNo",orderIn.getOrderId());
                commonResult = CommonResult.success(miniPayDetail);
                break;
            case 2:         //?????????????????????
                OrderInfo orderBuy= new OrderInfo();
                orderBuy.setOrderSn("XXX");
                orderBuy.setOrderCategory(0);
                orderBuy.setThingId(addOrderParam.getThingId());
                orderBuy.setThingName("?????????????????????");
                orderBuy.setThingSellId(Long.valueOf(addOrderParam.getThingSellId()));
                orderBuy.setPrice(addOrderParam.getAmount()); //??????????????????????????????
                orderBuy.setPayType(1);
                orderBuy.setOrderStatus(1);//?????????
                orderBuy.setPayStatus(0);
                orderBuy.setSellerId(Integer.valueOf(addOrderParam.getUserId())); //???????????????
                orderBuy.setBuyerId(Integer.valueOf(addOrderParam.getUserId()));
                orderBuy.setCreateTime(System.currentTimeMillis());
                orderBuy.setIsDel(false);
                orderBuy.setBuyerDel(false);
                orderBuy.setSellerDel(true);
                orderBuy.setPayDeadline(payDeadline.getTime());
                orderBuy.setTradeType(2);     //??????????????????
                orderBuy.setSonTradeType(71); //????????????
                orderInfoMapper.insert(orderBuy);
                miniPayDetail.setOrderId(orderBuy.getOrderId());
                miniPayDetail.setBizOrderNo(orderBuy.getOrderId().toString());
//                orderId.put("orderId",orderIn.getOrderId());
//                orderId.put("bizOrdNo",orderIn.getOrderId());
                commonResult = CommonResult.success(miniPayDetail);
                break;
            case 3:         //????????????
                OrderInfo orderInfo = orderInfoMapper.selectById(addOrderParam.getOrderId());
                String orderIdAgain = orderInfoService.getOrderId(orderInfo.getBuyerId().toString(),2);
                if(orderInfo.getPayDeadline()< System.currentTimeMillis()){
                    return CommonResult.failed("????????????");
                }
                orderInfo.setPayType(1);
                orderInfo.setTlOrderRequest(orderIdAgain);
                orderInfo.setSonTradeType(77); //????????????
                orderInfoMapper.updateSelective(orderInfo);
                miniPayDetail.setOrderId(orderInfo.getOrderId());
                miniPayDetail.setBizOrderNo(orderIdAgain);
                commonResult = CommonResult.success(miniPayDetail);
                break;
        }
        return commonResult;
    }
}
