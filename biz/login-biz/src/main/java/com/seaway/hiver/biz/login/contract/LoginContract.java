package com.seaway.hiver.biz.login.contract;


import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.model.common.data.vo.CheckUserNameVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;

/**
 * Created by Leo.Chang on 2017/5/10.
 */

public interface LoginContract {

    interface View extends IBaseView<Presenter> {
        /**
         * 获取记录的账号
         *
         * @param userName 登录账号
         */
        void getRememberUserName(String userName);

        void checkUserNameSuccess(CheckUserNameVo checkUserNameVo);

        /**
         * 登录成功
         */
        void loginSuccess(LoginVo loginVo);

        /**
         * 登录失败
         */
        void loginFail();

        /**
         * 获取图片验证码失败
         */
        void getIconCodeFail();
    }

    interface Presenter extends IBasePresenter {
        /**
         * 登录
         *
         * @param userName 用户名
         * @param pwd      密码
         */
        void login(String userName, String pwd);

        /**
         * 获取图形验证码
         */
        void getIconCode();

        /**
         * 记录登录账号
         *
         * @param userName 登录账号
         */
        void rememberUserName(String userName);

        /**
         * 获取登录账号
         */
        void getUserName();

        /**
         * 检测登录账号是否存在
         */
        void checkUserName(String userName);
    }
}