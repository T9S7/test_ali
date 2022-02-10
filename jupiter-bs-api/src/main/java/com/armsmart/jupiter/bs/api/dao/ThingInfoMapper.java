package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.ThingSellTypeQuery;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoBidDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoListDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingSellReturnDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo;
import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoQueryParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 * ThingInfo mapper接口
 *
 * @author 苏礼刚
 **/
@Mapper
public interface ThingInfoMapper {

    /**
     * 列表查询
     *
     * @param thingInfoQueryParam thingInfoQueryParam
     * @return java.util.List
     */
    List<ThingInfo> selectList(ThingInfoQueryParam thingInfoQueryParam);

    /**
     * 根据状态查询
     *
     * @param userId
     * @param list
     * @return java.util.List<com.armsmart.jupiter.bs.api.entity.ThingInfo>
     */
    List<ThingInfo> selectListByStates(@Param("userId") Integer userId, @Param("list") List<Integer> list);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.entity.ThingInfo
     */
    ThingInfo selectById(Serializable id);

    /**
     * 根据合约地址查询
     *
     * @param contractAddr
     * @return ArtInfo
     */
    ThingInfo selectByContractAddr(String contractAddr);

    ThingInfo selectByContract(String contractAddr);

    /**
     * 新增
     *
     * @param thingInfo thingInfo
     * @return int
     */
    int insert(ThingInfo thingInfo);

    /**
     * 更新（包含null）
     *
     * @param thingInfo thingInfo
     * @return int
     */
    int update(ThingInfo thingInfo);

    /**
     * 更新（不包含null）
     *
     * @param thingInfo thingInfo
     * @return int
     */
    int updateSelective(ThingInfo thingInfo);

    List<ThingInfo> selectListByUserId(Long userId);

    List<ThingInfoListDetail> selectListByOther(Long userId);

    List<ThingInfo> selectListPage(ThingSellTypeQuery thingSellTypeQuery);

    List<ThingSellReturnDetail> selectSellList(Long userId);

    List<ThingInfo> selectUploadList(Long userId);

    List<ThingInfoBidDetail> selectBidList(Long userId);

    /**
     * 用户已售物品列表
     *
     * @param userId
     * @return java.util.List<com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo>
     * @date 2021/12/21
     */
    List<ThingShortInfo> selectSoldList(@Param("userId") Long userId);


}
