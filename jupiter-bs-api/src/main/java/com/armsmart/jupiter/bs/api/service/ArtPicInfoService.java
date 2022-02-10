package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.ArtPicInfoAssembler;
import com.armsmart.jupiter.bs.api.dao.ArtPicInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtPicInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ArtPicInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * ArtPicInfo service
 * @author wei.lin
 **/
@Slf4j
@Service
public class ArtPicInfoService {

    @Autowired(required = false)
    private  ArtPicInfoMapper artPicInfoMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<ArtPicInfoDetail> selectPage(ArtPicInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<ArtPicInfo> pageInfo = new PageInfo<>(artPicInfoMapper.selectList(query));
        List<ArtPicInfoDetail> dtoList = ArtPicInfoAssembler.INSTANCE.getArtPicInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  artPicInfoAddParam
     * @date 2020/01/01
     * @return ArtPicInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(ArtPicInfoAddParam artPicInfoAddParam){
        ArtPicInfo entity = ArtPicInfoAssembler.INSTANCE.getArtPicInfo(artPicInfoAddParam);
        artPicInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param artPicInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ArtPicInfoUpdateParam artPicInfoUpdateParam){
        ArtPicInfo entity = ArtPicInfoAssembler.INSTANCE.getArtPicInfo(artPicInfoUpdateParam);
        artPicInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id){
        ArtPicInfo entity = new ArtPicInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        artPicInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids){
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
     * @return ArtPicInfoDetail
     */
    public CommonResult<ArtPicInfoDetail> selectById(Long id) {
        ArtPicInfo artPicInfo = artPicInfoMapper.selectById(id);
        ArtPicInfoDetail artPicInfoDetail = ArtPicInfoAssembler.INSTANCE.getArtPicInfoDetail(artPicInfo);
        return CommonResult.success(artPicInfoDetail);
    }

}
