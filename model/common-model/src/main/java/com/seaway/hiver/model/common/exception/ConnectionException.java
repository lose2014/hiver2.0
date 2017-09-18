package com.seaway.hiver.model.common.exception;

/**
 * Created by Leo.Chang on 2017/5/5.
 */
public class ConnectionException extends Exception {
    /**
     * 错误码
     */
    public String code;
    /**
     * 错误原因
     */
    public String message;

    public ConnectionException(Throwable t , String code) {
        super(t);
        this.code = code;
    }
}