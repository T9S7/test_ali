package com.armsmart.jupiter.bs.api.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyResultRequest;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyResultResponse;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyTokenRequest;
import com.aliyuncs.cloudauth.model.v20190307.DescribeVerifyTokenResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.assembler.UserAuthenticationAssembler;
//import com.armsmart.jupiter.bs.api.blockchain.BlockChainService;
import com.armsmart.jupiter.bs.api.dao.UserAuthenticationMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.UserAuthenticationDetail;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import static com.armsmart.jupiter.bs.api.error.NEError.*;

/**
 * UserAuthentication service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class UserAuthenticationService {

    public static final int KEY_LEN = 256;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;

//    @Autowired(required = false)
//    private BlockChainService blockChainService;

    DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAITig6PhDgFJHC", "FXj8bGOXplG1whPUNIwA3HcJETV7Cv");
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return PageInfo
     * @date 2020/01/01
     */
    public CommonPage<UserAuthenticationDetail> selectPage(UserAuthenticationQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<UserAuthentication> pageInfo = new PageInfo<>(userAuthenticationMapper.selectList(query));
        List<UserAuthenticationDetail> dtoList = UserAuthenticationAssembler.INSTANCE.getUserAuthenticationDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 插入数据
     *
     * @param userAuthenticationAddParam
     * @return UserAuthentication
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult insert(UserAuthenticationAddParam userAuthenticationAddParam) {
        //查询之前是否进行过身份认证
        String certificateNo = userAuthenticationAddParam.getCertificateNo();
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByCertNo(certificateNo);
        UserAuthentication userAuthentication1 = userAuthenticationMapper.selectByUserId(userAuthenticationAddParam.getUserId().toString());
        int ii;
        if (userAuthentication == null ) {
            if( userAuthentication1 ==null){
                UserAuthentication entity = UserAuthenticationAssembler.INSTANCE.getUserAuthentication(userAuthenticationAddParam);
                userAuthenticationMapper.insert(entity);
                ii = entity.getId().intValue();
            }else {
                UserAuthentication entity = UserAuthenticationAssembler.INSTANCE.getUserAuthentication(userAuthenticationAddParam);
                entity.setId(userAuthentication1.getId());
                userAuthenticationMapper.update(entity);
                ii = userAuthentication1.getId().intValue();
            }

        } else {
            if(!userAuthentication.getUserId().equals(userAuthenticationAddParam.getUserId())){
                System.out.println("数据库user_id:{}" + userAuthentication.getUserId() + ";入参user_id:" + userAuthenticationAddParam.getUserId());
                return CommonResult.failed(REAL_PERSON_USER_EXIST_FILE);
            }
            UserAuthentication entity = UserAuthenticationAssembler.INSTANCE.getUserAuthentication(userAuthenticationAddParam);
            System.out.println(userAuthentication.getId());
            entity.setId(userAuthentication.getId());
            userAuthenticationMapper.update(entity);
            ii = userAuthentication.getId().intValue();
            System.out.println(ii);
        }
        String bizId = getBizId(userAuthenticationAddParam.getMobile());
        String token = getToken(userAuthenticationAddParam,bizId).getData().toString();
        userAuthenticationMapper.updateBiz(ii,bizId,"10001");
        return  CommonResult.success(token);
    }

    /**
     * 通过身份证查询认证信息是否存在
     */
    public UserAuthenticationDetail selectByCertNo(String certificateNo) {
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByCertNo(certificateNo);
        UserAuthenticationDetail userAuthenticationDetail = UserAuthenticationAssembler.INSTANCE.getUserAuthenticationDetail(userAuthentication);
        return userAuthenticationDetail;
    }

    /**
     * 通过手机号查询认证信息是否存在
     */
    public UserAuthenticationDetail selectByMobile(String mobile) {
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByMobile(mobile);
        UserAuthenticationDetail userAuthenticationDetail = UserAuthenticationAssembler.INSTANCE.getUserAuthenticationDetail(userAuthentication);
        return userAuthenticationDetail;
    }


    /**
     * 认证通过修改已完成认证
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable updateCert(String certificateNo) {

        UserAuthenticationDetail userAuthenticationDetail = selectByCertNo(certificateNo);
        userAuthenticationMapper.updateCert(certificateNo);
        return userAuthenticationDetail.getId();
    }

    /**
     * 修改数据
     *
     * @param userAuthenticationUpdateParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(UserAuthenticationUpdateParam userAuthenticationUpdateParam) {
        UserAuthentication entity = UserAuthenticationAssembler.INSTANCE.getUserAuthentication(userAuthenticationUpdateParam);
        userAuthenticationMapper.update(entity);
    }

    /**
     * 绑定公钥
     *
     * @param userAuthenticationBindParam
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult bind(UserAuthenticationBindParam userAuthenticationBindParam) {
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByCertNo(userAuthenticationBindParam.getCertificateNo());
        if(userAuthentication == null ){
            return CommonResult.failed(REAL_PERSON_CERT_FILE);
        }
        if(userAuthenticationBindParam.getPublicKeyM().length() != 256){
            return CommonResult.failed(REAL_PERSON_PUBLIC_KEY_FILE);
        }
        UserAuthentication entity = UserAuthenticationAssembler.INSTANCE.getUserAuthentication(userAuthenticationBindParam);
        entity.setPublicKeyE(fillIn(entity.getPublicKeyE()));
        userAuthenticationMapper.bind(entity);
        return  CommonResult.success();
    }

    /**
     * 根据主键删除
     *
     * @param id
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        UserAuthentication entity = new UserAuthentication();
        entity.setId(id);
        entity.setIsDel(true);
        entity.setUpdateTime(System.currentTimeMillis());
        userAuthenticationMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2020/01/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Long> ids) {
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
     * @return UserAuthenticationDetail
     * @date 2020/01/01
     */
    public UserAuthenticationDetail selectById(Long id) {
        UserAuthentication userAuthentication = userAuthenticationMapper.selectById(id);
        UserAuthenticationDetail userAuthenticationDetail = UserAuthenticationAssembler.INSTANCE.getUserAuthenticationDetail(userAuthentication);
        return userAuthenticationDetail;
    }

    /**
     * 获取阿里实人认证token
     *
     * @param param 新增信息
     * @param bizId 阿里实人认证编号
     * @return UserAuthenticationDetail
     * @date 2020/01/01
     */
    public CommonResult getToken(UserAuthenticationAddParam param,String bizId){

        IAcsClient client = new DefaultAcsClient(profile);

        DescribeVerifyTokenRequest request = new DescribeVerifyTokenRequest();
        request.setBizType("10001");
        request.setBizId(bizId);
        request.setUserId(param.getUserId().toString());
        request.setName(param.getName());
        request.setIdCardNumber(param.getCertificateNo());

        try {
            DescribeVerifyTokenResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            return CommonResult.success(response.getVerifyToken());
        } catch (ServerException e) {
            e.printStackTrace();
            return CommonResult.failed(REAL_PERSON_TOKEN_ABNORMAL);
        } catch (ClientException e) {
            return CommonResult.failed(REAL_PERSON_TOKEN_ABNORMAL);
        }
    }

    /**
     * 获取阿里实人认证结果
     *
     * @param certNo 身份证号码
     * @return UserAuthenticationDetail
     * @date 2020/01/01
     */
    public CommonResult describeVerify(String certNo){
        UserAuthenticationCheckParam userAuthenticationCheckParam = userAuthenticationMapper.checkByCertNo(certNo);
        IAcsClient client = new DefaultAcsClient(profile);
        DescribeVerifyResultRequest request = new DescribeVerifyResultRequest();
        request.setBizId(userAuthenticationCheckParam.getBizId());
        request.setBizType(userAuthenticationCheckParam.getBizType());

        try {
        DescribeVerifyResultResponse response = client.getAcsResponse(request);
        return  CommonResult.success(response.toString());
        } catch (ServerException e) {
        e.printStackTrace();
            return CommonResult.failed(REAL_PERSON_ABNORMAL);
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return CommonResult.failed(REAL_PERSON_ABNORMAL);
        }
    }

    public UserAuthentication selectByUserId(String userId){
        UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(userId);
        if (userAuthentication == null){
           UserAuthentication ii = new UserAuthentication();
           ii.setIsCert(false);
           ii.setIsBind(false);
           return ii;
        }
        return userAuthentication;
    }
    /**
     * 生成唯一订单号
     * 规则：鉴定编号+"H"+时间hhmmss+"T"+六位随机数
     * history & time
     */
    public synchronized static String getBizId(String mobile) {
        String billCode = "aszz";
        String time = String.valueOf(System.currentTimeMillis());
        String orderCode = "";
        orderCode = billCode  + time + mobile;
        System.out.println(orderCode);
        return orderCode;
    }

    /**
     * 指数补0
     *
     * @param param
     * @return java.lang.String
     */
    public String fillIn(String param) {
        StringBuilder paramSb = new StringBuilder();
        int paramSbLen = param.length();
        if (paramSbLen < KEY_LEN) {
            for (int i = 0; i < KEY_LEN - paramSbLen; i++) {
                paramSb.append("0");
            }
        }
        paramSb.append(param);
        return paramSb.toString();
    }


}
