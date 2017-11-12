package com.xiao.nicevideoplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.nicevideoplayer.R;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevideoplayer.adapter.holder.VideoViewHolder;
import com.xiao.nicevideoplayer.bean.Video;

import java.util.List;

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private Context mContext;
    private List<Video> mVideoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        mContext = context;
        mVideoList = videoList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
//        measureView(itemView);
        VideoViewHolder holder = new VideoViewHolder(itemView);
        TxVideoPlayerController controller = new TxVideoPlayerController(mContext);
        holder.setController(controller);
        return holder;
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        if(lp == null){
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //headerView的宽度信息
        int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int childMeasureHeight;
        childMeasureHeight = (int) (childMeasureWidth * 9f / 16f);    // 高度为宽度的9/16
//        if(lp.height > 0){
//            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(lp.height, View.MeasureSpec.EXACTLY);
//            //最后一个参数表示：适合、匹配
//        } else {
//            childMeasureHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);//未指定
//        }
//System.out.println("childViewWidth"+childMeasureWidth);
//System.out.println("childViewHeight"+childMeasureHeight);
        //将宽和高设置给child
        child.measure(childMeasureWidth, childMeasureHeight);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}
