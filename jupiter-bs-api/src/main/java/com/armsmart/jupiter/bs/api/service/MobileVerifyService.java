package com.armsmart.jupiter.bs.api.service;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.GetMobileRequest;
import com.aliyun.dypnsapi20170525.models.GetMobileResponse;
import com.aliyun.dypnsapi20170525.models.VerifyMobileRequest;
import com.aliyun.dypnsapi20170525.models.VerifyMobileResponse;
import com.aliyun.teaopenapi.models.Config;
import com.armsmart.common.utils.JsonUtil;
import com.armsmart.jupiter.bs.api.config.MobileVerifyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author wei.lin
 * @date 2021/9/8
 */
@Service
@Slf4j
public class MobileVerifyService {
    @Autowired
    private MobileVerifyConfig mobileVerifyConfig;

    public String getMobile(String accessToken) throws Exception {
        Client client = createClient();
        GetMobileRequest getMobileRequest = new GetMobileRequest();
        getMobileRequest.setAccessToken(accessToken);
        GetMobileResponse response = client.getMobile(getMobileRequest);
        log.info("Get mobile response:{}", JsonUtil.bean2Json(response));
        if (null != response && Objects.equals("OK", response.getBody().getCode())) {
            return response.getBody().getMobileResultDTO.getMobile();
        }
        return null;
    }

    public boolean verifyMobile(String mobile, String accessCode) throws Exception {
        Client client = createClient();
        VerifyMobileRequest verifyMobileRequest = new VerifyMobileRequest();
        verifyMobileRequest.setAccessCode(accessCode);
        verifyMobileRequest.setPhoneNumber(mobile);
        VerifyMobileResponse response = client.verifyMobile(verifyMobileRequest);
        log.info("Verify mobile response:{}", JsonUtil.bean2Json(response));
        if (null != response && Objects.equals("OK", response.getBody().getCode())) {
            String result = response.getBody().getGateVerifyResultDTO().getVerifyResult();
            if (Objects.equals("PASS", result)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    private Client createClient() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(mobileVerifyConfig.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(mobileVerifyConfig.getAccessKeySecret());
        // 访问的域名
        config.endpoint = mobileVerifyConfig.getEndpoint();
        return new Client(config);
    }

}
