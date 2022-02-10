package com.armsmart.jupiter.bs.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allinpay.sdk.bean.BizParameter;
import com.allinpay.sdk.bean.OpenResponse;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dao.UserAuthenticationMapper;
import com.armsmart.jupiter.bs.api.dao.UserBankInfoMapper;
import com.armsmart.jupiter.bs.api.dao.UserInfoMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.armsmart.jupiter.bs.api.error.NEError.ART_TITLE_NOT_EXIST_FILE;

@Slf4j
@Service
public class MemberService extends CertBeforeService {

    @Autowired(required = false)
    private UserBankInfoMapper userBankInfoMapper;

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;


    /**
     *创建会员
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult add(String userId) throws Exception{
        /**
         * 没有去创建
         */
        CommonResult commonResult = null;
        CommonResult memberInfo = getMemberInfo(userId);
        if(memberInfo.getCode() ==500 && memberInfo.getMsg().equals("用户不存在。") ){
                final BizParameter param = new BizParameter();
                param.put("bizUserId", userId);
                param.put("memberType", 3L); //个人
                param.put("source", 1L);  //终端手机
                try {
                    final OpenResponse response = client.execute("allinpay.yunst.memberService.createMember", param);
                    System.out.println(response.getTraceId());
                    if ("OK".equals(response.getSubCode())) {
                        commonResult = CommonResult.success();
                        UserInfo userInfo = userInfoMapper.selectById(userId);
                        if(userInfo != null){
                            userInfo.setTlUserId(JSON.parseObject(response.getData()).getString("userId"));
                            userInfoMapper.updateSelective(userInfo);
                        }else {
                            commonResult = CommonResult.success("userId 不存在，测试");
                        }

                    }
                    else {
                        commonResult = CommonResult.failed(response.getSubMsg());
                    }
                } catch (final Exception e) {
                    commonResult = CommonResult.failed(e.getMessage());
                    e.printStackTrace();
                }
            }else{
                commonResult = CommonResult.success();
            }
        return commonResult;
    }

    /**
     * 发送验证码
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult sendVerificationCode(SendVerificationCodeParam sendVerificationCodeParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", sendVerificationCodeParam.getUserId());
        param.put("phone", sendVerificationCodeParam.getPhone());
        param.put("verificationCodeType", sendVerificationCodeParam.getVerificationCodeType());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.sendVerificationCode", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                commonResult = CommonResult.success();
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 实名认证并绑定手机号
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult setRealName(SetRealNameParam setRealNameParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", setRealNameParam.getUserId());
        param.put("phone", setRealNameParam.getPhone());
        param.put("verificationCode", setRealNameParam.getVerificationCode());
        final BizParameter realParam = new BizParameter();
        realParam.put("bizUserId", setRealNameParam.getUserId());
        realParam.put("isAuth", "true");
        realParam.put("name", setRealNameParam.getName());
        realParam.put("identityType", "1");
        realParam.put("identityNo", client.encrypt(setRealNameParam.getIdentityNo()));
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.bindPhone", param);
            if ("OK".equals(response.getSubCode()) || "手机已绑定".equals(response.getSubMsg()) ) {
                final OpenResponse realResponse = client.execute("allinpay.yunst.memberService.setRealName", realParam);
                if(realResponse.getSubCode().equals("OK")){
                    UserAuthentication userAuthentication = userAuthenticationMapper.selectByUserId(setRealNameParam.getUserId());
                    log.info("还没问题");
                    if(userAuthentication == null){
                        log.info("123");
                        UserAuthentication userAuthenticat = new UserAuthentication();
                        userAuthenticat.setUserId(Integer.valueOf(setRealNameParam.getUserId()));
                        userAuthenticat.setCertificateNo(setRealNameParam.getIdentityNo());
                        userAuthenticat.setName(setRealNameParam.getName());
                        userAuthenticat.setMobile(setRealNameParam.getPhone());
                        userAuthenticat.setIsCert(true);
                        userAuthenticat.setIsBind(false);
                        userAuthenticat.setIsDel(false);
                        userAuthenticat.setCreateTime(System.currentTimeMillis());
                        userAuthenticationMapper.insert(userAuthenticat);
                    }else {
                        userAuthentication.setIsCert(true);
                        userAuthenticationMapper.updateSelective(userAuthentication);
                    }
                    commonResult = CommonResult.success();
                }else{
                    commonResult = CommonResult.failed("认证失败" + realResponse.getSubMsg());
                }
            }else{
                commonResult = CommonResult.failed("绑定失败" + response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed("异常" + e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 仅实名认证
     * @param onlySetRealNameParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult onlySetRealName(OnlySetRealNameParam onlySetRealNameParam){
        final BizParameter realParam = new BizParameter();
        realParam.put("bizUserId", onlySetRealNameParam.getUserId());
        realParam.put("isAuth", "true");
        realParam.put("name", onlySetRealNameParam.getName());
        realParam.put("identityType", "1");
        realParam.put("identityNo", client.encrypt(onlySetRealNameParam.getIdentityNo()));
        CommonResult commonResult = null;
        try {
            final OpenResponse realResponse = client.execute("allinpay.yunst.memberService.setRealName", realParam);
            if("OK".equals(realResponse.getSubCode())){
                UserAuthentication userAuthenticat = userAuthenticationMapper.selectByUserId(onlySetRealNameParam.getUserId());
                if(userAuthenticat == null){
                    UserAuthentication userAuth = new UserAuthentication();
                    userAuth.setUserId(Integer.valueOf(onlySetRealNameParam.getUserId()));
                    userAuth.setCertificateNo(onlySetRealNameParam.getIdentityNo());
                    userAuth.setName(onlySetRealNameParam.getName());
                    userAuth.setMobile("");
                    userAuth.setIsCert(true);
                    userAuth.setIsBind(false);
                    userAuth.setIsDel(false);
                    userAuth.setCreateTime(System.currentTimeMillis());
                    userAuthenticationMapper.insert(userAuth);
            }
                commonResult = CommonResult.success();
            }else {
                commonResult = CommonResult.failed(realResponse.getSubMsg());
            }
        }catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 绑定手机号
     * @param bindPhoneParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult bindPhone(UnbindPhoneParam bindPhoneParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", bindPhoneParam.getUserId());
        param.put("phone", bindPhoneParam.getPhone());
        param.put("verificationCode", bindPhoneParam.getVerificationCode());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.bindPhone", param);
            if("OK".equals(response.getSubCode())){
                commonResult = CommonResult.success();
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        }catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }



    /**
     * 电子协议签订
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult signContract(String userId)throws Exception{
        CommonResult commonResult = null;
        CommonResult memberInfo = getMemberInfo(userId);
        if(memberInfo.getCode() ==200) {
            Boolean ff = JSONObject.parseObject(memberInfo.getData().toString()).getBoolean("isIdentityChecked");
            if (ff) {
                final BizParameter param = new BizParameter();
                param.put("bizUserId", userId);
                param.put("jumpUrl", "https://webview.close");
                param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/signContractCallBack");
                param.put("source", 1L);
                try {
                    // 非小程序跳转
                    final String url = client.concatUrlParams("allinpay.yunst.memberService.signContract", param);
                    System.out.println(url);
                    commonResult = CommonResult.success(url);
                } catch (final Exception e) {
                    commonResult = CommonResult.failed(e.getMessage());
                    e.printStackTrace();
                }
            }else {
                commonResult = CommonResult.failed(ART_TITLE_NOT_EXIST_FILE);
            }
        }else {
            commonResult = CommonResult.failed(ART_TITLE_NOT_EXIST_FILE);
        }
        return  commonResult;
    }

    /**
     * 获取会员详情
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult getMemberInfo(String userId){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", userId);
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.getMemberInfo", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                final JSONObject data = JSON.parseObject(response.getData());
                commonResult = CommonResult.success(data.getJSONObject("memberInfo"));
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 查询卡bin
     * @param cardNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult getBankCardBin(String cardNo){
        final BizParameter param = new BizParameter();
        param.put("cardNo", client.encrypt(cardNo));
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.getBankCardBin", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject ii = JSONObject.parseObject(response.getData());
                commonResult = CommonResult.success(ii);
//                commonResult = CommonResult.success(response.getData());
                System.out.println(response.getData());
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 请求绑定银行卡
     */
    @Transactional(rollbackFor = Exception.class)
    public  CommonResult applyBindBankCard(ApplyBindBankCardParam applyBindBankCardParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", applyBindBankCardParam.getUserId());
        param.put("phone", applyBindBankCardParam.getPhone());
        param.put("cardCheck", applyBindBankCardParam.getCardCheck());
        param.put("cardNo", client.encrypt(applyBindBankCardParam.getCardNo()));
        param.put("name", applyBindBankCardParam.getName());
        param.put("identityNo", client.encrypt(applyBindBankCardParam.getIdentityNo()));
        param.put("identityType", 1L);
//        param.put("validate", client.encrypt("205012"));
//        param.put("cvv2", client.encrypt("321"));
        param.put("isSafeCard", false);
//        param.put("unionBank", "");
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.applyBindBankCard", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                UserBankInfo userBankInfo = new UserBankInfo();
                userBankInfo.setUserId(applyBindBankCardParam.getUserId());
//                userBankInfo.setIsDel(false);
                userBankInfo.setCreateTime(System.currentTimeMillis());
                userBankInfo.setCardNo(applyBindBankCardParam.getCardNo());
                userBankInfo.setName(applyBindBankCardParam.getName());
                userBankInfo.setIdentityNo(applyBindBankCardParam.getIdentityNo());
                userBankInfo.setPhone(applyBindBankCardParam.getPhone());
                userBankInfo.setCardCheck(applyBindBankCardParam.getCardCheck());
                userBankInfo.setTranceNum(JSONObject.parseObject(response.getData().toString()).getString("tranceNum"));
                userBankInfo.setBankName(JSONObject.parseObject(response.getData().toString()).getString("bankName"));
                userBankInfo.setBankCode(JSONObject.parseObject(response.getData().toString()).getString("bankCode"));
                userBankInfo.setCardType(JSONObject.parseObject(response.getData().toString()).getString("cardType"));
                userBankInfo.setIsDel(true);
                userBankInfoMapper.insert(userBankInfo);
                commonResult = CommonResult.success(JSONObject.parseObject(response.getData().toString()));
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 确认绑定银行卡
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult bindBankCard(BindBankCardParam bindBankCardParam){
        final BizParameter param = new BizParameter();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        param.put("bizUserId", bindBankCardParam.getUserId());
        param.put("tranceNum", bindBankCardParam.getTranceNum());// 申请绑卡时返回的tranceNum
        param.put("transDate", sdf.format(new Date()));
        param.put("phone", bindBankCardParam.getPhone());
        param.put("verificationCode", bindBankCardParam.getVerificationCode());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.bindBankCard", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                UserBankInfo userBankInfo = userBankInfoMapper.selectByTrance(bindBankCardParam.getTranceNum());
                userBankInfo.setAgreementNo(JSONObject.parseObject(response.getData().toString()).getString("agreementNo"));
                userBankInfo.setTransDate(JSONObject.parseObject(response.getData().toString()).getString("transDate"));
                userBankInfo.setIsDel(false);
                userBankInfoMapper.updateSelective(userBankInfo);
                commonResult = CommonResult.success();
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 查询绑定银行卡
     * @param queryBankCardParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult queryBankCard(QueryBankCardParam queryBankCardParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", queryBankCardParam.getUserId());
        if(queryBankCardParam.getCardNo() != null ){
            param.put("cardNo", client.encrypt(queryBankCardParam.getCardNo()));
        }
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.queryBankCard", param);
            if ("OK".equals(response.getSubCode())) {
                JSONObject ii = JSONObject.parseObject(response.getData());
                commonResult = CommonResult.success(ii);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 银行卡解绑
     * @param unbindBankCardParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult unbindBankCard(UnbindBankCardParam unbindBankCardParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", unbindBankCardParam.getUserId());
        param.put("cardNo", client.encrypt(unbindBankCardParam.getCardNo()));
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.unbindBankCard", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                UserBankInfo userBankInfo = userBankInfoMapper.selectByCardNo(unbindBankCardParam.getUserId(),unbindBankCardParam.getCardNo());
                userBankInfo.setIsDel(true);
                userBankInfoMapper.updateSelective(userBankInfo);
                commonResult = CommonResult.success(JSONObject.parseObject(response.getData().toString()));
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 设置支付密码
     * @param setPayPwdParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult setPayPwd(SetPayPwdParam setPayPwdParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", setPayPwdParam.getUserId());
        param.put("phone", setPayPwdParam.getPhone());
        param.put("name", setPayPwdParam.getName());
        param.put("identityType", "1");
        param.put("identityNo", client.encrypt(setPayPwdParam.getIdentityNo()));
        param.put("jumpUrl", "https://webview.close");
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/setPayPwdCallBack");
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.setPayPwd", param);
            if(response.getCode().equals("10000")){
                String url = JSON.parseObject(response.getData()).getString("url");
                commonResult = CommonResult.success(url);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }

//			final String url = client.concatUrlParams("allinpay.yunst.memberService.setPayPwd", param);
//			System.out.println(url);

//			browser(url);// 打开浏览器
//            client.execute("allinpay.yunst.memberService.setPayPwd", param);
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 修改支付密码
     * @param updatePayPwdParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updatePayPwd(UpdatePayPwdParam updatePayPwdParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", updatePayPwdParam.getUserId());
        param.put("name", updatePayPwdParam.getName());
        param.put("identityType", "1");
        param.put("identityNo", client.encrypt(updatePayPwdParam.getIdentityNo()));
        param.put("jumpUrl", "https://webview.close");
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/setPayPwdCallBack");
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.updatePayPwd", param);
            if("10000".equals(response.getCode())){
                String url = JSON.parseObject(response.getData()).getString("url");
                commonResult = CommonResult.success(url);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 重置密码
     * @param resetPayPwdParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult resetPayPwd(ResetPayPwdParam resetPayPwdParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", resetPayPwdParam.getUserId());
        param.put("name", resetPayPwdParam.getName());
        param.put("phone", resetPayPwdParam.getPhone());
        param.put("identityType", "1");
        param.put("identityNo", client.encrypt(resetPayPwdParam.getIdentityNo()));
        param.put("jumpUrl", "https://webview.close");
        param.put("backUrl", "https://ajz.e2prove.com/tlCallBack/setPayPwdCallBack");
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.resetPayPwd", param);
            if("10000".equals(response.getCode())){
                String url = JSON.parseObject(response.getData()).getString("url");
                commonResult = CommonResult.success(url);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 修改绑定手机还（密码验证）
     * @param updatePhoneByPayPwdParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updatePhoneByPayPwd(UpdatePhoneByPayPwdParam updatePhoneByPayPwdParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", updatePhoneByPayPwdParam.getUserId());
        param.put("name", updatePhoneByPayPwdParam.getName());
        param.put("identityType", "1");
        param.put("identityNo", client.encrypt(updatePhoneByPayPwdParam.getIdentityNo()));
        param.put("oldPhone", updatePhoneByPayPwdParam.getOldPhone());
        param.put("jumpUrl", "https://webview.close");
        param.put("backUrl", "http://ceshi.allinpay.com");
        CommonResult commonResult = null;
        try {
//            final String url = client.concatUrlParams("allinpay.yunst.memberService.updatePhoneByPayPwd", param);
//            System.out.println(url);
//            browser(url);// 打开浏览器
            final OpenResponse response = client.execute("allinpay.yunst.memberService.updatePhoneByPayPwd", param);
            if("ok".equals(response.getSubCode())){
                String url = JSON.parseObject(response.getData()).getString("url");
                commonResult = CommonResult.success(url);
            }else {
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }

    /**
     * 解绑手机号（原手机号短信验证）
     * @param unbindPhoneParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public CommonResult unbindPhone(UnbindPhoneParam unbindPhoneParam){
        final BizParameter param = new BizParameter();
        param.put("bizUserId", unbindPhoneParam.getUserId());
        param.put("phone", unbindPhoneParam.getPhone());
        param.put("verificationCode", unbindPhoneParam.getVerificationCode());
        CommonResult commonResult = null;
        try {
            final OpenResponse response = client.execute("allinpay.yunst.memberService.unbindPhone", param);
            if ("OK".equals(response.getSubCode())) {
                System.out.println(response.getData());
                commonResult = CommonResult.success(response.getData());
            }else{
                commonResult = CommonResult.failed(response.getSubMsg());
            }
        } catch (final Exception e) {
            commonResult = CommonResult.failed(e.getMessage());
            e.printStackTrace();
        }
        return commonResult;
    }


}
