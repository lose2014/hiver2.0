package com.seaway.hiver.teacher.util;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.seaway.android.sdk.logger.Logger;
import com.seaway.hiver.apps.common.HiverApplication;

import java.util.Vector;

/**
 * Created by Leo.Chang on 2016/5/20.
 */
public class VoiceUtil implements SynthesizerListener {
	private MediaPlayer mMediaPlayer;

	/**
	 * 记录播放音频的id
	 */
	private Vector<String> mSoundQueue = new Vector<String>();

//	private SparseArray<String> mSoundQueue = new SparseArray<>();

	private static volatile VoiceUtil mVoiceUtil;

	/**
	 * 语音播报
	 */
	private static SpeechSynthesizer mTts;

	/**
	 * 是否正在播放<br>true：正在播放；false：未播放
	 */
	private Boolean isPlaying = false;

//	private OnPlayCompletionListener onPlayCompletionListener;

	private VoiceUtil() {
		if (null == mMediaPlayer) {
			mMediaPlayer = new MediaPlayer();
		}
	}

//	private VoiceUtil(OnPlayCompletionListener onPlayCompletionListener) {
//		if (null == mMediaPlayer) {
//			mMediaPlayer = new MediaPlayer();
//		}
//		this.onPlayCompletionListener = onPlayCompletionListener;
//	}

	/**
	 * 获取VoiceUtil的实例
	 *
	 * @return VoiceUtil的实例
	 */
	public static VoiceUtil getInstance() {
		if (null == mVoiceUtil) {
			synchronized (VoiceUtil.class) {
				if (null == mVoiceUtil) {
					mVoiceUtil = new VoiceUtil();
				}
			}
		}

		if (null == mTts) {
			//1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
			mTts = SpeechSynthesizer.createSynthesizer(HiverApplication.getInstance(), null);
			//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
			mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
			mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
			mTts.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100
			mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
		}

		return mVoiceUtil;
	}

	/**
	 * 获取VoiceUtil的实例
	 *
	 * @return VoiceUtil的实例
	 */
//	public static VoiceUtil getInstance(OnPlayCompletionListener onPlayCompletionListener) {
//		if (null == mVoiceUtil) {
//			Logger.e("实例化VoiceUtil");
//			mVoiceUtil = new VoiceUtil(onPlayCompletionListener);
//		}
//
//		return mVoiceUtil;
//	}

	/**
	 * 将音频添加到音频队列中
	 *
	 * @param msg 音频内容
	 */
	public void addSounds(String msg) {
		if (!TextUtils.isEmpty(msg)) {
			mSoundQueue.add(msg);
			Logger.e("mSoundQueue size is : " + mSoundQueue.size());
			if (!isPaying()) {
				playMusic();
			}
		}
	}

	/**
	 * 停止播放
	 */
	public void stopPlay() {
		Logger.e("停止音频播放");
//		if (null != mMediaPlayer && isPaying()) {
//			mMediaPlayer.stop();
//			SWLog.LogD("音频播放已停止");
//			clear();
//		}
		if (null != mTts) {
			mTts.stopSpeaking();
			mTts.destroy();
			clear();
		}
	}

	/**
	 * 播放语音
	 */
	private void playMusic() {
		Logger.e("开始播放音频");
		try {
			if (null == mSoundQueue || mSoundQueue.isEmpty()) {
				Logger.e("播放列表已播放完成");
//				if (null != onPlayCompletionListener) {
//					clear();
//				}
				clear();
				return;
			}

//			mMediaPlayer.reset();
//			mMediaPlayer.setDataSource(AggregateWebApplication.getSelf().getApplicationContext(), Uri.parse("android.resource://" + ICommApplication.getInstance().getPackageName() + "/" + mSoundQueue.firstElement()));
//			mMediaPlayer.prepare();
//			mMediaPlayer.start();

			Logger.e("播放音频内容：" + mSoundQueue.firstElement());
			mTts.startSpeaking(mSoundQueue.firstElement(),this);

			// 标识正在播放
			setIsPlaying(true);
//			mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//				@Override
//				public void onCompletion(MediaPlayer mp) {
//					SWLog.LogD("播放完成");
//					mSoundQueue.remove(mSoundQueue.firstElement());
//					setIsPlaying(false);
//					playMusic();
//				}
//			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置当前是否正在播放音频
	 *
	 * @param isPlaying true：正在播放；false：未播放
	 */
	private void setIsPlaying(boolean isPlaying) {
		synchronized (this.isPlaying) {
			this.isPlaying = isPlaying;
		}
	}

	/**
	 * 获取当前是否正在播放音频
	 *
	 * @return true：正在播放；false：未播放
	 */
	private boolean isPaying() {
		synchronized (this.isPlaying) {
			return this.isPlaying;
		}
	}

	private void clear() {
		mSoundQueue.clear();
//		mMediaPlayer.release();
//		mMediaPlayer = null;
//		onPlayCompletionListener.onPlayCompletion();
		if (null != mTts) {
			mTts.stopSpeaking();
			mTts.destroy();
		}
		mVoiceUtil = null;
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
//		if (100 == percent) {
//			SWLog.LogD("播放完成");
//			mSoundQueue.remove(mSoundQueue.firstElement());
//			setIsPlaying(false);
//			playMusic();
//		}
	}

	@Override
	public void onCompleted(SpeechError speechError) {
		Logger.e("onCompleted -> ");
		Logger.e("播放完成");
		mSoundQueue.remove(mSoundQueue.firstElement());
		setIsPlaying(false);
		playMusic();
	}

	@Override
	public void onEvent(int i, int i1, int i2, Bundle bundle) {

	}

	/**
	 * 音频列表播放完成监听
	 */
	public interface OnPlayCompletionListener {
		/**
		 * 音频队列已播放完成
		 */
		public void onPlayCompletion();
	}
}