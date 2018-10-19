package com.easy.rpc.server;

import com.easy.rpc.server.model.RpcServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
@ComponentScan(basePackages = "com.easy.rpc.server")
public class ServerRpcConfiguration {


}
