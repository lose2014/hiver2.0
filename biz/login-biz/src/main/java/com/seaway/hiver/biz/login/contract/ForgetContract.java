package com.seaway.hiver.biz.login.contract;


import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.common.biz.contract.RequestSmsCodeContract;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;

/**
 * Created by Leo.Chang on 2017/5/13.
 */
public interface ForgetContract {

    interface View extends  RequestSmsCodeContract.RequestSmsCodeView<Presenter>{
        /**
         * 重置登录密码成功
         */
        void requestResetPwdSuccess();

        void checkSuccess(BaseOutputVo vo);
    }

    interface Presenter extends RequestSmsCodeContract.RequestSmsCodePresenter {

        /**
         * 重置登录密码
         *
         * @param captcha     业务编码
         * @param newPwd        新密码
         * @param newPwdConfirm 确认密码
         */
        void requestResetPwd(String mobile,String captcha, String newPwd, String newPwdConfirm);

        /**
         * 重置密码校验
         *
         * @param mobileNumber     手机号码
         * @param smsCodeId        短信验证码编号
         * @param code             短息验证码
         * @return
         */
        void checkResetPwd(String mobileNumber, String smsCodeId, String code);
    }
}