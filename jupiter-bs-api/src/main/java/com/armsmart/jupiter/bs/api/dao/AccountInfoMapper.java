package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AccountInfo;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *AccountInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface AccountInfoMapper {

	/**
     * 列表查询
	 * @param accountInfoQueryParam accountInfoQueryParam
	 * @return java.util.List
	 */
	List<AccountInfo> selectList(AccountInfoQueryParam accountInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param accountId 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.AccountInfo
	 */
	AccountInfo selectById(Serializable accountId);

	AccountInfo selectByUserId(Integer userId);
    /**
     * 新增
     * @param accountInfo accountInfo
     * @return int
     */
	int insert(AccountInfo accountInfo);

    /**
     * 更新（包含null）
     * @param accountInfo accountInfo
     * @return int
     */
    int update(AccountInfo accountInfo);
    /**
     * 更新（不包含null）
     * @param accountInfo accountInfo
     * @return int
     */
    int updateSelective(AccountInfo accountInfo);


}
