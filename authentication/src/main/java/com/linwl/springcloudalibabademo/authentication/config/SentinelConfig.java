package com.linwl.springcloudalibabademo.authentication.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 9:21
 * @description ：
 * @modified By：
 */
@Configuration
@Slf4j
public class SentinelConfig {

    @Autowired
    private SentinelProperties sentinelProperties;

    @Bean
    public SentinelConfig init() throws Exception {
        log.info("[NacosSource初始化,从Nacos中获取熔断规则]");
        sentinelProperties.getDatasource().entrySet().stream().filter(map -> {
            return map.getValue().getNacos() != null;
        }).forEach(data -> {
            NacosDataSourceProperties nacos = data.getValue().getNacos();
            ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<List<FlowRule>>(nacos.getServerAddr(), nacos.getGroupId(), nacos.getDataId(), source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
            }));
            FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        });

        return new SentinelConfig();
    }
}