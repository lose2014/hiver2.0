package com.seaway.android.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.seaway.android.view.banner.adapter.BannerAdapter;
import com.seaway.android.view.banner.data.BannerData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 轮播Banner控件
 * 使用方法：
 * <com.seaway.android.view.banner.UIBanner
 * android:id="@+id/financial_portal_banner_view"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * // 获取图片失败时显示的图片
 * ui:ui_view_banner_failback="@drawable/financial_portal_banner_default_background"
 * // 图片数量脚标是否在界面下方显示
 * ui:ui_view_banner_flag_alignParentBottom="true"
 * // 图片数量脚标是否在界面右边显示
 * ui:ui_view_banner_flag_alignParentLeft="true"
 * // 图片数量脚标是否在界面左边显示
 * ui:ui_view_banner_flag_alignParentRight="true"
 * // 图片数量脚标是否水平居中
 * ui:ui_view_banner_flag_centerHorizontal="true"
 * // 图片数量脚标背景图片，请使用selector图片
 * ui:ui_view_banner_flag_background="@drawable/financial_portal_banner_flag"
 * // 图片数量脚标高度
 * ui:ui_view_banner_flag_height="2dp"
 * // 图片数量脚标下间距
 * ui:ui_view_banner_flag_marginBottom="2dp"
 * // 图片数量脚标右间距
 * ui:ui_view_banner_flag_marginRight="5dp"
 * // 图片数量脚标左间距
 * ui:ui_view_banner_flag_marginLeft="2dp"
 * // 图片数量脚标宽度
 * ui:ui_view_banner_flag_width="7dp"
 * // 对象中获取图片路径的属性名称
 * ui:ui_view_banner_image_path="imagePath"
 * // 没有图片时的默认背景图片
 * ui:ui_view_banner_none_image="@drawable/financial_portal_banner_default_background"
 * // 图片加载时的背景图片
 * ui:ui_view_banner_placeholder="@drawable/financial_portal_banner_default_background" />
 * Created by Leo.Chang on 2017/5/11.
 */
public class UIBanner<T> extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    // 无数据默认显示图片
    private int noneBannerRes;
    // 加载显示图片
    private int placeholderRes;
    // 加载失败显示图片
    private int failbackRes;
    // 图片路径对应的属性名
    private String imagePath;

    // 底部页面标识的背景
    private int flagBackground;
    // 页码标识底部间距
    private int marginBottom;
    // 页码标识右间距
    private int marginRight;
    // 页码标识左间距
    private int marginLeft;
    // 页码标识位置
    private boolean alignParentBottom;
    private boolean centerHorizontal;
    private boolean alignParentRight;
    private boolean alignParentLeft;

    // 页码标识宽度
    private int flagWidth;
    // 页码标识高度
    private int flagHeight;

    // 无数据时显示图片
    private ImageView mNoneImageView;

    private ViewPager mViewPager;
    private BannerAdapter mAdapter;

    private RadioGroup mRadioGroup;

    /**
     * Banner数据源
     */
    private List<T> dataSource;

    /**
     * 是否循环显示
     */
    private boolean isLoop = true;

    /**
     * 循环间隔时间，单位：秒
     */
    private int duration = 3;

    private boolean isPlaying;

    // 循环滚动句柄
    private Handler handler = new Handler();
