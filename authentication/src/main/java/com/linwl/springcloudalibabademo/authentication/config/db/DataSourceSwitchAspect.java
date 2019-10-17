package com.linwl.springcloudalibabademo.authentication.config.db;

import com.linwl.springcloudalibabademo.db.DbContextHolder;
import com.linwl.springcloudalibabademo.enums.DBTYPE;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:43
 * @description ：
 * @modified By：
 */
@Slf4j
@Aspect
@Order(-100)
@Component
public class DataSourceSwitchAspect {

    @Pointcut("execution(* com.linwl.springcloudalibabademo.authentication.mapper.db1..*.*(..))")
    public void db1Aspect(){

    }

    @Pointcut("execution(* com.linwl.springcloudalibabademo.authentication.mapper.db2..*.*(..))")
    public void db2Aspect(){

    }

    @Pointcut("execution(* com.linwl.springcloudalibabademo.authentication.mapper.db3..*.*(..))")
    public void db3Aspect(){

    }

    @Before("db1Aspect()")
    public void db1() {
        log.info("切换到db1 数据源...");
        DbContextHolder.setDbType(DBTYPE.db1);
    }

    @Before("db2Aspect()")
    public void db2() {
        log.info("切换到db2 数据源...");
        DbContextHolder.setDbType(DBTYPE.db2);
    }

    @Before("db3Aspect()")
    public void db3() {
        log.info("切换到db3 数据源...");
        DbContextHolder.setDbType(DBTYPE.db3);
    }


}
