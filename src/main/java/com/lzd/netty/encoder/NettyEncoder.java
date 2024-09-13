package com.lzd.netty.encoder;

import com.lzd.common.message.MessageType;
import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;
import com.lzd.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class NettyEncoder extends MessageToByteEncoder<Object> {
    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf out) throws Exception {
        System.out.println("当前消息类型为：" + message.getClass());
        // 写入消息类型
        if (message instanceof RpcRequest) {
            out.writeInt(MessageType.REQUEST.ordinal());
        } else if (message instanceof RpcResponse) {
            out.writeInt(MessageType.RESPONSE.ordinal());
        }
        // 写入序列化方式
        out.writeInt(serializer.getSerializerCode());

        byte[] bytes = serializer.serialize(message);
        // 写入序列化的字节数组长度
        out.writeInt(bytes.length);
        // 写入序列化的字节数组
        out.writeBytes(bytes);
    }
}
