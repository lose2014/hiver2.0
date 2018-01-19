package com.seaway.android.sdk.upload.util;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.seaway.android.sdk.toolkit.SWVerificationUtil;
import com.seaway.android.sdk.upload.data.param.UploadFileParam;

public class FileMapUtil {
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static UploadFileParam[] getUploadFileParams(String[] filePath) {
		if (null == filePath || 0 == filePath.length) {
			return null;
		}

		UploadFileParam[] params = new UploadFileParam[filePath.length];
		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			UploadFileParam formfile = new UploadFileParam(file.getName(),
					file, "image", "image/jpeg");
			params[i] = formfile;
		}

		return params;
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static HashMap<String, String> getUploadFileMap(String[] filePath) {
		if (null == filePath || 0 == filePath.length) {
			return null;
		}

		HashMap<String, String> params = new HashMap<String,String>(filePath.length);
		for (int i = 0; i < filePath.length; i++) {
			if(!SWVerificationUtil.isEmpty(filePath[i])){
				params.put("" + filePath[i].hashCode()+"_"+i, filePath[i]);
			}
		}

		return params;
	}

	/**
	 *
	 * @param filePath
	 * @return
	 */
	public static LinkedHashMap<String, String> getOrderUploadFileMap(String[] filePath) {
		if (null == filePath || 0 == filePath.length) {
			return null;
		}

		LinkedHashMap<String, String> params = new LinkedHashMap<String,String>(filePath.length);
		for (int i = 0; i < filePath.length; i++) {
			if(!SWVerificationUtil.isEmpty(filePath[i])){
				params.put("" + filePath[i].hashCode()+"_"+i, filePath[i]);
			}
		}

		return params;
	}
}