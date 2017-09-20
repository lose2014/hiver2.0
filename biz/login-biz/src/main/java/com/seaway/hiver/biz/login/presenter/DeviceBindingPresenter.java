package com.seaway.hiver.biz.login.presenter;


import com.seaway.hiver.biz.login.contract.DeviceBindingContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.RequestSmsCodePresenter;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.login.ILoginDataSource;
import com.seaway.hiver.model.login.impl.LoginDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 绑定设备
 * Created by Leo.Chang on 2017/5/18.
 */
public class DeviceBindingPresenter extends RequestSmsCodePresenter<DeviceBindingContract.DeviceBindingView, ILoginDataSource> implements DeviceBindingContract.DeviceBindingPresenter {

    public DeviceBindingPresenter(DeviceBindingContract.DeviceBindingView view) {
        super(view);
        // 注入数据源
        setDataSource(new LoginDataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void bindDevice(String nickName, String codeId, String code) {
        mDataSource.requestBindDeviceManage(nickName, codeId, code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<BaseOutputVo>(mView,mDisposable) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull BaseOutputVo baseOutputVo) {
                        mView.bindDeviceSuccess();
                    }
                });
    }
}
