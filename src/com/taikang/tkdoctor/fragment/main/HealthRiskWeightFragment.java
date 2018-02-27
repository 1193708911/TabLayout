package com.taikang.tkdoctor.fragment.main;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.activity.widget.ruler.Ruler;
import com.taikang.tkdoctor.activity.widget.ruler.RulerHandler;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.global.ManBingQuestCache;
import com.taikang.tkdoctor.util.AppManager;
import com.taikang.tkdoctor.util.Util;

/**
 * 健康评估体重
 */
public class HealthRiskWeightFragment extends BaseFragment {

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.ruler_weight)
	private Ruler rulerWeight;// 体重尺子
	@ViewInject(R.id.tv_myweight)
	private EditText mWeight;

	private static String mweightValue;
	private Map<String, String> paramsMap2 = new HashMap<String, String>();

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("您的体重？");
		tvTitleRight.setVisibility(View.GONE);
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
		return R.layout.activity_healthrisk_weight;
	}
	protected void initData() {
		mweightValue = "50";
		rulerWeight.setDefaultExtra((int) 50f);
		rulerWeight.setRulerHandler(rulerHandler);
		setViewData();
	}

	private void setViewData() {
		mWeight.setText(mweightValue);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				setRuler(mweightValue);
			}
		}, 500);
	}

	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
//		MainApplication.getInstance().listparamsMap.add(paramsMap2);
//		MainApplication.getInstance().params_manbing.put("weight", mweightValue);
//		LogUtils.i(JSON.toJSONString(MainApplication.getInstance().listparamsMap));
		ManBingQuestCache.weight = mweightValue;
		healthActivity.replaceTangniaoFragment();
	}
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceHealthRiskHealthFragment();
		
	}

	// 返回体重的数据
	private RulerHandler rulerHandler = new RulerHandler() {

		@Override
		public void markScrollto(int max, int min, float val) {
			// TODO Auto-generated method stub
			String returnValue = String.format("%.01f", ((float) max
					+ ((float) min + val) / 10 + 20));
			mweightValue = returnValue;
			mWeight.setText(returnValue);
//			paramsMap2.put("weight", mweightValue);
		}
	};

	private void setRuler(String valueString) {
		Double value = Double.parseDouble(valueString);
		int max = value.intValue();
		int min = (int) ((value.doubleValue() - max) * 10);
		float val = (float) (value.doubleValue() - max - min / 10.0f) * 10;
		rulerWeight.scrollTo(max, min, val);
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
