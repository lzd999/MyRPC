package com.lzd.server;

import com.lzd.common.service.UserService;
import com.lzd.common.service.impl.UserServiceImpl;
import com.lzd.server.impl.NettyRpcServer;
import org.junit.Test;

public class RpcServerTest {

    @Test
    public void testRpcServer() {
        UserService userService = new UserServiceImpl();

        ServiceMapping serviceMap = new ServiceMapping("127.0.0.1", 9999);
        serviceMap.addAndRegisterService(userService);

        RpcServer rpcServer = new NettyRpcServer(serviceMap);
        rpcServer.start(9999);
    }
}