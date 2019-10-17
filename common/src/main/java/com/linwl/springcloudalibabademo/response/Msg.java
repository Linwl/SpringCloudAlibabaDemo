package com.linwl.springcloudalibabademo.response;

import com.linwl.springcloudalibabademo.enums.ERRORCODE;
import lombok.Data;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:14
 * @description ：
 * @modified By：
 */
@Data
public class Msg {

    /**
     * 错误码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 记录数
     */
    private long count;

    public Msg()
    {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = ERRORCODE.Success.getMessage();
    }

    public Msg(String message)
    {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = message;
    }

    public Msg(Object data,long count)
    {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = ERRORCODE.Success.getMessage();
        this.data =data;
        this.count =count;
    }

    public Msg(ERRORCODE errorcode,Object data,long count)
    {
        this.code = errorcode.getErrorcode();
        this.message = errorcode.getMessage();
        this.data =data;
        this.count =count;
    }


    public Msg(String message,Object data,long count)
    {
        this.code = ERRORCODE.Success.getErrorcode();
        this.message = message;
        this.data =data;
        this.count =count;
    }

    public Msg(ERRORCODE errorcode)
    {
        this.code = errorcode.getErrorcode();
        this.message = errorcode.getMessage();
        this.data = null;
        this.count =0;
    }

    public Msg(ERRORCODE errorcode,String message)
    {
        this.code = errorcode.getErrorcode();
        this.message = message;
        this.count =0;
        this.data = null;
    }

    public Msg(Builder builder)
    {
        this.code = builder.code;
        this.data =builder.data;
        this.message =builder.message;
        this.count = builder.count;
    }


    public static class Builder {
        /**
         * 错误码
         */
        private int code;

        /**
         * 提示信息
         */
        private String message;

        /**
         * 返回数据
         */
        private Object data;

        /**
         * 记录数
         */
        private long count;

        public Builder setCode(ERRORCODE errorcode)
        {
            this.code = errorcode.getErrorcode();
            this.message=errorcode.getMessage();
            return this;
        }

        public Builder setMessage(String message)
        {
            this.message = message;
            return this;
        }

        public Builder setData(Object data)
        {
            this.data = data;
            return this;
        }

        public Builder setCount(long count)
        {
            this.count = count;
            return this;
        }

        public Msg build()
        {
            return new Msg(this);
        }
    }
}
