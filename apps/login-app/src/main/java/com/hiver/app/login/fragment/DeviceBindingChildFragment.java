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
import com.seaway.hiver.biz.login.contract.DeviceBindingContract;
import com.seaway.hiver.biz.login.presenter.DeviceBindingPresenter;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;


/**
 * 绑定设备
 * Created by Leo.Chang on 2017/5/15.
 */
public class DeviceBindingChildFragment extends BaseFragment<DeviceBindingContract.DeviceBindingPresenter> implements DeviceBindingContract.DeviceBindingView {
    EditText smsCodeEdit;
    UIButtonSmsCountDown smsCountDownButton;
    EditText aliasEdit;

    String codeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DeviceBindingPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_device_binding_child, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.first_login_sec_setting_next_button) {
            // 下一步
            if (verifyParam()) {
                mPresenter.bindDevice(aliasEdit.getText().toString(), codeId, smsCodeEdit.getText().toString());
            }
        } else if (v.getId() ==R.id.ui_view_get_sms_code_button) {
            // 获取短信验证码
            mPresenter.requestSmsCode("", "2011", "", "", "", "");
        }
    }

    @Override
    public void bindDeviceSuccess() {
        ((FirstLoginSecSettingFragment) getParentFragment()).setStep(1);
    }

    @Override
    public void getSmsCodeSuccess(RequestSmsCodeVo vo) {
        smsCountDownButton.setEnabled(false);
        Toast.makeText(getActivity(), String.format(getString(R.string.common_tips_1001),vo.getMobileNo()), Toast.LENGTH_SHORT).show();
        this.codeId = vo.getSmsCodeId();
    }

    private void getViews() {
        aliasEdit = (EditText) getView().findViewById(R.id.first_login_sec_setting_alias_edit_text);
        smsCodeEdit = (EditText) getView().findViewById(R.id.ui_view_get_sms_code_edit_text);
        smsCountDownButton = (UIButtonSmsCountDown) getView().findViewById(R.id.ui_view_get_sms_code_button);

        setOnClickListener();
    }

    private void setOnClickListener() {
        smsCountDownButton.setOnClickListener(this);
        getView().findViewById(R.id.first_login_sec_setting_next_button).setOnClickListener(this);
    }

    private boolean verifyParam() {
        if (TextUtils.isEmpty(aliasEdit.getText())) {
            UIToast.showToast(getActivity(), "请输入设备别名");
            return false;
        }
        if (aliasEdit.getText().length() > 10) {
            UIToast.showToast(getActivity(), "设备别名最多10个字，请重新输入");
            return false;
        }
        if (TextUtils.isEmpty(codeId)) {
            UIToast.showToast(getActivity(), "请先获取短信验证码");
            return false;
        }
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