package com.easy.rpc.client.proxy;


import com.easy.rpc.client.service.NettyService;
import com.easy.rpc.client.SyncFutureMgr;
import com.easy.rpc.client.model.SyncResFuture;
import com.easy.rpc.common.model.RpcRequest;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created on 2018/10/16.
 * 动态代理，发送请求
 * @author wangxiaodong
 */
public class RpcInvocationHandler implements InvocationHandler{

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
        System.out.println(rpcRequest);
        NettyService.sendAndFlush(rpcRequest);
        //保存请求
        SyncResFuture future = new SyncResFuture();
        SyncFutureMgr.put(rpcRequest.getRequestId(), future);
        //返回数据
        return future.getResponse().getData();
    }


}
