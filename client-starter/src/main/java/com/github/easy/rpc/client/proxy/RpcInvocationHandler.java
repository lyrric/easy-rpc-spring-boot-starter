package com.github.easy.rpc.client.proxy;


import com.github.easy.rpc.client.service.NettyService;
import com.github.easy.rpc.client.SyncFutureMgr;
import com.github.easy.rpc.client.model.SyncResFuture;
import com.github.easy.rpc.common.model.RpcRequest;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created on 2018/10/16.
 * 动态代理，发送请求
 * @author wangxiaodong
 */
public class RpcInvocationHandler implements InvocationHandler{

    private SyncFutureMgr syncFutureMgr;
    private NettyService nettyService;

    public RpcInvocationHandler(SyncFutureMgr syncFutureMgr, NettyService nettyService) {
        this.syncFutureMgr = syncFutureMgr;
        this.nettyService = nettyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setRequestTime(System.currentTimeMillis());
        nettyService.sendAndFlush(rpcRequest);
        //保存请求
        SyncResFuture future = new SyncResFuture(rpcRequest);
        syncFutureMgr.put(rpcRequest.getRequestId(), future);
        //返回数据
        return future.getResponse().getData();
    }


}
