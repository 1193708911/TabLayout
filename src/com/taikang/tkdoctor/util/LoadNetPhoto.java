package com.taikang.tkdoctor.util;

import com.cn.afinal.FinalBitmap;

import android.app.Activity;
import android.os.Environment;

public class LoadNetPhoto {
	public static LoadNetPhoto mLoad;
	private FinalBitmap fb;
	private LoadNetPhoto() {

	}

	public static LoadNetPhoto getInstance() {
		if (mLoad == null) {
			mLoad = new LoadNetPhoto();
		}
		return mLoad;
	}

	public FinalBitmap getFinalBit(Activity act) {
		if (fb == null) {
			initFinalBit(act);
		}
		return fb;
	}

	public void initFinalBit(Activity act) {
		fb = new FinalBitmap(act);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			fb.configDiskCachePath(Environment.getExternalStorageDirectory()
					+ "/kjd/photo/");
		}
		fb.configMemoryCacheSize(10 * 1024 * 1024);
		fb.configMemoryCachePercent(0.1f);
		fb.configBitmapLoadThreadSize(10);
		fb.init();
	}

}
