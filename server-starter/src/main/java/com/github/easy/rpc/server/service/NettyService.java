package com.github.easy.rpc.server.service;

import com.github.easy.rpc.common.codec.MsgDecoder;
import com.github.easy.rpc.common.codec.MsgEncoder;
import com.github.easy.rpc.server.handler.MessageHandler;
import com.github.easy.rpc.server.model.RpcServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created on 2018/10/19.
 *
 * @author wangxiaodong
 */
@Component
@CommonsLog
public class NettyService implements InitializingBean {

    @Resource
    private RpcServerProperties rpcServerProperties;
    @Resource
    private MessageHandler messageHandler;
    @Override
    public void afterPropertiesSet() throws Exception {
        startNetty();
    }

    /**
     * 初始化netty
     */
    private void startNetty(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MsgEncoder());
                            ch.pipeline().addLast(new MsgDecoder());
                            ch.pipeline().addLast(messageHandler);
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(rpcServerProperties.getPort()).sync();
            log.info("启动netty成功||端口="+rpcServerProperties.getPort());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
