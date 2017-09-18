package com.seaway.hiver.model.common.data.vo;

/**
 * 获取短信验证码返回参数
 * Created by Leo.Chang on 2017/5/18.
 */
public class RequestSmsCodeVo extends BaseOutputVo {
    private String smsCodeId;
    private String mobileNo;

    public String getSmsCodeId() {
        return smsCodeId;
    }

    public void setSmsCodeId(String smsCodeId) {
        this.smsCodeId = smsCodeId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
