package com.lzd.common.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 李泽栋
 */
@Data
@Builder
public class RpcResponse implements Serializable {
    private static final long serialVersionUID = 20240701L;

    // 状态码
    private int code;
    // 状态信息
    private String message;
    // 响应体
    private Object data;

    // 返回成功信息
    public static RpcResponse success(Object data) {
        return RpcResponse.builder()
                .code(200)
                .data(data)
                .build();
    }

    // 返回失败信息
    public static RpcResponse fail(String message) {
        return RpcResponse.builder()
                .code(500)
                .message(message)
                .build();
    }
}
