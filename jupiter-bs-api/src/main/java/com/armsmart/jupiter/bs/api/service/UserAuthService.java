package com.armsmart.jupiter.bs.api.service;


import cn.hutool.core.util.RandomUtil;
import com.armsmart.common.config.JwtConfig;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.common.utils.JwtTokenUtil;
import com.armsmart.jupiter.bs.api.assembler.UserAuthAssembler;
import com.armsmart.jupiter.bs.api.assembler.decorator.UserInfoDecorator;
import com.armsmart.jupiter.bs.api.cache.AuthCodeCacheService;
import com.armsmart.jupiter.bs.api.constants.AppUserType;
import com.armsmart.jupiter.bs.api.constants.IdentityType;
import com.armsmart.jupiter.bs.api.dao.UserAuthMapper;
import com.armsmart.jupiter.bs.api.dao.UserAuthenticationMapper;
import com.armsmart.jupiter.bs.api.dao.UserCompanyMapper;
import com.armsmart.jupiter.bs.api.dao.UserInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.MobileVerifyLoginParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthAdminAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthChangePhoneNumParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserMobileLoginParam;
import com.armsmart.jupiter.bs.api.dto.request.UserModifyPwdParam;
import com.armsmart.jupiter.bs.api.dto.request.UserResetPwdParam;
import com.armsmart.jupiter.bs.api.dto.request.UserVerifyCodeLoginParam;
import com.armsmart.jupiter.bs.api.dto.response.*;
import com.armsmart.jupiter.bs.api.entity.*;
import com.armsmart.jupiter.bs.api.error.BusinessError;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import com.armsmart.jupiter.bs.api.sms.AliSmsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

import static com.armsmart.jupiter.bs.api.error.BusinessError.USER_MOBILE_VERIFY_FAILED;

