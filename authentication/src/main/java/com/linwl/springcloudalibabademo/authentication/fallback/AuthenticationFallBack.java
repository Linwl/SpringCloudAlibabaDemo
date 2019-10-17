package com.linwl.springcloudalibabademo.authentication.fallback;

import com.linwl.springcloudalibabademo.authentication.dto.UserDto;
import com.linwl.springcloudalibabademo.enums.ERRORCODE;
import com.linwl.springcloudalibabademo.response.Msg;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:34
 * @description ：
 * @modified By：
 */
@Slf4j
public class AuthenticationFallBack {

    public static Msg login(UserDto user) {
        log.info(MessageFormat.format("熔断降级用户<{0}>的登录请求!", user.getName()));
        return new Msg(ERRORCODE.FallBackError, "由于登录人数较多,请稍等...");
    }
}
