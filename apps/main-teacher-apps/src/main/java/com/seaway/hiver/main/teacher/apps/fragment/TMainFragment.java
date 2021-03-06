package com.seaway.hiver.main.teacher.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.upload.util.FileMapUtil;
import com.seaway.android.sdk.upload.util.UploadUtil;
import com.seaway.android.view.banner.UIBanner;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.apps.adapter.TPortalListAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.main.teacher.biz.presenter.TMainPresenter;
import com.seaway.hiver.model.common.data.vo.AdvertVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.CourseVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetQuestionListVo;
import com.seaway.hiver.model.main.teacher.data.vo.IncomeVo;
import com.seaway.hiver.model.main.teacher.data.vo.QuestionVo;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiao.nicevideoplayer.demo.ProcessHome2Activity;
import com.xiao.nicevideoplayer.demo.TinyWindowPlayActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class TMainFragment extends BaseFragment<TMainContract.Presenter> implements View.OnClickListener, TMainContract.View, AdapterView.OnItemClickListener {

    private RecyclerView recyclerViewAd,recyclerViewWeke,recyclerViewQA,recyclerViewCloudClass,recyclerViewInformation;
    private LinearLayoutManager mLayoutManager;
    private MainViewAdapter mAdapter;
    private  TextView teacherName,inComeTv,accoutMoneyTv;
    private int selectedViewId = -1;
    List<String> mDatas;
    List<CourseVo> courseVoList,weikeVolist;
    private TPortalListAdapter weikeAdapter,cloudAdapter;
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
        LoginVo loginVo =HiverApplication.getInstance().loginVo;
        Logger.d("teacherId---"+loginVo.getTeacherId());
//        mPresenter.queryBillList("1",loginVo.getTeacherId()+"");
        mPresenter.queryMonthIncome();
        mPresenter.queryCourseList("1",loginVo.getTeacherId()+"",1,"");
        mPresenter.queryQuestionList("1","","","","","","");
//        upLoadPic();
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
//        upLoadPic();
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
        this.selectedViewId = v.getId();;
        if (v.getId() == R.id.t_main_portal_accout_detail) {
            // 账单详情
//            mPresenter.checkResource("592");
            addFragment(new TMyAcountFragment(),"myaccount");
        } else if (v.getId() == R.id.t_main_portal_cloud_detail) {
            // 云课程更多
//            Intent intent = new Intent(getActivity(),ProcessHome2Activity.class);
//            startActivity(intent);
//            addFragment(new VideoPlayFragment(),"videoplay");
            addFragment(new TMyWeKeFragment(),"myweke");
        } else if (v.getId() == R.id.t_main_portal_information_detail) {
            // 消息更多
            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
        } else if (v.getId() == R.id.t_main_portal_qa_detail) {
            // 问答更多
            addFragment(new TQAFragment(),"myweke");
//            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
        } else if (v.getId() == R.id.t_main_portal_weke_detail) {
            // 微客更多
            addFragment(new TMyWeKeFragment(),"myweke");
//            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
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

    @Override
    public void queryBillListSuccess(GetBillListVo getBillListVo) {

    }

    @Override
    public void queryCourseListSuccess(GetCourseListVo getCourseListVo) {
        List<CourseVo> list =getCourseListVo.getItems();
        for(int i=0;i<list.size();i++){
            if(list.get(i).getCourseType()==1){//1-微课;2-vip一对一课程;3-vip云课堂
                weikeVolist.add(list.get(i));
            }else if(list.get(i).getCourseType()==3){//1-微课;2-vip一对一课程;3-vip云课堂
                courseVoList.add(list.get(i));
            }
        }
        Logger.d("size--"+weikeVolist.size());
        if(weikeVolist.size()>0){
            weikeAdapter.notifyDataSetChanged();
        }
        if(courseVoList.size()>0){
            cloudAdapter.notifyDataSetChanged();
        }
    }

    public void upLoadPic(){
        //上传请求
        Logger.d("q上传图片");
        String[] path = {"/storage/emulated/0/video/1516098898904.mp4"};
        HashMap<String, String> map = new HashMap<String, String>();
        HiverApplication app = HiverApplication.getInstance();
        map.put("userId", "" + app.loginVo.getTeacherId());
        UploadUtil.upload(new UploadGoodPicHandler(this), map,
                FileMapUtil.getUploadFileMap(path),
                101, 1);
    }

    static class UploadGoodPicHandler extends Handler {
        Fragment mFragment;

        public UploadGoodPicHandler(Fragment fragment) {
            this.mFragment = fragment;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            if (null != ((TMainFragment) mFragment).waitingDialog
//                    && ((TMainFragment) mFragment).waitingDialog.isShowing()) {
//                ((TMainFragment) mFragment).waitingDialog.dismiss();
//            }

            //上传成功
            Logger.d("-----"+(String) msg.obj);
//            new Gson().fromJson((String) msg.obj,GetBillListVo.class);
//            GoodsPicListVo shopPicListVo = JsonVoParser.getResJsonObject((String) msg.obj, GoodsPicListVo.class);
//            if (Constants.SUCCESS_CODE.equals(shopPicListVo.getCode())) {
//                ((TMainFragment) mFragment).updatePicSuccess(shopPicListVo);
//            } else if (Constants.SESSION_TIMEOUT.equals(shopPicListVo.getCode())) {
//                // 如果是登录超时，则跳转到登录界面
////                ICommApplication.getInstance().onLogoutObservable
////                        .onLogoutInBackground();
//            } else if (Constants.USER_STATUS_LOCKED.equals(shopPicListVo.getCode())
//                    || Constants.USER_STATUS_FREEZE.equals(shopPicListVo.getCode())
//                    || Constants.USER_STATUS_DISABLE.equals(shopPicListVo.getCode())) {
//                // 用户状态异常
//                UIDefaultDialogHelper.showDefaultAlert(((TMainFragment) mFragment).getActivity(), shopPicListVo.getMessage(),
//                        new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                // TODO Auto-generated method stub
//                                UIDefaultDialogHelper.dialog.dismiss();
//                                UIDefaultDialogHelper.dialog = null;
//
////                                HiverApplication.getInstance().onLogoutObservable
////                                        .onLogoutInBackground();
//                            }
//                        });
//
//            } else {
////                Toast.showToast(((TMainFragment) mFragment).getActivity(), shopPicListVo.getMessage());
//            }
        }
    }
    
    /**
     * 账单收入获取成功
     * */
    @Override
    public void queryMonthIncomeSuccess(IncomeVo incomeVo) {
        inComeTv.setText("本月收入："+incomeVo.getIncome());
        accoutMoneyTv.setText("总收入："+incomeVo.getIncome());
    }

    @Override
    public void queryQuestionListSuccess(GetQuestionListVo advertListVo) {
        if(advertListVo.getTotalItems()>0){
            recyclerViewQA.setAdapter(new CommonAdapter<QuestionVo>(getActivity(),R.layout.t_main_information_view,advertListVo.getItems()) {
                @Override
                protected void convert(ViewHolder holder,final QuestionVo loginVo, int position) {
                    holder.setText(R.id.t_mian_item_content_tv,loginVo.getContent());
                    holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(),loginVo.getContent(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

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
//        LoginVo loginVo =HiverApplication.getInstance().loginVo;
//        if(loginVo!=null){
//
//        }
        inComeTv =(TextView) getView().findViewById(R.id.tmain_portal_month_income);
        accoutMoneyTv =(TextView) getView().findViewById(R.id.tmain_portal_account);
        banner = (UIBanner)  getView().findViewById(R.id.bank_portal_banner_view);
        banner.setOnBannerClickListener(this);
        teacherName =(TextView) getView().findViewById(R.id.tmain_portal_account_name);
        teacherName.setText(HiverApplication.getInstance().loginVo.getNickname()+"老师好");
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
        if(courseVoList!=null){
            courseVoList.clear();
            courseVoList= null;
            weikeVolist.clear();
            weikeVolist= null;
        }
        courseVoList =new ArrayList<>();
        cloudAdapter =new TPortalListAdapter(getActivity(),R.layout.t_main_weike_view,courseVoList);
        weikeVolist =new ArrayList<>();
        weikeAdapter =new TPortalListAdapter(getActivity(),R.layout.t_main_weike_view,weikeVolist);

        recyclerViewWeke =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_weke);
        recyclerViewWeke.setHasFixedSize(true);
        recyclerViewWeke.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerViewWeke.setItemAnimator(animator);
        recyclerViewWeke.setAdapter(weikeAdapter);
        weikeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                addFragment(new VideoPlayFragment(),"videoplay", new Gson().toJson(weikeVolist.get(position)));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerViewCloudClass =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_cloud_class);
        recyclerViewCloudClass.setHasFixedSize(true);
        recyclerViewCloudClass.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerViewCloudClass.setItemAnimator(animator);
        recyclerViewCloudClass.setAdapter(cloudAdapter);
        cloudAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                addFragment(new VideoPlayFragment(),"videoplay", new Gson().toJson(courseVoList.get(position)));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerViewQA =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_qa);
        recyclerViewQA.setHasFixedSize(true);
        recyclerViewQA.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewQA.setItemAnimator(animator);
//        recyclerViewQA.setAdapter(new CommonAdapter<LoginVo>(getActivity(),R.layout.t_main_information_view,loginVos) {
//            @Override
//            protected void convert(ViewHolder holder,final LoginVo loginVo, int position) {
//                holder.setText(R.id.t_mian_item_content_tv,loginVo.getMobile());
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(),loginVo.getMobile(),Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        recyclerViewInformation =(RecyclerView) getView().findViewById(R.id.main_portal_recycler_view_information);
        recyclerViewInformation.setHasFixedSize(true);
        recyclerViewInformation.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewInformation.setItemAnimator(animator);
        recyclerViewInformation.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.t_main_information_view,mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        });

//        recyclerViewCloudClass.setAdapter(new CommonAdapter<String>(getActivity(),R.layout.t_main_weike_view,mDatas) {
//            @Override
//            protected void convert(ViewHolder holder, String s, final int position) {
//                holder.setText(R.id.textView1,s);
//                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(getActivity(),mDatas.get(position),Toast.LENGTH_SHORT).show();
//                        addData();
//                    }
//                });
//            }
//        });
    }

    private void addData(){
//        mDatas.add("第三次");
//        recyclerViewCloudClass.getAdapter().notifyDataSetChanged();
        Intent intent = new Intent(getActivity(),TinyWindowPlayActivity.class);
        startActivity(intent);
    }

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

    }

}