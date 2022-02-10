package com.armsmart.jupiter.bs.api.dto.request;


import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotBlank;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * ApplyCheck添加DTO
 *
 * @author dong.chu
 **/
@Data
@ApiModel(value = "_request")
public class CompanyKeyParam {

    @NotBlank(message = "合约地址不能为空")
    @ApiModelProperty(value = "合约地址", required = true)
    private String contractAddr;

    @NotBlank(message = "公钥模数不能为空")
    @Length(max = 256, message = "公钥模数不能超过256")
    @ApiModelProperty(value = "公钥模数", required = true)
    private String modulus;

    @NotBlank(message = "公钥指数不能为空")
    @Length(max = 256, message = "公钥指数长度不能超过256")
    @ApiModelProperty(value = "公钥指数", required = true)
    private String exponent;

    public static void main3(String[] args) throws Exception {
        long time = System.currentTimeMillis();
        String appKey = "9669092353";
        String activeCode = "074b12231f8a4377be461b456c4af1c7";
        String encode = URLEncoder.encode("POST/eco-third-sdf/coupon/codeExchangeeco-auth-appkey:" + appKey + "eco-auth-timestamp:" + time + "json={\"activeCode\":\"" + activeCode + "\"}c634d5e29a7afd8118eb18a028524835", "utf-8");
        System.out.println(encode);

        final String sign =  DigestUtil.sha256Hex(encode.getBytes());
        System.out.println();
        String url = "https://eco-gateway-8080-wlm-member-dev.baicorv.e-engine.cn/eco-third-sdf/coupon/codeExchange";
        //String url = "https://beco.nxengine.com/eco-third-sdf/coupon/codeExchange";
        //String url = "http://localhost:8080/eco-third-sdf/coupon/codeExchange";

        RestTemplate restTemplate = new RestTemplate(generateHttpRequestFactory());
        //RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("eco-auth-appkey", appKey);
        requestHeaders.add("eco-auth-timestamp", String.valueOf(time));
        requestHeaders.add("eco-auth-sign", sign);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activeCode", activeCode);
        String params = jsonObject.toString();
        HttpEntity<String> httpEntity = new HttpEntity<>(params, requestHeaders);
        JSONObject jsonObject1 = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        System.out.println(jsonObject1.toString());
    }

    public static void main(String[] args) throws Exception {
        long time = 1623424500004L;
        String appKey = "7352945482";
        String secretKey = "5e7ff31cb285b927e7108e172c553ce7";
        String activeCode = "WDDSHhPMCpgaNNML";
        String encode = URLEncoder.encode("POST/eco-third-sdf/coupon/codeExchangeeco-auth-appkey:" + appKey + "eco-auth-timestamp:" + time + "json={\"activeCode\":\"" + activeCode + "\"}" + secretKey, "utf-8");
        System.out.println(encode);
        final String sign = DigestUtil.sha256Hex(encode.getBytes());
        System.out.println(sign);
        //POST/eco-third-sdf/coupon/codeExchangeeco-auth-appkey:7352945482eco-auth-timestamp:1623424500004json={"activeCode":"WDDSHhPMCpgaNNML"}5e7ff31cb285b927e7108e172c553ce7
        //String url = "https://eco-gateway-8080-wlm-member-dev.baicorv.e-engine.cn/eco-third-sdf/coupon/codeExchange";
        String url = "https://beco.nxengine.com/eco-third-sdf/coupon/codeExchange";
        //String url = "http://localhost:8080/eco-third-sdf/coupon/codeExchange";

        RestTemplate restTemplate = new RestTemplate(generateHttpRequestFactory());
        //RestTemplate restTemplate = new RestTemplate();

        /*HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add("eco-auth-appkey", appKey);
        requestHeaders.add("eco-auth-timestamp", String.valueOf(time));
        requestHeaders.add("eco-auth-sign", sign);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activeCode", activeCode);
        String params = jsonObject.toString();
        HttpEntity<String> httpEntity = new HttpEntity<>(params, requestHeaders);
        JSONObject jsonObject1 = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        System.out.println(jsonObject1.toString());*/
    }


    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }

    public static HttpComponentsClientHttpRequestFactory generateHttpRequestFactory()
            throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);
        return factory;
    }

}
