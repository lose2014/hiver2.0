package com.seaway.hiver.teacher.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.gson.Gson;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.HiverApplication;
import com.seaway.hiver.teacher.LaunchActivity;
import com.seaway.hiver.teacher.R;
import com.seaway.hiver.teacher.service.data.PushDataVo;
import com.seaway.hiver.teacher.util.VoiceUtil;

/**
 * Created by Leo.Chang on 2016/12/13.
 */
public class PushIntentService extends GTIntentService implements SynthesizerListener {
	public PushIntentService() {

	}

	@Override
	public void onReceiveServicePid(Context context, int pid) {
//		Logger.e("onReceiveServicePid -> " + pid);
	}

	@Override
	public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
//		Logger.e("onReceiveMessageData -> " + msg);

		String appid = msg.getAppid();
		String taskid = msg.getTaskId();
		String messageid = msg.getMessageId();
		byte[] payload = msg.getPayload();
		String pkg = msg.getPkgName();
		String cid = msg.getClientId();

//		Logger.e("onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
//				+ "\ncid = " + cid);
		if (payload == null) {
//			Logger.e("receiver payload = null");
		} else {
			String data = new String(payload);
			Logger.e("receiver payload = " + data);
			PushDataVo vo = new Gson().fromJson(data, PushDataVo.class);
			if (null != vo) {
//				Logger.e("title is : " + vo.getTitle());
//				Logger.e("body is : " + vo.getBody());
//				Logger.e("redirectURL is : " + vo.getRedirectURL());
				NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Notification.Builder builder = new Notification.Builder(this);
				builder.setSmallIcon(R.mipmap.ic_launcher); // 这里使用的系统默认图标，可自行更换
				builder.setTicker(vo.getTitle());
				builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
				builder.setContentTitle(vo.getTitle());
				builder.setContentText(vo.getBody());
				builder.setAutoCancel(true);

				Intent intent = new Intent(this, LaunchActivity.class);
				intent.putExtra("redirectURL", vo.getRedirectURL());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				PendingIntent pendingIntents = PendingIntent.getActivity(this, vo.getRedirectURL().hashCode(), intent, PendingIntent.FLAG_ONE_SHOT);
				builder.setContentIntent(pendingIntents);

				Notification notification = builder.build();
				// 启动 Notification
				notificationManager.notify(notification.hashCode(), builder.build());

				if ("0".equals(vo.getBroadcast())) {
					// 如果要播报语音，则调用
					broadcastVoice(vo.getBroadcastContent());
				}

			}
		}

//		Logger.e("是否主线程：" + (Looper.myLooper() == Looper.getMainLooper()));
	}

	@Override
	public void onReceiveClientId(Context context, String clientid) {
		Logger.e("onReceiveClientId -> " + "clientid = " + clientid);
		HiverApplication.getInstance().clientId = clientid;
	}

	@Override
	public void onReceiveOnlineState(Context context, boolean online) {
//		Logger.e("onReceiveOnlineState -> " + online);
	}

	@Override
	public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//		Logger.e("onReceiveCommandResult -> " + cmdMessage);
	}

	/**
	 * 语音播报
	 *
	 * @param msg
	 */
	private void broadcastVoice(String msg) {
		//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
//		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(this, null);
//		//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
//		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
//		mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
//		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//		//3.开始合成
//		mTts.startSpeaking(msg, this);
		VoiceUtil.getInstance().addSounds(msg);
	}

	@Override
	public void onSpeakBegin() {

	}

	@Override
	public void onBufferProgress(int i, int i1, int i2, String s) {

	}

	@Override
	public void onSpeakPaused() {

	}

	@Override
	public void onSpeakResumed() {

	}

	@Override
	public void onSpeakProgress(int percent, int beginPos, int endPos) {
		Logger.e("onSpeakProgress -> percent is : " + percent);
		if (100 == percent) {

		}
	}

	@Override
	public void onCompleted(SpeechError speechError) {
		Logger.e("onCompleted -> ");
	}

	@Override
	public void onEvent(int i, int i1, int i2, Bundle bundle) {

	}
}