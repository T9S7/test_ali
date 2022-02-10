package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.assembler.SmsInfoAssembler;
import com.armsmart.jupiter.bs.api.config.SmsConfig;
import com.armsmart.jupiter.bs.api.dao.SmsInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.SmsInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SmsInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SmsInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SmsInfoDetail;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import com.armsmart.jupiter.bs.api.sms.AliSmsService;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.common.utils.PhoneNumValidationUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import static com.armsmart.jupiter.bs.api.error.BusinessError.*;

/**
 * SmsInfo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class SmsInfoService {

    @Autowired(required = false)
    private SmsInfoMapper smsInfoMapper;
    @Autowired
    private AliSmsService aliSmsService;
    @Autowired
    private SmsConfig smsConfig;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<SmsInfoDetail> selectPage(SmsInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SmsInfo> pageInfo = new PageInfo<>(smsInfoMapper.selectList(query));
        List<SmsInfoDetail> dtoList = SmsInfoAssembler.INSTANCE.getSmsInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param smsInfoAddParam
     * @return SmsInfo
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(SmsInfoAddParam smsInfoAddParam) {
        SmsInfo entity = SmsInfoAssembler.INSTANCE.getSmsInfo(smsInfoAddParam);
        smsInfoMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改数据
     *
     * @param smsInfoUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SmsInfoUpdateParam smsInfoUpdateParam) {
        SmsInfo entity = SmsInfoAssembler.INSTANCE.getSmsInfo(smsInfoUpdateParam);
        smsInfoMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        SmsInfo entity = new SmsInfo();
        entity.setId(id);
        entity.setIsDel(true);
        smsInfoMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Integer> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return SmsInfoDetail
     * @date 2021/03/01
     */
    public SmsInfoDetail selectById(Integer id) {
        SmsInfo smsInfo = smsInfoMapper.selectById(id);
        SmsInfoDetail smsInfoDetail = SmsInfoAssembler.INSTANCE.getSmsInfoDetail(smsInfo);
        return smsInfoDetail;
    }

    /**
     * 获取最后一次验证码
     *
     * @param mobile
     * @return SmsInfo
     */
    public SmsInfo selectLatestVerifyCode(String mobile, SmsInfo.SmsType smsType) {
        if (!PhoneNumValidationUtil.isPhone(mobile)) {
            throw new BusinessException(MOBILE_IS_VALID);
        }
        return smsInfoMapper.selectLatest(mobile, smsType.getCode());
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return SmsInfo
     */
    public void sendVerifyCode(String mobile, SmsInfo.SmsType type) {
        if (!PhoneNumValidationUtil.isPhone(mobile)) {
            throw new BusinessException(MOBILE_IS_VALID);
        }
        SmsInfo smsInfo = smsInfoMapper.selectLatest(mobile, type.getCode());
        if (null != smsInfo) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - smsInfo.getCreateTime() < smsConfig.getIntervalTime() * 1000) {
                throw new BusinessException(SMS_SEND_BUSY);
            }
        }
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        smsInfo = new SmsInfo();
        long currentTime = System.currentTimeMillis();
        smsInfo.setCreateTime(currentTime);
        smsInfo.setExpireTime(currentTime + smsConfig.getDurationTime() * 1000L);
        smsInfo.setIsDel(false);
        smsInfo.setMobile(mobile);
        smsInfo.setVerifyCode(verifyCode);
        smsInfo.setSmsType(type.getCode());
        smsInfoMapper.insert(smsInfo);
        switch (type) {
            case AUTH:
                aliSmsService.sendAuthSmsCode(mobile, verifyCode);
                break;
            case REG_LOGIN:
                aliSmsService.sendRegisterSmsCode(mobile, verifyCode);
                break;
            default:
                log.warn("Unsupported sms type {}!", type);
                break;
        }

    }

    public void checkVerifyCode(String mobile, SmsInfo.SmsType smsType, String verifyCode) {
        SmsInfo smsInfo = this.selectLatestVerifyCode(mobile, smsType);
        if (null == smsInfo || smsInfo.getExpireTime() < System.currentTimeMillis()
                || !verifyCode.equals(smsInfo.getVerifyCode())) {
            log.warn("User {} verify code is expire or not match!", mobile);
            throw new BusinessException(VERIFY_CODE_INVALID);
        }
    }
}
