package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.io.Serializable;


/**
 *UserInfo mapper接口
 * @author wei.lin
 **/
@Mapper
public interface UserInfoMapper {

	/**
     * 列表查询
	 * @param userInfoQueryParam userInfoQueryParam
	 * @return java.util.List
	 */
	List<UserInfo> selectList(UserInfoQueryParam userInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return UserInfo
	 */
	UserInfo selectById(Serializable id);
	/**
	* 根据主键查询用户信息（含已注销用户）
	* @param id
	* @return com.armsmart.jupiter.bs.api.entity.UserInfo
	*/
	UserInfo selectByIdIncludeDel(Serializable id);

    /**
     * 新增
     * @param userInfo userInfo
     * @return int
     */
	int insert(UserInfo userInfo);

    /**
     * 更新（包含null）
     * @param userInfo userInfo
     * @return int
     */
    int update(UserInfo userInfo);
    /**
     * 更新（不包含null）
     * @param userInfo userInfo
     * @return int
     */
    int updateSelective(UserInfo userInfo);

    UserInfo selectByMobile(String mobile);


}
