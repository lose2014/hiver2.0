package com.seaway.hiver.model.common.exception;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.seaway.android.sdk.logger.Logger;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * 统一错误处理
 * Created by Leo.Chang on 2017/5/5.
 */
public class ConnectionExceptionHandler {

    /**
     * 统一处理Exception
     *
     * @param t
     * @return
     */
    public static ConnectionException handler(Throwable t) {
        ConnectionException ex = null;
        if (t instanceof HttpException) {
            // 如果是HTTPException，比如500,403,400
            Logger.i("ConnectionExceptionHandler -> HttpException");
            HttpException httpException = (HttpException) t;
            ex = new ConnectionException(t, String.valueOf(httpException.code()));

            try {
                ex.message = TextUtils.isEmpty(httpException.response().errorBody().string()) ? "网络连接超时，请稍后再试！" : httpException.response().errorBody().string();
            } catch (IOException e) {
                ex.message = "网络连接超时，请稍后再试！";
            }
            if(TextUtils.isEmpty(ex.message)){
                ex.message = "网络连接超时，请稍后再试！";
            }
            if(httpException.code()==404){
                ex.message = httpException.getMessage();
            }
        } else if (t instanceof ServerResponseException) {
            Logger.i("ConnectionExceptionHandler -> ServerResponseException");
            ServerResponseException serverResponseException = (ServerResponseException) t;
            ex = new ConnectionException(t, TextUtils.isEmpty(serverResponseException.code) ? "-100002" : serverResponseException.code);
            ex.message = TextUtils.isEmpty(serverResponseException.getMessage()) ? "网络连接超时，请稍后再试！" : serverResponseException.getMessage();
        } else if (t instanceof ConnectException) {
            // 如果是连接异常
            Logger.i("ConnectionExceptionHandler -> ConnectException");
            ex = new ConnectionException(t, "-100002");
            ex.message = "网络连接超时，请稍后再试！";
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            // 如果是连接超时异常
            Logger.i("ConnectionExceptionHandler -> TimeoutException");
            ex = new ConnectionException(t, "");
            ex.message = "服务器连接失败，请稍后再试！";
        } else if (t instanceof JsonParseException || t instanceof JSONException || t instanceof ParseException) {
            // json解析异常
            Logger.i("ConnectionExceptionHandler -> ParseException");
            ex = new ConnectionException(t, "-100002");
            ex.message = "网络连接超时，请稍后再试！";
        } else {
            // 未知异常
            Logger.i("ConnectionExceptionHandler -> OtherException");
            ex = new ConnectionException(t, "-100002");
            ex.message = TextUtils.isEmpty(t.getMessage()) ? "网络连接超时，请稍后再试！" : t.getMessage();
        }

        return ex;
    }
}