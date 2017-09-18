package com.seaway.common.apps.qrcode.scan.view;

import com.seaway.common.qrcode.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * 绘扫描框
 * 
 * @author mayl
 * 
 */
public class ScanRay extends ImageView {
	private ScaleAnimation scaleAnimation;

	public ScanRay(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init();
	}

	public ScanRay(Context context) {
		super(context);
		init();
	}

	public ScanRay(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init() {
		setBackgroundResource(R.drawable.ui_scan_ray);
		start();
	}

	public final void start() {
		if (scaleAnimation != null) {
			return;
		}
		scaleAnimation = new ScaleAnimation(1.0F, 1.0F, 0.0F, 1.0F);
		scaleAnimation.setDuration(1500L);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setRepeatCount(-1);
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		startAnimation(scaleAnimation);
	}

	public final void end() {
		if (scaleAnimation == null) {
			return;
		}
		clearAnimation();
		scaleAnimation = null;
	}
}
