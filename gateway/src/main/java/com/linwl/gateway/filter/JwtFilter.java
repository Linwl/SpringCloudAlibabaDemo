package com.linwl.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:44
 * @description ：JWT 验权,在配置文件配置：
 *                filters:
 *                  - JwtCheck
 *                才起作用
 * @modified By：
 */
public class JwtFilter implements GatewayFilter, Ordered {

    @Override
    public int getOrder() {
        return -97;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange);
    }
}
