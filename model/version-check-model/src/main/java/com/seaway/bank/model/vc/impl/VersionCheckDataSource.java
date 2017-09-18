package com.seaway.bank.model.vc.impl;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.seaway.android.sdk.SWApplication;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.toolkit.SWMD5;
import com.seaway.android.sdk.tools.AndroidUtil;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.bank.model.vc.DownloadProgressListener;
import com.seaway.bank.model.vc.IVersionCheckDataSource;
import com.seaway.bank.model.vc.service.VersionCheckService;
import com.seaway.bank.model.vc.util.DownloadRetrofitClient;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.data.param.BaseCheckInputParam;
import com.seaway.hiver.model.common.data.vo.CheckVersionVo;
import com.seaway.hiver.model.common.data.vo.ClientInfoVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 客户端版本检测
 * Created by Leo.Chang on 2017/6/10.
 */
public class VersionCheckDataSource implements IVersionCheckDataSource {
    /**
     * 客户端版本检测
     *
     * @return
     */
    @Override
    public Observable<CheckVersionVo> checkVersion() {
        return RetrofitClient.getInstance().create(VersionCheckService.class)
                .checkVersion(new BaseCheckInputParam(getClientInfo()))
//                .checkVersion(Util.transformat("checkVersion", new BaseCheckInputParam(getClientInfo())))
                .map(new ServerResponseFunc<CheckVersionVo>(CheckVersionVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<CheckVersionVo>())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<ResponseBody> download(String url ,DownloadProgressListener downloadProgressListener) {
        Logger.i("VersionCheckDataSource -> url is : " + url);
        return new DownloadRetrofitClient(url,downloadProgressListener).create(VersionCheckService.class)
                .downloadApk(url)
                .subscribeOn(Schedulers.io());
    }

    /**
     * 在Application中获取meta-data的值
     *
     * @param key
     * @return
     */
    public static String getMetaValueInApplication(String key) {
        String value = null;
        try {
            ApplicationInfo appInfo = SWApplication.getInstance().getApplicationContext().getPackageManager().getApplicationInfo(
                    SWApplication.getInstance().getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            if (null != appInfo && null != appInfo.metaData) {
                value = appInfo.metaData.getString(key);
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return value;
    }

    public ClientInfoVo getClientInfo() {
        Logger.e("WebView获取客户端信息");
        ClientInfoVo vo = new ClientInfoVo();
        if(DeviceUtil.getDeviceMod().equals("N5")||DeviceUtil.getDeviceManufacturer().equals("SUNMI")||DeviceUtil.getDeviceMod().equals("s1000")){
            vo.setClientType("1");
        }
//        vo.setClientType("1");
        vo.setCurrentVersion(AndroidUtil.getVersionName());
        vo.setMd5(getMD5());
//        vo.setMd5("e22fdd66338888070d737abd27a5c636");
        vo.setVersionState(getMetaValueInApplication("version_type"));
        Logger.e("client info is : " + new Gson().toJson(vo));
        return vo;
        //      openQrCodeScanner("callBack");
//        BaseCheckInputParam param = new BaseCheckInputParam(vo);
//        return new Gson().toJson(param);
//        return "{\"entity\": {\"clientType\": 2,\"currentVersion\": \"1.0.0.0 \",\"md5\": \"string\",\"phoneSystem\": \"AN\",\"versionState\": 1},\"operatorId\": 0}";
    }


    /**
     * 获得安装包MD5值
     *
     * @return
     */
    public static String getMD5() {
        StringBuffer md5 = new StringBuffer();
        try {
            String path = SWApplication.getInstance().getPackageResourcePath();
            File file = new File(path);
            if (file.exists()) {
                md5.append(SWMD5.getFileMD5String(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return md5.toString();
    }


}
