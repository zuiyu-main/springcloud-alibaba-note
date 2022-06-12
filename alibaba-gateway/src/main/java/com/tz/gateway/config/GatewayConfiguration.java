package com.tz.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName GatewayConfiguration
 * @Description
 * @Date 20:16 2022/6/12
 **/
@Component
public class GatewayConfiguration {
    @PostConstruct
    public void doInit(){
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                return ServerResponse.status(200).syncBody("系统繁忙，请稍后");
            }
        });
    }
}
