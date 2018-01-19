package com.seaway.hiver.model.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.model.common.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 从SDCard异步加载图片
 *
 * @author hanj 2013-8-22 19:25:46
 */
public class SDCardImageLoader {
    private Context context;
    // 缓存
    private LruCache<String, Bitmap> imageCache;
    // 固定2个线程来执行任务
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private Handler handler = new Handler();

    private int screenW, screenH;

    private static class SingletonHolder {
        /**
         * 单例对象实例
         */
        // 获取应用程序最大可用内存
        static int maxMemory = (int) Runtime.getRuntime().maxMemory();
        static int cacheSize = maxMemory / 8;

        static final LruCache<String, Bitmap> INSTANCE = new LruCache<String, Bitmap>(
                cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    public SDCardImageLoader(int screenW, int screenH) {
        this.screenW = screenW;
        this.screenH = screenH;

        // 设置图片缓存大小为程序最大可用内存的1/8
        imageCache = SingletonHolder.INSTANCE;

        // new LruCache<String, Bitmap>(cacheSize) {
        // @Override
        // protected int sizeOf(String key, Bitmap value) {
        // return value.getRowBytes() * value.getHeight();
        // }
        // };
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    private  Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;
        if (degree==0)
            return bm;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        return returnBm;
    }
    public Bitmap loadDrawable(final String filePath, final int smallRate,
                               final ImageCallback callback) {

        // 如果缓存过就从缓存中取出数据
        Bitmap cache=imageCache.get(filePath);
        if (cache != null) {
            handler.post(new Runnable() {
                public void run() {
                    callback.imageLoaded(imageCache.get(filePath), filePath);
                }
            });
            return imageCache.get(filePath);
        }

        // 如果缓存没有则读取SD卡
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    if (filePath != null && filePath.startsWith("http")) {
                      final Bitmap imgNet= ImageLoader.getInstance().loadImageSync(filePath);
    //                    Bitmap img=imgNet;
  //                      int size1=0,size2=0,size3=0;
                        // 存入map
//                        if (imgNet != null){
//                            ByteArrayOutputStream baos = null ;
//                            try{
//                                baos = new ByteArrayOutputStream();
//                                img.compress(Bitmap.CompressFormat.JPEG, 30, baos);
//                                byte[] bytes=baos.toByteArray();
//                                img= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                            }finally{
//                                try {
//                                    if(baos != null)
//                                        baos.close() ;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
//                            size2=  img.getAllocationByteCount();
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
//                            size2=    img.getByteCount();
//                        }
                        if(imgNet!=null)
                        imageCache.put(filePath, imgNet);
                        handler.post(new Runnable() {
                            public void run() {
                                callback.imageLoaded(imgNet, filePath);
                            }
                        });
                    } else {
                      int  degree= getBitmapDegree(filePath);
                        Logger.e("degree---"+degree);
                        BitmapFactory.Options opt = new BitmapFactory.Options();
                        opt.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(filePath, opt);

                        // 获取到这个图片的原始宽度和高度
                        int picWidth = opt.outWidth;
                        int picHeight = opt.outHeight;

                        Logger.e("outWidth--->"+opt.outHeight);
                        // 读取图片失败时直接返回
                        if (picWidth == 0 || picHeight == 0) {
                            return;
                        }
                        //获取图片大小，小于1m不压缩。
                        float size = getImageSize(filePath);
                        if (size < 666)
                            opt.inSampleSize = 1;// 初始压缩比例
                        else
                            opt.inSampleSize = smallRate;// 初始压缩比例
                        // 根据屏的大小和图片大小计算出缩放比例
                        if (picWidth > picHeight) {

                            if (picWidth > screenW)
                                opt.inSampleSize *= picWidth / screenW;
                        } else {
                            if (picHeight > screenH)
                                opt.inSampleSize *= picHeight / screenH;
                        }

                        // 这次再真正地生成一个有像素的，经过缩放了的bitmap
                        opt.inJustDecodeBounds = false;
                        Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){    //API 19
//                            int size1=  bmp.getAllocationByteCount();
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
//                            int size1=    bmp.getByteCount();
//                        }
                        if(bmp != null){
                            final Bitmap bmp1=  rotateBitmapByDegree(bmp,degree);
                            imageCache.put(filePath, bmp1);
                            handler.post(new Runnable() {
                                public void run() {
                                    callback.imageLoaded(bmp1, filePath);
                                }
                            });
                        }
                        // 存入map
//                        if (bmp != null)
//                            imageCache.put(filePath, bmp);
//                        handler.post(new Runnable() {
//                            public void run() {
//                                callback.imageLoaded(bmp, filePath);
//                            }
//                        });
                    }
                } catch (Exception e) {
                    Logger.e("图片压缩出错啦...");
                    e.printStackTrace();
                }

            }
        });

        return null;
    }

    private Bitmap loadDrawable(final int smallRate, final String filePath,
                                final ImageCallback callback) {

        // 如果缓存过就从缓存中取出数据
        if (imageCache.get(filePath) != null) {
            handler.post(new Runnable() {
                public void run() {
                    callback.imageLoaded(imageCache.get(filePath));
                }
            });
            return imageCache.get(filePath);
        }

        // 如果缓存没有则读取SD卡
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, opt);

                    // 获取到这个图片的原始宽度和高度
                    int picWidth = opt.outWidth;
                    int picHeight = opt.outHeight;

                    // 读取图片失败时直接返回
                    if (picWidth == 0 || picHeight == 0) {
                        return;
                    }
                    //获取图片大小，小于1m不压缩。
                    float size = getImageSize(filePath);
                    if (size < 666)
                        opt.inSampleSize = 1;// 初始压缩比例
                    else
                        opt.inSampleSize = smallRate;// 初始压缩比例
                    // 根据屏的大小和图片大小计算出缩放比例
                    if (picWidth > picHeight) {
                        if (picWidth > screenW)
                            opt.inSampleSize *= picWidth / screenW;
                    } else {
                        if (picHeight > screenH)
                            opt.inSampleSize *= picHeight / screenH;
                    }

                    // 这次再真正地生成一个有像素的，经过缩放了的bitmap
                    opt.inJustDecodeBounds = false;
                    final Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);

                    // 存入map
                    if (bmp != null)
                        imageCache.put(filePath, bmp);
                    handler.post(new Runnable() {
                        public void run() {
                            callback.imageLoaded(bmp);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return null;
    }

    /**
     * 异步读取SD卡图片，并按指定的比例进行压缩（最大不超过屏幕像素数）
     *
     * @param smallRate 压缩比例，不压缩时输入1，此时将按屏幕像素数进行输出
     * @param filePath  图片在SD卡的全路径
     * @param imageView 组件
     */
    public void loadImage(final Context context, int smallRate,
                          final String filePath, final ImageView imageView) {
        this.context = context;
        Bitmap bmp = loadDrawable(smallRate, filePath, new ImageCallback() {

            @Override
            public void imageLoaded(Bitmap bmp) {
                if (imageView.getTag().equals(filePath)) {
                    if (bmp != null) {
                        imageView.setImageDrawable(null);
                        imageView.setImageBitmap(bmp);
                    } else {
                        bmp = BitmapFactory.decodeResource(
                                context.getResources(), R.drawable.empty_photo);
                        imageView.setImageBitmap(bmp);
                        // imageView.setImageResource(R.drawable.empty_photo);
                    }
                }
            }

            @Override
            public void imageLoaded(Bitmap imageDrawable, String path) {

            }
        });

        // if (bmp != null) {
        // if (imageView.getTag().equals(filePath)) {
        // imageView.setImageBitmap(bmp);
        // }
        // } else {
        // bmp = BitmapFactory.decodeResource(context.getResources(),
        // R.drawable.empty_photo);
        // imageView.setImageBitmap(bmp);
        // //imageView.setImageResource(R.drawable.empty_photo);
        // }

    }

    /**
     * 异步读取SD卡图片，并按指定的比例进行压缩（最大不超过屏幕像素数）
     *
     * @param smallRate 压缩比例，不压缩时输入1，此时将按屏幕像素数进行输出
     * @param filePath  图片在SD卡的全路径
     * @param imageView 组件
     */
    public void loadImage(int smallRate, final String filePath,
                          final ImageView imageView) {
        Bitmap bmp = loadDrawable(smallRate, filePath, new ImageCallback() {

            @Override
            public void imageLoaded(Bitmap bmp) {
                if (imageView.getTag().equals(filePath)) {
                    if (bmp != null) {
                        imageView.setImageBitmap(bmp);
                    } else {
                        // bmp =
                        // BitmapFactory.decodeResource(context.getResources(),
                        // R.drawable.empty_photo);
                        // imageView.setImageBitmap(bmp);
                        imageView.setImageResource(R.drawable.empty_photo);
                    }
                }
            }

            @Override
            public void imageLoaded(Bitmap imageDrawable, String path) {

            }
        });

        if (bmp != null) {
            if (imageView.getTag().equals(filePath)) {
                imageView.setImageBitmap(bmp);
            }
        } else {
            // bmp = BitmapFactory.decodeResource(context.getResources(),
            // R.drawable.empty_photo);
            // imageView.setImageBitmap(bmp);
            imageView.setImageResource(R.drawable.empty_photo);
        }

    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key    LruCache的键，这里传入图片的URL地址。
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap对象。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            imageCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return imageCache.get(key);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth) {
        // 源图片的宽度
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            // 计算出实际宽度和目标宽度的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String pathName,
                                                         int reqWidth) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    /**
     * 获取图片大小(KB)
     *
     * @param filePath 图片路径
     * @return 图片大小，单位KB
     */
    public static float getImageSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            Log.w("ImageUtil", "Image File is not exists or not file!");
            return -1;
        }

        float size = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            size = fis.available() / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fis = null;
            }
        }

        return size;
    }

    // 对外界开放的回调接口
    public interface ImageCallback {
        // 注意 此方法是用来设置目标对象的图像资源
        public void imageLoaded(Bitmap imageDrawable);

        public void imageLoaded(Bitmap imageDrawable, String path);
    }
}
