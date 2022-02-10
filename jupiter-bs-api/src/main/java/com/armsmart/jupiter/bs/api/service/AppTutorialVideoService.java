package com.armsmart.jupiter.bs.api.service;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.AppTutorialVideoAssembler;
import com.armsmart.jupiter.bs.api.dao.AppTutorialVideoMapper;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppTutorialVideoDetail;
import com.armsmart.jupiter.bs.api.entity.AppTutorialVideo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * AppTutorialVideo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class AppTutorialVideoService {

    @Autowired
    private AppTutorialVideoMapper appTutorialVideoMapper;
    @Autowired
    private AliOssService aliOssService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public PageInfo<AppTutorialVideoDetail> selectPage(AppTutorialVideoQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        query.setIsDel(false);
        List<AppTutorialVideo> list = appTutorialVideoMapper.selectList(query);
        List<AppTutorialVideoDetail> dtoList = AppTutorialVideoAssembler.INSTANCE.getAppTutorialVideoDetailList(list);
        return new PageInfo<>(dtoList);
    }

    /**
     * 插入数据
     *
     * @param appTutorialVideoAddParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(AppTutorialVideoAddParam appTutorialVideoAddParam) {
        AppTutorialVideo entity = AppTutorialVideoAssembler.INSTANCE.getAppTutorialVideo(appTutorialVideoAddParam);
        AppTutorialVideoDetail appTutorialVideoDetail = selectOne();
        //未配置教程视频
        if (null == appTutorialVideoDetail) {
            appTutorialVideoMapper.insert(entity);
            return CommonResult.success(entity.getId());
        }
        return CommonResult.failed("教程视频已存在");
    }

    /**
     * 修改数据
     *
     * @param appTutorialVideoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(AppTutorialVideoUpdateParam appTutorialVideoUpdateParam) {
        AppTutorialVideo entity = AppTutorialVideoAssembler.INSTANCE.getAppTutorialVideo(appTutorialVideoUpdateParam);
        appTutorialVideoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        AppTutorialVideo entity = new AppTutorialVideo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        appTutorialVideoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.AppTutorialVideoDetail
     * @date 2020/01/01
     */
    public AppTutorialVideoDetail selectById(Integer id) {
        AppTutorialVideo appTutorialVideo = appTutorialVideoMapper.selectById(id);
        AppTutorialVideoDetail appTutorialVideoDetail = AppTutorialVideoAssembler.INSTANCE.getAppTutorialVideoDetail(appTutorialVideo);
        return appTutorialVideoDetail;
    }

    /**
     * 查询教程视频配置
     *
     * @param
     * @return com.armsmart.jupiter.bs.api.dto.response.AppTutorialVideoDetail
     * @date 2021/12/27
     */
    public AppTutorialVideoDetail selectOne() {
        AppTutorialVideoQueryParam queryParam = new AppTutorialVideoQueryParam();
        queryParam.setIsDel(false);
        PageHelper.startPage(1, 1);
        List<AppTutorialVideo> appTutorialVideos = appTutorialVideoMapper.selectList(queryParam);
        if (!CollectionUtils.isEmpty(appTutorialVideos)) {
            return AppTutorialVideoAssembler.INSTANCE.getAppTutorialVideoDetail(appTutorialVideos.get(0));
        }
        return null;
    }
}
