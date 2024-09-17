package com.lzd.netty.decoder;

import com.lzd.common.message.MessageType;
import com.lzd.netty.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author 李泽栋
 */
public class NettyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        // 读取消息类型
        int ordinal = in.readInt();
        if (ordinal >= MessageType.values().length || ordinal < 0) {
            throw new RuntimeException("不支持的消息类型！");
        }
        MessageType messageType = MessageType.values()[ordinal];

        // 读取消息的序列化方式
        int serializeCode = in.readInt();
        // 根据序列化方式获取序列化器
        Serializer serializer = Serializer.getSerializerTypeByCode(serializeCode);
        if (serializer == null) {
            throw new RuntimeException("不支持的序列化类型！");
        }
        // 读取消息体的长度
        int length = in.readInt();
        // 读取消息体
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        // 反序列化消息体
        Object object = serializer.deserialize(bytes, messageType);
        out.add(object);
    }
}
