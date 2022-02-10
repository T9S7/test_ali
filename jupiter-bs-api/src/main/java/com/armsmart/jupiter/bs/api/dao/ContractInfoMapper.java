package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ContractInfo;
import java.util.List;
import java.io.Serializable;


/**
 *ContractInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ContractInfoMapper {

	/**
     * 列表查询
	 * @param contractInfoQueryParam contractInfoQueryParam
	 * @return java.util.List
	 */
//	List<ContractInfo> selectList(ContractInfoQueryParam contractInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ContractInfo
	 */
	ContractInfo selectById(Serializable id);

    /**
     * 新增
     * @param contractInfo contractInfo
     * @return int
     */
	int insert(ContractInfo contractInfo);

    /**
     * 更新（包含null）
     * @param contractInfo contractInfo
     * @return int
     */
    int update(ContractInfo contractInfo);
    /**
     * 更新（不包含null）
     * @param contractInfo contractInfo
     * @return int
     */
    int updateSelective(ContractInfo contractInfo);

    String nextDealAddr();

    int updateAddr(String nextAddr);
}
