package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.CheckListDetail;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.CheckInfo;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoQueryParam;
import java.util.List;
import java.io.Serializable;


/**
 *CheckInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface CheckInfoMapper {

	/**
     * 列表查询
	 * @param checkInfoQueryParam checkInfoQueryParam
	 * @return java.util.List
	 */
//	List<CheckInfoDetail> selectList(CheckInfoQueryParam checkInfoQueryParam);
	List<CheckInfo> selectList(CheckInfoQueryParam checkInfoQueryParam);
	/**
	 * 根据主键查询
	 * @param id 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.CheckInfo
	 */
	CheckInfo selectById(Serializable id);

    /**
     * 新增
     * @param checkInfo checkInfo
     * @return int
     */
	int insert(CheckInfo checkInfo);

    /**
     * 更新（包含null）
     * @param checkInfo checkInfo
     * @return int
     */
    int update(CheckInfo checkInfo);
    /**
     * 更新（不包含null）
     * @param checkInfo checkInfo
     * @return int
     */
    int updateSelective(CheckInfo checkInfo);
}
