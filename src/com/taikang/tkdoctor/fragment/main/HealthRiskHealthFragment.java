package com.taikang.tkdoctor.fragment.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.base.BaseFragment;
/**
 * 健康评估身高*/
public class HealthRiskHealthFragment extends BaseFragment{
	
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	
	@ViewInject(R.id.tv_myhealthi)
	private TextView tvMyHealth;
	
	
	
	private String sex;
	private String health;
	
	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_healthrisk_health;
	}
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initData();
	}
	@Override
	protected void initData() {
		txtTitleText.setText("您的身高？");
		tvTitleRight.setVisibility(View.GONE);
	}

	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
		healthActivity.replaceHealthRiskWeightFragment();
	}
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceHealthRiskFragment();
	}
	
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		startActivity(new Intent(getActivity(),HomeActivity.class));
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
