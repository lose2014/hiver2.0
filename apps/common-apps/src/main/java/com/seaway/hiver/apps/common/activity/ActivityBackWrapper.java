package com.seaway.hiver.apps.common.activity;

import android.content.Intent;

import java.io.Serializable;

/**
 * User: Howard chen(howardchen@mooyoo.com.cn)
 * Date: 2017/2/8
 * Time: 下午7:43
 * Desc:
 */
public class ActivityBackWrapper implements Serializable {
    private Intent intent;
    private int requestCode;
    private int resultCode;

    public ActivityBackWrapper() {
    }

    public ActivityBackWrapper(Intent intent, int requestCode, int resultCode) {
        this.intent = intent;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
