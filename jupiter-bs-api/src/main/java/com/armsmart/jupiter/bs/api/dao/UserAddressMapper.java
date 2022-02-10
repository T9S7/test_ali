package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.UserAddressQueryParam;
import com.armsmart.jupiter.bs.api.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 * UserAddress mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface UserAddressMapper {

    /**
     * 列表查询
     *
     * @param userAddressQueryParam userAddressQueryParam
     * @return java.util.List
     */
    List<UserAddress> selectList(UserAddressQueryParam userAddressQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return UserAddress
     */
    UserAddress selectById(Serializable id);

    /**
     * 新增
     *
     * @param userAddress userAddress
     * @return int
     */
    int insert(UserAddress userAddress);

    /**
     * 更新（包含null）
     *
     * @param userAddress userAddress
     * @return int
     */
    int update(UserAddress userAddress);

    /**
     * 更新（不包含null）
     *
     * @param userAddress userAddress
     * @return int
     */
    int updateSelective(UserAddress userAddress);

    /**
     * 重置用户的默认地址为非默认地址
     *
     * @param userId
     * @return int
     */
    int resetDefault(@Param("userId") Integer userId);

    /**
    * 根据用户id查询默认地址
    * @param userId
    * @return UserAddress
    */
    UserAddress selectMyDefaultAddr(@Param("userId") Integer userId);
}
