package com.seaway.common.apps.qrcode.scan.activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;
import com.covics.zxingscanner.camera.CameraManager;
import com.seaway.common.apps.qrcode.scan.util.OnDataChangedNotificationObservable;
import com.seaway.common.apps.qrcode.scan.util.UIDefaultDialogHelper;
import com.seaway.common.apps.qrcode.scan.util.UIDefaultWaitingDialog;
import com.seaway.common.qrcode.R;
import com.seaway.common.apps.qrcode.scan.view.ScanRay;


/**
 * 扫一扫
 *
 * @author mayl
 */
public class QrCodeScannerActivity extends Activity implements
        OnDecodeCompletionListener, OnClickListener {
    public static QrCodeScannerActivity instance = null;
    public static final int SCAN_QR_CODE_REQUEST_CODE = 0x7385;

    public static final int SCANCODE_RESULT_CODE = 0x10;
    public static final int LOGOUT_RESULT_CODE = 0x11;

    public static final String KEY_SCANCODE_RESULT = "scan_result";
    private ScannerView scannerView;
    private TextView txtResult;
    private boolean isOpen = true; // 控制开关灯
    private ImageView btnOpen; // 开灯
    private Button btnBack;
    private Camera camera;
    private Parameters parameter;
    private LinearLayout scanRayView;

    private ScanRay scanRay;

    private Receiver mReceiver = new Receiver(this);
    private String callBack;
    private int openFlag;
    private OnDataChangedNotificationObservable onDataChangedNotificationObservable;

    private UIDefaultDialogHelper dialogHelper;
    private UIDefaultWaitingDialog waitingDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_scanner_activity_scan);
        // initViewAndListener();
        // initScanRay();
    }

    /**
     * 初始化控件属性
     */
    private void initViewAndListener() {
        //instance = this;
        onDataChangedNotificationObservable = new OnDataChangedNotificationObservable();
        if (getIntent() != null){
            openFlag  = getIntent().getIntExtra("openFlag",0);
            callBack = getIntent().getStringExtra("callBack");
        }

        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        txtResult = (TextView) findViewById(R.id.txtResult);
        scanRayView = (LinearLayout) findViewById(R.id.scan_ray);
        btnOpen = (ImageView) this.findViewById(R.id.btn_light_control);
        btnBack = (Button) this.findViewById(R.id.btn_back_control);
        btnBack.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        // 条码扫描后回调自己的onDecodeCompletion
        scannerView.setOnDecodeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_light_control) {
            try {
                camera = CameraManager.getCamera();
                if (null != camera) {
                    parameter = camera.getParameters();
                    // 开灯
                    if (isOpen) {
                        btnOpen.setBackgroundResource(R.drawable.ui_light_open);
                        parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameter);
                        isOpen = false;
                    } else { // 关灯
                        btnOpen.setBackgroundResource(R.drawable.ui_light_close);
                        parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameter);
                        isOpen = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.btn_back_control) {
            onBackPressed();
        }
    }

    /**
     * 显示提示框
     */
    public void showDialog() {
        UIDefaultDialogHelper.showDefaultAskAlert(
                this,
                "拨打电话"
                        + getResources().getString(
                        R.string.qr_code_sannner_tips_1000), "拨打", this);
    }

    /**
     * 隐藏提示框
     */
    public void dismissDialog() {
        if (UIDefaultDialogHelper.dialog != null && UIDefaultDialogHelper.dialog.isShowing())
            UIDefaultDialogHelper.dialog.dismiss();
    }

    /**
     * 显示进度条
     */
    public void showWaitingDialog() {
        if (waitingDialog != null) {
            if(waitingDialog.isShowing())
                this.waitingDialog.dismiss();
            this.waitingDialog = null;
        }
        waitingDialog = new UIDefaultWaitingDialog(this);
        waitingDialog.setCanceledOnTouchOutside(false);
        waitingDialog.setCancelable(false);

        waitingDialog.show();
    }

    /**
     * 隐藏进度条
     */
    public void dismiassWaitingDialog() {
        if (this.waitingDialog != null && this.waitingDialog.isShowing()) {
            this.waitingDialog.dismiss();
            this.waitingDialog = null;
        }
    }

    /**
     * 初始化扫描框大小
     */
    public void initScanRay() {
        if (null == scanRay) {
            scanRay = new ScanRay(this);
            /** 获取屏幕的分辨率 */
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int width = dm.widthPixels * 3 / 4;
            int height = (dm.heightPixels - frame.top) / 2;
            LayoutParams params = new LayoutParams(width, height - 20);
            scanRayView.addView(scanRay, params);
        }
    }

    // 将条码扫描识别结果打印在textview上，barcodeFormat是条码格式，barcode是条码内容，bitmap是条码图像
    @Override
    public void onDecodeCompletion(String barcodeFormat, String barcode,
                                   Bitmap bitmap) {
//        QrCodeVo vo = new QrCodeVo();
//        vo.setBarcode(barcode);
//        vo.setCallBack(callBack);
//        AggregateWebApplication.getSelf().onDataChangedNotificationObservable.dataChangedNotification(vo);
		Intent intent = new Intent();
		intent.putExtra("Barcode", barcode);
		intent.putExtra("callBack",callBack);
//		SWLog.LogE("ScannerActivity_barcode is : " + barcode);
		QrCodeScannerActivity.this.setResult(SCANCODE_RESULT_CODE, intent);
        QrCodeScannerActivity.this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // onResume时才打开相机和闪光灯
        //SWLog.LogE("ScannerActivity-onResume");
        initViewAndListener();
        initScanRay();
//        UIDefaultDialogHelper.showDefaultAskAlert(this, "请选择前置或者后置摄像头", "前置摄像头", "后置摄像头", new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UIDefaultDialogHelper.dialog.dismiss();
//                scannerView.onResume(1);
//            }
//        }, new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UIDefaultDialogHelper.dialog.dismiss();
//                scannerView.onResume(0);
//            }
//        });
        scannerView.onResume(openFlag);

//		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.cancelAll();
//		Intent intent = new Intent(this, ExitTimerService.class);
//		stopService(intent);
//
//		if (!mReceiver.hasRegister) {
//			IntentFilter filter = new IntentFilter();
//			filter.addAction(Intent.ACTION_SCREEN_OFF);
//			filter.addAction(Intent.ACTION_SCREEN_ON);
//			filter.addAction(ExitTimerService.LOGOUT_ACTION);
//
//			this.registerReceiver(mReceiver, filter);
//			mReceiver.hasRegister = true;
//		}
    }

    @Override
    protected void onPause() {
        super.onPause();
        // onPause时关闭相机和闪光灯
        scannerView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mReceiver.hasRegister) {
            unregisterReceiver(mReceiver);
        }
        if(instance!=null)
            instance =null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        if(instance!=null)
            instance =null;
        finish();
    }

