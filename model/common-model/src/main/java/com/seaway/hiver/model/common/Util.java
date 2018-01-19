package com.seaway.hiver.model.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.google.gson.Gson;
import com.seaway.android.ndk.NativeSDK;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.model.common.data.param.BankBaseParam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by Leo.Chang on 2017/5/16.
 */

public class Util {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static File file;
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
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(Context context, int type)
    {
        Uri uri = null;
        //适配Android N
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile(type));
        } else
        {
            return Uri.fromFile(getOutputMediaFile(type));
        }
    }

    /**
     * Create a File for saving an image or video
     */
    public static File getOutputMediaFile(int type)
    {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "image");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else
        {
            return null;
        }
        file = mediaFile;
        return mediaFile;
    }

    /**
     * 获取视频的第一帧图片
     */
    public static void getImageForVideo(String videoPath, OnLoadVideoImageListener listener)
    {
        LoadVideoImageTask task = new LoadVideoImageTask(listener);
        task.execute(videoPath);
    }

    public static class LoadVideoImageTask extends AsyncTask<String, Integer, File>
    {
        private OnLoadVideoImageListener listener;

        public LoadVideoImageTask(OnLoadVideoImageListener listener)
        {
            this.listener = listener;
        }

        @Override
        protected File doInBackground(String... params)
        {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            String path = params[0];
            if (path.startsWith("http"))
                //获取网络视频第一帧图片
                mmr.setDataSource(path, new HashMap());
            else
                //本地视频
                mmr.setDataSource(path);
            Bitmap bitmap = mmr.getFrameAtTime();
            //保存图片
            File f = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (f.exists())
            {
                f.delete();
            }
            try
            {
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            mmr.release();
            return f;
        }

        @Override
        protected void onPostExecute(File file)
        {
            super.onPostExecute(file);
            if (listener != null)
            {
                listener.onLoadImage(file);
            }
        }
    }

    public interface OnLoadVideoImageListener
    {
        void onLoadImage(File file);
    }

    /**
     * 获取本地视频的第一帧
     *
     * @param localPath
     * @return
     */
    public static Bitmap getLocalVideoBitmap(String localPath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(localPath);
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static Bitmap createVideoThumbnail(String filePath, int kind)
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try
        {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://"))
            {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            }
            else
            {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        }
        catch (IllegalArgumentException ex)
        {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        }
        catch (RuntimeException ex)
        {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                retriever.release();
            }
            catch (RuntimeException ex)
            {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }

        if (bitmap == null)
        {
            return null;
        }
        bitmap = compressImage(bitmap);
//        if (kind == MediaStore.Images.Thumbnails.MINI_KIND)
//        {//压缩图片 开始处
//            // Scale down the bitmap if it's too large.
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            int max = Math.max(width, height);
//            if (max > 512)
//            {
//                float scale = 512f / max;
//                int w = Math.round(scale * width);
//                int h = Math.round(scale * height);
//                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
//            }//压缩图片 结束处
//        }
//        else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND)
//        {
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
//                    96,
//                    96,
//                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        }
        return bitmap;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
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
