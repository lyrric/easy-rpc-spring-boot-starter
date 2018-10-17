package com.demo.client;

import com.easy.rpc.client.model.RpcClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created on 2018/10/17.
 *
 * @author wangxiaodong
 */
@SpringBootApplication
@EnableConfigurationProperties(RpcClientProperties.class)
public class DemoClientApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoClientApp.class, args);
    }
}
