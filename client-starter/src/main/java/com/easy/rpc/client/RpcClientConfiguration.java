package com.easy.rpc.client;

import com.easy.rpc.client.handler.MessageHandler;
import com.easy.rpc.client.model.RpcClientProperties;
import com.easy.rpc.common.codec.MsgDecoder;
import com.easy.rpc.common.codec.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2018/10/17.
 * 配置类
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
@ComponentScan(basePackages = "com.easy.rpc.client")
@CommonsLog
public class RpcClientConfiguration {

/*    *//**
     * 建立连接，保存sockChannel
     * @param properties
     * @return
     * @throws InterruptedException
     *//*
    @Bean(name = "socketChannel")
    @Autowired
    public SocketChannel initNetty(RpcClientProperties properties) throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel)  {
                        socketChannel.pipeline().addLast(new MsgEncoder());
                        socketChannel.pipeline().addLast(new MsgDecoder());
                        socketChannel.pipeline().addLast(new MessageHandler());
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture future = bootstrap.connect(properties.getServerHost(), properties.getServerPort()).sync();
        log.info(properties.getServerHost()+":"+properties.getServerPort()+"，连接已建立");
        return (SocketChannel) future.channel();
    }*/

}
