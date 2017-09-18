package com.seaway.hiver.model.common;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.seaway.android.sdk.SWApplication;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.android.sdk.tools.NetworkUtil;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.start.smartpos.aidl.ServiceProvider;
import com.start.smartpos.aidl.device.printer.PrinterProvider;

import java.util.List;

/**
 * Created by Leo.Chang on 2017/5/20.
 */
public class Application extends SWApplication {
    // 是否登录；true：已登录；false：未登录
    public boolean hasLogin;
    @Override
    public void onCreate() {
        super.onCreate();
        //闪退日志
//        CrashHandler mCrashHandler = CrashHandler.getInstance();
//        mCrashHandler.init(getApplicationContext());
        //SWLog.showLogs=true;//是否打印日志

    }

    // 登录用户信息
    public LoginVo loginVo;

    public static Application getInstance() {
        return (Application) application;
    }

    /**
     * 退出登录
     */
    public void logout() {
        // 网络状态
        boolean networkState = NetworkUtil.checkNetworking();

        hasLogin = false;
//        loginVo = null;
    }
}
