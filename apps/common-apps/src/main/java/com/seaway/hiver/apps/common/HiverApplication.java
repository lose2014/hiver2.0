package com.seaway.hiver.apps.common;

import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.apps.common.util.CrashHandler;

public class HiverApplication extends Application {

    /**
     * 应用在 是否在前台
     */
    public boolean isForeground;
    public String clientId;
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化Crash捕捉工具
        CrashHandler.getInstance().init();
        isForeground = true;
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    public static HiverApplication getInstance() {
        return (HiverApplication) application;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}