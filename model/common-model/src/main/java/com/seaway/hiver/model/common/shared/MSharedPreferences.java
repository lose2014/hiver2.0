package com.seaway.hiver.model.common.shared;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.seaway.android.ndk.NativeSDK;
import com.seaway.android.sdk.SWApplication;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;

/**
 * Created by Leo.Chang on 2017/5/17.
 */
public class MSharedPreferences {
    protected final static android.content.SharedPreferences prefs = SWApplication.getInstance().getApplicationContext().getSharedPreferences("BANK_SHARED_PREFERENCES", 0);

    private static final String LOGIN_USER_INFO_PERFERENCES_KEY = "login_user_info";

    private static final String ADVERT_INFO_PERFERENCES_KEY = "advert_info";

    /**
     * 记住登录用户信息
     *
     * @param userInfo 登录账号
     * @return true：保存成功；false：保存失败
     */
    public static boolean saveLoginUserInfo(String userInfo) {
        SharedPreferences.Editor editor = prefs.edit();
        if (TextUtils.isEmpty(userInfo)) {
            editor.remove(LOGIN_USER_INFO_PERFERENCES_KEY);
        } else {
            editor.putString(LOGIN_USER_INFO_PERFERENCES_KEY, NativeSDK.b(userInfo));
        }

        return editor.commit();
    }

    /**
     * 获取登录用户信息
     *
     * @return 登录账号
     */
    public static String getLoginUserInfo() {
        String userInfo = prefs.getString(LOGIN_USER_INFO_PERFERENCES_KEY, "");
        return TextUtils.isEmpty(userInfo) ? "" : NativeSDK.c(userInfo);
    }

    /**
     * 保存广告信息
     *
     * @param advertTypeId 广告位编号
     * @param vo           广告信息
     * @return
     */
    public static boolean saveAdvertInfo(String advertTypeId, QueryAdvertListVo vo) {
        SharedPreferences.Editor editor = prefs.edit();
        if (null == vo) {
            editor.remove(ADVERT_INFO_PERFERENCES_KEY + advertTypeId);
        } else {
            editor.putString(ADVERT_INFO_PERFERENCES_KEY + advertTypeId, NativeSDK.b(new Gson().toJson(vo)));
        }

        return editor.commit();
    }

    /**
     * 获取缓存的广告信息
     *
     * @param advertTypeId 广告位编号
     * @return 广告位信息
     */
    public static QueryAdvertListVo getAdvertInfo(String advertTypeId) {
        String advertInfo = prefs.getString(ADVERT_INFO_PERFERENCES_KEY + advertTypeId, "");

        return TextUtils.isEmpty(advertInfo) ? new QueryAdvertListVo("0") : new Gson().fromJson(NativeSDK.c(advertInfo), QueryAdvertListVo.class);
    }
}