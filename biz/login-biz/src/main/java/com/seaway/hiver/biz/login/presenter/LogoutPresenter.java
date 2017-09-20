package com.seaway.hiver.biz.login.presenter;


import com.seaway.hiver.biz.login.contract.LogoutContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.CommonLoginPresenter;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.shared.MSharedPreferences;
import com.seaway.hiver.model.login.ILoginDataSource;
import com.seaway.hiver.model.login.impl.LoginDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

/**
 * Created by Leo.Chang on 2017/5/18.
 */
public class LogoutPresenter extends CommonLoginPresenter<LogoutContract.View,ILoginDataSource> implements LogoutContract.Presenter {

    public LogoutPresenter(LogoutContract.View view) {
        super(view);
        setDataSource(new LoginDataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void requestLogout() {
        mDataSource.requestLogout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BaseOutputVo>(mView,mDisposable) {
                    @Override
                    public void onNext(@NonNull BaseOutputVo baseOutputVo) {
                        // 删除登录用户信息缓存
                        MSharedPreferences.saveLoginUserInfo(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // 删除登录用户信息缓存
                        MSharedPreferences.saveLoginUserInfo(null);
                    }
                });
    }
}