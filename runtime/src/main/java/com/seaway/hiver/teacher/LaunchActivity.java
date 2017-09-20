package com.seaway.hiver.teacher;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alipay.sdk.app.AuthTask;
import com.hiver.app.vc.ICheckVersionListener;
import com.hiver.app.vc.fragment.VersionCheckFragment;
import com.seaway.android.ndk.NativeSDK;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.toolkit.SWVerificationUtil;
import com.seaway.android.sdk.tools.NetworkUtil;
import com.seaway.hiver.apps.common.activity.BaseActivity;
import com.seaway.hiver.model.common.NetUtil;
import com.seaway.hiver.model.common.Util;
import com.seaway.hiver.model.common.data.param.CrashInfoParam;
import com.hiver.ui.dialog.DefineDialog;
import com.hiver.ui.dialog.UIDefaultDialogHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dell on 2017/6/27.
 */

public class LaunchActivity extends BaseActivity implements ICheckVersionListener {
    private String mPath = Environment.getExternalStorageDirectory().getPath()+ "/hiver";
    private String[] urls=new String[]{"https://www.4008889112.com:18087","https://www.4008889112.com:18088","https://www.4008889112.cn:18087"};
    private int i=0;

    private EditText nameEt,passWordEt;
    private Button forgetBtn,loginBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if(NetworkUtil.checkNetworking()){
            startToMain();
//            checkVersion();
//            if(!SWVerificationUtil.isEmpty(Util.readFromPath())){
//                checkNetWork(Util.readFromPath());
//            }else{
//            Logger.e("----------------------------"+urls[10]);
//                checkNetWork(urls[0]);
//            }
        }else{
            // 无网络
            final DefineDialog mDefineDialog = new DefineDialog(this);
            mDefineDialog.setMessage(getText(R.string.e1000_no_net).toString());
            mDefineDialog.setMiddleButton("确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDefineDialog.dismiss();
                    finish();
                }
            });
            mDefineDialog.show();
        }

    }


    private static long getEnvironmentSize()
    {
        File localFile = Environment.getDataDirectory();
        long l1;
        if (localFile == null)
            l1 = 0L;
        while (true)
        {

            String str = localFile.getPath();
            StatFs localStatFs = new StatFs(str);
            long l2 = localStatFs.getBlockSize();
            l1 = localStatFs.getBlockCount() * l2;
            return l1;
        }
    }

    //卸载应用程序
    public void unstallApp(){
        PackageManager pm = getPackageManager();
        Class[] arrayOfClass = new Class[2];
        Class localClass2 = Long.TYPE;
        arrayOfClass[0] = localClass2;
        arrayOfClass[1] = IPackageDataObserver.class;
        Method localMethod = null;
        try {
            localMethod = pm.getClass().getMethod("freeStorageAndNotify", arrayOfClass);
            Long localLong = Long.valueOf(getEnvironmentSize() - 1L);
            Object[] arrayOfObject = new Object[2];
            arrayOfObject[0] = localLong;
            try {
                localMethod.invoke(pm,localLong,new IPackageDataObserver.Stub(){
                    public void onRemoveCompleted(String packageName,boolean succeeded) throws RemoteException {
                        // TODO Auto-generated method stub
                    }});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测哪个服务端地址可用
     */
    private void checkNetWork(final String url){
            Logger.e(url+"连接中..."+i);
            Util.isNetWorkAvailableOfGet(url, new Comparable<Boolean>() {
                @Override
                public int compareTo(@NonNull Boolean available) {
                    Logger.e(url+"连接返回..."+available);
                    if (available) {// 设备访问Internet正常
                        Logger.e(url+"可用");
                        Util.setTxt(url);
                        NetUtil.setWebViewUrl(url);
                        checkVersion();
                    } else {// 设备无法访问Internet
                        if(i<urls.length) {
                            if (!urls[i].equals(url)) {
                                i++;
                                checkNetWork(urls[i - 1]);
                            } else {
                                i++;
                                if(i!=urls.length) {
                                    checkNetWork(urls[i]);
                                }else{
                                    Logger.e(i+"结束？");
                                    UIDefaultDialogHelper.showDefaultAlert(LaunchActivity.this, "您的网络差，建议更换网络后重试！", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finish();
                                        }
                                    });
                                }
                            }
                        }else{
                            Logger.e(i+"结束？");
                            UIDefaultDialogHelper.showDefaultAlert(LaunchActivity.this, "您的网络差，建议更换网络后重试！", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                        }
                    }
                    return 0;
                }
            });
    }

    public boolean clear(){
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        try {
            Class<?> amClass = Class.forName(am.getClass().getName());
            Method clearApp = amClass.getMethod("clearApplicationUserData", String.class, IPackageDataObserver.class);
            Logger.d( "clearApp: " + clearApp.getName());
            clearApp.invoke(am, "com.seaway.hiver", new IPackageDataObserver() {
                @Override
                public IBinder asBinder() {
                    return null;
                }

                @Override
                public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {

                }
            });
        } catch (Exception e) {
            Logger.d( "Exception: " + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 版本检测
     */
    private void checkVersion() {
        VersionCheckFragment checkVersionFragment = (VersionCheckFragment) mFragmentManager
                .findFragmentByTag("VersionCheckFragment");
        if (null == checkVersionFragment) {
            checkVersionFragment = new VersionCheckFragment();
            mFragmentManager.beginTransaction()
                    .add(checkVersionFragment, "VersionCheckFragment").commitAllowingStateLoss();
        }
    }

    /**
     * 到登录页
     */
    private void startToMain() {
//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
    }

    @Override
    public void onCheckVersionComplete(int result) {
        if (result == ICheckVersionListener.CHECK_VERSION_NO_UPDATE) {
            // 开启启动页服务
            startToMain();
        } else if (result == ICheckVersionListener.CHECK_VERSION_COMPLETE_AND_DOWNLOAD_FINISH) {
            finish();
        }
    }

    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;
    }

    /**
     * 获取默认的文件路径
     *
     * @return
     */
    public static void getDefaultFilePath() {
        String filepath = "";
        File sd = Environment.getExternalStorageDirectory();
        String mPath = sd.getPath() + "/hiver";
        File file = new File(mPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }
    /**
     * 读取文件信息
     *
     * @return
     */
    public String readFromPath() {
        byte[] data = new byte[1024];
        try {
            FileInputStream fileIS = new FileInputStream(mPath + "/baseUrl.txt");
            // BufferedReader buf = new BufferedReader(new
            // InputStreamReader(in))
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int len = -1;
            while ((len = fileIS.read(buffer)) != -1) {// 读取文件到缓冲区
                byteArrayOutputStream.write(buffer, 0, len);// 写入到内存
            }
            data = byteArrayOutputStream.toByteArray();// 将内存中的二进制数据转为数组
            byteArrayOutputStream.close();
            fileIS.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Logger.e("读取成功---"+NativeSDK.c(new String(data)));
        return new String(data);
    }

    public void setTxt(String txt){
        getDefaultFilePath();
        File f = new File(mPath + "/baseUrl.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            String info = NativeSDK.b( txt);
            fos.write(info.getBytes());
            fos.close();
            Logger.e("写入成功！"+mPath);
            readFromPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
