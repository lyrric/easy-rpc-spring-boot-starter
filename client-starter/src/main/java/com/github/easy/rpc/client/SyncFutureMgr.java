package com.github.easy.rpc.client;

import com.github.easy.rpc.client.model.SyncResFuture;
import com.github.easy.rpc.common.model.RpcResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.env.Environment;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2018/10/16.
 *
 * @author wangxiaodong
 */
@CommonsLog
public class SyncFutureMgr {

    /**
     * 超时时间
     */
    private Integer requestTimeout = 30;
    private ConcurrentHashMap<String, SyncResFuture> dataMap = new ConcurrentHashMap<>();

    /**
     * 放入待响应的请求
     * @param requestId
     * @param resFuture
     */
    public void put(String requestId, SyncResFuture resFuture){
        dataMap.put(requestId, resFuture);
    }

    /**
     * 设置数据，尝试唤醒等待中的线程
     * @param rpcResponse
     */
    public void release(RpcResponse rpcResponse){
        String requestId = rpcResponse.getRequestId();
        if(requestId == null || "".equals(requestId)){ return;}
        SyncResFuture future = dataMap.get(requestId);
        if(future == null) {return;}
        future.setResponse(rpcResponse);
        dataMap.remove(requestId);
    }
    /**
     * 定时任务，设置任务超时
     * 每隔三十秒执行一次
     */
    public void scanData(){
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setSuccess(false);
        rpcResponse.setErrMsg("等待返回数据超时");
        dataMap.forEach((key,value)->{
            log.info("now="+ System.currentTimeMillis()+","+value.getRequestTime());
            if(System.currentTimeMillis() > value.getRequestTime() + requestTimeout *1000){
                log.debug("数据已过期,requestId=" + value.getRequestId());
                rpcResponse.setRequestId(key);
                release(rpcResponse);
            }
        });
    }

    public void setRequestTimeout(Environment environment) {
        String str = environment.getProperty("rpc.request-timeout");
        if("".equals(str)){
            requestTimeout = Integer.valueOf(str);
        }
    }
}
