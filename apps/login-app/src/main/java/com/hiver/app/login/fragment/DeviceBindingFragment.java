package com.hiver.app.login.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.biz.login.contract.DeviceBindingContract;
import com.seaway.hiver.biz.login.presenter.DeviceBindingPresenter;
import com.seaway.hiver.model.common.data.vo.CheckResourceVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;


/**
 * 设备绑定
 * Created by Leo.Chang on 2017/5/18.
 */
public class DeviceBindingFragment extends BaseFragment<DeviceBindingContract.DeviceBindingPresenter> implements DeviceBindingContract.DeviceBindingView {

    EditText smsCodeEdit;
    UIButtonSmsCountDown smsCountDownButton;
    EditText aliasEdit;

    String codeId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 注入Presenter
        new DeviceBindingPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_device_binding, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getViews();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.login_device_binding_button) {
            // 绑定设备
            if (verifyParam()) {
                mPresenter.bindDevice(aliasEdit.getText().toString(), codeId, smsCodeEdit.getText().toString());
            }
        } else if (v.getId() == R.id.ui_view_get_sms_code_button) {
            // 获取短信验证码
            mPresenter.requestSmsCode("", "2011", "", "", "", "");
        }
    }

    /**
     * 获取短信验证码成功
     *
     * @param vo 获取短信验证码成功
     */
    @Override
    public void getSmsCodeSuccess(RequestSmsCodeVo vo) {
        smsCountDownButton.setEnabled(false);
        Toast.makeText(getActivity(), String.format(getString(R.string.common_tips_1001),vo.getMobileNo()), Toast.LENGTH_SHORT).show();
        this.codeId = vo.getSmsCodeId();
    }

    @Override
    public void bindDeviceSuccess() {
        HiverApplication.getInstance().hasLogin = true;
        if (null != getActivity().getIntent() && null != getActivity().getIntent().getParcelableExtra("checkResourceVo") && getActivity().getIntent().getParcelableExtra("checkResourceVo") instanceof CheckResourceVo) {
            // 如果有资源监测结果
            Intent intent = new Intent();
            intent.putExtra("checkResourceVo",getActivity().getIntent().getParcelableExtra("checkResourceVo"));
            getActivity().setResult(Activity.RESULT_OK,intent);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }
//        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void getViews() {
        smsCodeEdit = (EditText) getView().findViewById(R.id.ui_view_get_sms_code_edit_text);
        smsCountDownButton = (UIButtonSmsCountDown) getView().findViewById(R.id.ui_view_get_sms_code_button);

        aliasEdit = (EditText) getView().findViewById(R.id.login_alias_edit_text);
        setOnClickListener();
    }

    private void setOnClickListener() {
        getView().findViewById(R.id.login_device_binding_button).setOnClickListener(this);
        smsCountDownButton.setOnClickListener(this);
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