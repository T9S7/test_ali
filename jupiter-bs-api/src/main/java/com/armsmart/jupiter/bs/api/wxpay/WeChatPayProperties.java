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
@ConfigurationProperties(prefix = "wx-pay")
public class WeChatPayProperties {

    private String appId;

    private String mchId;

    private String apiKey;

    private String notifyUrl;

    public static void main(String[] args) throws Exception {
        WeChatPayProperties weChatPayProperties = new WeChatPayProperties();
        weChatPayProperties.setApiKey("HEFEIYIWUYIZHENGKEJIYOUXIANGONG1");
        weChatPayProperties.setAppId("wx57217fd2a4bfea33");
        weChatPayProperties.setMchId("1613025148");
        WXPayConfig wxPayConfig = new WXPayConfigImpl(weChatPayProperties);
        WXPay wxPay = new WXPay(wxPayConfig, "https://www.e2prove.com/pay/callback",false);
        SortedMap<String, String> map = new TreeMap<>();
        //设备号
        map.put("device_info", "WEB");
        //商品名称
        map.put("body", "古币");
        //商户订单号【备注：每次发起请求都需要随机的字符串，否则失败。
        map.put("out_trade_no", String.valueOf(System.nanoTime()));
        //支付金额：分
        map.put("total_fee", "1");
        //客户端主机
        map.put("spbill_create_ip", "58.48.56.178");
        //交易类型
        map.put("trade_type", TradeType.NATIVE.name());
        Map<String, String> payResultMap = wxPay.unifiedOrder(map);
        System.out.println("unifiedOrder result:" + JSONUtil.toJsonStr(payResultMap));
    }
}
