package com.seaway.hiver.common.biz;

/**
 * Created by Leo.Chang on 2017/5/10.
 */

public interface IBaseView<T> {
    /**
     * 设置Presenter
     *
     * @param presenter presenter
     */
    void setPresenter(T presenter);

    /**
     * 显示加载框
     */
    void showPregressDialog();
    void showAutoHidenDialog(int rid);
    void showUnconnectDialog(String msg);
    void showOnConnectionDialog(int rid);
    void disMissConnectingDialog();
    /**
     * 隐藏加载框
     */
    void dismissPregressDialog();

    /**
     * 请求错误处理
     *
     * @param e
     */
    void onError(Throwable e);
}