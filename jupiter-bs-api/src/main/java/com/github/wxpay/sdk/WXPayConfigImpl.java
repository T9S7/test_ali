package com.github.wxpay.sdk;


import com.armsmart.jupiter.bs.api.wxpay.WeChatPayProperties;

import java.io.InputStream;

/**
 * @author wei.lin
 * @date 2021/8/18
 */
public class WXPayConfigImpl extends WXPayConfig {

    private String appId;

    private String mchId;

    private String apiKey;

    private String certData;

    public WXPayConfigImpl(WeChatPayProperties weChatPayProperties) {
        this.appId = weChatPayProperties.getAppId();
        this.mchId = weChatPayProperties.getMchId();
        this.apiKey = weChatPayProperties.getApiKey();
    }

    @Override
    String getAppID() {
        return this.appId;
    }

    @Override
    String getMchID() {
        return this.mchId;
    }

    @Override
    String getKey() {
        return this.apiKey;
    }

    @Override
    InputStream getCertStream() {
        return this.getClass().getResourceAsStream("apiclient_cert.p12");
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return super.getHttpConnectTimeoutMs();
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return super.getHttpReadTimeoutMs();
    }

    @Override
    IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
    }

    @Override
    public boolean shouldAutoReport() {
        return false;
    }

    @Override
    public int getReportWorkerNum() {
        return super.getReportWorkerNum();
    }

    @Override
    public int getReportQueueMaxSize() {
        return super.getReportQueueMaxSize();
    }

    @Override
    public int getReportBatchSize() {
        return super.getReportBatchSize();
    }
}
