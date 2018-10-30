package com.github.easy.rpc.server;

import com.github.easy.rpc.server.model.RpcServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
@ComponentScan(basePackages = "com.github.easy.rpc.server")
@EnableScheduling
public class ServerRpcConfiguration {

    @Resource
    private RpcServerProperties rpcServerProperties;

//    /**
//     * 注册RpcCache
//     * @return
//     * @throws ClassNotFoundException
//     * @throws IllegalAccessException
//     * @throws InstantiationException
//     */
//    @Bean
//    public RpcCache rpcCache() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        String className = rpcServerProperties.getCacheClassName();
//        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
//        return (RpcCache) clazz.newInstance();
//    }
}
