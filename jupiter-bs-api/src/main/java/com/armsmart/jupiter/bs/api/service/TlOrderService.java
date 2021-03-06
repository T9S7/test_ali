package com.armsmart.jupiter.bs.api.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenResponse;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.cache.OrderPayCacheService;
import com.armsmart.jupiter.bs.api.dao.*;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.PayCashDetail;
import com.armsmart.jupiter.bs.api.entity.*;
import com.armsmart.jupiter.bs.api.tlpay.TlPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.armsmart.jupiter.bs.api.error.NEError.*;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;

@Slf4j
@Service
public class TlOrderService extends CertBeforeService {

    @Autowired(required = false)
    private TlPayProperties tlPayProperties;

    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;

    @Autowired(required = false)
    private AccountInfoMapper accountInfoMapper;

    @Autowired(required = false)
    private AccountCashDetailMapper accountCashDetailMapper;

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @Autowired
    private OrderPayCacheService orderPayCacheService;

    @Autowired(required = false)
    private RefundInfoMapper refundInfoMapper;

    @Autowired(required = false)
    private MemberService memberService;

    @Autowired(required = false)
    private OrderInfoService orderInfoService;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);


    /**
     * ??????????????????
     * @param depositApplyParam
     * @return
     */
    public CommonResult depositApply(DepositApplyParam depositApplyParam){
        CommonResult commonResult = null;
        String orderId = orderInfoService.getOrderId(depositApplyParam.getBizUserId(),1);
        DateTime payEndTime = DateUtil.offsetMinute(new Date(), 8);
        final String orderExpireDatetime = sdf.format(payEndTime); //??????????????????
        final BizParameter param = new BizParameter();
        final Map<String,Object> payMatch = new HashMap<>();
        JSONArray ii = new JSONArray();
        String payMethodVar = "";
        // ????????????
        Long validateType = 1L;
        switch (depositApplyParam.getPayMethod()){
            case 1:
                payMethodVar = "WECHATPAY_MINIPROGRAM_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("subAppid",depositApplyParam.getTlWxPayParam().getSubAppid());
                payMatch.put("limitPay","");
                payMatch.put("amount",depositApplyParam.getTlWxPayParam().getAmount());
                payMatch.put("acct",depositApplyParam.getTlWxPayParam().getAcct());
                validateType = 0L;
                break;
            case 2:
                payMethodVar = "SCAN_ALIPAY_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("amount",depositApplyParam.getAmount());
                payMatch.put("limitPay","");
                validateType = 0l;
                break;
            case 3:
                payMethodVar = "BALANCE";
                payMatch.put("accountSetNo",tlPayProperties.getTgAccount());
                payMatch.put("amount",depositApplyParam.getAmount());
                ii.add(payMatch);
                validateType = 2L;
                break;
            case 4:
                payMethodVar = "QUICKPAY_VSP";
                payMatch.put("bankCardNo", client.encrypt(depositApplyParam.getBankCardNo()));
                payMatch.put("amount", depositApplyParam.getAmount());
                validateType = 1L;
                break;
        }
        // ??????????????????
        final HashMap<String, Object> payMethod = new HashMap<>();
        payMethod.put(payMethodVar,payMatch);
        param.put("bizUserId", depositApplyParam.getBizUserId());
        param.put("bizOrderNo",orderId);
        param.put("accountSetNo", tlPayProperties.getTgAccount());
        param.put("amount",depositApplyParam.getAmount());
        param.put("fee", 0L);
        param.put("validateType", validateType);
        param.put("frontUrl", "http://ceshi.allinpay.com");
        param.put("backUrl", "http://ceshi.allinpay.com");
        param.put("orderExpireDatetime", orderExpireDatetime);
        param.put("payMethod", JSONObject.toJSON(payMethod));
//        param.put("goodsName", "computer");
        param.put("industryCode", tlPayProperties.getIndustryCode());
        param.put("industryName", tlPayProperties.getIndustryName());
        param.put("source", 1L);
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.depositApply", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * ?????????????????????
     * @return
     */
    public CommonResult<PayCashDetail> sellerPayCash(PayCashParam payCashParam){
        PayCashDetail payCashDetail = new PayCashDetail();
        //??????????????????????????????????????????
        if(payCashParam.getPayMatch() ==3){
            //??????????????????
           CommonResult commonResult =  memberService.getMemberInfo(payCashParam.getUserId().toString());
           if(commonResult.getCode() == 200 ){
               payCashDetail.setPayAgain(0);
               JSONObject data = JSON.parseObject(commonResult.getData().toString());
               if(data.getString("isSetPayPwd").equals("false")){
                   payCashDetail.setIfSetPwd(0);
                   UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(payCashParam.getUserId().toString());
                   SetPayPwdParam setPayPwdParam = new SetPayPwdParam();
                   setPayPwdParam.setUserId(payCashParam.getUserId().toString());
                   setPayPwdParam.setIdentityNo(userAuthentication.getCertificateNo());
                   setPayPwdParam.setName(userAuthentication.getName());
                   setPayPwdParam.setPhone(userAuthentication.getMobile());
                   CommonResult commonResult1 = memberService.setPayPwd(setPayPwdParam);
                   payCashDetail.setUrl(commonResult1.getData().toString());
                   Map<String,Object> orderId = new HashMap<>();
                   orderId.put("bizOrderNo","");
                   payCashDetail.setCommonResult(orderId);
                   return CommonResult.success(payCashDetail);
               };
               payCashDetail.setIfSetPwd(1);
           }

        }
        if(payCashParam.getThingId() != null){
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(payCashParam.getThingId());
            if(thingSellInfo != null){
                return CommonResult.failed(THING_SELL_ING);
            }
        }

        DateTime payDeadlineTime = DateUtil.offsetMinute(new Date(), 8);
        OrderInfo orderInfo = orderInfoMapper.selectBySeller(payCashParam.getThingId(),payCashParam.getUserId().toString());
        if(orderInfo != null){
            payCashDetail.setPayAgain(1);
            Map<String,Object> orderId = new HashMap<>();
            orderId.put("bizOrderNo",orderInfo.getOrderId());
            payCashDetail.setCommonResult(orderId);
            return CommonResult.success(payCashDetail);
        }
        payCashDetail.setPayAgain(0);
        CommonResult commonResult = null;
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        final Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 15);
//        final Date date = calendar.getTime();
        String orderExpireDatetime = sdf.format(payDeadlineTime); //??????????????????
        //????????????
        OrderInfo orderIn= new OrderInfo();
        orderIn.setOrderSn("XXX");
        orderIn.setOrderCategory(0);
        orderIn.setThingId(-2l);//thingId  -2l
        orderIn.setThingId(payCashParam.getThingId());
        orderIn.setThingName("?????????????????????");
        orderIn.setThingSellId(-2l);
        orderIn.setPayStatus(0);
        orderIn.setPrice(payCashParam.getAmount());
        orderIn.setOrderStatus(1);//?????????
        orderIn.setPayType(payCashParam.getPayMatch());
        orderIn.setSellerId(payCashParam.getUserId());
//        orderIn.setSellerId(999999999); //999999999  ????????????
        orderIn.setBuyerId(Integer.valueOf(-1));//?????????????????????   ??????id?????????id????????????
//        orderIn.setBuyerId(1111);
        orderIn.setCreateTime(System.currentTimeMillis());
        orderIn.setIsDel(false);
        orderIn.setBuyerDel(true);
        orderIn.setSellerDel(false);
        orderIn.setTradeType(2);     //??????????????????
        orderIn.setSonTradeType(71); //?????????????????????
        orderIn.setPayDeadline(payDeadline.getTime());
        orderInfoMapper.insert(orderIn);
//        orderPayCacheService.set(orderIn);
        List<RecieverListParam> recieverListParams = new ArrayList<>();
        RecieverListParam recieverListParam = new RecieverListParam();
        recieverListParam.setBizUserId(payCashParam.getUserId().toString()); //????????????????????????
        recieverListParam.setAmount(payCashParam.getAmount());
        recieverListParams.add(recieverListParam);
        //??????????????????
        AgentCollectApplyParam param = new AgentCollectApplyParam();
        param.setBizOrderNo(orderIn.getOrderId().toString());
        param.setAmount(payCashParam.getAmount());
        param.setBankCardNo(payCashParam.getBankCardNo());
        param.setFee(0L);
        param.setPayerId(payCashParam.getUserId().toString());
        param.setPayMethod(payCashParam.getPayMatch());
        param.setOrderExpireDatetime(orderExpireDatetime);
        if(payCashParam.getPayMatch() ==1){
            param.setTlWxPayParam(payCashParam.getTlWxPayParam());
        }
        param.setRecieverList(recieverListParams);
        param.setAmount(payCashParam.getAmount());
        param.setBizOrderNo(orderIn.getOrderId().toString());
        commonResult = agentCollectApply(param);
        if(commonResult.getCode() == 200){
            if(payCashParam.getPayMatch() == 3){
                PayByPwdParam payByPwdParam = new PayByPwdParam();
                payByPwdParam.setOrderId(orderIn.getOrderId().toString());
                payByPwdParam.setUserId(orderIn.getSellerId().toString());
//                String url = payByPwd(payByPwdParam).getData().toString();
                String url = payByPwd(payByPwdParam);
                payCashDetail.setUrl(url);
            }
            payCashDetail.setCommonResult(commonResult.getData());
            return CommonResult.success(payCashDetail);
        }else {
            return CommonResult.failed(commonResult.getMsg());
        }
//        return agentCollectApply(param);
    }

    /**
     * ?????????????????????
     * @return
     */
    public CommonResult<PayCashDetail> buyerPayCash(PayCashParam payCashParam){
        PayCashDetail payCashDetail = new PayCashDetail();
        //??????????????????????????????????????????
        if(payCashParam.getPayMatch() ==3){
            //??????????????????
            CommonResult commonResult =  memberService.getMemberInfo(payCashParam.getUserId().toString());
            if(commonResult.getCode() == 200 ){
                payCashDetail.setPayAgain(0);
                JSONObject data = JSON.parseObject(commonResult.getData().toString());
                if(data.getString("isSetPayPwd").equals("false")){
                    payCashDetail.setIfSetPwd(0);
                    UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(payCashParam.getUserId().toString());
                    SetPayPwdParam setPayPwdParam = new SetPayPwdParam();
                    setPayPwdParam.setUserId(payCashParam.getUserId().toString());
                    setPayPwdParam.setIdentityNo(userAuthentication.getCertificateNo());
                    setPayPwdParam.setName(userAuthentication.getName());
                    setPayPwdParam.setPhone(userAuthentication.getMobile());
                    CommonResult commonResult1 = memberService.setPayPwd(setPayPwdParam);
                    payCashDetail.setUrl(commonResult1.getData().toString());
                    Map<String,Object> orderId = new HashMap<>();
                    orderId.put("bizOrderNo","");
                    payCashDetail.setCommonResult(orderId);
                    return CommonResult.success(payCashDetail);
                };
                payCashDetail.setIfSetPwd(1);
            }

        }
        DateTime payDeadlineTime = DateUtil.offsetMinute(new Date(), 8);
        //????????????????????????????????????????????????
        OrderInfo orderInfo = orderInfoMapper.selectByBuyer(Long.valueOf(payCashParam.getThingSellId()),payCashParam.getUserId().toString());
        if(orderInfo != null){
            payCashDetail.setPayAgain(1);
            return CommonResult.success(payCashDetail);
        }
        payCashDetail.setPayAgain(0);
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        final Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE, 15);
//        final Date date = calendar.getTime();
        final String orderExpireDatetime = sdf.format(payDeadlineTime); //??????????????????
        CommonResult commonResult = null;
        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectById(payCashParam.getThingSellId());
        ThingInfo thingInfo = thingInfoMapper.selectById(payCashParam.getThingId());
        //????????????
        OrderInfo orderIn= new OrderInfo();
        orderIn.setOrderSn("XXX");
        orderIn.setOrderCategory(0);
        orderIn.setThingId(payCashParam.getThingId());
        orderIn.setThingName("?????????????????????");
        orderIn.setThingSellId(Long.valueOf(payCashParam.getThingSellId()));
        orderIn.setPrice(thingSellInfo.getMargin()); //??????????????????????????????
        orderIn.setPayType(payCashParam.getPayMatch());
        orderIn.setOrderStatus(1);//?????????
        orderIn.setPayStatus(0);
        orderIn.setSellerId(Integer.valueOf(-1)); //???????????????
        orderIn.setBuyerId(payCashParam.getUserId());
        orderIn.setCreateTime(System.currentTimeMillis());
        orderIn.setIsDel(false);
        orderIn.setBuyerDel(false);
        orderIn.setSellerDel(true);
        orderIn.setPayDeadline(payDeadline.getTime());
        orderIn.setTradeType(2);     //??????????????????
        orderIn.setSonTradeType(71); //????????????
        orderInfoMapper.insert(orderIn);
