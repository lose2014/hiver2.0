package com.seaway.hiver.apps.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.shared.CommonSharedPreferences;
import com.seaway.hiver.common.biz.IBasePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.hiver.ui.dialog.UIProgressDialog;


/**
 * Created by Leo.Chang on 2017/5/10.
 */
public class BaseActivity<T extends IBasePresenter>  extends AppCompatActivity {
    protected T mPresenter;

    protected UIProgressDialog mProgressDialog;

    protected FragmentManager mFragmentManager;
    protected FragmentTransaction mFragmentTransaction;

    protected RxPermissions permissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getSupportFragmentManager();
        permissions = new RxPermissions(this);

//        if (null != savedInstanceState) {
//            if (!CommonSharedPreferences.isBackgroundTimeout()) {
//                // 如果不是后台在线超时返回，则恢复登录信息
//                hiverApplication.getInstance().hasLogin = savedInstanceState.getBoolean("hasLogin", false);
//                hiverApplication.getInstance().loginVo = savedInstanceState.getParcelable("userInfo");
//            }
//
//            return;
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putBoolean("hasLogin", hiverApplication.getInstance().hasLogin);
//        outState.putParcelable("userInfo", hiverApplication.getInstance().loginVo);
    }

    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    /**
     * 显示加载框
     */
    public void showPregressDialog() {
        if (null == mProgressDialog) {
            mProgressDialog = new UIProgressDialog(this);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissPregressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        if (null != mPresenter) {
            mPresenter.unsubscribe();
        }

        super.onDestroy();
    }

}
