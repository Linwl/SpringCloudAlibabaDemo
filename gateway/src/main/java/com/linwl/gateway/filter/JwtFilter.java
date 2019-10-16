package com.linwl.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.linwl.gateway.client.AuthenticationService;
import com.linwl.gateway.config.JwtConfig;
import com.linwl.gateway.dto.JwtSubject;
import enums.ERRORCODE;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import response.Msg;
import utils.JWTUtils;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:44
 * @description ：JWT 验权,在配置文件配置：
 *                filters:
 *                  - JwtCheck
 *                才起作用
 * @modified By：
 */
@Slf4j
public class JwtFilter implements GatewayFilter, Ordered {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public int getOrder() {
        return -97;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        // 跳过jwt验证
        if (isSkipPath(path)) {
            log.info(MessageFormat.format("访问跳过jwt验证路径<{0}>!", path));
            return chain.filter(exchange);
        }
        String jwtToken = exchange.getRequest().getHeaders().getFirst("API-Token");
        if (StringUtils.isEmpty(jwtToken)) {
            log.info("Http头部API-Token为空,开始检验参数!");
            jwtToken = exchange.getRequest().getQueryParams().getFirst("API-Token");
        }
        // 校验jwtToken的合法性
        if (StringUtils.isNotEmpty(jwtToken)) {
            log.info(MessageFormat.format("开始校验API-Token<{0}>!", jwtToken));
            try {
                Claims claims = JWTUtils.parseJWT(jwtToken, jwtConfig.getSecretKey());
                if (claims != null) {
                    String role = claims.get("role", String.class);
                    log.info(MessageFormat.format("角色<{0}>访问了系统!", role));
                    return chain.filter(exchange);
                } else {
                    log.info("解析不了该Token,当做Token过期处理!");
                    // TOKEN过期
                    return handleTokenExpire(jwtToken, exchange, chain);
                }

            } catch (Exception e) {
                log.error(MessageFormat.format("解析Token发生异常:{0}", e.getMessage()));
                return handleTokenExpire(jwtToken, exchange, chain);
            }
        } else {
            // 不合法(响应未登录的异常)
            String warningStr =
                    JSONObject.toJSONString(new Msg(ERRORCODE.PermissionDenied, "缺少Api-Token"));
            return errorHandle(exchange, warningStr);
        }
    }


    /**
     * 处理过期token
     *
     * @param apiToken
     */
    private Mono<Void> handleTokenExpire(
            String apiToken, ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(MessageFormat.format("API-Token<{0}>已过期,开始验证是否刷新Token!", apiToken));
        Msg msg = authenticationService.refreshToken(apiToken);
        if (ERRORCODE.RefreshToken.getErrorcode() == msg.getCode()) {
            String data = JSONObject.toJSONString(msg.getData());
            JwtSubject jwtSubject = JSONObject.parseObject(data, JwtSubject.class);
            log.info(MessageFormat.format("刷新了用户<{0}>的API-Token", jwtSubject.getName()));
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            ServerHttpResponseDecorator decoratedResponse =
                    new ServerHttpResponseDecorator(originalResponse) {
                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            if (body instanceof Flux) {
                                Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                                return super.writeWith(
                                        fluxBody.map(
                                                dataBuffer -> {
                                                    // probably should reuse buffers
                                                    byte[] content = new byte[dataBuffer.readableByteCount()];
                                                    dataBuffer.read(content);
                                                    // 释放掉内存
                                                    DataBufferUtils.release(dataBuffer);
                                                    String oldResponse = new String(content, Charset.forName("UTF-8"));
                                                    // TODO:增加刷新Token
                                                    Msg msg = JSONObject.parseObject(oldResponse, Msg.class);
                                                    RefreshTokenResponseDto dto = new RefreshTokenResponseDto();
                                                    dto.setData(msg.getData());
                                                    dto.setNewToken(jwtSubject.getToken());
                                                    Msg newResponse = new Msg.Builder().setCode(ERRORCODE.RefreshToken).setData(dto).setCount(1).build();
                                                    String responseJson = JSONObject.toJSONString(newResponse);
                                                    log.info(MessageFormat.format("返回了用户<{0}>新的API-Token<{1}>", jwtSubject.getName(), jwtSubject.getToken()));
                                                    byte[] uppedContent = responseJson.getBytes();
                                                    return bufferFactory.wrap(uppedContent);
                                                }));
                            }
                            // if body is not a flux. never got there.
                            return super.writeWith(body);
                        }
                    };
            // replace response with decorator
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        return errorHandle(exchange, JSONObject.toJSONString(msg));
    }

    /**
     * 错误处理
     *
     * @param exchange
     * @param message
     * @return
     */
    private Mono<Void> errorHandle(ServerWebExchange exchange, String message) {
        log.error(message);
        ServerHttpResponse response = exchange.getResponse();
        // 设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        // 设置body
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(message.getBytes());
        return response.writeWith(Mono.just(bodyDataBuffer));
    }


    /**
     * 是否跳过jwt验证路径
     *
     * @return
     */
    private boolean isSkipPath(String path) {
        boolean result = false;
        try {
            String skipPaths = jwtConfig.getSkipPath();
            if (skipPaths.contains(",")) {
                List<String> skipPathList = Stream.of(skipPaths.split(",")).collect(Collectors.toList());
                for (String skipPath : skipPathList) {
                    if (skipPath.equals(path)) {
                        result = true;
                        break;
                    }
                }
            } else {
                result = skipPaths.equals(path);
            }
        } catch (Exception e) {
            log.error(MessageFormat.format("解析跳过jwt验证路径异常:{0}!", e.getMessage()));
        } finally {
            return result;
        }
    }
}
