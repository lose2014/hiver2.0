package com.seaway.hiver.model.login.shared;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.seaway.android.ndk.NativeSDK;
import com.seaway.hiver.model.common.shared.MSharedPreferences;


/**
 * Created by Leo.Chang on 2017/5/17.
 */
public class LoginSharedPreferences extends MSharedPreferences {
    private static final String LOGIN_USER_NAME_PERFERENCES_KEY = "login_user_name";

    /**
     * 记住登录账号
     *
     * @param userName 登录账号
     * @return true：保存成功；false：保存失败
     */
    public static boolean saveLoginUserName(String userName) {
        SharedPreferences.Editor editor = prefs.edit();
        if (TextUtils.isEmpty(userName)) {
            editor.remove(LOGIN_USER_NAME_PERFERENCES_KEY);
        } else {
            editor.putString(LOGIN_USER_NAME_PERFERENCES_KEY, NativeSDK.b(userName));
        }

        return editor.commit();
    }

    /**
     * 获取登录账号
     *
     * @return 登录账号
     */
    public static String getLoginUserName() {
        String userName = prefs.getString(LOGIN_USER_NAME_PERFERENCES_KEY, "");
        return TextUtils.isEmpty(userName) ? "" : NativeSDK.c(userName);
    }
}
