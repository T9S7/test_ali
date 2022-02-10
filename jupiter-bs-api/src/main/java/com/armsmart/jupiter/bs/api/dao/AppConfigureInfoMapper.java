package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AppConfigureInfo;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *AppConfigureInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface AppConfigureInfoMapper {

	/**
     * 列表查询
	 * @param appConfigureInfoQueryParam appConfigureInfoQueryParam
	 * @return java.util.List
	 */
	List<AppConfigureInfo> selectList(AppConfigureInfoQueryParam appConfigureInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.AppConfigureInfo
	 */
	AppConfigureInfo selectById(Serializable id);

    /**
     * 新增
     * @param appConfigureInfo appConfigureInfo
     * @return int
     */
	int insert(AppConfigureInfo appConfigureInfo);

    /**
     * 更新（包含null）
     * @param appConfigureInfo appConfigureInfo
     * @return int
     */
    int update(AppConfigureInfo appConfigureInfo);
    /**
     * 更新（不包含null）
     * @param appConfigureInfo appConfigureInfo
     * @return int
     */
    int updateSelective(AppConfigureInfo appConfigureInfo);
}
