package com.linwl.gateway.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:58
 * @description ：
 * @modified By：
 */
@Data
@ToString
public class JwtSubject {

    /**
     * jwt TOKEN
     */
    private String token;

    /**
     * 用户名字
     */
    private String name;
}
