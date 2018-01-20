package com.seaway.hiver.main.teacher.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.google.gson.Gson;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.apps.adapter.TPortalListAdapter;
import com.seaway.hiver.main.teacher.apps.adapter.TlakingListAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TWekeContract;
import com.seaway.hiver.main.teacher.biz.contract.VideoPlayContract;
import com.seaway.hiver.main.teacher.biz.presenter.TWekePresenter;
import com.seaway.hiver.main.teacher.biz.presenter.VideoPlayPresenter;
import com.seaway.hiver.model.common.NetUtil;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.main.teacher.data.vo.CourseVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.xiao.nicevideoplayer.adapter.VideoAdapter;
import com.xiao.nicevideoplayer.adapter.holder.VideoViewHolder;
import com.xiao.nicevideoplayer.base.HomeKeyWatcher;
import com.xiao.nicevideoplayer.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * w我的微课界面
 * Created by Leo.Chang on 2017/5/10.
 * 在此Fragment中，如果视频正在播放或缓冲，按下Home键，暂停视频播放，回到此Fragment后继续播放视频；
 * 如果离开次Fragment（跳转到其他Activity或按下Back键），则释放视频播放器
 */
public class VideoPlayFragment extends BaseFragment<VideoPlayContract.Presenter> implements View.OnClickListener, VideoPlayContract.View, AdapterView.OnItemClickListener {
    private NiceVideoPlayer mNiceVideoPlayer;
    private int selectedViewId = -1;
    private RxPermissions permissions;
    private TextView timeTv,titleTv,gradeTv;
    private RecyclerView recyclerViewAd;
    private TlakingListAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            selectedViewId = savedInstanceState.getInt("selectedViewId", -1);
        }
        // 注入Presenter
        new VideoPlayPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        permissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_video_play, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnClickListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedViewId", selectedViewId);
    }
    /**
     * 设置点击事件监听
     */
    private void setOnClickListener() {
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param");
            CourseVo cursor =(new Gson()).fromJson(mParam1, CourseVo.class);


            timeTv =(TextView) getView().findViewById(R.id.video_ply_time);
            titleTv =(TextView) getView().findViewById(R.id.video_ply_time);
            gradeTv =(TextView) getView().findViewById(R.id.video_ply_grade);
            titleTv.setText(cursor.getCreateTime());
            titleTv.setText(cursor.getCourseName());
            gradeTv.setText(cursor.getStatus()+" "+cursor.getGrade());
            mNiceVideoPlayer = (NiceVideoPlayer) getView().findViewById(R.id.nice_video_player);
            mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // IjkPlayer or MediaPlayer
//            String videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/办公室小野.mp4");
            mNiceVideoPlayer.setUp(NetUtil.getWebViewUrl()+cursor.getCourseVideoUrl(), null);
            TxVideoPlayerController controller = new TxVideoPlayerController(getActivity());
//            controller.setTitle(cursor.getCourseName());
            controller.setLenght(98000);
            Glide.with(this)
                    .load(NetUtil.getWebViewUrl()+cursor.getCourseCoverUrl())
                    .placeholder(com.xiao.nicevideoplayer.R.drawable.img_default)
                    .crossFade()
                    .into(controller.imageView());
            mNiceVideoPlayer.setController(controller);
            getView().findViewById(R.id.enterWindow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mNiceVideoPlayer.isIdle()) {
                        Toast.makeText(getActivity(), "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
                    } else {
                        mNiceVideoPlayer.enterTinyWindow();
                    }
                }
            });


            List<CourseVo> weikeVolist =new ArrayList<>();
            weikeVolist.add(cursor);
            weikeVolist.add(cursor);
            weikeVolist.add(cursor);
            DefaultItemAnimator animator = new DefaultItemAnimator() {
                @Override
                public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                    return true;
                }
            };

            mAdapter =new TlakingListAdapter(getActivity(),R.layout.t_video_play_view,weikeVolist);
            recyclerViewAd =(RecyclerView) getView().findViewById(R.id.video_play_recycler_view);
            recyclerViewAd.setHasFixedSize(true);
            recyclerViewAd.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recyclerViewAd.setItemAnimator(animator);
            recyclerViewAd.setAdapter(mAdapter);
            //解决触摸到RecyclerView的时候滑动还有些粘连的感觉
            recyclerViewAd.setNestedScrollingEnabled(false);
        }


    }



    @Override
    protected boolean needsGoBackWhenIsLogout() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == QrCodeScannerActivity.SCAN_QR_CODE_REQUEST_CODE) {
//            if (resultCode == QrCodeScannerActivity.SCANCODE_RESULT_CODE && null != data) {
//                addWebViewFragment(NetUtil.web_view_path + "/scan/pay?qrCodeResult=" + data.getStringExtra("Barcode"));
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        this.selectedViewId = v.getId();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 功能菜单
        selectedViewId = (int) id;
        mPresenter.checkResource(String.valueOf(view.getTag(R.id.bank_resource_id)));
    }

    @Override
    public void loginSuccess() {
        execute();
    }

    @Override
    public void gotoWithoutLogin() {

    }

    @Override
    public void queryCourseListSuccess(GetCourseListVo getBillListVo) {

    }


    private void execute() {

    }

}
