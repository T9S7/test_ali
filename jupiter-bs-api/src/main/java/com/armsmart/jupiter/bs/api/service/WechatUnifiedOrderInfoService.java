package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.WechatUnifiedOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.WechatUnifiedOrderInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.WechatUnifiedOrderInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo;
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
 * WechatUnifiedOrderInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class WechatUnifiedOrderInfoService {

    @Autowired
    private  WechatUnifiedOrderInfoMapper wechatUnifiedOrderInfoMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<WechatUnifiedOrderInfoDetail> selectPage(WechatUnifiedOrderInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<WechatUnifiedOrderInfo> pageInfo = new PageInfo<>(wechatUnifiedOrderInfoMapper.selectList(query));
        List<WechatUnifiedOrderInfoDetail> dtoList = WechatUnifiedOrderInfoAssembler.INSTANCE.getWechatUnifiedOrderInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  wechatUnifiedOrderInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(WechatUnifiedOrderInfoAddParam wechatUnifiedOrderInfoAddParam){
        WechatUnifiedOrderInfo entity = WechatUnifiedOrderInfoAssembler.INSTANCE.getWechatUnifiedOrderInfo(wechatUnifiedOrderInfoAddParam);
        wechatUnifiedOrderInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param wechatUnifiedOrderInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(WechatUnifiedOrderInfoUpdateParam wechatUnifiedOrderInfoUpdateParam){
        WechatUnifiedOrderInfo entity = WechatUnifiedOrderInfoAssembler.INSTANCE.getWechatUnifiedOrderInfo(wechatUnifiedOrderInfoUpdateParam);
        wechatUnifiedOrderInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        WechatUnifiedOrderInfo entity = new WechatUnifiedOrderInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        wechatUnifiedOrderInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.WechatUnifiedOrderInfoDetail
     */
    public CommonResult<WechatUnifiedOrderInfoDetail> selectById(Integer id) {
        WechatUnifiedOrderInfo wechatUnifiedOrderInfo = wechatUnifiedOrderInfoMapper.selectById(id);
        WechatUnifiedOrderInfoDetail wechatUnifiedOrderInfoDetail = WechatUnifiedOrderInfoAssembler.INSTANCE.getWechatUnifiedOrderInfoDetail(wechatUnifiedOrderInfo);
        return CommonResult.success(wechatUnifiedOrderInfoDetail);
    }

}
