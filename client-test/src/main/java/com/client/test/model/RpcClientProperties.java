package com.client.test;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 2018/10/17.
 *
 * @author wangxiaodong
 */
@ConfigurationProperties(prefix = "rpc")
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
     * test
     */
    private String str;

    public RpcClientProperties() {
        System.out.println("RpcClientProperties init");
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
