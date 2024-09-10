package com.lzd.client;

import com.lzd.common.message.RpcRequest;
import com.lzd.common.message.RpcResponse;

/**
 * @author 李泽栋
 */
public interface RpcClient {
    RpcResponse sendRequest(RpcRequest request) throws Exception;
}
