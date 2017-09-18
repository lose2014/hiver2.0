package com.seaway.hiver.apps.common;

import android.content.Context;

import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.apps.common.util.CrashHandler;

public class HiverApplication extends Application {

    /**
     * 应用在 是否在前台
     */
    public boolean isForeground;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化Crash捕捉工具
        CrashHandler.getInstance().init();
        isForeground = true;
    }

    public static HiverApplication getInstance() {
        return (HiverApplication) application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}