package com.armsmart.jupiter.bs.api.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.cache.OrderPayCacheService;
import com.armsmart.jupiter.bs.api.cache.OrderSendCacheService;
import com.armsmart.jupiter.bs.api.dao.*;
import com.armsmart.jupiter.bs.api.dto.request.CollectPayParam;
import com.armsmart.jupiter.bs.api.dto.request.RefundParam;
import com.armsmart.jupiter.bs.api.dto.request.SignalAgentPayParam;
import com.armsmart.jupiter.bs.api.dto.request.TlCallBackParam;
import com.armsmart.jupiter.bs.api.entity.*;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TlCallBackService {

//    @Autowired(required = false)
//    private UserBankInfoMapper userBankInfoMapper;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;

    @Autowired(required = false)
    private AccountInfoMapper accountInfoMapper;

    @Autowired(required = false)
    private AccountCashDetailMapper accountCashDetailMapper;

    @Autowired(required = false)
    private TlOrderService tlOrderService;

    @Autowired
    private OrderPayCacheService orderPayCacheService;

    @Autowired(required = false)
    private OrderSendCacheService orderSendCacheService;

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    @Autowired(required = false)
    private RefundInfoMapper refundInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    /**
     * 电子协议回调函数
     * @param tlCallBackParam
     * @return
     */
    public CommonResult signContractCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        if(((JSONObject) ii).getString("status").equals("OK")){
            UserInfo userInfo = userInfoMapper.selectById(((JSONObject) ii).getString("bizUserId"));
            userInfo.setContractNo(((JSONObject) ii).getString("contractNo"));
            userInfoMapper.updateSelective(userInfo);
        }
        return CommonResult.success();
    }

    /**
     *设置支付密码回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult setPayPwdCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        if(((JSONObject) ii).getString("status").equals("OK")){
            UserInfo userInfo = userInfoMapper.selectById(((JSONObject) ii).getString("bizUserId"));
            userInfo.setIsSetPwd(true);
            userInfoMapper.updateSelective(userInfo);
        }
        return CommonResult.success();
    }

    /**
     *托管代收回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult agentCollectApplyCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        String orderId = ((JSONObject) ii).getString("bizOrderNo");
        String Status = ((JSONObject) ii).getString("status");
        String userId = ((JSONObject) ii).getString("buyerBizUserId");
        OrderInfo orderInfo = new OrderInfo();
        if(orderId.length()>15){
            orderInfo = orderInfoMapper.selectByPayOrderId(orderId);
        }else {
            orderInfo = orderInfoMapper.selectById(orderId);
        }
        if( Status.equals("OK")){
            orderInfo.setPayStatus(1);
            if(orderInfo.getSonTradeType() != null && orderInfo.getSonTradeType() == 77){
                orderPayCacheService.delete(Long.valueOf(orderInfo.getOrderId())); //支付货款交易
                orderInfo.setPayTime(System.currentTimeMillis());
                orderInfo.setPayStatus(1);
                ThingInfo thingInfo = thingInfoMapper.selectById(orderInfo.getThingId());
                if (thingInfo != null && thingInfo.getIsOfficial()) {
                    log.info("-------当前下订单上链工具物品-------");
                    orderInfo.setOrderStatus(8);//交易完成
                    //直接托管代付
                    OrderInfo orderInfodf = new OrderInfo();
                    DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
                    orderInfodf.setPayDeadline(payDeadline.getTime());
                    orderInfodf.setPayType(0);
                    orderInfodf.setOrderSn("XXX");
                    orderInfodf.setOrderStatus(1);
                    orderInfodf.setOrderCategory(0);
                    orderInfodf.setTradeType(2);//转账
                    orderInfodf.setSonTradeType(3);//支付代付
                    orderInfodf.setSellerId(orderInfo.getSellerId());
                    orderInfodf.setBuyerId(orderInfo.getBuyerId());
                    orderInfodf.setPrice(orderInfo.getPrice());
                    orderInfodf.setCreateTime(System.currentTimeMillis());
                    orderInfodf.setThingSellId(orderInfo.getThingSellId());
                    orderInfodf.setThingId(orderInfo.getThingId());
                    orderInfodf.setThingName("上链工具款付给卖家");
                    orderInfodf.setIsDel(false);
                    orderInfodf.setSellerDel(false);
                    orderInfodf.setBuyerDel(false);
                    log.info("货款支付参数" + orderInfodf.toString());
                    orderInfoMapper.insert(orderInfodf);
                    SignalAgentPayParam signalAgentPayParam = new SignalAgentPayParam();
                    signalAgentPayParam.setUserId(orderInfo.getSellerId().toString());
                    signalAgentPayParam.setCallBackUrl("https://ajz.e2prove.com/tlCallBack/orderInfoPayCallBack");
                    signalAgentPayParam.setOrderId(orderInfodf.getOrderId().toString());
                    List<CollectPayParam> collectPayList = new ArrayList<>();
                    CollectPayParam collectPayParam = new CollectPayParam();
                    collectPayParam.setBizOrderNo(orderId);
                    collectPayParam.setAmount(orderInfo.getPrice());
                    collectPayList.add(collectPayParam);
                    signalAgentPayParam.setCollectPayList(collectPayList);
                    signalAgentPayParam.setFee(0l);
                    signalAgentPayParam.setAmount(orderInfo.getPrice());
                    log.info("代付参数" + signalAgentPayParam.toString());
                    tlOrderService.signalAgentPay(signalAgentPayParam);
                }else {
                    orderInfo.setOrderStatus(2);//支付完成
                    DateTime sendOutDeadline = DateUtil.offsetDay(new Date(), 2);
//                    DateTime sendOutDeadline = DateUtil.offsetMinute(new Date(), 10);
                    orderInfo.setSendOutDeadline(sendOutDeadline.getTime());
                    orderSendCacheService.set(orderInfo);
                }
                //买家家保证金退还
                OrderInfo orderInfoBuyer = orderInfoMapper.selectByBuyer(orderInfo.getThingSellId(),orderInfo.getBuyerId().toString());
                if(orderInfoBuyer !=null){
                    RefundInfo refundInfo = new RefundInfo();
                    refundInfo.setAmount(orderInfoBuyer.getPrice());
                    refundInfo.setCreateTime(System.currentTimeMillis());
                    refundInfo.setIsDel(false);
                    refundInfo.setPayMatch(orderInfoBuyer.getPayType());
                    refundInfo.setOrderId(orderInfoBuyer.getOrderId().toString());
                    refundInfo.setOrderStatus(0);
                    refundInfo.setUserId(orderInfoBuyer.getBuyerId());
                    refundInfoMapper.insert(refundInfo);
                }
            }else if(orderInfo.getSonTradeType() == 71){ //保证金交易
                orderInfo.setOrderStatus(8);//交易完成
                log.info("进到了这里了。。。。。");
                //支付完成，进用户保证金账户信息里
                AccountCashDetail accountCashDetail = new AccountCashDetail();
                accountCashDetail.setCreateTime(System.currentTimeMillis());
                accountCashDetail.setIsDel(false);
                accountCashDetail.setPayee("平台");
                accountCashDetail.setTradeOrderNo(orderInfo.getOrderId());//交易订单号
                accountCashDetail.setTradeTime(new Date());
                accountCashDetail.setSellId(orderInfo.getThingSellId().intValue());
                accountCashDetail.setTradeType(1);
                accountCashDetail.setTradeMoney(orderInfo.getPrice());//交易金额
//                AccountInfo accountInfo = new AccountInfo();
//                if(orderInfo.getThingName().equals("买家支付保证金")){
//                    accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(orderInfo.getBuyerId()));
//                }else if(orderInfo.getThingName().equals("卖家支付保证金")){
//                    accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(orderInfo.getSellerId()));
//                }
                AccountInfo accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(userId));
                if(accountInfo == null){
                    AccountInfo accountInfo1 = new AccountInfo();
                    accountInfo1.setBalance(Long.valueOf(0));
                    accountInfo1.setCashDeposit(orderInfo.getPrice());
                    accountInfo1.setIsDel(false);
                    accountInfo1.setCreateTime(System.currentTimeMillis());
                    accountInfo1.setUserId(Integer.valueOf(orderInfo.getBuyerId()));
                    accountInfoMapper.insert(accountInfo1);
                    accountCashDetail.setAccountId(accountInfo1.getAccountId());
                    accountCashDetail.setPreAccountBalance(Long.valueOf(0));//交易前金额
                    accountCashDetail.setCashAccountBalance(orderInfo.getPrice());//交易后保证金金额
                    accountCashDetailMapper.insert(accountCashDetail);
                }else {
                    accountInfo.setCashDeposit(accountInfo.getCashDeposit() + orderInfo.getPrice());
                    log.info("账户操作参数："+ accountInfo.toString());
                    accountInfoMapper.updateSelective(accountInfo);
                    accountCashDetail.setAccountId(accountInfo.getAccountId());
                    accountCashDetail.setPreAccountBalance(accountInfo.getCashDeposit());//交易前金额
                    accountCashDetail.setCashAccountBalance(accountInfo.getCashDeposit() + orderInfo.getPrice());//交易后保证金金额
                    accountCashDetailMapper.insert(accountCashDetail);
                }
            }
            orderInfoMapper.updateSelective(orderInfo);

        }
        return CommonResult.success();
    }


    /**
     *订单支付回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult orderPayCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        String orderId = ((JSONObject) ii).getString("bizOrderNo");
        String Status = ((JSONObject) ii).getString("status");
        OrderInfo orderInfo = new OrderInfo();
        if(orderId.length()>15){
             orderInfo = orderInfoMapper.selectByPayOrderId(orderId); //支付订单
        }else{
             orderInfo = orderInfoMapper.selectById(orderId); //支付订单
        }
//        OrderInfo orderInfo = orderInfoMapper.selectById(orderId); //支付订单

        orderPayCacheService.delete(Long.valueOf(orderInfo.getOrderId()));
        if(Status.equals("OK")){
            orderPayCacheService.delete(Long.valueOf(orderInfo.getOrderId())); //支付货款交易
            orderInfo.setPayTime(System.currentTimeMillis());
            orderInfo.setPayStatus(1);
            ThingInfo thingInfo = thingInfoMapper.selectById(orderInfo.getThingId());
            if (thingInfo != null && thingInfo.getIsOfficial()) {
                log.info("-------当前下订单上链工具物品-------");
                orderInfo.setOrderStatus(8);//交易完成
                //直接托管代付
                OrderInfo orderInfodf = new OrderInfo();
                DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
                orderInfodf.setPayDeadline(payDeadline.getTime());
                orderInfodf.setPayType(0);
                orderInfodf.setOrderSn("XXX");
                orderInfodf.setOrderStatus(1);
                orderInfodf.setOrderCategory(0);
                orderInfodf.setTradeType(2);//转账
                orderInfodf.setSonTradeType(3);//支付代付
                orderInfodf.setSellerId(orderInfo.getSellerId());
                orderInfodf.setBuyerId(orderInfo.getBuyerId());
                orderInfodf.setPrice(orderInfo.getPrice());
                orderInfodf.setCreateTime(System.currentTimeMillis());
                orderInfodf.setThingSellId(orderInfo.getThingSellId());
                orderInfodf.setThingId(orderInfo.getThingId());
                orderInfodf.setThingName("上链工具款付给卖家");
                orderInfodf.setIsDel(false);
                orderInfodf.setSellerDel(false);
                orderInfodf.setBuyerDel(false);
                log.info("货款支付参数" + orderInfodf.toString());
                orderInfoMapper.insert(orderInfodf);
                SignalAgentPayParam signalAgentPayParam = new SignalAgentPayParam();
                signalAgentPayParam.setUserId(orderInfo.getSellerId().toString());
                signalAgentPayParam.setCallBackUrl("https://ajz.e2prove.com/tlCallBack/orderInfoPayCallBack");
                signalAgentPayParam.setOrderId(orderInfodf.getOrderId().toString());
                List<CollectPayParam> collectPayList = new ArrayList<>();
                CollectPayParam collectPayParam = new CollectPayParam();
                collectPayParam.setBizOrderNo(orderId);
                collectPayParam.setAmount(orderInfo.getPrice());
                collectPayList.add(collectPayParam);
                signalAgentPayParam.setCollectPayList(collectPayList);
                signalAgentPayParam.setFee(0l);
                signalAgentPayParam.setAmount(orderInfo.getPrice());
                log.info("代付参数" + signalAgentPayParam.toString());
                tlOrderService.signalAgentPay(signalAgentPayParam);
            }else {
                orderInfo.setOrderStatus(2);//支付完成
                DateTime sendOutDeadline = DateUtil.offsetDay(new Date(), 2);
                orderInfo.setSendOutDeadline(sendOutDeadline.getTime());
                orderSendCacheService.set(orderInfo);
            }
//            orderInfo.setOrderStatus(2);//订单改为已支付代发货
//            orderInfo.setPayStatus(1);//已支付
//            orderInfo.setPayTime(System.currentTimeMillis());
//            DateTime sendOutDeadline = DateUtil.offsetHour(new Date(), 48);
//            orderInfo.setSendOutDeadline(sendOutDeadline.getTime());
//            orderInfo.setPayTime(System.currentTimeMillis());
            orderInfoMapper.updateSelective(orderInfo);
//            orderSendCacheService.set(orderInfo);
//            orderPayCacheService.delete(Long.valueOf(orderId));
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectById(orderInfo.getThingSellId());
            if(thingSellInfo != null && thingSellInfo.getSellType() == 2){
            //支付完成，买家保证金退还
            OrderInfo orderInfoBuyer = orderInfoMapper.selectByBuyer(orderInfo.getThingSellId(),orderInfo.getBuyerId().toString());
            RefundInfo refundInfo = new RefundInfo();
            refundInfo.setAmount(orderInfoBuyer.getPrice());
            refundInfo.setCreateTime(System.currentTimeMillis());
            refundInfo.setIsDel(false);
            refundInfo.setPayMatch(orderInfoBuyer.getPayType());
            refundInfo.setOrderId(orderInfoBuyer.getOrderId().toString());
            refundInfo.setOrderStatus(0);
            refundInfo.setUserId(orderInfoBuyer.getBuyerId());
            refundInfoMapper.insert(refundInfo);
//            RefundParam refundParam = new RefundParam();
//            refundParam.setAmont(orderInfoBuyer.getPrice());
//            refundParam.setOrg_order_id(orderInfoBuyer.getOrderId().toString());
//            refundParam.setUser_id(orderInfo.getBuyerId().toString());
//            tlOrderService.cashReturn(refundParam);
            }
        }
        return CommonResult.success();
    }

    /**
     * 货款订单支付回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult orderInfoPayCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        String orderId = ((JSONObject) ii).getString("bizOrderNo");
        String status = ((JSONObject) ii).getString("status");
        OrderInfo orderInfo = new OrderInfo();
        if(orderId.length()>15){
            orderInfo = orderInfoMapper.selectByPayOrderId(orderId); //支付订单
        }else{
            orderInfo = orderInfoMapper.selectById(orderId); //支付订单
        }
        //修改代付订单状态
        if(status.equals("OK")){
            orderInfo.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfo);
        }
        return CommonResult.success();
    }


    /**
     *买家保证金托管代付扣除回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult buyerAgentPayCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        String orderId = ((JSONObject) ii).getString("bizOrderNo");
        String status = ((JSONObject) ii).getString("status");
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId); //代付订单信息
        ThingInfo thingInfo =thingInfoMapper.selectById(orderInfo.getThingId());
        //修改代付订单状态
        if(status.equals("OK")){
            orderInfo.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfo);
            AccountCashDetail accountCashDetail = new AccountCashDetail();
            accountCashDetail.setCreateTime(System.currentTimeMillis());
            accountCashDetail.setIsDel(false);
            accountCashDetail.setPayee("保证金扣除");
            accountCashDetail.setTradeOrderNo(orderInfo.getOrderId());//交易订单号
            accountCashDetail.setTradeTime(new Date());
            accountCashDetail.setSellId(orderInfo.getThingSellId().intValue());
            accountCashDetail.setTradeType(2); //已代付
            accountCashDetail.setTradeMoney(0 - orderInfo.getPrice());//交易金额
            //卖家账户信息
            AccountInfo accountInfoSeller = accountInfoMapper.selectByUserId(thingInfo.getUserId());
            //买家账户信息
            AccountInfo accountInfoBuyer = accountInfoMapper.selectByUserId(orderInfo.getBuyerId());
            accountInfoBuyer.setCashDeposit(accountInfoBuyer.getCashDeposit() - orderInfo.getPrice());
            accountInfoMapper.updateSelective(accountInfoBuyer);
            accountInfoMapper.updateSelective(accountInfoSeller);
//            CommonResult queryBalanceResult = tlOrderService.queryBalance(orderInfo.getSellerId().toString());
//            Long balance = Long.valueOf(queryBalanceResult.getData().toString());
//            accountInfoSeller.setBalance(balance);
//            accountInfoMapper.updateSelective(accountInfoSeller);
            accountCashDetail.setAccountId(accountInfoBuyer.getAccountId());
            accountCashDetail.setPreAccountBalance(accountInfoBuyer.getCashDeposit());//交易前金额
            accountCashDetail.setCashAccountBalance(accountInfoBuyer.getCashDeposit() - orderInfo.getPrice());//交易后保证金金额
            accountCashDetailMapper.insert(accountCashDetail);

            //当前订单状态变更
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderInfo.getOrderId());
            orderInfoGet.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfoGet);


        }
        //保证金代付，违约方保证金账户扣
        //未违约保证金退回--退款
        return CommonResult.success();
    }

    /**
     *卖家保证金托管代付扣除回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult sellerAgentPayCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        String orderId = ((JSONObject) ii).getString("bizOrderNo");
        String status = ((JSONObject) ii).getString("status");
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId); //代付订单信息
//        OrderInfo orderInfo1 = orderInfoMapper.selectOrderBySeller(orderInfo.getThingId(),orderInfo.getSellerId().toString());
        //修改代付订单状态
        if(status.equals("OK")){
            orderInfo.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfo);
            //分账完成，卖家保证金账户减少；买家余额增加
            AccountCashDetail accountCashDetail = new AccountCashDetail();
            accountCashDetail.setCreateTime(System.currentTimeMillis());
            accountCashDetail.setIsDel(false);
            accountCashDetail.setPayee("保证金扣除");
            accountCashDetail.setTradeOrderNo(orderInfo.getOrderId());//交易订单号
            accountCashDetail.setTradeTime(new Date());
            accountCashDetail.setSellId(orderInfo.getThingSellId().intValue());
            accountCashDetail.setTradeType(2); //已代付
            accountCashDetail.setTradeMoney(0 - orderInfo.getPrice());//交易金额
            //卖家账户信息
            AccountInfo accountInfoSeller = accountInfoMapper.selectByUserId(orderInfo.getSellerId());
            //买家账户信息
//            AccountInfo accountInfoBuyer = accountInfoMapper.selectByUserId(orderInfo1.getBuyerId());
//            CommonResult queryBalanceResult = tlOrderService.queryBalance(orderInfo.getSellerId().toString());
//            Long balance = Long.valueOf(queryBalanceResult.getData().toString());
//            accountInfoBuyer.setBalance(balance);
//            accountInfoMapper.updateSelective(accountInfoBuyer);
            //卖家更新保证金明细
            accountCashDetail.setAccountId(accountInfoSeller.getAccountId());
            accountCashDetail.setPreAccountBalance(accountInfoSeller.getCashDeposit());//交易前金额
            accountCashDetail.setCashAccountBalance(accountInfoSeller.getCashDeposit() - orderInfo.getPrice());//交易后保证金金额
            accountCashDetailMapper.insert(accountCashDetail);
            //
            accountInfoSeller.setCashDeposit(accountInfoSeller.getCashDeposit() - orderInfo.getPrice());
            accountInfoMapper.updateSelective(accountInfoSeller);
            //当前订单状态变更
            OrderInfo orderInfoGet = orderInfoMapper.selectById(orderInfo.getOrderId());
            orderInfoGet.setOrderStatus(8);
            orderInfoMapper.updateSelective(orderInfoGet);
        }
        //保证金代付，违约方保证金账户扣
        //未违约保证金退回--退款
        return CommonResult.success();
    }


    /**
     *提现回调
     * @param tlCallBackParam
     * @return
     */
    public CommonResult withdrawApplyCallBack(TlCallBackParam tlCallBackParam){
        log.info("回调参数信息：{}",tlCallBackParam.toString());
        JSON ii = JSONObject.parseObject(tlCallBackParam.getBizContent());
        OrderInfo orderInfo = orderInfoMapper.selectById(((JSONObject) ii).getString("bizOrderNo"));
        AccountInfo accountInfo = accountInfoMapper.selectByUserId(Integer.valueOf(((JSONObject) ii).getString("buyerBizUserId")));
        if(((JSONObject) ii).getString("status").equals("OK")){
            orderInfo.setOrderStatus(8);
            orderInfo.setTlOrderSn(((JSONObject) ii).getString("orderNo"));
            orderInfoMapper.updateSelective(orderInfo);
            CommonResult commonResult = tlOrderService.queryBalance(((JSONObject) ii).getString("buyerBizUserId"));
            Long balance = Long.valueOf(commonResult.getData().toString());
            accountInfo.setBalance(balance);
            accountInfoMapper.updateSelective(accountInfo);
        }
        return CommonResult.success();
    }

}
