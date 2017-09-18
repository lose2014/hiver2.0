package com.seaway.hiver.model.common.exception;

/**
 * 服务端请求错误异常
 * Created by Leo.Chang on 2017/5/5.
 */
public class ServerResponseException extends RuntimeException {
    public String code;
//    public String message;

    public ServerResponseException(String code, String message) {
        super(message);
        this.code = code;
    }
}
