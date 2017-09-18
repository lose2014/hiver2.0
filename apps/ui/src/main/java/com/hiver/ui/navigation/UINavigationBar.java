package com.hiver.ui.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiver.ui.R;

/**
 * Created by Leo.Chang on 2017/5/13.
 */
public class UINavigationBar extends RelativeLayout {
    boolean showBackButton;
    String navigationBarTitle;

    public UINavigationBar(Context context) {
        super(context);
        initNavigationBar(null);
    }

    public UINavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initNavigationBar(attrs);
    }

    public UINavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNavigationBar(attrs);
    }

    /**
     * 设置返回按钮点击事件
     *
     * @param onClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        findViewById(R.id.ui_navigation_bar_back_button).setOnClickListener(onClickListener);
    }

    /**
     * 初始化导航栏
     *
     * @param attrs
     */
    private void initNavigationBar(AttributeSet attrs) {
        getAttributeSet(attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.ui_view_navigation_bar,this,true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setBackgroundResource(R.color.ui_navigation_bar_background_color);


        TextView titleText = (TextView) findViewById(R.id.ui_navigation_bar_title_text_view);
        ImageView backButton = (ImageView) findViewById(R.id.ui_navigation_bar_back_button);

        backButton.setVisibility(showBackButton ? View.VISIBLE : View.GONE);
        titleText.setText(navigationBarTitle);
    }

    /**
     * 初始化导航栏属性
     *
     * @param attrs
     */
    private void getAttributeSet(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray mArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ui_view_navigation_bar);

        showBackButton = mArray.getBoolean(R.styleable.ui_view_navigation_bar_show_back_button, true);
        navigationBarTitle = mArray.getString(R.styleable.ui_view_navigation_bar_navigation_bar_title);

        mArray.recycle();
    }
}