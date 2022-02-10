package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.ThingDealInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dao.UserInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.GiveInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.PushInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.PushInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.PushInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.PushInfo;
import com.armsmart.jupiter.bs.api.entity.ThingDealInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
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
 * PushInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class PushInfoService {

    @Autowired(required = false)
    private  PushInfoMapper pushInfoMapper;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @Autowired(required = false)
    private ThingDealInfoMapper thingDealInfoMapper;
    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */

    public CommonPage<PushInfoDetail> selectPage(PushInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<PushInfo> pageInfo = new PageInfo<>(pushInfoMapper.selectList(query));
        List<PushInfoDetail> dtoList = PushInfoAssembler.INSTANCE.getPushInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  pushInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.PushInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(PushInfoAddParam pushInfoAddParam){
        PushInfo entity = PushInfoAssembler.INSTANCE.getPushInfo(pushInfoAddParam);
        pushInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param pushInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(PushInfoUpdateParam pushInfoUpdateParam){
        PushInfo entity = PushInfoAssembler.INSTANCE.getPushInfo(pushInfoUpdateParam);
        pushInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        PushInfo entity = new PushInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        pushInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.PushInfoDetail
     */
    public CommonResult<PushInfoDetail> selectById(Integer id) {
        PushInfo pushInfo = pushInfoMapper.selectById(id);
        PushInfoDetail pushInfoDetail = PushInfoAssembler.INSTANCE.getPushInfoDetail(pushInfo);
        return CommonResult.success(pushInfoDetail);
    }

    /**
     * 待处理信息
     * @param userId
     * @return
     */
    public CommonResult<GiveInfoDetail> getPushInfo(Long userId){
        ThingDealInfo thingDealInfo = thingDealInfoMapper.getDealInfo(userId);
        GiveInfoDetail detail = new GiveInfoDetail();
        if(thingDealInfo == null){
            return CommonResult.success();
        }
        ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(thingDealInfo.getContractAddr());
        if(thingInfo == null){
            return CommonResult.success();
        }
        UserInfo userInfo = userInfoMapper.selectById(thingInfo.getUserId());
        if(userInfo == null){
            return CommonResult.success();
        }
        detail.setNickname(userInfo.getNickname());
        detail.setUserUrl(userInfo.getAvatar());
        detail.setThingInfo(thingInfo);
        return CommonResult.success(detail);
    }
}
