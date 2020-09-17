package com.github.easy.rpc.client;

import com.github.easy.rpc.client.model.RpcClientProperties;
import com.github.easy.rpc.client.service.NettyService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created on 2018/10/17.
 * 配置类
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
@ComponentScan(basePackages = "com.github.easy.rpc.client")
@EnableScheduling
@CommonsLog
public class RpcClientConfiguration implements ApplicationRunner {

    @Resource
    private RpcClientProperties rpcClientProperties;
    @Resource
    private NettyService nettyService;


    /**
     * 初始化netty
     * @return
     * @throws InterruptedException
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyService.init(rpcClientProperties);
    }
}
