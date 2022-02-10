package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *ThingSellInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ThingSellInfoMapper {

	/**
     * 列表查询
	 * @param thingSellInfoQueryParam thingSellInfoQueryParam
	 * @return java.util.List
	 */
	List<ThingSellInfo> selectList(ThingSellInfoQueryParam thingSellInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ThingSellInfo
	 */
	ThingSellInfo selectById(Serializable id);

    /**
     * 新增
     * @param thingSellInfo thingSellInfo
     * @return int
     */
	int insert(ThingSellInfo thingSellInfo);

    /**
     * 更新（包含null）
     * @param thingSellInfo thingSellInfo
     * @return int
     */
    int update(ThingSellInfo thingSellInfo);
    /**
     * 更新（不包含null）
     * @param thingSellInfo thingSellInfo
     * @return int
     */
    int updateSelective(ThingSellInfo thingSellInfo);

    ThingSellInfo selectByThingId(Long thingId);

    int thingPutOff(Long thingId);

	int thingPutOn(Long thingId);

	Integer putOnCount(Long userId);
}
