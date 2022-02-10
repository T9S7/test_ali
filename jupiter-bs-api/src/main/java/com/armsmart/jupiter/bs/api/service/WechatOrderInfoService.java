package com.armsmart.jupiter.bs.api.service;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.WechatOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.WechatOrderInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.WechatOrderInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.WechatOrderInfo;
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
 * WechatOrderInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class WechatOrderInfoService {

    @Autowired
    private  WechatOrderInfoMapper wechatOrderInfoMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<WechatOrderInfoDetail> selectPage(WechatOrderInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<WechatOrderInfo> pageInfo = new PageInfo<>(wechatOrderInfoMapper.selectList(query));
        List<WechatOrderInfoDetail> dtoList = WechatOrderInfoAssembler.INSTANCE.getWechatOrderInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  wechatOrderInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.WechatOrderInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(WechatOrderInfoAddParam wechatOrderInfoAddParam){
        WechatOrderInfo entity = WechatOrderInfoAssembler.INSTANCE.getWechatOrderInfo(wechatOrderInfoAddParam);
        wechatOrderInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param wechatOrderInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(WechatOrderInfoUpdateParam wechatOrderInfoUpdateParam){
        WechatOrderInfo entity = WechatOrderInfoAssembler.INSTANCE.getWechatOrderInfo(wechatOrderInfoUpdateParam);
        wechatOrderInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        WechatOrderInfo entity = new WechatOrderInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        wechatOrderInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.WechatOrderInfoDetail
     */
    public CommonResult<WechatOrderInfoDetail> selectById(Integer id) {
        WechatOrderInfo wechatOrderInfo = wechatOrderInfoMapper.selectById(id);
        WechatOrderInfoDetail wechatOrderInfoDetail = WechatOrderInfoAssembler.INSTANCE.getWechatOrderInfoDetail(wechatOrderInfo);
        return CommonResult.success(wechatOrderInfoDetail);
    }

}
