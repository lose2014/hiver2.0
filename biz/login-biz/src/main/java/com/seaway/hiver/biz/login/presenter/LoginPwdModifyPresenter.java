package com.seaway.hiver.biz.login.presenter;



import com.seaway.hiver.biz.login.contract.LoginPwdModifyContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.BasePresenter;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.login.impl.LoginDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leo.Chang on 2017/5/19.
 */

public class LoginPwdModifyPresenter extends BasePresenter<LoginPwdModifyContract.View, LoginDataSource> implements LoginPwdModifyContract.Presenter {
    public LoginPwdModifyPresenter(LoginPwdModifyContract.View view) {
        super(view);
        // 注入数据源
        setDataSource(new LoginDataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 修改登录密码
     *
     * @param oldPwd           旧密码
     * @param newPwd           新密码
     * @param credentialType   证件类型
     * @param credentialNumber 证件号码
     */
    @Override
    public void requestLoginPwdModify(String oldPwd, String newPwd, String credentialType, String credentialNumber) {
        mDataSource.requestLoginPwdModify(oldPwd, newPwd, credentialType, credentialNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BaseOutputVo>(mView, mDisposable) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull BaseOutputVo vo) {
                        mView.modifySuccess();
                    }
                });
    }
}
