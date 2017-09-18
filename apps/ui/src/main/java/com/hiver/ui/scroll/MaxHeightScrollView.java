package com.hiver.ui.scroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.hiver.ui.R;

/**
 * Created by Leo.Chang on 2017/6/10.
 */
public class MaxHeightScrollView extends ScrollView {
    private int maxHeight;

    public MaxHeightScrollView(Context context) {
        super(context);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributeSet(attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributeSet(attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (0 != maxHeight) {
            try {
                //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void getAttributeSet(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray mArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.max_height_scroll_view);

        maxHeight = mArray.getDimensionPixelOffset(R.styleable.max_height_scroll_view_max_height, 0);

        mArray.recycle();
    }
}
