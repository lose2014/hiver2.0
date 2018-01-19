package com.seaway.android.sdk.upload.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.os.Handler;

import com.seaway.android.sdk.upload.data.param.UploadFileParam;
import com.seaway.android.sdk.upload.net.UploadAsynTask;

public class UploadUtil {
	/**
	 * 文件上传
	 * 
	 * @param handler
	 * @param params
	 * @param files
	 * @param identifiy
	 */
	public static void upload(Handler handler, Map<String, String> params,
			UploadFileParam[] files, int identifiy) {
		new UploadAsynTask(handler).execute(params, files, identifiy);
	}
	
	/**
	 * 文件上传
	 * 
	 * @param handler
	 * @param params
	 * @param files
	 * @param identifiy
	 */
	public static void upload(Handler handler, HashMap<String, String> params,
			HashMap<String, String> files, int identifiy) {
		new UploadAsynTask(handler).execute(params, files, identifiy);
	}
	
	/**
	 * 文件上传（无序上传）
	 * 
	 * @param handler
	 * @param params
	 * @param files
	 * @param identifiy
	 * @param type 0=证件照，1=商品照片，2=视频
	 */
	public static void upload(Handler handler, HashMap<String, String> params,
			HashMap<String, String> files, int identifiy, int type) {
		new UploadAsynTask(handler).execute(params, files, identifiy,type);
	}

	/**
	 * 文件上传(有序上传)
	 *
	 * @param handler
	 * @param params
	 * @param files
	 * @param identifiy
	 */
	public static void upload(Handler handler, HashMap<String, String> params,
							  LinkedHashMap<String, String> files, int identifiy) {
		new UploadAsynTask(handler).execute(params, files, identifiy);
	}
}