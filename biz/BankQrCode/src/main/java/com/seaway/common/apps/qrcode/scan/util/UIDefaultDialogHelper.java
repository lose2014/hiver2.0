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
 * ClassName：CheckVersionHelper.java
 * Description：
 * 	默认弹出框帮助类，实现各种通用弹出框的实现
 * Reporting Bugs：
 * Modification history：
 * @version 1.0 2014-4-28
 * @since 2.3
 * @author changtao
 */
package com.seaway.common.apps.qrcode.scan.util;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.seaway.common.qrcode.R;


/**
 * 
 * 
 * 
 * @version 1.0 2014-4-28
 * @since 2.3
 * @author changtao
 * 
 */
public class UIDefaultDialogHelper {
	public static UIDefaultConfirmDialog dialog;

	/**
	 * 默认提示弹出框<br>
	 * 只包含提示内容及确定按钮<br>
	 * 确定按钮仅隐藏弹出框
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 */
	public static void showDefaultAlert(Context context, String msg) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getPositiveButton().setTextColor(
				context.getResources().getColor(
						R.color.ui_default_confirm_dialog_button_text_color));
		dialog.getPositiveButton().setText("确定");
		dialog.getPositiveButton().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

		dialog.show();
	}

	/**
	 * 默认提示弹出框<br>
	 * 只包含提示内容及确定按钮<br>
	 * 确定按钮仅隐藏弹出框
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 */
	public static void showDefaultAlert(Context context, String msg,String buttonTitle,OnClickListener listener) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getPositiveButton().setTextColor(
				context.getResources().getColor(
						R.color.ui_default_confirm_dialog_button_text_color));
		dialog.getPositiveButton().setText(buttonTitle);
		if (null == listener) {
			dialog.getPositiveButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dialog = null;
						}
					});
		} else {
			dialog.getPositiveButton().setOnClickListener(listener);
		}

		dialog.show();
	}

	/**
	 * 默认提示弹出框<br>
	 * 只包含提示内容及确定按钮<br>
	 * 确定按钮默认仅隐藏弹出框
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param listener
	 *            确定按钮点击事件
	 *
	 */
	public static void showDefaultAlert(Context context, String msg,
			OnClickListener listener) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getPositiveButton().setTextColor(
				context.getResources().getColor(
						R.color.ui_default_confirm_dialog_button_text_color));
		dialog.getPositiveButton().setText("确定");

		if (null == listener) {
			dialog.getPositiveButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dialog = null;
						}
					});
		} else {
			dialog.getPositiveButton().setOnClickListener(listener);
		}

		dialog.show();
	}

	/**
	 * 询问弹出框<br>
	 * 顶部图标为问号，包含左右两个按钮<br>
	 * 左边取消按钮仅隐藏弹出框
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param rightButtonText
	 *            右按钮文字
	 * @param listener
	 *            右边按钮点击监听
	 *
	 */
	public static void showDefaultAskAlert(Context context, String msg,
			String rightButtonText, OnClickListener listener) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);

		dialog.getPositiveButton().setText("取消");
		dialog.getPositiveButton().setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						dialog = null;
					}
				});

		dialog.getNegativeLayout().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setText(rightButtonText);
		dialog.getNegativeButton().setOnClickListener(listener);

		dialog.show();
	}

	/**
	 * 询问弹出框<br>
	 * 顶部图标为问号，包含左右两个按钮<br>
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param leftButtonText
	 *            左按钮文字
	 * @param rightButtonText
	 *            右按钮文字
	 * @param leftListener
	 *            左边按钮点击监听
	 * @param rightListener
	 *            右边按钮点击监听
	 *
	 */
	public static void showDefaultAskAlert(Context context, String msg,
			String leftButtonText,String rightButtonText, OnClickListener leftListener, OnClickListener rightListener) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);

		dialog.getPositiveButton().setText(leftButtonText);
		if(leftListener!=null){
			dialog.getPositiveButton().setOnClickListener(leftListener);
		}else{
			dialog.getPositiveButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							dialog = null;
						}
					});
		}

		dialog.getNegativeLayout().setVisibility(View.VISIBLE);
		dialog.getNegativeButton().setText(rightButtonText);
		dialog.getNegativeButton().setOnClickListener(rightListener);

		dialog.show();
	}

	/**
	 * 操作成功提示框<br>
	 * 顶部图标为对钩，包含一个确定按钮<br>
	 * 确定按钮默认隐藏提示框
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示内容
	 * @param listener
	 *            确定按钮点击监听
	 */
	public static void showDefaultSuccessAlert(Context context, String msg,
			OnClickListener listener) {
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog =null;
		}
		dialog = new UIDefaultConfirmDialog(context);
		dialog.getUiDialogMessage().setText(msg);
		dialog.getPositiveButton().setTextColor(
				context.getResources().getColor(
						R.color.ui_default_confirm_dialog_button_text_color));
		dialog.getPositiveButton().setText("确定");
		if (null != listener) {
			// 如果前端设置了监听
			dialog.getPositiveButton().setOnClickListener(listener);
		} else {
			// 如果前端未设置监听，则默认隐藏提示框
			dialog.getPositiveButton().setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
							dialog = null;
						}
					});
		}

		dialog.show();
	}
}
