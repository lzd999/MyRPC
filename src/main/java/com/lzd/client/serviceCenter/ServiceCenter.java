package com.lzd.client.serviceCenter;

import java.net.InetSocketAddress;

/**
 * @author 李泽栋
 */
public interface ServiceCenter {
    // 根据服务名称获取服务的 IP 地址和端口号，以套接字的形式返回
    InetSocketAddress getServiceAddress(String serviceName);
}
