package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *UserCollect mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface UserCollectMapper {

	/**
     * 列表查询
	 * @param userCollectQueryParam userCollectQueryParam
	 * @return java.util.List
	 */
	List<UserCollect> selectList(UserCollectQueryParam userCollectQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.UserCollect
	 */
	UserCollect selectById(Serializable id);

    /**
     * 新增
     * @param userCollect userCollect
     * @return int
     */
	int insert(UserCollect userCollect);

    /**
     * 更新（包含null）
     * @param userCollect userCollect
     * @return int
     */
    int update(UserCollect userCollect);
    /**
     * 更新（不包含null）
     * @param userCollect userCollect
     * @return int
     */
    int updateSelective(UserCollect userCollect);

    UserCollect selectInfo(UserCollectAddParam userCollectAddParam);

    Integer countByUserId(Long userId);
}
