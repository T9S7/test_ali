package com.armsmart.jupiter.bs.api.dao;

import cn.hutool.db.sql.Order;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *OrderInfo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface OrderInfoMapper {

	/**
     * 列表查询
	 * @param orderInfoQueryParam orderInfoQueryParam
	 * @return java.util.List
	 */
	List<OrderInfo> selectList(OrderInfoQueryParam orderInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param orderId 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.OrderInfo
	 */
	OrderInfo selectById(Serializable orderId);

    /**
     * 新增
     * @param orderInfo orderInfo
     * @return int
     */
	int insert(OrderInfo orderInfo);

    /**
     * 更新（包含null）
     * @param orderInfo orderInfo
     * @return int
     */
    int update(OrderInfo orderInfo);
    /**
     * 更新（不包含null）
     * @param orderInfo orderInfo
     * @return int
     */
    int updateSelective(OrderInfo orderInfo);

    OrderInfo selectByThingId(Long thingId);

	OrderInfo orderInfoByThingId(Long thingId);

	/**
	 * 根据订单编号查询
	 * @param orderSn
	 * @return
	 */
	OrderInfo selectByOrderSn(String orderSn);

	OrderInfo selectByBuyer(Long thingSellId,String buyerId);

	OrderInfo selectBySeller(Long thingId,String sellerId);

	OrderInfo selectByPayOrderId(String tlOrderRequest);

	OrderInfo selectOrderBySeller(Long thingId,String sellerId);
	OrderInfo selectOrderByBuyer(Long thingSellId,String buyerId);
}
