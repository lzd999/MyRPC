package com.lzd.server.impl;

import com.lzd.server.RpcServer;
import com.lzd.netty.server.NettyServerInitializer;
import com.lzd.server.ServiceMapping;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class NettyRpcServer implements RpcServer {
    private ServiceMapping serviceMap;

    @Override
    public void start(int port) {
        // 定义 netty 服务线程组，用于处理客户端的连接
        // masterGroup 负责与客户端建立连接，workerGroup 负责处理客户端的具体请求
        NioEventLoopGroup masterGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动 netty 服务器并初始化
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(masterGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceMap));
            // 绑定端口，开始监听客户端连接
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 持续监听，直到服务器关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭线程组
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
