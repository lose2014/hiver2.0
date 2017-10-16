package com.seaway.hiver.biz.login.contract;


import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.common.biz.contract.CommonLoginContract;

/**
 * Created by Leo.Chang on 2017/5/18.
 */

public interface LoginPwdModifyContract {
    interface View extends IBaseView<Presenter> {
        void modifySuccess();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 修改登录密码
         *
         * @param oldPwd           旧密码
         * @param newPwd           新密码
//         * @param credentialType   证件类型
//         * @param credentialNumber 证件号码
         */
        void requestLoginPwdModify(String oldPwd, String newPwd);
    }
}
