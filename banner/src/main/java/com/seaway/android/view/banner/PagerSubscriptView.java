package com.seaway.android.view.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.logging.Logger;

/**
 * 页码脚标
 * Created by Leo.Chang on 2017/5/11.
 */
public class PagerSubscriptView extends AppCompatRadioButton {
    public PagerSubscriptView(Context context, int background, int width, int height, Object tag) {
        super(context);
        initPagerSubscriptView(background, width, height, tag);
    }

    private void initPagerSubscriptView(int background, int width, int height, Object tag) {
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(width, height);
        params.leftMargin = (int) getResources().getDimension(
                R.dimen.cyclic_advert_subscript_horizontal_margin);
        params.rightMargin = (int) getResources().getDimension(
                R.dimen.cyclic_advert_subscript_horizontal_margin);
        setLayoutParams(params);
        setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        setBackgroundResource(background);
        setTag(tag);
    }
}
