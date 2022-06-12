package com.tz.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
}
