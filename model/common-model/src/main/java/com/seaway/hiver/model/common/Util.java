package com.seaway.hiver.model.common;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.seaway.android.ndk.NativeSDK;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.model.common.data.param.BankBaseParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Leo.Chang on 2017/5/16.
 */

public class Util {
    private static String mPath = Environment.getExternalStorageDirectory().getPath()+ "/hiver";
//    public static<T> T replace(T t) {
//        return t;
//    }

    /**
     * 转换请求参数
     *
     * @param methodId 请求方法名
     * @param t        请求参数
     * @param <T>      请求参数类型
     * @return
     */
    public static <T> BankBaseParam<String> transformat(String methodId, T t) {
        String json = new Gson().toJson(t);
        Logger.i("methodId is : " + methodId + "; param is : " + json);
        return new BankBaseParam<String>(methodId, json);
    }

    /**
     * 转换请求参数
     *
     * @param t        请求参数
     * @param <T>      请求参数类型
     * @return
     */
    public static <T> BankBaseParam<T> transformat( T t) {
        String json = new Gson().toJson(t);
        Logger.i( "param is : " + json);
        return new BankBaseParam<T>(t);
    }

    /**
     * 转换请求参数
     *
     * @param methodId 请求方法名
     * @param t        请求参数
     * @return
     */
    public static BankBaseParam<String> transformatString(String methodId, String t) {
        return new BankBaseParam<String>(methodId, t);
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
    public static   String readFromPath() {
        getDefaultFilePath();
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
            Logger.e("读取成功!"+data);
            return NativeSDK.c(new String(data));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static void setTxt(String txt){
        getDefaultFilePath();
        File f = new File(mPath + "/baseUrl.txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            String info = NativeSDK.b(txt);
            fos.write(info.getBytes());
            fos.close();
            Logger.e("写入成功！");
//            readFromPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查互联网地址是否可以访问-使用get请求
     *
     * @param urlStr   要检查的url
     * @param callback 检查结果回调（是否可以get请求成功）{@see java.lang.Comparable<T>}
     */
    public static void isNetWorkAvailableOfGet(final String urlStr, final Comparable<Boolean> callback) {
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callback != null) {
                    callback.compareTo(msg.arg1 == 0);
                }
            }

        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection conn = new Connection(urlStr);
                    Thread thread = new Thread(conn);
                    thread.start();
                    thread.join(5 * 1000); // 设置等待DNS解析线程响应时间为2秒
                    int resCode = conn.get(); // 获取get请求responseCode
                    msg.arg1 = resCode == 200 ? 0 : -1;
                } catch (Exception e) {
                    msg.arg1 = -1;
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * HttpURLConnection请求线程
     */
    private static class Connection implements Runnable {
        private String urlStr;
        private int responseCode;

        public Connection(String urlStr) {
            this.urlStr = urlStr;
        }

        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                set(conn.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void set(int responseCode) {
            this.responseCode = responseCode;
        }

        public synchronized int get() {
            return responseCode;
        }
    }
}
