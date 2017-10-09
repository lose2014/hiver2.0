package com.seaway.hiver.model.login.data.param;


import com.seaway.hiver.model.common.data.param.BaseInputParam;

/**
 * 修改登录密码请求参数
 * Created by Leo.Chang on 2017/5/19.
 */
public class RequestLoginPwdModifyParam extends BaseInputParam {
    private String credentialType;
    private String credentialNumber;
    private String oldPwd;
    private String newPwd;

    public RequestLoginPwdModifyParam(String oldPwd, String newPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public RequestLoginPwdModifyParam(String credentialType, String credentialNumber, String oldPwd, String newPwd) {
        this.credentialType = credentialType;
        this.credentialNumber = credentialNumber;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialNumber() {
        return credentialNumber;
    }

    public void setCredentialNumber(String credentialNumber) {
        this.credentialNumber = credentialNumber;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
