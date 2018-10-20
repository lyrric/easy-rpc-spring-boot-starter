
package com.client.test.model;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/10/17.
 * 配置类
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
public class RpcClientConfiguration {


}
