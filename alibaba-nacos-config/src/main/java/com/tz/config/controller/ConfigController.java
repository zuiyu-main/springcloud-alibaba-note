package com.tz.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author https://github.com/TianPuJun @256g的胃
 * @ClassName ConfigController
 * @Description 配置管理
 * @Date 16:21 2020/8/25
 **/
@RestController
@RefreshScope
@RequestMapping("/config")
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @GetMapping("/get")
    public boolean get(){
        return useLocalCache;
    }

}
