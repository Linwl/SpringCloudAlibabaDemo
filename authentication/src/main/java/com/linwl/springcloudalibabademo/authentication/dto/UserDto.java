package com.linwl.springcloudalibabademo.authentication.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:31
 * @description ：
 * @modified By：
 */
@Data
@ToString
public class UserDto {

    /**
     * 名字
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
}
