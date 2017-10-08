package com.hiver.app.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hiver.app.login.R;
import com.seaway.hiver.apps.common.fragment.BaseFragment;


/**
 * 首次登陆安全设置
 * Created by Leo.Chang on 2017/5/15.
 */
public class FirstLoginSecSettingFragment extends BaseFragment {

    ImageView progressImageView;

    private int step;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            step = savedInstanceState.getInt("step", 0);
            return;
        }
        step = getArguments().getInt("step",0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_login_sec_setting, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();

        setStep(step);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("step", step);
    }

    /**
     * 设置步骤
     *
     * @param step 0：设备绑定；1：登录密码修改；2：设置成功
     */
    protected void setStep(int step) {
        this.step = step;

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        switch (step) {
//            case 0: {
//                t.replace(R.id.first_login_sec_setting_frame_layout, new DeviceBindingChildFragment(), "DeviceBindingChildFragment");
////                t.addToBackStack("DeviceBindingChildFragment");
//                t.commit();
//                progressImageView.setImageResource(R.drawable.first_login_sec_setting_progress_1);
//            }
//            break;
//            case 1: {
//                t.replace(R.id.first_login_sec_setting_frame_layout, new PasswdModifyChildFragment(), "PasswdModifyChildFragment");
//                t.commit();
//                progressImageView.setImageResource(R.drawable.first_login_sec_setting_progress_2);
//            }
//            break;
//            default: {
//                t.replace(R.id.first_login_sec_setting_frame_layout, new SettingCompleteChildFragment(), "PasswdModifyChildFragment");
//                t.commit();
//                progressImageView.setImageResource(R.drawable.first_login_sec_setting_progress_3);
//            }
        }
    }

    /**
     * 获得View实例
     */
    private void getViews() {
//        progressImageView = (ImageView) getView().findViewById(R.id.first_login_sec_setting_top_image_view);
    }
}