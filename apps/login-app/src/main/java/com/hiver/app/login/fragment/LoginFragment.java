package com.hiver.app.login.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.hiver.app.login.R;
import com.hiver.ui.toast.UIToast;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.toolkit.SWVerificationUtil;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.biz.login.contract.LoginContract;
import com.seaway.hiver.biz.login.presenter.LoginPresenter;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;
import com.seaway.hiver.model.common.data.vo.CheckUserNameVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.pinpad.main.SWPINPadEdit;


/**
 * 登录界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener,TextWatcher {
    EditText mobileEdit;
    EditText pwdEdit;
    EditText codeEdit;
    ImageView codeImageView;
    CheckBox rememberCheckBox;

    private String codeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注入Presenter
        new LoginPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_loading, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            mPresenter.getIconCode();
//        }
    }

    @Override
    public void onDestroy() {
        mobileEdit.removeTextChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().contains("*")) {
            if (0 == count) {
                mobileEdit.setText("");
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_portal_login_button) {
            // 登录
            if (TextUtils.isEmpty(mobileEdit.getText())) {
                UIToast.showToast(getActivity(), "请输入用户名");
                return;
            }
//            if (11 != mobileEdit.getText().length()) {
//                UIToast.showToast(getActivity(), "手机号为11位数字，请重新输入");
//                return;
//            }
            if (0 == pwdEdit.getText().length()) {
                UIToast.showToast(getActivity(), "请输入登录密码");
                return;
            }

//            mPresenter.login(mobileEdit.getText().toString(), pwdEdit.getPassword(1), codeId, codeEdit.getText().toString());

            mPresenter.login(mobileEdit.getText().toString(), pwdEdit.getText().toString());
//            pwdEdit.clear();
            codeEdit.setText("");
        } else if (v.getId() == R.id.login_portal_forget_text_view) {
            // 忘记密码
            addFragment(new ForgetFragment(), R.id.login_fragment_content, "ForgetFragment");
        }
// else if (v.getId() == R.id.login_portal_code_image_view) {
//            // 获取图片验证码
//            mPresenter.getIconCode();
//        } else if (v.getId() == R.id.login_portal_register_text_view) {
//            // 注册
//            addFragment(new RegisterFragment(), R.id.login_fragment_content, "RegisterFragment");
//        }
    }

    @Override
    public void getRememberUserName(String userName) {
        mobileEdit.setText(userName);
//        rememberCheckBox.setChecked(!TextUtils.isEmpty(userName));
    }

    /**
     * 检查用户名是否存在
     * */
    @Override
    public void checkUserNameSuccess(CheckUserNameVo checkUserNameVo) {
        if(SWVerificationUtil.isEmpty(checkUserNameVo.getUsername())){

        }else{
            UIToast.showToast(getActivity(),"用户已存在！");
        }

    }


    @Override
    public void loginSuccess(LoginVo loginVo) {
//        if (rememberCheckBox.isChecked()) {
//            // 记住账号
//            if (!mobileEdit.getText().toString().contains("*")) {
//                mPresenter.rememberUserName(mobileEdit.getText().toString());
//            }
//        } else {
//            // 删除记录的账号
//            mPresenter.rememberUserName("");
//        }

//        if ("1".equalsIgnoreCase(loginVo.getPwdState()) && "1".equalsIgnoreCase(loginVo.getDeviceBindFlag())) {
//            // 如果设备已绑定，且不是初始密码，则登录成功，退出登录界面
//            HiverApplication.getInstance().hasLogin = true;
//            HiverApplication.getInstance().loginVo = loginVo;
//            if (null != getActivity().getIntent() && null != getActivity().getIntent().getParcelableExtra("checkResourceVo") && getActivity().getIntent().getParcelableExtra("checkResourceVo") instanceof CheckResourceVo) {
//                // 如果有资源监测结果
//                Intent intent = new Intent();
//                intent.putExtra("checkResourceVo",getActivity().getIntent().getParcelableExtra("checkResourceVo"));
//                getActivity().setResult(Activity.RESULT_OK,intent);
//            } else {
//                getActivity().setResult(Activity.RESULT_OK);
//            }
//
//            getActivity().finish();
//            return;
//        }
        HiverApplication.getInstance().hasLogin = false;
        HiverApplication.getInstance().loginVo = loginVo;
//        if ("0".equalsIgnoreCase(loginVo.getPwdState()) && "0".equalsIgnoreCase(loginVo.getDeviceBindFlag())) {
//            // 如果设备未绑定，且是初始密码，则跳转到首次登录界面
//            FirstLoginSecSettingFragment fragment = new FirstLoginSecSettingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("step", 0);
//            fragment.setArguments(bundle);
//            addFragment(fragment, R.id.login_fragment_content, "FirstLoginSecSettingFragment");
//            return;
//        }
//        if ("0".equalsIgnoreCase(loginVo.getPwdState()) && "1".equalsIgnoreCase(loginVo.getDeviceBindFlag())) {
//            // 如果设备已绑定，密码为初始密码，则跳转到首次登录界面第二步
//            FirstLoginSecSettingFragment fragment = new FirstLoginSecSettingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putInt("step", 1);
//            fragment.setArguments(bundle);
//            addFragment(fragment, R.id.login_fragment_content, "FirstLoginSecSettingFragment");
//            return;
//        }
//        if ("1".equalsIgnoreCase(loginVo.getPwdState()) && "0".equalsIgnoreCase(loginVo.getDeviceBindFlag())) {
//            // 如果设备未绑定，且不是初始密码，则跳转到设备绑定界面
////            HiverApplication.getInstance().hasLogin = false;
////            HiverApplication.getInstance().loginVo = loginVo;
//            addFragment(new DeviceBindingFragment(), R.id.login_fragment_content, "DeviceBindingFragment");
//            return;
//        }
    }

    @Override
    public void loginFail() {
        // 登录失败时，重新获取图片验证码
        mPresenter.getIconCode();
    }

    @Override
    public void getIconCodeFail() {
        // 获取图片验证码失败，显示重新获取图片
//        codeImageView.setImageResource(R.drawable.login_portal_code_error_image);
    }

    private void getViews() {
        mobileEdit = (EditText) getView().findViewById(R.id.login_portal_mobile_edit_text);
        mobileEdit.addTextChangedListener(this);

//        pwdEdit = (SWPINPadEdit) getView().findViewById(R.id.login_portal_pwd_edit_text);
        pwdEdit = (EditText) getView().findViewById(R.id.login_portal_pwd_edit_text);

//        pwdEdit.setEncryptionType(SWPINPadEdit.SWKeyboardEncryptionTypeLogin);
//        pwdEdit.setShowHighlighted(HiverApplication.getInstance().isShowHighlight);

//        codeEdit = (EditText) getView().findViewById(R.id.login_portal_code_edit_text);

//        codeImageView = (ImageView) getView().findViewById(R.id.login_portal_code_image_view);

//        rememberCheckBox = (CheckBox) getView().findViewById(R.id.login_portal_remember_check_box);

        setOnClickListener();
    }

    private void setOnClickListener() {
//        codeImageView.setOnClickListener(this);
//
        getView().findViewById(R.id.login_portal_login_button).setOnClickListener(this);
        getView().findViewById(R.id.login_portal_forget_text_view).setOnClickListener(this);
//        getView().findViewById(R.id.login_portal_register_text_view).setOnClickListener(this);
    }

    private Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    public void showAutoHidenDialog(int rid) {

    }

    @Override
    public void showUnconnectDialog(String msg) {

    }

    @Override
    public void showOnConnectionDialog(int rid) {

    }

    @Override
    public void disMissConnectingDialog() {

    }
}