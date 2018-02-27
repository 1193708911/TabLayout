package com.taikang.tkdoctor.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HealthRiskActivity;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.db.DBManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public abstract class BaseFragment extends Fragment{
	protected MainApplication mApp = MainApplication.getInstance();
	protected BaseActivity baseActivity;
	protected HomeActivity homeActivity;
	protected HealthRiskActivity healthActivity;
	protected View rootView;
	protected DBManager dbManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.d("onCreateView");
		super.onCreateView(inflater, container, savedInstanceState);
//		dbManager = mApp.getDbManager();//暂时注掉
		FragmentActivity activity = getActivity();
		if (activity instanceof BaseActivity) {
			baseActivity = (BaseActivity) activity;
		}
		if (activity instanceof HomeActivity) {
			homeActivity = (HomeActivity) activity;
		}
		if (activity instanceof HealthRiskActivity) {
			healthActivity = (HealthRiskActivity) activity;
		}
		rootView = inflater.inflate(getResource(), null);
		ViewUtils.inject(this, rootView);
		afterViews();
		return rootView;
	}

	protected abstract int getResource();
	
	protected abstract void initData();
	
	protected abstract void initWidget();
	
	abstract public void loadData();

	protected void afterViews() {
		
	}

	public View layoutInflater(int resource, ViewGroup root) {
		return LayoutInflater.from(getActivity()).inflate(resource, root, false);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (healthActivity != null) {
			healthActivity = null;
		}
	}
	
//	public Animation scaleAnimationin(){
//		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniin);
//		return animation;
//	}
//	public Animation scaleAnimationout(){
//		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniout);
//		return animation;
//	}
//	public void resetAnimation(String isvalue,View view){
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		view.startAnimation(scaleAnimationin());
//	}
}
