package com.linwl.springcloudalibabademo.authentication.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.linwl.springcloudalibabademo.authentication.dto.UserDto;
import com.linwl.springcloudalibabademo.authentication.fallback.AuthenticationFallBack;
import com.linwl.springcloudalibabademo.authentication.service.impl.AuthenticationService;
import com.linwl.springcloudalibabademo.response.Msg;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:26
 * @description ：
 * @modified By：
 */
@RequestMapping("verify")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("登录")
    @PostMapping("login")
    @SentinelResource(value = "login", fallbackClass = AuthenticationFallBack.class, fallback = "login")
    public Msg login(@RequestBody UserDto user, HttpServletRequest request) throws LoginException {
        String loginIP = request.getHeader("RequestIP");
        return authenticationService.login(user.getName(), user.getPassword(),loginIP);
    }

    @ApiOperation("刷新Token")
    @GetMapping("refreshToken")
    @ApiImplicitParam(name = "apiToken", value = "token", required = true, dataType = "String")
    public Msg refreshToken(@RequestParam(value = "apiToken") String apiToken) {
        return authenticationService.refreshToken(apiToken);
    }
}
