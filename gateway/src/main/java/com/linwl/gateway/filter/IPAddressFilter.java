package com.linwl.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:38
 * @description ：
 * @modified By：
 */
@Slf4j
@Component
public class IPAddressFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        InetAddress inetAddress = exchange.getRequest().getRemoteAddress().getAddress();
        String ip = inetAddress.getHostAddress();
        int port = exchange.getRequest().getRemoteAddress().getPort();
        log.info(MessageFormat.format("IP地址<{0}:{1}>访问了系统服务!", ip, port));
        // 向headers中放文件，记得build
        String[] ipAddr = {MessageFormat.format("{0}:{1}", ip, port)};
        ServerHttpRequest host = exchange.getRequest().mutate().header("RequestIP", ipAddr).build();
        // 将现在的request 变成 change对象
        ServerWebExchange build = exchange.mutate().request(host).build();
        return chain.filter(build);
    }

    @Override
    public int getOrder() {
        return -98;
    }
}
