package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.SysResourceQueryParam;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.io.Serializable;


/**
 *SysResource mapper接口
 * @author wei.lin
 **/
@Mapper
public interface SysResourceMapper {

	/**
     * 列表查询
	 * @param sysResourceQueryParam sysResourceQueryParam
	 * @return java.util.List
	 */
	List<SysResource> selectList(SysResourceQueryParam sysResourceQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return SysResource
	 */
	SysResource selectById(Serializable id);

    /**
     * 新增
     * @param sysResource sysResource
     * @return int
     */
	int insert(SysResource sysResource);

    /**
     * 更新（包含null）
     * @param sysResource sysResource
     * @return int
     */
    int update(SysResource sysResource);
    /**
     * 更新（不包含null）
     * @param sysResource sysResource
     * @return int
     */
    int updateSelective(SysResource sysResource);
}
