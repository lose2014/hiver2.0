package com.seaway.hiver.teacher;

import android.os.Bundle;

import com.seaway.hiver.apps.common.activity.BaseActivity;
import com.seaway.hiver.apps.common.util.BackPressedHandler;
import com.seaway.hiver.main.teacher.apps.fragment.TMainFragment;
import com.seaway.hiver.teacher.util.VoiceUtil;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 关闭音频播放
//        VoiceUtil.getInstance().stopPlay();
        if (null != getIntent() && null != getIntent().getStringExtra("redirectURL")) {

        }
        if (null == savedInstanceState) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.main_fragment_content, new TMainFragment(), "TMainFragment");
            mFragmentTransaction.addToBackStack("TMainFragment");
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
