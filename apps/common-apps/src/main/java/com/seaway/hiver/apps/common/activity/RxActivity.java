package com.seaway.hiver.apps.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.seaway.hiver.common.biz.IBasePresenter;

import io.reactivex.Observable;

/**
 * User: Howard chen(howardchen@mooyoo.com.cn)
 * Date: 2017/2/8
 * Time: 下午7:42
 * Desc:
 */
public class RxActivity<T extends IBasePresenter> extends BaseActivity<T> {
    public static final String ParcelableKey = "RxActivity_ParcelableKey";

    public static Observable<ActivityBackWrapper> startActivityForResult(FragmentActivity activity, Intent intent, int requestCode) {
        final String TAG = "RxActivity_Fragment_TAG";
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        AssistFragment assistFragment = (AssistFragment) fragmentManager.findFragmentByTag(TAG);
        if (assistFragment == null) {
            assistFragment = new AssistFragment();
            fragmentManager.beginTransaction().add(assistFragment, TAG).commit();
            fragmentManager.executePendingTransactions();
        }
        return assistFragment.startRxActivity(intent, requestCode);
    }

    public static void finish(Activity activity, String value, int resultCode) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(ParcelableKey, value);
        intent.putExtras(bundle);
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    public static String fetchData(Intent intent) {
        Bundle bundle = intent.getExtras();
        return bundle.getString(ParcelableKey);
    }
}