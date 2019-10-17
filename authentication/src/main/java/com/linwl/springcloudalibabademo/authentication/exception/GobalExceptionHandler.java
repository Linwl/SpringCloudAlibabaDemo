package com.linwl.springcloudalibabademo.authentication.exception;

import com.linwl.springcloudalibabademo.exception.BaseExceptionHandler;
import com.linwl.springcloudalibabademo.response.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:04
 * @description ：
 * @modified By：
 */
@RestControllerAdvice
@Slf4j
public class GobalExceptionHandler extends BaseExceptionHandler {

    @Override
    public Msg exceptionErrorHandler(Exception e) throws Exception {
        log.error(MessageFormat.format("用户验证服务发生异常:{0}!",e.getMessage()),e);
        return super.exceptionErrorHandler(e);
    }
}
