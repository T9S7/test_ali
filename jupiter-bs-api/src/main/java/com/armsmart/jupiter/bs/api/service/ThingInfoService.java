package com.armsmart.jupiter.bs.api.service;

import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.common.api.ResultCode;
import com.armsmart.jupiter.bs.api.assembler.ThingInfoAssembler;
import com.armsmart.jupiter.bs.api.blockchain.BlockChainNftService;
import com.armsmart.jupiter.bs.api.constants.AppUserType;
import com.armsmart.jupiter.bs.api.constants.BidStatesType;
import com.armsmart.jupiter.bs.api.constants.SellType;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dao.BidInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ContractInfoMapper;
import com.armsmart.jupiter.bs.api.dao.OrderInfoMapper;
import com.armsmart.jupiter.bs.api.dao.PushInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingDealInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingInfoMapper;
import com.armsmart.jupiter.bs.api.dao.ThingSellInfoMapper;
import com.armsmart.jupiter.bs.api.dao.UserAuthenticationMapper;
import com.armsmart.jupiter.bs.api.dao.UserCollectMapper;
import com.armsmart.jupiter.bs.api.dao.UserCompanyMapper;
import com.armsmart.jupiter.bs.api.dao.UserFansMapper;
import com.armsmart.jupiter.bs.api.dao.UserInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.ModifyOwnerAddInfoParam;
import com.armsmart.jupiter.bs.api.dto.request.ModifyOwnerAddParam;
import com.armsmart.jupiter.bs.api.dto.request.NfcInfoLoadParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInputInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserIdQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.response.NfcReadDetail;
import com.armsmart.jupiter.bs.api.dto.response.SellerInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingIdInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoBidDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoListDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingNftInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingSelectListResult;
import com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo;
import com.armsmart.jupiter.bs.api.dto.response.UserCountInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.UserFansCountDetail;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.entity.ThingDealInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.entity.UserFans;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import com.armsmart.jupiter.bs.api.error.NEError;
import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.armsmart.jupiter.bs.api.error.NEError.ADDR_NOT_EXIST_OR_SELL_FAILE;
import static com.armsmart.jupiter.bs.api.error.NEError.THING_GIVE_NO_EXIST;
import static com.armsmart.jupiter.bs.api.error.NEError.THING_GIVE_NO_MATCH;
import static com.armsmart.jupiter.bs.api.error.NEError.THING_HAVE_GIVE;
import static com.armsmart.jupiter.bs.api.error.NEError.USER_HAVE_NO_CERT;
import static com.armsmart.jupiter.bs.api.error.NftError.ART_TITLE_NOT_EXIST_FILE;

/**
 * ThingInfo service
 *
 * @author 苏礼刚
 **/
@Slf4j
@Service
public class ThingInfoService {

    @Autowired(required = false)
    private ThingInfoMapper thingInfoMapper;
    @Autowired(required = false)
    private ThingPicInfoService thingPicInfoService;
    @Autowired(required = false)
    private BlockChainNftService blockChainNftService;
    @Autowired(required = false)
    private UserAuthenticationService userAuthenticationService;
    @Autowired(required = false)
    private ThingSellInfoMapper thingSellInfoMapper;
    @Autowired(required = false)
    private BidInfoService bidInfoService;
    @Autowired(required = false)
    private BidInfoMapper bidInfoMapper;
    @Autowired(required = false)
    private UserCollectMapper userCollectMapper;
    @Autowired(required = false)
    private UserFansMapper userFansMapper;
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;
    @Autowired(required = false)
    private PushInfoMapper pushInfoMapper;
    @Autowired(required = false)
    private ContractInfoMapper contractInfoMapper;
    @Autowired(required = false)
    private UserCompanyMapper userCompanyMapper;
    @Autowired(required = false)
    private ThingDealInfoMapper thingDealInfoMapper;
    @Autowired(required = false)
    private UserFansService userFansService;

    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<ThingInfoDetail> selectPage(ThingInfoQueryParam query) {
        query.setIsDel(false);
        query.setUploadStatus(true);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectList(query));
        List<ThingInfoDetail> dtoList = ThingInfoAssembler.INSTANCE.getThingInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 华夏在售列表
     *
     */
//     public List<ThingSelectListResult> getHuaxiaList(){
//         ThingInfoQueryParam param = new ThingInfoQueryParam();
//
//     }

