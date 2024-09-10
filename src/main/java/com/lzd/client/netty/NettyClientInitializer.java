package com.lzd.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author 李泽栋
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 定义管道对象
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 设置管道的编码器
        pipeline.addLast(new ObjectEncoder());
        // 设置管道的解码器
        pipeline.addLast(new ObjectDecoder(Class::forName));
        // 处理入站数据时，使用基于长度字段的帧解码器
        // pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        // 处理出站数据时，使用 4 个字节标识后续消息长度
        // pipeline.addLast(new LengthFieldPrepender(4));
        // 为管道添加自定义处理器
        pipeline.addLast(new NettyClientHandler());
    }
}
