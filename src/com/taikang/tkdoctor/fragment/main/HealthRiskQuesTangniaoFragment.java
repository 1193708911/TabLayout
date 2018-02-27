package com.taikang.tkdoctor.fragment.main;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
 * 健康评估问卷(糖尿病)*/
public class HealthRiskQuesTangniaoFragment extends BaseFragment{

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.Btn_A)
	private Button mBtnA;
	@ViewInject(R.id.Btn_B)
	private Button mBtnB;
	@ViewInject(R.id.Btn_C)
	private Button mBtnC;
	private Map<String, String> paramsMap3 = new HashMap<String, String>();

	private String IsValue;
	private String value;

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

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_health_ques1;
	}
	protected void initData() {

	}

	@OnClick(R.id.Btn_A)
	private void btnA(View view) {
		IsValue="A";
		value = "0";
		resetAnimation(IsValue, view);
	}

	@OnClick(R.id.Btn_B)
	private void btnB(View view) {
		IsValue="B";
		value = "1";
		resetAnimation(IsValue, view);
	}

	@OnClick(R.id.Btn_C)
	private void btnC(View view) {
		IsValue="C";
		value = "2";
		resetAnimation(IsValue, view);
	}
	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
		ManBingQuestCache.tnb = value;
		healthActivity.replaceGaoxueyaFragment();
	}
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceHealthRiskWeightFragment();
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		startActivity(new Intent(getActivity(),HomeActivity.class));
	}
	public void resetAnimation(String isvalue,View view){
		if("A".equals(IsValue)){
			mBtnA.startAnimation(scaleAnimationout());
		}else if("B".equals(isvalue)){
			mBtnB.startAnimation(scaleAnimationout());
		}else if("C".equals(isvalue)){
			mBtnC.startAnimation(scaleAnimationout());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.startAnimation(scaleAnimationin());
	}
	public Animation scaleAnimationin(){
		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniin);
		return animation;
	}
	public Animation scaleAnimationout(){
		Animation animation=AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniout);
		return animation;
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