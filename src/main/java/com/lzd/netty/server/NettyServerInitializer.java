package com.lzd.netty.server;

import com.lzd.netty.decoder.NettyDecoder;
import com.lzd.netty.encoder.NettyEncoder;
import com.lzd.netty.serializer.impl.JsonSerializer;
import com.lzd.server.ServiceMapping;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ServiceMapping serviceMap;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new NettyEncoder(new JsonSerializer()));
        // pipeline.addLast(new ObjectDecoder(Class::forName));
        pipeline.addLast(new NettyDecoder());
        pipeline.addLast(new NettyServerHandler(serviceMap));
    }
}
