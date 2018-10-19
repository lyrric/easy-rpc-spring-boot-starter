package com.github.easy.rpc.server.cache;

import com.github.easy.rpc.common.model.RpcResponse;
import com.github.easy.rpc.server.model.RpcServerProperties;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2018/10/19.
 * 默认缓存
 * @author wangxiaodong
 */
@Component
@CommonsLog
public class DefaultRpcCache implements RpcCache{

    @Resource
    private RpcServerProperties rpcServerProperties;

    private ConcurrentHashMap<Object, Object> dataMap = new ConcurrentHashMap<>();

    @Override
    public Object getObject(String key) {
        return check(key);
    }

    @Override
    public void putObject(String key, Object value) {
        dataMap.put(key, value);
    }

    @Override
    public boolean isExist(String key) {
        return check(key) != null;
    }

    @Override
    public void remove(String key) {
        dataMap.remove(key);
    }

    /**
     * 检查数据是否存在并且有效，存在有效则返回，无效删除切返回null
     * @param key
     * @return
     */
    private Object check(String key){
        RpcResponse rpcResponse =  (RpcResponse) dataMap.get(key);
        if(rpcResponse == null) {
            return null;
        }
        //当前数据已过期，删除数据,返回null
        if(System.currentTimeMillis() > rpcResponse.getResponseTime() + rpcServerProperties.getResponseExpiry()*1000){
            log.info("数据已过期,requestId="+rpcResponse.getRequestId());
            dataMap.remove(key);
            return null;
        }
        return rpcResponse;
    }
}
