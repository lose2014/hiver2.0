package com.seaway.hiver.common.biz.contract;

import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.common.biz.IBaseView;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;

/**
 * Created by Leo.Chang on 2017/5/27.
 */

public interface CommonLoginContract {
    interface View<T> extends IBaseView<T> {
        /**
         * 使用账号密码登录
         */
        void accountLogin();

        /**
         * 手势密码登录
         */
        void gestureLogin();

        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 不需要登录资源跳转
         */
        void gotoWithoutLogin();

        /**
         * 用户等级不够，无法使用功能
         */
        void levelLowError();

        /**
         * 使用账号密码登录
         *
         * @param vo 资源监测结果
         */
        void accountLogin(CheckResourceVo vo);

        /**
         * 手势密码登录
         *
         * @param vo 资源监测结果
         */
        void gestureLogin(CheckResourceVo vo);
    }

    interface Presenter extends IBasePresenter {
        void executeLogin();

        /**
         * 客户端资源检查
         *
         * @param resourceId 资源编号
         */
        void checkResource(String resourceId);
    }
}
