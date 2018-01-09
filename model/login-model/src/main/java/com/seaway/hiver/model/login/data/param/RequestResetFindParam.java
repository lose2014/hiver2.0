package com.seaway.hiver.model.login.data.param;


import com.seaway.hiver.model.common.data.param.BaseInputParam;

/**
 * 忘记登录密码请求参数
 * Created by Leo.Chang on 2017/5/18.
 */
public class RequestResetFindParam extends BaseInputParam {
    private String mobile;
    private String captcha;
    private String newPassword;
    private String passwordConfirm;

    public RequestResetFindParam(String mobile, String captcha, String newPwd, String newPwdConfirm) {
        this.captcha = captcha;
        this.mobile = mobile;
        this.newPassword = newPwd;
        this.passwordConfirm = newPwdConfirm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
