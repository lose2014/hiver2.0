package com.xiao.nicevideoplayer.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.R;
import com.xiao.nicevideoplayer.adapter.VideoAdapter;
import com.xiao.nicevideoplayer.adapter.holder.VideoViewHolder;
import com.xiao.nicevideoplayer.base.CompatHomeKeyFragment;
import com.xiao.nicevideoplayer.util.DataUtil;

/**
 * Created by XiaoJianjun on 2017/7/7.
 * 如果你需要在播放的时候按下Home键能暂停，回调此Fragment又继续的话，需要继承自CompatHomeKeyFragment
 */
public class DemoProcessHomeKeyFragenment extends CompatHomeKeyFragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_demo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(getActivity(), DataUtil.getVideoListData());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
    }
}
