package com.lzd.server.serviceRegister.impl;

import com.lzd.server.serviceRegister.ServiceRegister;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;

/**
 * @author 李泽栋
 */
public class ZookeeperServiceRegister implements ServiceRegister {
    private static final String ROOT_PATH = "MyRPC";
    private final CuratorFramework zkClient;

    public ZookeeperServiceRegister() {
        // 创建 ZooKeeper 客户端并启动
        zkClient = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(ROOT_PATH)
                .build();
        zkClient.start();
        System.out.println("ZooKeeper 客户端启动成功！");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serviceAddress) {
        try {
            // 如果服务名不存在，则创建
            if (zkClient.checkExists().forPath("/" + serviceName) == null) {
                zkClient.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath("/" + serviceName);
            }
            String path = "/" + serviceName + "/" + serviceAddress.getHostString() + ":" + serviceAddress.getPort();
            // 创建服务地址节点，用于存放服务地址
            zkClient.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
            System.out.println("服务注册成功！");
        } catch (Exception e) {
            throw new RuntimeException("服务注册失败！", e);
        }
    }
}
