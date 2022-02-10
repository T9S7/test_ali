package com.armsmart.jupiter.bs.api.wxpay;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wei.lin
 * @date 2021/8/18
 */
@Component
public class WeChatConfig {

    @Autowired
    private WeChatPayProperties weChatPayProperties;

    @Bean
    public WXPay wxPay(WXPayConfig wxPayConfig) throws Exception {
        return new WXPay(wxPayConfig, weChatPayProperties.getNotifyUrl(), false);
    }

    @Bean
    public WXPayConfig wxPayConfig() {
        return new WXPayConfigImpl(weChatPayProperties);
    }
}
