package com.seaway.hiver.common.biz.contract;

import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;

/**
 * Created by Leo.Chang on 2017/5/18.
 */

public interface RequestSmsCodeContract {

    interface RequestSmsCodeView<T> extends IBaseView<T> {

        /**
         * 获取短信验证码成功
         *
         * @param vo 获取短信验证码成功
         */
        void getSmsCodeSuccess(BaseOutputVo vo);
    }

    interface RequestSmsCodePresenter extends IBasePresenter {
        /**
         * 获取短信验证码
         *
         * @param mobile       手机号码
         * @param businessType 业务类型
         * @param cardId       卡号
         * @param transAmt     交易金额
         * @param codeId       图形验证码编号
         * @param code         图形验证码内容
         */
        void requestSmsCode(String mobile, String businessType, String cardId, String transAmt, String codeId, String code);
    }
}
