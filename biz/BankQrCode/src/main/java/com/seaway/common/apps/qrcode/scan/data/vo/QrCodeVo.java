package com.seaway.common.apps.qrcode.scan.data.vo;

import java.io.Serializable;

//import com.seaway.bank.common.data.vo.SysResVo;

public class QrCodeVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4116735396401993502L;
	
	private String callBack;
	private String barcode;

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
}