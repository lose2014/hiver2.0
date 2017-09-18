/**	
 * Copyright (c) 2014-2020  Seaway I.T. Co, Ltd, All Rights Reserved.
 *		This source code contains CONFIDENTIAL INFORMATION and 
 *		TRADE SECRETS of Seaway I.T, Co, Ltd. ANY REPRODUCTION,
 * 		DISCLOSURE OR USE IN WHOLE OR IN PART is EXPRESSLY PROHIBITED, 
 * 		except as may be specifically authorized by prior written 
 *		agreement or permission by Seaway I.T, Co, Ltd.
 *
 *		THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE, The copyright 
 *		notice above does not evidence any actual or intended publication
 *		of such source code. 	
 * ClassName：DefaultConfirmDialog.java
 * Description：
 * 	默认的确认弹出框
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-8
 * @since 2.3
 * @author changtao
 */
package com.seaway.common.apps.qrcode.scan.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.seaway.common.qrcode.R;


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
	 * 确定
	 */
	public static final int CONFIRM_DIALOG_RESULT_OK = 0;

	/**
	 * 取消
	 */
	public static final int CONFIRM_DIALOG_RESULT_CANCEL = 1;

	/**
	 * 提示内容
	 */
	private TextView uiDialogMessage;
	
	/**
	 * 复选框
	 */
	private CheckBox uiDialogCheckbox;

	/**
	 * 左边确定按钮
	 */
	private Button positiveButton;

	/**
	 * 右边取消按钮
	 */
	private Button negativeButton;
	
	
	private LinearLayout contentLayout;
	
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

		uiDialogMessage = (TextView) view.findViewById(R.id.ui_dialog_message);
		uiDialogCheckbox = (CheckBox) view.findViewById(R.id.ui_dialog_checkbox);
		positiveButton = (Button) view
				.findViewById(R.id.ui_default_confirm_dialog_positive_button);
		contentLayout = (LinearLayout) view
				.findViewById(R.id.dialog_content_main);
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

		// Log.i("DefineDialog.width:" + params.width);
		// if (params.width <= 0)
		// params.width = ViewGroup.LayoutParams.WRAP_CONTENT * 7 / 8;

		setContentView(view, params);
	}

	public void setView(View view) {
		contentLayout.removeAllViews();
		contentLayout.addView(view);
	}
	
	public void setView(View view, LayoutParams param) {
		contentLayout.removeAllViews();
		contentLayout.addView(view, param);
	}

	public TextView getUiDialogMessage() {
		return uiDialogMessage;
	}

	public CheckBox getUiDialogCheckbox() {
		return uiDialogCheckbox;
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
}