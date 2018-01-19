package com.seaway.hiver.model.main.teacher.service;


import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.vo.BaseVo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Leo.Chang on 2017/5/16.
 */
public interface TMainService {

    /**
     * 在售理财产品查询
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> queryFinanceProductList(@Body BankBaseParam<String> param);

    @POST("/bill/list")
    Observable<BaseVo> queryBillList(@Body BankBaseParam param);

    @POST("/bill/monthIncome")
    Observable<BaseVo> queryMonthIncome(@Body BankBaseParam param);

    @POST("/course/list")
    Observable<BaseVo> queryCourseList(@Body BankBaseParam param);

    @POST("/question/list")
    Observable<BaseVo> queryQuestionList(@Body BankBaseParam param);

    /**
     * 获取应用签名
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> getWebCommunitySignVo(@Body BankBaseParam<String> param);
}