package com.seaway.hiver.model.common.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leo.Chang on 2017/6/10.
 */

public class CheckResourceVo extends BaseOutputVo implements Parcelable {
    private String state;
    private String reason;
    private String loginFlag;
    private String minAccType;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getMinAccType() {
        return minAccType;
    }

    public void setMinAccType(String minAccType) {
        this.minAccType = minAccType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.state);
        dest.writeString(this.reason);
        dest.writeString(this.loginFlag);
        dest.writeString(this.minAccType);
    }

    public CheckResourceVo() {
    }

    protected CheckResourceVo(Parcel in) {
        this.state = in.readString();
        this.reason = in.readString();
        this.loginFlag = in.readString();
        this.minAccType = in.readString();
    }

    public static final Parcelable.Creator<CheckResourceVo> CREATOR = new Parcelable.Creator<CheckResourceVo>() {
        @Override
        public CheckResourceVo createFromParcel(Parcel source) {
            return new CheckResourceVo(source);
        }

        @Override
        public CheckResourceVo[] newArray(int size) {
            return new CheckResourceVo[size];
        }
    };
}
