package com.linwl.gateway.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 8:36
 * @description ：
 * @modified By：
 */
@Data
@ToString
public class RefreshTokenResponseDto {
    /**
     * 请求返回的数据实体
     */
    private Object data;

    /**
     * 新Token
     */
    private String newToken;
}
