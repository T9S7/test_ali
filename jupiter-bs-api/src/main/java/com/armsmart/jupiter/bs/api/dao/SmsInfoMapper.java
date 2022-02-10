package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.SmsInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * SmsInfo mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface SmsInfoMapper {

    /**
     * 列表查询
     *
     * @param smsInfoQueryParam smsInfoQueryParam
     * @return java.util.List
     */
    List<SmsInfo> selectList(SmsInfoQueryParam smsInfoQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return SmsInfo
     */
    SmsInfo selectById(Serializable id);

    /**
     * 新增
     *
     * @param smsInfo smsInfo
     * @return int
     */
    int insert(SmsInfo smsInfo);

    /**
     * 更新（包含null）
     *
     * @param smsInfo smsInfo
     * @return int
     */
    int update(SmsInfo smsInfo);

    /**
     * 更新（不包含null）
     *
     * @param smsInfo smsInfo
     * @return int
     */
    int updateSelective(SmsInfo smsInfo);

    /**
     * 查询最后一条短信记录
     *
     * @param mobile
     * @return SmsInfo
     */
    SmsInfo selectLatest(@Param("mobile") String mobile, @Param("smsType") Integer smsType);
}
