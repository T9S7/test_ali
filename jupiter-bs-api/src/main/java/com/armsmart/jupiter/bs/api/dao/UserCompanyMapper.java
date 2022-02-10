package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.UserCompany;
//import com.armsmart.jupiter.bs.api.dto.request.UserCompanyQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *UserCompany mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface UserCompanyMapper {

	/**
     * 列表查询
	 * @param userCompanyQueryParam userCompanyQueryParam
	 * @return java.util.List
	 */
//	List<UserCompany> selectList(UserCompanyQueryParam userCompanyQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.UserCompany
	 */
	UserCompany selectById(Serializable id);

    /**
     * 新增
     * @param userCompany userCompany
     * @return int
     */
	int insert(UserCompany userCompany);

    /**
     * 更新（包含null）
     * @param userCompany userCompany
     * @return int
     */
    int update(UserCompany userCompany);
    /**
     * 更新（不包含null）
     * @param userCompany userCompany
     * @return int
     */
    int updateSelective(UserCompany userCompany);

	UserCompany selectByUserId(Long userId);

	List<UserCompany> selectListByCompanyId(Integer companyId);
}
