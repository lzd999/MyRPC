package com.lzd.client.serviceCenter.impl;

import com.lzd.client.serviceCenter.ServiceCenter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author 李泽栋
 */
public class ZookeeperServiceCenter implements ServiceCenter {
    private final CuratorFramework zkClient;
    private static final String ROOT_PATH = "MyRPC";

    public ZookeeperServiceCenter() {
        this.zkClient = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace(ROOT_PATH)
                .build();
        this.zkClient.start();
    }

    @Override
    public InetSocketAddress getServiceAddress(String serviceName) {
        try {
            List<String> services = zkClient.getChildren().forPath("/" + serviceName);
            String service = services.get(0);
            String[] ss = service.split(":");
            return new InetSocketAddress(ss[0], Integer.parseInt(ss[1]));
        } catch (Exception e) {
            throw new RuntimeException("获取服务地址失败！", e);
        }
    }
}
