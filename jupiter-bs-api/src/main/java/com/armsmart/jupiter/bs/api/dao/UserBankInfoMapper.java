package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoQueryParam;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;
import java.io.Serializable;


/**
 *UserBankInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface UserBankInfoMapper {

	/**
     * 列表查询
	 * @param userBankInfoQueryParam userBankInfoQueryParam
	 * @return java.util.List
	 */
	List<UserBankInfo> selectList(UserBankInfoQueryParam userBankInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.UserBankInfo
	 */
	UserBankInfo selectById(Serializable id);

    /**
     * 新增
     * @param userBankInfo userBankInfo
     * @return int
     */
	int insert(UserBankInfo userBankInfo);

    /**
     * 更新（包含null）
     * @param userBankInfo userBankInfo
     * @return int
     */
    int update(UserBankInfo userBankInfo);
    /**
     * 更新（不包含null）
     * @param userBankInfo userBankInfo
     * @return int
     */
    int updateSelective(UserBankInfo userBankInfo);

    UserBankInfo selectByTrance(String tranceNum);

    UserBankInfo selectByCardNo(String userId,String cardNo);
}