//        orderPayCacheService.set(orderIn);
        List<RecieverListParam> recieverListParams = new ArrayList<>();
        RecieverListParam recieverListParam = new RecieverListParam();
        recieverListParam.setBizUserId(payCashParam.getUserId().toString()); //????????????????????????
        recieverListParam.setAmount(payCashParam.getAmount());
        recieverListParams.add(recieverListParam);
        //??????????????????
        AgentCollectApplyParam param = new AgentCollectApplyParam();
        param.setBizOrderNo(orderIn.getOrderId().toString());
        param.setAmount(payCashParam.getAmount());
        param.setBankCardNo(payCashParam.getBankCardNo());
        param.setFee(0L);
        param.setPayerId(payCashParam.getUserId().toString());
        param.setPayMethod(payCashParam.getPayMatch());
        param.setOrderExpireDatetime(orderExpireDatetime);
        if(payCashParam.getPayMatch() ==1){
            param.setTlWxPayParam(payCashParam.getTlWxPayParam());
        }
        param.setRecieverList(recieverListParams);
        param.setAmount(payCashParam.getAmount());
        param.setBizOrderNo(orderIn.getOrderId().toString());
        commonResult = agentCollectApply(param);
        if(commonResult.getCode() == 200){
            if(payCashParam.getPayMatch() == 3){
                PayByPwdParam payByPwdParam = new PayByPwdParam();
                payByPwdParam.setOrderId(orderIn.getOrderId().toString());
                payByPwdParam.setUserId(payCashParam.getUserId().toString());
//                String url = payByPwd(payByPwdParam).getData().toString();
                String url = payByPwd(payByPwdParam);
                payCashDetail.setUrl(url);
            }
            payCashDetail.setCommonResult(commonResult.getData());
            return CommonResult.success(payCashDetail);
        }else {
            return CommonResult.failed(commonResult.getMsg());
        }
    }

    /**
     * ????????????????????????
     * @return
     */
    public CommonResult cashReturn(RefundParam param){
        CommonResult commonResult = null;
        //???????????????
//        RefundParam refundParam = new RefundParam();
        OrderInfo orderInfoOrg = orderInfoMapper.selectById(param.getOrg_order_id());
        OrderInfo orderIn= new OrderInfo();
        orderIn.setOrderSn("XXX");
        orderIn.setThingId(-2l);//thingId  -2l
        orderIn.setThingName("???????????????");
        orderIn.setThingSellId(-2l);
        orderIn.setPrice(orderInfoOrg.getPrice());
        orderIn.setOrderCategory(0);
        orderIn.setOrderStatus(1);//?????????
        orderIn.setSellerId(orderInfoOrg.getSellerId()); //999999999  ????????????
//        orderIn.setBuyerId(Integer.valueOf(payCashParam.getUser_id()));
        orderIn.setBuyerId(orderInfoOrg.getBuyerId());
        orderIn.setCreateTime(System.currentTimeMillis());
        orderIn.setIsDel(false);
        orderIn.setBuyerDel(false);
        orderIn.setSellerDel(false);
        orderIn.setTradeType(4);     //??????????????????
        orderIn.setSonTradeType(73); //????????????
        orderIn.setPayDeadline(payDeadline.getTime());
        orderInfoMapper.insert(orderIn);
//        OrderInfo orderInfo =orderInfoMapper.selectById(param.getOrg_order_id());
        commonResult = refund(param);
        if(commonResult.getCode() == 200){
            // ?????????????????????
            AccountCashDetail accountCashDetail = new AccountCashDetail();
            accountCashDetail.setCreateTime(System.currentTimeMillis());
            accountCashDetail.setIsDel(false);
            accountCashDetail.setPayee("??????");
            accountCashDetail.setTradeOrderNo(orderIn.getOrderId());//???????????????
            accountCashDetail.setTradeTime(new Date());
            accountCashDetail.setSellId(orderIn.getThingSellId().intValue());
            accountCashDetail.setTradeType(3); //?????????
            accountCashDetail.setTradeMoney(0 - orderIn.getPrice());//????????????
            AccountInfo accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(orderIn.getBuyerId()));
            accountInfo.setCashDeposit(accountInfo.getCashDeposit() - orderIn.getPrice());
            accountInfoMapper.updateSelective(accountInfo);
            accountCashDetail.setAccountId(accountInfo.getAccountId());
            accountCashDetail.setPreAccountBalance(accountInfo.getCashDeposit());//???????????????
            accountCashDetail.setCashAccountBalance(accountInfo.getCashDeposit() - orderIn.getPrice());//????????????????????????
            accountCashDetailMapper.insert(accountCashDetail);

            //????????????????????????
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderIn.getOrderId());
            orderInfoGet.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfoGet);
        }else{
            //????????????????????????
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderIn.getOrderId());
            orderInfoGet.setOrderStatus(9);
            orderInfoMapper.updateSelective(orderInfoGet);
        }
        return commonResult;
    }

    /**
     * ?????????????????????
     * @return
     */
    public CommonResult buyerCashDeduct(String orderId){//BuyerCashDeductParam
        CommonResult commonResult = null;
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        ThingInfo thingInfo = thingInfoMapper.selectById(orderInfo.getThingId());
//        OrderInfo orderInfo1 = orderInfoMapper.selectOrderByBuyer(orderInfo.getThingId(),orderInfo.getSellerId().toString());
        List<SplitRuleParam> splitRuleList =new ArrayList<>();
        SplitRuleParam splitRule = new SplitRuleParam();
        SplitRuleParam splitRule2 = new SplitRuleParam();
        List<CollectPayParam> collectPayList = new ArrayList<>();
        CollectPayParam collectPayParam = new CollectPayParam();
        collectPayParam.setAmount(orderInfo.getPrice());
        collectPayParam.setBizOrderNo(orderId);
        collectPayList.add(collectPayParam);
        Double i = Double.valueOf(tlPayProperties.getBuyerCashPlat().trim());
//                Integer.valueOf(tlPayProperties.getBuyerCashPlat());
        log.info("????????????????????????????????????"+ i);
        Long platCash =new Double(floor(orderInfo.getPrice() * i)).longValue();
        log.info("??????????????????{}",platCash);
        if(platCash > 0 ){
            Long sellerGetCash = orderInfo.getPrice() - platCash;
            splitRule.setRemark("??????????????????");
            splitRule.setAccountSetNo("100001");
            splitRule.setAmount(platCash);
            splitRule.setBizUserId("#yunBizUserId_B2C#");
            splitRule.setFee(0l);
            splitRule2.setRemark("??????????????????");
            splitRule2.setAmount(sellerGetCash);
            splitRule2.setBizUserId(thingInfo.getUserId().toString());
            splitRule2.setFee(0l);
            splitRuleList.add(splitRule);
            splitRuleList.add(splitRule2);
        }else{
            splitRule2.setRemark("??????????????????");
            splitRule2.setAmount(orderInfo.getPrice());
            splitRule2.setBizUserId(thingInfo.getUserId().toString());
            splitRule2.setFee(0l);
            splitRuleList.add(splitRule2);
        }
        //??????????????????????????????
        OrderInfo orderInfodf = new OrderInfo();
        orderInfodf.setOrderSn("XXX");
        orderInfodf.setOrderCategory(0);
        orderInfodf.setPayDeadline(payDeadline.getTime());
        orderInfodf.setPayType(5);//
        orderInfodf.setOrderStatus(1);
        orderInfodf.setTradeType(2);//??????
        orderInfodf.setSonTradeType(72);//???????????????
        orderInfodf.setBuyerId(orderInfo.getBuyerId());
        orderInfodf.setSellerId(orderInfo.getSellerId());
        orderInfodf.setPrice(orderInfo.getPrice());
        orderInfodf.setCreateTime(System.currentTimeMillis());
        orderInfodf.setThingSellId(orderInfo.getThingSellId());
        orderInfodf.setThingId(orderInfo.getThingId());
        orderInfodf.setThingName("?????????????????????");
        orderInfodf.setIsDel(false);
        orderInfodf.setSellerDel(false);
        orderInfodf.setBuyerDel(false);
        orderInfoMapper.insert(orderInfodf);
        SignalAgentPayParam signalAgentPayParam = new SignalAgentPayParam();
        signalAgentPayParam.setAmount(orderInfo.getPrice());
        signalAgentPayParam.setFee(0l);
        signalAgentPayParam.setCollectPayList(collectPayList);
        signalAgentPayParam.setSplitRuleList(splitRuleList);
        signalAgentPayParam.setOrderId(orderInfodf.getOrderId().toString());
        signalAgentPayParam.setUserId(orderInfo.getBuyerId().toString());
        signalAgentPayParam.setCallBackUrl("https://ajz.e2prove.com/tlCallBack/buyerAgentPayCallBack");
        commonResult = signalAgentPay(signalAgentPayParam);
        if(commonResult.getCode() == 200){
            return commonResult;
        }else{
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderInfodf.getOrderId());
            orderInfoGet.setOrderStatus(9);
            orderInfoMapper.updateSelective(orderInfoGet);
            return commonResult;
        }
    }

    /**
     * ?????????????????????
     * @return
     */
    public CommonResult sellerCashDeduct(String orderId){
        log.info("?????????????????????");
        CommonResult commonResult = null;
//        OrderInfo
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        OrderInfo orderInfo1 = orderInfoMapper.selectOrderBySeller(orderInfo.getThingId(),orderInfo.getSellerId().toString());
        List<SplitRuleParam> splitRuleList =new ArrayList<>();
        SplitRuleParam splitRule = new SplitRuleParam();
        SplitRuleParam splitRule2 = new SplitRuleParam();
        List<CollectPayParam> collectPayList = new ArrayList<>();
        CollectPayParam collectPayParam = new CollectPayParam();
        collectPayParam.setAmount(orderInfo.getPrice());
        collectPayParam.setBizOrderNo(orderId);
        collectPayList.add(collectPayParam);
//        Integer i = Integer.valueOf(tlPayProperties.getSellerCashPlat());
//        int i = Integer.parseInt(tlPayProperties.getSellerCashPlat().trim());
        Double i = Double.valueOf(tlPayProperties.getSellerCashPlat().trim());
        log.info("????????????????????????????????????"+ i);
        Long platCash =new Double(floor(orderInfo.getPrice() * i)).longValue();
        if(platCash > 0 ){
            Long sellerGetCash = orderInfo.getPrice() - platCash;
            splitRule.setRemark("??????????????????");
            splitRule.setAccountSetNo("100001");
            splitRule.setAmount(platCash);
            splitRule.setBizUserId("#yunBizUserId_B2C#");
            splitRule.setFee(0l);
            splitRule2.setRemark("??????????????????");
            splitRule2.setAmount(sellerGetCash);
            splitRule2.setBizUserId(orderInfo1.getBuyerId().toString());
            splitRule2.setFee(0l);
            splitRuleList.add(splitRule);
            splitRuleList.add(splitRule2);
        }else{
            splitRule2.setRemark("???????????????");
            splitRule2.setAmount(orderInfo.getPrice());
            splitRule2.setBizUserId(orderInfo1.getBuyerId().toString());
            splitRule2.setFee(0l);
            splitRuleList.add(splitRule2);
        }
        //??????????????????????????????
        OrderInfo orderInfodf = new OrderInfo();
        orderInfodf.setOrderSn("XXX");
        orderInfodf.setOrderCategory(0);
        orderInfodf.setPayDeadline(payDeadline.getTime());
        orderInfodf.setPayType(5);//wu
        orderInfodf.setOrderStatus(1);
        orderInfodf.setTradeType(2);//??????
        orderInfodf.setSonTradeType(72);//???????????????
        orderInfodf.setBuyerId(orderInfo.getBuyerId());
        orderInfodf.setSellerId(orderInfo.getSellerId());
        orderInfodf.setPrice(orderInfo.getPrice());
        orderInfodf.setCreateTime(System.currentTimeMillis());
        orderInfodf.setThingSellId(orderInfo.getThingSellId());
        orderInfodf.setThingId(orderInfo.getThingId());
        orderInfodf.setThingName("????????????????????????");
        orderInfodf.setIsDel(false);
        orderInfodf.setSellerDel(false);
        orderInfodf.setBuyerDel(false);
        orderInfoMapper.insert(orderInfodf);
        SignalAgentPayParam signalAgentPayParam = new SignalAgentPayParam();
        signalAgentPayParam.setAmount(orderInfo.getPrice());
        signalAgentPayParam.setFee(0l);
        signalAgentPayParam.setCollectPayList(collectPayList);
        signalAgentPayParam.setSplitRuleList(splitRuleList);
        signalAgentPayParam.setOrderId(orderInfodf.getOrderId().toString());
        signalAgentPayParam.setUserId(orderInfodf.getSellerId().toString());
        signalAgentPayParam.setCallBackUrl("https://ajz.e2prove.com/tlCallBack/sellerAgentPayCallBack");
        commonResult = signalAgentPay(signalAgentPayParam);
        if(commonResult.getCode() == 200){
            return commonResult;
        }else{
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderInfodf.getOrderId());
            orderInfoGet.setOrderStatus(9);
            orderInfoMapper.updateSelective(orderInfoGet);
            return commonResult;
        }

    }


    /**
     * ????????????
     * @return
     */
    public CommonResult withdrawApply(WithdrawApplyParam withdrawApplyParam){
        //???????????????????????????????????????
        CommonResult commonResult = null;
        Long fee = 0l;
//        fee????????????????????????????????????????????? ??????????????????????????????????????????1.5??????????????????????????????1.5????????????????????????????????????1.5???1.5???????????????????????????
        if(withdrawApplyParam.getAmount()>100000){
            fee = new Double(floor(withdrawApplyParam.getAmount() * 0.0015)).longValue();
        }else if(withdrawApplyParam.getAmount()>150){
            fee = 150l;
        }else{
            return CommonResult.failed(WITHDRAWALS_ARE_NOT_ALLOWED);
        }
        //??????????????????
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderSn("XXX");
        orderInfo.setThingId(-2l);//thingId  -2l
        orderInfo.setThingName("????????????");
        orderInfo.setThingSellId(-2l);
        orderInfo.setPrice(withdrawApplyParam.getAmount());
        orderInfo.setOrderStatus(1);//?????????
        orderInfo.setSellerId(999999999); //999999999  ????????????
//        orderIn.setSellerId(Integer.valueOf(payCashParam.getUser_id()));
        orderInfo.setBuyerId(Integer.valueOf(withdrawApplyParam.getUserId()));
//        orderInfo.setBuyerId(1111);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setIsDel(false);
        orderInfo.setBuyerDel(false);
        orderInfo.setPayType(-1);
        orderInfo.setSellerDel(false);
        orderInfo.setTradeType(3);     //????????????
        orderInfo.setSonTradeType(35); //???????????????
        orderInfoMapper.insert(orderInfo);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        final Date date = calendar.getTime();
        final String orderExpireDatetime = sdf.format(date);
        final BizParameter param = new BizParameter();
        param.put("bizUserId", withdrawApplyParam.getUserId());
        param.put("bizOrderNo", System.currentTimeMillis() + "tx");
        param.put("accountSetNo", tlPayProperties.getTgAccount());
        param.put("amount", withdrawApplyParam.getAmount());
        param.put("fee", fee);
        param.put("validateType", 0L);//?????????
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/withdrawApplyCallBack");
        param.put("orderExpireDatetime", orderExpireDatetime);
        param.put("bankCardNo", client.encrypt(withdrawApplyParam.getBankCardNo()));
        param.put("bankCardPro", 0L);//???????????????
        param.put("withdrawType", "D0");
        param.put("industryCode", tlPayProperties.getIndustryCode());
        param.put("industryName", tlPayProperties.getIndustryName());
        param.put("source", 1L);
//        param.put("summary", "withdraw");
//        param.put("extendInfo", "this is extendInfo");
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.withdrawApply", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data);
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * ?????????????????????????????????
     */
    public CommonResult agentCollectApply(AgentCollectApplyParam agentCollectApplyParam) {
        log.info("??????????????????" + agentCollectApplyParam.toString());
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        // ????????????
        final Map<String,Object> payMatch = new HashMap<>();
        JSONArray ii = new JSONArray();
        String payMethodVar = "";
        Long validateType = 1L;
        switch (agentCollectApplyParam.getPayMethod()){
            case 1: //????????????
                payMethodVar = "WECHATPAY_MINIPROGRAM_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("subAppid",agentCollectApplyParam.getTlWxPayParam().getSubAppid());
                payMatch.put("limitPay","");
                payMatch.put("amount",agentCollectApplyParam.getTlWxPayParam().getAmount());
                payMatch.put("acct",agentCollectApplyParam.getTlWxPayParam().getAcct());
                validateType = 0L;
                break;
            case 2: //???????????????
                payMethodVar = "SCAN_ALIPAY_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("amount",agentCollectApplyParam.getAmount());
                payMatch.put("limitPay","");
                validateType = 0L;
                break;
            case 3:
                payMethodVar = "BALANCE";
                payMatch.put("accountSetNo",tlPayProperties.getTgAccount());
                payMatch.put("amount",agentCollectApplyParam.getAmount());
                ii.add(payMatch);
                validateType = 2L;
                break;
            case 4:
                payMethodVar = "QUICKPAY_VSP";
                payMatch.put("bankCardNo", client.encrypt(agentCollectApplyParam.getBankCardNo()));
                payMatch.put("amount", agentCollectApplyParam.getAmount());
                validateType = 1L;
                break;
        }
        // ??????????????????
        final HashMap<String, Object> payMethod = new HashMap<>();
        if(agentCollectApplyParam.getPayMethod() == 3){
            payMethod.put(payMethodVar, ii);
        }else{
            payMethod.put(payMethodVar, payMatch);
        }
        // ????????????
        final JSONArray receiverList = new JSONArray();
        final HashMap<String, Object> receiver1 = new HashMap<>();
        int i = agentCollectApplyParam.getRecieverList().size();
        for(int j=0;j<i;j++){
            receiver1.put("bizUserId", agentCollectApplyParam.getRecieverList().get(j).getBizUserId());  //??????
            receiver1.put("amount", agentCollectApplyParam.getRecieverList().get(j).getAmount());
            receiverList.add(new JSONObject(receiver1));
        }
        param.put("payerId", agentCollectApplyParam.getPayerId());
        param.put("bizOrderNo", agentCollectApplyParam.getBizOrderNo());
        param.put("recieverList", receiverList);
        param.put("tradeCode", "3001");
        param.put("amount", agentCollectApplyParam.getAmount());
        param.put("fee", agentCollectApplyParam.getFee());
        param.put("validateType", validateType);
//        param.put("frontUrl", "http://ceshi.allinpay.com");
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/agentCollectApplyCallBack");
        param.put("orderExpireDatetime", agentCollectApplyParam.getOrderExpireDatetime());
        param.put("payMethod", payMethod);
        param.put("goodsName", "computer");
        param.put("goodsDesc", "computer made in china");
        param.put("industryCode", tlPayProperties.getIndustryCode());
        param.put("industryName", tlPayProperties.getIndustryName());
        param.put("source", 1L);
//        param.put("summary", "consume");
//        param.put("extendInfo", "this is extendInfo");
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.agentCollectApply", param);
            if ("OK".equals(response.getSubCode())) {
                log.info("-----response.getData()" + response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                log.info("-----data" + JSON.toJSONString(data));
                OrderInfo orderInfo = new OrderInfo();
                if(agentCollectApplyParam.getBizOrderNo().length() > 15){
                    orderInfo = orderInfoMapper.selectByPayOrderId(agentCollectApplyParam.getBizOrderNo());
                }else {
                    orderInfo = orderInfoMapper.selectById(agentCollectApplyParam.getBizOrderNo());
                }
                orderInfo.setTlOrderSn(data.getString("orderNo"));
                orderInfoMapper.updateSelective(orderInfo);
                commonResult = CommonResult.success(data);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }

    /**
     * ????????????--??????????????????
     */
    public CommonResult orderPay(AgentCollectApplyParam agentCollectApplyParam) {
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        // ????????????
        final Map<String,Object> payMatch = new HashMap<>();
        JSONArray ii = new JSONArray();
        String payMethodVar = "";
        Long validateType = 1l;
        switch (agentCollectApplyParam.getPayMethod()){
            case 1: //????????????
                payMethodVar = "WECHATPAY_MINIPROGRAM_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("subAppid",agentCollectApplyParam.getTlWxPayParam().getSubAppid());
                payMatch.put("limitPay","");
                payMatch.put("amount",agentCollectApplyParam.getTlWxPayParam().getAmount());
                payMatch.put("acct",agentCollectApplyParam.getTlWxPayParam().getAcct());
                validateType = 0L;
                break;
            case 2: //???????????????
                payMethodVar = "SCAN_ALIPAY_ORG";
                payMatch.put("vspCusid",tlPayProperties.getVspCusid());
                payMatch.put("amount",agentCollectApplyParam.getAmount());
                payMatch.put("limitPay","");
                validateType = 0L;
                break;
            case 3:
                payMethodVar = "BALANCE";
                payMatch.put("accountSetNo",tlPayProperties.getTgAccount());
                payMatch.put("amount",agentCollectApplyParam.getAmount());
                ii.add(payMatch);
                validateType = 2L;
                break;
            case 4:
                payMethodVar = "QUICKPAY_VSP";
                payMatch.put("bankCardNo", client.encrypt(agentCollectApplyParam.getBankCardNo()));
                payMatch.put("amount", agentCollectApplyParam.getAmount());
                validateType = 1L;
                break;
        }
        // ??????????????????
        final HashMap<String, Object> payMethod = new HashMap<>();
        if(agentCollectApplyParam.getPayMethod() == 3){
            payMethod.put(payMethodVar, ii);
        }else{
            payMethod.put(payMethodVar, payMatch);
        }
        param.put("payerId", agentCollectApplyParam.getPayerId());
        if(agentCollectApplyParam.getOrderIdAgain() != null){
            param.put("bizOrderNo", agentCollectApplyParam.getOrderIdAgain());
        }else{
            param.put("bizOrderNo", agentCollectApplyParam.getBizOrderNo());
        }
        param.put("recieverList", agentCollectApplyParam.getRecieverList());
        param.put("tradeCode", "3001");
        param.put("amount", agentCollectApplyParam.getAmount());
        param.put("fee", agentCollectApplyParam.getFee());
        param.put("validateType", validateType);
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/orderPayCallBack");
        param.put("orderExpireDatetime", agentCollectApplyParam.getOrderExpireDatetime());
        param.put("payMethod", payMethod);
//        param.put("goodsName", "computer");
//        param.put("goodsDesc", "computer made in china");
        param.put("industryCode", tlPayProperties.getIndustryCode());
        param.put("industryName", tlPayProperties.getIndustryName());
        param.put("source", 1L);
//        param.put("summary", "consume");
//        param.put("extendInfo", "this is extendInfo");
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.agentCollectApply", param);
            if ("OK".equals(response.getSubCode())) {
                log.info("-----response.getData()" + response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                log.info("-----data" + JSON.toJSONString(data));
                OrderInfo orderInfo = orderInfoMapper.selectById(agentCollectApplyParam.getBizOrderNo());
                orderInfo.setTlOrderSn(data.getString("orderNo"));
                orderInfoMapper.updateSelective(orderInfo);
                commonResult = CommonResult.success(data);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.failed(e.getMessage());
        }
        return commonResult;
    }



    /**
     * ?????????????????????????????????
     */
    public CommonResult signalAgentPay(SignalAgentPayParam signalAgentPayParam) {
        log.info("????????????????????????");
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        // ?????????????????????????????????
//        final JSONArray collectPayList = new JSONArray();
//        final HashMap<String, Object> collect1 = new HashMap<>();
//        collect1.put("bizOrderNo", "1578901808944ds");
//        collect1.put("amount", 30L);
//        collectPayList.add(new JSONObject(collect1));
        param.put("bizUserId", signalAgentPayParam.getUserId());
        param.put("accountSetNo", tlPayProperties.getTgAccount());
        param.put("bizOrderNo", signalAgentPayParam.getOrderId());
//        param.put("collectPayList", collectPayList);
        param.put("splitRuleList",signalAgentPayParam.getSplitRuleList());
        param.put("collectPayList", signalAgentPayParam.getCollectPayList());
        param.put("tradeCode", "4001");
        param.put("amount", signalAgentPayParam.getAmount());
        param.put("fee", signalAgentPayParam.getFee());
        param.put("backUrl", signalAgentPayParam.getCallBackUrl());
        param.put("summary", "consume");
        param.put("extendInfo", "this is extendInfo");
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.signalAgentPay", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data);
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


    /**
     * ?????????????????????+ ?????????
     * @param payByBackSMSParam
     * @return
     */
    public CommonResult payByBackSMS(PayByBackSMSParam payByBackSMSParam){
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        param.put("bizUserId", payByBackSMSParam.getUserId().toString());
        param.put("bizOrderNo", payByBackSMSParam.getBizOrderNo());
//		param.put("tradeNo", "");
        param.put("verificationCode", payByBackSMSParam.getVerificationCode());
        param.put("consumerIp", tlPayProperties.getConsumerIp());
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.payByBackSMS", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                if(data.getString("payStatus").equals("success")){
                    commonResult = CommonResult.success(data);
                }else {
                    commonResult = CommonResult.failed(data.getString("payFailMessage"));
                }
            }else {
                commonResult = CommonResult.failed();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * ??????
     */
    public CommonResult refund(RefundParam refundParam) {
        //update
        OrderInfo orderInfo = new OrderInfo();
        if(refundParam.getOrg_order_id().length()>15){
            orderInfo = orderInfoMapper.selectByPayOrderId(refundParam.getOrg_order_id());
        }else {
            orderInfo = orderInfoMapper.selectById(refundParam.getOrg_order_id());
        }
        QueryVSPFundParam queryVSPFundParam = new QueryVSPFundParam();
        if(orderInfo.getPayType() != 3){
        if(orderInfo.getPayType() <3){
            queryVSPFundParam.setVspOrgid(tlPayProperties.getVspOrgid());
            queryVSPFundParam.setVspCusid(tlPayProperties.getVspCusid());
        }else if(orderInfo.getPayType() >3){
            queryVSPFundParam.setVspCusid(tlPayProperties.getVspCusidKj());
        }
        Integer todayTk = Integer.valueOf(queryVSPFund(queryVSPFundParam).getData().toString());
        if(orderInfo.getPrice() > todayTk){
            return CommonResult.failed(INSUFFICIENT_REFUND_FLOW);
        }
        }
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo1.setTradeType(4);
        orderInfo1.setThingId(orderInfo.getThingId());
        orderInfo1.setThingName(orderInfo.getThingName() + "??????");
        orderInfo1.setOrderSn(orderInfo.getOrderSn());
        orderInfo1.setThingSellId(orderInfo.getThingSellId());
        orderInfo1.setSellerDel(false);
        orderInfo1.setBuyerDel(false);
        orderInfo1.setIsDel(false);
        orderInfo1.setCreateTime(System.currentTimeMillis());
        orderInfo1.setSellerId(999999999); //999999999  ??????
//        orderIn.setBuyerId(Integer.valueOf(payCashParam.getUser_id()));
        orderInfo1.setBuyerId(Integer.valueOf(refundParam.getUser_id()));
        orderInfo1.setPrice(refundParam.getAmont());
        orderInfo1.setOrderStatus(1);
        orderInfo1.setTradeType(4);
        orderInfo1.setSonTradeType(11);
        orderInfoMapper.insert(orderInfo1);
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        param.put("bizUserId", refundParam.getUser_id());
        param.put("bizOrderNo", orderInfo1.getOrderId());
        param.put("oriBizOrderNo", refundParam.getOrg_order_id());
        param.put("amount", refundParam.getAmont());
        param.put("refundType", "D0");
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.refund", param);
            if ("OK".equals(response.getSubCode())) {
                OrderInfo orderInfo2 = orderInfoMapper.selectById(orderInfo1.getOrderId());
                orderInfo2.setOrderStatus(8);
                //????????????????????????????????????
                RefundInfo refundInfo = refundInfoMapper.selectByOrderId(refundParam.getOrg_order_id());
                if(refundInfo !=null){
                    refundInfo.setOrderStatus(1);
                    refundInfoMapper.updateSelective(refundInfo);
                }
                //???????????????
                // ?????????????????????
                AccountCashDetail accountCashDetail = new AccountCashDetail();
                accountCashDetail.setCreateTime(System.currentTimeMillis());
                accountCashDetail.setIsDel(false);
                accountCashDetail.setPayee("??????");
                accountCashDetail.setTradeOrderNo(orderInfo1.getOrderId());//?????????????????????
                accountCashDetail.setTradeTime(new Date());
                accountCashDetail.setSellId(orderInfo1.getThingSellId().intValue());
                accountCashDetail.setTradeType(3); //?????????
                accountCashDetail.setTradeMoney(0 - orderInfo1.getPrice());//????????????
                AccountInfo accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(orderInfo1.getBuyerId()));
                if(accountInfo != null){
                    accountInfo.setCashDeposit(accountInfo.getCashDeposit() - orderInfo1.getPrice());
                    accountInfoMapper.updateSelective(accountInfo);
                }
                accountCashDetail.setAccountId(accountInfo.getAccountId());
                accountCashDetail.setPreAccountBalance(accountInfo.getCashDeposit());//???????????????
                accountCashDetail.setCashAccountBalance(accountInfo.getCashDeposit() - orderInfo1.getPrice());//????????????????????????
                accountCashDetailMapper.insert(accountCashDetail);
                orderInfoMapper.updateSelective(orderInfo2);
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data);
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * ??????????????????
     */
    public CommonResult getOrderDetail(String oderId) {
        OrderInfo orderInfo = new OrderInfo();
        if(oderId.length()> 15){
            orderInfo = orderInfoMapper.selectByPayOrderId(oderId);
        }else{
            orderInfo = orderInfoMapper.selectById(oderId);
        }
        String payOrderId = "";
        if(orderInfo.getTlOrderRequest() == null){
            payOrderId = oderId;
        }else{
            payOrderId = orderInfo.getTlOrderRequest();
        }
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        param.put("bizOrderNo", payOrderId);
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.getOrderDetail", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                 commonResult = CommonResult.success(data.getString("orderStatus"));
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


    /**
     * ????????????????????????
     */
    public CommonResult queryInExpDetail(QueryInExpDetailParam queryInExpDetailParam) {
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        param.put("bizUserId", queryInExpDetailParam.getUserId());
        param.put("accountSetNo", tlPayProperties.getTgAccount());
        param.put("dateStart", queryInExpDetailParam.getDateStart());
        param.put("dateEnd", queryInExpDetailParam.getDateEnd());
        param.put("startPosition", queryInExpDetailParam.getStartPosition());
        param.put("queryNum", queryInExpDetailParam.getQueryNum());
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.queryInExpDetail", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data);
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * ????????????
     */
    public CommonResult queryBalance(String userId) {
        final BizParameter param = new BizParameter();
        param.put("bizUserId", userId);
        param.put("accountSetNo", tlPayProperties.getTgAccount());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.queryBalance", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject data = JSON.parseObject(response.getData());
                System.out.println(response.getData());
                commonResult = CommonResult.success(data.getString("allAmount"));
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }



//    /**
//     * ?????????????????????+??????????????????
//     */
//    public void payBySMS(PayBySMSParam payBySMSParam) {
//        CommonResult commonResult = null;
//        final BizParameter param = new BizParameter();
//        param.put("bizUserId", payBySMSParam.getUser_id());
//        param.put("bizOrderNo",payBySMSParam.getBizOrderNo() );
//        param.put("verificationCode", payBySMSParam.getVerificationCode());
//        param.put("consumerIp", tlPayProperties.getConsumerIp());
//        try {
//            final String url = client.concatUrlParams("allinpay.yunst.orderService.payBySMS", param);
//            System.out.println(url);
//            browser(url);// ???????????????
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//    }
//
    /**
     * ?????????????????????+???????????????
     */
//    public CommonResult payByPwd(PayByPwdParam payByPwdParam) {
    public String payByPwd(PayByPwdParam payByPwdParam) {
        final BizParameter param = new BizParameter();
        param.put("bizUserId", payByPwdParam.getUserId());
        param.put("bizOrderNo", payByPwdParam.getOrderId());
//		param.put("tradeNo", "");
        param.put("jumpUrl", "https://webview.close");
        param.put("consumerIp", tlPayProperties.getConsumerIp());
//        CommonResult commonResult = null;
        String commonResult ="";
        try {
            final String url = client.concatUrlParams("allinpay.yunst.orderService.payByPwd", param);
//            Map<String,Object> returnUrl = new HashMap<>();
//            returnUrl.put("url",url);
            commonResult = url;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


    /**
     * ??????????????????
     */
     public CommonResult queryVSPFund(QueryVSPFundParam queryVSPFundParam){
         final BizParameter param = new BizParameter();
         param.put("vspOrgid", queryVSPFundParam.getVspOrgid());
         param.put("vspCusid", queryVSPFundParam.getVspCusid());
         CommonResult commonResult = null;
         try {
             final OpenResponse response = client.execute("allinpay.yunst.merchantService.queryVSPFund", param);
             if ("OK".equals(response.getSubCode())) {
                 JSONObject data = JSON.parseObject(response.getData());
                 System.out.println(response.getData());
                 commonResult = CommonResult.success(data.getString("balance"));
             }else{
                 commonResult = CommonResult.failed(response.getSubMsg());
             }
         } catch (final Exception e) {
             e.printStackTrace();
         }
         return commonResult;
     }

    /**
     * ????????????
     *
     */
    public CommonResult applicationTransfer(ApplicationTransferParam applicationTransferParam) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderSn("XXX");
        orderInfo.setThingId(-2l);//thingId  -2l
        orderInfo.setThingName("????????????");
        orderInfo.setThingSellId(-2l);
        orderInfo.setPrice(applicationTransferParam.getAmount());
        orderInfo.setOrderStatus(1);//?????????
        orderInfo.setSellerId(999999999); //999999999  ??????
//        orderIn.setSellerId(Integer.valueOf(payCashParam.getUser_id()));
//        orderIn.setBuyerId(Integer.valueOf(payCashParam.getUser_id()));
        orderInfo.setBuyerId(Integer.valueOf(applicationTransferParam.getUserId()));
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setIsDel(false);
        orderInfo.setBuyerDel(false);
        orderInfo.setPayType(-2);//????????????
        orderInfo.setSellerDel(false);
        orderInfo.setTradeType(2);     //????????????
        orderInfo.setSonTradeType(6); //????????????
        orderInfoMapper.insert(orderInfo);
        CommonResult commonResult = null;
        final BizParameter param = new BizParameter();
        param.put("sourceAccountSetNo", "100001"); //
        param.put("bizTransferNo", orderInfo.getOrderId());
        param.put("targetBizUserId", applicationTransferParam.getUserId());
        param.put("targetAccountSetNo", "401108");
        param.put("amount", applicationTransferParam.getAmount());
        try {
            final OpenResponse response = client.execute("allinpay.yunst.orderService.applicationTransfer", param);
            OrderInfo orderInfo1 = orderInfoMapper.selectById(orderInfo.getOrderId());
            if ("OK".equals(response.getSubCode())) {
                orderInfo1.setOrderStatus(8);
                orderInfoMapper.updateSelective(orderInfo1);
                System.out.println(response.getData());
                JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data);
            }else {
                orderInfo1.setOrderStatus(9);
                orderInfoMapper.updateSelective(orderInfo1);
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return commonResult;
    }


}
