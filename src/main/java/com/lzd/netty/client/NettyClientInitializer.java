package com.lzd.netty.client;

import com.lzd.netty.decoder.NettyDecoder;
import com.lzd.netty.encoder.NettyEncoder;
import com.lzd.netty.serializer.impl.JsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
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
        // pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new NettyEncoder(new JsonSerializer()));
        // 设置管道的解码器
        // pipeline.addLast(new ObjectDecoder(Class::forName));
        pipeline.addLast(new NettyDecoder());
        // 为管道添加自定义处理器
        pipeline.addLast(new NettyClientHandler());
    }
}
