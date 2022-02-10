package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ThingDealInfo;
//import com.armsmart.jupiter.bs.api.dto.request.ThingDealInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *ThingDealInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ThingDealInfoMapper {

	/**
     * 列表查询
	 * @param thingDealInfoQueryParam thingDealInfoQueryParam
	 * @return java.util.List
	 */
//	List<ThingDealInfo> selectList(ThingDealInfoQueryParam thingDealInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ThingDealInfo
	 */
	ThingDealInfo selectById(Serializable id);

    /**
     * 新增
     * @param thingDealInfo thingDealInfo
     * @return int
     */
	int insert(ThingDealInfo thingDealInfo);

    /**
     * 更新（包含null）
     * @param thingDealInfo thingDealInfo
     * @return int
     */
    int update(ThingDealInfo thingDealInfo);
    /**
     * 更新（不包含null）
     * @param thingDealInfo thingDealInfo
     * @return int
     */
    int updateSelective(ThingDealInfo thingDealInfo);

    ThingDealInfo getDealInfo(Long userId); // 赠送
	ThingDealInfo getInfoByThingId(Long userId);
	ThingDealInfo selectDealInfo(String contractAddr);
}
