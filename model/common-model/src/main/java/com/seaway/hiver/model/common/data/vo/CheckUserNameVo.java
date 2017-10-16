package com.seaway.hiver.model.common.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.seaway.android.sdk.tools.DeviceUtil;

/**
 * 登录返回参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class CheckUserNameVo extends BaseOutputVo{
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}