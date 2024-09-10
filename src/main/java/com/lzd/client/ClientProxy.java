package com.lzd.client;

import com.lzd.client.impl.NettyRpcClient;
import com.lzd.client.impl.SimpleRpcClient;
import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 李泽栋
 */
public class ClientProxy implements InvocationHandler {
    private RpcClient rpcClient;

    public ClientProxy(String host, Integer port, Integer mode) {
        if (mode == 0) {
            rpcClient = new SimpleRpcClient(host, port);
        } else if (mode == 1) {
            rpcClient = new NettyRpcClient();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 客户端使用本地代理封装调用信息
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes())
                .build();
        // 向服务器发送请求然后等待服务器响应
        RpcResponse response = rpcClient.sendRequest(rpcRequest);
        return response.getData();
    }

    public <T> T getProxyInstance(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
    }
}