//    private LoopRunnable runnable;

    private Runnable playTask = new Runnable() {

        @Override
        public void run() {
            mViewPager.setCurrentItem(
                    mViewPager.getCurrentItem() + 1, true);
            handler.postDelayed(this, duration * 1000);
        }
    };

    private OnClickListener onBannerClickListener;

    public UIBanner(Context context) {
        super(context);
        initView(null);
    }

    public UIBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public UIBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i("SeawayAndroidLog", "UIBanner -> onAttachedToWindow");
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i("SeawayAndroidLog", "UIBanner -> onDetachedFromWindow");
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }

        super.onWindowVisibilityChanged(visibility);
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    private synchronized void setPlaying(boolean playing) {
        if (isLoop) {
            if (!isPlaying && playing && dataSource != null && dataSource.size() > 1) {
                handler.postDelayed(playTask, duration * 1000);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                handler.removeCallbacksAndMessages(null);
                isPlaying = false;
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getCount() > 1) {
            // 如果页面数量大于，则说明有循环参数，那么
            if (0 == position) {
                mViewPager.setCurrentItem(mAdapter.getCount() - 2, false);
                return;
            } else if (position == mAdapter.getCount() - 1) {
                // 当滑动的最后一个
                mViewPager.setCurrentItem(1, false);
                return;
            }

            View subscriptView = mRadioGroup.findViewWithTag(mAdapter
                    .getObjectId(position));
            if (null != subscriptView && subscriptView instanceof RadioButton) {
                ((RadioButton) subscriptView).setChecked(true);
            }
//            loopAdvertView();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setOnBannerClickListener(OnClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null != onBannerClickListener) {
            onBannerClickListener.onClick(v);
        }
    }

    /**
     * 设置是否循环显示
     *
     * @param isLoop true：循环;false：不循环
     */
    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    /**
     * 设置轮播间隔时间，默认3秒
     *
     * @param secentd 间隔时间，单位：秒
     */
    public void setDuration(int secentd) {
        this.duration = secentd;
    }

    /**
     * 通知数据变更
     *
     * @param data Banner数据
     */
    public void notifyDataChanged(List<T> data) {
        setPlaying(false);
        if (null == data || 0 == data.size()) {
            // 如果没有数据，
            if (null != dataSource) {
                dataSource.clear();
                dataSource = null;
            }

            mNoneImageView.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);

            mRadioGroup.removeAllViews();
            mRadioGroup.setVisibility(View.GONE);
            return;
        }
        this.dataSource = data;
        mNoneImageView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.VISIBLE);
        initBanners(new CreateBannerImagesHandler(this));
    }

    /**
     * 获取自定义数据
     *
     * @param attrs
     */
    private void getAttributeSet(AttributeSet attrs) {
        if (null == attrs) {
            return;
        }
        TypedArray mArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.ui_view_banner);
        placeholderRes = mArray.getResourceId(R.styleable.ui_view_banner_ui_view_banner_placeholder, 0);
        failbackRes = mArray.getResourceId(R.styleable.ui_view_banner_ui_view_banner_failback, 0);
        noneBannerRes = mArray.getResourceId(R.styleable.ui_view_banner_ui_view_banner_none_image, 0);
        imagePath = mArray.getString(R.styleable.ui_view_banner_ui_view_banner_image_path);
        if (TextUtils.isEmpty(imagePath)) {
            throw new IllegalArgumentException("ui_view_banner_image_path attribute can't empty!");
        }

        marginBottom = mArray.getDimensionPixelOffset(R.styleable.ui_view_banner_ui_view_banner_flag_marginBottom, 0);
        marginLeft = mArray.getDimensionPixelOffset(R.styleable.ui_view_banner_ui_view_banner_flag_marginLeft, 0);
        marginRight = mArray.getDimensionPixelOffset(R.styleable.ui_view_banner_ui_view_banner_flag_marginRight, 0);
        alignParentBottom = mArray.getBoolean(R.styleable.ui_view_banner_ui_view_banner_flag_alignParentBottom, true);
        centerHorizontal = mArray.getBoolean(R.styleable.ui_view_banner_ui_view_banner_flag_centerHorizontal, true);
        alignParentRight = mArray.getBoolean(R.styleable.ui_view_banner_ui_view_banner_flag_alignParentRight, false);
        alignParentLeft = mArray.getBoolean(R.styleable.ui_view_banner_ui_view_banner_flag_alignParentLeft, false);

        flagBackground = mArray.getResourceId(R.styleable.ui_view_banner_ui_view_banner_flag_background, R.drawable.ui_view_banner_flag);

        flagWidth = mArray.getDimensionPixelOffset(R.styleable.ui_view_banner_ui_view_banner_flag_width, 0);
        flagHeight = mArray.getDimensionPixelOffset(R.styleable.ui_view_banner_ui_view_banner_flag_height, 0);

        mArray.recycle();
    }

    /**
     * 初始化界面
     */
    private void initView(AttributeSet attrs) {
        Log.i("TAG", "UIBanner -> initView");
        getAttributeSet(attrs);

        LayoutInflater.from(getContext()).inflate(R.layout.ui_view_banner, this, true);

        mNoneImageView = (ImageView) findViewById(R.id.ui_view_banner_default_image_view);
        mNoneImageView.setImageResource(noneBannerRes);

        mViewPager = (ViewPager) findViewById(R.id.ui_view_banner_view_pager);

        mAdapter = new BannerAdapter(placeholderRes, failbackRes);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        mRadioGroup = (RadioGroup) findViewById(R.id.ui_view_banner_flag_group);

        // 初始化页码标识
        initRadioGroup();

//        handler = new LooperHandler();
//        runnable = new LoopRunnable(this);
    }

    /**
     * 初始化Banner图
     *
     * @param handler
     */
    private void initBanners(final Handler handler) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                List<ImageView> images = null;
                if (dataSource.size() > 1) {
                    // 如果广告图片大于1，则为了能够循环显示，需要在最前面加上最后一章广告图；在最后面加上第一张广告图
                    images = new ArrayList<ImageView>(dataSource.size() + 2);
                    // 在第一页添加最后一张图片
                    images.add(0, Util.createAdvertImageView(getContext(), dataSource.get(dataSource
                            .size() - 1), imagePath, UIBanner.this));

                    for (T t : dataSource) {
                        images.add(Util.createAdvertImageView(getContext(), t, imagePath, UIBanner.this));
                    }

                    // 在最后添加第一张
                    images.add(Util.createAdvertImageView(getContext(), dataSource.get(0), imagePath, UIBanner.this));
                } else {
                    images = new ArrayList<ImageView>(dataSource.size());
                    images.add(Util.createAdvertImageView(getContext(), dataSource.get(0), imagePath, UIBanner.this));
                }

                if (null != handler) {
                    Message msg = Message.obtain();
                    msg.what = CreateBannerImagesHandler.CREATE_BANNER_IMAGES_IDENTIFIER;
                    msg.obj = images;
                    handler.sendMessage(msg);
                }
            }
        }).start();
        initSubscriptView();
    }

    /**
     * 在ViewPager中显示广告列表
     *
     * @param images
     */
    protected void showAdvertList(List<ImageView> images) {
        mAdapter.setImageViews(images);
        mAdapter.notifyDataSetChanged();
        if (images.size() > 1) {
            mViewPager.setCurrentItem(1, false);
        }
        setPlaying(true);
    }

    /**
     *
     */
    private void initRadioGroup() {
        RelativeLayout.LayoutParams params = (LayoutParams) mRadioGroup.getLayoutParams();
        if (alignParentBottom) {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        if (centerHorizontal) {
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }
        if (alignParentLeft) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        if (alignParentRight) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        params.setMargins(marginLeft, 0, marginRight, marginBottom);
        mRadioGroup.setLayoutParams(params);
    }

    /**
     * 初始化页码标识
     */
    private void initSubscriptView() {
        if (dataSource.size() <= 1) {
            // 如果广告数量只有一条，则不需要下标
            return;
        }

        mRadioGroup.removeAllViews();
        for (int i = 0; i < dataSource.size(); i++) {
            PagerSubscriptView view = new PagerSubscriptView(getContext(), flagBackground, flagWidth, flagHeight,
                    dataSource.get(i));
            mRadioGroup.addView(view);
        }
    }

    /**
     * 循环播放广告图片
     */
    private void loopAdvertView() {
        if (null != dataSource && isLoop && dataSource.size() > 1) {
            // 如果要自动循环显示
            if (0 != duration) {
                if (null != handler) {
                    handler.removeCallbacks(playTask);
                    handler.postDelayed(playTask, duration * 1000);
                }
            }
        }
    }

//    static class LooperHandler extends Handler {
//    }
//
//    static class LoopRunnable implements Runnable {
//        private WeakReference<UIBanner> mBanner;
//
//        public LoopRunnable(UIBanner banner) {
//            mBanner = new WeakReference<UIBanner>(banner);
//        }
//
//        @Override
//        public void run() {
//            Log.i("TAG","LoopRunnable -> ");
//            if (null != mBanner && null != mBanner.get() && mBanner.get().isShown()) {
//                UIBanner view = mBanner.get();
//                Log.i("TAG","banner is shown ? " + view.isShown());
//                view.mViewPager.setCurrentItem(
//                        view.mViewPager.getCurrentItem() + 1, true);
//                view.handler.postDelayed(this,view.duration * 1000);
//            } else {
//            }
//        }
//    }
}
