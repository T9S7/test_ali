package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.ArtPicInfo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;


/**
 *ArtPicInfo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface ArtPicInfoMapper {

	/**
     * 列表查询
	 * @param artPicInfoQueryParam artPicInfoQueryParam
	 * @return java.util.List
	 */
	List<ArtPicInfo> selectList(ArtPicInfoQueryParam artPicInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return ArtPicInfo
	 */
	ArtPicInfo selectById(Serializable id);

    /**
     * 新增
     * @param artPicInfo artPicInfo
     * @return int
     */
	int insert(ArtPicInfo artPicInfo);

    /**
     * 更新（包含null）
     * @param artPicInfo artPicInfo
     * @return int
     */
    int update(ArtPicInfo artPicInfo);
    /**
     * 更新（不包含null）
     * @param artPicInfo artPicInfo
     * @return int
     */
    int updateSelective(ArtPicInfo artPicInfo);

    /**
    * 根据艺术品ID物理删除
    * @param artId
    * @return void
    */
    void physicalDelByArtId(Long artId);
}
