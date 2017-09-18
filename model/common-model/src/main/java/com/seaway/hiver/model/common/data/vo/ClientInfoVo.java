package com.seaway.hiver.model.common.data.vo;

/**
 * 客户端信息
 * Created by Leo.Chang on 2016/12/13.
 */
public class ClientInfoVo {
	private String phoneSystem = "AN";
	private String clientType = "2";//1是pos，2是手机
	private String osversion;
	private String currentVersion;
	private String versionState;
	private String md5;

	public String getPhoneSystem() {
		return phoneSystem;
	}

	public void setPhoneSystem(String phoneSystem) {
		this.phoneSystem = phoneSystem;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getOsversion() {
		return osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getVersionState() {
		return versionState;
	}

	public void setVersionState(String versionState) {
		this.versionState = versionState;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
}
