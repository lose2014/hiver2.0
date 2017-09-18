package com.seaway.hiver.model.common.data.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.seaway.android.sdk.tools.DeviceUtil;

/**
 * 登录返回参数
 * Created by Leo.Chang on 2017/5/16.
 */
public class LoginVo extends BaseOutputVo implements Parcelable {
    private String accountId;
    private String mobileNumber;
    private String nickName;
    private String accountName;
    private String accountType;
    private String lastLoginTime;
    private String checkInfo;
    private String pwdState;
    private String deviceBindFlag;
    private String sessionId;
    private String credentialType;
    private String credentialNumber;
    private String credentialNumberEncrypt;
    private String accountNameEncrypt;
    private String mobileNumberEncrypt;
    private String pwdIdentify;
    private String pwdEndDate;
    private String uuid = DeviceUtil.getDeviceId();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getPwdState() {
        return pwdState;
    }

    public void setPwdState(String pwdState) {
        this.pwdState = pwdState;
    }

    public String getDeviceBindFlag() {
        return deviceBindFlag;
    }

    public void setDeviceBindFlag(String deviceBindFlag) {
        this.deviceBindFlag = deviceBindFlag;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getCredentialNumberEncrypt() {
        return credentialNumberEncrypt;
    }

    public void setCredentialNumberEncrypt(String credentialNumberEncrypt) {
        this.credentialNumberEncrypt = credentialNumberEncrypt;
    }

    public String getAccountNameEncrypt() {
        return accountNameEncrypt;
    }

    public void setAccountNameEncrypt(String accountNameEncrypt) {
        this.accountNameEncrypt = accountNameEncrypt;
    }

    public String getMobileNumberEncrypt() {
        return mobileNumberEncrypt;
    }

    public void setMobileNumberEncrypt(String mobileNumberEncrypt) {
        this.mobileNumberEncrypt = mobileNumberEncrypt;
    }

    public String getPwdIdentify() {
        return pwdIdentify;
    }

    public void setPwdIdentify(String pwdIdentify) {
        this.pwdIdentify = pwdIdentify;
    }

    public String getPwdEndDate() {
        return pwdEndDate;
    }

    public void setPwdEndDate(String pwdEndDate) {
        this.pwdEndDate = pwdEndDate;
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
        dest.writeString(this.accountId);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.nickName);
        dest.writeString(this.accountName);
        dest.writeString(this.accountType);
        dest.writeString(this.lastLoginTime);
        dest.writeString(this.checkInfo);
        dest.writeString(this.pwdState);
        dest.writeString(this.deviceBindFlag);
        dest.writeString(this.sessionId);
        dest.writeString(this.credentialType);
        dest.writeString(this.credentialNumber);
        dest.writeString(this.credentialNumberEncrypt);
        dest.writeString(this.accountNameEncrypt);
        dest.writeString(this.mobileNumberEncrypt);
        dest.writeString(this.pwdIdentify);
        dest.writeString(this.pwdEndDate);
        dest.writeString(this.uuid);
    }

    public LoginVo() {
    }

    protected LoginVo(Parcel in) {
        this.accountId = in.readString();
        this.mobileNumber = in.readString();
        this.nickName = in.readString();
        this.accountName = in.readString();
        this.accountType = in.readString();
        this.lastLoginTime = in.readString();
        this.checkInfo = in.readString();
        this.pwdState = in.readString();
        this.deviceBindFlag = in.readString();
        this.sessionId = in.readString();
        this.credentialType = in.readString();
        this.credentialNumber = in.readString();
        this.credentialNumberEncrypt = in.readString();
        this.accountNameEncrypt = in.readString();
        this.mobileNumberEncrypt = in.readString();
        this.pwdIdentify = in.readString();
        this.pwdEndDate = in.readString();
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