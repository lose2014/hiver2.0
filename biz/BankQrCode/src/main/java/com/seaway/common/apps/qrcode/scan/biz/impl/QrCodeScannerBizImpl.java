package com.seaway.common.apps.qrcode.scan.biz.impl;

import java.lang.ref.WeakReference;

import android.os.Handler;

import com.seaway.common.apps.qrcode.scan.biz.IQrCodeScannerBiz;
import com.seaway.common.apps.qrcode.scan.data.param.RequestScanTwoDimensionParam;
import com.seaway.common.apps.qrcode.scan.data.vo.RequestScanTwoDimensionVo;
//import com.seaway.bank.common.constant.Methods;
//import com.seaway.bank.common.fragment.MobileBankBaseFragment;
//import com.seaway.bank.common.net.MobileBankRequest;

public class QrCodeScannerBizImpl implements IQrCodeScannerBiz {
//	private WeakReference<MobileBankBaseFragment> mActivity;
//
//	public QrCodeScannerBizImpl(MobileBankBaseFragment activity) {
//		mActivity = new WeakReference<MobileBankBaseFragment>(activity);
//	}

	@Override
	public void requestScanTwoDimension(String result, Handler handler) {
//		if (hasReference()) {
//			RequestScanTwoDimensionParam param = new RequestScanTwoDimensionParam();
//			param.setCompactSign(result);
//
//			new MobileBankRequest().httpRequest(mActivity.get().getActivity(),
//					Methods.REQUEST_SCAN_TWO_DIMENSION, handler, param,
//					RequestScanTwoDimensionVo.class.getName());
//		}
	}

//	private boolean hasReference() {
//		return null != mActivity && null != mActivity.get();
//	}
}