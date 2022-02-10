package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.UserFansAssembler;
import com.armsmart.jupiter.bs.api.dao.UserCollectMapper;
import com.armsmart.jupiter.bs.api.dao.UserFansMapper;
import com.armsmart.jupiter.bs.api.dto.request.ThingUserIdStateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserFansAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserFansQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserFansUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingUserIdStateDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansCountDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansListDetail;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.entity.UserFans;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.armsmart.jupiter.bs.api.error.BusinessError.USER_FANS_NOT_EXIST;
import static com.armsmart.jupiter.bs.api.error.BusinessError.USER_FOCUS_EXIST;
import static com.armsmart.jupiter.bs.api.error.BusinessError.USER_FOCUS_NOT_MYSELF;

/**
 * UserFans service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class UserFansService {

    @Autowired(required = false)
    private UserFansMapper userFansMapper;

    @Autowired(required = false)
    private UserCollectMapper userCollectMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<UserFansDetail> selectPage(UserFansQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserFans> pageInfo = new PageInfo<>(userFansMapper.selectList(query));
        List<UserFansDetail> dtoList = UserFansAssembler.INSTANCE.getUserFansDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 添加关注
     *
     * @param userId
     * @return com.armsmart.jupiter.bs.api.entity.UserFans
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(Long userId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return CommonResult.failed();
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            UserFansAddParam userFansAddParam = new UserFansAddParam();
            if (userId.intValue() == appUserDetails.getUserInfo().getId()) {
                return CommonResult.failed(USER_FOCUS_NOT_MYSELF);
            }
            userFansAddParam.setUserId(appUserDetails.getUserInfo().getId().longValue());
            userFansAddParam.setFocusUserId(userId);
            UserFans userFans = userFansMapper.selectFans(userFansAddParam);
            if (userFans != null) {
                return CommonResult.failed(USER_FOCUS_EXIST);
            }
            UserFans entity = UserFansAssembler.INSTANCE.getUserFans(userFansAddParam);
            if (exiesUserId(userFansAddParam)) {
                entity.setFocusState(2);
                changeState(userFansAddParam);
            } else {
                entity.setFocusState(1);
            }
            userFansMapper.insert(entity);
            return CommonResult.success(entity.getId());
        }
    }

    /**
     * 取消关注
     *
     * @param userId
     * @return com.armsmart.jupiter.bs.api.entity.UserFans
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult cancel(Long userId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return CommonResult.failed();
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            UserFansAddParam userFansAddParam = new UserFansAddParam();
            userFansAddParam.setFocusUserId(userId);
            userFansAddParam.setUserId(appUserDetails.getUserInfo().getId().longValue());
            UserFans entity = userFansMapper.selectFans(userFansAddParam);
            if (entity == null) {
                return CommonResult.failed(USER_FANS_NOT_EXIST);
            }
            if (entity.getFocusState().equals(2)) {
                UserFansAddParam param = new UserFansAddParam();
                param.setUserId(entity.getUserId());
                param.setFocusUserId(entity.getFocusUserId());
                returnState(param);
            }
            return CommonResult.success(deleteById(entity.getId()));

        }
    }

    /**
     * 修改数据
     *
     * @param userFansUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(UserFansUpdateParam userFansUpdateParam) {
        UserFans entity = UserFansAssembler.INSTANCE.getUserFans(userFansUpdateParam);
        userFansMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id) {
        UserFans entity = new UserFans();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        userFansMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
        return CommonResult.success();
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.UserFansDetail
     * @date 2020/01/01
     */
    public CommonResult<UserFansDetail> selectById(Long id) {
        UserFans userFans = userFansMapper.selectById(id);
        UserFansDetail userFansDetail = UserFansAssembler.INSTANCE.getUserFansDetail(userFans);
        return CommonResult.success(userFansDetail);
    }

    /**
     * @param userId 用户id
     * @serialData 2021/9/13
     * 用户关注数和粉丝数
     */
    public CommonResult<UserFansCountDetail> selectCount(Long userId) {
        UserFansCountDetail userFansCountDetail = userFansMapper.selectCount(userId);
        return CommonResult.success(userFansCountDetail);
    }

    /**
     * 查询关注的userId是否也关注自己
     */
    public Boolean exiesUserId(UserFansAddParam userFansAddParam) {
        Integer ii = userFansMapper.exiesUserId(userFansAddParam);
        if (ii > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CommonResult changeState(UserFansAddParam userFansAddParam) {
        userFansMapper.changeState(userFansAddParam);
        return CommonResult.success();
    }

    public CommonResult returnState(UserFansAddParam userFansAddParam) {
        userFansMapper.returnState(userFansAddParam);
        return CommonResult.success();
    }

    /**
     * 关注用户信息
     */
    public CommonPage<UserFansListDetail> selectFocusList(UserIdQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserFansListDetail> pageInfo = new PageInfo<>(userFansMapper.selectFocusList(query.getUserId()));
        log.info(pageInfo.getList().toString());
        List<UserFansListDetail> dtoList = pageInfo.getList();
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 粉丝用户信息
     */
    public CommonPage<UserFansListDetail> selectFansList(UserIdQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserFansListDetail> pageInfo = new PageInfo<>(userFansMapper.selectFansList(query.getUserId()));
        List<UserFansListDetail> dtoList = pageInfo.getList();
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 查询对当前用户状态
     */
    public CommonResult<ThingUserIdStateDetail> getFocusState(ThingUserIdStateParam param) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ThingUserIdStateDetail detail = new ThingUserIdStateDetail();
        if ("anonymousUser".equals(principal)) {
            detail.setCollectState(Integer.valueOf(-1));
            detail.setFocusState(Integer.valueOf(-1));
            return CommonResult.success(detail);
        } else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            //关注状态
            if (appUserDetails.getUserInfo().getId().longValue() == param.getUserId()) {
                detail.setFocusState(0);
                detail.setCollectState(0);
            } else {
                UserFansAddParam userFansAddParam = new UserFansAddParam();
                userFansAddParam.setUserId(appUserDetails.getUserInfo().getId().longValue());
                userFansAddParam.setFocusUserId(param.getUserId());
                UserFans userFans = userFansMapper.selectFans(userFansAddParam);
                if (userFans == null) {
                    detail.setFocusState(Integer.valueOf(-1));
                } else {
                    detail.setFocusState(1);
                }
            }
            //收藏状态
            UserCollectAddParam userCollectAddParam = new UserCollectAddParam();
            userCollectAddParam.setUserId(appUserDetails.getUserInfo().getId().longValue());
            userCollectAddParam.setThingId(param.getThingId());
            UserCollect userCollect = userCollectMapper.selectInfo(userCollectAddParam);
            if (userCollect == null) {
                log.info("--------------------userCollect is null------------------");
                detail.setCollectState(Integer.valueOf(-1));
            } else {
                detail.setCollectState(1);
            }
            return CommonResult.success(detail);
        }

    }

    /**
     * 获取用户的关注状态
     *
     * @param userId      用户ID
     * @param focusUserId 关注用户ID
     * @return com.armsmart.jupiter.bs.api.entity.UserFans
     * @date 2021/12/16
     */
    public UserFans getUserFans(Integer userId, Integer focusUserId) {
        UserFansAddParam userFansAddParam = new UserFansAddParam();
        userFansAddParam.setUserId(userId.longValue());
        userFansAddParam.setFocusUserId(focusUserId.longValue());
        return userFansMapper.selectFans(userFansAddParam);
    }


}
