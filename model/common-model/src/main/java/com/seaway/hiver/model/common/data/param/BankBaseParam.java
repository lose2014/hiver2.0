package com.seaway.hiver.model.common.data.param;

/**
 * Created by Leo.Chang on 2017/5/16.
 */
public class BankBaseParam<T> extends BaseParam {
    private String method;

    public BankBaseParam(T input) {
        super(input);
    }

    public BankBaseParam(String method, T input) {
        super(input);
        this.method = method;
    }

    public BankBaseParam(String method, String[] params) {
        super(params[0], params[1]);
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}