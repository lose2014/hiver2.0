package com.hiver.app.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hiver.app.login.R;


/**
 * Created by Leo.Chang on 2017/5/15.
 */
public class SettingCompleteChildFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ui_default_confirm_dialog,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getViews();
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.first_login_sec_setting_relogin_button) {
//            // 重新登录
//            getParentFragment().getFragmentManager().popBackStack();
//        }
    }

    private void getViews() {
//        TextView aliasText = (TextView) getView().findViewById(R.id.first_login_sec_setting_alias_text_view);

        setOnClickListener();
    }

    private void setOnClickListener() {
//        getView().findViewById(R.id.first_login_sec_setting_relogin_button).setOnClickListener(this);
    }
}
