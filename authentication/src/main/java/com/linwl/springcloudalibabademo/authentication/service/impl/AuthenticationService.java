package com.linwl.springcloudalibabademo.authentication.service.impl;

import com.linwl.springcloudalibabademo.enums.ERRORCODE;
import com.linwl.springcloudalibabademo.response.Msg;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:03
 * @description ：
 * @modified By：
 */
@Service
public class AuthenticationService {

    /**
     * 登录
     * @param userNum
     * @param password
     * @return
     */
    public Msg login(String userNum,String password,String loginIp) throws LoginException
    {
        //TODO:登录业务
       return new Msg(ERRORCODE.Success);
    }

    /**
     * 刷新Token
     *
     * @param apiToken
     * @return
     */
    public Msg refreshToken(String apiToken) {
        //TODO:刷新Token业务
        return new Msg(ERRORCODE.Success);
    }
}
