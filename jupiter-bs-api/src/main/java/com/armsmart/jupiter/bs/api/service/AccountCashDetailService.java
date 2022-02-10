package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailQueryParam;
import com.armsmart.jupiter.bs.api.dao.AccountCashDetailMapper;
import com.armsmart.jupiter.bs.api.assembler.AccountCashDetailAssembler;
import com.armsmart.jupiter.bs.api.entity.AccountCashDetail;
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
 * AccountCashDetail service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class AccountCashDetailService {

    @Autowired(required = false)
    private  AccountCashDetailMapper accountCashDetailMapper;

    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<AccountCashDetailDetail> selectPage(AccountCashDetailQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<AccountCashDetail> pageInfo = new PageInfo<>(accountCashDetailMapper.selectList(query));
        List<AccountCashDetailDetail> dtoList = AccountCashDetailAssembler.INSTANCE.getAccountCashDetailDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     * @param  accountCashDetailAddParam
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.AccountCashDetail
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(AccountCashDetailAddParam accountCashDetailAddParam){
        AccountCashDetail entity = AccountCashDetailAssembler.INSTANCE.getAccountCashDetail(accountCashDetailAddParam);
        accountCashDetailMapper.insert(entity);
        return CommonResult.success(entity.getId());
     }

    /**
     * 修改数据
     * @param accountCashDetailUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(AccountCashDetailUpdateParam accountCashDetailUpdateParam){
        AccountCashDetail entity = AccountCashDetailAssembler.INSTANCE.getAccountCashDetail(accountCashDetailUpdateParam);
        accountCashDetailMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Integer id){
        AccountCashDetail entity = new AccountCashDetail();
        entity.setId(id);
        entity.setIsDel(true);
//        entity.setUpdateTime(System.currentTimeMillis());
        accountCashDetailMapper.updateSelective(entity);
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
     * @return com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail
     */
    public CommonResult<AccountCashDetailDetail> selectById(Integer id) {
        AccountCashDetail accountCashDetail = accountCashDetailMapper.selectById(id);
        AccountCashDetailDetail accountCashDetailDetail = AccountCashDetailAssembler.INSTANCE.getAccountCashDetailDetail(accountCashDetail);
        return CommonResult.success(accountCashDetailDetail);
    }

}
