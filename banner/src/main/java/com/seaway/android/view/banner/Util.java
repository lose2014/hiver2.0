package com.seaway.android.view.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.seaway.android.view.banner.data.BannerData;

import java.lang.reflect.Field;

/**
 * Created by Leo.Chang on 2017/5/11.
 */

public class Util {
    /**
     * 创建广告图片控件
     *
     * @param context context
     * @param t       Banner数据
     * @return 图片控件
     */
    public static <T> ImageView createAdvertImageView(Context context, T t ,String imagePathPreperty, View.OnClickListener onClickListener) {
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.ui_view_banner_id);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setContentDescription("cyclicAdvertView");
        imageView.setTag(R.id.ui_view_banner_path, getImagePath(t,imagePathPreperty));
        imageView.setTag(R.id.ui_view_banner_info, t);
        if (null != onClickListener) {
            imageView.setOnClickListener(onClickListener);
        }

        return imageView;
    }

    private static <T> String getImagePath(T t ,String imagePathPreperty) {
        String value = "";
        try {
            Class cls = t.getClass();
            Field field = cls.getDeclaredField(imagePathPreperty);
            field.setAccessible(true);
            Object obj = field.get(t);
            if (null != obj) {
                value = String.valueOf(obj);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return value;
    }
}