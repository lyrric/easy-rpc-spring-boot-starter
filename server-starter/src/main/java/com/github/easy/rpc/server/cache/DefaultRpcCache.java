package com.github.easy.rpc.server.cache;

import com.github.easy.rpc.common.model.RpcResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2018/10/19.
 * 默认缓存
 * @author wangxiaodong
 */
@CommonsLog
public class DefaultRpcCache implements RpcCache{

    private Integer expiry = null;

    private ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();

    @Override
    public Object getObject(String key) {
        return check(key);
    }

    @Override
    public void putObject(String key, Object value, int expiry) {
        if(this.expiry == null){
            this.expiry = expiry;
        }
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
     * 检查数据是否存在并且有效，存在有效则返回，无效删除且返回null
     * @param key
     * @return
     */
    private Object check(String key){
        RpcResponse rpcResponse =  (RpcResponse) dataMap.get(key);
        if(rpcResponse == null) {
            return null;
        }
        //当前数据已过期，删除数据,返回null
        if(System.currentTimeMillis() > rpcResponse.getResponseTime() +expiry*1000){
            log.info("数据已过期,requestId="+rpcResponse.getRequestId());
            dataMap.remove(key);
            return null;
        }
        return rpcResponse;
    }

    /**
     * 定时任务，设置任务超时，并清除dataMap中过时的数据
     * 每隔三十秒执行一次
     */
    @Scheduled(cron = "10/30 * * * * ? ")
    public void scanData(){
        dataMap.forEach((key,value)->{
            if(System.currentTimeMillis() > ((RpcResponse)value).getResponseTime() + expiry*1000){
                log.debug("缓存数据已过期,requestId=" + ((RpcResponse)value).getRequestId());
                dataMap.remove(key);
            }
        });
    }
}
