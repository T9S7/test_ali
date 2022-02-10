package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingPicInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.ThingPicInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.ThingPicInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * ThingPicInfo service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class ThingPicInfoService {

    @Autowired(required = false)
    private ThingPicInfoMapper thingPicInfoMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<ThingPicInfoDetail> selectPage(ThingPicInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ThingPicInfo> pageInfo = new PageInfo<>(thingPicInfoMapper.selectList(query));
        List<ThingPicInfoDetail> dtoList = ThingPicInfoAssembler.INSTANCE.getThingPicInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param thingPicInfoAddParam
     * @return com.armsmart.jupiter.bs.api.entity.ThingPicInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(ThingPicInfoAddParam thingPicInfoAddParam) {
        ThingPicInfo entity = ThingPicInfoAssembler.INSTANCE.getThingPicInfo(thingPicInfoAddParam);
        thingPicInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
    }

    /**
     * 修改数据
     *
     * @param thingPicInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ThingPicInfoUpdateParam thingPicInfoUpdateParam) {
        ThingPicInfo entity = ThingPicInfoAssembler.INSTANCE.getThingPicInfo(thingPicInfoUpdateParam);
        thingPicInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id) {
        ThingPicInfo entity = new ThingPicInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        thingPicInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.ThingPicInfoDetail
     * @date 2020/01/01
     */
    public CommonResult<ThingPicInfoDetail> selectById(Long id) {
        ThingPicInfo thingPicInfo = thingPicInfoMapper.selectById(id);
        ThingPicInfoDetail thingPicInfoDetail = ThingPicInfoAssembler.INSTANCE.getThingPicInfoDetail(thingPicInfo);
        return CommonResult.success(thingPicInfoDetail);
    }

    /**
     * 根据物品ID删除图片
     *
     * @param thingId
     * @return void
     */
    public void deletePhysical(Long thingId) {
        thingPicInfoMapper.deleteByThingId(thingId);
    }

}
