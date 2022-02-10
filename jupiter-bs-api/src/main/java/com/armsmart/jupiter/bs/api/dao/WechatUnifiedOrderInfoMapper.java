package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *WechatUnifiedOrderInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface WechatUnifiedOrderInfoMapper {

	/**
     * 列表查询
	 * @param wechatUnifiedOrderInfoQueryParam wechatUnifiedOrderInfoQueryParam
	 * @return java.util.List
	 */
	List<WechatUnifiedOrderInfo> selectList(WechatUnifiedOrderInfoQueryParam wechatUnifiedOrderInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo
	 */
	WechatUnifiedOrderInfo selectById(Serializable id);

    /**
     * 新增
     * @param wechatUnifiedOrderInfo wechatUnifiedOrderInfo
     * @return int
     */
	int insert(WechatUnifiedOrderInfo wechatUnifiedOrderInfo);

    /**
     * 更新（包含null）
     * @param wechatUnifiedOrderInfo wechatUnifiedOrderInfo
     * @return int
     */
    int update(WechatUnifiedOrderInfo wechatUnifiedOrderInfo);
    /**
     * 更新（不包含null）
     * @param wechatUnifiedOrderInfo wechatUnifiedOrderInfo
     * @return int
     */
    int updateSelective(WechatUnifiedOrderInfo wechatUnifiedOrderInfo);
}
