package com.seaway.hiver.model.main.teacher.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.param.BaseInputParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.common.impl.DataSource;
import com.seaway.hiver.model.main.teacher.IMainTeacherDataSource;
import com.seaway.hiver.model.main.teacher.data.param.BillListParam;
import com.seaway.hiver.model.main.teacher.data.param.CourselListParam;
import com.seaway.hiver.model.main.teacher.data.param.GetIconCodeParam;
import com.seaway.hiver.model.main.teacher.data.param.LoginParam;
import com.seaway.hiver.model.main.teacher.data.param.QuestionListParam;
import com.seaway.hiver.model.main.teacher.data.param.RequestBindDeviceManageParam;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetQuestionListVo;
import com.seaway.hiver.model.main.teacher.data.vo.IncomeVo;
import com.seaway.hiver.model.main.teacher.service.TMainService;
import com.seaway.hiver.model.main.teacher.shared.LoginSharedPreferences;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/10.
 */
public class MainTeacherDataSource extends DataSource implements IMainTeacherDataSource {


    /**
     * 理财首页理财产品查询
     *
     * @return
     */
    @Override
    public Observable<GetIconCodeVo> queryFinanceProductList() {
        GetIconCodeParam param = new GetIconCodeParam();

        return Observable.just(param)
                .flatMap(new Function<GetIconCodeParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull GetIconCodeParam param) throws Exception {
                        return RetrofitClient.getInstance().create(TMainService.class).queryFinanceProductList(Util.transformat("queryFinanceProductList", param));
                    }
                })
                .map(new ServerResponseFunc<GetIconCodeVo>(GetIconCodeVo.class))
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 账号余额查询
     *
     * @return
     */
    @Override
    public Observable<IncomeVo> queryMonthIncome() {

        return RetrofitClient.getInstance()
                .create(TMainService.class).queryMonthIncome(Util.transformat(new BaseInputParam()))
                .map(new ServerResponseFunc<IncomeVo>(IncomeVo.class)).subscribeOn(Schedulers.newThread())
                .onErrorResumeNext(new ErrorInterceptorFunc<IncomeVo>()).subscribeOn(Schedulers.newThread());
    }


    @Override
    public Observable<GetBillListVo> queryBillList(String page, String id) {
        BillListParam param = new BillListParam();
        param.setPage(page);
        param.setSize("10");
        param.setTeacherId(id);

        return Observable.just(param)
                .flatMap(new Function<BillListParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull BillListParam loginParam) throws Exception {
                        return RetrofitClient.getInstance().create(TMainService.class).queryBillList(Util.transformat(loginParam));
                    }
                })
                .map(new ServerResponseFunc<GetBillListVo>(GetBillListVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<GetBillListVo>())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<GetCourseListVo> queryCourseList(String page, String id, int status, String subject) {
        CourselListParam param = new CourselListParam();
        param.setPage(page);
        param.setSize("10");
        param.setTeacherId(id);

        return Observable.just(param)
                .flatMap(new Function<CourselListParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull CourselListParam loginParam) throws Exception {
                        return RetrofitClient.getInstance().create(TMainService.class).queryCourseList(Util.transformat(loginParam));
                    }
                })
                .map(new ServerResponseFunc<GetCourseListVo>(GetCourseListVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<GetCourseListVo>())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<GetQuestionListVo> queryQuestionList(String page, String id, String grade, String unit, String content, String status, String subject) {
        QuestionListParam param = new QuestionListParam();
        param.setPage(page);
        param.setSize("10");
//        param.setTeacherId(id);

        return Observable.just(param)
                .flatMap(new Function<QuestionListParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull QuestionListParam loginParam) throws Exception {
                        return RetrofitClient.getInstance().create(TMainService.class).queryQuestionList(Util.transformat(loginParam));
                    }
                })
                .map(new ServerResponseFunc<GetQuestionListVo>(GetQuestionListVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<GetQuestionListVo>())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 理财首页理财产品查询
     *
     * @return
     */
    @Override
    public Observable<GetIconCodeVo> queryFinanceProductListInCache() {
        return Observable.create(new ObservableOnSubscribe<GetIconCodeVo>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<GetIconCodeVo> e) throws Exception {
//                GetIconCodeVo vo = BankSharedPreferences.getFinancialInfo();
                GetIconCodeVo vo = new GetIconCodeVo();
                e.onNext(vo);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
    }
}