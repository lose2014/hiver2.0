package com.seaway.hiver.model.login.data.vo;


import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 * 获取图形验证码
 * Created by Leo.Chang on 2017/5/16.
 */
public class RequestResetPwdVo extends BaseOutputVo {
    private String codeId;
    private String codeImgStr;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeImgStr() {
        return codeImgStr;
    }

    public void setCodeImgStr(String codeImgStr) {
        this.codeImgStr = codeImgStr;
    }
}