/**
 * UserAuth service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class UserAuthService {

    @Autowired(required = false)
    private UserAuthMapper userAuthMapper;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SmsInfoService smsInfoService;
    @Autowired
    private AliSmsService aliSmsService;
    @Autowired
    private MobileVerifyService mobileVerifyService;
    @Autowired
    private AuthCodeCacheService authCodeCacheService;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;

    @Autowired(required = false)
    private UserCompanyMapper userCompanyMapper;

    /**
     * 根据用户名加载用户详情
     *
     * @param username
     * @return org.springframework.security.core.userdetails.AppUserDetails
     * @date 2021/1/2
     */
    public UserDetails loadUserByUsername(String username) {
        UserAuth userAuth = userAuthMapper.selectByIdentifier(username);
        if (null == userAuth) {
            throw new BusinessException(BusinessError.WRONG_USERNAME_OR_PWD);
        }
        UserInfo userInfo = userInfoMapper.selectById(userAuth.getUserId());
        return new AppUserDetails(userAuth, userInfo);
    }

    public CheckTokenResult checkToken(HttpServletRequest request) {
        CheckTokenResult result = new CheckTokenResult();
        result.setOutOfDate(false);
        String authHeader = request.getHeader(jwtConfig.getTokenHeader());
        String authToken = authHeader.substring(jwtConfig.getTokenHead().length());
        long expireTime = jwtTokenUtil.getExpiredDateFromToken(authToken).getTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= 3 * 24 * 60 * 60 * 1000) {
            String refreshToken = jwtTokenUtil.refreshHeadToken(authToken);
            result.setRefreshToken(refreshToken);
            result.setOutOfDate(true);
            return result;
        }
        return result;
    }


    /**
     * 阿里云号码认证登录
     *
     * @param loginParam 登录参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public UserLoginResult login(MobileVerifyLoginParam loginParam) throws Exception {
        String identifier = mobileVerifyService.getMobile(loginParam.getAccessToken());
        if (StringUtils.hasText(identifier)) {
            UserAuth userAuth = userAuthMapper.selectByIdentifier(identifier);
            if (null == userAuth) {
                String pwd = RandomUtil.randomString(6);
                insert(identifier, pwd, AppUserType.NORMAL);
                log.info("用户{}号码认证注册成功！", identifier);
            }
            UserAuth userAuth1 = userAuthMapper.selectByIdentifier(identifier);
            UserInfo userInfo = userInfoMapper.selectById(userAuth1.getUserId());
            AppUserDetails userDetails = new AppUserDetails(userAuth1, userInfo);
            return login(identifier, null, loginParam.getRegistrationId(), loginParam.getPlatform(), userDetails, false);
        }
        throw new BusinessException(USER_MOBILE_VERIFY_FAILED);
    }

    /**
     * 验证码登录
     *
     * @param loginParam 登录参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public UserLoginResult login(UserVerifyCodeLoginParam loginParam) {
        String identifier = loginParam.getMobile();


        UserAuth userAuth = userAuthMapper.selectByIdentifier(identifier);
        if (null == userAuth) {
            String pwd = RandomUtil.randomString(6);
            insert(identifier, pwd, AppUserType.NORMAL);
            log.info("用户{}验证码注册成功！", identifier);
            userAuth = userAuthMapper.selectByIdentifier(identifier);
        }
        UserInfo userInfo = userInfoMapper.selectById(userAuth.getUserId());
        AppUserDetails userDetails = new AppUserDetails(userAuth, userInfo);
        if(!identifier.equals("18056891058")){ //系统上线测试账号
            smsInfoService.checkVerifyCode(identifier, SmsInfo.SmsType.REG_LOGIN, loginParam.getVerifyCode());
        }
        return login(identifier, null, loginParam.getRegistrationId(), loginParam.getPlatform(), userDetails, false);
    }


    /**
     * 手机号密码登录
     *
     * @param loginParam 登录参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public UserLoginResult login(UserMobileLoginParam loginParam) {
        String username = loginParam.getMobile();
        String password = loginParam.getPassword();
        AppUserDetails userDetails = (AppUserDetails) loadUserByUsername(username);
        return login(username, password, loginParam.getRegistrationId(), loginParam.getPlatform(), userDetails, true);
    }

    /**
     * 用户登录
     *
     * @param username       账号
     * @param password       密码
     * @param registrationId 消息推送设备ID
     * @param platform       平台
     * @param userDetails    用户详情
     * @param checkPwd       是否校验密码
     * @return UserLoginResult
     */
    private UserLoginResult login(String username, String password, String registrationId,
                                  Integer platform, AppUserDetails userDetails, boolean checkPwd) {
        if (userDetails == null) {
            log.warn("User {} login failed!", username);
            throw new BusinessException(BusinessError.MOBILE_NOT_REGISTER);
        }
        if (checkPwd) {
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                log.warn("User {} login failed!", username);
                throw new BusinessException(BusinessError.WRONG_USERNAME_OR_PWD);
            }
        }
        if (!userDetails.isEnabled()) {
            log.warn("User {} was disabled!", username);
            throw new BusinessException(BusinessError.USER_IS_DISABLED);
        }
        UserInfo userInfo = userInfoMapper.selectById(userDetails.getUserAuth().getUserId());
        String authCode = RandomUtil.randomString(10);
        UserInfo updateUserInfo = new UserInfo();
        updateUserInfo.setId(userInfo.getId());
        updateUserInfo.setRegistrationId(registrationId);
        updateUserInfo.setPlatform(platform);
        updateUserInfo.setUpdateTime(System.currentTimeMillis());
        updateUserInfo.setAuthCode(authCode);
        userInfoMapper.updateSelective(updateUserInfo);


        UserInfoDetail userInfoDetail = UserInfoDecorator.INSTANCE.getUserInfoDetail(userInfo);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(userInfoDetail.getId().toString());
        UserAuthenticationResult userAuthenticationResult = new UserAuthenticationResult();
        if( null != userAuthentication){
            userAuthenticationResult.setCertifino(userAuthentication.getCertificateNo());
            userAuthenticationResult.setCertifinoBack(userAuthentication.getCertificateBack());
            userAuthenticationResult.setCertifinoFace(userAuthentication.getCertificateFace());
            userAuthenticationResult.setIsBind(userAuthentication.getIsBind());
            userAuthenticationResult.setIsCert(userAuthentication.getIsCert());
            userAuthenticationResult.setName(userAuthentication.getName());
            userAuthenticationResult.setPubkeyE(userAuthentication.getPublicKeyE());
            userAuthenticationResult.setPubkeyM(userAuthentication.getPublicKeyM());
        }else{
            userAuthenticationResult.setIsBind(false);
            userAuthenticationResult.setIsCert(false);
        }
