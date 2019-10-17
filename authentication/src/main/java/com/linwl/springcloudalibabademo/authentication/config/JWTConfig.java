package com.linwl.springcloudalibabademo.authentication.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:21
 * @description ：
 * @modified By：
 */
@ConfigurationProperties("jwt.config")
@Component
@Data
public class JWTConfig {

    /**
     * JWT私钥
     */
    private String secretKey;

    /**
     * 过期时间
     */
    private long exNum;

    /**
     * 过期时间格式(Second,Minute,Hour,Day)
     */
    private String  exFormat;
}

