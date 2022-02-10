package com.armsmart.jupiter.bs.api.service;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.UserInfoAssembler;
import com.armsmart.jupiter.bs.api.dao.*;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.UserFansListDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserInfoDetail;
import com.armsmart.jupiter.bs.api.entity.*;
import com.armsmart.jupiter.bs.api.error.BusinessError;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.armsmart.jupiter.bs.api.error.AuctionError.*;

/**
 * UserInfo service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class UserInfoService {

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private UserAuthMapper userAuthMapper;

    @Autowired
    private SmsInfoService smsInfoService;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @Autowired(required = false)
    private UserAuthService userAuthService;

    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Autowired(required = false)
    private UserAuthenticationService userAuthenticationService;

    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;

    @Autowired(required = false)
    private UserFansMapper userFansMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<UserInfoDetail> selectPage(UserInfoQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfoMapper.selectList(query));
        List<UserInfoDetail> dtoList = UserInfoAssembler.INSTANCE.getUserInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param userInfoAddParam
     * @return UserInfo
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(UserInfoAddParam userInfoAddParam) {
        UserInfo entity = UserInfoAssembler.INSTANCE.getUserInfo(userInfoAddParam);
        userInfoMapper.insert(entity);
        return entity.getId();
    }

    /**
     * 修改数据
     *
     * @param userInfoUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UserInfoUpdateParam userInfoUpdateParam) {
        UserInfo entity = UserInfoAssembler.INSTANCE.getUserInfo(userInfoUpdateParam);
        userInfoMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        UserInfo entity = new UserInfo();
        entity.setId(id);
        entity.setIsDel(true);
        userInfoMapper.updateSelective(entity);
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
     * @return UserInfoDetail
     * @date 2021/03/01
     */
    public UserInfoDetail selectById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        UserInfoDetail userInfoDetail = UserInfoAssembler.INSTANCE.getUserInfoDetail(userInfo);
        return userInfoDetail;
    }

    /**
     * 根据主键查询用户信息（含已注销用户）
     *
     * @param id 主键ID
     * @return UserInfo
     * @date 2021/03/01
     */
    public UserInfo selectByIdIncludeDel(Integer id) {
        return userInfoMapper.selectByIdIncludeDel(id);
    }

    /**
     * 设置启用/禁用
     *
     * @param userId   用户ID
     * @param isEnable true表示启用
     * @return void
     */
    public void setIsEnable(Integer userId, boolean isEnable) {
        UserInfo entity = new UserInfo();
        entity.setId(userId);
        entity.setIsEnable(isEnable);
        entity.setUpdateTime(System.currentTimeMillis());
        if (isEnable) {
            log.warn("用户{}被启用！", userId);
        } else {
            log.warn("用户{}被禁用！", userId);
        }
        userInfoMapper.updateSelective(entity);
    }


    public CommonResult checkPhoneNum(UserInfoCheckPhoneNumParam userInfoCheckPhoneNumParam) {
        log.info("还没开始");
        UserInfo userInfo = userInfoMapper.selectById(userInfoCheckPhoneNumParam.getUserId());
        if (!userInfoCheckPhoneNumParam.getMobile().equals(userInfo.getMobile())) {
            return CommonResult.failed(BusinessError.USER_EX_MOBILE_NOT_SAME);
        }
        UserInfo entiy = userInfoMapper.selectByMobile(userInfoCheckPhoneNumParam.getMobile());
        if (null == entiy) {
            return CommonResult.failed(BusinessError.MOBILE_NOT_REGISTER);
        }
        smsInfoService.checkVerifyCode(userInfoCheckPhoneNumParam.getMobile(), SmsInfo.SmsType.AUTH, userInfoCheckPhoneNumParam.getVerifyCode());
        return CommonResult.success();
    }

    public CommonResult changePhoneNum(UserInfoChangePhoneNumParam userInfoChangePhoneNumParam) {
        //验证码校验
        smsInfoService.checkVerifyCode(userInfoChangePhoneNumParam.getMobile(), SmsInfo.SmsType.AUTH, userInfoChangePhoneNumParam.getVerifyCode());

        UserInfo userInfo = userInfoMapper.selectById(userInfoChangePhoneNumParam.getUserId());
        String newMobile = userInfoChangePhoneNumParam.getMobile();
        if (newMobile.equals(userInfo.getMobile())) {
            return CommonResult.failed(BusinessError.USER_MOBILE_REPEATED);
        }
        UserInfo entity = userInfoMapper.selectByMobile(newMobile);
        if (null != entity) {
            log.info("entity不为空，异常报错结束！");
            return CommonResult.failed(BusinessError.USER_MOBILE_EXIST);
        }
        UserAuth userAuth = userAuthMapper.selectByIdentifier(newMobile);
        if (null != userAuth) {
            log.warn("该手机号已注册：{}", newMobile);
            return CommonResult.failed(BusinessError.MOBILE_WAS_REGISTERED);
        }
        //更换为新手机号登录
        UserAuth thisUserAuth = userAuthMapper.selectByIdentifier(userInfo.getMobile());
        thisUserAuth.setIdentifier(newMobile);
        userAuthMapper.updateSelective(thisUserAuth);


        userInfo.setMobile(newMobile);
        //查询是否存在有效实人认证信息，同步变更手机号
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(userInfoChangePhoneNumParam.getUserId().toString());
        if (userAuthentication != null) {
            UserAuthentication updateUserAuth = new UserAuthentication();
            updateUserAuth.setId(userAuthentication.getId());
            updateUserAuth.setMobile(userInfoChangePhoneNumParam.getMobile());
            userAuthenticationMapper.updateSelective(updateUserAuth);
        }
        return CommonResult.success(userInfoMapper.updateSelective(userInfo));
    }

    public CommonResult accountCancel(Integer id){
        if(id != 14000049){
            ThingInfoQueryParam param = new ThingInfoQueryParam();
            param.setUserId(id);
            param.setIsDel(false);
            param.setCurrentState(1);
            List<ThingInfo> listThing = thingInfoMapper.selectList(param);
            if(listThing.size() > 0){
                return CommonResult.failed(USER_HAVING_THING_SELLING);
            }
            OrderInfoQueryParam orderParam = new OrderInfoQueryParam();
            orderParam.setBuyerId(id);
            orderParam.setIsDel(false);
            List<Integer> listInt = new ArrayList<>();
            listInt.add(1);
            listInt.add(2);
            listInt.add(3);
            listInt.add(4);
            orderParam.setOrderStatus(listInt);
            List<OrderInfo> listOrder = orderInfoMapper.selectList(orderParam);
            if(listOrder.size() > 0){
                return CommonResult.failed(USER_HAVING_ORDER_INFO_NO_END);
            }
            UserAuth userAuth = userAuthMapper.selectByUserId(id);
            if(userAuth != null){
                userAuthService.deleteById(userAuth.getId());
            }
            userInfoService.deleteById(id);

            UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(id.toString());
            if(userAuthentication != null){
                userAuthenticationService.deleteById(userAuthentication.getId());
            }
            List<UserFansListDetail> userFocus = userFansMapper.selectFocusList(id.longValue());
            List<UserFansListDetail> userFans = userFansMapper.selectFansList(id.longValue());
            userFocus.forEach(userFansListDetail ->{
                UserFans userFocus1 = new UserFans();
                userFocus1.setId(userFansListDetail.getId());
                userFocus1.setIsDel(true);
                userFansMapper.updateSelective(userFocus1);
            });
            userFans.forEach(userFansListDetail ->{
                UserFans userFans1 = new UserFans();
                userFans1.setId(userFansListDetail.getId());
                userFans1.setIsDel(true);
                userFansMapper.updateSelective(userFans1);
            });
            return CommonResult.success();
        }
        return CommonResult.failed(USER_NO_CHANNEL);
    }
}
