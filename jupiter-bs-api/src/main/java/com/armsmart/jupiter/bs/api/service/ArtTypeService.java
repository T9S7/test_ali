package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtTypeDetail;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeQueryParam;
import com.armsmart.jupiter.bs.api.dao.ArtTypeMapper;
import com.armsmart.jupiter.bs.api.assembler.ArtTypeAssembler;
import com.armsmart.jupiter.bs.api.entity.ArtType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ArtType service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class ArtTypeService {

    @Autowired(required = false)
    private  ArtTypeMapper artTypeMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<ArtTypeDetail> selectPage(ArtTypeQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<ArtType> pageInfo = new PageInfo<>(artTypeMapper.selectList(query));
        List<ArtTypeDetail> dtoList = ArtTypeAssembler.INSTANCE.getArtTypeDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  artTypeAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.ArtType
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(ArtTypeAddParam artTypeAddParam){
        ArtType entity = ArtTypeAssembler.INSTANCE.getArtType(artTypeAddParam);
        if(artTypeAddParam.getParentId() == 0){
            entity.setLever(1);
        }else {
            ArtType artType = artTypeMapper.selectById(artTypeAddParam.getParentId());
            entity.setLever(artType.getLever()+1);
        }
        artTypeMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param artTypeUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ArtTypeUpdateParam artTypeUpdateParam){
        ArtType entity = ArtTypeAssembler.INSTANCE.getArtType(artTypeUpdateParam);
        artTypeMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        ArtType entity = new ArtType();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        artTypeMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.ArtTypeDetail
     */
    public CommonResult<ArtTypeDetail> selectById(Integer id) {
        ArtType artType = artTypeMapper.selectById(id);
        ArtTypeDetail artTypeDetail = ArtTypeAssembler.INSTANCE.getArtTypeDetail(artType);
        return CommonResult.success(artTypeDetail);
    }


    public CommonPage<ArtTypeDetail> selectListById(Integer id){
//        query.setIsDel(false);
//        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<ArtType> pageInfo = new PageInfo<>(artTypeMapper.selectListById(id));
        List<ArtTypeDetail> dtoList = ArtTypeAssembler.INSTANCE.getArtTypeDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 类型数据树
     *
     * @param id     id
     * @return SysRoleDetail
     * @date 2021/03/01
     */

    public List<ArtTypeDetail> selectRolePermissionTree(Integer id) {
        List<ArtTypeDetail> rolePermissions = selectRolePermission(id);
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
//        if (!containsBtn) {
//            rolePermissions = rolePermissions.stream().filter(sysResource -> sysResource.getResourceType() == ArtType.MENU.getCode()).collect(Collectors.toList());
//        }
        List<ArtTypeDetail> resourceDetails = buildTree(rolePermissions);
        return resourceDetails;
    }


    public List<ArtTypeDetail> selectRolePermission(Integer roleId) {
        List<ArtType> resultList = artTypeMapper.selectRolePermission(roleId);
        return ArtTypeAssembler.INSTANCE.getSysResourceDetailList(resultList);
    }

    /**
     * 查询所有类型以树结构返回
     *
     * @return java.util.List
     * @date 2021/1/2
     */
    public List<ArtTypeDetail> selectTree() {
        List<ArtType> list = selectAll();
        return buildTree(ArtTypeAssembler.INSTANCE.getArtTypeDetailList(list));
    }


    public List<ArtType> selectAll(){
        ArtTypeQueryParam artTypeQueryParam = new ArtTypeQueryParam();
        artTypeQueryParam.setIsDel(false);
        return artTypeMapper.selectList(artTypeQueryParam);
    }

    /**
     * 将平铺资源集合转换为树形结构
     *
     * @param detailList
     * @return java.util.List
     * @date 2021/1/2
     */
    public List<ArtTypeDetail> buildTree(List<ArtTypeDetail> detailList) {
        List<ArtTypeDetail> resultList = new ArrayList<>();
        Map<Integer, ArtTypeDetail> map = detailList.stream().collect(Collectors.toMap(ArtTypeDetail::getId, sysResourceDetail -> sysResourceDetail));
        for (ArtTypeDetail artTypeDetail : detailList) {
            Integer parentId = artTypeDetail.getParentId();
            if (parentId != null && parentId.intValue() != 0 && !map.containsKey(parentId)) {
                continue;
            }
            ArtTypeDetail thisNode = map.get(artTypeDetail.getId());
            ArtTypeDetail parentNode = map.get(artTypeDetail.getParentId());
            Integer resourceType = thisNode.getLever();
            if (null != parentNode) {
                if (parentNode.getChildren() == null) {
                    parentNode.setChildren(new ArrayList<>());
                }
                parentNode.getChildren().add(thisNode);
            } else {
                if (resourceType != null) {
                    resultList.add(thisNode);
                }
            }
        }
        return resultList;
    }
}
