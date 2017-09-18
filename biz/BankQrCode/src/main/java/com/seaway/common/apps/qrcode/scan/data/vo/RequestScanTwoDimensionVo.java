package com.seaway.common.apps.qrcode.scan.data.vo;

import java.io.Serializable;

//import com.seaway.bank.common.data.vo.SysResVo;

public class RequestScanTwoDimensionVo
		//extends SysResVo
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4116735396401993502L;
	
	private String codeType;
	private String twoCodeId;
	private String pathUrl;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getTwoCodeId() {
		return twoCodeId;
	}

	public void setTwoCodeId(String twoCodeId) {
		this.twoCodeId = twoCodeId;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
}