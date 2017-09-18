package com.hiver.ui.toast;

import android.content.Context;

/**
 * Created by Leo.Chang on 2017/5/11.
 */

public class UIToast {
    private static android.widget.Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast(Context context, CharSequence msg) {
        if (null == mToast) {
            mToast = android.widget.Toast.makeText(context, msg,
                    android.widget.Toast.LENGTH_LONG);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
