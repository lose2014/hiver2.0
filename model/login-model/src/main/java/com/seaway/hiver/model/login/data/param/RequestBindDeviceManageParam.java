package com.seaway.hiver.model.login.data.param;


import com.seaway.hiver.model.common.data.param.BaseInputParam;

/**
 * 绑定设备请求参数
 * Created by Leo.Chang on 2017/5/18.
 */
public class RequestBindDeviceManageParam extends BaseInputParam {
    private String manageType;
    private String id;
    private String nickName;
    private String codeId;
    private String code;

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
