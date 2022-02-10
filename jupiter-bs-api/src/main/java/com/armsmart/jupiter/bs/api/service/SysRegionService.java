package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.assembler.SysRegionAssembler;
import com.armsmart.jupiter.bs.api.dao.SysRegionMapper;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysRegionDetail;
import com.armsmart.jupiter.bs.api.entity.SysRegion;
import com.armsmart.common.api.CommonPage;
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
 * SysRegion service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class SysRegionService {

    @Autowired(required = false)
    private SysRegionMapper sysRegionMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2020/03/04
     */
    public CommonPage<SysRegionDetail> selectPage(SysRegionQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SysRegion> pageInfo = new PageInfo<>(sysRegionMapper.selectList(query));
        List<SysRegionDetail> dtoList = SysRegionAssembler.INSTANCE.getSysRegionDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param sysRegionAddParam
     * @return com.armsmart.abas.app.entity.SysRegion
     * @date 2020/03/04
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(SysRegionAddParam sysRegionAddParam) {
        SysRegion entity = SysRegionAssembler.INSTANCE.getSysRegion(sysRegionAddParam);
        sysRegionMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改数据
     *
     * @param sysRegionUpdateParam
     * @date 2020/03/04
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRegionUpdateParam sysRegionUpdateParam) {
        SysRegion entity = SysRegionAssembler.INSTANCE.getSysRegion(sysRegionUpdateParam);
        sysRegionMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/03/04
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        SysRegion entity = new SysRegion();
        entity.setId(id);
        sysRegionMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/03/04
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
     * @return com.armsmart.abas.app.dto.response.SysRegionDetail
     * @date 2020/03/04
     */
    public SysRegionDetail selectById(Integer id) {
        SysRegion sysRegion = sysRegionMapper.selectById(id);
        SysRegionDetail sysRegionDetail = SysRegionAssembler.INSTANCE.getSysRegionDetail(sysRegion);
        return sysRegionDetail;
    }

    /**
     * 根据父节点查询子节点
     *
     * @param regionParent 父节点ID
     * @return com.armsmart.abas.app.dto.response.SysRegionDetail
     * @date 2020/03/04
     */
    public List<SysRegionDetail> selectByParent(Integer regionParent) {
        List<SysRegion> list = sysRegionMapper.selectByParent(regionParent);
        List<SysRegionDetail> dtoList = SysRegionAssembler.INSTANCE.getSysRegionDetailList(list);
        return dtoList;
    }

}
