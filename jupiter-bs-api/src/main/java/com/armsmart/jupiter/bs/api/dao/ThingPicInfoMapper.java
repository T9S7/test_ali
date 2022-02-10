package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 *ThingPicInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ThingPicInfoMapper {

	/**
     * 列表查询
	 * @param thingPicInfoQueryParam thingPicInfoQueryParam
	 * @return java.util.List
	 */
	List<ThingPicInfo> selectList(ThingPicInfoQueryParam thingPicInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ThingPicInfo
	 */
	ThingPicInfo selectById(Serializable id);

    /**
     * 新增
     * @param thingPicInfo thingPicInfo
     * @return int
     */
	int insert(ThingPicInfo thingPicInfo);

    /**
     * 更新（包含null）
     * @param thingPicInfo thingPicInfo
     * @return int
     */
    int update(ThingPicInfo thingPicInfo);
    /**
     * 更新（不包含null）
     * @param thingPicInfo thingPicInfo
     * @return int
     */
    int updateSelective(ThingPicInfo thingPicInfo);
    
    int deleteByThingId(@Param("thingId") Long thingId);
}
