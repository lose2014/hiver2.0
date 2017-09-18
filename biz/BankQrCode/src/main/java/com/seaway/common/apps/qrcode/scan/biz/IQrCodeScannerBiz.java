package com.seaway.common.apps.qrcode.scan.biz;

import android.os.Handler;

public interface IQrCodeScannerBiz {
	/**
	 * 二维码结果回调
	 * 
	 * @param result
	 *            二维码扫描结果
	 * @param handler
	 *            回调句柄
	 */
	 void requestScanTwoDimension(String result, Handler handler);
}