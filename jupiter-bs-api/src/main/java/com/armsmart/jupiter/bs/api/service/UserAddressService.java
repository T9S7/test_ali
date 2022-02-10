package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.dao.UserAddressMapper;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressMyQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAddressDetail;
import com.armsmart.jupiter.bs.api.entity.UserAddress;
import com.armsmart.jupiter.bs.api.error.BusinessError;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.UserAddressAssembler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * UserAddress service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class UserAddressService {

    @Autowired(required = false)
    private UserAddressMapper userAddressMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<UserAddressDetail> selectPage(UserAddressQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserAddress> pageInfo = new PageInfo<>(userAddressMapper.selectList(query));
        List<UserAddressDetail> dtoList = UserAddressAssembler.INSTANCE.getUserAddressDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 根据用户id查询默认地址
     *
     * @param userId
     * @return UserAddressDetail
     */
    public CommonResult<UserAddressDetail> myDefaultAddress(Integer userId) {
        UserAddress userAddress = userAddressMapper.selectMyDefaultAddr(userId);
        if (null == userAddress) {
            return CommonResult.failed(BusinessError.USER_NOT_SET_DEFAULT_ADDR);
        }
        UserAddressDetail userAddressDetail = UserAddressAssembler.INSTANCE.getUserAddressDetail(userAddress);
        return CommonResult.success(userAddressDetail);
    }

    /**
     * 我的地址列表
     *
     * @param param
     * @return java.util.List<UserAddressDetail>
     */
    public List<UserAddressDetail> myAddress(UserAddressMyQueryParam param) {
        UserAddressQueryParam query = UserAddressAssembler.INSTANCE.getUserAddressQueryParam(param);
        List<UserAddress> list = userAddressMapper.selectList(query);
        List<UserAddressDetail> dtoList = UserAddressAssembler.INSTANCE.getUserAddressDetailList(list);
        return dtoList;
    }


    /**
     * 插入数据
     *
     * @param userAddressAddParam
     * @return UserAddress
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(UserAddressAddParam userAddressAddParam) {
        UserAddress entity = UserAddressAssembler.INSTANCE.getUserAddress(userAddressAddParam);
        if (entity.getIsDefault()) {
            userAddressMapper.resetDefault(entity.getUserId());
        }
        entity.setAddressType(0);
        userAddressMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改数据
     *
     * @param userAddressUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAddressUpdateParam userAddressUpdateParam) {
        UserAddress entity = UserAddressAssembler.INSTANCE.getUserAddress(userAddressUpdateParam);
        entity.setAddressType(0);
        if (entity.getIsDefault()) {
            userAddressMapper.resetDefault(entity.getUserId());
        }
        userAddressMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        UserAddress entity = new UserAddress();
        entity.setId(id);
        entity.setIsDel(true);
        userAddressMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Integer> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return UserAddressDetail
     * @date 2021/03/01
     */
    public UserAddressDetail selectById(Integer id) {
        UserAddress userAddress = userAddressMapper.selectById(id);
        UserAddressDetail userAddressDetail = UserAddressAssembler.INSTANCE.getUserAddressDetail(userAddress);
        return userAddressDetail;
    }

}
