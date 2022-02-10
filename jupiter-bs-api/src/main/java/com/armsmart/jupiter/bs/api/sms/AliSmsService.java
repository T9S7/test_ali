package com.armsmart.jupiter.bs.api.sms;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.jupiter.bs.api.config.SmsConfig;
import com.armsmart.jupiter.bs.api.error.BusinessError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class AliSmsService {

    @Autowired
    private SmsConfig smsConfig;

    /**
     * 用户注册-短信验证码
     *
     * @param phoneNumber
     * @param code
     * @return void
     */
    public void sendRegisterSmsCode(String phoneNumber, String code) {
        String templateParam = "{\"code\":\"" + code + "\"}";
        sendSms(phoneNumber, smsConfig.getRegisterTemplateCode(), templateParam);
    }

    /**
     * 身份认证-短信验证码
     *
     * @param phoneNumber
     * @param code
     * @return void
     */
    public void sendAuthSmsCode(String phoneNumber, String code) {
        String templateParam = "{\"code\":\"" + code + "\"}";
        sendSms(phoneNumber, smsConfig.getAuthTemplateCode(), templateParam);
    }

    /**
     * 短信通知-用户密码发送
     *
     * @param phoneNumber
     * @param pwd
     * @return void
     */
    public void sendAppUserPwd(String phoneNumber, String pwd) {
        String templateParam = "{\"pwd\":\"" + pwd + "\"}";
        sendSms(phoneNumber, smsConfig.getAdminRegisterTemplateCode(), templateParam);
    }

    private JSON sendSms(String phoneNumber, String templateCode, String templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile(smsConfig.getRegion(), smsConfig.getAccessKeyId(), smsConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(smsConfig.getEndpoint());
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", smsConfig.getRegion());
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            log.info("Sending sms!TemplateCode={},phoneNum = {},pramas={}", templateCode, phoneNumber, templateParam);
            CommonResponse response = client.getCommonResponse(request);
            log.info("Received sms response:{}", response.getData());
            JSON json = JSONUtil.parse(response.getData());
            if (!"OK".equals(json.getByPath("Code").toString())) {
                throw new BusinessException(BusinessError.SMS_SEND_FAILED);
            }
            return json;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(BusinessError.SMS_SEND_FAILED);
        }
    }


}
