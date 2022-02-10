package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ArtType;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 *ArtType mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ArtTypeMapper {

	/**
     * 列表查询
	 * @param artTypeQueryParam artTypeQueryParam
	 * @return java.util.List
	 */
	List<ArtType> selectList(ArtTypeQueryParam artTypeQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ArtType
	 */
	ArtType selectById(Serializable id);

	List<ArtType> selectListById(Integer id);

	List<ArtType> selectRolePermission(@Param("Id") Integer id);
    /**
     * 新增
     * @param artType artType
     * @return int
     */
	int insert(ArtType artType);

    /**
     * 更新（包含null）
     * @param artType artType
     * @return int
     */
    int update(ArtType artType);
    /**
     * 更新（不包含null）
     * @param artType artType
     * @return int
     */
    int updateSelective(ArtType artType);
}
