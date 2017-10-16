package com.seaway.hiver.model.login.data.param;

import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 登录请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class LoginParam extends BaseInputParam {
    private String userName;
    private String loginPasswd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

}
