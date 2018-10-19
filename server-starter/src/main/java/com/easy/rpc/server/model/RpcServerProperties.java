package com.easy.rpc.server.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@ConfigurationProperties(prefix = "rpc.server")
@Data
public class RpcServerProperties {
    /**
     * server 端口
     */
    private Integer port = 9898;
}
