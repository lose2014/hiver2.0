package com.seaway.bank.model.vc.service;

import com.seaway.hiver.model.common.data.param.BaseCheckInputParam;
import com.seaway.hiver.model.common.data.vo.BaseVo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 客户端版本检测
 * Created by Leo.Chang on 2017/6/10.
 */
public interface VersionCheckService {
    /**
     * 客户端版本检测
     *
     * @param param
     * @return
     */
    @POST("checkVersion")
    Observable<BaseVo> checkVersion(@Body BaseCheckInputParam param);

    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);
}
