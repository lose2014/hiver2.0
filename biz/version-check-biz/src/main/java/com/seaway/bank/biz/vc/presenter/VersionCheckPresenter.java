package com.seaway.bank.biz.vc.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.seaway.android.sdk.SWApplication;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.bank.biz.vc.contract.VersionCheckContract;
import com.seaway.bank.model.vc.DownloadProgressListener;
import com.seaway.bank.model.vc.IVersionCheckDataSource;
import com.seaway.bank.model.vc.impl.VersionCheckDataSource;
import com.seaway.hiver.common.biz.presenter.BasePresenter;
import com.seaway.hiver.model.common.Application;
import com.seaway.hiver.model.common.DataCleanManager;
import com.seaway.hiver.model.common.data.vo.CheckVersionVo;
import com.seaway.hiver.model.common.exception.ConnectionException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 客户端版本检测
 * Created by Leo.Chang on 2017/6/10.
 */
public class VersionCheckPresenter extends BasePresenter<VersionCheckContract.View, IVersionCheckDataSource> implements VersionCheckContract.Presenter {
    public VersionCheckPresenter(VersionCheckContract.View view) {
        super(view);
        setDataSource(new VersionCheckDataSource());
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        checkVersion();
    }

    /**
     * 版本检测
     */
    @Override
    public void checkVersion() {
        mDataSource.checkVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckVersionVo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull CheckVersionVo checkVersionVo) {
                        if ("0".equalsIgnoreCase(checkVersionVo.getUpdate())) {
                            // 无需更新

                            mView.checkVersionNoUpdate();
                        } else if ("1".equalsIgnoreCase(checkVersionVo.getUpdate())) {
                            if ("0".equalsIgnoreCase(checkVersionVo.getIsOptional())) {
                                // 非强制更新
                                mView.checkVersionOptUpdate(checkVersionVo);
                            } else {
                                //强制更新
                                mView.checkVersionMustUpdate(checkVersionVo);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ConnectionException ce = (ConnectionException) e;
                        mView.checkVersionFail(ce.message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 下载
     *
     * @param url 下载地址
     */
    @Override
    public void download(String url,DownloadProgressListener downloadProgressListener) {
        Logger.i("download -> url is : " + url);
        mView.showPregressDialog();
        mDataSource.download(url,downloadProgressListener)
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(@NonNull ResponseBody responseBody) throws Exception {
                        Logger.i("contentLength is : " + responseBody.contentLength());
                        return responseBody.byteStream();
                    }
                })
                .map(new Function<InputStream, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull InputStream inputStream) throws Exception {

                        return writeResponseBodyToDisk(inputStream);
                    }
                })
                .observeOn(Schedulers.io())
//                .doOnNext(new Consumer<InputStream>() {
//                    @Override
//                    public void accept(@NonNull InputStream inputStream) throws Exception {
//                        boolean flag = writeResponseBodyToDisk(inputStream);
//                        Logger.i("flag is : " + flag);
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean bool) {
                        Logger.i("download -> onNext");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("download -> onError");
                        mView.dismissPregressDialog();
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("download -> onComplete");
                        mView.dismissPregressDialog();
                        mView.disMissConnectingDialog();
                    }
                });
    }

    //下载到本地后执行安装
    protected void installAPK(File file) {
        Logger.e("installAPK===========");
        if (!file.exists()) return;
        Logger.e("开始重新安装apk");
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(SWApplication.getInstance().getApplicationContext(), "com.seaway.hiver.fileprovider", file);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            SWApplication.getInstance().getApplicationContext().startActivity(install);
        } else{
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SWApplication.getInstance().getApplicationContext().startActivity(install);
        }
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        Uri uri;
//        if (Build.VERSION.SDK_INT > 23) {
//           uri = FileProvider.getUriForFile( SWApplication.getInstance().getApplicationContext(),  SWApplication.getInstance().getApplicationContext().getApplicationContext().getPackageName() + ".provider", file);
//        }else
//            uri = Uri.parse("file://" + file.toString());
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        //在服务中开启activity必须设置flag,后面解释
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        SWApplication.getInstance().getApplicationContext().startActivity(intent);


    }


    private boolean writeResponseBodyToDisk(InputStream is) {
        try {
            Logger.i("download -> start...");
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Application.getInstance().getApplicationContext().getExternalFilesDir(null) + File.separator + "kuaiyi.apk");

            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = is.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (is != null) {
                    is.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
                installAPK(futureStudioIconFile);
            }
        } catch (IOException e) {
            return false;
        }
    }
}
