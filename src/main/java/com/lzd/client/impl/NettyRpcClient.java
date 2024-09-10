package com.lzd.client.impl;

import com.lzd.client.RpcClient;
import com.lzd.client.netty.NettyClientInitializer;
import com.lzd.client.serviceCenter.ServiceCenter;
import com.lzd.client.serviceCenter.impl.ZookeeperServiceCenter;
import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author 李泽栋
 */
public class NettyRpcClient implements RpcClient {

    private final ServiceCenter serviceCenter;

    public NettyRpcClient() {
        this.serviceCenter = new ZookeeperServiceCenter();
    }

    private static EventLoopGroup group;
    private static Bootstrap bootstrap;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        InetSocketAddress serviceAddress = serviceCenter.getServiceAddress(request.getInterfaceName());
        String host = serviceAddress.getHostString();
        int port = serviceAddress.getPort();
        try {
            // 向服务器发起连接请求并等待服务器响应
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 向服务器发送请求数据后等待服务器响应
            channel.writeAndFlush(request);
            channel.closeFuture().sync();
            // 读取服务器响应数据
            RpcResponse response = (RpcResponse) channel.attr(AttributeKey.valueOf("RpcResponse")).get();
            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException("Netty 客户端等待服务器响应时发生异常", e);
        }
    }
}
