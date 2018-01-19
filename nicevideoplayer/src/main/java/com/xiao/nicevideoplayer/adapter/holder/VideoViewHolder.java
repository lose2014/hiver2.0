package com.xiao.nicevideoplayer.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.R;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevideoplayer.bean.Video;

import org.w3c.dom.Text;

/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public TxVideoPlayerController mController;
    public NiceVideoPlayer mVideoPlayer;
    private TextView titleView,couserView,moneyView,statusView,contentView,timeView;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
        couserView = (TextView) itemView.findViewById(R.id.wk_couser_tv);
        titleView = (TextView) itemView.findViewById(R.id.wk_title_tv);
        moneyView = (TextView) itemView.findViewById(R.id.wk_money_tv);
        statusView = (TextView) itemView.findViewById(R.id.wk_status_tv);
        contentView = (TextView) itemView.findViewById(R.id.wk_content_tv);
        timeView = (TextView) itemView.findViewById(R.id.wk_time_tv);
//        // 将列表中的每个视频设置为默认16:9的比例
//        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
//        params.width = itemView.getResources().getDisplayMetrics().widthPixels/2; // 宽度为屏幕宽度itemView
//        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
//        mVideoPlayer.setLayoutParams(params);
    }

    public void setController(TxVideoPlayerController controller) {
        mController = controller;
        mVideoPlayer.setController(mController);
    }

    public void bindData(Video video) {
//        timeView.setText(video.getCreateTime());
        mController.setTitle(video.getTitle());
        mController.setLenght(video.getLength());
        Glide.with(itemView.getContext())
                .load(video.getImageUrl())
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(mController.imageView());
        mVideoPlayer.setUp(video.getVideoUrl(), null);
    }
}
