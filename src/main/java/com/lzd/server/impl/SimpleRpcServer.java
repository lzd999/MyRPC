package com.lzd.server.impl;

import com.lzd.server.RpcServer;
import com.lzd.server.WorkThread;
import com.lzd.server.ServiceMapping;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class SimpleRpcServer implements RpcServer {
    private ServiceMapping serviceMap;

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("RPC 服务器成功启动，监听端口：" + port);
            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                // 连接成功后，启动一个线程处理客户端请求
                new Thread(new WorkThread(socket, serviceMap)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("RPC 服务器启动失败！", e);
        }
    }

    @Override
    public void stop() {

    }
}
