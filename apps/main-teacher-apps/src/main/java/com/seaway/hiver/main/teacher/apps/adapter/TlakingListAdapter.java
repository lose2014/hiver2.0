package com.seaway.hiver.main.teacher.apps.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.model.common.NetUtil;
import com.seaway.hiver.model.common.SDCardImageLoader;
import com.seaway.hiver.model.main.teacher.data.vo.CourseVo;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Leo.Chang on 2017/5/13.
 */
public class TlakingListAdapter extends CommonAdapter<CourseVo> {
    private int type;
    private SDCardImageLoader loader;
    public TlakingListAdapter(Context context, int layoutId, List<CourseVo> datas) {
        super(context, layoutId, datas);
        if(loader==null){
            loader = new SDCardImageLoader(DeviceUtil.getScreenInfo()[0], DeviceUtil.getScreenInfo()[1]);

        }
    }

    @Override
    protected void convert(final ViewHolder holder, CourseVo courseVos, int position) {

        holder.setText(R.id.textView3,courseVos.getCourseIntroduce());
        holder.setText(R.id.textView1,courseVos.getTeaRealname());
        holder.setText(R.id.textView2,courseVos.getCreateTime());
//        holder.setImageResource(R.id.weike_img_iv,R.drawable.img_default);
//        if(!TextUtils.isEmpty(courseVos.getCourseCoverUrl())){
//            loader.loadDrawable(NetUtil.getWebViewUrl()+courseVos.getCourseCoverUrl(),1, new SDCardImageLoader.ImageCallback() {
//
//                @Override
//                public void imageLoaded(Bitmap bmp) {
//
//                }
//                @Override
//                public void imageLoaded(Bitmap bmp, String path) {
//                    if (bmp != null) {
//                        holder.setImageBitmap(R.id.weike_img_iv,bmp);
//                    }
//                }
//            });
//        }

//        holder.setImageBitmap(R.id.weike_img_iv,FileMapUtil.createVideoThumbnail("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4", MediaStore.Images.Thumbnails.MINI_KIND));
//        holder.setImageBitmap(R.id.weike_img_iv,FileMapUtil.getNetVideoBitmap("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4"));
//        FileMapUtil.getImageForVideo("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4", new FileMapUtil.OnLoadVideoImageListener() {
//            @Override
//            public void onLoadImage(File file) {
//
//                loader.loadDrawable(file.getAbsolutePath(),1, new SDCardImageLoader.ImageCallback() {
//
//                    @Override
//                    public void imageLoaded(Bitmap bmp) {
//
//                    }
//                    @Override
//                    public void imageLoaded(Bitmap bmp, String path) {
//                            if (bmp != null) {
//                                holder.setImageBitmap(R.id.weike_img_iv,bmp);
//                            }
//                    }
//                });
//            }
//        });
    }
}
