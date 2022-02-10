package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationCheckParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAuthenticationResult;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;


/**
 *UserAuthentication mapper接口
 * @author wei.lin
 **/
@Mapper
public interface UserAuthenticationMapper {

	/**
     * 列表查询
	 * @param userAuthenticationQueryParam userAuthenticationQueryParam
	 * @return java.util.List
	 */
	List<UserAuthentication> selectList(UserAuthenticationQueryParam userAuthenticationQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return UserAuthentication
	 */
	UserAuthentication selectById(Serializable id);

	UserAuthentication selectByCertNo(String certificateNo);

	UserAuthentication selectByUserId(String userId);

	UserAuthentication selectByMobile(String mobile);

    /**
     * 新增
     * @param userAuthentication userAuthentication
     * @return int
     */
	int insert(UserAuthentication userAuthentication);

    /**
     * 更新（包含null）
     * @param userAuthentication userAuthentication
     * @return int
     */
    int update(UserAuthentication userAuthentication);

	UserAuthenticationCheckParam checkByCertNo(String certificateNo);

	/**
	 * 更新实人认证信息
	 * @param id
	 * @param bizId
	 * @param bizType
	 * @return
	 */
    int updateBiz(int id, String bizId, String bizType);
	/**
	 * 绑定公钥
	 * @param userAuthentication userAuthentication
	 * @return int
	 */
    int bind(UserAuthentication userAuthentication);

    int updateCert(String certifino);

    /**
     * 更新（不包含null）
     * @param userAuthentication userAuthentication
     * @return int
     */
    int updateSelective(UserAuthentication userAuthentication);

    UserAuthenticationResult loginResult(Integer userId);
}
