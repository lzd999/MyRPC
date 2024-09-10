package com.lzd.server;

import com.lzd.server.serviceRegister.ServiceRegister;
import com.lzd.server.serviceRegister.impl.ZookeeperServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李泽栋
 */
public class ServiceMapping {
    private final String host;
    private final Integer port;
    // 服务映射表
    private final Map<String, Object> serviceMap;
    // 使用 Zookeeper 作为服务注册中心
    private final ServiceRegister serviceRegister;

    public ServiceMapping(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.serviceMap = new HashMap<>();
        this.serviceRegister = new ZookeeperServiceRegister();
    }

    // 使用本地提供服务注册
    public void addAndRegisterService(Object service) {
        // 获取服务名
        String serviceName = service.getClass().getName();
        // 获取服务调用接口
        Class<?>[] interfaceNames = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaceNames) {
            // 创建映射关系
            serviceMap.put(clazz.getName(), service);
            // 注册服务
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    // 返回服务
    public Object getService(String interfaceName) {
        return serviceMap.get(interfaceName);
    }
}
