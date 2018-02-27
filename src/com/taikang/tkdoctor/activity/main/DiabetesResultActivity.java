package com.taikang.tkdoctor.activity.main;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
@ContentView(R.layout.activity_diabetes_result)
public class DiabetesResultActivity extends BaseActivity {
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initView();


	}
	private void initView() {
		// TODO Auto-generated method stub
		txtTitleText.setText("糖尿病慢病评估报告");
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
}
