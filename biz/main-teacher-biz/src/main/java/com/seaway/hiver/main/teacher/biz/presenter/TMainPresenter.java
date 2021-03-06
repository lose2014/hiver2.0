package com.seaway.hiver.main.teacher.biz.presenter;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.CommonLoginPresenter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.main.teacher.IMainTeacherDataSource;
import com.seaway.hiver.model.main.teacher.data.param.BillListParam;
import com.seaway.hiver.model.main.teacher.data.vo.BillVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetQuestionListVo;
import com.seaway.hiver.model.main.teacher.data.vo.IncomeVo;
import com.seaway.hiver.model.main.teacher.impl.MainTeacherDataSource;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/11.
 */

public class TMainPresenter extends CommonLoginPresenter<TMainContract.View, IMainTeacherDataSource> implements TMainContract.Presenter {

    public TMainPresenter(TMainContract.View view) {
        super(view);
        view.setPresenter(this);
        setDataSource(new MainTeacherDataSource());
    }

    @Override
    public void subscribe() {
        // 获取初始数据
//        queryAdvertList(0);
//        queryFinancialInfo(0);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void initBankPortalDataSource() {

    }

    @Override
    public void queryBillList(String page, String id) {
        mDataSource.queryBillList(page, id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<GetBillListVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull GetBillListVo loginVo) {
                        Logger.e("登录成功");
                        // 登录成功
                        mView.queryBillListSuccess(loginVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        Logger.d("exception---"+e);
//                        mView.loginFail();
                    }
                });
    }

    @Override
    public void queryMonthIncome() {

        mDataSource.queryMonthIncome().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<IncomeVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull IncomeVo loginVo) {
                        Logger.e("收入获取成功");
                        // 登录成功
                        mView.queryMonthIncomeSuccess(loginVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        Logger.d("收入获取失败---"+e);
//                        mView.loginFail();
                    }
                });
    }

    /**
     * 在线问答
     * */
    @Override
    public void queryQuestionList(String page, String id, String grade, String unit, String content, String status, String subject) {
        mDataSource.queryQuestionList(page, id,grade,unit,content,status,subject).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<GetQuestionListVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull GetQuestionListVo loginVo) {
                        Logger.e("课程获取成功");
                        // 登录成功
                        mView.queryQuestionListSuccess(loginVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        Logger.d("exception---"+e);
//                        mView.loginFail();
                    }
                });
    }

    @Override
    public void queryCourseList(String page, String id, int status, String subject) {
        mDataSource.queryCourseList(page, id,status,subject).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<GetCourseListVo>(mView, mDisposable) {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        mView.showPregressDialog();
                    }

                    @Override
                    public void onNext(@NonNull GetCourseListVo loginVo) {
                        Logger.e("课程获取成功");
                        // 登录成功
                        mView.queryCourseListSuccess(loginVo);
                    }

                    @Override
                    protected void error(@NonNull Throwable e) {
                        Logger.d("exception---"+e);
//                        mView.loginFail();
                    }
                });
    }

    /**
     * 查询轮播图
     *
     * @param type 0：缓存数据；1：远程数据；
     */
    @Override
    public void queryAdvertList(int type) {
//        if (0 == type) {
//            // 从缓存中获取轮播图数据
//            mDataSource.queryAdvertListInCache("402")
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<QueryAdvertListVo>() {
//                        @Override
//                        public void accept(@NonNull QueryAdvertListVo advertListVo) throws Exception {
//                            mView.queryAdvertListInCacheSuccess(advertListVo);
//                        }
//                    });
//        } else {
//            // 从服务端获取数据
//            Observable<QueryAdvertListVo> remoteObservable = mDataSource.queryAdvertList("402").subscribeOn(Schedulers.newThread());
//            Observable<QueryAdvertListVo> localObservable = mDataSource.queryAdvertListInCache("402");
//
//            Observable
//                    .zip(remoteObservable, localObservable, new BiFunction<QueryAdvertListVo, QueryAdvertListVo, HashMap<String, QueryAdvertListVo>>() {
//                        @Override
//                        public HashMap<String, QueryAdvertListVo> apply(@NonNull QueryAdvertListVo remote, @NonNull QueryAdvertListVo local) throws Exception {
//                            HashMap<String, QueryAdvertListVo> map = new HashMap<String, QueryAdvertListVo>(1);
//                            if (remote.equals(local)) {
//                                // 如果远程数据和本地数据一样，则什么也不做
//                                Logger.i("轮播图数据与缓存一致，无需更新");
//                            } else {
//                                // 如果远程数据和本地数据不一样，则缓存本次的远程数据
//                                Logger.i("轮播图数据有更新");
//                                mDataSource.saveAdvertListInCache("402", remote);
//                                map.put("advert", remote);
//                            }
//                            return map;
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<HashMap<String, QueryAdvertListVo>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//                            mDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(@NonNull HashMap<String, QueryAdvertListVo> map) {
//                            if (null != map.get("advert")) {
//                                mView.queryAdvertListSuccess(map.get("advert"), true);
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        }
//                    });
//
//        }
    }

    /**
     * 查询理财产品
     *
     * @param type 0：缓存数据；1：远程数据；
     */
    @Override
    public void queryFinancialInfo(int type) {
//        if (0 == type) {
//            // 从缓存中获取理财产品
//            mDataSource.queryFinanceProductListInCache()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<FinanceProductVo>() {
//                        @Override
//                        public void accept(@NonNull FinanceProductVo financeProductVo) throws Exception {
//                            mView.queryFinancialInCacheSuccess(financeProductVo);
//                        }
//                    });
//        } else {
            // 从服务器获取数据
//            Observable<QueryFinanceProductListVo> remoteObservable = mDataSource.queryFinanceProductList();
//            Observable<FinanceProductVo> localObservable = mDataSource.queryFinanceProductListInCache();
//
//            Observable
//                    .zip(remoteObservable, localObservable, new BiFunction<QueryFinanceProductListVo, FinanceProductVo, HashMap<String, FinanceProductVo>>() {
//                        @Override
//                        public HashMap<String, FinanceProductVo> apply(@NonNull QueryFinanceProductListVo remote, @NonNull FinanceProductVo local) throws Exception {
//                            HashMap<String, FinanceProductVo> map = new HashMap<String, FinanceProductVo>(1);
//                            if (null != remote.getDetails() && remote.getDetails().size() > 0) {
//                                FinanceProductVo remoteVo = remote.getDetails().get(0);
//                                if (remoteVo.equals(local)) {
//                                    // 如果远程数据和本地数据一样，则什么也不做
//                                    Logger.i("理财产品数据与缓存一致，无需更新");
//                                } else {
//                                    // 如果远程数据和本地数据不一样，则缓存本次的远程数据
//                                    Logger.i("理财产品数据有更新");
//                                    BankSharedPreferences.saveFinancialInfo(remoteVo);
//                                    map.put("financial", remoteVo);
//                                }
//                            }
//                            return map;
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<HashMap<String, FinanceProductVo>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//                            mDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(@NonNull HashMap<String, FinanceProductVo> map) {
//                            if (null != map.get("financial")) {
//                                mView.queryFinancialSuccess(map.get("financial"));
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                        }
//
//                        @Override
//                        public void onComplete() {
//                        }
//                    });
//        }
    }


}
