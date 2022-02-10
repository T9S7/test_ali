package com.armsmart.jupiter.gateway.bside.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
@Component
public class ScAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json; charset=UTF-8");

        HashMap<String, String> map = new HashMap<>();
        map.put("code", "000000");
        map.put("message", "未授权禁止访问");

        log.error("access forbidden path={}", exchange.getRequest().getPath());

        DataBuffer dataBuffer = response.bufferFactory().wrap(map.toString().getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
