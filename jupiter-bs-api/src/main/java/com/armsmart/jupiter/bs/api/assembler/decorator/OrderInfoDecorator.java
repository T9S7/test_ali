package com.armsmart.jupiter.bs.api.assembler.decorator;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.armsmart.jupiter.bs.api.assembler.OrderInfoAssembler;

import com.armsmart.jupiter.bs.api.constants.OrderStatus;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoUpdateParam;

import java.util.Date;
import java.util.List;

/**
 * OrderInfo 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class OrderInfoDecorator implements OrderInfoAssembler {

    private OrderInfoAssembler delegate;

    public OrderInfoDecorator(OrderInfoAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public OrderInfo getOrderInfo(OrderInfoAddParam param) {
        OrderInfo orderInfo = delegate.getOrderInfo(param);
        orderInfo.setCreateTime(System.currentTimeMillis());
        orderInfo.setIsDel(false);
        orderInfo.setSellerDel(false);
        orderInfo.setBuyerDel(false);
        orderInfo.setOrderStatus(OrderStatus.WAITING_PAY.getCode());
        orderInfo.setSellerDel(false);
        orderInfo.setBuyerDel(false);
        DateTime payDeadline = DateUtil.offsetMinute(new Date(), 8);
        orderInfo.setPayDeadline(payDeadline.getTime());
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderInfo(OrderInfoUpdateParam param) {
        OrderInfo orderInfo = delegate.getOrderInfo(param);
        orderInfo.setUpdateTime(System.currentTimeMillis());
        return orderInfo;
    }
}



