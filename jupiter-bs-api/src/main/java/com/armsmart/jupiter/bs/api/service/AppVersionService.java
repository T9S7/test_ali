package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.AppVersionAssembler;
import com.armsmart.jupiter.bs.api.constants.PlatformType;
import com.armsmart.jupiter.bs.api.dao.AppVersionMapper;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppVersionDetail;
import com.armsmart.jupiter.bs.api.entity.AppVersion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * AppVersion service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class AppVersionService {

    @Autowired(required = false)
    private AppVersionMapper appVersionMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public PageInfo<AppVersionDetail> selectPage(AppVersionQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        query.setIsDel(false);
        List<AppVersion> list = appVersionMapper.selectList(query);
        List<AppVersionDetail> dtoList = AppVersionAssembler.INSTANCE.getAppVersionDetailList(list);
        return new PageInfo<>(dtoList);
    }

    /**
     * 插入数据
     *
     * @param appVersionAddParam
     * @return com.armsmart.jupiter.bs.api.entity.AppVersion
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(AppVersionAddParam appVersionAddParam) {
        AppVersion entity = AppVersionAssembler.INSTANCE.getAppVersion(appVersionAddParam);
        PlatformType platformType = PlatformType.getByCode(entity.getPlatform());
        if (Objects.equals(PlatformType.android, platformType)) {
            if (!StringUtils.hasText(entity.getDownloadUrl())) {
                return CommonResult.failed("发布安卓平台升级包时，程序下载地址为必填项");
            }
        }
        appVersionMapper.insert(entity);
        return CommonResult.success(entity.getId());
    }

    /**
     * 修改数据
     *
     * @param appVersionUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(AppVersionUpdateParam appVersionUpdateParam) {
        AppVersion entity = AppVersionAssembler.INSTANCE.getAppVersion(appVersionUpdateParam);
        appVersionMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(java.lang.Integer id) {
        AppVersion entity = new AppVersion();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        appVersionMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
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
     * @return com.armsmart.jupiter.bs.api.dto.response.AppVersionDetail
     * @date 2020/01/01
     */
    public AppVersionDetail selectById(java.lang.Integer id) {
        AppVersion appVersion = appVersionMapper.selectById(id);
        AppVersionDetail appVersionDetail = AppVersionAssembler.INSTANCE.getAppVersionDetail(appVersion);
        return appVersionDetail;
    }


    public CommonResult<AppVersionDetail> getLatestVersion(Integer platform) {
        AppVersion appVersion = appVersionMapper.getLatestVersion(platform);
        AppVersionDetail appVersionDetail = AppVersionAssembler.INSTANCE.getAppVersionDetail(appVersion);
        return CommonResult.success(appVersionDetail);
    }


}
