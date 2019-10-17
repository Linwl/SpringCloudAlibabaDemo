package com.linwl.springcloudalibabademo.authentication.entity.db1;

import lombok.Data;
import lombok.ToString;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:19
 * @description ：
 * @modified By：
 */
@Data
@ToString
public class UserEntity {

    private Integer id;

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
