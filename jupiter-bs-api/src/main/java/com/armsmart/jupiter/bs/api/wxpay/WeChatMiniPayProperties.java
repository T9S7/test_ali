package com.armsmart.jupiter.bs.api.wxpay;

import cn.hutool.json.JSONUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConfigImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author wei.lin
 * @date 2021/8/18
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx-minipay")
public class WeChatMiniPayProperties {

    private String appid;

    private String appsecret;


}
