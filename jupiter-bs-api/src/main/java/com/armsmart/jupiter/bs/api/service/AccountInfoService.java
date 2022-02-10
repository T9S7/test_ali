package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoQueryParam;
import com.armsmart.jupiter.bs.api.dao.AccountInfoMapper;
import com.armsmart.jupiter.bs.api.assembler.AccountInfoAssembler;
import com.armsmart.jupiter.bs.api.entity.AccountInfo;
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
 * AccountInfo service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class AccountInfoService {

    @Autowired(required = false)
    private  AccountInfoMapper accountInfoMapper;

    @Autowired
    private TlOrderService tlOrderService;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<AccountInfoDetail> selectPage(AccountInfoQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(accountInfoMapper.selectList(query));
        List<AccountInfoDetail> dtoList = AccountInfoAssembler.INSTANCE.getAccountInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  accountInfoAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.AccountInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(AccountInfoAddParam accountInfoAddParam){
        AccountInfo entity = AccountInfoAssembler.INSTANCE.getAccountInfo(accountInfoAddParam);
        accountInfoMapper.insert(entity);
        return CommonResult.success(entity.getAccountId());
     }

    /**
     * 修改数据
     * @param accountInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(AccountInfoUpdateParam accountInfoUpdateParam){
        AccountInfo entity = AccountInfoAssembler.INSTANCE.getAccountInfo(accountInfoUpdateParam);
        accountInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param accountId
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer accountId){
        AccountInfo entity = new AccountInfo();
        entity.setAccountId(accountId);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        accountInfoMapper.updateSelective(entity);
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

//    /**
//     * 获取详情
//     * @param accountId 主键ID
//     * @date 2020/01/01
//     * @return com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail
//     */
//    public CommonResult<AccountInfoDetail> selectById(Integer accountId) {
//        AccountInfo accountInfo = accountInfoMapper.selectById(accountId);
//        AccountInfoDetail accountInfoDetail = AccountInfoAssembler.INSTANCE.getAccountInfoDetail(accountInfo);
//        return CommonResult.success(accountInfoDetail);
//    }

    /**
     * 获取详情
     * @param userId 用户id
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail
     */
    public CommonResult<AccountInfoDetail> selectById(Integer userId) {
        AccountInfo accountInfo = accountInfoMapper.selectByUserId(userId);
        String balance = tlOrderService.queryBalance(userId.toString()).getData().toString();
        AccountInfoDetail accountInfoDetail = AccountInfoAssembler.INSTANCE.getAccountInfoDetail(accountInfo);
        if(accountInfoDetail == null){
            AccountInfoDetail accountInfoDetail1 = new AccountInfoDetail();
            accountInfoDetail1.setAccountId(Integer.valueOf(-1));
            accountInfoDetail1.setUserId(userId);
            accountInfoDetail1.setBalance(Long.valueOf(balance));
            accountInfoDetail1.setCashDeposit(0l);
            return CommonResult.success(accountInfoDetail1);
        }else{
            accountInfoDetail.setBalance(Long.valueOf(balance));
            return CommonResult.success(accountInfoDetail);
        }
    }

}
