package com.lzd.server.serviceRegister;

import java.net.InetSocketAddress;

/**
 * @author 李泽栋
 */
public interface ServiceRegister {
    // 服务注册
    void register(String serviceName, InetSocketAddress serviceAddress);
}
