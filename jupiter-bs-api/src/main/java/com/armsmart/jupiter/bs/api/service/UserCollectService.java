package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.BidInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingSellInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.CollectSelectListResult;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoBidDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingSelectListResult;
import com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectQueryParam;
import com.armsmart.jupiter.bs.api.dao.UserCollectMapper;
import com.armsmart.jupiter.bs.api.assembler.UserCollectAssembler;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * UserCollect service
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class UserCollectService {

    @Autowired(required = false)
    private  UserCollectMapper userCollectMapper;

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;

    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;

    @Autowired(required = false)
    private BidInfoMapper bidInfoMapper;
    /**
     * 分页查询
     * @param query 查询条件
     * @date 2020/01/01
     * @return PageInfo
     */
    public CommonPage<UserCollectDetail> selectPage(UserCollectQueryParam query){
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        PageInfo<UserCollect> pageInfo = new PageInfo<>(userCollectMapper.selectList(query));
        List<UserCollectDetail> dtoList = UserCollectAssembler.INSTANCE.getUserCollectDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 添加收藏
     * @param  thingId
     * @date 2020/01/01
     * @return com.armsmart.jupiter.bs.api.entity.UserCollect
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(Long thingId){
        UserCollectAddParam userCollectAddParam = new UserCollectAddParam();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal)){
            return CommonResult.failed();
        }else {
            AppUserDetails appUserDetails = (AppUserDetails) principal;
            userCollectAddParam.setUserId(appUserDetails.getUserInfo().getId().longValue());
            userCollectAddParam.setThingId(thingId);
            UserCollect userCollect = selectInfo(userCollectAddParam);
            if(userCollect == null){
                UserCollect entity = UserCollectAssembler.INSTANCE.getUserCollect(userCollectAddParam);
                userCollectMapper.insert(entity);
            }else {
                if(userCollect.getIsDel()){
                    UserCollectUpdateParam userCollectUpdateParam = new UserCollectUpdateParam();
                    userCollectUpdateParam.setId(userCollect.getId());
                    userCollectUpdateParam.setIsDel(false);
                    userCollectUpdateParam.setCreateTime(userCollect.getCreateTime());
                    userCollectUpdateParam.setUpdateTime(System.currentTimeMillis());
                    userCollectUpdateParam.setThingId(userCollect.getThingId());
                    update(userCollectUpdateParam);
                }
            }
        }

        return CommonResult.success();
     }

     public CommonResult center(Long thingId){
         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         if("anonymousUser".equals(principal)){
             return CommonResult.failed();
         }else {
             AppUserDetails appUserDetails = (AppUserDetails) principal;
             UserCollectAddParam param = new UserCollectAddParam();
             param.setThingId(thingId);
             param.setUserId(appUserDetails.getUserInfo().getId().longValue());
             UserCollect userCollect = userCollectMapper.selectInfo(param);
             if(userCollect == null){
                 return CommonResult.failed();
             }
             return deleteById(userCollect.getId());
         }
     }

    /**
     * 修改数据
     * @param userCollectUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(UserCollectUpdateParam userCollectUpdateParam){
        UserCollect entity = UserCollectAssembler.INSTANCE.getUserCollect(userCollectUpdateParam);
        userCollectMapper.updateSelective(entity);
        return CommonResult.success();
    }
    /**
     * 根据主键删除
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteById(Long id){
        UserCollect entity = new UserCollect();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        userCollectMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 根据主键批量删除
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult batchDel(List<Long> ids){
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
     * @return com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail
     */
    public CommonResult<UserCollectDetail> selectById(Long id) {
        UserCollect userCollect = userCollectMapper.selectById(id);
        UserCollectDetail userCollectDetail = UserCollectAssembler.INSTANCE.getUserCollectDetail(userCollect);
        return CommonResult.success(userCollectDetail);
    }

    /**
     * 判断当前物品当前用户查询记录
     * @param userCollectAddParam
     * @return com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail
     * */
    public UserCollect selectInfo(UserCollectAddParam userCollectAddParam){
        UserCollect userCollect = userCollectMapper.selectInfo(userCollectAddParam);
        return userCollect;
    }


    /**
     * 用户收藏数
     * */
    public CommonResult<Integer> countByUserId(Long userId){
        int i = userCollectMapper.countByUserId(userId);
    return CommonResult.success(i);
    }

    /**
     * 收藏列表
     * 收藏物品只收藏在售物品
     * @param param
     * @return
     */
    public CommonPage<CollectSelectListResult> selectCollectList(UserIdQueryParam param){
        UserCollectQueryParam userCollectQueryParam = new UserCollectQueryParam();
        userCollectQueryParam.setUserId(param.getUserId());
        userCollectQueryParam.setIsDel(false);
        userCollectQueryParam.setPageSize(param.getPageSize());
        userCollectQueryParam.setPageNum(param.getPageNum());
//        List<UserCollectDetail> userCollectList = selectPage(userCollectQueryParam).getList();//userCollectMapper.selectList(query)
        List<UserCollect> userCollectList = userCollectMapper.selectList(userCollectQueryParam);
//        List<Long> thingList = new ArrayList<>();
        if(userCollectList == null){
            PageHelper.startPage(param.getPageNum(),param.getPageSize());
            PageInfo<CollectSelectListResult> pageInfo = new PageInfo<>();
            List<CollectSelectListResult> dtoList = pageInfo.getList();
            return CommonPage.restPage(pageInfo, dtoList);
        }
        List<CollectSelectListResult> thingSelectListResultList = new ArrayList<>();
        for(int i = 0;i < userCollectList.size();i++) {
//            thingList.add(userCollectList.get(i).getThingId());
            Long thingId = userCollectList.get(i).getThingId();
            CollectSelectListResult thingSelectList = new CollectSelectListResult();
            ThingInfo thingInfo = thingInfoMapper.selectById(thingId);
            if (thingInfo == null || thingInfo.getCurrentState() != 1 || !thingInfo.getUploadStatus()|| thingInfo.getIsDel()) {
                continue;
            }
            UserCollectAddParam userCollectAddParam = new UserCollectAddParam();
            userCollectAddParam.setThingId(thingId);
            userCollectAddParam.setUserId(param.getUserId());
            UserCollect userCollect = userCollectMapper.selectInfo(userCollectAddParam);
            thingSelectList.setId(userCollect.getId());
            thingSelectList.setThingId(thingId);
            thingSelectList.setUserId(thingInfo.getUserId());
            thingSelectList.setPics(thingInfo.getPics());
            thingSelectList.setCurrentPrice(thingInfo.getCurrentPrice());
            thingSelectList.setArtName(thingInfo.getArtName());
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
            if (thingSellInfo == null) {
                thingSelectListResultList.add(thingSelectList);
                continue;
            }
            log.info("当前收藏信息：{}",thingId);
            if (thingSellInfo.getSellType() == 2) {
                if (thingSellInfo.getAuctionStartTime() > System.currentTimeMillis()) {
                    thingSelectList.setAuctionState(0);//拍卖前
                    thingSelectList.setAuctionTime(thingSellInfo.getAuctionStartTime());
                }else if (thingSellInfo.getAuctionEndTime() >= System.currentTimeMillis()
                        && thingSellInfo.getAuctionStartTime() < System.currentTimeMillis()) {
                    thingSelectList.setAuctionState(1);//拍卖中
                    thingSelectList.setAuctionTime(thingSellInfo.getAuctionEndTime());
                }else {
                    thingSelectList.setAuctionState(-1);//拍卖已结束
                }
            }

            thingSelectList.setSellType(thingSellInfo.getSellType());
            thingSelectListResultList.add(thingSelectList);
        }
        PageInfo<CollectSelectListResult> pageInfo = new PageInfo<>(thingSelectListResultList);

        return CommonPage.restPage(pageInfo, thingSelectListResultList);
    }
}
