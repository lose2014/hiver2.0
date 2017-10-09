package com.seaway.hiver.model.login.service;


import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.vo.BaseVo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Leo.Chang on 2017/5/16.
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> login(@Body BankBaseParam<String> param);

    /**
     * 获取图片验证码
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> getIconCode(@Body BankBaseParam<String> param);

    /**
     * 绑定设备
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> requestBindDeviceManage(@Body BankBaseParam<String> param);

    /**
     * 登录注销
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> requestLogout(@Body BankBaseParam<String> param);
}