package com.seaway.hiver.model.common.service;

import com.seaway.hiver.model.common.data.CrashParam;
import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.param.BankBaseParam;
import com.seaway.hiver.model.common.data.param.RequestSmsCodeParam;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 公用请求接口
 * Created by Leo.Chang on 2017/5/18.
 */
public interface CommonService {

    /**
     * 获取短信验证码
     *
     * @param param 请求参数
     * @return
     */
    @POST("/captcha")
    Observable<BaseVo> requestSmsCode(@Body BankBaseParam param);
    /**
     * 获取短信验证码
     *
     * @param param 请求参数
     * @return
     */
    @POST("/message/list")
    Observable<BaseVo> requestMessageList(@Body BankBaseParam param);
    /**
     * 获取协议
     *
     * @param param 请求参数
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> getAgreement(@Body BankBaseParam<String> param);
    /**
     * 客户端资源检查
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> checkResource(@Body BankBaseParam<String> param);

    /**
     * 广告位查询
     *
     * @param param
     * @return
     */
    @POST("sendCookie")
    Observable<BaseVo> queryAdvertList(@Body BankBaseParam<String> param);

    /**
     * 下载文件
     *
     * @param url 下载地址
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);

    /**
     * 上传Crash日志
     *
     * @param param 日志信息
     * @return
     */
    @POST("crash")
    Observable<BaseVo> requestUploadPhoneLogs(@Body CrashParam param);
}
