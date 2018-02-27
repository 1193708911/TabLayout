package com.taikang.tkdoctor.activity.main;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
@ContentView(R.layout.activity_chr_disease_risk)
public class ChrDiseaseRiskActivity extends BaseActivity {
	@ViewInject(R.id.txtDX)
	private TextView txtDX;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("慢病评估");
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
	@OnClick(R.id.txtXXG)
	private void xinXueGuan(View view){
		Intent  intent =new Intent(getApplication(),ChrDiseaseInfoActivity.class);
		intent.putExtra("value", "0");
		startActivity(intent);
	}
	@OnClick(R.id.txtTNB)
	private void tangNiaoBing(View view){
		Intent  intent =new Intent(getApplication(),ChrDiseaseInfoActivity.class);
		intent.putExtra("value", "1");
		startActivity(intent);
	}
	@OnClick(R.id.txtGXY)
	private void gaoXueYa(View view){
		Intent  intent =new Intent(getApplication(),ChrDiseaseInfoActivity.class);
		intent.putExtra("value", "2");
		startActivity(intent);
	}
	@OnClick(R.id.txtDX)
	private void daiXie(View view){
		Intent  intent =new Intent(getApplication(),ChrDiseaseInfoActivity.class);
		intent.putExtra("value", "3");
		startActivity(intent);
	}
}
