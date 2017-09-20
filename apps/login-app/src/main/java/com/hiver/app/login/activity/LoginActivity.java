package com.hiver.app.login.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hiver.app.login.R;
import com.hiver.app.login.fragment.LoginFragment;
import com.seaway.hiver.apps.common.activity.BaseActivity;
import com.seaway.hiver.apps.common.util.BackPressedHandler;


/**
 * 登录、注册、忘记密码界面容器
 * Created by Leo.Chang on 2017/5/13.
 */
public class LoginActivity extends BaseActivity {
    public static final int REQUEST_LOGIN_CODE = 0x7149;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_login);

        if (null == savedInstanceState) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.login_fragment_content, new LoginFragment(), "LoginFragment");
            mFragmentTransaction.addToBackStack("LoginFragment");
            mFragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (1 == mFragmentManager.getBackStackEntryCount()) {
            // 如果栈中只有1个Fragment，则说明在登录界面，点击退出登录界面
            finish();
        } else {
            if (!BackPressedHandler.handleBackPress(this)) {
                // 如果栈中没有Fragment拦截返回键
                mFragmentManager.popBackStack();
            }
        }
    }
}