package com.seaway.hiver.model.login.data.param;


import com.seaway.hiver.model.common.data.param.BaseInputParam;

/**
 * 修改登录密码请求参数
 * Created by Leo.Chang on 2017/5/19.
 */
public class RequestLoginPwdModifyParam extends BaseInputParam {
    private String password;
    private String newPassword;
    private String passwordConfirm;


    public RequestLoginPwdModifyParam(String oldPwd, String newPwd,String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        this.password = oldPwd;
        this.newPassword = newPwd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
