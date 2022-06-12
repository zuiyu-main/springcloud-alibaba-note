package com.tz.sentinel.agent;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName FeignAgent
 * @Description
 * @Date 19:40 2022/6/12
 **/
@FeignClient(value = "alibaba-nacos-discovery-server",fallback = FallBackService.class)
public interface FeignAgent {
    @GetMapping("/test")
    String hello();
}
