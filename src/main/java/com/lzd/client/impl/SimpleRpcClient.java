package com.lzd.client.impl;

import com.lzd.client.RpcClient;
import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 李泽栋
 */
public class SimpleRpcClient implements RpcClient {
    private String host;
    private int port;

    public SimpleRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public RpcResponse sendRequest(RpcRequest request) throws Exception {
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            // 与服务器建立连接
            socket = new Socket(host, port);

            // 向服务器发送请求
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);
            oos.flush();

            // 接收服务器的执行结果
            ois = new ObjectInputStream(socket.getInputStream());
            return (RpcResponse) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("RPC 客户端发送请求错误！", e);
        } finally {
            if (socket != null) {
                socket.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
    }
}
