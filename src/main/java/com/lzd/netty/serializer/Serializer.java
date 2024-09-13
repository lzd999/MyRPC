package com.lzd.netty.serializer;

import com.lzd.common.message.MessageType;
import com.lzd.netty.serializer.impl.JsonSerializer;
import com.lzd.netty.serializer.impl.NativeSerializer;

/**
 * @author 李泽栋
 */
public interface Serializer {
    // 将对象序列化为字节数组
    byte[] serialize(Object obj);

    // 将字节数组反序列化为对象
    Object deserialize(byte[] bytes, MessageType code);

    // 获取序列化器名称
    String getSerializerType();

    // 获取序列化器编码
    int getSerializerCode();

    // 根据编码获取序列化器
    static Serializer getSerializerTypeByCode(int code) {
        switch (code) {
            case 0:
                return new NativeSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
