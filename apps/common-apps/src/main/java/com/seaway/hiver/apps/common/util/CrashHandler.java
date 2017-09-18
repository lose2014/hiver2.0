package com.seaway.hiver.apps.common.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.android.sdk.tools.DeviceUtil;
import com.seaway.android.sdk.tools.NetworkUtil;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.model.common.IDataSource;
import com.seaway.hiver.model.common.data.param.CrashInfoParam;
import com.seaway.hiver.model.common.impl.DataSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Leo.Chang on 2017/6/20.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private volatile static CrashHandler instance;

    private IDataSource dataSource = new DataSource();

    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    /**
     * 日期格式化工具类
     */
    private DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
            Locale.CHINESE);
    /**
     * 文件保存路径
     */
    protected String path;

    /**
     * Crash文件名
     */
    protected String crashFileName = formatter.format(new Date())
            + "_crash.txt";

    /**
     * 发送日志内容
     */
    private StringBuffer sb;


    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (null == instance) {
            synchronized (CrashHandler.class) {
                if (null == instance) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init() {
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);

        path = HiverApplication.getInstance().getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                + "/crash";
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        boolean flag = handleException(e);
        if (!flag && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                // SWLog.LogE("error is : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(HiverApplication.getInstance().getApplicationContext(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }
        }.start();

        // 保存日志到手机本地
        dataSource.uploadCrashInfo(result).subscribe();

        // 保存日志到手机本地
//        saveInfoToFile(result);

        return true;
    }

    /**
     * 保存日志到手机本地
     *
     * @param result crash日志
     */
    private void saveInfoToFile(String result) {
        sb = new StringBuffer();
        CrashInfoParam vo = new CrashInfoParam();

        try {
            PackageManager pm = HiverApplication.getInstance().getApplicationContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(HiverApplication.getInstance().getApplicationContext().getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                // 版本号
                sb.append("versionName=" + pi.versionName + "\n");
                // 包名
                sb.append("packageName=" + pi.packageName + "\n");

                vo.setVersionName(pi.versionName);
                vo.setPackageName(pi.packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        sb.append("systemCode = " + Build.VERSION.SDK_INT + "\n");
        vo.setSystemCode("" + Build.VERSION.SDK_INT);

        sb.append("crashInfo = " + result + "\n");
        vo.setCrashInfo(result);

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
        sb.append("provider = " + android.os.Build.MODEL + "\n");
        vo.setProvider(android.os.Build.MODEL);

        FileOutputStream fos = null;
        try {
            // String fileName = "crash.txt";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(Environment.getExternalStorageDirectory().getPath()+"/"+crashFileName);
            Logger.e("crash file is : " + file);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());

            Logger.e("crash info is : " + sb);

            // 发送给开发人员
            sendCrashLog(vo);
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
        }
    }

    private void sendCrashLog(CrashInfoParam param) {
        dataSource.uploadCrashInfo(param).subscribe();
    }
}
