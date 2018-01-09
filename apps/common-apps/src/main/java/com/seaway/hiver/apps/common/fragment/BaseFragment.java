package com.seaway.hiver.apps.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.HiverApplication;

import com.seaway.hiver.apps.common.shared.CommonSharedPreferences;
import com.seaway.hiver.common.biz.IBasePresenter;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;
import com.seaway.hiver.model.common.exception.ConnectionException;
import com.seaway.hiver.apps.common.R;
import com.hiver.ui.dialog.UIDefaultDialogHelper;
import com.hiver.ui.dialog.UIProgressDialog;
import com.hiver.ui.toast.UIToast;

/**
 * Fragment基类
 * Created by Leo.Chang on 2017/5/10.
 */
public class BaseFragment<T extends IBasePresenter> extends Fragment implements View.OnClickListener {
    // 账号登录标识
    protected static final int ACCOUNT_LOGIN_REQUEST_CODE = 0x3977;
    // 手势密码登录标识
    protected static final int GESTURE_LOGIN_REQUEST_CODE = 0x8366;

    protected T mPresenter;

    protected FragmentManager mFragmentManager;
    protected UIProgressDialog mProgressDialog;

    public void setPresenter(T presenter) {

        this.mPresenter = presenter;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != getView() && null != getView().findViewById(R.id.ui_navigation_bar_back_button)) {
            getView().findViewById(R.id.ui_navigation_bar_back_button).setOnClickListener(this);
        }
    }

    /**
     * 显示加载框
     */
    public void showPregressDialog() {
        if (null == mProgressDialog) {
            mProgressDialog = new UIProgressDialog(getActivity());
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void showProgess(int num){
//        mProgressDialog.setProcess(num);
        mProgressDialog.getProgressBar().setProgress(num);
        mProgressDialog.getTv().setText("正在下载中！("+num+"%)");
    }

    /**
     * 隐藏加载框
     */
    public void dismissPregressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 错误处理
     *
     * @param e
     */
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ConnectionException) {
            ConnectionException ex = (ConnectionException) e;
            if ("200005".equalsIgnoreCase(ex.code)) {
                // 登录超时
                UIDefaultDialogHelper.showDefaultAlert(getActivity(), ex.message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UIDefaultDialogHelper.dialog.dismiss();
                        UIDefaultDialogHelper.dialog = null;
                        HiverApplication.getInstance().logout();
                        mFragmentManager.popBackStack("portal", 0);
                    }
                });
            } else {
                UIDefaultDialogHelper.showDefaultAlert(getActivity(), ex.message);
            }

