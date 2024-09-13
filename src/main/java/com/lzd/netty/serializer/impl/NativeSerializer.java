package com.lzd.netty.serializer.impl;

import com.lzd.common.message.MessageType;
import com.lzd.netty.serializer.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author 李泽栋
 */
public class NativeSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        // 定义 byte[] 对象，用于存放序列化后的字节数组
        byte[] bytes = null;
        // 定义 ByteArrayOutputStream 对象，用于序列化对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 定义 ObjectOutputStream 对象，用于序列化对象
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 将对象序列化后写入 ByteArrayOutputStream
            oos.writeObject(object);
            // 刷新 ByteArrayOutputStream，确保所有的缓冲区的数据都被写入 ByteArrayOutputStream
            oos.flush();
            // 将序列化对象转换为字节数组
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception e) {
            throw new RuntimeException("使用 Java 内置序列化机制序列化失败！", e);
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, MessageType code) {
        // 定义 Object 对象，用于存放反序列化后的对象
        Object object = null;
        try {
            // 定义 ByteArrayInputStream 对象，用于接收序列化的字节数组
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            // 定义 ObjectInputStream 对象，用于将字节数组反序列化为对象
            ObjectInputStream ois = new ObjectInputStream(bis);
            // 读取反序列化后的对象
            object = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException("使用 Java 内置序列化机制反序列化失败！", e);
        }
        return object;
    }

    @Override
    public String getSerializerType() {
        return "Native";
    }

    @Override
    public int getSerializerCode() {
        return 0;
    }
}
