package com.taikang.tkdoctor.fragment.selfdiagnosis;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.selfdiagnosis.SelfDiagnosisActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.requestback.SymptonListBean.SymptonBean;
import com.taikang.tkdoctor.bean.AutognosisQuestionBean;
import com.taikang.tkdoctor.bean.MyQuestionList.Questions;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetAutognosisQuestionBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;

public class SelfDiagnosisQuesFragment extends BaseFragment implements NetCallback{
	/**
	 * 获取问题
	 */
	private String age="";
	private String id="";
	private String sex="";
	private SymptonBean symptonBean;
	private Questions questions;
	@ViewInject(R.id.tv_question)
	private TextView tv_question;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.btn_yes)
	private Button btn_yes;
	@ViewInject(R.id.btn_no)
	private Button btn_no;
	private SelfDiagnosisActivity mActivity;
	private int time = 0;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("疾病自诊");
		mActivity=(SelfDiagnosisActivity) getActivity();
		resetMyBac();
		btn_yes.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.drawable_selfselect));
		btn_yes.setTextColor(Color.WHITE);
		symptonBean=(SymptonBean) getArguments().getSerializable("bean");
		sendRequestQuestion(null,"1");
	}

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_selfdiagnosis_quest;
	}
	//第一个问题
	private void sendRequestQuestion(String id,String root) {
		// TODO Auto-generated method stub
		GetAutognosisQuestionBiz getQuestionBiz=new GetAutognosisQuestionBiz();
		getQuestionBiz.setCallback(this);
		//接口改动，反复发作性呕吐
		
		LogUtils.e("问题Id" + id + "***"+ root);
		if (time != 0) {
			getQuestionBiz.getAutognosisQuestion(PreferencesUtil.getString("sex"),PreferencesUtil.getString("age"),id,symptonBean.getSymptom(), root);
			time = time + 1;
			return;
		}
		time = time + 1;
		String sympton = symptonBean.getSymptom();
		if (!sympton.equals("反复发作性呕吐")) {
			getQuestionBiz.getAutognosisQuestion(PreferencesUtil.getString("sex"),PreferencesUtil.getString("age"),id,symptonBean.getSymptom(), root);
			return;
		}
		
		String sex = PreferencesUtil.getString("sex");
		if (sex.equals("1")) {//男性
			getQuestionBiz.getAutognosisQuestion(PreferencesUtil.getString("sex"),PreferencesUtil.getString("age"),id,symptonBean.getSymptom(), "321");
		}else {//女性
			getQuestionBiz.getAutognosisQuestion(PreferencesUtil.getString("sex"),PreferencesUtil.getString("age"),id,symptonBean.getSymptom(), "320");
		}

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
	/**
	 * 确定
	 * @param view
	 */
	@OnClick(R.id.btn_yes)
	private void yes(View view){
		resetMyBac();
		btn_yes.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.drawable_selfselect));
		btn_yes.setTextColor(Color.WHITE);
		if(isEmpty()){
			mActivity.replaceResltFragment(questions);
		}else {
			if("1".equals(PreferencesUtil.getString("sex"))){
				id=questions.getMaleis();

			}else if("0".equals(PreferencesUtil.getString("sex"))){
				id=questions.getFemaleis();
			}
			sendRequestQuestion(id, "0");
		}
	}
	/**
	 * 取消
	 * @param view
	 */
	@OnClick(R.id.btn_no)
	private void no(View view){
		resetMyBac();
		btn_no.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.drawable_selfselect));
		btn_no.setTextColor(Color.WHITE);
		if(isEmpty()){
			mActivity.replaceResltFragment(questions);
		}else {
			if("1".equals(PreferencesUtil.getString("sex"))){
				id=questions.getMaleno();

			}else if("0".equals(PreferencesUtil.getString("sex"))){
				id=questions.getFemaleno();
			}
			sendRequestQuestion(id, "0");
		}

	}
	//重置背景
	private void resetMyBac(){
		btn_no.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.drawable_selfunselect));
		btn_yes.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.drawable_selfunselect));
		btn_yes.setTextColor(Color.BLACK);
		btn_no.setTextColor(Color.BLACK);
	}
	public boolean isEmpty(){
		if(null!=questions.getFemaleis()
				&&null!=questions.getFemaleno()
				&&null!=questions.getMaleis()
				&&null!=questions.getMaleno()){
			return false;
		}else {
			return true;
		}


	}
	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		if(response!=null){
			AutognosisQuestionBean questionBean=(AutognosisQuestionBean) response;
			if(questionBean!=null){
				questions=questionBean.getResultlist().get(0).getResultlist().get(0);
				tv_question.setText(questions.getDatacontent());
			}
		}
	}

	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		Util.showToast("获取问题失败");

	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		getActivity().finish();
	}

}
