package com.taikang.tkdoctor.fragment.selfdiagnosis;

import java.io.Serializable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.bean.MyQuestionList.Questions;

public class SelfDiagnosisResultFragment extends BaseFragment{
	@ViewInject(R.id.txtCall)
	private TextView txtCall;
	@ViewInject(R.id.txtResult)
	private TextView txtResult;
	private Questions questions;
	@ViewInject(R.id.txtDepart)
	private TextView txtDepart;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_selfdiagnosis_result;
	}
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("导诊结果");
		questions=getMyArgument();
		setData();
	}
	//设置data
	private void setData() {
		// TODO Auto-generated method stub
		if(questions!=null){
			txtResult.setText(questions.getDatacontent());
			if(null!=questions.getDepartment()){
				txtDepart.setText(questions.getDepartment());
			}

		}

	}
	private Questions getMyArgument(){
		questions=(Questions) getArguments().getSerializable("bean");
		return questions;
	}
	
	//一键问医生
	@OnClick(R.id.txtCall)
	private void txtCall(View view){
		Intent  intent=new Intent();
		//激活源代码,添加intent对象
		intent.setAction("android.intent.action.CALL");
		//intent.addCategory("android.intent.category.DEFAULT");内部会自动添加类别，
		intent.setData(Uri.parse("tel:"+"100011"));
		//激活Intent
		startActivity(intent);

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
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		getActivity().finish();
	}
}
