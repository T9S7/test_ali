package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserBankInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.UserBankInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.UserBankInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.io.Serializable;

/**
 * UserBankInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class UserBankInfoService {

    @Autowired(required = false)
    private  UserBankInfoMapper userBankInfoMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<UserBankInfoDetail> selectPage(UserBankInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<UserBankInfo> pageInfo = new PageInfo<>(userBankInfoMapper.selectList(query));
        List<UserBankInfoDetail> dtoList = UserBankInfoAssembler.INSTANCE.getUserBankInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 银行卡列表
     * @param
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<UserBankInfoDetail> userBankList(){
        Object ii = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDetails userDetails = (AppUserDetails) ii;
        UserBankInfoQueryParam query  =new UserBankInfoQueryParam();
        query.setIsDel(false);
        query.setUserId(userDetails.getUserInfo().getId().toString());
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<UserBankInfo> pageInfo = new PageInfo<>(userBankInfoMapper.selectList(query));
        List<UserBankInfoDetail> dtoList = UserBankInfoAssembler.INSTANCE.getUserBankInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  userBankInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.UserBankInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(UserBankInfoAddParam userBankInfoAddParam){
        UserBankInfo entity = UserBankInfoAssembler.INSTANCE.getUserBankInfo(userBankInfoAddParam);
        userBankInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param userBankInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(UserBankInfoUpdateParam userBankInfoUpdateParam){
        UserBankInfo entity = UserBankInfoAssembler.INSTANCE.getUserBankInfo(userBankInfoUpdateParam);
        userBankInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        UserBankInfo entity = new UserBankInfo();
        entity.setId(id);
        entity.setIsDel(true);
//        entity.setUpdateTime(System.currentTimeMillis());
        userBankInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Integer> ids){
        if(!CollectionUtils.isEmpty(ids)){
            ids.forEach(id -> {
                deleteById(id);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     * @param id 主键ID
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.dto.response.UserBankInfoDetail
     */
    public CommonResult<UserBankInfoDetail> selectById(Integer id) {
        UserBankInfo userBankInfo = userBankInfoMapper.selectById(id);
        UserBankInfoDetail userBankInfoDetail = UserBankInfoAssembler.INSTANCE.getUserBankInfoDetail(userBankInfo);
        return CommonResult.success(userBankInfoDetail);
    }

}
