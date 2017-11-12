package com.seaway.hiver.model.common.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.seaway.android.sdk.tools.DeviceUtil;

/**
 * 登录返回参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class QATitleVo extends BaseOutputVo implements Parcelable {
    private int img;
    private String name;

    private String uuid = DeviceUtil.getDeviceId();


    public static Creator<QATitleVo> getCREATOR() {
        return CREATOR;
    }

    public String getUuid() {
        return DeviceUtil.getDeviceId();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.img);
        dest.writeString(this.name);
        dest.writeString(this.uuid);
    }

    public QATitleVo() {
    }

    protected QATitleVo(Parcel in) {
        this.img = in.readInt();
        this.name = in.readString();
        this.uuid = in.readString();
    }

    public static final Creator<QATitleVo> CREATOR = new Creator<QATitleVo>() {
        @Override
        public QATitleVo createFromParcel(Parcel source) {
            return new QATitleVo(source);
        }

        @Override
        public QATitleVo[] newArray(int size) {
            return new QATitleVo[size];
        }
    };
}