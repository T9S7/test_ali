package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.ArtInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.ArtInfo;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;


/**
 *ArtInfo mapper接口
 * @author 苏礼刚
 **/
@Mapper
public interface ArtInfoMapper {

	/**
     * 列表查询
	 * @param artInfoQueryParam artInfoQueryParam
	 * @return java.util.List
	 */
	List<ArtInfo> selectList(ArtInfoQueryParam artInfoQueryParam);

	/**
	 * 根据主键查询
	 * @param artId 主键ID
	 * @return com.armsmart.jupiter.bs.api.entity.ArtInfo
	 */
	ArtInfo selectById(Serializable artId);

    /**
     * 根据合约地址查询
     *
     * @param contractAddr
     * @return ArtInfo
     */
    ArtInfo selectByContractAddr(String contractAddr);

    /**
     * 新增
     * @param artInfo artInfo
     * @return int
     */
	int insert(ArtInfo artInfo);

    /**
     * 更新（包含null）
     * @param artInfo artInfo
     * @return int
     */
    int update(ArtInfo artInfo);
    /**
     * 更新（不包含null）
     * @param artInfo artInfo
     * @return int
     */
    int updateSelective(ArtInfo artInfo);

    /**
    * 根据主键物理删除
    * @param artId
    * @return void
    */
    void physicalDel(Serializable artId);
}
