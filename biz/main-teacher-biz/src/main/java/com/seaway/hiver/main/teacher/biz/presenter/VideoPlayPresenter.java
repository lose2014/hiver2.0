package com.seaway.hiver.main.teacher.biz.presenter;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.common.biz.function.CommonObserver;
import com.seaway.hiver.common.biz.presenter.CommonLoginPresenter;
import com.seaway.hiver.main.teacher.biz.contract.TWekeContract;
import com.seaway.hiver.main.teacher.biz.contract.VideoPlayContract;
import com.seaway.hiver.model.main.teacher.IMainTeacherDataSource;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.impl.MainTeacherDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Leo.Chang on 2017/5/11.
 */

public class VideoPlayPresenter extends CommonLoginPresenter<VideoPlayContract.View, IMainTeacherDataSource> implements VideoPlayContract.Presenter {

    public VideoPlayPresenter(VideoPlayContract.View view) {
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

}
