package com.lzd.server;

import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;
import lombok.AllArgsConstructor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class WorkThread implements Runnable {
    private Socket socket;

    private ServiceMapping serviceMapping;

    @Override
    public void run() {
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            // 读取客户端请求
            ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest clientRequest = (RpcRequest) ois.readObject();
            // 服务器根据客户端请求调用对应的服务
            RpcResponse serverResponse = getRpcResponse(clientRequest);
            // 向客户端返回服务端响应
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(serverResponse);
            oos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private RpcResponse getRpcResponse(RpcRequest clientRequest) {
        // 从客户端请求解析需调用的方法名、参数类型和参数名
        String methodName = clientRequest.getMethodName();
        Class[] parameterTypes = clientRequest.getParamsType();
        Object[] parameters = clientRequest.getParams();
        // 服务器根据客户端请求调用对应的服务
        // 这里需要根据客户端请求的接口名和方法名，从服务端的服务提供者中获取服务实现类，并反射调用方法
        Object service = serviceMapping.getService(clientRequest.getInterfaceName());
        Method method;
        try {
            method = service.getClass().getMethod(methodName, parameterTypes);
            Object invoke = method.invoke(service, parameters);
            System.out.println("方法执行成功！");
            return RpcResponse.success(invoke);
        } catch (Exception e) {
            return RpcResponse.fail("方法执行失败！");
        }
    }
}
