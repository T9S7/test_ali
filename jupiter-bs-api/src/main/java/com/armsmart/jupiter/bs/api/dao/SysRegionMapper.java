package com.armsmart.jupiter.bs.api.dao;


import com.armsmart.jupiter.bs.api.dto.request.SysRegionQueryParam;
import com.armsmart.jupiter.bs.api.entity.SysRegion;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;


/**
 *SysRegion mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface SysRegionMapper {

	/**
     * 列表查询
	 * @param sysRegionQueryParam sysRegionQueryParam
	 * @return java.util.List
	 */
	List<SysRegion> selectList(SysRegionQueryParam sysRegionQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.abas.app.entity.SysRegion
	 */
	SysRegion selectById(Serializable id);

	/**
	 * 根据主键查询
	 * @param regionParent 父节点ID
	 * @return java.util.List
	 */
	List<SysRegion>  selectByParent(Integer regionParent);

    /**
     * 新增
     * @param sysRegion sysRegion
     * @return int
     */
	int insert(SysRegion sysRegion);

    /**
     * 更新（包含null）
     * @param sysRegion sysRegion
     * @return int
     */
    int update(SysRegion sysRegion);
    /**
     * 更新（不包含null）
     * @param sysRegion sysRegion
     * @return int
     */
    int updateSelective(SysRegion sysRegion);
}
