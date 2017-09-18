package com.seaway.hiver.common.biz.presenter;

import com.seaway.hiver.common.biz.contract.Contract;
import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.impl.DataSource;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/17.
 */
public class Presenter extends BasePresenter<Contract.View, IDataSource> implements Contract.Presenter {

    public Presenter(Contract.View view) {
        super(view);
        // 注入数据源
        setDataSource(new DataSource());
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    /**
     * 获取登录用户信息
     */
    @Override
    public void getUserInfo() {
//        Observable.create(new ObservableOnSubscribe<LoginVo>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<LoginVo> e) throws Exception {
//                e.onNext(mDataSource.getLoginUserInfo());
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<LoginVo>() {
//            @Override
//            public void accept(@NonNull LoginVo loginVo) throws Exception {
//                mView.setUserInfo(loginVo);
//            }
//        });
    }
}
