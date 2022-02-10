package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.common.api.CommonResult;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.CompanyInfo;
import com.armsmart.jupiter.bs.api.dto.request.CompanyInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *CompanyInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface CompanyInfoMapper {

	/**
     * 列表查询
	 * @param companyInfoQueryParam companyInfoQueryParam
	 * @return java.util.List
	 */
	List<CompanyInfo> selectList(CompanyInfoQueryParam companyInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.CompanyInfo
	 */
	CompanyInfo selectById(Serializable id);

    /**
     * 新增
     * @param companyInfo companyInfo
     * @return int
     */
	int insert(CompanyInfo companyInfo);

    /**
     * 更新（包含null）
     * @param companyInfo companyInfo
     * @return int
     */
    int update(CompanyInfo companyInfo);
    /**
     * 更新（不包含null）
     * @param companyInfo companyInfo
     * @return int
     */
    int updateSelective(CompanyInfo companyInfo);

    CompanyInfo selectByName(String companyName);
}
