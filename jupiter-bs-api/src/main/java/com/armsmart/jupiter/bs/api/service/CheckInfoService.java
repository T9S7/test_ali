package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingPicInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail;
import com.armsmart.jupiter.bs.api.dao.CheckInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.CheckInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.response.CheckListDetail;
import com.armsmart.jupiter.bs.api.entity.CheckInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

/**
 * CheckInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class CheckInfoService {

    @Autowired(required = false)
    private  CheckInfoMapper checkInfoMapper;

    @Autowired(required = false)
    private ThingPicInfoMapper thingPicInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    /**
     * 分页查询
     * @param param 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<CheckListDetail> selectPage(UserIdQueryParam param){
        CheckInfoQueryParam query = new CheckInfoQueryParam();
        query.setUserId(param.getUserId().intValue());
        query.setIsDel(false);
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        PageInfo<CheckInfo> dtoListPage = new PageInfo<>(checkInfoMapper.selectList(query));
        List<CheckInfo> dtoList = dtoListPage.getList();
//        List<CheckInfo> dtoList = checkInfoMapper.selectList(query);
        List<CheckListDetail> checkListDetails = new ArrayList<>();
        List<Integer> CheckId = new ArrayList();
        if(dtoList == null){
            PageInfo<CheckListDetail> pageInfo1 = new PageInfo<>();
            return CommonPage.restPage(pageInfo1, checkListDetails);
        }else{
            for(int i =0;i<dtoList.size();i++){
                CheckId.add(dtoList.get(i).getId());
            }
//            Collections.reverse(CheckId);
            CheckId.forEach(id ->{
                CheckInfo checkInfo = checkInfoMapper.selectById(id);
                CheckListDetail checkListDetail = new CheckListDetail();
                ThingPicInfoQueryParam param1 = new ThingPicInfoQueryParam();
                param1.setThingId(checkInfo.getThingId());
                param1.setIsDel(false);
                List<ThingPicInfo> pics = thingPicInfoMapper.selectList(param1);
                ThingInfo thingInfo = thingInfoMapper.selectById(checkInfo.getThingId());
                if(null == thingInfo){
                    return;
                }else {
                    checkListDetail.setPics(pics);
                    checkListDetail.setCurrentPrice(thingInfo.getCurrentPrice());
                    checkListDetail.setCheckType(checkInfo.getCheckType());
                    checkListDetail.setCreateTime(checkInfo.getCreateTime());
                    checkListDetail.setId(id);
                    checkListDetail.setContractAddr(checkInfo.getContractAddr());
                    checkListDetail.setFileInfo(checkInfo.getFileInfo());
                    checkListDetail.setThingId(checkInfo.getThingId());
                    checkListDetail.setUserId(checkInfo.getUserId());
                    checkListDetail.setArtName(thingInfo.getArtName());
                    checkListDetails.add(checkListDetail);
                }
            });
//
//            PageInfo<CheckListDetail> pageList =new PageInfo<>(checkListDetails);
//            List<CheckListDetail> dtoList2 = pageList.getList();
//            return CommonPage.restPage(pageList, dtoList2);
            return CommonPage.restPage(dtoListPage, checkListDetails);
        }
    }

    /**
     * 插入数据
     * @param  checkInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.CheckInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(CheckInfoAddParam checkInfoAddParam){
        CheckInfo entity = CheckInfoAssembler.INSTANCE.getCheckInfo(checkInfoAddParam);
        checkInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param checkInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(CheckInfoUpdateParam checkInfoUpdateParam){
        CheckInfo entity = CheckInfoAssembler.INSTANCE.getCheckInfo(checkInfoUpdateParam);
        checkInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        CheckInfo entity = new CheckInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        checkInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail
     */
    public CommonResult<CheckInfoDetail> selectById(Integer id) {
        CheckInfo checkInfo = checkInfoMapper.selectById(id);
        CheckInfoDetail checkInfoDetail = CheckInfoAssembler.INSTANCE.getCheckInfoDetail(checkInfo);
        return CommonResult.success(checkInfoDetail);
    }

}
