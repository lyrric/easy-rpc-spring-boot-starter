package com.github.easy.rpc.server.model;

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
    /**
     * 超时设置(发送请求的和现在的时间差大于此值时不做任何处理)，单位秒，默认为一分钟
     */
    private Long requestExpiry = (long) 60;
    /**
     * 返回结果的缓存时间(缓存时间内收到同样的requestId将会直接返回缓存中的值)，单位秒，默认为一分钟
     */
    private Long responseExpiry = (long)60;
}
