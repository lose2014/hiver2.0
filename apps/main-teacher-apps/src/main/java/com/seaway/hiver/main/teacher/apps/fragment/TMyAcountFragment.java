package com.seaway.hiver.main.teacher.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.apps.common.view.CommonLoadMoreWrapper;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TAccountContract;
import com.seaway.hiver.main.teacher.biz.presenter.TAccountPresenter;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.main.teacher.data.vo.BillVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的账单界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class TMyAcountFragment extends BaseFragment<TAccountContract.Presenter> implements View.OnClickListener, TAccountContract.View, AdapterView.OnItemClickListener {
    LoadMoreWrapper mLoadMoreWrapper;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    private MainViewAdapter mAdapter;
    private int currPage=1;
    private int totalSize=10;
    private int selectedViewId = -1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RxPermissions permissions;
    List<BillVo> loginVos;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            selectedViewId = savedInstanceState.getInt("selectedViewId", -1);
        }
        // 注入Presenter
        new TAccountPresenter(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        permissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_tmyaccountl, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        swipeRefreshLayout =(SwipeRefreshLayout) getView().findViewById(R.id.swipeRl);
//        swipeRefreshLayout.setRefreshing(false);
//        swipeRefreshLayout.setEnabled(false);
        setOnClickListener();
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        loginVos =new ArrayList<>();
//        for(int i=0;i<6;i++){
//            LoginVo loginVo =new LoginVo();
//            loginVo.setMobile("张"+i+"买了课程"+i);
//            loginVos.add(loginVo);
//        }
        // 初化 RecyclerView
        recyclerView = (RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_account);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(animator);
        mLoadMoreWrapper = new LoadMoreWrapper(new CommonAdapter<BillVo>(getActivity(),R.layout.t_main_account_view,loginVos) {
            @Override
            protected void convert(ViewHolder holder, final BillVo loginVo, int position) {
                holder.setText(R.id.t_mian_item_content_tv,loginVo.getBillTeacherDesc());
                holder.setText(R.id.t_mian_item_time_tv,loginVo.getTradeTime());
                holder.setText(R.id.t_mian_item_money_btn,"+"+loginVo.getIncome());
                holder.setText(R.id.t_mian_result_tv,loginVo.getStatus()==0?"交易成功":"交易失败");
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                Logger.d("加载更多的数据");
//                try {
//                    Thread.sleep(3000);
//                    loadMoreData();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        loadMoreData();
                    }
                }, 3000);
            }
        });
        recyclerView.setAdapter(mLoadMoreWrapper);
        mPresenter.queryBillList(currPage+"", HiverApplication.getInstance().loginVo.getTeacherId()+"");
    }

    private void loadMoreData() {
        List<LoginVo> moreList = new ArrayList<>();
//        for (int i = 10; i < 13; i++) {
//            moreList.add("加载更多的数据");
//        }
//        for(int i=0;i<10;i++){
//            LoginVo loginVo =new LoginVo();
//            loginVo.setMobile("张"+i+"买了课程"+i);
//            loginVos.add(loginVo);
//        }
//        loginVos.addAll(moreList);
//        Handler handler = new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
                mLoadMoreWrapper.notifyDataSetChanged();
//            }
//        };
//        handler.post(r);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedViewId", selectedViewId);
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

    private void execute() {

    }

    @Override
    public void queryBillListSuccess(GetBillListVo getCourseListVo) {
        loginVos.addAll(getCourseListVo.getItems());
        Logger.d("---"+loginVos.size());
        if(loginVos.size()<10){
            mLoadMoreWrapper.setLoadMoreView(0);
        }
        mLoadMoreWrapper.notifyDataSetChanged();
    }
}
