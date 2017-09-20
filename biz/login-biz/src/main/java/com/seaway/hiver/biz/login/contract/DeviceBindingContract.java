package com.seaway.hiver.biz.login.contract;


import com.seaway.hiver.common.biz.contract.RequestSmsCodeContract;

/**
 * Created by Leo.Chang on 2017/5/18.
 */

public interface DeviceBindingContract {

    interface DeviceBindingView extends RequestSmsCodeContract.RequestSmsCodeView<DeviceBindingPresenter> {
        void bindDeviceSuccess();
    }

    interface DeviceBindingPresenter extends RequestSmsCodeContract.RequestSmsCodePresenter {
        /**
         * 绑定设备
         *
         * @param nickName 设备别名
         * @param codeId   短信验证码编号
         * @param code     短信验证码
         */
        void bindDevice(String nickName, String codeId, String code);
    }
}