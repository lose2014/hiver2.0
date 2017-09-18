package com.seaway.bank.model.vc.util;

import android.text.TextUtils;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.rx.retrofit.BaseRetrofit;
import com.seaway.bank.model.vc.DownloadProgressListener;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leo.Chang on 2017/6/10.
 */
public class DownloadRetrofitClient {
    private DownloadProgressListener downloadProgressListener;
    private Retrofit retrofit;

    public DownloadRetrofitClient(String url,DownloadProgressListener downloadProgressListener) {
        Logger.i("DownloadRetrofitClient -> url is : " + url);
        this.downloadProgressListener = downloadProgressListener;
        this.retrofit = createRetrofit("https://terminal.4008889112.cn:18087/");
    }

    Retrofit createRetrofit(String url) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .client(createOkHttpClient())
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.build();
    }

    /**
     * 创建OKHttpClient
     *
     * @return OkHttpClient
     */
    OkHttpClient createOkHttpClient() {
        final X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SSLContext sslContext = null;
        SSLSocketFactory sslSocketFactory=null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置连接超时时间
        builder.connectTimeout(30, TimeUnit.SECONDS);
        // 设置读取超时时间
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(new DownloadProgressInterceptor(downloadProgressListener));
//        builder.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });

        return builder.build();
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}