    /**
     * APP首页查询-上链工具
     *
     * @param query
     * @return com.armsmart.common.api.CommonPage<com.armsmart.jupiter.bs.api.dto.response.ThingSelectListResult>
     */
    public CommonPage<ThingShortInfo> selectUploadTools(ThingInfoQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        query.setIsOfficial(true);
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectList(query));
        List<ThingShortInfo> thingSelectListResult = ThingInfoAssembler.INSTANCE.getThingShortInfoList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, thingSelectListResult);
    }

    /**
     * APP首页查询-上链工具详情
     *
     * @param thingId
     * @param currentUserId
     * @return com.armsmart.common.api.CommonResult<com.armsmart.jupiter.bs.api.dto.response.ThingNftInfo>
     */
    public CommonResult<ThingNftInfo> selectUploadTool(Long thingId, Integer currentUserId) {
        ThingInfo thingInfo = thingInfoMapper.selectById(thingId);
        if (null == thingInfo) {
            return CommonResult.failed("物品不存在");
        }
        SellerInfoDetail sellerInfo = getSellerInfo(thingInfo.getUserId(), currentUserId);
        ThingNftInfo thingNftInfo = ThingInfoAssembler.INSTANCE.getThingNftInfo(thingInfo);
        thingNftInfo.setSellerInfo(sellerInfo);
        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
        if (null != thingSellInfo) {
            thingNftInfo.setThingSellId(thingSellInfo.getId());
        }
        return CommonResult.success(thingNftInfo);
    }

    /**
     * @param id
     * @param isPutOn
     * @return com.armsmart.common.api.CommonResult
     */
    public CommonResult setPutOn(Long id, Boolean isPutOn) {
        ThingInfo thingInfo = thingInfoMapper.selectById(id);
        if (null == thingInfo) {
            return CommonResult.failed("物品不存在");
        }
        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(id);
        if (null == thingSellInfo) {
            thingSellInfo = new ThingSellInfo();
            thingSellInfo.setPutOn(isPutOn);
            thingSellInfo.setIsDel(false);
            thingSellInfo.setCreateTime(System.currentTimeMillis());
            thingSellInfo.setSellType(SellType.BUYOUT.getCode());
            thingSellInfo.setThingId(id);
            thingSellInfo.setThingPrice(thingInfo.getCurrentPrice());
            thingSellInfo.setSellerInfo(thingInfo.getThingDesc());
            thingSellInfoMapper.insert(thingSellInfo);
        } else {
            ThingSellInfo updateEntity = new ThingSellInfo();
            updateEntity.setId(thingSellInfo.getId());
            updateEntity.setPutOn(isPutOn);
            updateEntity.setUpdateTime(System.currentTimeMillis());
            thingSellInfoMapper.updateSelective(updateEntity);
        }
        ThingInfo updateEntity = new ThingInfo();
        updateEntity.setId(id);
        updateEntity.setCurrentState(isPutOn ? ThingState.ON_SALE.getCode() : ThingState.PUT_OFF.getCode());
        updateEntity.setUpdateTime(System.currentTimeMillis());
        return CommonResult.success(thingInfoMapper.updateSelective(updateEntity));
    }


    /**
     * APP首页列表
     *
     * @param query
     * @return
     */
    public CommonPage<ThingSelectListResult> selectList(ThingInfoQueryParam query) {
        List<ThingSelectListResult> tsList = new ArrayList<>();
        //在卖物品信息
        List<ThingInfo> thingInfoDetails = thingInfoMapper.selectList(query);
        if (thingInfoDetails == null) {
            PageInfo<ThingSelectListResult> pageInfo = new PageInfo<>();
            List<ThingSelectListResult> dtoList = pageInfo.getList();
            return CommonPage.restPage(pageInfo, dtoList);
        }

        thingInfoDetails.forEach(thingInfo -> {
            Long thingId = thingInfo.getId();
            ThingSelectListResult thingSelectListResult = new ThingSelectListResult();
            UserInfo userInfo = userInfoMapper.selectById(thingInfo.getUserId());
            if (userInfo == null || thingInfo == null) {
                return;
            }
            thingSelectListResult.setArtName(thingInfo.getArtName());
            thingSelectListResult.setCurrentPrice(thingInfo.getCurrentPrice());
            thingSelectListResult.setPics(thingInfo.getPics());
            thingSelectListResult.setThingId(thingId);
            thingSelectListResult.setUserId(thingInfo.getUserId());
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
            if (thingSellInfo != null) {
                if (thingSellInfo.getPutOn()) {
                    thingSelectListResult.setSellType(thingSellInfo.getSellType());
                    if (thingSellInfo.getSellType() == 2) {
                        if (thingSellInfo.getAuctionStartTime() > System.currentTimeMillis()) {
                            thingSelectListResult.setAuctionState(0);//拍卖前
                            thingSelectListResult.setAuctionTime(thingSellInfo.getAuctionStartTime());
                        }
                        if (thingSellInfo.getAuctionEndTime() >= System.currentTimeMillis()
                                && thingSellInfo.getAuctionStartTime() < System.currentTimeMillis()) {
                            thingSelectListResult.setAuctionState(1);//拍卖中
                            thingSelectListResult.setAuctionTime(thingSellInfo.getAuctionEndTime());
                        }
                    }
                    tsList.add(thingSelectListResult);
                }
            }
        });
        log.info("列表信息：" + tsList.toString());
        PageInfo<ThingSelectListResult> pageInfo = pageInfoResult(query.getPageSize(), query.getPageNum(), tsList);
        List<ThingSelectListResult> dtoList = listToPage(tsList, query.getPageNum(), query.getPageSize());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    public static <T> PageInfo<T> pageInfoResult(int pageSize, int pagetNum, List<T> dtoList) {
        PageInfo<T> result = new PageInfo<>();
        double r = Math.ceil((double) dtoList.size() / (double) pageSize);
        int ii = (int) r;
        result.setPages(ii);
        result.setPageSize(pageSize);
        result.setPageNum(pagetNum);
        result.setTotal(dtoList.size());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(ThingNfcAddParam addParam) {
        ThingInfo entity = ThingInfoAssembler.INSTANCE.getThingInfo(addParam);
        UserInfoQueryParam userInfoQueryParam = new UserInfoQueryParam();
        userInfoQueryParam.setUserType(AppUserType.OFFICIAL.getCode());
        List<UserInfo> userInfos = userInfoMapper.selectList(userInfoQueryParam);
        if (!CollectionUtils.isEmpty(userInfos)) {
            entity.setUserId(userInfos.get(0).getId());
            entity.setUploaderId(entity.getUserId());
        }
        thingInfoMapper.insert(entity);
        addParam.getPics().forEach(item -> {
            item.setThingId(entity.getId());
            thingPicInfoService.insert(item);
        });
        return CommonResult.success(entity.getId());
    }

    /**
     * 插入数据
     *
     * @param thingInfoAddParam
     * @return com.armsmart.jupiter.bs.api.entity.ThingInfo
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public DeferredResult<CommonResult> insert(ThingInfoAddParam thingInfoAddParam) {
        ThingInfo entity = ThingInfoAssembler.INSTANCE.getThingInfo(thingInfoAddParam);
        ThingInputInfoAddParam artInputInfoAddParam = new ThingInputInfoAddParam();
        artInputInfoAddParam.setContractAddr(thingInfoAddParam.getContractAddr());
        artInputInfoAddParam.setArtName(thingInfoAddParam.getArtName());
        artInputInfoAddParam.setArtYear(thingInfoAddParam.getArtYear());
        artInputInfoAddParam.setThingType(thingInfoAddParam.getThingType());
        artInputInfoAddParam.setArtSize(thingInfoAddParam.getThingSize());
        artInputInfoAddParam.setArtWeight(thingInfoAddParam.getThingWeight());
        artInputInfoAddParam.setArtCondition(thingInfoAddParam.getThingCondition()); //资质
        artInputInfoAddParam.setThingElement(thingInfoAddParam.getThingElement());
        artInputInfoAddParam.setArtDesc(thingInfoAddParam.getThingDesc());
        artInputInfoAddParam.setMd5(thingInfoAddParam.getMd5());
        artInputInfoAddParam.setAuthor(thingInfoAddParam.getAuthor());
        UserAuthentication userAuthentication = userAuthenticationService.selectByUserId(thingInfoAddParam.getUserId().toString());
        if (userAuthentication.equals(null)) {
            DeferredResult<CommonResult> entiy = new DeferredResult<>();
            entiy.setErrorResult(ART_TITLE_NOT_EXIST_FILE);
            return entiy;
        }
        artInputInfoAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        artInputInfoAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
        artInputInfoAddParam.setRandNum(thingInfoAddParam.getRandNum());
        artInputInfoAddParam.setMessageIn(thingInfoAddParam.getMessageIn());
        artInputInfoAddParam.setWebStart(thingInfoAddParam.getWebStart());
        artInputInfoAddParam.setArtSign(thingInfoAddParam.getArtSign());
        artInputInfoAddParam.setArtistSign(thingInfoAddParam.getArtistSign());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.thingLoad(artInputInfoAddParam);
        entity.setCurrentState(99);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(thingInfoAddParam.getContractAddr());
                thingInfo.setUserId(thingInfoAddParam.getUserId());
                thingInfo.setUploaderId(thingInfoAddParam.getUserId());
                thingInfo.setThingCondition(thingInfoAddParam.getThingCondition());
                thingInfo.setThingElement(thingInfoAddParam.getThingElement());
                thingInfo.setThingSize(thingInfoAddParam.getThingSize());
                thingInfo.setThingType(thingInfoAddParam.getThingType());
                thingInfo.setThingWeight(thingInfoAddParam.getThingWeight());
                thingInfo.setUpdateTime(System.currentTimeMillis());
                thingInfoMapper.updateSelective(thingInfo);
                log.info("返回的物品ip信息是：{}", result.getData().toString());
                thingInfoAddParam.getPics().forEach(item -> {
                    item.setThingId(Long.valueOf(result.getData().toString()));
                    thingPicInfoService.insert(item);
                });
            }
        });
        return deferredResult;
    }

    /**
     * 修改数据
     *
     * @param thingInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ThingInfoUpdateParam thingInfoUpdateParam) {
        ThingInfo entity = ThingInfoAssembler.INSTANCE.getThingInfo(thingInfoUpdateParam);
        thingInfoMapper.updateSelective(entity);
        return CommonResult.success();
    }

    /**
     * 修改数据
     *
     * @param thingInfoUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult update(ThingNfcUpdateParam thingInfoUpdateParam) {
        ThingInfo entity = ThingInfoAssembler.INSTANCE.getThingInfo(thingInfoUpdateParam);
        thingInfoMapper.updateSelective(entity);
        thingPicInfoService.deletePhysical(entity.getId());
        thingInfoUpdateParam.getPics().forEach(item -> {
            item.setThingId(entity.getId());
            thingPicInfoService.insert(item);
        });
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
        ThingInfo entity = new ThingInfo();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        thingInfoMapper.updateSelective(entity);
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

    private SellerInfoDetail getSellerInfo(Integer userId, Integer currentUserId) {
        UserInfo userInfo = userInfoMapper.selectByIdIncludeDel(userId);
        if (null != userInfo) {
            SellerInfoDetail sellerInfoDetail = new SellerInfoDetail();
            sellerInfoDetail.setId(userInfo.getId());
            sellerInfoDetail.setNickname(userInfo.getNickname());
            sellerInfoDetail.setAvatar(userInfo.getAvatar());
            sellerInfoDetail.setAuthDesc(userInfo.getAuthDesc());
            if (null == currentUserId) {
                sellerInfoDetail.setFocusState(-1);
            } else if (Objects.equals(currentUserId, userInfo.getId())) {
                sellerInfoDetail.setFocusState(0);
            } else {
                UserFans userFans = userFansService.getUserFans(currentUserId, userId);
                if (null != userFans) {
                    sellerInfoDetail.setFocusState(userFans.getFocusState());
                } else {
                    sellerInfoDetail.setFocusState(-1);
                }
            }
            sellerInfoDetail.setPutOnCount(thingSellInfoMapper.putOnCount(userInfo.getId().longValue()));
            return sellerInfoDetail;
        }
        return null;
    }

    /**
     * 获取详情
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail
     * @date 2020/01/01
     */
    public CommonResult<ThingIdInfoDetail> selectById(Long id, Integer currentUserId) {
        ThingIdInfoDetail thingInfoBidDetail = new ThingIdInfoDetail();
        thingInfoBidDetail.setBidInfoList(new ArrayList<>());
        ThingInfo thingInfo = thingInfoMapper.selectById(id);
        Integer marginPayState = 0;
        thingInfoBidDetail.setMarginPayState(marginPayState);
        if (thingInfo == null) {
            return CommonResult.success(thingInfoBidDetail);
        }
        thingInfoBidDetail.setThingInfo(thingInfo);
        SellerInfoDetail sellerInfoDetail = getSellerInfo(thingInfo.getUserId(), currentUserId);
        if (null != sellerInfoDetail) {

            thingInfoBidDetail.setSellerInfo(sellerInfoDetail);
        }
        if (null == currentUserId) {
            thingInfoBidDetail.setCollectState(-1);
        } else {
            if (Objects.equals(currentUserId, thingInfo.getUserId())) {
                thingInfoBidDetail.setCollectState(0);
            } else {
                UserCollectAddParam userCollectAddParam = new UserCollectAddParam();
                userCollectAddParam.setUserId(currentUserId.longValue());
                userCollectAddParam.setThingId(id);
                UserCollect userCollect = userCollectMapper.selectInfo(userCollectAddParam);
                if (null == userCollect) {
                    thingInfoBidDetail.setCollectState(-1);
                } else {
                    thingInfoBidDetail.setCollectState(1);
                }
            }
        }

        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingInfo.getId());
        if (thingSellInfo == null) {
            thingInfoBidDetail.setThingInfo(thingInfo);
            return CommonResult.success(thingInfoBidDetail);
        }
        if (currentUserId == null) {
            marginPayState = 0;
        } else {
            OrderInfo orderInfo = orderInfoMapper.selectByBuyer(thingSellInfo.getId(), currentUserId.toString());
            if (orderInfo != null && orderInfo.getOrderStatus() == 8) {
                marginPayState = 1;
            } else {
                marginPayState = 0;
            }
        }
        thingInfoBidDetail.setMarginPayState(marginPayState);
        thingInfoBidDetail.setThingSellInfo(thingSellInfo);
        if (thingSellInfo.getSellType().equals(2)) {
            log.info("拍卖结束时间：" + thingSellInfo.getAuctionEndTime() + ";系统当前时间 ：" + System.currentTimeMillis());
            if (System.currentTimeMillis() < thingSellInfo.getAuctionStartTime()) {
                Long i = new Long((long) 0);
                thingInfoBidDetail.setBidLastTime(i);
            } else if (System.currentTimeMillis() > thingSellInfo.getAuctionEndTime()) {
                Long i = new Long((long) -1);
                thingInfoBidDetail.setBidLastTime(i);
            } else {
                Long i = thingSellInfo.getAuctionEndTime() - System.currentTimeMillis();
                thingInfoBidDetail.setBidLastTime(i);
            }
//            BidInfo bidInfo = bidInfoMapper.selectMaxBid(thingSellInfo.getId());
            List<BidInfo> bidInfoList = bidInfoMapper.selectTopBid(thingSellInfo.getId());
            if (bidInfoList == null) {
                return CommonResult.success(thingInfoBidDetail);
            }
            thingInfoBidDetail.setBidInfoList(bidInfoList);
        }

        return CommonResult.success(thingInfoBidDetail);
    }


    /**
     * 根据合约地址查询
     *
     * @param contractAddr
     * @return ArtInfo
     */
    public CommonResult<NfcReadDetail> selectByContractAddr(String contractAddr) {
        NfcReadDetail nfcReadDetail = new NfcReadDetail();
        Object ii = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(ii)) {
            return CommonResult.success(nfcReadDetail);
        } else {
            AppUserDetails userDetails = (AppUserDetails) ii;
            //添加读取记录
            ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(contractAddr);
            if (thingInfo == null) {
                return CommonResult.success(nfcReadDetail);
            }
            if (userDetails.getUserInfo().getId().equals(thingInfo.getUserId())) {
                nfcReadDetail.setIsSelf(1);
            } else {
                nfcReadDetail.setIsSelf(0);
            }
            ThingInfoDetail thingInfoDetail = ThingInfoAssembler.INSTANCE.getThingInfoDetail(thingInfo);
            nfcReadDetail.setArtName(thingInfoDetail.getArtName());
            nfcReadDetail.setArtYear(thingInfoDetail.getArtYear());
            nfcReadDetail.setAuthor(thingInfoDetail.getAuthor());
            nfcReadDetail.setContractAddr(contractAddr);
            nfcReadDetail.setCreateTime(thingInfoDetail.getCreateTime());
            nfcReadDetail.setCurrentPrice(thingInfoDetail.getCurrentPrice());
            nfcReadDetail.setCurrentState(thingInfoDetail.getCurrentState());
            nfcReadDetail.setId(thingInfoDetail.getId());
            nfcReadDetail.setIsDel(thingInfoDetail.getIsDel());
            nfcReadDetail.setPics(thingInfoDetail.getPics());
            nfcReadDetail.setThingCondition(thingInfoDetail.getThingCondition());
            nfcReadDetail.setThingDesc(thingInfoDetail.getThingDesc());
            nfcReadDetail.setThingElement(thingInfoDetail.getThingElement());
            nfcReadDetail.setThingSize(thingInfoDetail.getThingSize());
            nfcReadDetail.setThingType(thingInfoDetail.getThingType());
            nfcReadDetail.setThingWeight(thingInfoDetail.getThingWeight());
            nfcReadDetail.setUpdateTime(thingInfoDetail.getUpdateTime());
            nfcReadDetail.setUploadStatus(thingInfoDetail.getUploadStatus());
            nfcReadDetail.setUserId(thingInfoDetail.getUserId());
            return CommonResult.success(nfcReadDetail);
        }

    }


    /**
     * 根据用户id查询上传成功物品列表
     *
     * @param query
     * @return ThingInfoDetail
     */
    public CommonPage<ThingInfoDetail> selectListByUserId(UserIdQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectListByUserId(query.getUserId()));
        List<ThingInfoDetail> dtoList = ThingInfoAssembler.INSTANCE.getThingInfoDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 根据用户id查询本人所有上链物品信息
     */

    public CommonPage<ThingInfoListDetail> selectListByOther(UserIdQueryParam query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<ThingInfoListDetail> pageInfo = new PageInfo<>(thingInfoMapper.selectListByOther(query.getUserId()));
        List<ThingInfoListDetail> dtoList = pageInfo.getList();
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 赠送好友
     */
    public DeferredResult<CommonResult> thingGiveAway(ModifyOwnerAddInfoParam param) {
        Object ii = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserDetails userDetails = (AppUserDetails) ii;
        ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(param.getContractAddr());
        if (thingInfo == null || thingInfo.getCurrentState() == 1 || thingInfo.getCurrentState().equals(2)) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(ADDR_NOT_EXIST_OR_SELL_FAILE));
            return deferredResult;
        }
        ThingDealInfo thingDealInfo = thingDealInfoMapper.getInfoByThingId(thingInfo.getId());
        if (thingDealInfo != null) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(THING_HAVE_GIVE));
            return deferredResult;
        }
        UserAuthentication userAuthenti = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
        if (userAuthenti == null || !userAuthenti.getIsCert() || !userAuthenti.getIsBind()) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(USER_HAVE_NO_CERT));
            return deferredResult;
        }
        ModifyOwnerAddParam modifyOwnerAddParam = new ModifyOwnerAddParam();
        modifyOwnerAddParam.setArtistSign(param.getArtistSign());
        modifyOwnerAddParam.setArtSign(param.getArtSign());
        modifyOwnerAddParam.setAuthTokenId(param.getContractAddr());
        modifyOwnerAddParam.setMessageIn(param.getMessageIn());
        modifyOwnerAddParam.setWebStart(param.getMessageIn());
        modifyOwnerAddParam.setModifyType(param.getModifyType());
        modifyOwnerAddParam.setTermInfo(param.getTermInfo());
        String nextDealAddr = contractInfoMapper.nextDealAddr();
        modifyOwnerAddParam.setNextDealTokenId(nextDealAddr);
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
        UserInfo userInfo = userInfoMapper.selectById(param.getUserId());
        modifyOwnerAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
        modifyOwnerAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.modifyOwner(modifyOwnerAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                contractInfoMapper.updateAddr(nextDealAddr);
                thingInfo.setCurrentState(3);
                thingInfo.setUpdateTime(System.currentTimeMillis());
                thingInfoMapper.updateSelective(thingInfo);
                ThingDealInfo thingDealInfoAdd = new ThingDealInfo();
                thingDealInfoAdd.setBuyUserId(param.getUserId());
                thingDealInfoAdd.setContractAddr(param.getContractAddr());
                thingDealInfoAdd.setDealContractAddr(nextDealAddr);
                thingDealInfoAdd.setIsDel(false);
                thingDealInfoAdd.setIsDone(false);
                thingDealInfoAdd.setGiveUserId(userDetails.getUserInfo().getId().longValue());
                thingDealInfoAdd.setType(1);
                thingDealInfoAdd.setCreateTime(System.currentTimeMillis());
                thingDealInfoAdd.setThingId(thingInfo.getId());
                thingDealInfoMapper.insert(thingDealInfoAdd);
                log.info("变更所有者成功");
            } else {
                log.warn("变更所有者失败");
            }
        });
        return deferredResult;

    }

    /**
     * 赠送签收
     */
    public DeferredResult<CommonResult> getGiveAway(ModifyOwnerAddInfoParam param) {
        ModifyOwnerAddParam modifyOwnerAddParam = new ModifyOwnerAddParam();
        ThingDealInfo thingDealInfo = thingDealInfoMapper.getDealInfo(param.getUserId());
        if (thingDealInfo == null) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(THING_GIVE_NO_EXIST));
            return deferredResult;
        }
        if (!thingDealInfo.getContractAddr().equals(param.getContractAddr())) {
            DeferredResult<CommonResult> deferredResult = new DeferredResult<>();
            deferredResult.setResult(CommonResult.failed(THING_GIVE_NO_MATCH));
            return deferredResult;
        }
        modifyOwnerAddParam.setArtistSign(param.getArtistSign());
        modifyOwnerAddParam.setArtSign(param.getArtSign());
        modifyOwnerAddParam.setAuthTokenId(param.getContractAddr());
        modifyOwnerAddParam.setMessageIn(param.getMessageIn());
        modifyOwnerAddParam.setWebStart(param.getMessageIn());
        modifyOwnerAddParam.setModifyType(param.getModifyType());
        modifyOwnerAddParam.setTermInfo(param.getTermInfo());
        String nextDealAddr = thingDealInfo.getDealContractAddr();
        modifyOwnerAddParam.setNextDealTokenId(nextDealAddr);
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
        modifyOwnerAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
        modifyOwnerAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.modifyOwner(modifyOwnerAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(param.getContractAddr());
                thingInfo.setCurrentState(99);
                thingInfo.setUpdateTime(System.currentTimeMillis());
                thingInfo.setUserId(param.getUserId().intValue());
                thingInfoMapper.updateSelective(thingInfo);
//                更新推送信息
                thingDealInfo.setDoneTime(System.currentTimeMillis());
                thingDealInfo.setIsDone(true);
                thingDealInfo.setIsDel(true);
                thingDealInfoMapper.updateSelective(thingDealInfo);
                log.info("变更所有者成功");
            } else {
                log.warn("变更所有者失败");
            }
        });
        return deferredResult;
    }


    /***
     * 查询物品在售状态
     */
    private int getThingType(Long thingId) {
        ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(thingId);
        if (thingSellInfo == null) {
            return -1;
        }
        return thingSellInfo.getSellType();
    }

    public CommonResult shareByThing(Long id, Integer ii) {
        ThingInfo thingInfo = thingInfoMapper.selectById(id);
        if (null == thingInfo) {
            return CommonResult.failed(NEError.THING_NOT_EXIST);
        }
        if (thingInfo.getCurrentState() == 1 || thingInfo.getCurrentState() == 2) {
            return CommonResult.failed(NEError.THING_TYPE_CANNOT_CHANGE);
        }

        thingInfo.setCurrentState(ii); //-1 非卖展示，99 私密隐藏
        thingInfoMapper.updateSelective(thingInfo);

        return CommonResult.success();
    }


    public CommonPage<ThingInfoDetail> selectUploadList(UserIdQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectUploadList(param.getUserId()));
        List<ThingInfoDetail> dtoList = ThingInfoAssembler.INSTANCE.getThingInfoDetailList(pageInfo.getList());
