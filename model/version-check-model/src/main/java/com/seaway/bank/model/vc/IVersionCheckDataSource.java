package com.seaway.bank.model.vc;

import com.seaway.hiver.model.common.data.vo.CheckVersionVo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 客户端版本检测
 * Created by Leo.Chang on 2017/6/10.
 */
public interface IVersionCheckDataSource {
    /**
     * 客户端版本检测
     *
     * @return
     */
    Observable<CheckVersionVo> checkVersion();

    /**
     * 下载
     *
     * @param url 下载地址
     * @return
     */
    Observable<ResponseBody> download(String url ,DownloadProgressListener downloadProgressListener);
}