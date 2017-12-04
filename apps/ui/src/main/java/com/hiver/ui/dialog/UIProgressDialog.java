package com.hiver.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hiver.ui.R;

import java.util.logging.Logger;

/**
 * 网络加载等待框
 * Created by Leo.Chang on 2017/5/10.
 */
public class UIProgressDialog extends Dialog {
    private TextView mTv;
    private ProgressBar progressBar;
    private ColorArcProgressBar colorArcProgressBar;
    public UIProgressDialog(Context context) {
        super(context, R.style.UIDefaultWaitingProgressDialog);
        setContentView(R.layout.ui_default_waiting_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(false);
//        mTv =(TextView) findViewById(R.id.ui_tv);
//        colorArcProgressBar =(ColorArcProgressBar) findViewById(R.id.ui_waiting_dialog_progress_bar);
//        colorArcProgressBar.setCurrentValues(100);
//        progressBar =(ProgressBar) findViewById(R.id.ui_waiting_dialog_progress_bar);
//        ImageView imageView = (ImageView) findViewById(R.id.ui_waiting_dialog_animation_image);
//        Glide.with(context).load(R.drawable.ui_waiting_image).asGif().into(imageView);

    }

    public ProgressBar getProgressBar(){
        return  progressBar;
    }

    public TextView getTv(){
        return  mTv;
    }
    public void setProcess(int num){
//        progressBar.setProgress(num);
//        mTv.setText("正在下载中！("+num+"%)");
//        colorArcProgressBar.setCurrentValues(num);
    }
}