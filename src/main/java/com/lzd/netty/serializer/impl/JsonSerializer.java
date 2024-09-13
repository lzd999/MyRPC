package com.lzd.netty.serializer.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.lzd.common.message.MessageType;
import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;
import com.lzd.netty.serializer.Serializer;

/**
 * @author 李泽栋
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = JSONObject.toJSONBytes(object, JSONReader.Feature.SupportClassForName.ordinal());
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, MessageType code) {
        Object object = null;
        switch (code) {
            case REQUEST:
                RpcRequest request = JSON.parseObject(bytes, RpcRequest.class, JSONReader.Feature.SupportClassForName);
                Object[] objects = new Object[request.getParams().length];
                for (int i = 0; i < objects.length; i++) {
                    Class<?> clazz = request.getParamsType()[i];
                    if (!clazz.isAssignableFrom(request.getParams()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getParams()[i], clazz);
                    } else {
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                object = request;
                break;
            case RESPONSE:
                RpcResponse response = JSON.parseObject(bytes, RpcResponse.class);
                Class<?> dataType = response.getDataType();
                if (!dataType.isAssignableFrom(response.getData().getClass())) {
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(), dataType));
                }
                object = response;
                break;
            default:
                System.out.println("不支持的消息类型！");
                break;
        }
        return object;
    }

    @Override
    public String getSerializerType() {
        return "Json";
    }

    @Override
    public int getSerializerCode() {
        return 1;
    }
}
