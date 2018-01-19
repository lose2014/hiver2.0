package com.seaway.hiver.model.main.teacher;

import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.main.teacher.data.param.BillListParam;
import com.seaway.hiver.model.main.teacher.data.param.GetIconCodeParam;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetQuestionListVo;
import com.seaway.hiver.model.main.teacher.data.vo.IncomeVo;
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
    Observable<IncomeVo> queryMonthIncome();
    /**
     * 账单查询
     *
     * @return
     */
    Observable<GetBillListVo> queryBillList(String page, String id);

    /**
     * 课程查询
     *
     * @return
     */
    Observable<GetCourseListVo> queryCourseList(String page, String id, int status, String subject);
    /**
     * 课程查询
     *
     * @return
     */
    Observable<GetQuestionListVo> queryQuestionList(String page, String id,String grade,String unit,String content, String status, String subject);

    /**
     * 理财首页理财产品查询
     *
     * @return
     */
    Observable<GetIconCodeVo> queryFinanceProductListInCache();
}