//        Collections.reverse(dtoList);
        return CommonPage.restPage(pageInfo, dtoList);
    }


    /**
     * 参拍list
     */
    public CommonPage<ThingInfoBidDetail> selectBidList(UserIdQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Long> sellList = bidInfoMapper.selectSellList(param.getUserId());
        Collections.reverse(sellList);
        log.info("参拍list" + sellList.toString());
        List<ThingInfoBidDetail> thingInfoBidList = new ArrayList<>();
        sellList.forEach(sellId -> {
            ThingInfoBidDetail thingInfoBidDetail = new ThingInfoBidDetail();
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectById(sellId);
            if (thingSellInfo == null) {
                return;
            }
            thingInfoBidDetail.setThingSellInfo(thingSellInfo);
            ThingInfo thingInfo = thingInfoMapper.selectById(thingSellInfo.getThingId());
            if (thingInfo == null) {
                return;
            }
            thingInfoBidDetail.setThingInfo(thingInfo);
            SellerInfoDetail sellerInfoDetail = getSellerInfo(thingInfo.getUserId(), param.getUserId().intValue());
            thingInfoBidDetail.setSellerInfo(sellerInfoDetail);
            BidInfo bidInfo = bidInfoMapper.selectMaxBid(thingSellInfo.getId());
            thingInfoBidDetail.setBidInfo(bidInfo);
            if (thingSellInfo.getAuctionEndTime() > System.currentTimeMillis()) {
                thingInfoBidDetail.setBidLastTime(thingSellInfo.getAuctionEndTime() - System.currentTimeMillis());
                if (bidInfo.getUserId().longValue() == param.getUserId()) {
                    thingInfoBidDetail.setBidStates(BidStatesType.LEAD.getCode());
                } else {
                    thingInfoBidDetail.setBidStates(BidStatesType.OUT_CONTINUE.getCode());
                }
            } else {
                thingInfoBidDetail.setBidLastTime(Long.valueOf(-1));
                if (bidInfo.getUserId().longValue() == param.getUserId()) {
                    thingInfoBidDetail.setBidStates(BidStatesType.DONE_TO_PAY.getCode());
                } else {
                    thingInfoBidDetail.setBidStates(BidStatesType.OUT.getCode());
                }
            }
            thingInfoBidList.add(thingInfoBidDetail);
        });
        PageInfo<ThingInfoBidDetail> pageInfo = pageInfoResult(param.getPageSize(), param.getPageNum(), thingInfoBidList);
        List<ThingInfoBidDetail> dtoList = listToPage(thingInfoBidList, param.getPageNum(), param.getPageSize());
        return CommonPage.restPage(pageInfo, thingInfoBidList);
    }

    /**
     * 用户数据
     */
    public CommonResult<UserCountInfoDetail> selectCountInfo(Long userId) {
        List<Long> sellList = bidInfoService.selectSellList(userId);
        UserCountInfoDetail userCountInfoDetail = new UserCountInfoDetail();
        if (sellList == null) {
            userCountInfoDetail.setBidCount(0);
        } else {
            userCountInfoDetail.setBidCount(sellList.size());
        }
        if (userCollectMapper.countByUserId(userId) == null) {
            userCountInfoDetail.setCollectCount(0);
        } else {
            userCountInfoDetail.setCollectCount(userCollectMapper.countByUserId(userId));
        }
        UserFansCountDetail userFansCount = userFansMapper.selectCount(userId);
        if (userFansCount == null) {
            userCountInfoDetail.setFocusCount(0);
            userCountInfoDetail.setFansCount(0);
        } else {
            userCountInfoDetail.setFocusCount(userFansMapper.selectCount(userId).getFocusCount());
            userCountInfoDetail.setFansCount(userFansMapper.selectCount(userId).getFansCount());
        }
        if (userCountInfoDetail.getFansCount() == null) {
            userCountInfoDetail.setFansCount(0);
        }
        if (userCountInfoDetail.getFocusCount() == null) {
            userCountInfoDetail.setFocusCount(0);
        }
        userCountInfoDetail.setUserId(userId.intValue());
        return CommonResult.success(userCountInfoDetail);
    }

    /**
     * 为您推荐
     */
    public CommonResult<ThingSelectListResult> recommend(Long id) {

        ThingInfoQueryParam query = new ThingInfoQueryParam();
        query.setCurrentState(1); //在售
        query.setIsDel(false);
        query.setIsOfficial(false);
        query.setPageNum(1);
        query.setPageSize(20);
        List<ThingSelectListResult> list = selectList(query).getList();
        if (CollectionUtils.isEmpty(list)) {
            return CommonResult.success();
        }
        if (!id.equals(list.get(0).getThingId())) {
            return CommonResult.success(list.get(0));
        } else {
            if (list.size() > 1) {
                return CommonResult.success(list.get(1));
            }
        }
        return CommonResult.success();
    }

    /**
     * 已售物品list
     * 由于物品已售，ownerId变更，因此单独接口从订单中查询
     *
     * @param param
     * @return
     */
    public CommonPage<ThingShortInfo> soldList(UserIdQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        PageInfo<ThingShortInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectSoldList(param.getUserId()));
        return CommonPage.restPage(pageInfo);
    }

    /**
     * 我的权证
     *
     * @param param
     * @return com.armsmart.common.api.CommonPage<com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo>
     */
    public CommonPage<ThingShortInfo> myWarrant(UserIdQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectListByStates(param.getUserId().intValue(), Arrays.asList(ThingState.PRIVATE.getCode(), ThingState.PUT_OFF.getCode())));
        List<ThingShortInfo> dtoList = ThingInfoAssembler.INSTANCE.getThingShortInfoList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 物品list
     *
     * @param param
     * @return
     */
    public CommonPage<ThingShortInfo> thingList(UserIdQueryParam param, Integer i) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        ThingInfoQueryParam thingInfoQueryParam = new ThingInfoQueryParam();
        thingInfoQueryParam.setIsDel(false);
        thingInfoQueryParam.setUploadStatus(true);
        thingInfoQueryParam.setUserId(param.getUserId().intValue());
        if (i == ThingState.PRIVATE.getCode()) {
            thingInfoQueryParam.setCurrentStates(Arrays.asList(ThingState.PRIVATE.getCode(), ThingState.PUT_OFF.getCode()));
        } else {
            thingInfoQueryParam.setCurrentState(i);
        }
        PageInfo<ThingInfo> pageInfo = new PageInfo<>(thingInfoMapper.selectList(thingInfoQueryParam));
        List<ThingShortInfo> dtoList = ThingInfoAssembler.INSTANCE.getThingShortInfoList(pageInfo.getList());
        dtoList.forEach(item -> {
            ThingSellInfo thingSellInfo = thingSellInfoMapper.selectByThingId(item.getThingId());
            if (thingSellInfo != null) {
                item.setSellType(thingSellInfo.getSellType());
                if (thingSellInfo.getPutOn()) {
                    if (thingSellInfo.getSellType() == 2) {
                        if (thingSellInfo.getAuctionStartTime() > System.currentTimeMillis()) {
                            item.setAuctionState(0);//拍卖前
                            item.setAuctionTime(thingSellInfo.getAuctionStartTime());
                        }
                        if (thingSellInfo.getAuctionEndTime() >= System.currentTimeMillis()
                                && thingSellInfo.getAuctionStartTime() < System.currentTimeMillis()) {
                            item.setAuctionState(1);//拍卖中
                            item.setAuctionTime(thingSellInfo.getAuctionEndTime());
                        }
                    }
                }
            }
        });
        return CommonPage.restPage(pageInfo, dtoList);
    }

    public CommonResult nfcInfoLoad(NfcInfoLoadParam param) {
        ThingInfo thingInfo = new ThingInfo();
        thingInfo.setCurrentPrice(Long.valueOf(param.getThingPrice()));//(分转元)
        thingInfo.setCurrentState(1);//售卖中
        thingInfo.setArtYear("2021");
        thingInfo.setAuthor("爱较真");
        thingInfo.setThingDesc(param.getThingDesc());
        thingInfo.setIsDel(false);
        thingInfo.setUploadStatus(true);
        thingInfo.setCreateTime(System.currentTimeMillis());
        thingInfo.setUserId(14000049);
        if (param.getThingType().equals(0)) {
            thingInfo.setArtName("孚贴");
            thingInfo.setContractAddr("0x0000000000000000000000000000000000000000");
            thingInfo.setThingType(42);
        } else if (param.getThingType().equals(1)) {
            thingInfo.setArtName("读卡器");
            thingInfo.setContractAddr("0x1111111111111111111111111111111111111111");
            thingInfo.setThingType(43);
        }
        log.info("入参信息是：{}", thingInfo.toString());
        thingInfoMapper.insert(thingInfo);
        param.getPics().forEach(item -> {
            item.setThingId(thingInfo.getId());
            thingPicInfoService.insert(item);
        });
        return CommonResult.success();
    }


    /**
     * 确权接口
     *
     * @param param
     * @return
     */
    public DeferredResult<CommonResult> confirmOwner(ModifyOwnerAddInfoParam param) {
        ModifyOwnerAddParam modifyOwnerAddParam = new ModifyOwnerAddParam();
        modifyOwnerAddParam.setArtistSign(param.getArtistSign());
        modifyOwnerAddParam.setArtSign(param.getArtSign());
        modifyOwnerAddParam.setAuthTokenId(param.getContractAddr());
        modifyOwnerAddParam.setMessageIn(param.getMessageIn());
        modifyOwnerAddParam.setWebStart(param.getMessageIn());
        modifyOwnerAddParam.setModifyType(param.getModifyType());
        modifyOwnerAddParam.setTermInfo(param.getTermInfo());
        String nextDealAddr = contractInfoMapper.nextDealAddr();
        modifyOwnerAddParam.setNextDealTokenId(nextDealAddr);
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(param.getUserId().toString());
        modifyOwnerAddParam.setUserPubkeyE(userAuthentication.getPublicKeyE());
        modifyOwnerAddParam.setUserPubkeyM(userAuthentication.getPublicKeyM());
        DeferredResult<CommonResult> deferredResult = blockChainNftService.modifyOwner(modifyOwnerAddParam);
        deferredResult.onCompletion(() -> {
            CommonResult result = (CommonResult) deferredResult.getResult();
            if (result.getCode() == ResultCode.SUCCESS.getCode()) {
                contractInfoMapper.updateAddr(nextDealAddr);
                ThingInfo thingInfo = thingInfoMapper.selectByContractAddr(param.getContractAddr());
                thingInfo.setUserId(param.getUserId().intValue());
                thingInfo.setCurrentState(99);
                thingInfoMapper.updateSelective(thingInfo);
                log.info("确权成功");
            } else {
                log.warn("确权失败");
            }
        });
        return deferredResult;
    }

    public List listToPage(List uu, int pageNum, int pageSize) {
        List ii = new ArrayList();
        if (uu != null && uu.size() > 0) {
            int cuurIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < uu.size() - cuurIdx; i++) {
                ii.add(uu.get(cuurIdx + i));
            }
        }
        return ii;
    }
}





