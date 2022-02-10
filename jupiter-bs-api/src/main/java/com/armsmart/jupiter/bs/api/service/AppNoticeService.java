package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.assembler.AppNoticeAssembler;
import com.armsmart.jupiter.bs.api.constants.AppNoticeCategory;
import com.armsmart.jupiter.bs.api.dao.AppNoticeMapper;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail;
import com.armsmart.jupiter.bs.api.entity.AppNotice;
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
 * AppNotice service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class AppNoticeService {

    @Autowired
    private AppNoticeMapper appNoticeMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public PageInfo<AppNoticeDetail> selectPage(AppNoticeQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        query.setIsDel(false);
        List<AppNotice> list = appNoticeMapper.selectList(query);
        List<AppNoticeDetail> dtoList = AppNoticeAssembler.INSTANCE.getAppNoticeDetailList(list);
        return new PageInfo<>(dtoList);
    }

    /**
     * 插入数据
     *
     * @param appNoticeAddParam
     * @return com.armsmart.jupiter.bs.api.entity.AppNotice
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(AppNoticeAddParam appNoticeAddParam) {
        AppNotice entity = AppNoticeAssembler.INSTANCE.getAppNotice(appNoticeAddParam);
        if (entity.getEnabled()) {
            appNoticeMapper.resetEnabled();
        }
        appNoticeMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改数据
     *
     * @param appNoticeUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(AppNoticeUpdateParam appNoticeUpdateParam) {
        AppNotice entity = AppNoticeAssembler.INSTANCE.getAppNotice(appNoticeUpdateParam);
        appNoticeMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        AppNotice entity = new AppNotice();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        appNoticeMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail
     * @date 2020/01/01
     */
    public AppNoticeDetail selectById(Integer id) {
        AppNotice appNotice = appNoticeMapper.selectById(id);
        AppNoticeDetail appNoticeDetail = AppNoticeAssembler.INSTANCE.getAppNoticeDetail(appNotice);
        return appNoticeDetail;
    }

    /**
     * 获取维护通知
     *
     * @param
     * @return com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail
     * @date 2021/12/27
     */
    public AppNoticeDetail getMaintenanceNotice() {
        AppNoticeQueryParam queryParam = new AppNoticeQueryParam();
        queryParam.setIsDel(false);
        queryParam.setEnabled(true);
        queryParam.setCategory(AppNoticeCategory.MAINTENANCE.getCode());
        List<AppNotice> appNotices = appNoticeMapper.selectList(queryParam);
        if (!CollectionUtils.isEmpty(appNotices)) {
            return AppNoticeAssembler.INSTANCE.getAppNoticeDetail(appNotices.get(0));
        }
        return null;
    }

}
