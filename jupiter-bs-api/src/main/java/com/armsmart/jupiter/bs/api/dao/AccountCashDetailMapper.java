package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.AccountCashDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *AccountCashDetail mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface AccountCashDetailMapper {

	/**
     * 列表查询
	 * @param accountCashDetailQueryParam accountCashDetailQueryParam
	 * @return java.util.List
	 */
	List<AccountCashDetail> selectList(AccountCashDetailQueryParam accountCashDetailQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.AccountCashDetail
	 */
	AccountCashDetail selectById(Serializable id);

    /**
     * 新增
     * @param accountCashDetail accountCashDetail
     * @return int
     */
	int insert(AccountCashDetail accountCashDetail);

    /**
     * 更新（包含null）
     * @param accountCashDetail accountCashDetail
     * @return int
     */
    int update(AccountCashDetail accountCashDetail);
    /**
     * 更新（不包含null）
     * @param accountCashDetail accountCashDetail
     * @return int
     */
    int updateSelective(AccountCashDetail accountCashDetail);
}
