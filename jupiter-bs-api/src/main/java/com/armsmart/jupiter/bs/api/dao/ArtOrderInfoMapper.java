package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.ArtOrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.ArtOrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;


/**
 *ArtOrderInfo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface ArtOrderInfoMapper {

	/**
     * 列表查询
	 * @param artOrderInfoQueryParam artOrderInfoQueryParam
	 * @return java.util.List
	 */
	List<ArtOrderInfo> selectList(ArtOrderInfoQueryParam artOrderInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param orderId 主键ID
	 * @return ArtOrderInfo
	 */
	ArtOrderInfo selectById(Serializable orderId);

    /**
     * 新增
     * @param artOrderInfo artOrderInfo
     * @return int
     */
	int insert(ArtOrderInfo artOrderInfo);

    /**
     * 更新（包含null）
     * @param artOrderInfo artOrderInfo
     * @return int
     */
    int update(ArtOrderInfo artOrderInfo);
    /**
     * 更新（不包含null）
     * @param artOrderInfo artOrderInfo
     * @return int
     */
    int updateSelective(ArtOrderInfo artOrderInfo);

	/**
	 * 根据艺术品ID物理删除
	 * @param artId
	 * @return void
	 */
	void physicalDelByArtId(Long artId);
}
