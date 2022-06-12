package com.tz.sentinel.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName SentinelConfig
 * @Description
 * @Date 23:15 2022/6/12
 **/
@Component
public class SentinelConfig {
    @PostConstruct
    public void init(){
        WebCallbackManager.setRequestOriginParser(new RequestOriginParser() {
            @Override
            public String parseOrigin(HttpServletRequest httpServletRequest) {
                return httpServletRequest.getRemoteAddr();
            }
        });
    }
}
