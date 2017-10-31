package com.seaway.hiver.main.teacher.apps.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.view.banner.UIBanner;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.main.teacher.biz.presenter.TMainPresenter;
import com.seaway.hiver.model.common.data.vo.AdvertVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 首界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class TMainFragment extends BaseFragment<TMainContract.Presenter> implements View.OnClickListener, TMainContract.View, AdapterView.OnItemClickListener {

    private RecyclerView recyclerViewAd,recyclerViewWeke,recyclerViewQA,recyclerViewCloudClass,recyclerViewInformation;
    private LinearLayoutManager mLayoutManager;
    private MainViewAdapter mAdapter;

    private int selectedViewId = -1;
    List<String> mDatas;
    private RxPermissions permissions;
    private UIBanner banner;
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
        return inflater.inflate(R.layout.fragment_main_portal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setOnClickListener();
        initUI();
        QueryAdvertListVo queryAdvertListVo=null;
        updateView(queryAdvertListVo);
    }

    public void updateView(QueryAdvertListVo vo) {
        if (null == vo) {
            List<AdvertVo> list=new ArrayList<>();
            AdvertVo advertVo =new AdvertVo();
            advertVo.setContent("1111");
            advertVo.setId("1");
            advertVo.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508208411&di=f5256dc29ef6b48ec5f4d28e629ba2ae&imgtype=jpg&er=1&src=http%3A%2F%2F5.66825.com%2Fdownload%2Fpic%2F000%2F330%2Fcf0f6d186a2f14b9dbf1df36b271ae8c.jpg");
            advertVo.setUrlPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508208411&di=f5256dc29ef6b48ec5f4d28e629ba2ae&imgtype=jpg&er=1&src=http%3A%2F%2F5.66825.com%2Fdownload%2Fpic%2F000%2F330%2Fcf0f6d186a2f14b9dbf1df36b271ae8c.jpg");
            list.add(advertVo);
            AdvertVo advertVo2 =new AdvertVo();
            advertVo2.setContent("2222");
            advertVo2.setId("2");
            advertVo2.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507613691076&di=66a45ea1454ab7f11fdcf549b817bc93&imgtype=0&src=http%3A%2F%2Fimgm.cnmo-img.com.cn%2Fappimg%2Fscreenpic%2Fmiddle%2F779%2F778678.jpg");
            advertVo2.setUrlPath("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1507613691076&di=66a45ea1454ab7f11fdcf549b817bc93&imgtype=0&src=http%3A%2F%2Fimgm.cnmo-img.com.cn%2Fappimg%2Fscreenpic%2Fmiddle%2F779%2F778678.jpg");
            list.add(advertVo2);
            vo =new QueryAdvertListVo();
            vo.setAdvertInfos(list);
            banner.setIsLoop("0".equals(vo.getLoopView()));
            banner.setDuration(Integer.parseInt(vo.getViewTime()));
            banner.notifyDataChanged(vo.getAdvertInfos());
//            banner.notifyDataChanged(null);
        } else {
            banner.setIsLoop("0".equals(vo.getLoopView()));
            banner.setDuration(Integer.parseInt(vo.getViewTime()));
            banner.notifyDataChanged(vo.getAdvertInfos());
        }
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
        // 从缓存中获取轮播广告及理财数据
//        mPresenter.subscribe();
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
        if (v.getId() == R.id.t_main_portal_accout_detail) {
            // 账单详情
            mPresenter.checkResource("592");
        } else if (v.getId() == R.id.t_main_portal_cloud_detail) {
            // 云课程更多
        } else if (v.getId() == R.id.t_main_portal_information_detail) {
            // 消息更多
            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
        } else if (v.getId() == R.id.t_main_portal_qa_detail) {
            // 问答更多
            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
        } else if (v.getId() == R.id.t_main_portal_weke_detail) {
            // 微客更多
            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
        } else if (v.getId() == R.id.ui_view_banner_id) {
            // Banner图
            if (null != v.getTag(R.id.ui_view_banner_info) && v.getTag(R.id.ui_view_banner_info) instanceof AdvertVo) {
                AdvertVo vo = (AdvertVo) v.getTag(R.id.ui_view_banner_info);
                if (!TextUtils.isEmpty(vo.getUrlPath())) {
                    addWebViewFragment(vo.getUrlPath());
                }
            }
        }
//
// else if (v.getId() == R.id.bank_portal_financial_title_text) {
//            // 更多理财
//            mPresenter.checkResource("150");
//        } else if (v.getId() == R.id.bank_portal_financial_info_layout) {
//            // 理财产品
//            if (null != v.getTag()) {
//                // 理财详情
//                addWebViewFragment(NetUtil.web_view_path + "/financial/productdetail?prdCode=" + v.getTag());
//            }
//        }
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
//        if (selectedViewId == R.id.bank_portal_network_text) {
//            // 网点查询
//            permissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
//                    .subscribe(new Consumer<Permission>() {
//                        @Override
//                        public void accept(@io.reactivex.annotations.NonNull Permission permission) throws Exception {
//                            if (permission.granted) {
//                                // 同意打开权限
//                                addWebViewFragment(NetUtil.web_view_path + "/branch/list");
//                            } else if (permission.shouldShowRequestPermissionRationale) {
//                                //  询问
//                                Logger.i("禁止");
//                                addWebViewFragment(NetUtil.web_view_path + "/branch/list");
//                            } else {
//                                UIDefaultDialogHelper.showPermissionAskDialog(getActivity(), "位置权限未开启", "请在”设置—邢台银行—权限“中打开开关， 并允许邢台银行访问您的位置信息", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        UIDefaultDialogHelper.dialog.dismiss();
//                                        UIDefaultDialogHelper.dialog = null;
//                                        addWebViewFragment(NetUtil.web_view_path + "/branch/list");
//                                    }
//                                });
//                            }
//                        }
//                    });
//        } else if (selectedViewId == 2) {
//            // 理财产品
//            addWebViewFragment(NetUtil.web_view_path + "/financial/index");
//        } else if (selectedViewId == 3) {
//            // 贷款业务
//            addWebViewFragment(NetUtil.web_view_path + "/loan/index");
//        } else if (selectedViewId == 4) {
//            // 定活互转
//            addWebViewFragment(NetUtil.web_view_path + "/dhhz/index");
//        } else if (selectedViewId == 6) {
//            // 生活缴费
//            addWebViewFragment(NetUtil.web_view_path + "/lifeCost/index");
//        } else if (selectedViewId == 9) {
//            // 金融助手
//            addWebViewFragment(NetUtil.web_view_path + "/financialAide/index");
//        } else if (selectedViewId == 10) {
//            // 电信缴费
//            addWebViewFragment(NetUtil.web_view_path + "/telecompayment/query");
//        } else if (selectedViewId == 12) {
//            // 方付通
//            if (XtBankApplication.getInstance().hasLogin && null != XtBankApplication.getInstance().loginVo) {
//                // 如果已登录
//                mPresenter.getWebCommunitySign();
//            } else {
//                // 如果没有登录
//                FFTUtil.toFFT(getActivity(), null);
//            }
//        } else if (selectedViewId == R.id.bank_portal_financial_title_text) {
//            // 理财产品
//            addWebViewFragment(NetUtil.web_view_path + "/financial/index");
//        }
    }


    /**
     * 广告页查询成功
     *
     * @param advertVo 广告位信息
     * @param flag     true：要更新；false：不需要更新
     */
    @Override
    public void queryAdvertListSuccess(QueryAdvertListVo advertVo, boolean flag) {
//        mAdapter.setAdvertListVo(advertVo);
//        mAdapter.notifyItemChanged(2);
    }

    /**
     * 获取缓存广告信息成功
     *
     * @param advertListVo 广告信息
     */
    @Override
    public void queryAdvertListInCacheSuccess(QueryAdvertListVo advertListVo) {
//        mAdapter.setAdvertListVo(advertListVo);
//        mAdapter.notifyItemChanged(2);
//        // 先显示缓存数据，再去服务器获取新的数据
//        mPresenter.queryAdvertList(1);
    }

    /**
     * 查询理财产品成功
     *
     * @param financial 理财产品
     */
    @Override
    public void queryFinancialSuccess( GetIconCodeVo financial) {
//        mAdapter.setFinanceProductVo(financial);
//        mAdapter.notifyItemChanged(3);
    }

    /**
     * 查询缓存理财产品成功
     *
     * @param financial 理财产品
     */
    @Override
    public void queryFinancialInCacheSuccess(GetIconCodeVo financial) {
        // 先显示缓存数据，再去服务器获取新的数据
//        mAdapter.setFinanceProductVo(financial);
//        mAdapter.notifyItemChanged(3);
//        mPresenter.queryFinancialInfo(1);
    }

    /**
     * 初始化view
     * */
    private void initUI(){
        banner = (UIBanner)  getView().findViewById(R.id.bank_portal_banner_view);
        banner.setOnBannerClickListener(this);
        // 初化 recyclerViewAd
        recyclerViewAd = (RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_ad);
        recyclerViewAd.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewAd.setLayoutManager(mLayoutManager);
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };
        recyclerViewAd.setItemAnimator(animator);

        mAdapter = new MainViewAdapter(this, this);
        recyclerViewAd.setAdapter(mAdapter);

        mDatas =new ArrayList<>();
        mDatas.add("第一次");
        mDatas.add("第二次");
        List<LoginVo> loginVos =new ArrayList<>();
        for(int i=0;i<6;i++){
            LoginVo loginVo =new LoginVo();
            loginVo.setMobile("1810838"+i+"电话呀");
            loginVos.add(loginVo);
        }

        recyclerViewWeke =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_weke);
        recyclerViewWeke.setHasFixedSize(true);
        recyclerViewWeke.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerViewWeke.setItemAnimator(animator);

        recyclerViewCloudClass =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_cloud_class);
        recyclerViewCloudClass.setHasFixedSize(true);
        recyclerViewCloudClass.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerViewCloudClass.setItemAnimator(animator);

        recyclerViewQA =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_qa);
        recyclerViewQA.setHasFixedSize(true);
        recyclerViewQA.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewQA.setItemAnimator(animator);
        recyclerViewQA.setAdapter(new CommonAdapter<LoginVo>(getActivity(),R.layout.t_main_information_view,loginVos) {
            @Override
            protected void convert(ViewHolder holder,final LoginVo loginVo, int position) {
                holder.setText(R.id.t_mian_item_content_tv,loginVo.getMobile());
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),loginVo.getMobile(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        recyclerViewInformation =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_information);
        recyclerViewInformation.setHasFixedSize(true);
        recyclerViewInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewInformation.setItemAnimator(animator);
        recyclerViewInformation.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.t_main_information_view,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        });

        recyclerViewCloudClass.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.t_main_weike_view,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, final int position) {
                holder.setText(R.id.textView1,s);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),mDatas.get(position),Toast.LENGTH_SHORT).show();
