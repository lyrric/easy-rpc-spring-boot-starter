package com.easy.rpc.client.proxy;


import com.easy.rpc.common.model.RpcRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * Created on 2018/10/16.
 * 动态代理，发送请求
 * @author wangxiaodong
 */
@Component
public class RpcInvocationHandler implements InvocationHandler {

//    @Resource
//    private SocketChannel socketChannel;


    public RpcInvocationHandler() {
        System.out.println("RpcInvocationHandler init");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String methodName = method.getName();
            if ("equals".equals(methodName)) {
                return proxy == args[0];
            } else if ("hashCode".equals(methodName)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(methodName)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setRequestTime(System.currentTimeMillis());
        System.out.println(rpcRequest);
//        NettyService.send(rpcRequest);
//        //保存请求
//        SyncResFuture future = new SyncResFuture();
//        SyncFutureMgr.put(rpcRequest.getRequestId(), future);
//        //返回数据
//        return future.getResponse().getData();
        return null;
    }
}
