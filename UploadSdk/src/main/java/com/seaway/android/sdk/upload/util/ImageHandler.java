package com.seaway.android.sdk.upload.util;

import java.io.File;

import com.seaway.hiver.model.common.Application;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Leo.Chang on 2016/5/18.
 */
public class ImageHandler {
	private static final int MAX_KB = 100;

	private static final int DEFAULT_MAX_WIDTH = 720;
	private static final int DEFAULT_MAX_HEIGHT = 1280;

	/**
	 * 处理图片
	 * 
	 * @param filePath
	 *            图片路径
	 * @param maxKb
	 *            图片最大KB
	 * @param outWidth
	 *            处理后图片的宽度
	 * @param outHeight
	 *            处理后图片的高度
	 */
	public static String handler(String filePath, int maxKb, int outWidth,
			int outHeight) {
		if (null == filePath || "".equals(filePath.trim())) {
			// 如果图片路径为空，则返回null
			Log.w("ImageHandler", "bitmap path is empty!");
			// return null;
			return "";
		}

		// 计算图片的KB
		float kbSize = ImageUtil.getImageSize(filePath);
		if (-1 == kbSize) {
			// 文件不存在
			// return null;
			return "";
		}

		StringBuffer outputFilePath = new StringBuffer(Application
				.getInstance().getExternalCacheDir().getAbsolutePath()).append(
				File.separator).append("upload");
		File file = new File(outputFilePath.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		outputFilePath.append(File.separator).append(getOutputFileName());

		int degree = ImageUtil.readPictureDegree(filePath);

		if (kbSize < maxKb) {
			// 如果图片KB小于最大KB，则无需处理，直接返回图片
			// return BitmapFactory.decodeFile(filePath);
			Log.w("ImageHandler", "bitmap size < " + maxKb + "kb");
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(), 50,
					degree);

			bitmap.recycle();
			return outputFilePath.toString();
		}

		int[] widthAndHeigth = ImageUtil.getImageWidthAndHeight(filePath);
		if (widthAndHeigth[0] * widthAndHeigth[1] >= outWidth * outHeight) {
			// 如果原始图片的宽高大于720*1280或1280*720，则压缩图片尺寸到720*1280或1280*720之下
			Log.i("ImageHandler",
					"Image Size out fo 720*1280 or 1280*720,real size is : "
							+ widthAndHeigth[0] + "*" + widthAndHeigth[1]);
			Log.i("ImageHandler", "first scale image");
			Bitmap bitmap = ImageUtil
					.scaleImage(filePath, outWidth * outHeight);
			ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(), 50,
					degree);
			bitmap.recycle();
		} else {
			// 如果原始图片的宽高小于720*1280或1280*720，则直接压缩为原图片质量的50%
			if ((widthAndHeigth[0] < outWidth && widthAndHeigth[1] < outHeight)
					|| (widthAndHeigth[0] < outHeight && widthAndHeigth[1] < outWidth)) {
				Log.i("ImageHandler", "Image width and heigth all less than "
						+ outWidth + " and " + outHeight);
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(),
						50, degree);

				bitmap.recycle();
				// NativeUtil.compressBitmap(bitmap,filePath,true);
			} else {
				Bitmap bitmap = BitmapFactory.decodeFile(filePath);
				ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(),
						20, degree);
				bitmap.recycle();
			}
		}

		return outputFilePath.toString();
	}

	/**
	 * 处理图片(只压缩大小，不压缩质量)
	 * 
	 * @param filePath
	 *            图片路径
	 * @param maxKb
	 *            图片最大KB
	 * @param outWidth
	 *            处理后图片的宽度
	 * @param outHeight
	 *            处理后图片的高度
	 */
	public static String handlerSize(String filePath, int maxKb, int outWidth,
			int outHeight) {
		if (null == filePath || "".equals(filePath.trim())) {
			// 如果图片路径为空，则返回null
			Log.w("ImageHandler", "bitmap path is empty!");
			// return null;
			return "";
		}

		// 计算图片的KB
		float kbSize = ImageUtil.getImageSize(filePath);
		if (-1 == kbSize) {
			// 文件不存在
			// return null;
			return "";
		}

		StringBuffer outputFilePath = new StringBuffer(Application
				.getInstance().getExternalCacheDir().getAbsolutePath()).append(
				File.separator).append("upload");
		File file = new File(outputFilePath.toString());
		if (!file.exists()) {
			file.mkdirs();
		}
		outputFilePath.append(File.separator).append(getOutputFileName());

		int degree = ImageUtil.readPictureDegree(filePath);

		if (kbSize < maxKb) {
			// 如果图片KB小于最大KB，则无需处理，直接返回图片
			// return BitmapFactory.decodeFile(filePath);
			Log.w("ImageHandler", "bitmap size < " + maxKb + "kb");
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(), 50,
					degree);

			bitmap.recycle();
			return outputFilePath.toString();
		}

		int[] widthAndHeigth = ImageUtil.getImageWidthAndHeight(filePath);
		if (widthAndHeigth[0] * widthAndHeigth[1] >= outWidth * outHeight) {
			// 如果原始图片的宽高大于720*1280或1280*720，则压缩图片尺寸到720*1280或1280*720之下
			Log.i("ImageHandler",
					"Image Size out fo 720*1280 or 1280*720,real size is : "
							+ widthAndHeigth[0] + "*" + widthAndHeigth[1]);
			Log.i("ImageHandler", "first scale image");
			Bitmap bitmap = ImageUtil
					.scaleImage(filePath, outWidth * outHeight);
			ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(), 50,
					degree);
			bitmap.recycle();
		} else {
			// 如果原始图片的宽高小于720*1280或1280*720，则无需处理，直接返回图片
			Log.w("ImageHandler", "bitmap size < " + maxKb + "kb");
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			ImageUtil.rotateImageAndSave(bitmap, outputFilePath.toString(), 50,
					degree);

			bitmap.recycle();
		}

		return outputFilePath.toString();
	}
	
	/**
	 * 处理图片
	 * 
	 * @param filePath
	 *            图片路径
	 * @param outWidth
	 *            处理后图片的宽度
	 * @param outHeight
	 *            处理后图片的高度
	 * @return 处理后的图片
	 */
	public static String handler(String filePath, int outWidth, int outHeight) {
		return handler(filePath, MAX_KB, outWidth, outHeight);
	}

	/**
	 * 处理图片
	 * 
	 * @param filePath
	 *            图片路径
	 * @return 处理后的图片
	 */
	public static String handler(String filePath) {
		return handler(filePath, MAX_KB, DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
	}


	/**
	 * 设置图片名称，默认使用系统当前时间，如需修改，则在子类中重写该方法
	 * 
	 * @return 图片名称
	 */
	private static String getOutputFileName() {
		return "IMG_" + System.currentTimeMillis() + ".jpg";
	}

}