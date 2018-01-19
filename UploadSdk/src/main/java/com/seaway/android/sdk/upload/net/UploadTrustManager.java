package com.seaway.android.sdk.upload.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class UploadTrustManager implements HostnameVerifier {

	@Override
	public boolean verify(String hostname, SSLSession session) {
		// TODO Auto-generated method stub
		return true;
	}
}
