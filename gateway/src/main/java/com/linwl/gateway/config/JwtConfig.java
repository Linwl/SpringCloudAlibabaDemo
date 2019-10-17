package com.linwl.gateway.config;

import com.linwl.gateway.filter.JwtCheckGatewayFilterFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:44
 * @description ：
 * @modified By：
 */
@ConfigurationProperties("jwt.config")
@Configuration
@Data
public class JwtConfig {

    /**
     * JWT私钥
     */
    private String secretKey;

    /**
     * 跳过jwt验证的路径(多个路径请,分隔)
     */
    private String skipPath;

    @Bean
    public JwtCheckGatewayFilterFactory jwtCheckGatewayFilterFactory(){
        return new JwtCheckGatewayFilterFactory();
    }
}

