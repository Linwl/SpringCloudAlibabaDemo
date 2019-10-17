package com.linwl.springcloudalibabademo.exception;

import com.linwl.springcloudalibabademo.enums.ERRORCODE;
import com.linwl.springcloudalibabademo.response.Msg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:08
 * @description ：
 * @modified By：
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Msg exceptionErrorHandler(Exception e) throws Exception {
        return new Msg.Builder().setCode(ERRORCODE.SystemErr).setMessage(MessageFormat.format("系统发生异常:{0}!",e.getMessage())).build();
    }

}