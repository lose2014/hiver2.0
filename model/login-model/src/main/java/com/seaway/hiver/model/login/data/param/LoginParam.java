package com.seaway.hiver.model.login.data.param;

import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 登录请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class LoginParam extends BaseInputParam {
    private String username;
    private String password;
//    private String captcha="1111";
//    private String mobile="15168380370";
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getCaptcha() {
//        return captcha;
//    }
//
//    public void setCaptcha(String captcha) {
//        this.captcha = captcha;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
