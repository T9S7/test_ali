package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.WechatOrderInfo;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *WechatOrderInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface WechatOrderInfoMapper {

	/**
     * 列表查询
	 * @param wechatOrderInfoQueryParam wechatOrderInfoQueryParam
	 * @return java.util.List
	 */
	List<WechatOrderInfo> selectList(WechatOrderInfoQueryParam wechatOrderInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.WechatOrderInfo
	 */
	WechatOrderInfo selectById(Serializable id);

    /**
     * 新增
     * @param wechatOrderInfo wechatOrderInfo
     * @return int
     */
	int insert(WechatOrderInfo wechatOrderInfo);

    /**
     * 更新（包含null）
     * @param wechatOrderInfo wechatOrderInfo
     * @return int
     */
    int update(WechatOrderInfo wechatOrderInfo);
    /**
     * 更新（不包含null）
     * @param wechatOrderInfo wechatOrderInfo
     * @return int
     */
    int updateSelective(WechatOrderInfo wechatOrderInfo);

	/**
	 * 根据微信订单编号查询
	 * @param transactionId
	 * @return
	 */
	WechatOrderInfo selectByTransactionId(String transactionId);

	/**
	 * 根据商户订单编号查询
	 * @param outTradeNo
	 * @return
	 */
	WechatOrderInfo selectByOutTradeNo(String outTradeNo);
}
