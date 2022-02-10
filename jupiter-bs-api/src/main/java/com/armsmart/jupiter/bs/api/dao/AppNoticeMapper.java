package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AppNotice;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeQueryParam;

import java.util.List;
import java.io.Serializable;


/**
 * AppNotice mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface AppNoticeMapper {

    /**
     * 列表查询
     *
     * @param appNoticeQueryParam appNoticeQueryParam
     * @return java.util.List
     */
    List<AppNotice> selectList(AppNoticeQueryParam appNoticeQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.entity.AppNotice
     */
    AppNotice selectById(Serializable id);

    /**
     * 新增
     *
     * @param appNotice appNotice
     * @return int
     */
    int insert(AppNotice appNotice);

    /**
     * 更新（包含null）
     *
     * @param appNotice appNotice
     * @return int
     */
    int update(AppNotice appNotice);

    /**
     * 更新（不包含null）
     *
     * @param appNotice appNotice
     * @return int
     */
    int updateSelective(AppNotice appNotice);

    /**
     * 重置启用状态
     *
     * @return int
     * @date 2021/12/27
     */
    int resetEnabled();
}
