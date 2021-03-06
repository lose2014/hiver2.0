package com.seaway.hiver.teacher;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.apps.common.activity.BaseActivity;
import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.apps.common.util.BackPressedHandler;
import com.seaway.hiver.main.teacher.apps.fragment.THelpFragment;
import com.seaway.hiver.main.teacher.apps.fragment.TMainFragment;
import com.seaway.hiver.main.teacher.apps.fragment.TSettingFragment;
import com.seaway.hiver.teacher.util.VoiceUtil;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    private int selectId;
    // 首页
    RadioButton portalRadio;
    // 设置
    RadioButton settingRadio;
    // 帮助
    RadioButton helpRadio;
    private long exitTime = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 关闭音频播放
//        VoiceUtil.getInstance().stopPlay();
        if (null != getIntent() && null != getIntent().getStringExtra("redirectURL")) {

        }
        initView();
        if (null != savedInstanceState) {
            Logger.i("PortalActivity -> onCreate 还原数据");
            // 还原RadioGroup的显示状态
            this.selectId = savedInstanceState.getInt("checkedRadioButtonID", -1);
            HiverApplication.getInstance().hasLogin = savedInstanceState.getBoolean("hasLogin", false);
            HiverApplication.getInstance().loginVo = savedInstanceState.getParcelable("userInfo");
            return;
        }
        this.selectId = portalRadio.getId();
        portalRadio.setChecked(true);
        transferFragment();
    }

    private void initView(){
        //通过findViewById()来找到我们需要的RadioGroup
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg);
        //设置状态改变的事件
//        radioGroup.setOnCheckedChangeListener(this);

        portalRadio =(RadioButton) findViewById(R.id.tmain_portal_radioButton_portal);
        settingRadio =(RadioButton) findViewById(R.id.tmain_portal_radioButton_setting);
        helpRadio =(RadioButton) findViewById(R.id.tmain_portal_radioButton_help);
        portalRadio.setOnClickListener(this);
        settingRadio.setOnClickListener(this);
        helpRadio.setOnClickListener(this);
    }

    private void transferFragment(){
            mFragmentManager.popBackStack("portal",
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mFragmentTransaction = mFragmentManager.beginTransaction();
            BaseFragment baseFragment =null;
            if (selectId==R.id.tmain_portal_radioButton_portal){
                Drawable dra= getResources().getDrawable(R.drawable.icon_portal_click);
                dra.setBounds( 0, 0, dra.getMinimumWidth(),dra.getMinimumHeight());
                portalRadio.setCompoundDrawables(null,dra,null,null);
                baseFragment = new TMainFragment();
                mFragmentTransaction.replace(R.id.main_fragment_content, baseFragment, "portal");
                mFragmentTransaction.addToBackStack("portal");
            }else if (selectId==R.id.tmain_portal_radioButton_setting){
                baseFragment = new TSettingFragment();
                mFragmentTransaction.replace(R.id.main_fragment_content, baseFragment, "portal");
                mFragmentTransaction.addToBackStack("portal");
            }else if (selectId==R.id.tmain_portal_radioButton_help){
                baseFragment = new THelpFragment();
                mFragmentTransaction.replace(R.id.main_fragment_content, baseFragment, "portal");
                mFragmentTransaction.addToBackStack("portal");
            }

            mFragmentTransaction.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 记录选择的TAB编号
        outState.putInt("checkedRadioButtonID", selectId);

        outState.putBoolean("hasLogin", HiverApplication.getInstance().hasLogin);
        outState.putParcelable("userInfo", HiverApplication.getInstance().loginVo);
    }

    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        // 当确认没有子Fragmnt时可以直接return false

        if (1 == mFragmentManager.getBackStackEntryCount()) {
            // 如果栈中只有1个Fragment，则说明在登录界面，点击退出登录界面
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } else {
            if (!BackPressedHandler.handleBackPress(this)) {
                // 如果栈中没有Fragment拦截返回键
                mFragmentManager.popBackStack();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //根据不同ID 弹出不同的吐司
        if (this.selectId == checkedId) {
            if (mFragmentManager.getBackStackEntryCount() == 1) {
                mFragmentManager.popBackStack("portal", 0);
            }
            return;
        }
        this.selectId = checkedId;
        // 切换界面
        transferFragment();
    }

    @Override
    public void onClick(View v) {
        //根据不同ID 弹出不同的吐司
        if (this.selectId == v.getId()) {
            Logger.e("-----------"+mFragmentManager.getBackStackEntryCount());
            if (mFragmentManager.getBackStackEntryCount() > 1) {
                mFragmentManager.popBackStack("portal", 0);
            }
            return;
        }
        this.selectId = v.getId();
        // 切换界面
        transferFragment();
    }

}
