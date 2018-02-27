package com.taikang.tkdoctor.fragment.main;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.customview.RulerHandler;
import com.taikang.tkdoctor.customview.VerticalRuler;
import com.taikang.tkdoctor.global.ManBingQuestCache;
import com.umeng.socialize.utils.Log;
/**
 * 健康评估身高*/
public class HealthRiskHeightFragment extends BaseFragment{

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;

	@ViewInject(R.id.tv_myhealthi)
	private TextView tvMyHealth;
	
	@ViewInject(R.id.vertical_ruler)
	private VerticalRuler ruler;
	
	private String mheight="150";
	private Map<String, String> paramsMap1 = new HashMap<String, String>();

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_healthrisk_health;
	}
	
	private RulerHandler rulerHandler2 = new RulerHandler() {
		
		@Override
		public void markScrollto(int max, int min, float val) {
			LogUtils.e("ruler.."+max+"..min..."+min+"..val.."+val);
			LogUtils.e("ruler..."+(float)max+((float)min+val)/10);//240-93  147
			String showValue=String.format("%.01f", (240-(float)max+((float)min+val)/10));
			if("100".equals(showValue) || "240".equals(showValue)){
				tvMyHealth.setText(showValue);
			}else{
				Double value=Double.parseDouble(showValue);
				int maxx=value.intValue();
				double minn=(1-(value-maxx));
				double result=(maxx-1)+minn;
				tvMyHealth.setText(String.valueOf(result));
			}
		}
	};
	
	
	private void setRuler(String valueString) {
//		Double value = Double.parseDouble(valueString)-100;//减去100至关重要
		Double value = 240-Double.parseDouble(valueString);//减去100至关重要
		int max = value.intValue();
		int min = (int) ((value.doubleValue() - max) * 10);
		float val = (float) (value.doubleValue() - max - min / 10.0f) * 10;
		ruler.scrollTo(max, min, val);
	}
	
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
	protected void initData() {
		txtTitleText.setText("您的身高？");
		tvTitleRight.setVisibility(View.GONE);
		//在这块进行判断
		if("0".equals(ManBingQuestCache.sex)){
			mheight="175";
		}else if("1".equals(ManBingQuestCache.sex)){
			mheight="150";
		}
		tvMyHealth.setText(mheight);
		ruler.setRulerHandler(rulerHandler2);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setRuler(mheight);//mHeight150显示 设置大小为100
			}
		}, 500);
	}

	@OnClick(R.id.img_health_next)
	private void goToNext(View view) {
//		paramsMap1.put("height", mheight);
//		MainApplication.getInstance().listparamsMap.add(paramsMap1);
//		MainApplication.getInstance().params_manbing.put("height", mheight);
		ManBingQuestCache.height = mheight;
//		LogUtils.i(JSON.toJSONString(mApp.params_manbing));
		healthActivity.replaceHealthRiskWeightFragment();
	}
	
	@OnClick(R.id.img_health_back)
	private void goToBack(View view) {
		healthActivity.replaceHealthRiskFragment();
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
