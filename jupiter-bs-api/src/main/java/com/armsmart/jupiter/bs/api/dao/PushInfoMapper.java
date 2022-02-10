package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.PushInfo;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *PushInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface PushInfoMapper {

	/**
     * 列表查询
	 * @param pushInfoQueryParam pushInfoQueryParam
	 * @return java.util.List
	 */
	List<PushInfo> selectList(PushInfoQueryParam pushInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.PushInfo
	 */
	PushInfo selectById(Serializable id);

    /**
     * 新增
     * @param pushInfo pushInfo
     * @return int
     */
	int insert(PushInfo pushInfo);

    /**
     * 更新（包含null）
     * @param pushInfo pushInfo
     * @return int
     */
    int update(PushInfo pushInfo);
    /**
     * 更新（不包含null）
     * @param pushInfo pushInfo
     * @return int
     */
    int updateSelective(PushInfo pushInfo);

	PushInfo getPushInfo(Long userId);

	/**
	 * 根据推送内容查询推送信息id
	 */
	PushInfo selectPushInfo(Long userId);
}
