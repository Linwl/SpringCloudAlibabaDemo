package com.linwl.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:44
 * @description ：
 * @modified By：
 */
@Slf4j
public class JwtCheckGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtCheckGatewayFilterFactory.Config> {

    @Autowired
    private JwtFilter jwtFilter;

    public JwtCheckGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return jwtFilter;
    }

    public static class Config {
        // Put the configuration properties for your filter here
    }
}
