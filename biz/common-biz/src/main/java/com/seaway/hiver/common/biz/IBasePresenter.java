package com.seaway.hiver.common.biz;

/**
 * Created by Leo.Chang on 2017/5/10.
 */
public interface IBasePresenter {
    /**
     * 订阅
     */
    void subscribe();

    /**
     * 取消订阅
     */
    void unsubscribe();
}
