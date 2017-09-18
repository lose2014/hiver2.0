package com.seaway.hiver.model.common.ssl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by Leo.Chang on 2016/8/19.
 */
public class SocketFactory {

	public static SSLSocketFactory getSSLSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());

		return sc.getSocketFactory();
	}
}
