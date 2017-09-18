package com.hiver.ui.grid;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 在Re
 * Created by Leo.Chang on 2017/5/11.
 */
public class RecyclerViewGridView extends GridView {

    public RecyclerViewGridView(Context context,
                                AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerViewGridView(Context context) {
        super(context);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
