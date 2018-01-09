package com.seaway.hiver.model.common.data.param;

public class RequestSmsCodeParam extends BaseInputParam {
    private String mobile;
    private String bizType;

    public RequestSmsCodeParam(String mobile, String bizType) {
        this.mobile = mobile;
        this.bizType = bizType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}