//                        addData();
                    }
                });
            }
        });
    }

//    private void addData(){
//        mDatas.add("第三次");
//        recyclerViewCloudClass.getAdapter().notifyDataSetChanged();
//    }

    /**
     * 设置点击事件监听
     */
    private void setOnClickListener() {
        getView().findViewById(R.id.t_main_portal_accout_detail).setOnClickListener(this);
        getView().findViewById(R.id.t_main_portal_cloud_detail).setOnClickListener(this);
        getView().findViewById(R.id.t_main_portal_information_detail).setOnClickListener(this);
        getView().findViewById(R.id.t_main_portal_qa_detail).setOnClickListener(this);
        getView().findViewById(R.id.t_main_portal_weke_detail).setOnClickListener(this);
    }

    private void execute() {
//        if (selectedViewId == R.id.bank_portal_bill_text) {
//            // 我的账单
//            addWebViewFragment(NetUtil.web_view_path + "/bankBill/list");
//        } else if (selectedViewId == R.id.bank_portal_message_text) {
//            // 消息
//
//        } else if (selectedViewId == R.id.bank_portal_scan_text) {
//            // 扫一扫
//            Logger.i("扫一扫");
//            mPresenter.queryOpenQRCode();
//        } else if (selectedViewId == R.id.bank_portal_payments_text) {
//            // 收/付款
//            Logger.i("收/付款");
//            addWebViewFragment(NetUtil.web_view_path + "/receiving/index");
//        }
//        switch (selectedViewId) {
//            case 0: {
//                // 我的资产
//                addWebViewFragment(NetUtil.web_view_path + "/myassets/index");
//            }
//            break;
//            case 1: {
//                // 转账汇款
//                addWebViewFragment(NetUtil.web_view_path + "/transfer/index");
//            }
//            break;
//            case 5: {
//                // 无卡存取款
//                addWebViewFragment(NetUtil.web_view_path + "/noCard/index");
//            }
//            break;
//            case 7: {
//                // 话费充值
//                addWebViewFragment(NetUtil.web_view_path + "/telephoneRecharge/Index");
//            }
//            break;
//            case 8: {
//                // 公务卡
//                addWebViewFragment(NetUtil.web_view_path + "/office/index");
//            }
//            break;
//            case 11: {
//                // 金牛通
//                addWebViewFragment(NetUtil.web_view_path + "/taurustong/index");
//            }
//            break;
//            case 12: {
//                // 方付通
//
//            }
//            break;
//            case 13: {
//                // 积分商城
//                Fragment fragment = new IntegralMallFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", "http://192.168.32.93:28600/miController/getjfMallPage");
//                fragment.setArguments(bundle);
//                FragmentTransaction t = mFragmentManager.beginTransaction();
//                t.hide(this).add(com.xtbank.app.common.R.id.portal_fragment_content_layout, fragment, "IntegralMallFragment");
//                t.addToBackStack("IntegralMallFragment");
//                t.commit();
//            }
//            break;
//        }
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