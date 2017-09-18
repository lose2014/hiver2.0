package com.seaway.common.apps.qrcode.scan.data.param;

//import com.seaway.bank.common.data.param.SysReqParam;

/**
 * 二维码扫描结果回调参数
 * 
 * @author Leo.Chang
 * 
 */
public class RequestScanTwoDimensionParam
		//extends SysReqParam
{
	private String compactSign;

	public String getCompactSign() {
		return compactSign;
	}

	public void setCompactSign(String compactSign) {
		this.compactSign = compactSign;
	}
}