package com.seaway.hiver.common.biz.presenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Leo.Chang on 2017/5/10.
 */
public class BasePresenter<T,E> {
    protected CompositeDisposable mDisposable = new CompositeDisposable();

    protected T mView;

    protected E mDataSource;

    public BasePresenter(T view) {
        this.mView = view;
    }

    protected void setDataSource(E dataSource) {
        this.mDataSource = dataSource;
    }

    public void unsubscribe() {
        mDisposable.clear();
    }
}