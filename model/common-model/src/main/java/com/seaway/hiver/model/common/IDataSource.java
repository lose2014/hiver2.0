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
     * 获取短信验证码
     *
     * @param mobile       手机号码
     * @param businessType 业务类型
     * @param cardId       卡号
     * @param transAmt     交易金额
     * @param codeId       图形验证码编号
     * @param code         图形验证码内容
     * @return
     */
    Observable<RequestSmsCodeVo> requestSmsCode(String mobile, String businessType, String cardId, String transAmt, String codeId, String code);

    /**
     * 上传Crash日志
     *
     * @param param 日志内容
     * @return
     */
    Observable<BaseVo> uploadCrashInfo(String param);
}