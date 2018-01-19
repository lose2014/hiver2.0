package com.seaway.hiver.model.common.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.seaway.android.sdk.tools.DeviceUtil;

/**
 * 登录返回参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class LoginVo extends BaseOutputVo implements Parcelable {
    private Long teacherId;
    private String mobile;
    private String accessToken;
    private String nickname;
    private String realname;
    private String headimgPath;

    private String uuid = DeviceUtil.getDeviceId();

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHeadimgPath() {
        return headimgPath;
    }

    public void setHeadimgPath(String headimgPath) {
        this.headimgPath = headimgPath;
    }

    public static Creator<LoginVo> getCREATOR() {
        return CREATOR;
    }

    public String getUuid() {
        return DeviceUtil.getDeviceId();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.teacherId);
        dest.writeString(this.mobile);
        dest.writeString(this.accessToken);
        dest.writeString(this.nickname);
        dest.writeString(this.realname);
        dest.writeString(this.headimgPath);
        dest.writeString(this.uuid);
    }

    public LoginVo() {
    }

    protected LoginVo(Parcel in) {
        this.teacherId = in.readLong();
        this.mobile = in.readString();
        this.accessToken = in.readString();
        this.nickname = in.readString();
        this.realname = in.readString();
        this.headimgPath = in.readString();
        this.uuid = in.readString();
    }

    public static final Creator<LoginVo> CREATOR = new Creator<LoginVo>() {
        @Override
        public LoginVo createFromParcel(Parcel source) {
            return new LoginVo(source);
        }

        @Override
        public LoginVo[] newArray(int size) {
            return new LoginVo[size];
        }
    };
}