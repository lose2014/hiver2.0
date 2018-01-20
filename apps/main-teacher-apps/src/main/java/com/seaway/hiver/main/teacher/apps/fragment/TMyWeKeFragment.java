package com.seaway.hiver.main.teacher.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.main.teacher.biz.contract.TWekeContract;
import com.seaway.hiver.main.teacher.biz.presenter.TMainPresenter;
import com.seaway.hiver.main.teacher.biz.presenter.TWekePresenter;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.adapter.VideoAdapter;
import com.xiao.nicevideoplayer.adapter.holder.VideoViewHolder;
import com.xiao.nicevideoplayer.base.CompatHomeKeyFragment;
import com.xiao.nicevideoplayer.base.HomeKeyWatcher;
import com.xiao.nicevideoplayer.util.DataUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.R.attr.name;

/**
 * w我的微课界面
 * Created by Leo.Chang on 2017/5/10.
 * 在此Fragment中，如果视频正在播放或缓冲，按下Home键，暂停视频播放，回到此Fragment后继续播放视频；
 * 如果离开次Fragment（跳转到其他Activity或按下Back键），则释放视频播放器
 */
public class TMyWeKeFragment extends BaseFragment<TWekeContract.Presenter> implements View.OnClickListener, TWekeContract.View, AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private MainViewAdapter mainViewAdapter;
    private ImageView searchTv;
    private int selectedViewId = -1;
    private EditText searchEt;
    private RxPermissions permissions;
    private int timeIndex,statusIndex,subjectIndex;
    private String[] timeList={"发布时间", "今天", "一周内", "近一个月", "一个月以上"},statusList={"微课状态", "上架", "编辑", "下架","审核"},
            subjectList={"科目", "语文", "数学", "英语", "生物", "物理", "历史", "地理", "政治", "化学"};


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mHomeKeyWatcher = new HomeKeyWatcher(getActivity());
//        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
//            @Override
//            public void onHomePressed() {
//                pressedHome = true;
//            }
//        });
//        pressedHome = false;
//        mHomeKeyWatcher.startWatch();
//    }
//
//    @Override
//    public void onStart() {
//        mHomeKeyWatcher.startWatch();
//        pressedHome = false;
//        super.onStart();
//        NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
//    }
//
//    @Override
//    public void onStop() {
//        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
//        if (pressedHome) {
//            NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
//        } else {
//            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
//        }
//        super.onStop();
//        mHomeKeyWatcher.stopWatch();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            selectedViewId = savedInstanceState.getInt("selectedViewId", -1);
        }
        // 注入Presenter
        new TWekePresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        permissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_my_weike, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchEt =(EditText) getView().findViewById(R.id.wk_search_et);
        searchTv =(ImageView) getView().findViewById(R.id.my_weike_search_iv);
        setOnClickListener();
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        List<LoginVo> loginVos =new ArrayList<>();
        for(int i=0;i<6;i++){
            LoginVo loginVo =new LoginVo();
            loginVo.setMobile("张"+i+"买了课程"+i);
            loginVos.add(loginVo);
        }

        final Spinner statusSp = (Spinner) getView().findViewById(R.id.nice_spinner1);
//将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
//        String[] name={"微课状态", "上架", "编辑", "下架","审核"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.custom_spiner_text_item,statusList);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改custom_spiner_text_item custom_spinner_dropdown_item
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        //将adapter 添加到spinner中
        statusSp.setAdapter(adapter);
        //添加事件Spinner事件监听
        statusSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusIndex=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner timeSp = (Spinner) getView().findViewById(R.id.nice_spinner2);
//将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
//        String[] name2={"发布时间", "今天", "一周内", "近一个月", "一个月以上"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),R.layout.custom_spiner_text_item,timeList);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改custom_spiner_text_item custom_spinner_dropdown_item
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        //将adapter 添加到spinner中
        timeSp.setAdapter(adapter2);
        //添加事件Spinner事件监听
        timeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeIndex=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner subjectSp = (Spinner) getView().findViewById(R.id.nice_spinner3);
//将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
//        String[] name3={"科目", "语文", "数学", "英语", "生物", "物理", "历史", "地理", "政治", "化学"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),R.layout.custom_spiner_text_item,subjectList);
        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改custom_spiner_text_item custom_spinner_dropdown_item
        adapter3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        //将adapter 添加到spinner中
        subjectSp.setAdapter(adapter3);
        //添加事件Spinner事件监听
        subjectSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((Spinner)view).getSelectedItem().toString();
                subjectIndex=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // 初化 RecyclerView
        recyclerView = (RecyclerView) getView().findViewById(R.id.t_my_weke_recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setItemAnimator(animator);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        VideoAdapter videoAdapter = new VideoAdapter(getActivity(), DataUtil.getVideoListData());
        recyclerView.setAdapter(videoAdapter);
        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
                if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
                    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
                }
            }
        });
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
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("搜索------"+ searchEt.getText());
            }
        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Logger.e("输入完点击确认执行该方法,输入结束"+ v.getText());

                return false;
            }
        });
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    Logger.d("zzzz"+v.getText().toString());
                }
                return false;
            }
        });
        getView().findViewById(R.id.ui_navigation_bar_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager.popBackStack();
            }
        });
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
