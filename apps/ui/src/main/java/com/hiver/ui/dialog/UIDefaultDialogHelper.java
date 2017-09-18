/**
 * Copyright (c) 2014-2020  Seaway I.T. Co, Ltd, All Rights Reserved.
 * This source code contains CONFIDENTIAL INFORMATION and
 * TRADE SECRETS of Seaway I.T, Co, Ltd. ANY REPRODUCTION,
 * DISCLOSURE OR USE IN WHOLE OR IN PART is EXPRESSLY PROHIBITED,
 * except as may be specifically authorized by prior written
 * agreement or permission by Seaway I.T, Co, Ltd.
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE, The copyright
 * notice above does not evidence any actual or intended publication
 * of such source code.
 * ClassName：CheckVersionHelper.java
 * Description：
 * 默认弹出框帮助类，实现各种通用弹出框的实现
 * Reporting Bugs：
 * Modification history：
 *
 * @version 1.0 2014-4-28
 * @author changtao
 * @since 2.3
 */
package com.hiver.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author changtao
 * @version 1.0 2014-4-28
 * @since 2.3
 */
public class UIDefaultDialogHelper {
    public static UIDefaultConfirmDialog dialog;

    /**
     * 默认提示弹出框<br>
     * 只包含提示内容及确定按钮<br>
     * 确定按钮仅隐藏弹出框
     *
     * @param context 上下文
     * @param msg     提示内容
     */
    public static void showDefaultAlert(Context context, String msg) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);
        dialog.getNegativeButton().setText("确定");
        dialog.getNegativeButton().setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        dialog = null;
                    }
                });

        dialog.show();
    }

    /**
     * 默认提示弹出框<br>
     * 只包含提示内容及确定按钮<br>
     * 确定按钮默认仅隐藏弹出框
     *
     * @param context  上下文
     * @param msg      提示内容
     * @param listener 确定按钮点击事件
     */
    public static void showDefaultAlert(Context context, String msg,
                                        OnClickListener listener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);
        dialog.getNegativeButton().setText("确定");

        if (null == listener) {
            dialog.getNegativeButton().setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                            dialog = null;
                        }
                    });
        } else {
            dialog.getNegativeButton().setOnClickListener(listener);
        }

        dialog.show();
    }

    public static void showDefaultAlert(Context context, String msg, String buttonTitle) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);
        dialog.getNegativeButton().setText(buttonTitle);
        dialog.getNegativeButton().setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        dialog = null;
                    }
                });

        dialog.show();
    }

    public static void showDefaultAlert(Context context, String msg, String buttonTitle,
                                        OnClickListener listener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);
        dialog.getNegativeButton().setText(buttonTitle);

        if (null == listener) {
            dialog.getNegativeButton().setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                            dialog = null;
                        }
                    });
        } else {
            dialog.getNegativeButton().setOnClickListener(listener);
        }

        dialog.show();
    }

    /**
     * 询问弹出框<br>
     * 顶部图标为问号，包含左右两个按钮<br>
     * 左边取消按钮仅隐藏弹出框
     *
     * @param context         上下文
     * @param msg             提示内容
     * @param rightButtonText 右按钮文字
     * @param listener        右边按钮点击监听
     */
    public static void showDefaultAskAlert(Context context, String msg,
                                           String rightButtonText, OnClickListener listener) {
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
     * 左边取消按钮仅隐藏弹出框
     *
     * @param context         上下文
     * @param msg             提示内容
     * @param rightButtonText 右按钮文字
     * @param listener        右边按钮点击监听
     */
    public static void showDefaultAskAlert(Context context, String msg,
                                           String rightButtonText, String leftButtonText, OnClickListener listener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);

        dialog.getPositiveButton().setText(TextUtils.isEmpty(leftButtonText) ? "取消" : leftButtonText);
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
     * 左边取消按钮仅隐藏弹出框
     *
     * @param context         上下文
     * @param msg             提示内容
     * @param rightButtonText 右按钮文字
     * @param listener        右边按钮点击监听
     */
    public static void showDefaultAskAlert(Context context, String msg,
                                           String rightButtonText, String leftButtonText, OnClickListener listener, OnClickListener cancelListener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);

        dialog.getPositiveButton().setText(TextUtils.isEmpty(leftButtonText) ? "取消" : leftButtonText);
        if (null != cancelListener) {
            dialog.getPositiveButton().setOnClickListener(cancelListener);
        } else {
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
        dialog.getNegativeButton().setOnClickListener(listener);

        dialog.show();
    }

    /**
     * 操作成功提示框<br>
     * 顶部图标为对钩，包含一个确定按钮<br>
     * 确定按钮默认隐藏提示框
     *
     * @param context  上下文
     * @param msg      提示内容
     * @param listener 确定按钮点击监听
     */
    public static void showDefaultSuccessAlert(Context context, String msg,
                                               OnClickListener listener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);
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

    /**
     * 询问弹出框<br>
     * 顶部图标为问号，包含左右两个按钮<br>
     * 左边取消按钮仅隐藏弹出框
     *
     * @param context         上下文
     * @param msg             提示内容
     * @param rightButtonText 右按钮文字
     * @param listener        右边按钮点击监听
     */
    public static void showDefaultAskAlertWithTitle(Context context, String title, String msg,
                                                    String rightButtonText, String leftButtonText, OnClickListener listener, OnClickListener cancelListener) {
        dialog = new UIDefaultConfirmDialog(context);
        dialog.getUiDialogMessage().setText(msg);

        dialog.getPositiveButton().setText(TextUtils.isEmpty(leftButtonText) ? "取消" : leftButtonText);
        if (null != cancelListener) {
            dialog.getPositiveButton().setOnClickListener(cancelListener);
        } else {
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
        dialog.getNegativeButton().setOnClickListener(listener);

        dialog.show();
    }

    /**
     * 显示权限询问
     *
     * @param context
     * @param title
     * @param msg
     */
    public static void showPermissionAskDialog(final Context context, String title, String msg) {
        dialog = new UIDefaultConfirmDialog(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.getUiDialogTitle().setText(title);
            dialog.getUiDialogTitle().setVisibility(View.VISIBLE);
        }

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
        dialog.getNegativeButton().setText("立即开启");
        dialog.getNegativeButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;

                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
                context.startActivity(localIntent);
            }
        });

        dialog.show();
    }

    /**
     * 显示权限询问
     *
     * @param context
     * @param title
     * @param msg
     */
    public static void showPermissionAskDialog(final Context context, String title, String msg ,OnClickListener onClickListener) {
        dialog = new UIDefaultConfirmDialog(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.getUiDialogTitle().setText(title);
            dialog.getUiDialogTitle().setVisibility(View.VISIBLE);
        }

        dialog.getUiDialogMessage().setText(msg);

        dialog.getPositiveButton().setText("取消");
        dialog.getPositiveButton().setOnClickListener(onClickListener);

        dialog.getNegativeLayout().setVisibility(View.VISIBLE);
        dialog.getNegativeButton().setText("立即开启");
        dialog.getNegativeButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;

                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
                context.startActivity(localIntent);
            }
        });

        dialog.show();
    }

    public static void showPermissionAskDialogWithExit(final Context context, String title, String msg) {
        dialog = new UIDefaultConfirmDialog(context);

        if (!TextUtils.isEmpty(title)) {
            dialog.getUiDialogTitle().setText(title);
            dialog.getUiDialogTitle().setVisibility(View.VISIBLE);
        }

        dialog.getUiDialogMessage().setText(msg);

        dialog.getPositiveButton().setText("退出");
        dialog.getPositiveButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
                ((Activity) context).finish();
            }
        });

        dialog.getNegativeLayout().setVisibility(View.VISIBLE);
        dialog.getNegativeButton().setText("去设置");
        dialog.getNegativeButton().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;

                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
                context.startActivity(localIntent);
                ((Activity) context).finish();
            }
        });

        dialog.show();
    }
}
