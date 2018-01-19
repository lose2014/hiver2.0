package com.seaway.android.sdk.upload.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class PictureUtil {
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static InputStream bitmapToInputStream(String filePath) throws FileNotFoundException {

//		Bitmap bm = getSmallBitmap(filePath);
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
//		
//		if (bm != null) {
//			bm.recycle();
//			bm = null;
//		}
//		return new ByteArrayInputStream(baos.toByteArray());
		String outputFile = ImageHandler.handler(filePath, 720, 1280);
		return new FileInputStream(new File(outputFile));
	}

	/**
	 * 把bitmap转换成String(只压缩大小，不压缩质量)
	 * 
	 * @param filePath
	 * @param maxKb 图片最大大小
	 * @param outWidth 压缩后的宽
	 * @param outHeight 压缩后的高
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static InputStream bitmapToInputStream(String filePath,int maxKb, int outWidth, int outHeight ) throws FileNotFoundException {
		String outputFile = ImageHandler.handlerSize(filePath,maxKb, outWidth, outHeight);
		return new FileInputStream(new File(outputFile));
	}



	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @param maxKb 图片最大大小
	 * @param outWidth 压缩后的宽
	 * @param outHeight 压缩后的高
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static InputStream bitmapToInputStreamCrop(String filePath,int maxKb, int outWidth, int outHeight ) throws FileNotFoundException {
		String outputFile = ImageHandler.handlerSize(filePath,maxKb, outWidth, outHeight);
		return new FileInputStream(new File(outputFile));
	}
	
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static InputStream bitmapToInputStream(String filePath, int quality) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		
		if (bm != null) {
			bm.recycle();
			bm = null;
		}

		return new ByteArrayInputStream(baos.toByteArray());
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		
		if (bm != null) {
			bm.recycle();
			bm = null;
		}

		return Base64.encodeToString(b, Base64.NO_WRAP);
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath, int quality) {

		Bitmap bm = getSmallBitmap(filePath);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] b = baos.toByteArray();
		
		if (bm != null) {
			bm.recycle();
			bm = null;
		}

		return Base64.encodeToString(b, Base64.NO_WRAP);
	}

	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath, int quality,
			int width, int height) {

		Bitmap bm = getSmallBitmap(filePath, width, height);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] b = baos.toByteArray();
		
		if (bm != null) {
			bm.recycle();
			bm = null;
		}

		return Base64.encodeToString(b, Base64.NO_WRAP);
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 320);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int width, int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, width, height);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
}
