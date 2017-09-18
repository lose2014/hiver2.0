package com.hiver.app.vc.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.seaway.hiver.model.common.data.vo.CheckVersionVo;

/**
 * Created by Leo.Chang on 2017/6/10.
 */

public class CheckVersionDialogHelper {
    public static CheckVersionDialog dialog;

    public static void showOptUpdateDialog(Context context, CheckVersionVo vo, View.OnClickListener leftButtonOnClickListener, View.OnClickListener rightButtonOnClickListener) {
        dialog = new CheckVersionDialog(context);
        StringBuilder title = new StringBuilder();
        title.append("更新版本：v").append(vo.getVersion()).append(" 更新大小：").append(vo.getSize()).append("kb");
        Spannable span = new SpannableString(title.toString());
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 5, 6 + vo.getVersion().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), vo.getVersion().length() + 12, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        dialog.versionInfoText.setText(span);
        dialog.versionContentText.setText(vo.getDescription());
        dialog.leftButton.setText("下次再说");
        dialog.leftButton.setOnClickListener(leftButtonOnClickListener);
        dialog.rightButton.setText("立即下载");
        dialog.rightButton.setOnClickListener(rightButtonOnClickListener);
        dialog.show();
    }

    public static void showMustUpdateDialog(Context context, CheckVersionVo vo, View.OnClickListener leftButtonOnClickListener, View.OnClickListener rightButtonOnClickListener) {
        dialog = new CheckVersionDialog(context);
        StringBuilder title = new StringBuilder();
        title.append("更新版本：v").append(vo.getVersion()).append(" 更新大小：").append(vo.getSize()).append("kb");
        Spannable span = new SpannableString(title.toString());
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), 5, 6 + vo.getVersion().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")), vo.getVersion().length() + 12, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        dialog.versionInfoText.setText(span);
        dialog.versionContentText.setText(vo.getDescription());
        dialog.leftButton.setText("退出");
        dialog.leftButton.setOnClickListener(leftButtonOnClickListener);
        dialog.rightButton.setText("立即下载");
        dialog.rightButton.setOnClickListener(rightButtonOnClickListener);
        dialog.show();
    }
}