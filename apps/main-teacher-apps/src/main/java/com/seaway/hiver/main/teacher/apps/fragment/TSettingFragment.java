package com.seaway.hiver.main.teacher.apps.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiver.app.login.activity.LoginActivity;
import com.hiver.app.login.fragment.LoginFragment;
import com.hiver.app.login.fragment.PasswdModifyChildFragment;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.main.teacher.apps.R;
import com.seaway.hiver.main.teacher.apps.adapter.MainViewAdapter;
import com.seaway.hiver.main.teacher.biz.contract.TMainContract;
import com.seaway.hiver.main.teacher.biz.contract.TWekeContract;
import com.seaway.hiver.main.teacher.biz.presenter.TMainPresenter;
import com.seaway.hiver.main.teacher.biz.presenter.TWekePresenter;
import com.seaway.hiver.model.common.data.vo.QueryAdvertListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetBillListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetCourseListVo;
import com.seaway.hiver.model.main.teacher.data.vo.GetIconCodeVo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

/**
 * 首界面
 * Created by Leo.Chang on 2017/5/10.
 */
public class TSettingFragment extends BaseFragment<TWekeContract.Presenter> implements View.OnClickListener, TWekeContract.View, AdapterView.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private LinearLayout modifyPWLL,checkVersionLL,aboutUsLL;
    private Button loginOutBtn;
    private TextView curVersionTv;
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
        return inflater.inflate(R.layout.fragement_teacher_set, container, false);
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
        if (v.getId() == R.id.teacher_set_loginout_btn) {
            // 退出登录
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
//            addFragment(new LoginFragment(),"loginFragment");
        } else if (v.getId() == R.id.teacher_set_modifypw_ll) {
            // 修改密码
            addFragment(new PasswdModifyChildFragment(),"modifyfragment");
        } else if (v.getId() == R.id.teacher_set_check_version_ll) {
            // 版本检测

        } else if (v.getId() == R.id.teacher_set_about_us_ll) {
            // 关于我们

        }
//
// else if (v.getId() == R.id.bank_portal_network_text) {
//            // 网点
//            mPresenter.checkResource(String.valueOf(v.getTag(R.id.bank_resource_id)));
//        } else if (v.getId() == R.id.ui_view_banner_id) {
//            // Banner图
//            if (null != v.getTag(R.id.ui_view_banner_info) && v.getTag(R.id.ui_view_banner_info) instanceof AdvertVo) {
//                AdvertVo vo = (AdvertVo) v.getTag(R.id.ui_view_banner_info);
//                if (!TextUtils.isEmpty(vo.getUrlPath())) {
//                    addWebViewFragment(vo.getUrlPath());
//                }
//            }
//        } else if (v.getId() == R.id.bank_portal_financial_title_text) {
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

    @Override
    public void queryCourseListSuccess(GetCourseListVo getBillListVo) {

    }


    /**
     * 设置点击事件监听
     */
    private void setOnClickListener() {
        modifyPWLL =(LinearLayout) getView().findViewById(R.id.teacher_set_modifypw_ll);
        checkVersionLL =(LinearLayout) getView().findViewById(R.id.teacher_set_check_version_ll);
        aboutUsLL =(LinearLayout) getView().findViewById(R.id.teacher_set_about_us_ll);
        curVersionTv =(TextView) getView().findViewById(R.id.teacher_set_version_tv);

        modifyPWLL.setOnClickListener(this);
        checkVersionLL.setOnClickListener(this);
        aboutUsLL.setOnClickListener(this);
        getView().findViewById(R.id.teacher_set_loginout_btn).setOnClickListener(this);
//        getView().findViewById(R.id.bank_portal_message_text).setOnClickListener(this);
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

}