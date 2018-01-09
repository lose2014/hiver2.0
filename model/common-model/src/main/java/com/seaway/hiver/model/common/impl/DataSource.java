package com.seaway.hiver.model.common.impl;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.google.gson.Gson;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.android.sdk.tools.NetworkUtil;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.RetrofitClient;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.CrashParam;
import com.seaway.hiver.model.common.data.param.CrashInfoParam;
import com.seaway.hiver.model.common.data.param.RequestMessageListParam;
import com.seaway.hiver.model.common.data.param.RequestSmsCodeParam;
import com.seaway.hiver.model.common.data.vo.BaseOutputVo;
import com.seaway.hiver.model.common.data.vo.BaseVo;
import com.seaway.hiver.model.common.data.vo.LoginVo;
import com.seaway.hiver.model.common.data.vo.RequestMessageListVo;
import com.seaway.hiver.model.common.data.vo.RequestSmsCodeVo;
import com.seaway.hiver.model.common.function.ErrorInterceptorFunc;
import com.seaway.hiver.model.common.function.ServerResponseFunc;
import com.seaway.hiver.model.common.service.CommonService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Leo.Chang on 2017/5/17.
 */
public  class DataSource implements IDataSource {

    /**
     * 上传Crash日志
     *
     * @param param 日志内容
     * @return
     */
    @Override
    public Observable<BaseVo> uploadCrashInfo(CrashInfoParam param) {
        return RetrofitClient.getInstance()
                .create(CommonService.class)
                .requestUploadPhoneLogs(new CrashParam(new Gson().toJson(param)))
                .observeOn(Schedulers.newThread());
    }

    @Override
    public Observable<BaseOutputVo> requestSmsCode(String mobile, String businessType, String cardId, String transAmt, String codeId, String code) {
        return null;
    }

    /**
     * 消息列表
     *
     *   @param page 页码1表示第1页，默认为1
     *   @param size 每页数量 默认10
     *    @param messageBelong 消息所属对象(1-教师;2-学生)
     * @return
     */
    @Override
    public Observable<RequestMessageListVo> requestMessageInfo(int page,int size,int messageBelong) {
        return RetrofitClient.getInstance()
                .create(CommonService.class)
                .requestMessageList( Util.transformat(new RequestMessageListParam(page+"",size+"",messageBelong+"",Application.getInstance().loginVo.getTeacherId())))
                .map(new ServerResponseFunc<RequestMessageListVo>(RequestMessageListVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<RequestMessageListVo>())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<BaseOutputVo> requestSmsCode(String mobile, String businessType) {
        Logger.e("super");
        RequestSmsCodeParam param =new RequestSmsCodeParam(mobile,"REGISTER");
        return RetrofitClient.getInstance()
                .create(CommonService.class)
                .requestSmsCode( Util.transformat(param))
                .map(new ServerResponseFunc<BaseOutputVo>(BaseOutputVo.class))
                .onErrorResumeNext(new ErrorInterceptorFunc<BaseOutputVo>())
                .subscribeOn(Schedulers.newThread());
    }


    /**
     * 上传Crash日志
     *
     * @param crashInfo 日志内容
     * @return
     */
    @Override
    public Observable<BaseVo> uploadCrashInfo(final String crashInfo) {
        return Observable
                .create(new ObservableOnSubscribe<CrashInfoParam>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<CrashInfoParam> e) throws Exception {
                        // 构造Crash信息
                        StringBuffer sb = new StringBuffer();
                        CrashInfoParam vo = new CrashInfoParam();
                        vo.setDeviceId(DeviceUtil.getDeviceId());

                        try {
                            PackageManager pm = Application.getInstance().getApplicationContext().getPackageManager();
                            PackageInfo pi = pm.getPackageInfo(Application.getInstance().getApplicationContext().getPackageName(),
                                    PackageManager.GET_ACTIVITIES);
                            if (pi != null) {
                                // 版本号
                                sb.append("versionName=" + pi.versionName + "\n");
                                // 包名
                                sb.append("packageName=" + pi.packageName + "\n");

                                vo.setVersionName(pi.versionName);
                                vo.setPackageName(pi.packageName);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        // 网络状态
                        int networkState = NetworkUtil.checkNetworkingState();
                        vo.setNetworkState("" + networkState);
                        if (-1 == networkState) {
                            sb.append("networkState = 没有可用网络\n");
                        } else if (0 == networkState) {
                            sb.append("networkState = GPRS\n");
                        } else {
                            sb.append("networkState = WIFI\n");
                        }

                        // 有无SD卡
                        boolean hasSDCard = Environment.MEDIA_MOUNTED.equals(Environment
                                .getExternalStorageState());
                        sb.append("hasSDCard = " + hasSDCard + "\n");
                        vo.setHasSdCard(hasSDCard ? "1" : "0");

                        sb.append("system = Android\n");
                        vo.setSystem("Android");

                        sb.append("systemCode = " + Build.VERSION.SDK_INT + "\n");
                        vo.setSystemCode("" + Build.VERSION.SDK_INT);

                        sb.append("crashInfo = " + crashInfo + "\n");
                        vo.setCrashInfo(crashInfo);

                        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
                                Locale.CHINESE);
                        String crashTime = formatter.format(new Date());
                        sb.append("crashTime = " + crashTime + "\n");
                        vo.setCrashTime(crashTime);

                        String manufacture = DeviceUtil.getDeviceManufacturer();
                        sb.append("manufacture = " + manufacture + "\n");
                        vo.setManufacturer(manufacture);

                        String provider = DeviceUtil.getProvideName();
                        sb.append("provider = " + provider + "\n");
                        vo.setProvider(provider);

                        vo.setModel(android.os.Build.MODEL);
                        sb.append("model = " + android.os.Build.MODEL + "\n");


                        vo.setDesc(sb.toString());

                        e.onNext(vo);
                        e.onComplete();
                    }
                })
                .flatMap(new Function<CrashInfoParam, ObservableSource<BaseVo>>() {
                    @Override
                    public ObservableSource<BaseVo> apply(@NonNull CrashInfoParam param) throws Exception {

                        Logger.i("uploadCrashInfo -> thread is : " + Thread.currentThread().getName());

                        // 将Crash信息写入SD卡
                        String path =  Environment.getExternalStorageDirectory().getPath()+ "/crash";
                        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
                                Locale.CHINESE);
                        String crashFileName = formatter.format(new Date())
                                + "_crash.txt";

                        FileOutputStream fos = null;
                        try {
                            // String fileName = "crash.txt";
                            File dir = new File(path);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }

//                            File file = new File(path, crashFileName);
                            File file = new File(path+"/"+crashFileName);
                            Logger.e("crash file is : " + file);
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            fos = new FileOutputStream(file);
                            fos.write(param.getDesc().getBytes());

                            Logger.e("crash info is : " + param.getDesc());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != fos) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                fos = null;
                            }
                            CrashParam crashParam =new CrashParam(new Gson().toJson(param));
                            // 将Crash信息上传服务器
                            return RetrofitClient.getInstance()
                                    .create(CommonService.class)
                                    .requestUploadPhoneLogs(crashParam)
                                    .subscribeOn(Schedulers.newThread());
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }
}