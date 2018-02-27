package com.taikang.tkdoctor.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.db.DBManager;
import com.taikang.tkdoctor.util.AppManager;


public class BaseActivity extends FragmentActivity {
	
	protected MainApplication mApp = MainApplication.getInstance();
	protected BaseActivity mThis;
	protected FragmentManager fragmentManager;
	protected DBManager dbManager;

	public BaseActivity() {
		mThis = this;
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_base);
        fragmentManager = getSupportFragmentManager();
//		dbManager = MainApplication.getInstance().getDbManager();//暂时注掉
		ViewUtils.inject(mThis);
		AppManager.getInstance().addActivity(mThis);
		afterViews();
    }
    
    protected void afterViews() {
	}
    
    @Override
	protected void onStart() {
		super.onStart();
		LogUtils.d("onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtils.d("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtils.d("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtils.d("onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.d("onDestroy");
		AppManager.getInstance().removeActivity(mThis);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		// overridePendingTransition(R.anim.scale_center_in,
		// R.anim.alpha_none_out);
	}

	@Override
	public void startActivityFromFragment(Fragment fragment, Intent intent,
			int requestCode) {
		super.startActivityFromFragment(fragment, intent, requestCode);
		/*
		 * 注意：此方法只能在startActivity和finish方法之后调用。
		 * 第一个参数为第一个Activity离开时的动画，第二参数为所进入的Activity的动画效果
		 */
		// overridePendingTransition(R.anim.scale_center_in,
		// R.anim.alpha_none_out);
	}

	/**
	 * 必须有，否则Fragment中的onActivityResult不起作用
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	public void finish() {
		super.finish();
		// overridePendingTransition(R.anim.alpha_none_in,
		// R.anim.scale_center_out);
	}

	protected void replaceFragment(int containerViewId, Fragment fragment) {
		fragmentManager.beginTransaction().replace(containerViewId, fragment)
				.commit();
	}

	private Activity getActivity() {
		return mThis;
	}

	public View layoutInflater(int resource, ViewGroup root) {
		return LayoutInflater.from(getActivity())
				.inflate(resource, root, false);
	}

	protected void ShowShortToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	protected void ShowShortToast(int id) {
		Toast.makeText(this, getResources().getString(id), Toast.LENGTH_SHORT)
				.show();
	}

	protected void ShowLongToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	protected void ShowLongToast(int id) {
		Toast.makeText(this, getResources().getString(id), Toast.LENGTH_LONG)
				.show();
	}
//	/**
//	 * 监听返回事件
//	 */
//	private long exitTime=0;
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN) {
//			if ((System.currentTimeMillis() - exitTime) > 2000) {
//				ShowLongToast("再按一次退出程序");
//				exitTime = System.currentTimeMillis();
//			} else {
////				finish();
//				System.exit(0);
//			}
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}

}
