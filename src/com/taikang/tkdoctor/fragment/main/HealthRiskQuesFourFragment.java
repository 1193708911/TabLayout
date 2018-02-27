package com.taikang.tkdoctor.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.global.ManBingQuestCache;
import com.taikang.tkdoctor.util.AppManager;
import com.taikang.tkdoctor.util.Util;

/**
 * 健康评估(上班恐惧)*/
public class HealthRiskQuesFourFragment extends BaseFragment{
	
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.imageA)
	private ImageView imageA;
	@ViewInject(R.id.imageB)
	private ImageView imageB;
	@ViewInject(R.id.imageC)
	private ImageView imageC;
	private String IsValue;
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("健康评估问卷");
		tvTitleRight.setVisibility(View.GONE);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@OnClick(R.id.imageA)
	private void btnA(View view) {
		resetAnimation(IsValue, view);
		IsValue="A";
	}

	@OnClick(R.id.imageB)
	private void btnB(View view) {
		resetAnimation(IsValue, view);
		IsValue="B";
	}

	@OnClick(R.id.imageC)
	private void btnC(View view) {
		resetAnimation(IsValue, view);
		IsValue="C";
	}
	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
		ManBingQuestCache.ans_17 = IsValue;
		healthActivity.replaceSixFragment();
	}
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceThreeFragment();
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		startActivity(new Intent(getActivity(),HomeActivity.class));
	}
	public Animation scaleAnimationin(){
		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniin);
		return animation;
	}
	public Animation scaleAnimationout(){
		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniout);
		return animation;
	}
	public void resetAnimation(String isvalue,View view){
		if("A".equals(IsValue)){
			imageA.startAnimation(scaleAnimationout());
		}else if("B".equals(isvalue)){
			imageB.startAnimation(scaleAnimationout());
		}else if("C".equals(isvalue)){
			imageC.startAnimation(scaleAnimationout());
		}else {

		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.startAnimation(scaleAnimationin());
	}

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_health_ques4;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
}
