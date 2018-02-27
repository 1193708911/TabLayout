package com.taikang.tkdoctor.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadImageAsynTask extends AsyncTask<String, Void, Bitmap> {
	ImageTaskCallback iImageTaskCallback;

	public LoadImageAsynTask(ImageTaskCallback iImageTaskCallback) {
		this.iImageTaskCallback = iImageTaskCallback;
	}

	public interface ImageTaskCallback {
		// 当图片下载完毕后的方法
		void onImageLoaded(Bitmap bitmap);

		// 在图片下载之前调用的方法
		void beforeImageLoaded();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		iImageTaskCallback.beforeImageLoaded();
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		iImageTaskCallback.onImageLoaded(result);
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String path = params[0];
		try {
			//Bitmap bitmap = getBitmapImage(path);
			Bitmap bitmap = getBitmapFromUrl(path);
			return bitmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 返回指定path所对应的图片
	 * 
	 * @param path
	 *            图片的地址
	 * @return 图片所对应的bitmap
	 */
	public Bitmap getBitmapImage(String path) throws Exception {
		URL url = new URL(path);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 2;
		return BitmapFactory.decodeStream(is, null, opts);
	}

	/**
	 * 从网络上获取图片并转化为bitma
	 * 
	 * @param imgUrl
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(String imgUrl) {
		URL url;
		Bitmap bitmap = null;
		try {
			url = new URL(imgUrl);
			InputStream is = url.openConnection().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] b = getBytes(is);
			bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
			bis.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = is.read(b, 0, 1024)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
}
