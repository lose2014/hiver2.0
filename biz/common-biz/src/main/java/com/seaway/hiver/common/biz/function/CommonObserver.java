package com.seaway.hiver.common.biz.function;

import com.seaway.hiver.common.biz.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leo.Chang on 2017/5/18.
 */

public abstract class CommonObserver<T> implements Observer<T> {
    private WeakReference<IBaseView> mView;
    private CompositeDisposable mDisposable;

    public CommonObserver(IBaseView view , CompositeDisposable mDisposable) {
        mView = new WeakReference<IBaseView>(view);
        this.mDisposable = mDisposable;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable.add(d);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (null != mView && null != mView.get()) {
            mView.get().onError(e);
            mView.get().dismissPregressDialog();
            error(e);
        }
    }

    @Override
    public void onComplete() {
        if (null != mView && null != mView.get()) {
            mView.get().dismissPregressDialog();
        }
    }

    protected void error(@NonNull Throwable e) {
    }
}
