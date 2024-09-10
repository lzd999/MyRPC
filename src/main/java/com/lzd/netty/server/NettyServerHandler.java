package com.lzd.netty.server;

import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;
import com.lzd.server.ServiceMapping;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author 李泽栋
 */
@AllArgsConstructor
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private final ServiceMapping serviceMap;

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse rpcResponse = getResponse(rpcRequest);
        channelHandlerContext.writeAndFlush(rpcResponse);
        channelHandlerContext.close();
    }

    private RpcResponse getResponse(RpcRequest rpcRequest) {
        String serviceName = rpcRequest.getInterfaceName();
        Object service = serviceMap.getService(serviceName);
        Method method;
        try {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsType());
            Object result = method.invoke(service, rpcRequest.getParams());
            System.out.println("服务调用成功！");
            return RpcResponse.success(result);
        } catch (Exception e) {
            return RpcResponse.fail("服务调用失败！");
        }
    }
}
