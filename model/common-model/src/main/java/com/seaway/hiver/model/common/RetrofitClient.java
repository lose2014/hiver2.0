package com.seaway.hiver.model.common;

import android.provider.Settings;

import com.seaway.android.sdk.SWApplication;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.rx.retrofit.BaseRetrofit;
import com.seaway.android.sdk.rx.retrofit.logger.HttpLoggingInterceptor;
import com.seaway.android.sdk.toolkit.SWVerificationUtil;
import com.seaway.android.sdk.tools.AndroidUtil;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.model.common.shared.MSharedPreferences;
import com.seaway.hiver.model.common.ssl.MyTrustManager;
import com.seaway.hiver.model.common.ssl.SocketFactory;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公用请求封装类
 * Created by Leo.Chang on 2017/5/9.
 */
public class RetrofitClient extends BaseRetrofit {
    private volatile static RetrofitClient client;

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (null == client) {
            synchronized (RetrofitClient.class) {
                if (null == client) {
                    client = new RetrofitClient();
                }
            }
        }
//
//        synchronized (RetrofitClient.class) {
//            if (null == client) {
//                client = new RetrofitClient();
//            }
//        }

        return client;
    }

    @Override
    protected String getBaseUrl() {
//        return "http://ebao.seaway.net.cn:18600/phoneVersions/";//银客多测试地址
//        if(SWVerificationUtil.isEmpty(Util.readFromPath())||"https://www.4008889112.cn:18087".equals(Util.readFromPath())){
//            return "https://www.4008889112.cn:18087/phoneVersions/";//正式地址
            return NetUtil.web_view_path+"/phoneVersions/";
////            return "http://ebao.seaway.net.cn:18600/quickeasy-terminal/phoneVersions/";
//        }else{
//            return  Util.readFromPath()+"/phoneVersions/";//存储地址
//        }
//        return "https://www.4008889112.com:18088/test/";//正式地址
//        return "http://ebao.seaway.net.cn:18600/quickeasy-terminal/phoneVersions/";

    }

    @Override
    protected Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Logger.i("初始化Http Header");
                int[] screen = DeviceUtil.getScreenInfo();
                String deviceId=DeviceUtil.getDeviceId();
                if(SWVerificationUtil.isEmpty(deviceId)){
                    //android.provider.Settings
                    deviceId =Settings.Secure.getString(SWApplication.getInstance().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                }
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        // 设备生产厂商
                        .addHeader("deviceMfrs", DeviceUtil.getDeviceManufacturer())
                        // 设备型号
                        .addHeader("deviceModel", DeviceUtil.getDeviceMod())
                        .addHeader("marketId", "")
                        // 运营商
                        .addHeader("provider", DeviceUtil.getProvideName())
                        // 屏幕高度
                        .addHeader("height", String.valueOf(screen[1]))
                        // 屏幕宽度
                        .addHeader("width", String.valueOf(screen[0]))
                        // 操作系统
                        .addHeader("osName", "A")
                        // 操作系统版本
                        .addHeader("osVersion", DeviceUtil.getOSVersion())
                        // 客户端版本号
                        .addHeader("clientVersion", AndroidUtil.getVersionName())
                        // 客户端类型
                        .addHeader("clientType", "1")
                        // 版本类型
                        .addHeader("versionType", SWApplication.getInstance().getMetaValueInApplication("version_type"))
                        // 经度
                        .addHeader("longitude", "")
                        // 维度
                        .addHeader("latitude", "")
                        // 设备号
                        .addHeader("deviceNo", deviceId)
//                        // 会话
//                        .addHeader("sessionId", null == Application.getInstance().loginVo ? "" : Application.getInstance().loginVo.getSessionId())
                        // 推送编号
                        .addHeader("pushId", deviceId)
                        .build();

                return chain.proceed(request);
            }
        };
    }

    @Override
    protected Interceptor getNetworkInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Override
    protected SSLSocketFactory getSSLSocketFactory() {
        try {
            return SocketFactory.getSSLSocketFactory();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected X509TrustManager getX509TrustManager() {
        return new MyTrustManager();
    }

    @Override
    protected void configureOkHttpClient(OkHttpClient.Builder builder) {
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
//        super.configureOkHttpClient(builder);

    }
}