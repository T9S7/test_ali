package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.AppVersionQueryParam;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AppVersion;
//import com.armsmart.jupiter.bs.api.dto.request.AppVersionQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 * AppVersion mapper接口
 *
 * @author 苏礼刚
 **/
@Mapper
public interface AppVersionMapper {

    /**
     * 列表查询
     * @param appVersionQueryParam appVersionQueryParam
     * @return java.util.List
     */
	List<AppVersion> selectList(AppVersionQueryParam appVersionQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.entity.AppVersion
     */
    AppVersion selectById(Serializable id);

    /**
     * 新增
     *
     * @param appVersion appVersion
     * @return int
     */
    int insert(AppVersion appVersion);

    /**
     * 更新（包含null）
     *
     * @param appVersion appVersion
     * @return int
     */
    int update(AppVersion appVersion);

    /**
     * 更新（不包含null）
     *
     * @param appVersion appVersion
     * @return int
     */
    int updateSelective(AppVersion appVersion);

    /**
     * 获取APP最新版本
     *
     * @param platform
     * @return com.armsmart.jupiter.bs.api.entity.AppVersion
     * @date 2021/12/27
     */
    AppVersion getLatestVersion(Integer platform);
}
