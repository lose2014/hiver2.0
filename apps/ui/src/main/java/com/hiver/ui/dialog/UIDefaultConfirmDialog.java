/**
 * Copyright (c) 2014-2020  Seaway I.T. Co, Ltd, All Rights Reserved.
 * This source code contains CONFIDENTIAL INFORMATION and
 * TRADE SECRETS of Seaway I.T, Co, Ltd. ANY REPRODUCTION,
 * DISCLOSURE OR USE IN WHOLE OR IN PART is EXPRESSLY PROHIBITED,
 * except as may be specifically authorized by prior written
 * agreement or permission by Seaway I.T, Co, Ltd.
 * <p>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE, The copyright
 * notice above does not evidence any actual or intended publication
 * of such source code.
 * ClassName：DefaultConfirmDialog.java
 * Description：
 * 默认的确认弹出框
 * Reporting Bugs：
 * Modification history：
 *
 * @version 1.0 2014-4-8
 * @author changtao
 * @since 2.3
 */
package com.hiver.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hiver.ui.R;

/**
 *
 * 默认的确认弹出框<br>
 * 可包含确定和取消按钮
 *
 * @version 1.0 2014-4-17
 * @since 2.3
 * @author changtao
 *
 */
public class UIDefaultConfirmDialog extends Dialog {
    /**
     * 标题
     */
    private TextView uiDialogTitle;

    /**
     * 提示内容
     */
    private TextView uiDialogMessage;

    /**
     * 左边确定按钮
     */
    private Button positiveButton;

    /**
     * 右边取消按钮
     */
    private Button negativeButton;

    /**
     * 右边按钮
     */
    private LinearLayout negativeLayout;


    public UIDefaultConfirmDialog(Context context) {
        super(context, R.style.UIDefaultConfirmDialogTheme);
        // TODO Auto-generated constructor stub
        this.setCancelable(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initDialog();
    }

    /**
     * 初始化弹出框
     */
    @SuppressLint("InflateParams")
    private void initDialog() {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.ui_default_confirm_dialog, null);

        uiDialogTitle = (TextView) view.findViewById(R.id.ui_dialog_title);
        uiDialogMessage = (TextView) view.findViewById(R.id.ui_dialog_message);

        positiveButton = (Button) view
                .findViewById(R.id.ui_default_confirm_dialog_positive_button);
        negativeLayout = (LinearLayout) view.findViewById(R.id.ui_default_confirm_dialog_negative_layout);

        positiveButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }

        });

        negativeButton = (Button) view
                .findViewById(R.id.ui_default_confirm_dialog_negative_button);

        DisplayMetrics metric = getContext().getResources().getDisplayMetrics();

        LayoutParams params = new LayoutParams(
                metric.widthPixels * 7 / 8,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        setContentView(view, params);
    }

    public TextView getUiDialogMessage() {
        return uiDialogMessage;
    }

    public Button getPositiveButton() {
        return positiveButton;
    }

    public Button getNegativeButton() {
        return negativeButton;
    }

    public LinearLayout getNegativeLayout() {
        return negativeLayout;
    }

    public TextView getUiDialogTitle() {
        return uiDialogTitle;
    }
}