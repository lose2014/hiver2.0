package com.seaway.hiver.model.main.teacher;

import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.main.teacher.data.param.GetIconCodeParam;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.seaway.hiver.model.main.teacher.service.TMainService;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/19.
 */

public interface IMainTeacherDataSource  extends IDataSource {


    /**
     * 理财首页理财产品查询
     *
     * @return
     */
    Observable<GetIconCodeVo> queryFinanceProductList();

    /**
     * 理财首页理财产品查询
     *
     * @return
     */
    Observable<GetIconCodeVo> queryFinanceProductListInCache();
}
