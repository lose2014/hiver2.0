package com.seaway.hiver.common.biz.contract;

import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.model.common.data.vo.LoginVo;


/**
 * Created by Leo.Chang on 2017/5/17.
 */

public interface Contract {

    interface View extends IBaseView<Presenter> {
        /**
         * 获取登录用户信息
         *
         * @param vo 登录用户信息
         */
        void setUserInfo(LoginVo vo);
    }

//    interface RequestSmsCodeView extends IBaseView<Presenter> {
//
//    }

    interface Presenter extends IBasePresenter {
        /**
         * 获取登录用户信息
         */
        void getUserInfo();
    }

}