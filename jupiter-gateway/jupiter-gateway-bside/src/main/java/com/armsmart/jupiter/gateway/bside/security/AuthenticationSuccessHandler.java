package com.armsmart.jupiter.gateway.bside.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        System.out.println("success");
        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization");
        //设置body
        // WsResponse wsResponse = WsResponse.success();
        byte[] dataBytes = {};
        ObjectMapper mapper = new ObjectMapper();
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            httpHeaders.add(HttpHeaders.AUTHORIZATION, uuid);
            //wsResponse.setResult(authentication.getName());
            //保存token
            //redisTemplate.boundValueOps(uuid).set(authentication.getName(), 2 * 60 * 60, TimeUnit.SECONDS);
            // dataBytes = mapper.writeValueAsBytes(wsResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
            // JsonObject result = new JsonObject();
            //result.addProperty("status", MessageCode.COMMON_FAILURE.getCode());
            //result.addProperty("message", "授权异常");
            // dataBytes = result.toString().getBytes();
        }
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        // System.out.println(wsResponse);
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
