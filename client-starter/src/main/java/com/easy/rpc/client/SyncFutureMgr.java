package com.easy.rpc.client;

import com.easy.rpc.client.model.SyncResFuture;
import com.easy.rpc.common.model.RpcResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 2018/10/16.
 *
 * @author wangxiaodong
 */
public class SyncFutureMgr {

    private static Map<String, SyncResFuture> dataMap = new TreeMap<>();

    /**
     * 放入待响应的请求
     * @param requestId
     * @param resFuture
     */
    public static void put(String requestId, SyncResFuture resFuture){
        dataMap.put(requestId, resFuture);
    }

    /**
     * 设置数据，尝试唤醒等待中的线程
     * @param rpcResponse
     */
    public static void release(RpcResponse rpcResponse){
        String requestId = rpcResponse.getRequestId();
        if(requestId == null || "".equals(requestId)) return;
        SyncResFuture future = dataMap.get(requestId);
        if(future == null) return;
        future.setResponse(rpcResponse);
        dataMap.remove(requestId);
    }
}
