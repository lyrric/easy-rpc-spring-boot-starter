package com.github.easy.rpc.client.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 2018/10/17.
 *
 * @author wangxiaodong
 */
@ConfigurationProperties(prefix = "rpc")
@Data
public class RpcClientProperties {
    /**
     * server端口
     */
    private String serverHost;
    /**
     * server端口
     */
    private Integer serverPort;
    /**
     * 接口路径
     */
    private String basePackage;
    /**
     * 超时设置
     */
    private Integer requestTimeout;





}
