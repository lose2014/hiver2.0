package com.seaway.android.view.banner.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.seaway.android.view.banner.R;
import com.seaway.android.view.banner.data.BannerData;

import java.util.List;

/**
 *
 * Created by Leo.Chang on 2017/5/11.
 */
public class BannerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;

    private int placeholderRes;
    private int failbackRes;

    /**
     * 默认构造方法
     *
     * @param placeholderRes 加载时默认图片
     * @param failbackRes    加载失败显示图片
     */
    public BannerAdapter(int placeholderRes, int failbackRes) {
        this.placeholderRes = placeholderRes;
        this.failbackRes = failbackRes;
    }

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return null == imageViews ? 0 : imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        Glide.with(container.getContext())
                .load(imageView.getTag(R.id.ui_view_banner_path))
                .placeholder(placeholderRes)
                .fallback(failbackRes)
                .into(imageView);
        container.addView(imageView);
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }

    public Object getObjectId(int posistion) {
        if (null == imageViews || posistion >= imageViews.size()
                || null == imageViews.get(posistion)) {
            return null;
        }

        return imageViews.get(posistion).getTag(R.id.ui_view_banner_info);
    }

    /**
     * 销毁数据
     */
    public void clear() {
        if (null != imageViews) {
            imageViews.clear();
            imageViews = null;
            notifyDataSetChanged();
        }
    }
}
