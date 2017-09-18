package com.hiver.app.vc.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiver.app.vc.R;


/**
 * Created by Leo.Chang on 2017/6/10.
 */

public class CheckVersionDialog extends Dialog {
    TextView versionInfoText;
    TextView versionContentText;
    Button leftButton;
    Button rightButton;
    LinearLayout rightLayout;

    public CheckVersionDialog(@NonNull Context context) {
        super(context, R.style.UIDefaultConfirmDialogTheme);
        this.setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initDialog();
    }

    /**
     * 初始化弹出框
     */
    private void initDialog() {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.check_version_view, null);

        versionInfoText = (TextView) view.findViewById(R.id.check_version_size_text_view);
        versionContentText = (TextView) view.findViewById(R.id.check_version_content_text_view);
        leftButton = (Button) view.findViewById(R.id.check_version_left_button);
        rightButton = (Button) view.findViewById(R.id.check_version_right_button);
        rightLayout = (LinearLayout) view.findViewById(R.id.check_version_right_button_layout);

        DisplayMetrics metric = getContext().getResources().getDisplayMetrics();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                metric.widthPixels * 7 / 8,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        setContentView(view, params);
    }
}
