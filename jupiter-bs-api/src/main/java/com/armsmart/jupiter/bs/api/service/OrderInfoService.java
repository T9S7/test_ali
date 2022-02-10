package com.armsmart.jupiter.bs.api.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.common.utils.SerialGenerator;
import com.armsmart.jupiter.bs.api.assembler.*;
import com.armsmart.jupiter.bs.api.blockchain.BlockChainNftService;
import com.armsmart.jupiter.bs.api.cache.OrderDeadlineCacheService;
import com.armsmart.jupiter.bs.api.cache.OrderLockCacheService;
import com.armsmart.jupiter.bs.api.cache.OrderPayCacheService;
import com.armsmart.jupiter.bs.api.cache.OrderSendCacheService;
import com.armsmart.jupiter.bs.api.cache.constants.CacheConstant;
import com.armsmart.jupiter.bs.api.constants.OrderCategory;
import com.armsmart.jupiter.bs.api.constants.SellType;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dao.*;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.NfcOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.PayCashDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail;
import com.armsmart.jupiter.bs.api.entity.*;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import com.armsmart.jupiter.bs.api.wxpay.TradeType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.armsmart.jupiter.bs.api.error.AuctionError.*;
import static com.armsmart.jupiter.bs.api.error.NEError.*;

/**
 * OrderInfo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class OrderInfoService {

    @Autowired
    private WXPay wxPay;
    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;
    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private OrderPayCacheService orderPayCacheService;
    @Autowired
    private OrderSendCacheService orderSendCacheService;
    @Autowired(required = false)
    private ContractInfoMapper contractInfoMapper;
    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;
    @Autowired
    private BlockChainNftService blockChainNftService;
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private PushInfoMapper pushInfoMapper;
    @Autowired(required = false)
    private WechatUnifiedOrderInfoMapper wechatUnifiedOrderInfoMapper;

    @Autowired(required = false)
    private WechatOrderInfoMapper wechatOrderInfoMapper;
    @Autowired
    private OrderLockCacheService orderLockCacheService;

    private final ReentrantLock lock = new ReentrantLock();

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    @Autowired(required = false)
    private ThingDealInfoMapper thingDealInfoMapper;

    @Autowired
    private TlOrderService tlOrderService;

    @Autowired(required = false)
    private RefundInfoMapper refundInfoMapper;

    @Autowired
    private MemberService memberService;
    @Autowired
    private OrderDeadlineCacheService orderDeadlineCacheService;
    @Autowired(required = false)
    private BidInfoMapper bidInfoMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<OrderInfoDetail> selectPage(OrderInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderInfoMapper.selectList(query));
        List<OrderInfoDetail> dtoList = OrderInfoAssembler.INSTANCE.getOrderInfoDetailList(pageInfo.getList());
        dtoList.forEach(item -> {
            ThingInfo thingInfo = thingInfoMapper.selectById(item.getThingId());
            ThingInfoDetail thingInfoDetail = ThingInfoAssembler.INSTANCE.getThingInfoDetail(thingInfo);
            item.setThingInfo(thingInfoDetail);

            UserInfo userInfo = userInfoService.selectByIdIncludeDel(item.getSellerId());
            if (null != userInfo) {
                item.setSellerNickname(userInfo.getNickname());
                item.setSellerAvatar(userInfo.getAvatar());
            }

        });
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 芯片读卡器列表
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<NfcOrderInfoDetail> getNfcOrder(PageQueryParam query) {
        OrderInfoQueryParam queryParam = new OrderInfoQueryParam();
        queryParam.setThingSellId(Long.valueOf(-1));
        List<Integer> orderStatus = new ArrayList<>();
        orderStatus.add(8);
        queryParam.setOrderStatus(orderStatus);
        queryParam.setPageNum(query.getPageNum());
        queryParam.setPageSize(query.getPageSize());
        queryParam.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderInfoMapper.selectList(queryParam));
        List<OrderInfoDetail> dtoList = OrderInfoAssembler.INSTANCE.getOrderInfoDetailList(pageInfo.getList());
        List<NfcOrderInfoDetail> nfcOrderInfoDetailList = OrderInfoAssembler.INSTANCE.getNfcOrderInfoDetailList(dtoList);
        nfcOrderInfoDetailList.forEach(item -> {
            if (item.getExpressNumber() == null) {
                item.setIsSend(false);
            } else {
                item.setIsSend(true);
            }
            ThingInfo thingInfo = thingInfoMapper.selectById(item.getThingId());
            ThingInfoDetail thingInfoDetail = ThingInfoAssembler.INSTANCE.getThingInfoDetail(thingInfo);
            item.setThingInfo(thingInfoDetail);

            UserInfo userInfo = userInfoService.selectByIdIncludeDel(item.getSellerId());
            if (null != userInfo) {
                item.setSellerNickname(userInfo.getNickname());
                item.setSellerAvatar(userInfo.getAvatar());
            }

        });
        return CommonPage.restPage(pageInfo, nfcOrderInfoDetailList);
    }

    /**
     * 插入数据
     *
     * @param orderInfoAddParam
     * @return com.armsmart.jupiter.bs.api.entity.OrderInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<OrderInfoDetail> insert(OrderInfoAddParam orderInfoAddParam) throws InterruptedException {
        OrderInfo entity = OrderInfoAssembler.INSTANCE.getOrderInfo(orderInfoAddParam);
        entity.setOrderSn(SerialGenerator.getOrder());
        entity.setPayStatus(0);//支付状态，未支付
        entity.setAmount(orderInfoAddParam.getAmount());
        CommonResult commonResult = null;
        Long thingId = orderInfoAddParam.getThingId();
        ThingInfo thingInfo = thingInfoMapper.selectById(thingId);
        if (thingInfo != null && thingInfo.getIsOfficial()) {
            log.info("-------当前下订单的是芯片和读卡器-------");
            entity.setThingName(thingInfo.getArtName());
            entity.setPrice(thingInfo.getCurrentPrice() * orderInfoAddParam.getAmount());
            entity.setOrderCategory(OrderCategory.UPLOAD_TOOL.getCode());
            orderInfoMapper.insert(entity);
            orderPayCacheService.set(entity);
            OrderInfoDetail orderInfoDetail = OrderInfoAssembler.INSTANCE.getOrderInfoDetail(entity);
            return CommonResult.success(orderInfoDetail);
        }
        log.info("--------------当前线程名称：" + Thread.currentThread().getName());
        try {
            //加锁下单，同一个物品同时只能一个人下单
            int tryCount = 0;
            boolean locked = false;
            while (!locked && tryCount < CacheConstant.LOCK_MAX_TRY_COUNT) {
                locked = orderLockCacheService.lock(thingId.toString());
                if (!locked) {
                    tryCount++;
                    Thread.sleep(50L);
                }
            }
            if (!locked) {
                log.warn("下单失败，下单加锁失败！thingId={}", thingId);
                return CommonResult.failed(ORDER_ADD_FAILED);
            }
            //加锁后再次拉取最新缓存，double check
            thingInfo = thingInfoMapper.selectById(thingId);
            if (null == thingInfo) {
                log.warn("获取物品信息为空！thingId={}", thingId);
                return CommonResult.failed(THING_ITEM_NOT_EXIST);
            }
            //自己不能购买
            if (Objects.equals(orderInfoAddParam.getBuyerId(), thingInfo.getUserId())) {
                return CommonResult.failed(CAN_NOT_BUY_SELF);
            }
            entity.setThingName(thingInfo.getArtName());
            entity.setPrice(thingInfo.getCurrentPrice() * orderInfoAddParam.getAmount());
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
            if (thingSellInfo.getSellType() == SellType.AUCTION.getCode()) {
                BidInfo maxBid = bidInfoMapper.selectMaxBid(thingSellInfo.getId());
                //检测下单是否已截止
                BidInfo bidInfo = orderDeadlineCacheService.get(maxBid.getBidId());
                if (null == bidInfo) {
                    log.warn("下单时间已截止！sellId={}", thingSellInfo.getId());
                    return CommonResult.failed(BID_ORDER_EXPIRE);
                }
                orderDeadlineCacheService.delete(bidInfo.getBidId());
                //下单已成功，删除参拍记录
                bidInfoMapper.deleteBySellIdAndUserId(bidInfo.getSellId(), bidInfo.getUserId());
                DateTime payDeadline = DateUtil.offsetDay(new Date(), 2);
//                DateTime payDeadline = DateUtil.offsetMinute(new Date(), 10);
                entity.setPayDeadline(payDeadline.getTime());
            }
            entity.setOrderCategory(OrderCategory.NORMAL.getCode());
            orderInfoMapper.insert(entity);
            orderPayCacheService.set(entity);
            thingInfo.setCurrentState(ThingState.SOLD.getCode());
            thingInfoMapper.updateSelective(thingInfo);
            OrderInfoDetail orderInfoDetail = OrderInfoAssembler.INSTANCE.getOrderInfoDetail(entity);
            commonResult = CommonResult.success(orderInfoDetail);
        } finally {
            orderLockCacheService.unlock(thingId.toString());
        }
        return commonResult;


    }


    /**
     * 获取预支付相关信息
     *
     * @param orderInfoQueryParam
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult getUnifiedOrderInfo(OrderInfoQueryParam orderInfoQueryParam, HttpServletRequest request) throws Exception {
        Map<String, String> unifiedOrderResult = null;
        ThingInfo thingInfo = thingInfoMapper.selectById(orderInfoQueryParam.getThingId());
        if (null == thingInfo) {
            return CommonResult.failed(THING_NOT_EXIST);
        }
        OrderInfo entity = orderInfoMapper.selectById(orderInfoQueryParam.getOrderId());
        if (entity.getOrderSn() == null || entity.getOrderSn().equals("")) {
            return CommonResult.failed(ORDER_NOT_EXIST);
        }
        if (entity.getPayType() == 1) {
            //微信支付
            unifiedOrderResult = unifiedOrder(entity, request);
            Map<String, String> appPayInfoMap = wxPay.getAppPayInfoMap(unifiedOrderResult);
            WechatUnifiedOrderInfoAddParam wechatUnifiedOrderInfoAddParam = new WechatUnifiedOrderInfoAddParam();
            wechatUnifiedOrderInfoAddParam.setAppId(appPayInfoMap.get("appid"));
            wechatUnifiedOrderInfoAddParam.setNonceStr(appPayInfoMap.get("noncestr"));
            wechatUnifiedOrderInfoAddParam.setOrderSn(entity.getOrderSn());
            wechatUnifiedOrderInfoAddParam.setPartnerId(appPayInfoMap.get("partnerid"));
            wechatUnifiedOrderInfoAddParam.setPrepayId(appPayInfoMap.get("prepayid"));
            wechatUnifiedOrderInfoAddParam.setSign(appPayInfoMap.get("sign"));
            wechatUnifiedOrderInfoAddParam.setTimeStamp(appPayInfoMap.get("timestamp"));
            wechatUnifiedOrderInfoAddParam.setPackageStr(appPayInfoMap.get("package"));
            WechatUnifiedOrderInfo wechatUnifiedOrderInfoIentity = WechatUnifiedOrderInfoAssembler.INSTANCE.getWechatUnifiedOrderInfo(wechatUnifiedOrderInfoAddParam);
            wechatUnifiedOrderInfoMapper.insert(wechatUnifiedOrderInfoIentity);
            return CommonResult.success(appPayInfoMap);
        } else if (entity.getPayType() == 2) {
            //支付宝
        }
        return CommonResult.success(unifiedOrderResult);
    }


    /**
     * 修改数据
     *
     * @param orderInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(OrderInfoUpdateParam orderInfoUpdateParam) {
        OrderInfo entity = OrderInfoAssembler.INSTANCE.getOrderInfo(orderInfoUpdateParam);
        orderInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param orderId
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long orderId, Integer userId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (orderInfo == null) {
            return CommonResult.success();
        }
        OrderInfo entity = new OrderInfo();
        entity.setOrderId(orderId);
        entity.setUpdateTime(System.currentTimeMillis());
        if (userId == null) {
            entity.setIsDel(true);
        } else {
            if (Objects.equals(userId, orderInfo.getBuyerId())) {
                entity.setBuyerDel(true);
            } else if (Objects.equals(userId, orderInfo.getSellerId())) {
                entity.setSellerDel(true);
            }
        }
        orderInfoMapper.updateSelective(entity);
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
                deleteById(id, null);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     *
     * @param orderId 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail
     * @date 2020/01/01
     */
    public CommonResult<OrderInfoDetail> selectById(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        OrderInfoDetail orderInfoDetail = OrderInfoAssembler.INSTANCE.getOrderInfoDetail(orderInfo);
        if (null != orderInfo) {
            ThingInfo thingInfo = thingInfoMapper.selectById(orderInfoDetail.getThingId());
            ThingInfoDetail thingInfoDetail = ThingInfoAssembler.INSTANCE.getThingInfoDetail(thingInfo);
            orderInfoDetail.setThingInfo(thingInfoDetail);
            UserInfo userInfo = userInfoService.selectByIdIncludeDel(orderInfo.getSellerId());
            if (null != userInfo) {
                orderInfoDetail.setSellerNickname(userInfo.getNickname());
                orderInfoDetail.setSellerAvatar(userInfo.getAvatar());
            }
        }
        return CommonResult.success(orderInfoDetail);
    }

    /**
     * 订单发货
     *
     * @param sendOutParam
     * @return com.armsmart.common.api.CommonResult
     */
    public CommonResult sendOut(OrderInfoSendOutParam sendOutParam) {
        OrderInfo orderInfo = orderInfoMapper.selectById(sendOutParam.getOrderId());
        if (null == orderInfo) {
            log.warn("订单{}发货失败,订单不存在!", sendOutParam.getOrderId());
            return CommonResult.failed(ORDER_NOT_EXIST);
        }
        if (!Objects.equals(orderInfo.getSellerId(), sendOutParam.getUserId())) {
            log.warn("订单{}发货失败,用户ID{}不匹配，无权限!", sendOutParam.getOrderId(), sendOutParam.getUserId());
            return CommonResult.failed(THING_USER_NOT_MATCH);
        }
        if (orderInfo.getOrderStatus() != 2) {
            log.warn("订单{}发货失败,订单非待发货状态!", sendOutParam.getOrderId());
            return CommonResult.failed(ORDER_STATUS_NOT_MATCH_2);
        }
        OrderInfo updateEntity = new OrderInfo();
        updateEntity.setOrderId(orderInfo.getOrderId());
        updateEntity.setExpressCompany(sendOutParam.getExpressCompany());
        updateEntity.setExpressNumber(sendOutParam.getExpressNumber());
        updateEntity.setOrderStatus(3);
        orderInfoMapper.updateSelective(updateEntity);
        orderSendCacheService.delete(orderInfo.getOrderId());
//        //确认收货退还保证金
//        OrderInfo orderInfoSeller = orderInfoMapper.selectBySeller(orderInfo.getThingId(),orderInfo.getSellerId().toString());
//        RefundParam refundParam = new RefundParam();
//        refundParam.setUser_id(orderInfoSeller.getSellerId().toString());
//        refundParam.setOrg_order_id(orderInfoSeller.getOrderId().toString());
//        refundParam.setAmont(orderInfoSeller.getPrice());
//        tlOrderService.cashReturn(refundParam);
        log.info("订单{}发货成功!", sendOutParam.getOrderId());
        return CommonResult.success();
    }

    public Map<String, String> unifiedOrder(OrderInfo orderInfo, HttpServletRequest request) throws Exception {

        SortedMap<String, String> map = new TreeMap<>();
        //设备号
        map.put("device_info", "WEB");
        //商品名称
        map.put("body", orderInfo.getThingName());
        //商户订单号【备注：每次发起请求都需要随机的字符串，否则失败。
        map.put("out_trade_no", orderInfo.getOrderSn());
        //支付金额：分
        map.put("total_fee", String.valueOf(orderInfo.getPrice()));
        //客户端主机
        map.put("spbill_create_ip", request.getRemoteAddr());
        //交易类型
        map.put("trade_type", TradeType.APP.name());
        Map<String, String> payResultMap = wxPay.unifiedOrder(map);
        log.info("unifiedOrder result:{}", payResultMap);
        if ("SUCCESS".equals(payResultMap.get("return_code"))) {
            return payResultMap;
        }
        throw new BusinessException("微信预支付创建失败");
    }

    @Transactional(rollbackFor = Exception.class)
    public String payCallback(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Map<String, String> resultMap = preCheckCallback(request);
            log.info("微信支付回调结果：{}", resultMap);
            String orderSn = resultMap.get("out_trade_no");
            log.info("---------orderSn-------" + orderSn);
            OrderInfo orderInfo = orderInfoMapper.selectByOrderSn(orderSn);
            if (orderInfo == null) {
                //订单不存在
                map.put("return_code", "FAIL");
                map.put("return_msg", ORDER_NOT_EXIST.getMessage());
                return WXPayUtil.mapToXml(map);
            }
            String totalFee = resultMap.get("total_fee");
            if (orderInfo.getPrice() != Long.parseLong(totalFee)) {
                //订单金额不正确
                map.put("return_code", "FAIL");
                map.put("return_msg", ORDER_FEE_ERROR.getMessage());
                return WXPayUtil.mapToXml(map);
            }
            if (lock.tryLock()) {
                //业务加锁
                if (orderInfo.getPayStatus() != 0) {
                    //订单状态已更新
                    map.put("return_code", "SUCCESS");
                    map.put("return_msg", ORDER_STATUS_UPDATED.getMessage());
                    return WXPayUtil.mapToXml(map);
                }
                //更新订单支付状态
                if ("SUCCESS".equals(resultMap.get("return_code"))) {
                    orderInfo.setPayStatus(1); //微信支付成功
                    if (orderInfo.getThingSellId() == -1) {
                        orderInfo.setOrderStatus(8);//待发货
                    } else {
                        orderInfo.setOrderStatus(2);//待发货
                    }
                    orderInfo.setPayTime(System.currentTimeMillis());
//                    orderInfo.setSendOutDeadline(DateUtil.offsetDay(new Date(), 2).getTime());
                    orderInfo.setSendOutDeadline(DateUtil.offsetMinute(new Date(), 10).getTime());
                    orderInfoMapper.updateSelective(orderInfo);
                    orderPayCacheService.delete(orderInfo.getOrderId());
                    orderSendCacheService.set(orderInfo);
//                    orderSendCacheService.set(orderInfo);
                    log.info("订单{}支付成功！", orderInfo);
                    map.put("return_code", "SUCCESS");
                    map.put("return_msg", "OK");
                } else {
                    orderInfo.setPayStatus(2); //微信支付失败
                    orderInfoMapper.updateSelective(orderInfo);
                    map.put("return_code", "FAIL");
                    map.put("return_msg", ORDER_PAY_FAIL.getMessage());
                }
            } else {
                map.put("return_code", "FAIL");
                map.put("return_msg", ORDER_LOCK_GET_FAILED.getMessage());
            }
        } catch (Exception e) {
            log.error("支付回调处理失败：", e);
            map.put("return_code", "FAIL");
            if (e instanceof BusinessException) {
                map.put("return_msg", e.getMessage());
            }
        } finally {
            if (!lock.isHeldByCurrentThread()) {
                return WXPayUtil.mapToXml(map);
            }
            lock.unlock();
            return WXPayUtil.mapToXml(map);
        }

    }


    private Map<String, String> preCheckCallback(HttpServletRequest request) throws Exception {
        byte[] bytes = IoUtil.readBytes(request.getInputStream());
        String xml = new String(bytes, StandardCharsets.UTF_8.toString());
        log.info("---------xml-------" + xml);
        Map<String, String> map = WXPayUtil.xmlToMap(xml);
        if (null == map) {
            throw new BusinessException("回调参数为空");
        }
        boolean signatureValid = wxPay.isPayResultNotifySignatureValid(map);
        if (!signatureValid) {
            throw new BusinessException("回调验签失败");
        }
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResult weChatOrderquery(WechatOrderInfoParam wechatOrderInfoParam) throws Exception {
        CommonResult commonResult = null;
        OrderInfo orderInfo = orderInfoMapper.selectById(wechatOrderInfoParam.getOrder_id());
        if (orderInfo == null) {
            return CommonResult.failed(ORDER_NOT_EXIST);
        }
        wechatOrderInfoParam.setOut_trade_no(orderInfo.getOrderSn());
        Map<String, String> paramMap = wxPay.getSignOfOrderQuery(wechatOrderInfoParam);
        Map<String, String> orderQueryResult = wxPay.orderQuery(paramMap);
        log.info("----------orderQueryResult------" + orderQueryResult.toString());


        WechatOrderInfo wechatOrderInfoNew = new WechatOrderInfo();

        if (orderQueryResult != null && StrUtil.nullToEmpty(orderQueryResult.get("return_code")).equals("SUCCESS") && StrUtil.nullToEmpty(orderQueryResult.get("result_code")).equals("SUCCESS") &&
                StrUtil.nullToEmpty(orderQueryResult.get("trade_state")).equals("SUCCESS")) {
            if (orderInfo.getOrderStatus() == 1) {
                if (orderInfo.getThingSellId() == -1) {
                    orderInfo.setOrderStatus(8);//待发货
                } else {
                    orderInfo.setOrderStatus(2);//待发货
                }
                orderInfo.setPayStatus(1);
                orderInfo.setPayTime(System.currentTimeMillis());
//                orderInfo.setSendOutDeadline(DateUtil.offsetDay(new Date(), 10).getTime());
                orderInfo.setSendOutDeadline(DateUtil.offsetMinute(new Date(), 10).getTime());
                orderInfoMapper.updateSelective(orderInfo);
            }
            orderPayCacheService.delete(orderInfo.getOrderId());
            String errMsg = StrUtil.nullToEmpty(orderQueryResult.get("return_msg"));
            commonResult = CommonResult.success();
            wechatOrderInfoNew.setReturnMsg(errMsg);

        } else if (!StrUtil.nullToEmpty(orderQueryResult.get("return_code")).equals("SUCCESS")) {
            String errMsg = StrUtil.nullToEmpty(orderQueryResult.get("return_msg"));
            commonResult = CommonResult.failed(PAY_OF_WECHAT_FAILED, errMsg);
            wechatOrderInfoNew.setReturnMsg(errMsg);

        } else if (StrUtil.nullToEmpty(orderQueryResult.get("return_code")).equals("SUCCESS") && !StrUtil.nullToEmpty(orderQueryResult.get("result_code")).equals("SUCCESS")) {

            String errMsg = StrUtil.nullToEmpty(orderQueryResult.get("err_code_des"));
            commonResult = CommonResult.failed(PAY_OF_WECHAT_FAILED, errMsg);
            wechatOrderInfoNew.setReturnMsg(errMsg);

        } else if (StrUtil.nullToEmpty(orderQueryResult.get("return_code")).equals("SUCCESS") && StrUtil.nullToEmpty(orderQueryResult.get("result_code")).equals("SUCCESS")
                && !StrUtil.nullToEmpty(orderQueryResult.get("trade_state")).equals("SUCCESS")) {

            String errMsg = StrUtil.nullToEmpty(orderQueryResult.get("err_code_des"));
            commonResult = CommonResult.failed(PAY_OF_WECHAT_FAILED, errMsg);
            wechatOrderInfoNew.setReturnMsg(errMsg);
        }


        wechatOrderInfoNew.setOutTradeNo(orderInfo.getOrderSn());
        wechatOrderInfoNew.setAttach(orderQueryResult.get("attach"));
        wechatOrderInfoNew.setBankType(orderQueryResult.get("bank_type"));
        wechatOrderInfoNew.setCashFee(Integer.parseInt(orderQueryResult.get("cash_fee") == null ? "0" : orderQueryResult.get("cash_fee")));
        wechatOrderInfoNew.setCashFeeType(orderQueryResult.get("cash_fee_type"));
        wechatOrderInfoNew.setDeviceInfo(orderQueryResult.get("device_info"));
        wechatOrderInfoNew.setFeeType(orderQueryResult.get("fee_type"));
        wechatOrderInfoNew.setIsSubscribe(orderQueryResult.get("is_subscribe"));
        wechatOrderInfoNew.setOpenid(orderQueryResult.get("openid"));
//        wechatOrderInfoNew.setOutTradeNo(orderQueryResult.get("out_trade_no"));
        wechatOrderInfoNew.setSettlementTotalFee(Integer.parseInt(orderQueryResult.get("settlement_total_fee") == null ? "0" : orderQueryResult.get("settlement_total_fee")));
        wechatOrderInfoNew.setTimeEnd(orderQueryResult.get("time_end"));
        wechatOrderInfoNew.setTotalFee(Integer.parseInt(orderQueryResult.get("total_fee") == null ? "0" : orderQueryResult.get("total_fee")));
        wechatOrderInfoNew.setTradeState(orderQueryResult.get("trade_state"));
        wechatOrderInfoNew.setTradeType(orderQueryResult.get("trade_type"));
        wechatOrderInfoNew.setTransactionId(orderQueryResult.get("transaction_id"));
        wechatOrderInfoNew.setTradeStateDesc(orderQueryResult.get("trade_state_desc"));
        wechatOrderInfoNew.setIsDel(false);

        WechatOrderInfo wechatOrderInfo = wechatOrderInfoMapper.selectByOutTradeNo(wechatOrderInfoParam.getOut_trade_no());
        if (wechatOrderInfo == null) {
            wechatOrderInfoNew.setCreateTime(System.currentTimeMillis());
            wechatOrderInfoMapper.insert(wechatOrderInfoNew);
        } else {
            wechatOrderInfoNew.setId(wechatOrderInfo.getId());
            wechatOrderInfoNew.setUpdateTime(new Date().getTime());
            wechatOrderInfoMapper.updateSelective(wechatOrderInfoNew);
        }

        return commonResult;

    }


    /**
     * 链上操作
     */
    public DeferredResult<CommonResult> thingSendOut(ModifyOwnerAddInfoParam param) {
        Object ii = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDetails userDetails = (AppUserDetails) ii;
        ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(param.getContractAddr());
        ThingDealInfo thingDealInfo = thingDealInfoMapper.selectDealInfo(param.getContractAddr());
        if (thingInfo == null && thingInfo.getCurrentState() != 2) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(ADDR_NOT_EXIST_OR_SELL_FAILE));
            return deferredResult;
        }
        ModifyOwnerAddParam modifyOwnerAddParam = new ModifyOwnerAddParam();
        modifyOwnerAddParam.setArtistSign(param.getArtistSign());
        modifyOwnerAddParam.setArtSign(param.getArtSign());
        modifyOwnerAddParam.setAuthTokenId(param.getContractAddr());
        modifyOwnerAddParam.setMessageIn(param.getMessageIn());
        modifyOwnerAddParam.setWebStart(param.getMessageIn());
        modifyOwnerAddParam.setModifyType(param.getModifyType());
        modifyOwnerAddParam.setTermInfo(param.getTermInfo());
        String nextDealAddr;
        if (param.getModifyType() == 1) {
            nextDealAddr = contractInfoMapper.nextDealAddr();
        } else {
            nextDealAddr = thingDealInfo.getDealContractAddr();
            if (thingDealInfo == null) {
                DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
                deferredResult.setResult(CommonResult.failed(THING_GIVE_NO_EXIST));
                return deferredResult;
            }
            if (!thingDealInfo.getContractAddr().equals(param.getContractAddr())) {
                DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
                deferredResult.setResult(CommonResult.failed(THING_GIVE_NO_MATCH));
                return deferredResult;
            }
        }
        modifyOwnerAddParam.setNextDealTokenId(nextDealAddr);
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
        modifyOwnerAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
        modifyOwnerAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.modifyOwner(modifyOwnerAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                if (param.getModifyType() == 1) {
                    contractInfoMapper.updateAddr(nextDealAddr);
                    ThingDealInfo thingDealIAdd = new ThingDealInfo();
                    thingDealIAdd.setIsDel(false);
                    thingDealIAdd.setIsDone(false);
                    thingDealIAdd.setDealContractAddr(nextDealAddr);
                    thingDealIAdd.setThingId(thingInfo.getId());
                    thingDealIAdd.setCreateTime(System.currentTimeMillis());
                    thingDealIAdd.setType(2);
                    thingDealIAdd.setGiveUserId(userDetails.getUserInfo().getId().longValue());
                    thingDealIAdd.setContractAddr(param.getContractAddr());
                    thingDealIAdd.setBuyUserId(param.getUserId());
                    thingDealInfoMapper.insert(thingDealIAdd);
                    log.info("卖家发货操作成功");
                } else {
                    thingInfo.setUpdateTime(System.currentTimeMillis());
                    thingInfo.setUserId(param.getUserId().intValue());
                    thingInfo.setCurrentState(99);
                    thingInfoMapper.updateSelective(thingInfo);
                    thingDealInfo.setIsDone(true);
                    thingDealInfo.setDoneTime(System.currentTimeMillis());
                    thingDealInfo.setUpdateTime(System.currentTimeMillis());
                    thingDealInfoMapper.updateSelective(thingDealInfo);
                    log.info("收货确认物品id：" + thingInfo.getId());
                    OrderInfo orderInfo = orderInfoMapper.selectByThingId(thingInfo.getId());
                    log.info("确认收货订单信息" + orderInfo.toString());
                    orderInfo.setOrderStatus(8);//交易完成
                    orderInfo.setUpdateTime(System.currentTimeMillis());
                    orderInfoMapper.updateSelective(orderInfo);
                    ThingSellInfo thingSellInfo = new ThingSellInfo();
                    thingSellInfo.setId(orderInfo.getThingSellId());
                    thingSellInfo.setIsDel(true);
                    thingSellInfoMapper.updateSelective(thingSellInfo);
                    log.info("买家确认收货成功");
                    //确认收货将资金付给卖家
                    //创建待付支付订单
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
                    orderInfodf.setThingName("货款支付给卖家");
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
                    String bizOrderNo = "";
                    if (orderInfo.getTlOrderRequest() == null) {
                        bizOrderNo = orderInfo.getOrderId().toString();
                    } else {
                        bizOrderNo = orderInfo.getTlOrderRequest();
                    }
                    CollectPayParam collectPayParam = new CollectPayParam();
                    collectPayParam.setBizOrderNo(bizOrderNo);
                    collectPayParam.setAmount(orderInfo.getPrice());
                    collectPayList.add(collectPayParam);
                    signalAgentPayParam.setCollectPayList(collectPayList);
                    signalAgentPayParam.setFee(0l);
                    signalAgentPayParam.setAmount(orderInfo.getPrice());
                    log.info("代付参数" + signalAgentPayParam.toString());
                    tlOrderService.signalAgentPay(signalAgentPayParam);
                    //卖家保证金退还
                    OrderInfo orderInfoSeller = orderInfoMapper.selectBySeller(thingInfo.getId(), thingInfo.getUserId().toString());
                    RefundInfo refundInfo = new RefundInfo();
                    refundInfo.setAmount(orderInfoSeller.getPrice());
                    refundInfo.setCreateTime(System.currentTimeMillis());
                    refundInfo.setPayMatch(orderInfoSeller.getPayType());
                    refundInfo.setIsDel(false);
                    refundInfo.setOrderId(orderInfoSeller.getOrderId().toString());
                    refundInfo.setOrderStatus(0);
                    refundInfo.setUserId(orderInfoSeller.getBuyerId());
                    log.info("卖家保证金退还参数   " + refundInfo.toString());
                    refundInfoMapper.insert(refundInfo);
                }
            } else {
                log.warn("操作失败");
            }
        });
        return deferredResult;

    }

    public static void main(String[] args) {
        System.out.println(Integer.parseInt(""));
    }


    /**
     * 订单支付
     *
     * @param orderPayParam
     * @return
     */
    public CommonResult<PayCashDetail> orderPay(OrderPayParam orderPayParam) {
        PayCashDetail payCashDetail = new PayCashDetail();
        payCashDetail.setPayAgain(0);
        Map<String, Object> orderId = new HashMap<>();
        if (orderPayParam.getPayMatch() == 3) {
            //查询会员详情
            CommonResult commonResult = memberService.getMemberInfo(orderPayParam.getBuyerId());
            if (commonResult.getCode() == 200) {
                JSONObject data = JSON.parseObject(commonResult.getData().toString());
                if (data.getString("isSetPayPwd").equals("false")) {
                    payCashDetail.setIfSetPwd(0);
                    UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(orderPayParam.getBuyerId());
                    SetPayPwdParam setPayPwdParam = new SetPayPwdParam();
                    setPayPwdParam.setUserId(orderPayParam.getBuyerId());
                    setPayPwdParam.setIdentityNo(userAuthentication.getCertificateNo());
                    setPayPwdParam.setName(userAuthentication.getName());
                    setPayPwdParam.setPhone(userAuthentication.getMobile());
                    CommonResult commonResult1 = memberService.setPayPwd(setPayPwdParam);
                    payCashDetail.setUrl(commonResult1.getData().toString());
                    orderId.put("bizOrderNo", "");
                    payCashDetail.setCommonResult(orderId);
                    return CommonResult.success(payCashDetail);
                }
                ;
            } else {
                return CommonResult.failed("获取会员详情失败");
            }
        }
        payCashDetail.setIfSetPwd(1);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderPayParam.getOrderId());
        if (orderInfo.getPayDeadline() < System.currentTimeMillis()) {
            return CommonResult.failed("超时订单");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        orderInfo.setPayType(orderPayParam.getPayMatch());
        orderInfo.setPayTime(System.currentTimeMillis());
        orderInfoMapper.updateSelective(orderInfo);
        DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
        List<RecieverListParam> recieverList = new ArrayList<>();
        RecieverListParam recieverListParam = new RecieverListParam();
        recieverListParam.setBizUserId(orderInfo.getSellerId().toString());
        recieverListParam.setAmount(orderInfo.getPrice());
        recieverList.add(recieverListParam);
        AgentCollectApplyParam agentCollectApplyParam = new AgentCollectApplyParam();
        agentCollectApplyParam.setPayerId(orderInfo.getBuyerId().toString());
        agentCollectApplyParam.setRecieverList(recieverList);
        agentCollectApplyParam.setTlWxPayParam(orderPayParam.getTlWxPayParam());
        agentCollectApplyParam.setPayMethod(orderPayParam.getPayMatch());
        agentCollectApplyParam.setFee(0l);
        agentCollectApplyParam.setBankCardNo(orderPayParam.getBankCardNo());
        agentCollectApplyParam.setBizOrderNo(orderPayParam.getOrderId());
        agentCollectApplyParam.setAmount(orderInfo.getPrice());
//        agentCollectApplyParam.setOrderExpireDatetime(sdf.format(date));
        agentCollectApplyParam.setOrderExpireDatetime(sdf.format(payDeadline));
        CommonResult commonResult = null;
        commonResult = tlOrderService.orderPay(agentCollectApplyParam);
        if (orderPayParam.getPayMatch() == 3 && commonResult.getCode() == 200) {
            PayByPwdParam payByPwdParam = new PayByPwdParam();
            payByPwdParam.setOrderId(orderInfo.getOrderId().toString());
            payByPwdParam.setUserId(orderInfo.getBuyerId().toString());
//            String url = tlOrderService.payByPwd(payByPwdParam).getData().toString();
            String url = tlOrderService.payByPwd(payByPwdParam);
//            HashMap<String, Object> returnInfo = new HashMap<>();
            orderId.put("commonResult", commonResult.getData());
            payCashDetail.setUrl(url);
            payCashDetail.setCommonResult(orderId);
            return CommonResult.success(payCashDetail);
        } else {
            payCashDetail.setUrl("");
            payCashDetail.setCommonResult(commonResult.getData());
            return CommonResult.success(payCashDetail);
        }
    }

    /**
     * 再次支付接口
     */
    public CommonResult<PayCashDetail> orderPayAgain(OrderPayParam orderPayParam) {
        PayCashDetail payCashDetail = new PayCashDetail();
        payCashDetail.setPayAgain(0);
        Map<String, Object> orderId = new HashMap<>();
        if (orderPayParam.getPayMatch() == 3) {
            //查询会员详情
            CommonResult commonResult = memberService.getMemberInfo(orderPayParam.getBuyerId());
            if (commonResult.getCode() == 200) {
                JSONObject data = JSON.parseObject(commonResult.getData().toString());
                if (data.getString("isSetPayPwd").equals("false")) {
                    payCashDetail.setIfSetPwd(0);
                    UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(orderPayParam.getBuyerId());
                    SetPayPwdParam setPayPwdParam = new SetPayPwdParam();
                    setPayPwdParam.setUserId(orderPayParam.getBuyerId());
                    setPayPwdParam.setIdentityNo(userAuthentication.getCertificateNo());
                    setPayPwdParam.setName(userAuthentication.getName());
                    setPayPwdParam.setPhone(userAuthentication.getMobile());
                    CommonResult commonResult1 = memberService.setPayPwd(setPayPwdParam);
                    payCashDetail.setUrl(commonResult1.getData().toString());
                    orderId.put("bizOrderNo", "");
                    payCashDetail.setCommonResult(orderId);
                    return CommonResult.success(payCashDetail);
                }
            } else {
                return CommonResult.failed("获取会员详情失败");
            }
        }
        payCashDetail.setIfSetPwd(1);
        String orderIdAgain = getOrderId(orderPayParam.getBuyerId(), 2);
        OrderInfo orderInfo = orderInfoMapper.selectById(orderPayParam.getOrderId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将时间戳转换为时间
//        Date date = new Date(orderInfo.getPayDeadline());
        DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
        if (orderInfo.getPayDeadline() < System.currentTimeMillis()) {
            return CommonResult.failed("超时订单");
        }
        orderInfo.setPayType(orderPayParam.getPayMatch());
        orderInfo.setTlOrderRequest(orderIdAgain);
        orderInfoMapper.updateSelective(orderInfo);
        List<RecieverListParam> recieverList = new ArrayList<>();
        RecieverListParam recieverListParam = new RecieverListParam();
        recieverListParam.setBizUserId(orderInfo.getSellerId().toString());
        recieverListParam.setAmount(orderInfo.getPrice());
        recieverList.add(recieverListParam);
        AgentCollectApplyParam agentCollectApplyParam = new AgentCollectApplyParam();
        agentCollectApplyParam.setPayerId(orderInfo.getBuyerId().toString());
        agentCollectApplyParam.setRecieverList(recieverList);
        agentCollectApplyParam.setTlWxPayParam(orderPayParam.getTlWxPayParam());
        agentCollectApplyParam.setPayMethod(orderPayParam.getPayMatch());
        agentCollectApplyParam.setFee(0l);
        agentCollectApplyParam.setBankCardNo(orderPayParam.getBankCardNo());
        agentCollectApplyParam.setBizOrderNo(orderPayParam.getOrderId());
        agentCollectApplyParam.setOrderIdAgain(orderIdAgain);
        agentCollectApplyParam.setAmount(orderInfo.getPrice());
//        agentCollectApplyParam.setOrderExpireDatetime(sdf.format(date));
        agentCollectApplyParam.setOrderExpireDatetime(sdf.format(payDeadline));
        CommonResult commonResult = null;
        commonResult = tlOrderService.orderPay(agentCollectApplyParam);
        if (commonResult.getCode() == 200) {
            if (orderPayParam.getPayMatch() == 3) {
                PayByPwdParam payByPwdParam = new PayByPwdParam();
                payByPwdParam.setOrderId(orderIdAgain);
                payByPwdParam.setUserId(orderInfo.getBuyerId().toString());
                String url = tlOrderService.payByPwd(payByPwdParam);
                payCashDetail.setUrl(url);
                payCashDetail.setCommonResult(commonResult.getData());
                return CommonResult.success(payCashDetail);
            } else {
                payCashDetail.setUrl("");
                payCashDetail.setCommonResult(commonResult.getData());
                return CommonResult.success(payCashDetail);
            }
        } else {
            return CommonResult.failed(commonResult.getMsg());
        }
    }

    /**
     * 订单生成方法
     *
     * @param
     * @return 格式为E20200819001
     */
    public String getOrderId(String userId, Integer type) {
        // 对当前日期进行格式化
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String date = sdf.format(new Date());
        String end = "";
        String date = String.valueOf(System.currentTimeMillis());
        switch (type) {
            case 1:
                end = "cz";
                break;
            case 2:
                end = "zz";
                break;
            case 3:
                end = "tx";
                break;
            case 4:
                end = "tk";
                break;
        }
        String returnInfo = userId + date + end;
        return returnInfo;


    }

}
