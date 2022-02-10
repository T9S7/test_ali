package com.armsmart.jupiter.bs.api.service;


import cn.hutool.core.util.RandomUtil;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.ResultCode;
import com.armsmart.common.config.JwtConfig;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.common.utils.JwtTokenUtil;
import com.armsmart.jupiter.bs.api.assembler.SysUserAssembler;
import com.armsmart.jupiter.bs.api.constants.IdentityType;
import com.armsmart.jupiter.bs.api.dao.SysUserAuthMapper;
import com.armsmart.jupiter.bs.api.dao.SysUserMapper;
import com.armsmart.jupiter.bs.api.dto.request.SysUserAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserChangePwdParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserLoginParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserVerifyCodeLoginParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.dto.response.SysUserDetail;
import com.armsmart.jupiter.bs.api.dto.response.SysUserLoginResult;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import com.armsmart.jupiter.bs.api.entity.SysRole;
import com.armsmart.jupiter.bs.api.entity.SysUser;
import com.armsmart.jupiter.bs.api.entity.SysUserAuth;
import com.armsmart.jupiter.bs.api.error.ManageError;
import com.armsmart.jupiter.bs.api.security.AdminUserDetails;
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

import java.io.Serializable;
import java.util.List;

/**
 * SysUser service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserAuthMapper sysUserAuthMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private SmsInfoService smsInfoService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<SysUserDetail> selectPage(SysUserQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserMapper.selectList(query));
        List<SysUserDetail> dtoList = SysUserAssembler.INSTANCE.getSysUserDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 根据认证信息加载用户详情
     *
     * @param identifier
     * @return org.springframework.security.core.userdetails.UserDetails
     * @date 2021/1/2
     */
    public UserDetails loadUserByUsername(String identifier) {
        SysUserAuth sysUserAuth = sysUserAuthMapper.selectByIdentifier(identifier);
        if (null == sysUserAuth) {
            throw new BusinessException(ManageError.WRONG_USERNAME_OR_PWD);
        }
        SysUser sysUser = sysUserMapper.selectById(sysUserAuth.getUserId());
        if (sysUser == null || null == sysUser.getRoleId()) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        List<SysResourceDetail> sysResources = sysRoleService.selectRolePermission(sysUser.getRoleId());
        if (CollectionUtils.isEmpty(sysResources)) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        return new AdminUserDetails(sysUser, sysUserAuth, sysResources);
    }

    /**
     * 用户登录-验证码
     *
     * @param loginParam 登录参数
     * @return UserLoginResult
     * @date 2021/1/2 0002
     */
    public SysUserLoginResult login(SysUserVerifyCodeLoginParam loginParam) {
        String identifier = loginParam.getMobile();
        smsInfoService.checkVerifyCode(identifier, SmsInfo.SmsType.AUTH, loginParam.getVerifyCode());
        return loginCheck(identifier, null, false);
    }

    /**
     * 登录校验
     *
     * @param identifier
     * @param password
     * @param checkPwd
     * @return com.armsmart.jupiter.bs.api.dto.response.SysUserLoginResult
     * @date 2021/9/5
     */
    private SysUserLoginResult loginCheck(String identifier, String password, boolean checkPwd) {
        AdminUserDetails userDetails = (AdminUserDetails) loadUserByUsername(identifier);
        if (userDetails == null) {
            throw new BusinessException(ManageError.WRONG_USERNAME_OR_PWD);
        }
        if (checkPwd && !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BusinessException(ManageError.WRONG_USERNAME_OR_PWD);
        }
        if (!userDetails.isEnabled()) {
            log.warn("System user {} was disabled!", identifier);
            throw new BusinessException(ManageError.USER_IS_DISABLED);
        }
        SysUser sysUser = userDetails.getSysUser();
        SysRole sysRole = sysRoleService.selectById(sysUser.getRoleId());
        if (!sysRole.getIsEnable()) {
            log.warn("System role {} was disabled!", sysRole.getRoleName());
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authCode = RandomUtil.randomString(10);
        return SysUserAssembler.INSTANCE.getSysUserLoginResult(sysUser, sysRole, jwtTokenUtil.generateToken(userDetails, false, authCode), jwtConfig.getTokenHead());
    }

    /**
     * 用户登录-密码
     *
     * @param sysUserLoginParam 登录参数
     * @return jSysUserLoginResult
     * @date 2021/1/2 0002
     */
    public SysUserLoginResult login(SysUserLoginParam sysUserLoginParam) {
        return loginCheck(sysUserLoginParam.getIdentifier(), sysUserLoginParam.getPassword(), true);
    }

    /**
     * 插入数据
     *
     * @param sysUserAddParam
     * @return SysUser
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(SysUserAddParam sysUserAddParam) {
        SysUserAuth sysUserAuth = sysUserAuthMapper.selectByIdentifier(sysUserAddParam.getUsername());
        SysUser sysUser = sysUserMapper.selectByUsername(sysUserAddParam.getUsername());
        if (null != sysUserAuth || null != sysUser) {
            throw new BusinessException(ManageError.USERNAME_IS_EXIST);
        }
        SysUserAuth phoneAuth = sysUserAuthMapper.selectByIdentifier(sysUserAddParam.getTelephone());
        if (null != phoneAuth) {
            throw new BusinessException(ManageError.TELEPHONE_IS_EXIST);
        }
        if (StringUtils.hasText(sysUserAddParam.getEmail())) {
            SysUserAuth emailAuth = sysUserAuthMapper.selectByIdentifier(sysUserAddParam.getEmail());
            if (null != emailAuth) {
                throw new BusinessException(ManageError.EMAIL_IS_EXIST);
            }
        }
        SysUser entity = SysUserAssembler.INSTANCE.getSysUser(sysUserAddParam);
        sysUserMapper.insert(entity);
        Integer userId = entity.getUserId();
        //用户鉴权信息
        insertAuth(entity.getTelephone(), userId, IdentityType.MOBILE);
        insertAuth(entity.getUsername(), userId, IdentityType.USERNAME);
        if (StringUtils.hasText(entity.getEmail())) {
            insertAuth(entity.getEmail(), userId, IdentityType.EMAIL);
        }
        return userId;
    }

    private void insertAuth(String identifier, Integer userId, IdentityType type) {
        String pwd = passwordEncoder.encode("123456");
        SysUserAuth sysUserAuth = new SysUserAuth();
        sysUserAuth.setIdentityType(type.getCode());
        sysUserAuth.setIdentifier(identifier);
        sysUserAuth.setUserId(userId);
        sysUserAuth.setCreateTime(System.currentTimeMillis());
        sysUserAuth.setIsDel(false);
        sysUserAuth.setCredential(pwd);
        sysUserAuthMapper.insert(sysUserAuth);
    }

    /**
     * 修改数据
     *
     * @param sysUserUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserUpdateParam sysUserUpdateParam) {
        SysUser sysUser = sysUserMapper.selectById(sysUserUpdateParam.getUserId());
        if (null == sysUser) {
            throw new BusinessException(ManageError.USER_IS_NOT_EXIST);
        }
        String newTelephone = sysUserUpdateParam.getTelephone();
        String newEmail = sysUserUpdateParam.getEmail();
        //更换手机号
        if (StringUtils.hasText(newTelephone) && !newTelephone.equals(sysUser.getTelephone())) {
            SysUserAuth auth = sysUserAuthMapper.selectByIdentifier(sysUser.getTelephone());
            auth.setIdentifier(newTelephone);
            auth.setUpdateTime(System.currentTimeMillis());
            sysUserAuthMapper.updateSelective(auth);
        }
        //更换邮箱
        if (StringUtils.hasText(newEmail) && !newEmail.equals(sysUser.getEmail())) {
            SysUserAuth auth = sysUserAuthMapper.selectByIdentifier(sysUser.getEmail());
            auth.setIdentifier(newEmail);
            auth.setUpdateTime(System.currentTimeMillis());
            sysUserAuthMapper.updateSelective(auth);
        }
        SysUser entity = SysUserAssembler.INSTANCE.getSysUser(sysUserUpdateParam);
        entity.setUsername(sysUser.getUsername());
        sysUserMapper.updateSelective(entity);
    }

    /**
     * 设置启用/禁用
     *
     * @param userId   用户ID
     * @param isEnable true表示启用
     * @return void
     */
    public void setIsEnable(Integer userId, boolean isEnable) {
        SysUser entity = new SysUser();
        entity.setUserId(userId);
        entity.setIsEnable(isEnable);
        sysUserMapper.updateSelective(entity);
    }

    /**
     * 根据主键删除
     *
     * @param userId
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer userId) {
        SysUser entity = new SysUser();
        entity.setUserId(userId);
        entity.setIsDel(true);
        sysUserMapper.updateSelective(entity);
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
     * @param userId 主键ID
     * @return SysUserDetail
     * @date 2021/03/01
     */
    public SysUserDetail selectById(Serializable userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        SysRole sysRole = sysRoleService.selectById(sysUser.getRoleId());
        SysUserDetail sysUserDetail = SysUserAssembler.INSTANCE.getSysUserDetail(sysUser, sysRole);
        return sysUserDetail;
    }

    /**
     * 修改密码
     *
     * @param changePwdParam
     */
    public void changePwd(SysUserChangePwdParam changePwdParam) {
        SysUser sysUser = sysUserMapper.selectByUsername(changePwdParam.getUsername());
        SysUserAuth sysUserAuth = sysUserAuthMapper.selectByIdentifier(changePwdParam.getUsername());
        if (null == sysUser || null == sysUserAuth) {
            throw new BusinessException(ManageError.USER_IS_NOT_EXIST);
        }
        if (!passwordEncoder.matches(changePwdParam.getOldPassword(), sysUserAuth.getCredential())) {
            throw new BusinessException(ManageError.USER_OLD_PWD_NOT_MATCH);
        }
        List<SysUserAuth> sysUserAuthList = sysUserAuthMapper.selectByUserId(sysUser.getUserId());
        if (!CollectionUtils.isEmpty(sysUserAuthList)) {
            for (SysUserAuth userAuth : sysUserAuthList) {
                SysUserAuth updateEntity = new SysUserAuth();
                updateEntity.setId(userAuth.getId());
                updateEntity.setCredential(passwordEncoder.encode(changePwdParam.getNewPassword()));
                sysUserAuthMapper.updateSelective(updateEntity);
            }
        }

    }


}
