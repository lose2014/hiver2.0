package com.seaway.hiver.model.common.function;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.model.common.exception.ConnectionExceptionHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 统一Exception处理，拦截所有网络请求中的错误，并转换为ConnectionException
 * Created by Leo.Chang on 2017/5/5.
 */
public class ErrorInterceptorFunc<T> implements Function<Throwable, ObservableSource<T>> {
    @Override
    public ObservableSource<T> apply(@NonNull Throwable throwable) throws Exception {
        throwable.printStackTrace();
        Logger.e(throwable.getMessage());
        return Observable.error(ConnectionExceptionHandler.handler(throwable));
    }
}