package com.taikang.tkdoctor.customview;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.cn.afinal.FinalBitmap;
import com.cn.afinal.bitmap.core.BitmapDisplayConfig;
import com.cn.afinal.bitmap.display.Displayer;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.util.BitmapUtils;
import com.taikang.tkdoctor.util.ImageZoom;
import com.taikang.tkdoctor.util.LoadImageAsynTask;
import com.taikang.tkdoctor.util.LoadImageAsynTask.ImageTaskCallback;
import com.taikang.tkdoctor.util.LoadNetPhoto;
import com.taikang.tkdoctor.util.LoadURLPhoto;
import com.taikang.tkdoctor.util.Util;


/**
 * @ClassName: SlideImageLayout
 * @Description: 切换圆点view
 * @author helongfei
 * @date
 * 
 */
public class SlideImageLayout {
	private ArrayList<ImageView> mImageList = null;
	private ImageView[] mImageViews = null;
	private ImageView mImageView = null;
	private int pageIndex = 0;
	private ImgOncLick mImgclick;
	private FinalBitmap fb;
	private Activity act;
	private static final String PHOTO_PATH = "/kjd/photo/";
	public SlideImageLayout(Activity act) {
		this.act = act;
		mImageList = new ArrayList<ImageView>();
		fb = LoadNetPhoto.getInstance().getFinalBit(act);
	}

	/**
	 * 加载本地图片
	 * 
	 * @param id
	 *            图片资源Id
	 * @return
	 */
	public View getSlideImageLayout(int id) {
		LinearLayout imageLinerLayout = new LinearLayout(act);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		ImageView iv = new ImageView(act);
		iv.setBackgroundResource(id);
		iv.setScaleType(ScaleType.CENTER_CROP);
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv, imageLinerLayoutParames);
		mImageList.add(iv);
		return imageLinerLayout;
	}

	/**
	 * 加载网络图片
	 * 
	 * @param imgUrl
	 *            图片url
	 * @return
	 */
	public View getSlideImageLayout(String imgUrl, int width, int height) {
		LinearLayout imageLinerLayout = new LinearLayout(act);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		ImageView iv = new ImageView(act);
		iv.setLayoutParams(imageLinerLayoutParames);
		iv.setScaleType(ScaleType.CENTER_CROP);
		//loadNetPhoto1(iv, imgUrl, width, height);
		//loadNetPhoto2(iv, imgUrl, width, height);
		loadNetPhoto3(iv, imgUrl, width, height);
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv);
		mImageList.add(iv);
		imageLinerLayout.setBackgroundColor(Color.LTGRAY);
		return imageLinerLayout;
	}
	
	private void loadNetPhoto3(ImageView iv,String imgUrl, int width, int height){
		fb.configLoadingImage(R.drawable.pic_load);
		fb.configLoadfailImage(R.drawable.pic_load);
		fb.configBitmapMaxHeight(Util.dpToPx(act, width));
		fb.configBitmapMaxWidth(Util.dpToPx(act, height));
		iv.setTag(imgUrl);
		fb.display(iv, imgUrl);
	}
	
	

	private void loadNetPhoto2(ImageView iv, String imgUrl, int width,
			int height) {
		// TODO Auto-generated method stub
		LoadURLPhoto loadURLPhoto = new LoadURLPhoto(iv, width, height);
		loadURLPhoto.setCache(true);
		loadURLPhoto.setCachePath(PHOTO_PATH);
		Bitmap bitmap = loadURLPhoto.getCacheBitmap(imgUrl);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
		} else {
			loadURLPhoto.execute(imgUrl);
		}
	}

	private void loadNetPhoto1(final ImageView iv, String imgUrl, int width,
			int height) {
		// TODO Auto-generated method stub
		boolean isCache = false;
		int start = imgUrl.lastIndexOf("/");
		int end = imgUrl.length();
		final String iconname = imgUrl.substring(start, end);
		System.out.println(iconname);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			isCache = true;
		}
		showPhoto(iv, imgUrl, isCache, iconname, width, height);
	}

	private void showPhoto(final ImageView iv, String imgUrl,
			final boolean isCache, final String iconname, final int width,
			final int height) {
		// TODO Auto-generated method stub
		File fileDir = null;
		if (isCache) {
			fileDir = new File(Environment.getExternalStorageDirectory()
					+ PHOTO_PATH);
			if (!fileDir.exists()) {
				fileDir.mkdir();
			}
		}
		File file = new File(fileDir, iconname);
		if (isCache && file.exists()) {
			// iv.setImageURI(Uri.fromFile(file));
			iv.setImageBitmap(BitmapUtils.getimage(file.getAbsolutePath(),
					width, height));
		} else {
			new LoadImageAsynTask(new ImageTaskCallback() {
				public void onImageLoaded(Bitmap bitmap) {
					if (bitmap != null) {

						bitmap = ImageZoom.extractThumbnail(bitmap, width,
								height);

						// bitmap = BitmapUtils.comp(bitmap, width, height);
						iv.setImageBitmap(bitmap);
						if (isCache) {
							try {
								File file = new File(Environment
										.getExternalStorageDirectory()
										+ PHOTO_PATH, iconname);
								FileOutputStream fos = new FileOutputStream(
										file);
								bitmap.compress(CompressFormat.JPEG, 100, fos);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

				public void beforeImageLoaded() {
					iv.setImageResource(R.drawable.pic_load);

				}
			}).execute(imgUrl);
		}
	}

	public View getLinearLayout(View view, int width, int height) {
		LinearLayout linerLayout = new LinearLayout(act);
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, height, 1);
		linerLayout.setPadding(2, 0, 2, 0);
		linerLayout.addView(view, linerLayoutParames);

		return linerLayout;
	}

	public void setCircleImageLayout(int size) {
		mImageViews = new ImageView[size];
	}

	public ImageView getCircleImageLayout(int index) {
		mImageView = new ImageView(act);
		mImageView.setLayoutParams(new LayoutParams(10, 10));
		mImageView.setScaleType(ScaleType.FIT_XY);

		mImageViews[index] = mImageView;

		if (index == 0) {
			mImageViews[index].setBackgroundResource(R.drawable.dot_selected1);
		} else {
			mImageViews[index].setBackgroundResource(R.drawable.dot_none1);
		}

		return mImageViews[index];
	}

	public void setPageIndex(int index) {
		pageIndex = index;
	}

	private class ImageOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mImgclick.onclickIndex(pageIndex);
		}
	}

	public interface ImgOncLick {
		public void onclickIndex(int index);
	}

	public void setOnImageClick(ImgOncLick mImgclick) {
		this.mImgclick = mImgclick;
	}

}
