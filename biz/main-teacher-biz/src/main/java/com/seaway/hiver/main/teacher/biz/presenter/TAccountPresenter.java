package com.seaway.hiver.main.teacher.biz.presenter;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.CommonLoginPresenter;
import com.seaway.hiver.main.teacher.biz.contract.TAccountContract;
import com.seaway.hiver.main.teacher.biz.contract.TWekeContract;
import com.seaway.hiver.model.main.teacher.IMainTeacherDataSource;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.impl.MainTeacherDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leo.Chang on 2017/5/11.
 */

public class TAccountPresenter extends CommonLoginPresenter<TAccountContract.View, IMainTeacherDataSource> implements TAccountContract.Presenter {

    public TAccountPresenter(TAccountContract.View view) {
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

}
