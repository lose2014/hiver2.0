package com.seaway.hiver.common.biz.presenter;

import com.seaway.android.sdk.toolkit.SWTimerUtil;
import com.seaway.hiver.common.biz.contract.CommonLoginContract;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.exception.ServerResponseException;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/27.
 */
public class CommonLoginPresenter<T extends CommonLoginContract.View, E extends IDataSource> extends BasePresenter<T, E> implements CommonLoginContract.Presenter {

    public CommonLoginPresenter(T view) {
        super(view);
    }

    public CommonLoginPresenter(T view, E dataSource) {
        super(view);
        setDataSource(dataSource);
        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {
    }

    @Override
    public void executeLogin() {
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
//                if (!Application.getInstance().hasLogin) {
//                    // 获取登录用户信息
//                    LoginVo vo = (null == Application.getInstance().loginVo ? mDataSource.getLoginUserInfo() : Application.getInstance().loginVo);
//                    if (null == vo) {
//                        e.onNext(1);
//                        e.onComplete();
//                        return;
//                    }
//                    Application.getInstance().loginVo = vo;
//                    if ("2".equalsIgnoreCase(vo.getPwdIdentify()) && Long.parseLong(SWTimerUtil.getCurrentDate("yyyyMMdd")) <= Long.parseLong(vo.getPwdEndDate())) {
//                        // 如果用户已开通手势密码，并且当前时间未超过登录有效期，则使用手势密码登录
//                        e.onNext(2);
//                        e.onComplete();
//                    } else {
//                        e.onNext(1);
//                        e.onComplete();
//                    }
//                } else {
//                    e.onNext(0);
//                    e.onComplete();
//                }
//            }
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        switch (integer) {
//                            case 0: {
//                                // 已登录
//                                mView.loginSuccess();
//                            }
//                            break;
//                            case 1: {
//                                //跳转账号登录
//                                mView.accountLogin();
//                            }
//                            break;
//                            default: {
//                                // 跳转手势密码登录
//                                mView.gestureLogin();
//                            }
//                        }
//                    }
//                });
    }

    @Override
    public void checkResource(String resourceId) {
//        mDataSource.checkResource(resourceId)
//                .map(new Function<CheckResourceVo, CheckResourceVo>() {
//                    @Override
//                    public CheckResourceVo apply(@NonNull CheckResourceVo checkResourceVo) throws Exception {
//                        if ("0".equalsIgnoreCase(checkResourceVo.getState())) {
//                            // 如果资源可用，返回是否需要登录；true：需要登录；false：不需要登录
////                            return !"0".equalsIgnoreCase(checkResourceVo.getLoginFlag());
//                            return checkResourceVo;
//                        } else {
//                            ServerResponseException sre = new ServerResponseException("100000", checkResourceVo.getReason());
//                            throw sre;
//                        }
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new CommonObserver<CheckResourceVo>(mView, mDisposable) {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        super.onSubscribe(d);
//                        mView.showPregressDialog();
//                    }
//
//                    @Override
//                    public void onNext(@NonNull CheckResourceVo vo) {
////                        if (aBoolean) {
////                            // 需要登录
////                            executeLogin();
////                        } else {
////                            // 不需要登录
////                            mView.gotoWithoutLogin();
////                        }
//                        if ("0".equals(vo.getState())) {
//                            // 可以正常使用
//                            if ("0".equals(vo.getLoginFlag())) {
//                                // 不需要登录
//                                mView.gotoWithoutLogin();
//                            } else {
//                                // 需要登录
//                                executeLogin(vo);
//                            }
//                        }
//                    }
//                });
    }

    public void executeLogin(final CheckResourceVo vo) {
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
//                if (!Application.getInstance().hasLogin) {
//                    // 获取登录用户信息
//                    LoginVo vo = (null == Application.getInstance().loginVo ? mDataSource.getLoginUserInfo() : Application.getInstance().loginVo);
//                    if (null == vo) {
//                        e.onNext(1);
//                        e.onComplete();
//                        return;
//                    }
//                    Application.getInstance().loginVo = vo;
//                    if ("2".equalsIgnoreCase(vo.getPwdIdentify()) && Long.parseLong(SWTimerUtil.getCurrentDate("yyyyMMdd")) <= Long.parseLong(vo.getPwdEndDate())) {
//                        // 如果用户已开通手势密码，并且当前时间未超过登录有效期，则使用手势密码登录
//                        e.onNext(2);
//                        e.onComplete();
//                    } else {
//                        e.onNext(1);
//                        e.onComplete();
//                    }
//                } else {
//                    e.onNext(0);
//                    e.onComplete();
//                }
//            }
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        switch (integer) {
//                            case 0: {
//                                // 已登录
//                                if (Integer.parseInt(Application.getInstance().loginVo.getAccountType()) >= Integer.parseInt(vo.getMinAccType())) {
//                                    // 如果登录用户的账户类型高于要求的用户等级，则可以使用
//                                    mView.loginSuccess();
//                                } else {
//                                    mView.levelLowError();
//                                }
//                            }
//                            break;
//                            case 1: {
//                                //跳转账号登录
//                                mView.accountLogin(vo);
//                            }
//                            break;
//                            default: {
//                                // 跳转手势密码登录
//                                mView.gestureLogin(vo);
//                            }
//                        }
//                    }
//                });
    }
}