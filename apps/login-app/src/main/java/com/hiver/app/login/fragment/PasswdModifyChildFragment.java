package com.hiver.app.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.hiver.app.login.R;
import com.hiver.ui.spinner.UISelectionBoxView;
import com.hiver.ui.toast.UIToast;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.biz.login.contract.LoginPwdModifyContract;
import com.seaway.hiver.biz.login.presenter.LoginPwdModifyPresenter;
import com.seaway.pinpad.main.SWPINPadEdit;


/**
 * 修改登录密码
 * Created by Leo.Chang on 2017/5/15.
 */

public class PasswdModifyChildFragment extends BaseFragment<LoginPwdModifyContract.Presenter> implements LoginPwdModifyContract.View {
    EditText oldPwdEdit;
    EditText newPwdEdit;
    EditText confirmPwdEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoginPwdModifyPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_password_modify_child, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getViews();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.first_login_sec_setting_next_button) {
            // 设置
            if (verifyParam()) {
                mPresenter.requestLoginPwdModify(oldPwdEdit.getText().toString(), newPwdEdit.getText().toString());
            }
        }
    }

    @Override
    public void modifySuccess() {
        ((FirstLoginSecSettingFragment) getParentFragment()).setStep(2);
    }

    private void getViews() {
//        idTypeText = (UISelectionBoxView) getView().findViewById(R.id.first_login_sec_setting_id_type_text_view);
//        idNoEdit = (EditText) getView().findViewById(R.id.first_login_sec_setting_id_no_edit_text);

        oldPwdEdit = (EditText) getView().findViewById(R.id.first_login_sec_setting_old_pwd_edit_text);
//        oldPwdEdit.setEncryptionType(SWPINPadEdit.SWKeyboardEncryptionTypeLogin);
//        oldPwdEdit.setShowHighlighted(HiverApplication.getInstance().isShowHighlight);

        newPwdEdit = (EditText) getView().findViewById(R.id.first_login_sec_setting_new_pwd_edit_text);
//        newPwdEdit.setEncryptionType(SWPINPadEdit.SWKeyboardEncryptionTypeLogin);
//        newPwdEdit.setShowHighlighted(HiverApplication.getInstance().isShowHighlight);

        confirmPwdEdit = (EditText) getView().findViewById(R.id.first_login_sec_setting_confirm_pwd_edit_text);
//        confirmPwdEdit.setEncryptionType(SWPINPadEdit.SWKeyboardEncryptionTypeLogin);
//        confirmPwdEdit.setShowHighlighted(HiverApplication.getInstance().isShowHighlight);

        setOnClickListener();
    }

    private void setOnClickListener() {
        getView().findViewById(R.id.first_login_sec_setting_next_button).setOnClickListener(this);
    }

    private boolean verifyParam() {

        if (0 == oldPwdEdit.getText().toString().length()) {
            UIToast.showToast(getActivity(), "请输入原密码");
            return false;
        }
        if (0 == newPwdEdit.getText().toString().length()) {
            UIToast.showToast(getActivity(), "请输入新密码");
            return false;
        }
        if (newPwdEdit.getText().toString().length() < 6 || newPwdEdit.getText().toString().length() > 20) {
            UIToast.showToast(getActivity(), "新密码长度为6-20位，必须包含字母和数字，请重新输入");
            return false;
        }
        if (0 == confirmPwdEdit.getText().toString().length()) {
            UIToast.showToast(getActivity(), "请再次输入新密码");
            return false;
        }
        if (!newPwdEdit.getText().toString().equalsIgnoreCase(confirmPwdEdit.getText().toString())) {
            UIToast.showToast(getActivity(), "两次输入的新密码不一致，请重新输入");
            return false;
        }

        return true;
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
