package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.BidInfoQueryParam;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * BidInfo mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface BidInfoMapper {

    /**
     * 列表查询
     *
     * @param bidInfoQueryParam bidInfoQueryParam
     * @return java.util.List
     */
    List<BidInfo> selectList(BidInfoQueryParam bidInfoQueryParam);

    /**
     * 根据主键查询
     *
     * @param bidId 主键ID
     * @return BidInfo
     */
    BidInfo selectById(Serializable bidId);

    /**
     * 新增
     *
     * @param bidInfo bidInfo
     * @return int
     */
    int insert(BidInfo bidInfo);

    /**
     * 更新（包含null）
     *
     * @param bidInfo bidInfo
     * @return int
     */
    int update(BidInfo bidInfo);

    /**
     * 更新（不包含null）
     *
     * @param bidInfo bidInfo
     * @return int
     */
    int updateSelective(BidInfo bidInfo);

    /**
     * 查询当前最高出价
     *
     * @param sellId
     * @return com.armsmart.jupiter.bs.api.entity.BidInfo
     */
    BidInfo selectMaxBid(Long sellId);

    List<BidInfo> selectTopBid(Long sellId);

    /**
     * 根据用户ID、拍卖ID查询一条
     *
     * @param userId
     * @param sellId
     * @return com.armsmart.jupiter.bs.api.entity.BidInfo
     */
    BidInfo selectOne(@Param("userId") Integer userId, @Param("sellId") Long sellId);

    List<Long> selectSellList(Long userId);

    /**
     * 根据拍卖ID更新状态(排除userId)
     *
     * @param bidStates
     * @param sellId
     * @param userId
     * @return int
     */
    int updateBidStateBySellId(@Param("bidStates") Integer bidStates, @Param("sellId") Long sellId, @Param("userId") Integer userId);

    int deleteBySellIdAndUserId(@Param("sellId") Long sellId, @Param("userId") Integer userId);

}
