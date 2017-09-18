package com.hiver.app.vc.fragment;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDataObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.hiver.app.vc.view.CheckVersionDialogHelper;
import com.seaway.android.sdk.SWApplication;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.bank.biz.vc.contract.VersionCheckContract;
import com.seaway.bank.biz.vc.presenter.VersionCheckPresenter;
import com.seaway.bank.model.vc.DownloadProgressListener;

import com.seaway.hiver.apps.common.fragment.BaseFragment;
import com.seaway.hiver.model.common.DataCleanManager;
import com.seaway.hiver.model.common.data.vo.CheckVersionVo;
import com.hiver.app.vc.ICheckVersionListener;
import com.hiver.ui.dialog.UIDefaultDialogHelper;
import com.hiver.ui.dialog.UIProgressDialog;


import java.io.File;
import java.lang.reflect.Method;

/**
 * 版本检测
 * Created by Leo.Chang on 2017/6/10.
 */
public class VersionCheckFragment extends BaseFragment<VersionCheckContract.Presenter> implements VersionCheckContract.View, DownloadProgressListener,ICheckVersionListener {
    private ICheckVersionListener checkVersionListener;
    private  DownloadManager dManager;
    private long refernece;
    public UIProgressDialog uiProgressDialog;
    private int maxSize;
    public VersionCheckFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        new VersionCheckPresenter(this);
        if (!(context instanceof ICheckVersionListener)) {
            throw new ClassCastException(context.toString()
                    + " must implement ICheckVersionListener");
        }
        uiProgressDialog =new UIProgressDialog(getContext());
        checkVersionListener = (ICheckVersionListener) context;
        mPresenter.checkVersion();
    }

    public void checkVersion(){
        mPresenter.checkVersion();
    }

    /**
     * 无需更新
     */
    @Override
    public void checkVersionNoUpdate() {
        // 通知无需更新
        checkVersionListener.onCheckVersionComplete(ICheckVersionListener.CHECK_VERSION_NO_UPDATE);
    }

    @Override
    public void checkVersionMustUpdate(final CheckVersionVo vo) {
        maxSize =Integer.valueOf(vo.getSize());
        CheckVersionDialogHelper.showMustUpdateDialog(getActivity(), vo,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 退出
                        getActivity().finish();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 立即下载
                        CheckVersionDialogHelper.dialog.dismiss();
                        CheckVersionDialogHelper.dialog = null;
                        updateAndroid(vo.getUrl());
//                        if (gotoDownload(vo.getUrl())) {
//                            getActivity().finish();
//                        }
                    }
                });
    }

    @Override
    public void checkVersionOptUpdate(final CheckVersionVo vo) {
        CheckVersionDialogHelper.showOptUpdateDialog(getActivity(), vo,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 下次再说
                        CheckVersionDialogHelper.dialog.dismiss();
                        CheckVersionDialogHelper.dialog = null;
                        checkVersionListener.onCheckVersionComplete(ICheckVersionListener.CHECK_VERSION_NO_UPDATE);
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 立即下载
                        CheckVersionDialogHelper.dialog.dismiss();
                        CheckVersionDialogHelper.dialog = null;
                        updateAndroid(vo.getUrl());
//                        if (gotoDownload(vo.getUrl())) {
//                            getActivity().finish();
//                        }
                    }
                });
    }

    /**
     *
     * 客户端下载apk
     * */
    private void updateAndroid(String url){
        mPresenter.download(url,this);
//        dManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//// 设置下载路径和文件名
//        request.setDestinationInExternalPublicDir("download", "updata.apk");
//        request.setDescription("软件新版本下载");
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setMimeType("application/vnd.android.package-archive");
//// 设置为可被媒体扫描器找到
//        request.allowScanningByMediaScanner();
//// 设置为可见和可管理
//        request.setVisibleInDownloadsUi(true);
//// 获取此次下载的ID
//        refernece = dManager.enqueue(request);
//// 注册广播接收器，当下载完成时自动安装
//        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//        BroadcastReceiver receiver = new BroadcastReceiver() {
//
//            public void onReceive(Context context, Intent intent) {
//                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                Logger.i(">>>下载成功。。。");
//                if (refernece == myDwonloadID) {
//                    Intent install = new Intent(Intent.ACTION_VIEW);
//                    Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
//                    install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(install);
//                }
//            }
//        };
//        getActivity().registerReceiver(receiver, filter);
    }
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };
    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(refernece);//筛选下载任务，传入任务ID，可变参数
        Cursor c = dManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Logger.i(">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Logger.i(">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Logger.i(">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Logger.i(">>>下载完成");
                    //下载完成安装APK
                    //downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + versionName;
//                    Logger(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    Logger.i(">>>下载失败");
                    break;
            }
        }
    }




    /**
     * 版本检测失败
     *
     * @param reason 失败原因
     */
    @Override
    public void checkVersionFail(String reason) {
        UIDefaultDialogHelper.showDefaultAlert(getActivity(), reason, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIDefaultDialogHelper.dialog.dismiss();
                UIDefaultDialogHelper.dialog = null;
                getActivity().finish();
            }
        });
    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        Logger.i("update -> bytesRead is : " + bytesRead);
//        Logger.i("update -> contentLength is : " + contentLength);
//        Logger.i("update -> done is : " + done+"---"+((bytesRead*5/1024/maxSize)*100));
        float a =(float) bytesRead;
        float b =(float) 1024*maxSize;
        float x =(a/b)*100;
        Logger.i("update -> a " + a+"---b"+b+"===="+x);
        final int num =(int)x;
//        showProgess((int)((bytesRead/maxSize)*100));
        getActivity().runOnUiThread(new Runnable()
        {
            public void run()
            {
                mProgressDialog.setProcess(num);
            }

        });
    }

//    private void show(int num){
//        mProgressDialog.setProcess(num);
//    }

    /**
     * 跳转到浏览器下载客户端
     *
     * @param url 客户端下载地址
     * @return true：成功跳转；false：没有浏览器应用
     */
    private boolean gotoDownload(String url) {

        boolean flag = false;
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            getActivity().startActivity(intent);
            flag = true;
        } catch (Exception e) {
            Toast.makeText(getActivity(), "请先安装浏览器应用",Toast.LENGTH_LONG).show();
        }

        return flag;
    }

    @Override
    public void showAutoHidenDialog(int rid) {

    }

    @Override
    public void showUnconnectDialog(String msg) {
        uiProgressDialog.show();
    }

    @Override
    public void showOnConnectionDialog(int rid) {

    }

    @Override
    public void disMissConnectingDialog() {
        Logger.e("finished...");
        getActivity().finish();

//        unstallApp();
    }

    @Override
    public void onCheckVersionComplete(int result) {
        if (result == ICheckVersionListener.CHECK_VERSION_COMPLETE_AND_DOWNLOAD_FINISH) {
            Logger.e("清除数据");
//            DataCleanManager.cleanApplicationData(getActivity());
            getActivity().finish();
//            unstallApp();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.unsubscribe();
        }
    }
}