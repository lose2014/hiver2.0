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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.main.teacher.biz.presenter.TMainPresenter;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * w我的微课界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class TMyWeKeFragment extends BaseFragment<TMainContract.Presenter> implements View.OnClickListener, TMainContract.View, AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private MainViewAdapter mAdapter;

    private int selectedViewId = -1;

    private RxPermissions permissions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            selectedViewId = savedInstanceState.getInt("selectedViewId", -1);
        }
        // 注入Presenter
        new TMainPresenter(this);
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

        // 初化 RecyclerView
        recyclerView = (RecyclerView) getView().findViewById(R.id.t_my_weke_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(animator);
        List<String> mDatas =new ArrayList<>();
        mDatas.add("第一次");
        mDatas.add("第二次");
        mDatas.add("第一次");
        mDatas.add("第二次");
        mDatas.add("第一次");
        recyclerView.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.t_my_weike_view,mDatas) {
            @Override
            protected void convert(ViewHolder holder, final String loginVo, int position) {
//                holder.setText(R.id.t_mian_item_content_tv,loginVo.getMobile());
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(),loginVo.getMobile(),Toast.LENGTH_SHORT).show();
//                    }
//                });
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
    public void showPortal(@NonNull Map<Integer, Object> dataSource) {
//        mAdapter.setDataSource(dataSource);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loginSuccess() {
        execute();
    }

    @Override
    public void gotoWithoutLogin() {

    }


    /**
     * 广告页查询成功
     *
     * @param advertVo 广告位信息
     * @param flag     true：要更新；false：不需要更新
     */
    @Override
    public void queryAdvertListSuccess(QueryAdvertListVo advertVo, boolean flag) {

    }

    /**
     * 获取缓存广告信息成功
     *
     * @param advertListVo 广告信息
     */
    @Override
    public void queryAdvertListInCacheSuccess(QueryAdvertListVo advertListVo) {

    }

    /**
     * 查询理财产品成功
     *
     * @param financial 理财产品
     */
    @Override
    public void queryFinancialSuccess(GetIconCodeVo financial) {

    }

    /**
     * 查询缓存理财产品成功
     *
     * @param financial 理财产品
     */
    @Override
    public void queryFinancialInCacheSuccess(GetIconCodeVo financial) {
        // 先显示缓存数据，再去服务器获取新的数据

    }

    private void execute() {

    }

    @Override
    public void showAutoHidenDialog(int rid) {

    }

    @Override
    public void showUnconnectDialog(String msg) {

    }

    @Override
    public void showOnConnectionDialog(int rid) {

    }

    @Override
    public void disMissConnectingDialog() {

    }
}
