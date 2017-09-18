package com.seaway.hiver.apps.common.shared;

import android.content.SharedPreferences;

import com.seaway.android.sdk.SWApplication;

/**
 * Created by Leo.Chang on 2017/5/22.
 */
public class CommonSharedPreferences {
    private static SharedPreferences prefs = SWApplication.getInstance().getApplicationContext().getSharedPreferences("BANK_SHARED_PREFERENCES", 0);

    private static final String BACKGROUND_TIMEOUT_PERFERENCES_KEY = "background_timeout";

    /**
     * 记住是否后台在线超时
     *
     * @param flag true：后台在线超时；
     * @return true：保存成功；false：保存失败
     */
    public static boolean saveIsBackgroundTimeout(boolean flag) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(BACKGROUND_TIMEOUT_PERFERENCES_KEY, flag);

        return editor.commit();
    }

    /**
     * 获取是否后台在线超时
     *
     * @return true：后台在线超时；
     */
    public static boolean isBackgroundTimeout() {
        return prefs.getBoolean(BACKGROUND_TIMEOUT_PERFERENCES_KEY, false);
    }
}
