package com.seaway.hiver.model.login.data.param;


import com.seaway.hiver.model.common.data.param.BaseInputParam;

/**
 * 获取图形验证码请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class GetIconCodeParam extends BaseInputParam {
    private String businessType = "3000";

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
