package com.seaway.hiver.teacher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.seaway.hiver.teacher.util.VoiceUtil;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 关闭音频播放
        VoiceUtil.getInstance().stopPlay();
        if (null != getIntent() && null != getIntent().getStringExtra("redirectURL")) {

        }
    }

}
