package com.easy.rpc.client.service;

import com.easy.rpc.client.handler.MessageHandler;
import com.easy.rpc.client.model.RpcClientProperties;
import com.easy.rpc.common.codec.MsgDecoder;
import com.easy.rpc.common.codec.MsgEncoder;
import com.easy.rpc.common.model.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.apachecommons.CommonsLog;

import java.util.Properties;


/**
 * Created on 2018/10/18.
 *
 * @author wangxiaodong
 */
@CommonsLog
public class NettyService {

    private static SocketChannel socketChannel;

    /**
     * 初始化
     * @param rpcClientProperties
     * @throws InterruptedException
     */
    public static void init(RpcClientProperties rpcClientProperties) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel)  {
                        socketChannel.pipeline().addLast(new MsgEncoder());
                        socketChannel.pipeline().addLast(new MsgDecoder());
                        socketChannel.pipeline().addLast(new MessageHandler());
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
    public static void sendAndFlush(RpcRequest rpcRequest){
        socketChannel.writeAndFlush(rpcRequest);
    }
}
