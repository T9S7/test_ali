package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.RefundInfo;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *RefundInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface RefundInfoMapper {

	/**
     * 列表查询
	 * @param refundInfoQueryParam refundInfoQueryParam
	 * @return java.util.List
	 */
	List<RefundInfo> selectList(RefundInfoQueryParam refundInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.RefundInfo
	 */
	RefundInfo selectById(Serializable id);

    /**
     * 新增
     * @param refundInfo refundInfo
     * @return int
     */
	int insert(RefundInfo refundInfo);

    /**
     * 更新（包含null）
     * @param refundInfo refundInfo
     * @return int
     */
    int update(RefundInfo refundInfo);
    /**
     * 更新（不包含null）
     * @param refundInfo refundInfo
     * @return int
     */
    int updateSelective(RefundInfo refundInfo);

    RefundInfo selectByOrderId(String orderId);

    Long selectCountVsp(List<Integer> payMatchs);
}
