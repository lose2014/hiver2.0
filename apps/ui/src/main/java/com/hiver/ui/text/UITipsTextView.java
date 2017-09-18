package com.hiver.ui.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiver.ui.R;

/**
 * Created by Leo.Chang on 2017/5/13.
 */

public class UITipsTextView extends LinearLayout {
    private String tipContent;

    public UITipsTextView(Context context) {
        super(context);
        initUITipsTextView(null);
    }

    public UITipsTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUITipsTextView(attrs);
    }

    public UITipsTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUITipsTextView(attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        TextView tipText = (TextView) findViewById(R.id.ui_viwe_tips_content_text_view);
        tipText.setText(tipContent);
    }

    private void initUITipsTextView(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.ui_view_tips,this,true);
        this.setOrientation(LinearLayout.VERTICAL);

        getAttributeSet(attrs);
    }

    private void getAttributeSet(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray mArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ui_view_tip_text);

        tipContent = mArray.getString(R.styleable.ui_view_tip_text_tip_content);

        mArray.recycle();
    }
}
