package com.linwl.springcloudalibabademo.authentication.config.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.linwl.springcloudalibabademo.enums.DBTYPE;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:22
 * @description ：
 * @modified By：
 */
@Configuration
@MapperScan("com.linwl.springcloudalibabademo.authentication.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {

    private static final String MAPPER_LOCATION = "classpath*:mapper/**/*.xml";
    private static final String DOMAIN_PACKAGE = "com.linwl.springcloudalibabademo.authentication.entity,com.linwl.springcloudalibabademo.authentication.dao";

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource db1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource db2() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db3")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db3")
    public DataSource db3() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("db1") DataSource db1,@Qualifier("db2") DataSource db2,@Qualifier("db3") DataSource db3) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTYPE.db1.getValue(), db1);
        targetDataSources.put(DBTYPE.db2.getValue(), db2);
        targetDataSources.put(DBTYPE.db3.getValue(), db3);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }


    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(db1(),db2(),db3()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MybatisPlusConfig.MAPPER_LOCATION));
        sqlSessionFactory.setTypeAliasesPackage(MybatisPlusConfig.DOMAIN_PACKAGE);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //添加分页功能
        sqlSessionFactory.setPlugins(new Interceptor[]{
                paginationInterceptor()
        });
        return sqlSessionFactory.getObject();
    }
}
