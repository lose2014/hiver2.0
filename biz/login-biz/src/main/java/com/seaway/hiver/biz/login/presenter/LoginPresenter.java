package com.seaway.hiver.biz.login.presenter;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.biz.login.contract.LoginContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.BasePresenter;
import com.seaway.hiver.model.common.data.vo.CheckUserNameVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.login.ILoginDataSource;
import com.seaway.hiver.model.login.impl.LoginDataSource;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录业务处理类
 * Created by Leo.Chang on 2017/5/10.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View, ILoginDataSource> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
        // 注入数据源
        setDataSource(new LoginDataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getUserName();
//        getIconCode();
    }

    @Override
    public void login(String userName, String pwd) {
        mDataSource.login(userName, pwd).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<LoginVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull LoginVo loginVo) {
                        // 登录成功
                        mView.loginSuccess(loginVo);
                        // 记录登录信息
                        mDataSource.saveLoginUserInfo(loginVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        mView.loginFail();
                    }
                });
    }

    @Override
    public void getIconCode() {

    }


    @Override
    public void rememberUserName(final String userName) {
        Logger.i("记录账号");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                mDataSource.rememberUserName(userName);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void getUserName() {
        Disposable d = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(mDataSource.getUserName());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                mView.getRememberUserName(s);
            }
        });
        mDisposable.add(d);
    }

    @Override
    public void checkUserName(String userName) {
        mDataSource.checkUserName(userName).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<CheckUserNameVo>(mView,mDisposable) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull CheckUserNameVo checkUserNameVo) {
                        mView.checkUserNameSuccess(checkUserNameVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        mView.loginFail();
                    }
                });
    }
}