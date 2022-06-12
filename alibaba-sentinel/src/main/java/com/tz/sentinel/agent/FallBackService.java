package com.tz.sentinel.agent;

import org.springframework.stereotype.Component;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName FallBackService
 * @Description
 * @Date 19:50 2022/6/12
 **/
@Component
public class FallBackService implements FeignAgent {
    @Override
    public String hello() {
        return "系统繁忙，请稍后";
    }
}
