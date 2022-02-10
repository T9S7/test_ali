package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AppTutorialVideo;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *AppTutorialVideo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface AppTutorialVideoMapper {

	/**
     * 列表查询
	 * @param appTutorialVideoQueryParam appTutorialVideoQueryParam
	 * @return java.util.List
	 */
	List<AppTutorialVideo> selectList(AppTutorialVideoQueryParam appTutorialVideoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.AppTutorialVideo
	 */
	AppTutorialVideo selectById(Serializable id);

    /**
     * 新增
     * @param appTutorialVideo appTutorialVideo
     * @return int
     */
	int insert(AppTutorialVideo appTutorialVideo);

    /**
     * 更新（包含null）
     * @param appTutorialVideo appTutorialVideo
     * @return int
     */
    int update(AppTutorialVideo appTutorialVideo);
    /**
     * 更新（不包含null）
     * @param appTutorialVideo appTutorialVideo
     * @return int
     */
    int updateSelective(AppTutorialVideo appTutorialVideo);
}
