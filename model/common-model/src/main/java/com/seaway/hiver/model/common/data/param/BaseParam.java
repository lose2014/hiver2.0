package com.seaway.hiver.model.common.data.param;

/**
 * 基础请求类
 * Created by Leo.Chang on 2017/5/5.
 */
public class BaseParam<T> {
    /**
     * 签名/加密方式
     * 0:不验证（默认）
     * 1:MD5
     * 10:RSA
     * 20:3DES + RSA
     */
    private String signType = "20";
    /**
     * 签名
     * 如果signType=1，存放签名串；<br>如果signType=10，为空<br>如果signType=20，存放RSA加密后的3DES密钥
     */
    private String sign;
    /**
     * 业务参数
     * 如果signType=10，存放加密后文本<br>如果signType=20，存放3DES加密后文本<br>其他情况存放明文参数
     */
    private T input;

    /**
     * 第三方渠道编号
     * 适用于第三方渠道接入场景，不同的渠道有不同的验签/解密密钥，通过本字段值获取验签/解密密钥
     */
    private String domainId;

    public BaseParam(T input) {
        this.input = input;
    }

    public BaseParam(String sign, T input) {
        this.sign = sign;
        this.input = input;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getInput() {
        return input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }
}