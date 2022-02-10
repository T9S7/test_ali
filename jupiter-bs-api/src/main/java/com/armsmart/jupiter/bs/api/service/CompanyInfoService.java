package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.UserCompanyMapper;
import com.armsmart.jupiter.bs.api.dao.UserInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.CompanyInfoDetail;
import com.armsmart.jupiter.bs.api.dao.CompanyInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.CompanyInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.response.UserCompanyResult;
import com.armsmart.jupiter.bs.api.entity.CompanyInfo;
import com.armsmart.jupiter.bs.api.entity.UserCompany;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

import org.apache.catalina.User;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import static com.armsmart.jupiter.bs.api.error.NEError.*;

/**
 * CompanyInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class CompanyInfoService {

    @Autowired(required = false)
    private  CompanyInfoMapper companyInfoMapper;

    @Autowired(required = false)
    private UserCompanyMapper userCompanyMapper;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<CompanyInfoDetail> selectPage(CompanyInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<CompanyInfo> pageInfo = new PageInfo<>(companyInfoMapper.selectList(query));
        List<CompanyInfoDetail> dtoList = CompanyInfoAssembler.INSTANCE.getCompanyInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  companyInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.CompanyInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(CompanyInfoAddParam companyInfoAddParam){
        CompanyInfo entity = CompanyInfoAssembler.INSTANCE.getCompanyInfo(companyInfoAddParam);
        CompanyInfo companyInfo = companyInfoMapper.selectByName(companyInfoAddParam.getCompanyName().trim());
        if(companyInfo != null){
            return CommonResult.failed(COMPANY_HAVE_EXIST);
        }
        companyInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param companyInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(CompanyInfoUpdateParam companyInfoUpdateParam){
        CompanyInfo entity = CompanyInfoAssembler.INSTANCE.getCompanyInfo(companyInfoUpdateParam);
        companyInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        CompanyInfo entity = new CompanyInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        companyInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.CompanyInfoDetail
     */
    public CommonResult<CompanyInfoDetail> selectById(Integer id) {
        CompanyInfo companyInfo = companyInfoMapper.selectById(id);
        CompanyInfoDetail companyInfoDetail = CompanyInfoAssembler.INSTANCE.getCompanyInfoDetail(companyInfo);
        return CommonResult.success(companyInfoDetail);
    }

//    addUser

    /**
     * 插入数据
     * @param  param
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.CompanyInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addUser(UserCompanyAddParam param){
        UserCompany userCompany = new UserCompany();
        userCompany.setCompanyId(param.getCompanyId());
        userCompany.setCreateTime(System.currentTimeMillis());
        userCompany.setIsCompanyOfficial(param.getIsCompanyOfficial());
        userCompany.setIsDel(false);
        userCompany.setUserId(param.getUserId());
        userCompany.setUserType(param.getUserType());
        CompanyInfo companyInfo = companyInfoMapper.selectById(param.getCompanyId());
        if(companyInfo == null){
            return CommonResult.failed(COMPANY_NOT_EXIST);
        }
        UserCompany userCompany1 = userCompanyMapper.selectByUserId(param.getUserId().longValue());
        if(userCompany1 != null){
            return CommonResult.failed(USER_COMPANY_HAVE_EXIST);
        }

        userCompanyMapper.insert(userCompany);
        return CommonResult.success(userCompany.getId());
    }

    /**
     * 修改是否官方授权
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateState(Long userId){
        UserCompany userCompany = userCompanyMapper.selectByUserId(userId);
        if(userCompany == null){
            return CommonResult.failed(USER_COMPANY_NOT_EXIST);
        }
        if (userCompany.getIsCompanyOfficial()){
            userCompany.setIsCompanyOfficial(false);
        }else {
            userCompany.setIsCompanyOfficial(true);
        }
         return CommonResult.success(userCompanyMapper.updateSelective(userCompany));
    }

    /**
     * 企业下用户列表
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<CommonPage<UserCompanyResult>> selectComUser(CompanyIdQueryParam param){
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<UserCompany> list = userCompanyMapper.selectListByCompanyId(param.getCompanyId());
        List<UserCompanyResult> userCompanyList = new ArrayList<>();
            if(list != null){
                for (int i=0;i<list.size();i++){
                    UserCompanyResult userCompanyResult = new UserCompanyResult();
                    userCompanyResult.setCompanyId(list.get(i).getCompanyId());
                    CompanyInfo companyInfo = companyInfoMapper.selectById(list.get(i).getCompanyId());
                    if(companyInfo == null){
                        continue;
                    }
                    userCompanyResult.setCompanyName(companyInfo.getCompanyName());
                    UserInfo userInfo = userInfoMapper.selectById(list.get(i).getUserId());
                    if(userInfo == null){
                        continue;
                    }
                    userCompanyResult.setUserId(list.get(i).getUserId());
                    userCompanyResult.setNickname(userInfo.getNickname());
                    userCompanyResult.setMobile(userInfo.getMobile());
                    userCompanyResult.setId(list.get(i).getId());
                    userCompanyResult.setUserType(list.get(i).getUserType());
                    userCompanyResult.setIsCompanyOfficial(list.get(i).getIsCompanyOfficial());
                    userCompanyList.add(userCompanyResult);
                }
            }
        PageInfo<UserCompanyResult> pageInfo = new PageInfo<>(userCompanyList);
        List<UserCompanyResult> dtoList = pageInfo.getList();
        return CommonResult.success(CommonPage.restPage(pageInfo, dtoList));
    }



}
