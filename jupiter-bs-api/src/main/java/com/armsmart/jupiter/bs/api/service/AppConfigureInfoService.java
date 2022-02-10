package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppConfigureInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.AppConfigureInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.AppConfigureInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.AppConfigureInfo;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import java.io.Serializable;

/**
 * AppConfigureInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class AppConfigureInfoService {

    @Autowired(required = false)
    private  AppConfigureInfoMapper appConfigureInfoMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<AppConfigureInfoDetail> selectPage(AppConfigureInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<AppConfigureInfo> pageInfo = new PageInfo<>(appConfigureInfoMapper.selectList(query));
        List<AppConfigureInfoDetail> dtoList = AppConfigureInfoAssembler.INSTANCE.getAppConfigureInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  appConfigureInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.AppConfigureInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(AppConfigureInfoAddParam appConfigureInfoAddParam){
        AppConfigureInfo entity = AppConfigureInfoAssembler.INSTANCE.getAppConfigureInfo(appConfigureInfoAddParam);
        appConfigureInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param appConfigureInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(AppConfigureInfoUpdateParam appConfigureInfoUpdateParam){
        AppConfigureInfo entity = AppConfigureInfoAssembler.INSTANCE.getAppConfigureInfo(appConfigureInfoUpdateParam);
        appConfigureInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        AppConfigureInfo entity = new AppConfigureInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        appConfigureInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.AppConfigureInfoDetail
     */
    public CommonResult<AppConfigureInfoDetail> selectById(Integer id) {
        AppConfigureInfo appConfigureInfo = appConfigureInfoMapper.selectById(id);
        AppConfigureInfoDetail appConfigureInfoDetail = AppConfigureInfoAssembler.INSTANCE.getAppConfigureInfoDetail(appConfigureInfo);
        return CommonResult.success(appConfigureInfoDetail);
    }

}
