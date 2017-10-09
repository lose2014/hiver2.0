package com.seaway.hiver.model.main.teacher.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.param.BaseInputParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.common.impl.DataSource;
import com.seaway.hiver.model.main.teacher.IMainTeacherDataSource;
import com.seaway.hiver.model.main.teacher.data.param.GetIconCodeParam;
import com.seaway.hiver.model.main.teacher.data.param.LoginParam;
import com.seaway.hiver.model.main.teacher.data.param.RequestBindDeviceManageParam;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
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
                GetIconCodeVo vo =new GetIconCodeVo();
                e.onNext(vo);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
    }
}