package com.seaway.hiver.biz.login.contract;


import com.seaway.hiver.common.biz.contract.CommonLoginContract;

/**
 * Created by Leo.Chang on 2017/5/18.
 */

public interface LogoutContract {
    interface View extends CommonLoginContract.View<Presenter> {
    }

    interface Presenter extends CommonLoginContract.Presenter {
        void requestLogout();
    }
}
