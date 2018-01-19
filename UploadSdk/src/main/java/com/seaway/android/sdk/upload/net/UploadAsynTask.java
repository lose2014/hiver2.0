package com.seaway.android.sdk.upload.net;

import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.model.common.NetUtil;

public class UploadAsynTask extends AsyncTask<Object, Integer, Message> {
//	private static final String UPLOAD_URL = "http://192.168.32.129:8081/v1/upload/picture/0";
//	private static final String UPLOADOPEN_URL = "http://192.168.32.197:8082/directbank-port-war/directController/upload/1";
//	private static final String UPLOAD_URL ="http://192.168.32.197:8082/directbank-port-war/directController/upload/1";

	private static final String UPLOAD_URL = NetUtil.getWebViewUrl()+ "uploads/picture/1";//上传图片
	private static final String UPLOADOPEN_URL = NetUtil.getWebViewUrl() + "/uploads/video";//上传视频
	private Handler mHandler;
	
	public  UploadAsynTask(Handler handler) {
		this.mHandler = handler;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Message doInBackground(Object... params) {
		Logger.d("文件上传地址" + UPLOAD_URL);
		String result =null;
		if(params.length==4){
			if(((Integer)params[3]).intValue()==2){
				result = UploadSocketHttpReuqest.doHttpPost(UPLOADOPEN_URL, (HashMap<String, String>)params[0], (HashMap<String, String>)params[1],((Integer)params[3]).intValue());
			}else{
				result = UploadSocketHttpReuqest.doHttpPost(UPLOAD_URL, (HashMap<String, String>)params[0], (HashMap<String, String>)params[1],((Integer)params[3]).intValue());
			}
		}else{
			result = UploadSocketHttpReuqest.doHttpPost(UPLOAD_URL, (HashMap<String, String>)params[0], (HashMap<String, String>)params[1]);
		}
		Logger.d("result is : " + result);

		Message msg = Message.obtain();
		msg.what = (Integer) params[2];
		if (UploadSocketHttpReuqest.FAILURE.equals(result)) {
			// 如果上传失败
			msg.obj = "{\"code\":\"20001\",\"succ\":false,\"message\":\"图片上传失败，请稍后重试\"}";
		} else {
			msg.obj = result;
		}
		
		return msg;
	}

	@Override
	protected void onPostExecute(Message msg) {
		if (null != mHandler) {
			mHandler.sendMessage(msg);
		}
	}
}