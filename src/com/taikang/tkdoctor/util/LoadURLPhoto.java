package com.taikang.tkdoctor.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.taikang.tkdoctor.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

public class LoadURLPhoto extends AsyncTask<String, Void, Bitmap> {

	private String cachePath = "/kjd/photo/";;
	private ImageView imageView;
	private int width;
	private int height;
	private String imgUrl;
	private boolean isCycle = false;
	private boolean isCache = false;
	private int deaafutLoadingId;

	/**
	 * 
	 * @param act
	 *            上下文对象
	 * @param imageView
	 *            显示网络图片的控件
	 * @param width
	 *            等比例缩放图片的宽
	 * @param height
	 *            等比例缩放图片的高
	 * @param isCycle
	 *            是否对图片圆角处理
	 */
	public LoadURLPhoto(ImageView imageView, int width, int height) {
		this.imageView = imageView;
		this.width = width;
		this.height = height;
	}

	public LoadURLPhoto(ImageView imageView) {
		super();
		this.imageView = imageView;
		height = imageView.getHeight();
		width = imageView.getWidth();
		System.out.println("控件宽:" + width + "控件高:" + height);
	}

	/**
	 * 设置默认加载图片的id
	 * 
	 * @param deaafutLoadingId
	 */
	public void setDeaafutLoadingId(int deaafutLoadingId) {
		this.deaafutLoadingId = deaafutLoadingId;
	}

	/**
	 * 是否缓存图片到sdCard
	 * 
	 * @param isCache
	 */
	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}

	/**
	 * 是否对图片圆角处理
	 * 
	 * @param isCycle
	 */
	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	/**
	 * 设置图片的缓存路径
	 * 
	 * @param cachePath
	 */
	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	/**
	 * 圆角显示图片
	 * 
	 * @param image
	 * @param width
	 * @param height
	 */
	private void aroundImage(ImageView image, int width, int height) {
		// TODO Auto-generated method stub
		// setImageSize(image, width, height);
		toRoundBitmap(image);
	}

	private void toRoundBitmap(ImageView view) {
		Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
		if (null != bitmap) {
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(toRoundBitmap(bitmap));
			view.setImageDrawable(drawable);
		}
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (null == bitmap) {
			return null;
		}
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * @param view
	 *            ImageView
	 * @param width
	 *            dp
	 * @param height
	 *            dp
	 */
	public static void setImageSize(ImageView view, int width, int height) {
		Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
		if (null != bitmap) {
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(bitmap);
			view.setImageDrawable(drawable);
		}
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		String path = params[0];
		imgUrl = path;
		Bitmap bitmap = getCacheBitmap(path);
		if (bitmap != null) {
			return bitmap;
		}
		bitmap = getBitmapFromUrl(path);
		if (bitmap != null && width != 0 && height != 0) {
			bitmap = BitmapUtils.comp(bitmap, width, height);
		}
		saveBitmap(bitmap);
		return bitmap;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		imageView.setImageResource(R.drawable.pic_load);
		if (deaafutLoadingId != 0) {
			imageView.setImageResource(deaafutLoadingId);
		}
		if (isCycle) {
			try {
				aroundImage(imageView, width, height);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (result != null) {
			if (isCycle && width != 0 && height != 0) {
				imageView.setImageBitmap(result);
				aroundImage(imageView, width, height);
			} else {
				imageView.setImageBitmap(result);
			}
		}
	}

	private void saveBitmap(Bitmap bitmap) {
		// TODO Auto-generated method stub
		try {
			if (isCache && checkSDcard()) {
				File fileDir = creatDir(cachePath);
				String filePath = getPhotoName(imgUrl);
				if (fileDir != null && filePath != null) {
					File file = new File(fileDir, filePath);
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(CompressFormat.JPEG, 100, fos);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Bitmap getCacheBitmap(String imgUrl) {
		Bitmap bitmap = null;
		if (isCache && checkSDcard()) {
			try {
				File fileDir = creatDir(cachePath);
				String filePath = getPhotoName(imgUrl);
				if (fileDir != null && filePath != null) {
					File file = new File(fileDir, filePath);
					if (width != 0 && height != 0) {
						bitmap = BitmapUtils.getimage(file.getAbsolutePath(),
								width, height);
					} else {
						bitmap = BitmapFactory.decodeFile(file
								.getAbsolutePath());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	private File creatDir(String cachePath) {
		File fileDir = null;
		if (null == cachePath) {
			return fileDir;
		}
		try {
			fileDir = new File(Environment.getExternalStorageDirectory()
					+ cachePath);
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fileDir;
	}

	private boolean checkSDcard() {
		boolean isOk = false;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			isOk = true;
		}
		return isOk;
	}

	/**
	 * 获取网络图片的名字
	 * 
	 * @param imagepath
	 * @return
	 */
	private String getPhotoName(String imagepath) {
		String iconname = null;
		if (imagepath == null) {
			return iconname;
		}
		try {
			int start = imagepath.lastIndexOf("/");
			int end = imagepath.length();
			iconname = imagepath.substring(start, end);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return iconname;
	}

	/**
	 * 从网络上获取图片并转化为bitmap
	 * 
	 * @param imgUrl
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String imgUrl) {
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

	private byte[] getBytes(InputStream is) throws IOException {
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
