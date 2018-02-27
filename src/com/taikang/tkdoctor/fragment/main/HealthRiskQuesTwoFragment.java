package com.taikang.tkdoctor.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
 * 健康评估问卷(主食量)*/
public class HealthRiskQuesTwoFragment extends BaseFragment{

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	private String sex;
	private String health;
	private String weight;
	@ViewInject(R.id.Btn_A)
	private Button Btn_A;
	@ViewInject(R.id.Btn_B)
	private Button Btn_B;
	@ViewInject(R.id.Btn_C)
	private Button Btn_C;
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

	@OnClick(R.id.Btn_A)
	private void btnA(View view) {
		resetAnimation(IsValue);
		Btn_A.startAnimation(scaleAnimationin());
		IsValue="A";
	}

	@OnClick(R.id.Btn_B)
	private void btnB(View view) {
		resetAnimation(IsValue);
		Btn_B.startAnimation(scaleAnimationin());
		IsValue="B";
	}

	@OnClick(R.id.Btn_C)
	private void btnC(View view) {
		resetAnimation(IsValue);
		Btn_C.startAnimation(scaleAnimationin());
		IsValue="C";
	}
	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
		ManBingQuestCache.ans_9 = IsValue;
		healthActivity.replaceThreeFragment();
	}
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceTengfengXueniaoFragment();
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
	public void resetAnimation(String isvalue){
		if("A".equals(IsValue)){
			Btn_A.startAnimation(scaleAnimationout());
		}else if("B".equals(isvalue)){
			Btn_B.startAnimation(scaleAnimationout());
		}else if("C".equals(isvalue)){
			Btn_C.startAnimation(scaleAnimationout());
		}else {

		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_health_ques2;
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
