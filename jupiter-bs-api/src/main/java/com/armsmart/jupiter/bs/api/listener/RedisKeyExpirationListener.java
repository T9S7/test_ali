package com.armsmart.jupiter.bs.api.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.Order;
import com.armsmart.jupiter.bs.api.cache.OrderDeadlineCacheService;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.constants.BidStatesType;
import com.armsmart.jupiter.bs.api.constants.ExpireType;
import com.armsmart.jupiter.bs.api.constants.OrderStatus;
import com.armsmart.jupiter.bs.api.constants.SellType;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dao.*;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.*;
import com.armsmart.jupiter.bs.api.service.ThingSellInfoService;
import com.armsmart.jupiter.bs.api.service.TlOrderService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * redis key过期监听器
 *
 * @author wei.lin
 **/
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private ThingSellInfoService thingSellInfoService;
    @Autowired(required = false)
    private BidInfoMapper bidInfoMapper;
    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;
    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;
    @Autowired(required = false)
    private RefundInfoMapper refundInfoMapper;
    @Autowired
    private TlOrderService tlOrderService;
    @Autowired
    private OrderDeadlineCacheService orderDeadlineCacheService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] bytes) {
        String key = new String(message.getBody());
        if (key.startsWith(CacheConstant.AUCTION_BEGIN_CACHE)) {
            String sellId = getId(key);
            log.info("拍卖编号：{} 开始拍卖！", sellId);
        } else if (key.startsWith(CacheConstant.BID_ORDER_CACHE)) {
            String bidId = getId(key);
            BidInfo bidInfo = bidInfoMapper.selectById(Long.valueOf(bidId));
            if (null != bidInfo) {
                log.warn("拍卖编号：{} 到期未下单！", bidInfo.getSellId());
                //1.扣除保证金 买家保证金扣除,卖家保证金退还
                OrderInfo orderInfoBuyer = orderInfoMapper.selectByBuyer(bidInfo.getSellId(), bidInfo.getUserId().toString());
                tlOrderService.buyerCashDeduct(orderInfoBuyer.getOrderId().toString());
                //2.物品进入下架状态
                Long sellId = bidInfo.getSellId();
                ThingSellInfo thingSellInfo = thingSellInfoMapper.selectById(sellId);
                if (null == thingSellInfo) {
                    log.warn("拍卖信息不存在！sellId ={}", sellId);
                    return;
                }
                thingSellInfoMapper.thingPutOff(thingSellInfo.getThingId());
                ThingInfo update = new ThingInfo();
                update.setId(thingSellInfo.getThingId());
                update.setCurrentState(ThingState.PUT_OFF.getCode());
                thingInfoMapper.updateSelective(update);
                log.info("拍卖编号：{} 到期未下单！物品id={}进入下架状态", bidInfo.getSellId(), thingSellInfo.getThingId());
                ThingInfo thingInfo11 = thingInfoMapper.selectById(thingSellInfo.getThingId());
                OrderInfo orderInfoS = orderInfoMapper.selectBySeller(thingInfo11.getId(), thingInfo11.getUserId().toString());
                RefundInfo refundInfo = new RefundInfo();
                refundInfo.setAmount(orderInfoS.getPrice());
                refundInfo.setCreateTime(System.currentTimeMillis());
                refundInfo.setPayMatch(orderInfoS.getPayType());
                refundInfo.setIsDel(false);
                refundInfo.setOrderId(orderInfoS.getOrderId().toString());
                refundInfo.setOrderStatus(0);
                refundInfo.setUserId(orderInfoS.getSellerId());
                refundInfoMapper.insert(refundInfo);
                orderInfoS.setOrderStatus(9);
                orderInfoS.setUpdateTime(System.currentTimeMillis());
                orderInfoMapper.updateSelective(orderInfoS);
                //3.参拍记录更新为出局
                bidInfo.setBidStates(BidStatesType.OUT.getCode());
                bidInfoMapper.updateSelective(bidInfo);
                log.warn("用户{} 拍买中标物品{}到期未下单，进入出局状态！", bidInfo.getUserId(), bidInfo.getSellId());
            }
        } else if (key.startsWith(CacheConstant.AUCTION_END_CACHE)) {
            String sellId = getId(key);
            log.info("拍卖编号：{} 结束竞拍！", sellId);
            ThingSellInfo thingSellInfo = thingSellInfoService.selectByIdInternal(Long.valueOf(sellId));
            if (null == thingSellInfo) {
                log.info("拍卖编号：{} 不存在！", sellId);
                return;
            }
            ThingInfo thingInfo = thingInfoMapper.selectById(thingSellInfo.getThingId());
            if (null == thingInfo) {
                log.info("拍卖编号：{} 对应的物品信息不存在", sellId);
                return;
            }

            BidInfo bidInfo = bidInfoMapper.selectMaxBid(thingSellInfo.getId());
            if (null != bidInfo) {
                thingInfo.setCurrentState(ThingState.SOLD.getCode());//已出售
                log.info("拍卖编号：{} 拍卖成功！竞拍人ID={}，中标价={}", sellId, bidInfo.getUserId(), bidInfo.getBidPrice());
                //其他人全部出局
                bidInfoMapper.updateBidStateBySellId(BidStatesType.OUT.getCode(), bidInfo.getSellId(), bidInfo.getUserId());
                //领先者中标，进入待下单状态
                bidInfo.setBidStates(BidStatesType.DONE_TO_PAY.getCode());
                bidInfo.setOrderDeadline(DateUtil.offsetDay(new Date(), 1).getTime());
//                bidInfo.setOrderDeadline(DateUtil.offsetMinute(new Date(), 5).getTime());
                bidInfoMapper.updateSelective(bidInfo);
                orderDeadlineCacheService.set(bidInfo);

                //其他竞拍用户保证金进入退款列表
                OrderInfoQueryParam orderInfoQueryParam = new OrderInfoQueryParam();
                orderInfoQueryParam.setThingSellId(Long.valueOf(sellId));
                List<Integer> orderStatus = new ArrayList<>();
                orderStatus.add(8);
                orderInfoQueryParam.setOrderStatus(orderStatus);
                orderInfoQueryParam.setIsDel(false);
                List<OrderInfo> orderInfoList = orderInfoMapper.selectList(orderInfoQueryParam);
                orderInfoList.forEach(orderInfo -> {
                    //其他参拍者保证金进入待退还列表
                    if (!Objects.equals(orderInfo.getBuyerId(), bidInfo.getUserId())) {
                        RefundInfo refundInfo = new RefundInfo();
                        refundInfo.setAmount(orderInfo.getPrice());
                        refundInfo.setCreateTime(System.currentTimeMillis());
                        refundInfo.setPayMatch(orderInfo.getPayType());
                        refundInfo.setIsDel(false);
                        refundInfo.setOrderId(orderInfo.getOrderId().toString());
                        refundInfo.setOrderStatus(0);
                        refundInfo.setUserId(orderInfo.getBuyerId());
                        refundInfoMapper.insert(refundInfo);
                    }
                });
            } else {
                log.info("拍卖编号：{} 流拍！交易关闭！", sellId);
                thingInfo.setCurrentState(ThingState.PUT_OFF.getCode());
                //发布信息删除
                thingSellInfo.setIsDel(true);
                thingSellInfo.setPutOn(false);
                thingSellInfoMapper.updateSelective(thingSellInfo);
                //卖家保证退还
                OrderInfo orderInfoSeller = orderInfoMapper.selectBySeller(thingInfo.getId(), thingInfo.getUserId().toString());
                if (orderInfoSeller == null) {
                    log.info("卖家{}保证金不存在", thingInfo.getUserId().toString());
                    return;
                }
                orderInfoSeller.setOrderStatus(9);
                orderInfoMapper.updateSelective(orderInfoSeller);
                RefundInfo refundInfo = new RefundInfo();
                refundInfo.setAmount(orderInfoSeller.getPrice());
                refundInfo.setCreateTime(System.currentTimeMillis());
                refundInfo.setPayMatch(orderInfoSeller.getPayType());
                refundInfo.setIsDel(false);
                refundInfo.setOrderId(orderInfoSeller.getOrderId().toString());
                refundInfo.setOrderStatus(0);
                refundInfo.setUserId(orderInfoSeller.getSellerId());
                refundInfoMapper.insert(refundInfo);
            }
            thingInfoMapper.updateSelective(thingInfo);

        } else if (key.startsWith(CacheConstant.ORDER_PAY_CACHE)) {
            processOrderDeadline(key, ExpireType.PAY);
        } else if (key.startsWith(CacheConstant.ORDER_SEND_CACHE)) {
            processOrderDeadline(key, ExpireType.SHIPPED);
        }
    }

    private void processOrderDeadline(String key, ExpireType type) {
        String orderId = getId(key);
        log.info("订单和发货缓存执行开始,订单编号{}", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        log.info("待支付的订单信息：{}", orderInfo.toString());
        if (null == orderId || orderInfo.getIsDel()) {
            log.warn("订单{}倒计时到期，订单{}不存在！", type.getName(), orderId);
            return;
        }
        log.info("订单{}倒计时到期，编号{}", orderId);
        OrderInfo updateEntity = new OrderInfo();
        updateEntity.setOrderId(Long.valueOf(orderId));
        //交易失败
        updateEntity.setOrderStatus(OrderStatus.FAILED.getCode());
        updateEntity.setUpdateTime(System.currentTimeMillis());


        ThingInfo thingInfo = thingInfoMapper.selectById(orderInfo.getThingId());
        if (null == thingInfo) {
            log.warn("订单{}倒计时到期，订单物品{}不存在！", type.getName(), orderInfo.getThingId());
            return;
        }
        ThingSellInfo thingSellInfo = new ThingSellInfo();
        thingSellInfo.setId(orderInfo.getThingSellId());
        thingSellInfo.setIsDel(false);
        if (Objects.equals(type, ExpireType.PAY)) {
            //恢复物品在售状态
            log.info("订单{}倒计时到期，物品恢复出售状态！", orderId);
//            thingInfo.setCurrentState(ThingState.ON_SALE.getCode());
//            thingSellInfo.setPutOn(true);
            //拍卖2 中标超时未支付，保证金扣除
            ThingSellInfo thingSellInfo1 = thingSellInfoMapper.selectById(orderInfo.getThingSellId());
            if (thingSellInfo1 != null && thingSellInfo1.getSellType() == SellType.AUCTION.getCode()) {
                OrderInfo orderInfoBuy = orderInfoMapper.selectByBuyer(orderInfo.getThingSellId(),orderInfo.getBuyerId().toString());
                tlOrderService.buyerCashDeduct(orderInfoBuy.getOrderId().toString());
                thingSellInfo.setIsDel(true);//发布信息删除
                thingInfo.setCurrentState(ThingState.PUT_OFF.getCode());
                thingSellInfo.setPutOn(false);
                //卖家保证金退还
                OrderInfo orderInfo1 = orderInfoMapper.selectBySeller(thingInfo.getId(), orderInfo.getSellerId().toString());
                RefundInfo refundInfo = new RefundInfo();
                refundInfo.setAmount(orderInfo1.getPrice());
                refundInfo.setCreateTime(System.currentTimeMillis());
                refundInfo.setPayMatch(orderInfo1.getPayType());
                refundInfo.setIsDel(false);
                refundInfo.setOrderId(orderInfo1.getOrderId().toString());
                refundInfo.setOrderStatus(0);
                refundInfo.setUserId(orderInfo1.getSellerId());
                refundInfoMapper.insert(refundInfo);
                orderInfo1.setOrderStatus(9);
                orderInfoMapper.updateSelective(orderInfo1);
                updateEntity.setBuyerFailureReason("由于您未在规定时间内完成支付，将扣除保证金");
                updateEntity.setSellerFailureReason("由于买家未在规定时间内完成支付，交易已关闭");
            } else {
                //一口价物品恢复在售状态
                thingInfo.setCurrentState(ThingState.ON_SALE.getCode());
                thingSellInfo.setPutOn(true);
                updateEntity.setBuyerFailureReason("由于您未在规定时间内完成支付，交易已关闭");
                updateEntity.setSellerFailureReason("由于买家未在规定时间内完成支付，交易已关闭");
            }
        } else if (Objects.equals(type, ExpireType.SHIPPED)) {
            //非上链物品物品改为下架状态
            if (!thingInfo.getIsOfficial()) {
                log.info("订单{}倒计时到期，物品状态变更为已下架！", type, orderId);
                thingInfo.setCurrentState(ThingState.PUT_OFF.getCode());
                thingSellInfo.setPutOn(false);
            }
            updateEntity.setBuyerFailureReason("由于卖家未在规定时间内完成发货，交易已关闭");

            //卖家保证金扣除
            OrderInfo orderInfo1 = orderInfoMapper.selectBySeller(thingInfo.getId(), orderInfo.getSellerId().toString());
            if(orderInfo1 != null){
                tlOrderService.sellerCashDeduct(orderInfo1.getOrderId().toString());
                updateEntity.setSellerFailureReason("由于您未在规定时间内完成发货，将扣除保证金");
                orderInfo1.setOrderStatus(9);
                orderInfoMapper.updateSelective(orderInfo1);
            }else{
                updateEntity.setSellerFailureReason("由于您未在规定时间内完成发货，交易已关闭");
            }
            //买家资金退还
            log.info("支付订单信息：{}",orderInfo.toString());
            RefundInfo refundInfo = new RefundInfo();
            refundInfo.setAmount(orderInfo.getPrice());
            refundInfo.setCreateTime(System.currentTimeMillis());
            refundInfo.setPayMatch(orderInfo.getPayType());
            refundInfo.setIsDel(false);
            if(orderInfo.getTlOrderRequest() == null){
                refundInfo.setOrderId(orderInfo.getOrderId().toString());
            }else{
                refundInfo.setOrderId(orderInfo.getTlOrderRequest());
            }
            refundInfo.setOrderStatus(0);
            refundInfo.setUserId(orderInfo.getBuyerId());
            refundInfoMapper.insert(refundInfo);
        }
        orderInfoMapper.updateSelective(updateEntity);
        thingInfo.setUpdateTime(System.currentTimeMillis());
        thingInfoMapper.updateSelective(thingInfo);
        thingSellInfoMapper.updateSelective(thingSellInfo);
    }


    private String getId(String key) {
        String[] split = key.split(":");
        return split[split.length - 1];

    }
}
