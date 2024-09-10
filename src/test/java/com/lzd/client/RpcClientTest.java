package com.lzd.client;

import com.lzd.common.pojo.User;
import com.lzd.common.service.UserService;
import org.junit.Test;

public class RpcClientTest {
    @Test
    public void testRpcClient() {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 9999, 1);
        UserService proxy = clientProxy.getProxyInstance(UserService.class);

        User user1 = new User();
        user1.setId(819);
        user1.setName("lzd");
        user1.setAge(25);
        user1.setGender("男");
        Boolean result = proxy.insertUser(user1);
        System.out.println("向服务器插入用户，结果：\n" + result);

        User user2 = proxy.getUserById(819);
        System.out.println("从服务器获取用户，用户信息：\n" + user2);
    }


}