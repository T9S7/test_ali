package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserFansAddParam;
import com.armsmart.jupiter.bs.api.dto.response.UserFansCountDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansListDetail;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.UserFans;
import com.armsmart.jupiter.bs.api.dto.request.UserFansQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *UserFans mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface UserFansMapper {

	/**
     * 列表查询
	 * @param userFansQueryParam userFansQueryParam
	 * @return java.util.List
	 */
	List<UserFans> selectList(UserFansQueryParam userFansQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.UserFans
	 */
	UserFans selectById(Serializable id);

    /**
     * 新增
     * @param userFans userFans
     * @return int
     */
	int insert(UserFans userFans);

    /**
     * 更新（包含null）
     * @param userFans userFans
     * @return int
     */
    int update(UserFans userFans);
    /**
     * 更新（不包含null）
     * @param userFans userFans
     * @return int
     */
    int updateSelective(UserFans userFans);


    UserFansCountDetail selectCount(Long userId);

	int exiesUserId(UserFansAddParam userFansAddParam);

    int changeState(UserFansAddParam userFansAddParam);
	int returnState(UserFansAddParam userFansAddParam);

	List<UserFansListDetail> selectFocusList(Long userId);
	List<UserFansListDetail> selectFansList(Long userId);

	UserFans selectFans(UserFansAddParam userFansAddParam);
}