//	@Override
//	public void loginSessionOut(String msg) {
//	}

    static class Receiver extends BroadcastReceiver {
        protected boolean hasRegister;
        private WeakReference<QrCodeScannerActivity> mActivity;

        public Receiver(QrCodeScannerActivity activity) {
            mActivity = new WeakReference<QrCodeScannerActivity>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
//			MobileBankApplication app = (MobileBankApplication) MobileBankApplication
//					.getInstance();
//
//			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//				// 如果屏幕关闭
//				SWLog.LogD("关闭屏幕");
//				if (null != app.userInfoVo && !ExitTimerService.isTiming) {
//					Intent i = new Intent(context, ExitTimerService.class);
//					context.startService(i);
//				}
//			} else if (intent.getAction() == Intent.ACTION_SCREEN_ON) {
//				// 退出
//			} else if (intent.getAction()
//					.equals(ExitTimerService.LOGOUT_ACTION)) {
//				SWLog.LogD("QrCodeScannerActivity--收到退出登录广播");
//
//				if (null != app.userInfoVo) {
//					new MobileBankRequest().requestWithoutResult(context,
//							Methods.REQUEST_LOGOUT_METHOD, null,
//							new SysReqParam());
//				}
//				app.logout();
//				if (null != mActivity && null != mActivity.get()) {
//					mActivity.get().finish();
//				}
//			}
        }
    }
}
