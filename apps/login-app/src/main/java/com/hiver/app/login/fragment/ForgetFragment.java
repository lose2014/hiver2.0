package com.hiver.app.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hiver.app.login.R;
import com.hiver.ui.toast.UIToast;
import com.seaway.android.ui.UIButtonSmsCountDown;

import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.biz.login.contract.ForgetContract;
import com.seaway.hiver.biz.login.presenter.ForgetPresenter;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;


/**
 * 忘记密码
 * Created by Leo.Chang on 2017/5/13.
 */
public class ForgetFragment extends BaseFragment<ForgetContract.Presenter> implements ForgetContract.View, View.OnClickListener {
    EditText psEdit,psConfirmEdit;
    EditText nameEdit;
    EditText mobileEdit;

    EditText smsCodeEdit;
    UIButtonSmsCountDown smsCountDownButton;

    String codeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ForgetPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.forget_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
    }


    @Override
    public void getSmsCodeSuccess(BaseOutputVo vo) {
        smsCountDownButton.setEnabled(false);
        Toast.makeText(getActivity(), String.format(getString(R.string.common_tips_1001), mobileEdit.getText().toString()), Toast.LENGTH_SHORT).show();
//        this.codeId = vo.getSmsCodeId();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.ui_view_get_sms_code_button) {
            // 获取短信验证码
                if (TextUtils.isEmpty(mobileEdit.getText())) {
                UIToast.showToast(getActivity(), "请输入手机号");
                return;
            }
            if (11 != mobileEdit.getText().length()) {
                UIToast.showToast(getActivity(), "手机号为11位数字，请重新输入");
                return;
            }
            mPresenter.requestSmsCode(mobileEdit.getText().toString(), "13", "", "", "", "");
        } else if (v.getId() == R.id.forget_portal_comfirm_button) {
            // 确定
            if (!verifyParam()) {
                return;
            }

            mPresenter.requestResetPwd(mobileEdit.getText().toString(),  smsCodeEdit.getText().toString(),psEdit.getText().toString(),psConfirmEdit.getText().toString());

            mobileEdit.setText("");
//            idNoEdit.setText("");
//            nameEdit.setText("");
//            codeId = null;
            smsCodeEdit.setText("");
        }
    }

    private void getViews() {
        mobileEdit = (EditText) getView().findViewById(R.id.forget_portal_mobile_edit_text);

        smsCountDownButton = (UIButtonSmsCountDown) getView().findViewById(R.id.ui_view_get_sms_code_button);
        smsCodeEdit = (EditText) getView().findViewById(R.id.ui_view_get_sms_code_edit_text);
        psEdit =(EditText)  getView().findViewById(R.id.forget_portal_new_pwd_edit_text);
        psConfirmEdit =(EditText)  getView().findViewById(R.id.forget_portal_confirm_pwd_edit_text);
        setOnClickListener();
    }

    private void setOnClickListener() {
        getView().findViewById(R.id.forget_portal_comfirm_button).setOnClickListener(this);
        smsCountDownButton.setOnClickListener(this);
    }

    private boolean verifyParam() {
        if (TextUtils.isEmpty(mobileEdit.getText())) {
            UIToast.showToast(getActivity(), "请输入手机号");
            return false;
        }
        if (11 != mobileEdit.getText().length()) {
            UIToast.showToast(getActivity(), "手机号为11位数字，请重新输入");
            return false;
        }
//        if (TextUtils.isEmpty(codeId)) {
//            UIToast.showToast(getActivity(), "请先获取短信验证码");
//            return false;
//        }
        if (TextUtils.isEmpty(smsCodeEdit.getText())) {
            UIToast.showToast(getActivity(), "请输入短信验证码");
            return false;
        }
        if (6 != smsCodeEdit.getText().length()) {
            UIToast.showToast(getActivity(), "请输入6位短信验证码");
            return false;
        }

        return true;
    }


    @Override
    public void requestResetPwdSuccess() {

    }


    @Override
    public void checkSuccess(BaseOutputVo vo) {

    }
}