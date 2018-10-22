package com.github.easy.rpc.client.handler;

import com.github.easy.rpc.client.SyncFutureMgr;
import com.github.easy.rpc.common.model.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created on 2018/10/13.
 *
 * @author wangxiaodong
 */
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private SyncFutureMgr syncFutureMgr;

    public MessageHandler(SyncFutureMgr syncFutureMgr) {
        this.syncFutureMgr = syncFutureMgr;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        RpcResponse rpcResponse = (RpcResponse)msg;
        syncFutureMgr.release(rpcResponse);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }


}
