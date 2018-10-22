package com.github.easy.rpc.client.service;

import com.github.easy.rpc.client.handler.MessageHandler;
import com.github.easy.rpc.client.model.RpcClientProperties;
import com.github.easy.rpc.common.codec.MsgDecoder;
import com.github.easy.rpc.common.codec.MsgEncoder;
import com.github.easy.rpc.common.model.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.apachecommons.CommonsLog;


/**
 * Created on 2018/10/18.
 *
 * @author wangxiaodong
 */
@CommonsLog
public class NettyService {

    private static SocketChannel socketChannel;
    private MessageHandler messageHandler;

    public NettyService(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * 初始化
     * @param rpcClientProperties
     * @throws InterruptedException
     */
    public void init(RpcClientProperties rpcClientProperties) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel)  {
                        socketChannel.pipeline().addLast(new MsgEncoder());
                        socketChannel.pipeline().addLast(new MsgDecoder());
                        socketChannel.pipeline().addLast(messageHandler);
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = bootstrap.connect(rpcClientProperties.getServerHost(), rpcClientProperties.getServerPort()).sync();
        log.info(rpcClientProperties.getServerHost()+":"+rpcClientProperties.getServerPort()+"，连接已建立");
        socketChannel = (SocketChannel) future.channel();
    }

    /**
     * 发送请求
     * @param rpcRequest
     * @return
     */
    public void sendAndFlush(RpcRequest rpcRequest){
        log.info("发送消息||"+rpcRequest.getRequestId()+"||"+rpcRequest.getClassName()+"||"+rpcRequest.getMethodName());
        socketChannel.writeAndFlush(rpcRequest);
    }
}
