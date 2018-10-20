package com.github.easy.rpc.server;

import com.github.easy.rpc.server.model.RpcServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

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


}
