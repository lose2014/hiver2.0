package com.hiver.ui;

import android.view.View;
import android.widget.AdapterView;

/**
 * 点击监听
 * Created by Leo.Chang on 2017/5/11.
 */
public interface OnRecyclerViewItemClickListener {
    /**
     * 点击监听回调
     *
     * @param v 点击的View
     */
    public void onRecyclerViewClick(View v);

    public void onRecyclerItemClick(AdapterView<?> parent, View view, int position, long id);
}
