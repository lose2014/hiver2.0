package com.seaway.hiver.biz.login.presenter;


import com.seaway.hiver.biz.login.contract.ForgetContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.BasePresenter;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.login.impl.LoginDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leo.Chang on 2017/5/13.
 */
public class ForgetPresenter extends BasePresenter<ForgetContract.View, LoginDataSource> implements ForgetContract.Presenter {
    public ForgetPresenter(ForgetContract.View view) {
        super(view);
        setDataSource(new LoginDataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void requestResetPwd(String mobile,String captcha, String newPwd, String newPwdConfirm) {
        mDataSource.requestResetPwd(mobile,captcha, newPwd, newPwdConfirm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BaseOutputVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull BaseOutputVo vo) {
                        mView.requestResetPwdSuccess();
                    }
                });
    }

    @Override
    public void checkResetPwd(String mobileNumber, String smsCodeId, String code) {

    }

    @Override
    public void requestSmsCode(String mobile, String businessType, String cardId, String transAmt, String codeId, String code) {

    }
}