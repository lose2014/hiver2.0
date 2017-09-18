package com.seaway.hiver.model.common;

import com.seaway.hiver.model.common.data.param.CrashInfoParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;
import com.seaway.hiver.model.common.data.vo.GetAgreementVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Leo.Chang on 2017/5/17.
 */

public interface IDataSource {

    /**
     * 保存Session
     *
     * @param session session
     */
//    void saveSession(String session);


    /**
     * 上传Crash日志
     *
     * @param param 日志内容
     * @return
     */
    Observable<BaseVo> uploadCrashInfo(CrashInfoParam param);


    /**
     * 上传Crash日志
     *
     * @param param 日志内容
     * @return
     */
    Observable<BaseVo> uploadCrashInfo(String param);
}