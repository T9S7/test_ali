package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserAuthQueryParam;
import com.armsmart.jupiter.bs.api.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 * UserAuth mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface UserAuthMapper {

    /**
     * 列表查询
     *
     * @param userAuthQueryParam userAuthQueryParam
     * @return java.util.List
     */
    List<UserAuth> selectList(UserAuthQueryParam userAuthQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return UserAuth
     */
    UserAuth selectById(Serializable id);

    /**
     * 新增
     *
     * @param userAuth userAuth
     * @return int
     */
    int insert(UserAuth userAuth);

    /**
     * 更新（包含null）
     *
     * @param userAuth userAuth
     * @return int
     */
    int update(UserAuth userAuth);

    /**
     * 更新（不包含null）
     *
     * @param userAuth userAuth
     * @return int
     */
    int updateSelective(UserAuth userAuth);

    UserAuth selectByIdentifier(@Param("identifier") String identifier);

    UserAuth selectByUserId(@Param("userId") Integer userId);

    int delByUserId(Integer userId);
}
