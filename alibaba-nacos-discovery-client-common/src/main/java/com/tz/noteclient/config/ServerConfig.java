package com.tz.noteclient.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author https://github.com/TianPuJun @256g的胃
 * @ClassName ServerConfig
 * @Description 通用配置
 * @Date 17:03 2020/8/25
 **/
@Configuration
public class ServerConfig {
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
