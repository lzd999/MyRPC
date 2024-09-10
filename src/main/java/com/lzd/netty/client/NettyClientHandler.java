package com.lzd.netty.client;

import com.lzd.common.message.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author 李泽栋
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) {
        AttributeKey<Object> key = AttributeKey.valueOf("RpcResponse");
        channelHandlerContext.channel().attr(key).set(rpcResponse);
        channelHandlerContext.close();
    }
}
