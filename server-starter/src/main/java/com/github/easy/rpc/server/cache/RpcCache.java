package com.github.easy.rpc.server.cache;

import com.github.easy.rpc.common.model.RpcResponse;

import java.util.concurrent.TimeUnit;

/**
 * Created on 2018/10/19.
 * 缓存Rpc返回信息
 * @author wangxiaodong
 */
public interface RpcCache {

    /**
     * 获取值
     * @param key
     * @return
     */
    Object getObject(String key);

    /**
     * 存放数据
     * @param key
     * @param value
     * @param expiry 过期时间（秒）
     */
    void putObject(String key, Object value, int expiry);

    /**
     * 根据key查找是否存在
     * @param key
     * @return
     */
    boolean isExist(String key);

    /**
     * 移除数据
     * @param key
     */
    void remove(String key);
}
