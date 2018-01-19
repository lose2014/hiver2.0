package com.seaway.android.sdk.upload.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Leo.Chang on 2016/5/18.
 */
public class ImageUtil {

	/**
	 * 获取图片大小(KB)
	 * 
	 * @param filePath
	 *            图片路径
	 * @return 图片大小，单位KB
	 */
	public static float getImageSize(String filePath) {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			Log.w("ImageUtil", "Image File is not exists or not file!");
			return -1;
		}

		float size = 0;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			size = fis.available() / 1024;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fis = null;
			}
		}

		return size;
	}

	/**
	 * 获取图片宽高
	 * 
	 * @param filePath
	 *            图片路径
	 * @return 图片宽高
	 */
	public static int[] getImageWidthAndHeight(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		return new int[] { options.outWidth, options.outHeight };
	}

	/**
	 * 等比要锁图片宽高
	 * 
	 * @param filePath
	 * @param maxNumOfPixels
	 * @return
	 */
	public static Bitmap scaleImage(String filePath, int maxNumOfPixels) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		int sampleSize = ImageUtil.computeSampleSize(options, -1,
				maxNumOfPixels);
		Log.i("ImageUtil", "sampleSize is : " + sampleSize);
		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 获取图片的旋转角度
	 * 
	 * @param path
	 *            图片路径
	 * @return 图片旋转角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static void rotateImageAndSave(Bitmap bitmap, String filePath,
			int quality, int degree) {
		if (0 != degree) {
			// 如果角度不为0 ，则旋转角度后重新保存图片
			Matrix matrix = new Matrix();
			matrix.setRotate(degree);
			Bitmap bit = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, true);
			
			// 保存bitmap
			saveBitmap(bit, filePath,quality);
			bit.recycle();
		} else {
			saveBitmap(bitmap, filePath, quality);
		}
	}

	/**
	 * 保存图片
	 * 
	 * @param bitmap
	 * @param filePath
	 */
	public static void saveBitmap(Bitmap bitmap, String filePath, int quality) {
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
			out.flush();
			out.close();
			Log.i("TAG", "保存成功");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 计算图片缩放比
	 * 
	 * @param options
	 *            图片属性
	 * @param minSideLength
	 * @param maxNumOfPixels
	 *            图片最大像素值
	 * @return 缩放比例
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}
}