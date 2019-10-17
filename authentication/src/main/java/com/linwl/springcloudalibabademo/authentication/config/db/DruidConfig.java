package com.linwl.springcloudalibabademo.authentication.config.db;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:03
 * @description ：
 * @modified By：
 */
@Configuration
public class DruidConfig {

    /**
     * 注册ServletRegistrationBean
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet()
    {
        ServletRegistrationBean reg =new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // IP白名单
        reg.addInitParameter("allow","127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
//        reg.addInitParameter("deny","127.0.0.1");
        //控制台管理用户
        reg.addInitParameter("loginUsername","linwl");
        reg.addInitParameter("loginPassword","linwl#1234");
        //是否能够重置数据
        reg.addInitParameter("resetEnable","false");
        return reg;
    }

    /**
     * 注册FilterRegistrationBean
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
        return filterRegistrationBean;
    }
}