//        UserAuthenticationResult userAuthenticationResult = userAuthenticationMapper.loginResult(userInfoDetail.getId());
        UserLoginResult userLoginResult = new UserLoginResult();
        userLoginResult.setToken(jwtTokenUtil.generateToken(userDetails, true, authCode));
        userLoginResult.setTokenHead(jwtConfig.getTokenHead());
        userLoginResult.setUserInfo(userInfoDetail);
        userLoginResult.setUserAuthenticationResult(userAuthenticationResult);
        UserCompany userCompany = userCompanyMapper.selectByUserId(updateUserInfo.getId().longValue());
        if(userCompany != null && userCompany.getIsCompanyOfficial() ){
                userLoginResult.setUserType(1);
        }else {
            userLoginResult.setUserType(0);
        }
        log.info("User {} login succeed!", username);
        return userLoginResult;
    }

    /**
     * 用户重置密码
     *
     * @param resetPwdParam 重置密码参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public void resetPwd(UserResetPwdParam resetPwdParam) {
        String username = resetPwdParam.getMobile();
        String password = resetPwdParam.getPassword();
        UserAuth userAuth = userAuthMapper.selectByIdentifier(username);
        if (null == userAuth) {
            throw new BusinessException(BusinessError.MOBILE_NOT_REGISTER);
        }
        //double check
        try {
            smsInfoService.checkVerifyCode(username, SmsInfo.SmsType.AUTH, resetPwdParam.getVerifyCode());
        } catch (Exception e) {
            log.warn("", e);
            throw new BusinessException(BusinessError.AUTH_EXPIRE_OR_FAILED);
        }
        UserAuth updateEntity = new UserAuth();
        updateEntity.setId(userAuth.getId());
        updateEntity.setCredential(passwordEncoder.encode(password));
        userAuthMapper.updateSelective(updateEntity);
        log.info("User {} reset pwd succeed!", username);
    }

    /**
     * 用户修改密码
     *
     * @param modifyPwdParam 修改密码参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public void modifyPwd(UserModifyPwdParam modifyPwdParam) {
        String username = modifyPwdParam.getMobile();
        String oldPassword = modifyPwdParam.getOldPassword();
        String newPassword = modifyPwdParam.getNewPassword();
        UserAuth userAuth = userAuthMapper.selectByIdentifier(username);
        if (null == userAuth) {
            throw new BusinessException(BusinessError.WRONG_USERNAME_OR_PWD);
        }
        if (!passwordEncoder.matches(oldPassword, userAuth.getCredential())) {
            log.warn("User {} modify pwd failed!", username);
            throw new BusinessException(BusinessError.WRONG_USERNAME_OR_PWD);
        }
        UserAuth updateEntity = new UserAuth();
        updateEntity.setId(userAuth.getId());
        updateEntity.setCredential(passwordEncoder.encode(newPassword));
        userAuthMapper.updateSelective(updateEntity);
        log.info("User {} modify pwd succeed!", username);
    }

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public PageInfo<UserAuthDetail> selectPage(UserAuthQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserAuth> list = userAuthMapper.selectList(query);
        List<UserAuthDetail> dtoList = UserAuthAssembler.INSTANCE.getUserAuthDetailList(list);
        return new PageInfo<>(dtoList);
    }

    /**
     * 插入数据
     *
     * @param userAuthAddParam
     * @return UserAuth
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(UserAuthAddParam userAuthAddParam) {
        smsInfoService.checkVerifyCode(userAuthAddParam.getMobile(), SmsInfo.SmsType.REG_LOGIN, userAuthAddParam.getVerifyCode());
        UserAuth userAuth = userAuthMapper.selectByIdentifier(userAuthAddParam.getMobile());
        if (null != userAuth) {
            throw new BusinessException(BusinessError.MOBILE_WAS_REGISTERED);
        }
        return insert(userAuthAddParam.getMobile(), userAuthAddParam.getPassword(), AppUserType.NORMAL);
    }

    /**
     * 插入数据
     *
     * @param addParam
     * @return UserAuth
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(UserAuthAdminAddParam addParam) {
        UserAuth userAuth = userAuthMapper.selectByIdentifier(addParam.getMobile());
        if (null != userAuth) {
            throw new BusinessException(BusinessError.MOBILE_WAS_REGISTERED);
        }
        String pwd = RandomUtil.randomString(6);
        AppUserType userType = AppUserType.getByCode(addParam.getUserType());
        insert(addParam.getMobile(), pwd, userType);
        log.info("管理员注册APP用户[{}]！密码：{}", addParam.getMobile(), pwd);
        aliSmsService.sendAppUserPwd(addParam.getMobile(), pwd);
        return userAuth.getId();
    }


    private Integer insert(String mobile, String pwd, AppUserType appUserType) {
        //用户基本信息
        long registerTime = System.currentTimeMillis();
        UserInfo userInfo = new UserInfo();
        userInfo.setIsEnable(true);
        userInfo.setIsDel(false);
        userInfo.setNickname(appUserType.getName() + "_" + RandomUtil.randomString(6));
        userInfo.setRegisterTime(registerTime);
        userInfo.setUserType(appUserType.getCode());
        userInfo.setMobile(mobile);
        userInfo.setDeposit(0L);
        userInfo.setAccountRegisterStatus(0);
        userInfoMapper.insert(userInfo);
        //用户鉴权信息
        UserAuth entity = new UserAuth();
        entity.setIdentityType(IdentityType.MOBILE.getCode());
        entity.setIdentifier(mobile);
        entity.setUserId(userInfo.getId());
        entity.setIsDel(false);
        entity.setCredential(passwordEncoder.encode(pwd));
        userAuthMapper.insert(entity);
        return userInfo.getId();
    }


    /**
     * 根据登录账户查询
     *
     * @param identifier
     * @return UserAuth
     */
    public UserAuth selectByIdentifier(String identifier) {
        return userAuthMapper.selectByIdentifier(identifier);
    }


    public void changePhoneNum(UserAuthChangePhoneNumParam changePhoneNumParam) {
        UserAuth thisUserAuth = userAuthMapper.selectById(changePhoneNumParam.getUserId());
        if (changePhoneNumParam.getMobile().equals(thisUserAuth.getIdentifier())) {
            throw new BusinessException(BusinessError.USER_MOBILE_REPEATED);
        }
        UserAuth userAuth = userAuthMapper.selectByIdentifier(changePhoneNumParam.getMobile());
        if (null != userAuth) {
            throw new BusinessException(BusinessError.MOBILE_WAS_REGISTERED);
        }
        smsInfoService.checkVerifyCode(changePhoneNumParam.getMobile(), SmsInfo.SmsType.AUTH, changePhoneNumParam.getVerifyCode());
        thisUserAuth.setIdentifier(changePhoneNumParam.getMobile());
        userAuthMapper.updateSelective(thisUserAuth);
    }

    /**
     * 修改数据
     *
     * @param userAuthUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAuthUpdateParam userAuthUpdateParam) {
        UserAuth entity = UserAuthAssembler.INSTANCE.getUserAuth(userAuthUpdateParam);
        userAuthMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(java.lang.Integer id) {
        UserAuth entity = new UserAuth();
        entity.setId(id);
        entity.setIsDel(true);
        userAuthMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<java.lang.Integer> ids) {
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
     * @return UserAuthDetail
     * @date 2021/03/01
     */
    public UserAuthDetail selectById(java.lang.Integer id) {
        UserAuth userAuth = userAuthMapper.selectById(id);
        UserAuthDetail userAuthDetail = UserAuthAssembler.INSTANCE.getUserAuthDetail(userAuth);
        return userAuthDetail;
    }

}
