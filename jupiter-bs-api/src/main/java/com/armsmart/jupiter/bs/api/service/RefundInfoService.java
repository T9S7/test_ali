package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.QueryVSPFundParam;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.RefundFeeFlowDetail;
import com.armsmart.jupiter.bs.api.dto.response.RefundInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.RefundInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.RefundInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.RefundInfo;
import com.armsmart.jupiter.bs.api.tlpay.TlPayProperties;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * RefundInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class RefundInfoService {

    @Autowired(required = false)
    private  RefundInfoMapper refundInfoMapper;

    @Autowired(required = false)
    private TlPayProperties tlPayProperties;

    @Autowired(required = false)
    private TlOrderService tlOrderService;
    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<RefundInfoDetail> selectPage(RefundInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<RefundInfo> pageInfo = new PageInfo<>(refundInfoMapper.selectList(query));
        List<RefundInfoDetail> dtoList = RefundInfoAssembler.INSTANCE.getRefundInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  refundInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.RefundInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(RefundInfoAddParam refundInfoAddParam){
        RefundInfo entity = RefundInfoAssembler.INSTANCE.getRefundInfo(refundInfoAddParam);
        refundInfoMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param refundInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(RefundInfoUpdateParam refundInfoUpdateParam){
        RefundInfo entity = RefundInfoAssembler.INSTANCE.getRefundInfo(refundInfoUpdateParam);
        refundInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        RefundInfo entity = new RefundInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        refundInfoMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.RefundInfoDetail
     */
    public CommonResult<RefundInfoDetail> selectById(Integer id) {
        RefundInfo refundInfo = refundInfoMapper.selectById(id);
        RefundInfoDetail refundInfoDetail = RefundInfoAssembler.INSTANCE.getRefundInfoDetail(refundInfo);
        return CommonResult.success(refundInfoDetail);
    }

    /**
     * 查询当日流水
     * @return
     */
    public CommonResult<RefundFeeFlowDetail> feeFlowOneDay(){
        RefundFeeFlowDetail refundFeeFlowDetail = new RefundFeeFlowDetail();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = sdf.format(new Date());

        QueryVSPFundParam queryVSPFundParam = new QueryVSPFundParam();
        queryVSPFundParam.setVspOrgid(tlPayProperties.getVspOrgid());
        queryVSPFundParam.setVspCusid(tlPayProperties.getVspCusid());
        Long vspCusFlow = Long.valueOf(tlOrderService.queryVSPFund(queryVSPFundParam).getData().toString());
        QueryVSPFundParam vspOrgParam = new QueryVSPFundParam();
        vspOrgParam.setVspCusid(tlPayProperties.getVspCusidKj());
        Long vspOrgFlow = Long.valueOf(tlOrderService.queryVSPFund(vspOrgParam).getData().toString());
        refundFeeFlowDetail.setDateTime(dateTime);
        refundFeeFlowDetail.setVspCusFlow(vspCusFlow);
        refundFeeFlowDetail.setVspOrgFlow(vspOrgFlow);
        List<Integer> payMatchs = new ArrayList<>();
        payMatchs.add(1);
        payMatchs.add(2);
        Long vspCusReturn = refundInfoMapper.selectCountVsp(payMatchs);
        refundFeeFlowDetail.setVspCusReturn(vspCusReturn);
        List<Integer> payMatchs1 = new ArrayList<>();
        payMatchs1.add(4);
        Long vspOrgReturn = refundInfoMapper.selectCountVsp(payMatchs1);
        refundFeeFlowDetail.setVspOrgReturn(vspOrgReturn);
        return CommonResult.success(refundFeeFlowDetail);
    }


}
