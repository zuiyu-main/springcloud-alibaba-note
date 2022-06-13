package com.tz.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.tz.sentinel.agent.FeignAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName TestController
 * @Description 限流测试
 * @Date 19:30 2022/6/12
 **/
@RestController
public class TestController {
    @Autowired
    FeignAgent feignAgent;
    @SentinelResource(value = "Sentinel_SpringCloud",blockHandler = "exceptionHandler")
    @GetMapping("/ann")
    public String hello(){
        return "hello sentinel";
    }
    public String exceptionHandler(BlockException e){
        e.printStackTrace();
        return "系统繁忙，请稍后";
    }

    @GetMapping("/feign")
    public String feignHello(){
        return feignAgent.hello();
    }
    // 手动创建配置限流规则
    public static void main(String[] args) throws Exception {
        final String remoteAddress = "localhost:8848";
        final String groupId = "Sentinel_Demo";
        final String dataId = "com.alibaba.csp.sentinel.demo.flow.rule";
        final String rule = "[\n"
                + "  {\n"
                + "    \"resource\": \"Sentinel_SpringCloud\",\n"
                + "    \"controlBehavior\": 0,\n"
                + "    \"count\": 5.0,\n"
                + "    \"grade\": 1,\n"
                + "    \"limitApp\": \"default\",\n"
                + "    \"strategy\": 0\n"
                + "  }\n"
                + "]";
        ConfigService configService = NacosFactory.createConfigService(remoteAddress);
        System.out.println(configService.publishConfig(dataId, groupId, rule));
    }
}
