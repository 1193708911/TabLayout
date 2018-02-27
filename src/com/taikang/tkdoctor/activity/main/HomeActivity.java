package com.taikang.tkdoctor.activity.main;

import java.util.ArrayList;

import android.R.integer;
import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.alarmclock.AlarmClockManager;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetUserInfoBiz;
import com.taikang.tkdoctor.fragment.askdoctor.AskDoctorFragment;
import com.taikang.tkdoctor.fragment.information.InformationFragment;
import com.taikang.tkdoctor.fragment.main.HomeFragment;
import com.taikang.tkdoctor.fragment.selfdiagnosis.SelfDiagnosisFragment;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.ReturnCallback;
import com.taikang.tkdoctor.util.Util;

@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity{
	
	//首页
	@ViewInject(R.id.iv_tabMain)
	private ImageView ivTabMain;
	
	//资讯
	@ViewInject(R.id.iv_tabInformation)
	private ImageView ivTabInformation;

	
	//自诊
	@ViewInject(R.id.iv_tabSelfDiagnosis)
	private ImageView ivTabSelfDiagnosis;

	//问医
	@ViewInject(R.id.iv_tabAskDoctor)
	private ImageView ivTabAskDoctor;
	
	private HomeFragment mHomeFragment;
	private InformationFragment mInformationFragment;
	private SelfDiagnosisFragment mSelfDiagnosisFragment;
	private AskDoctorFragment mAskDoctorFragment;
	private ArrayList<Fragment> mListFragments;
	@Override
	protected void afterViews() {
		super.afterViews();
//		ivTabMain.performClick();
		initData();
		setNextAlarm();
	}

	
	//进入主界面之后开始设置最近一次闹钟
	private void setNextAlarm(){
		AlarmClockManager.setNextAlarm(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		initData();
	}

	private void initData() {
		boolean isLogin = false;
		isLogin = PreferencesUtil.getBoolean("isLogin");
		AutoLogin autoLogin = new AutoLogin(mThis);
		if (!isLogin) {//未登录状态 ,逛逛登录
			autoLogin.playLogin(new ReturnCallback() {
				
				@Override
				public void taskSuccess() {
					// TODO Auto-generated method stub
//					ShowShortToast("逛逛登录");
//					getBaseInfo();
					ivTabMain.performClick();
				}
				
				@Override
				public void taskError() {
					// TODO Auto-generated method stub
//					startActivity(new Intent(mThis,LoginActivity.class));
					StartLogin();
					finish();
				}
			});
			return;
		}
		//正式用户自动登录
		autoLogin.autoLogin(new ReturnCallback() {
			
			@Override
			public void taskSuccess() {
				// TODO Auto-generated method stub
//				ShowShortToast("用户登录");
				getBaseInfo();
				ivTabMain.performClick();
			}
			
			@Override
			public void taskError() {
				// TODO Auto-generated method stub
				StartLogin();
				finish();
			}
		});
	}
	
	private void StartLogin(){
		startActivity(new Intent(mThis,LoginActivity.class));
//		startActivityForResult(new Intent(mThis,LoginActivity.class), 100);
	}
	
	//获取基本信息
	public static void getBaseInfo() {
		// TODO Auto-generated method stub
		GetUserInfoBiz getUserInfoBiz=new GetUserInfoBiz();
		getUserInfoBiz.setCallback(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				MyUtil.saveUser(response);
				
			}
			
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				LogUtils.d("获取基本信息失败");
				
				
			}
		});
		getUserInfoBiz.getUserBaseInfo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@OnClick(R.id.iv_tabMain)
	public void tvTabHome(View v){
		resetTabImages();
		ivTabMain.setImageResource(R.drawable.menu_home_selected);
		replaceHomeFragment();
//		vpHome.setCurrentItem(0);
	}
	
	@OnClick(R.id.iv_tabInformation)
	public void tvTabInfor(View v){
		resetTabImages();
		ivTabInformation.setImageResource(R.drawable.menu_read_selected);
		replaceInforFragment();
//		vpHome.setCurrentItem(1);
	}
	
	@OnClick(R.id.iv_tabSelfDiagnosis)
	public void tvTabSelf(View v){
		resetTabImages();
		ivTabSelfDiagnosis.setImageResource(R.drawable.menu_information_selected);
		replaceSelfDiagnFragment();
//		vpHome.setCurrentItem(2);
	}
	
	@OnClick(R.id.iv_tabAskDoctor)
	public void tvTabAsk(View v){
		resetTabImages();
		ivTabAskDoctor.setImageResource(R.drawable.menu_ask_selected);
		replaceAskDocFragment();
//		vpHome.setCurrentItem(3);
	}
	
	private void resetTabImages() {
		ivTabMain.setImageResource(R.drawable.menu_home_default);
		ivTabInformation.setImageResource(R.drawable.menu_read_deafult);
		ivTabSelfDiagnosis.setImageResource(R.drawable.menu_information_deafult);
		ivTabAskDoctor.setImageResource(R.drawable.menu_ask_default);
	}
	
	public void replaceHomeFragment() {
		if (mHomeFragment == null) {
			mHomeFragment = new HomeFragment();
		}
		replaceFragment(R.id.content_frame, mHomeFragment);
	}
	
	private void replaceInforFragment() {
		mInformationFragment = new InformationFragment();
//		if (mInformationFragment == null) {
//			mInformationFragment = new InformationFragment();
//		}
		replaceFragment(R.id.content_frame, mInformationFragment);
	}
	
	private void replaceSelfDiagnFragment() {
		if (mSelfDiagnosisFragment == null) {
			mSelfDiagnosisFragment = new SelfDiagnosisFragment();
		}
		replaceFragment(R.id.content_frame, mSelfDiagnosisFragment);
	}
	
	private void replaceAskDocFragment() {
		if (mAskDoctorFragment == null) {
			mAskDoctorFragment = new AskDoctorFragment();
		}
		replaceFragment(R.id.content_frame, mAskDoctorFragment);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode,
			Bundle options) {
		super.startActivityForResult(intent, requestCode, options);
	}
	
}
