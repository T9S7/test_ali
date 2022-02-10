package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.constants.ResourceType;
import com.armsmart.jupiter.bs.api.dao.SysResourceMapper;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import com.armsmart.common.api.CommonPage;
import com.armsmart.jupiter.bs.api.assembler.SysResourceAssembler;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SysResource service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class SysResourceService {

    @Autowired(required = false)
    private SysResourceMapper sysResourceMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<SysResourceDetail> selectPage(SysResourceQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SysResource> pageInfo = new PageInfo<>(sysResourceMapper.selectList(query));
        List<SysResourceDetail> dtoList = SysResourceAssembler.INSTANCE.getSysResourceDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 查询所有
     *
     * @return List
     * @date 2021/03/01
     */

    public List<SysResource> selectAll() {
        SysResourceQueryParam query = new SysResourceQueryParam();
        query.setIsDel(false);
        return sysResourceMapper.selectList(query);
    }

    /**
     * 查询所有资源以树结构返回
     *
     * @return java.util.List
     * @date 2021/1/2
     */
    public List<SysResourceDetail> selectTree() {
        List<SysResource> list = selectAll();
        return buildTree(SysResourceAssembler.INSTANCE.getSysResourceDetailList(list));
    }

    /**
     * 插入数据
     *
     * @param sysResourceAddParam
     * @return SysResource
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(SysResourceAddParam sysResourceAddParam) {
        SysResource entity = SysResourceAssembler.INSTANCE.getSysResource(sysResourceAddParam);
        sysResourceMapper.insert(entity);
        return entity.getResourceId();
    }

    /**
     * 修改数据
     *
     * @param sysResourceUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SysResourceUpdateParam sysResourceUpdateParam) {
        SysResource entity = SysResourceAssembler.INSTANCE.getSysResource(sysResourceUpdateParam);
        sysResourceMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        SysResource entity = new SysResource();
        entity.setResourceId(id);
        entity.setIsDel(true);
        sysResourceMapper.updateSelective(entity);
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
     * @return SysResourceDetail
     * @date 2021/03/01
     */
    public SysResourceDetail selectById(Serializable id) {
        SysResource sysResource = sysResourceMapper.selectById(id);
        SysResourceDetail sysResourceDetail = SysResourceAssembler.INSTANCE.getSysResourceDetail(sysResource);
        return sysResourceDetail;
    }

    /**
     * 设置启用/禁用
     *
     * @param resourceId 资源ID
     * @param isEnable   true表示启用
     * @return void
     */
    public void setIsEnable(Integer resourceId, boolean isEnable) {
        SysResource entity = new SysResource();
        entity.setResourceId(resourceId);
        entity.setIsEnable(isEnable);
        sysResourceMapper.updateSelective(entity);
    }


    /**
     * 将平铺资源集合转换为树形结构
     *
     * @param detailList
     * @return java.util.List
     * @date 2021/1/2
     */
    public List<SysResourceDetail> buildTree(List<SysResourceDetail> detailList) {
        List<SysResourceDetail> resultList = new ArrayList<>();
        Map<Integer, SysResourceDetail> map = detailList.stream().collect(Collectors.toMap(SysResourceDetail::getResourceId, sysResourceDetail -> sysResourceDetail));
        for (SysResourceDetail sysResourceDetail : detailList) {
            Integer parentId = sysResourceDetail.getParentId();
            if (parentId != null && parentId.intValue() != 0 && !map.containsKey(parentId)) {
                continue;
            }
            SysResourceDetail thisNode = map.get(sysResourceDetail.getResourceId());
            SysResourceDetail parentNode = map.get(sysResourceDetail.getParentId());
            Integer resourceType = thisNode.getResourceType();
            if (null != parentNode) {
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(thisNode);
            } else {
                if (resourceType != null && resourceType.intValue() == ResourceType.MENU.getCode()) {
                    resultList.add(thisNode);
                }
            }
        }
        return resultList;
    }

}