//            UIToast.showToast(getActivity(), ex.message);
        }else{
            Logger.e(e.getMessage());
            UIDefaultDialogHelper.showDefaultAlert(getActivity(), e.getMessage(),new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    UIDefaultDialogHelper.dialog.dismiss();
                    UIDefaultDialogHelper.dialog = null;
                    getActivity().finish();
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ui_navigation_bar_back_button) {
            mFragmentManager.popBackStack();
        }
    }

    /**
     * 当应用切换到后台之后，用于判断界面是否需要返回到应用主界面； 如果当前fragment需要登录后才能使用，则该方法必须返回true;<br>
     * 如果当前fragment不需要登录就能使用，且该fragment之前的fragment都不需要登录就能使用，则重写该方法并返回false<br>
     * 如果当前fragment不需要登录就能使用，且该fragment之前的fragment需要登录就能使用，则该方法必须返回true
     *
     * @return true：登录超时后，界面需要返回到首界面；<br>
     * false：登录超时后，界面不需要处理
     */
    protected boolean needsGoBackWhenIsLogout() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonSharedPreferences.isBackgroundTimeout() && !HiverApplication.getInstance().hasLogin) {
            // 如果已经退出登录，且需要回退到首页，则回退
            mFragmentManager.popBackStack("portal", 0);
            CommonSharedPreferences.saveIsBackgroundTimeout(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("super---------------onActivityResult");
//        if (requestCode == GESTURE_LOGIN_REQUEST_CODE) {
//            // 如果是手势密码登录返回
//            if (resultCode == Activity.RESULT_FIRST_USER) {
//                // 使用其它账号登录
//                if (null != data && null != data.getParcelableExtra("checkResourceVo") && data.getParcelableExtra("checkResourceVo") instanceof CheckResourceVo) {
//                    // 如果有资源监测结果
//                    accountLogin((CheckResourceVo) data.getParcelableExtra("checkResourceVo"));
//                } else {
//                    accountLogin();
//                }
//            } else if (resultCode == Activity.RESULT_OK) {
//                // 手势密码登录成功
//                if (null != data && null != data.getParcelableExtra("checkResourceVo") && data.getParcelableExtra("checkResourceVo") instanceof CheckResourceVo) {
//                    CheckResourceVo vo = data.getParcelableExtra("checkResourceVo");
////                    if (Integer.parseInt(Application.getInstance().loginVo.getAccountType()) < Integer.parseInt(vo.getMinAccType())) {
////                        levelLowError();
////                        return;
////                    }
//                }
//                loginSuccess();
//            }
//        } else if (requestCode == ACCOUNT_LOGIN_REQUEST_CODE) {
//            // 如果是账号登录返回
//            if (resultCode == Activity.RESULT_OK) {
//                // 账号登录成功
//                if (null != data && null != data.getParcelableExtra("checkResourceVo") && data.getParcelableExtra("checkResourceVo") instanceof CheckResourceVo) {
//                    CheckResourceVo vo = data.getParcelableExtra("checkResourceVo");
////                    if (Integer.parseInt(Application.getInstance().loginVo.getAccountType()) < Integer.parseInt(vo.getMinAccType())) {
////                        levelLowError();
////                        return;
////                    }
//                }
//                loginSuccess();
//            }
//        }
    }

    /**
     * 跳转到手势密码等登录
     */
    public void gestureLogin() {
        try {
            Class cls = Class.forName("com.xtbank.app.gs.activity.GestureLoginActivity");
            Intent intent = new Intent(getActivity(), cls);
            startActivityForResult(intent, GESTURE_LOGIN_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账号登录
     */
    public void accountLogin() {
        try {
            Class cls = Class.forName("com.xtbank.app.login.activity.LoginActivity");
            Intent intent = new Intent(getActivity(), cls);
            startActivityForResult(intent, ACCOUNT_LOGIN_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到手势密码等登录
     */
    public void gestureLogin(CheckResourceVo vo) {
        try {
            Class cls = Class.forName("com.xtbank.app.gs.activity.GestureLoginActivity");
            Intent intent = new Intent(getActivity(), cls);
            intent.putExtra("checkResourceVo", vo);
            startActivityForResult(intent, GESTURE_LOGIN_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 账号登录
     */
    public void accountLogin(CheckResourceVo vo) {
        try {
            Class cls = Class.forName("com.xtbank.app.login.activity.LoginActivity");
            Intent intent = new Intent(getActivity(), cls);
            intent.putExtra("checkResourceVo", vo);
            startActivityForResult(intent, ACCOUNT_LOGIN_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录成功回调
     */
    public void loginSuccess() {
    }

    /**
     * 不需要登录资源跳转
     */
    public void gotoWithoutLogin() {
    }

    /**
     * 用户等级不够
     */
    public void levelLowError() {
        UIDefaultDialogHelper.showDefaultAlert(getActivity(), "手机银行大众版用户不支持该功能，若需使用请至柜台开通手机银行专业版");
    }

    @Override
    public void onDestroy() {
        if (null != mPresenter) {
            mPresenter.unsubscribe();
        }

        super.onDestroy();
    }

    /**
     * 添加Fragment
     *
     * @param fragment  添加的Fragment
     * @param resLayout Fragment的容器
     * @param tag       Fragment的标识
     */
    protected void addFragment(Fragment fragment, int resLayout, String tag) {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        t.hide(this).add(resLayout, fragment, tag);
        t.addToBackStack(tag);
        t.commit();
    }

    /**
     * 添加Fragment
     *
     * @param fragment 添加的Fragment
     * @param tag      Fragment的标识
     */
    protected void addFragment(Fragment fragment, String tag) {
        FragmentTransaction t = mFragmentManager.beginTransaction();
        t.hide(this).add(R.id.main_fragment_content, fragment, tag);
        t.addToBackStack(tag);
        t.commit();
    }

    /**
     * UIWebViewFragment
     *
     * @param url webview地址
     */
    protected void addWebViewFragment(String url) {
        try {
            Class<Fragment> cls = (Class<Fragment>) Class.forName("com.seaway.bank.app.fragment.UIWebViewFragment");
            Fragment fragment = cls.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            fragment.setArguments(bundle);
            FragmentTransaction t = mFragmentManager.beginTransaction();
            t.hide(this).add(R.id.main_fragment_content, fragment, "UIWebViewFragment");
            t.addToBackStack("UIWebViewFragment");
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}