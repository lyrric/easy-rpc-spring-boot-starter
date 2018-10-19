package com.easy.rpc.server.handler;

import com.easy.rpc.common.model.RpcRequest;
import com.easy.rpc.common.model.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * Created on 2018/10/13.
 *
 * @author wangxiaodong
 */
@ChannelHandler.Sharable
@CommonsLog
@Component
public class MessageHandler extends ChannelInboundHandlerAdapter {

    @Resource
    private WebApplicationContext applicationContext;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info(ctx.channel().remoteAddress()+", 连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        RpcRequest rpcRequest = (RpcRequest)msg;
        ctx.channel().writeAndFlush(handle(rpcRequest));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        log.info("一个连接关闭了");
    }

    /**
     * 异步调用并返回数据
     * @param rpcRequest
     */
    private RpcResponse handle(RpcRequest rpcRequest){
        String clazzName = rpcRequest.getClassName();
        String methodName =rpcRequest.getMethodName();
        RpcResponse rpcResponse = new RpcResponse();
        try {
            //获取对应类的实例
            Object serviceBean = getBean(clazzName);
            //反射，执行方法
            FastClass fastClass = FastClass.create(serviceBean.getClass());
            FastMethod fastMethod = fastClass.getMethod(methodName, rpcRequest.getParameterTypes());
            Object object = fastMethod.invoke(serviceBean, rpcRequest.getParameters());
            //封装返回数据
            rpcResponse.setData(object);
            rpcResponse.setRequestId(rpcRequest.getRequestId());
            rpcResponse.setSuccess(true);
        } catch (InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            rpcResponse.setSuccess(false);
            rpcResponse.setErrMsg("调用方法失败");
        }
        return rpcResponse;
    }

    /**
     * 获取实例
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private Object getBean(String className) throws ClassNotFoundException {
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        return applicationContext.getBean(clazz);
    }
}
