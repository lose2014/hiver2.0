package com.seaway.hiver.teacher.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.seaway.android.sdk.toolkit.SWMD5;
import com.seaway.hiver.apps.common.HiverApplication;

import java.io.File;

//import com.seaway.android.toolkit.base.SWLog;

/**
 * Android工具类
 * Created by Leo.Chang on 2016/12/12.
 */
public class AndroidUtil {
	/**
	 * 获取客户端版本号
	 *
	 * @return
	 */
	public static String getClientVersion() {
		StringBuffer versionName = new StringBuffer();

		try {
			PackageManager e = HiverApplication.getInstance().getApplicationContext().getPackageManager();
			PackageInfo pi = e.getPackageInfo(HiverApplication.getInstance().getApplicationContext().getPackageName(), 0);
			versionName.append(pi.versionName);
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return versionName.toString();
	}

	/**
	 * 获取设备编号
	 *
	 * @return
	 */
	public static String getDeviceNo() {
		StringBuffer deviceNo = new StringBuffer();
		try {
			TelephonyManager tm = (TelephonyManager) HiverApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
			deviceNo.append(tm.getDeviceId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return deviceNo.toString();
	}

	/**
	 * 获取运营商信息
	 *
	 * @return 0：中国移动；<br>
	 * 1：中国联通；<br>
	 * 2：中国电信<br>
	 * 3、其它
	 */
	public static String getSIMInfo() {
		String type = "";
		try {
			TelephonyManager tm = (TelephonyManager)  HiverApplication.getInstance().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
			String operator = tm.getSimOperator();

			if (null != operator) {
				if (operator.equals("46000") || operator.equals("46002")) {
					// 中国移动
					type = "0";
				} else if (operator.equals("46001")) {
					// 中国联通
					type = "1";
				} else if (operator.equals("46003")) {
					// 中国电信
					type = "2";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return type;
	}

	/**
	 * 获取手机厂商
	 *
	 * @return
	 */
	public static String getMobileFactory() {
		return Build.MANUFACTURER;
	}

	/**
	 * 获取手机型号
	 *
	 * @return
	 */
	public static String getMobileMod() {
		return Build.MODEL;
	}

	/**
	 * 获得安装包MD5值
	 *
	 * @return
	 */
	public static String getMD5() {
		StringBuffer md5 = new StringBuffer();
		try {
			String path =  HiverApplication.getInstance().getPackageResourcePath();
			File file = new File(path);
			if (file.exists()) {
				md5.append(SWMD5.getFileMD5String(file));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return md5.toString();
	}

	/**
	 * 获取屏幕大小
	 *
	 * @param type 0：宽度；1：高度;2:密度
	 * @return
	 */
	public static int getScreenInfo(int type) {
		DisplayMetrics metric =  HiverApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics();
		if (0 == type) {
			return  metric.widthPixels;
		} else if(1==type){
			return  metric.heightPixels;
		}else{
			return metric.densityDpi;
		}
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
			ApplicationInfo appInfo =  HiverApplication.getInstance().getApplicationContext().getPackageManager().getApplicationInfo(
					 HiverApplication.getInstance().getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
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

	/**
	 * 获取系统亮度
	 * 取值在(0 -- 255)之间
	 */
	public static int getSystemScreenBrightness(Context context) {
		int values = 0;
		try {
			if(isAutoBrightness((Activity)context)){
				stopAutoBrightness((Activity)context);
			}
			values = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Settings.SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * 设置系统亮度
	 *
	 * @param systemBrightness 返回的亮度值是处于0-255之间的整型数值
	 */
	public static boolean setSystemScreenBrightness(Context context, int systemBrightness) {
//        Window window = ((Activity)context).getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
//        window.setAttributes(lp);
//        return true;
		stopAutoBrightness((Activity) context);
		return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, systemBrightness);
	}

	/**
	 * 系统是否自动调节亮度
	 * return true 是自动调节亮度   return false 不是自动调节亮度
	 */
	public static boolean isAutoBrightness(Activity activity) {
		int autoBrightness = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		try {
			autoBrightness = Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}

		if (autoBrightness == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 关闭系统自动调节亮度
	 */
	public static void stopAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	/**
	 * 打开系统自动调节亮度
	 */
	public static void startAutoBrightness(Activity activity) {
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
				Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
	}

	/**
	 * 请求屏幕常亮
	 *
	 * @param activity
	 */
	public static void requireScreenOn(Activity activity) {
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * 取消屏幕常亮
	 *
	 * @param activity
	 */
	public static void releaseScreenOn(Activity activity) {
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}
