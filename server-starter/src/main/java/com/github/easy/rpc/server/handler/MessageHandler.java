package com.github.easy.rpc.server.handler;

import com.github.easy.rpc.common.model.RpcRequest;
import com.github.easy.rpc.common.model.RpcResponse;
import com.github.easy.rpc.server.annotation.RpcService;
import com.github.easy.rpc.server.model.RpcServerProperties;
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
    @Resource
    private RpcServerProperties rpcServerProperties;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info(ctx.channel().remoteAddress()+", 连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        RpcRequest rpcRequest = (RpcRequest)msg;
        RpcResponse rpcResponse = handle(rpcRequest);
        ctx.channel().writeAndFlush(rpcResponse);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        log.info("一个连接关闭了");
    }

    /**
     * 异步调用并返回数据
     * @param rpcRequest
     */
    private RpcResponse handle(RpcRequest rpcRequest){
        //请求时间据现在已经超过了有效期，返回null
        if(System.currentTimeMillis() > rpcRequest.getRequestTime()+rpcServerProperties.getRequestTimeout()*1000){
            return new RpcResponse(rpcRequest.getRequestId(), false, "请求超时", null);
        }
        String clazzName = rpcRequest.getClassName();
        String methodName =rpcRequest.getMethodName();
        try {
            //获取对应类的实例
            Object serviceBean = getBean(clazzName);
            if(serviceBean == null ){
                return new RpcResponse(rpcRequest.getRequestId(), false, "服务不存在", null);
            }
            //反射，执行方法
            FastClass fastClass = FastClass.create(serviceBean.getClass());
            FastMethod fastMethod = fastClass.getMethod(methodName, rpcRequest.getParameterTypes());
            Object object = fastMethod.invoke(serviceBean, rpcRequest.getParameters());
            //封装返回数据
            return new RpcResponse(rpcRequest.getRequestId(), true, null, object);
        } catch (InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            return new RpcResponse(rpcRequest.getRequestId(), false, e.getMessage(), null);
        }
    }

    /**
     * 获取实例,并判断是否带有@RpcService注解
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private Object getBean(String className) throws ClassNotFoundException {
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        Object object = applicationContext.getBean(clazz);
        return object.getClass().isAnnotationPresent(RpcService.class)?object:null;
    }
}
