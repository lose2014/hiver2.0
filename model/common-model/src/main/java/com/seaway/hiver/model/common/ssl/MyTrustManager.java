package com.seaway.hiver.model.common.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by Leo.Chang on 2016/8/19.
 */
public class MyTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
//			if (SWVerificationUtil.isEmpty(certificateName)) {
//				return;
//			}
//			Certificate cer = null;
//			InputStream ins;
//			try {
//				// ins = context.getAssets().open(certificateName);
//				ins = SWApplication.getInstance().getApplicationContext()
//						.getAssets().open(certificateName);
//				CertificateFactory cerFactory = CertificateFactory
//						.getInstance("X.509");
//				cer = cerFactory.generateCertificate(ins);
//				ins.close();
//				ins = null;
//
//				for (int i = 0; i < chain.length; i++) {
//					Certificate certificate = chain[i];
//					if (cer.equals(certificate)) {
//						return;
//					}
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			// for (int i = 0; i < chain.length; i++) {
//			// Certificate certificate = chain[i];
//			// if (cer.equals(certificate)) {
//			// return;
//			// }
//			// }
//			throw new CertificateException();
		return;
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return new X509Certificate[]{};
	}
}
