package com.seaway.bank.model.vc.util;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.bank.model.vc.DownloadProgressListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Leo.Chang on 2017/6/10.
 */
public class DownloadProgressInterceptor implements Interceptor {
    private DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        Logger.i("DownloadProgressInterceptor ->");
        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}
