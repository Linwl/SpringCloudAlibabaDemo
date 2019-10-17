package com.linwl.springcloudalibabademo.enums;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:12
 * @description ：
 * @modified By：
 */
public enum DBTYPE {

    db1("db1"),db2("db2"),db3("db3");

    private String value;

    DBTYPE(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
