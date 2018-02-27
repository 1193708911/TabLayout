package com.taikang.tkdoctor.fragment.main;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.global.ManBingQuestCache;

/**
 * 健康风险评估性別
 */
public class HealthRiskFragment extends BaseFragment {

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.id_health_sex_nan)
	private TextView SexNan;
	@ViewInject(R.id.id_health_sex_nv)
	private TextView SexNv;
	private Map<String, String> paramsMap0 = new HashMap<String, String>();

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initData();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_healthrisk;
	}

	@OnClick(R.id.id_health_sex_nan)
	public void onClick1(View view) {
//		paramsMap0.put("sex", "0");
//		MainApplication.getInstance().listparamsMap.add(paramsMap0);
//		MainApplication.getInstance().params_manbing.put("sex", "0");
		ManBingQuestCache.sex = "0";
		healthActivity.replaceHealthRiskHealthFragment();
	}

	@OnClick(R.id.id_health_sex_nv)
	public void onClick2(View view) {
//		paramsMap0.put("sex", "1");
//		MainApplication.getInstance().listparamsMap.add(paramsMap0);
//		MainApplication.getInstance().params_manbing.put("sex", "1");
		ManBingQuestCache.sex = "1";
		healthActivity.replaceHealthRiskHealthFragment();
	}

	@Override
	protected void initData() {
		txtTitleText.setText("您的性别？");
		tvTitleRight.setVisibility(View.GONE);
	}

	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		getActivity().finish();
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
