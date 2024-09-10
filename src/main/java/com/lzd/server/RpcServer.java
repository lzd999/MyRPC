package com.lzd.server;

/**
 * @author 李泽栋
 */
public interface RpcServer {
    // 开启监听
    void start(int port);

    // 停止监听
    void stop();
}
