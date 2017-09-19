package com.seaway.hiver.model.main.teacher.data.param;

import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.data.param.BaseInputParam;


/**
 * 登录请求参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class LoginParam extends BaseInputParam {
    private String userName;
    private String loginPasswd;
    private String codeId;
    private String code;
    private String loginMode;
    private String deviceNo = DeviceUtil.getDeviceId();

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

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(String loginMode) {
        this.loginMode = loginMode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
