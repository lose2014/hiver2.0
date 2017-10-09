package com.seaway.android.view.banner;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.seaway.android.view.banner.UIBanner;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Leo.Chang on 2017/5/11.
 */
public class CreateBannerImagesHandler extends Handler {
    public static final int CREATE_BANNER_IMAGES_IDENTIFIER = 0x8654;

    private WeakReference<UIBanner> mBanner;

    public CreateBannerImagesHandler(UIBanner banner) {
        mBanner = new WeakReference<UIBanner>(banner);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == CREATE_BANNER_IMAGES_IDENTIFIER) {
            if (null != mBanner && null != mBanner.get()) {
                mBanner.get().showAdvertList((List<ImageView>) msg.obj);
            }
        }
    }
}
