package com.taikang.tkdoctor.activity.mycenter;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.ChrDiseaseRiskActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.ReportInfoBean;
import com.taikang.tkdoctor.db.ReportDBDaoImp;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;
@ContentView(R.layout.activity_tcm_phisique_man_bing)
public class TcmPhisiqueManBingActivity extends BaseActivity {
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("慢病风险评估报告");
	}
	@OnClick(R.id.rl_QueXue)
	private void QueXue(View view){
		showAlertDialog(PreferencesUtil.getString("quexue"),excuteSQL(Constants.HEART_DISEASE), "您还尚未对缺血性心血管病进行检测", "立即评测", "heart_disease");
	}
	@OnClick(R.id.rl_TangNiao)
	private void TangNiao(View view){
		showAlertDialog(PreferencesUtil.getString("tangniaobing"),excuteSQL("diabete"), "您还尚未对该项体质进行检测", "立即评测", "diabete");

	}
	@OnClick(R.id.rl_DaiXie)
	private void DaiXie(View view){
		showAlertDialog(PreferencesUtil.getString("daixie"),excuteSQL("metabolism"), "您还尚未对该项体质进行检测", "立即评测", "metabolism");
	}
	@OnClick(R.id.rl_GaoXueYa)
	private void GaoXueYa(View view){
		showAlertDialog(PreferencesUtil.getString("gaoxueya"),excuteSQL("high_blood_pressure"), "您还尚未对该项体质进行检测", "立即评测", "high_blood_pressure");
	}
	private List<ReportInfoBean> excuteSQL(String report_type) {
		ReportDBDaoImp reportDb=new ReportDBDaoImp(this);
		String sql="select * from report where report_type = ?";
		List<ReportInfoBean> bean=reportDb.queryBySQL(sql, new String[]{report_type});
		return bean;
	}
	/*
	 * 展示对话框
	 */
	private void showAlertDialog(String str,List<ReportInfoBean> bean,String title,String btnMsg,final String report_type) {
//		if(bean.isEmpty()){
		if(str==null){
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle(title);
			builder.setPositiveButton(btnMsg, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(TcmPhisiqueManBingActivity.this,ChrDiseaseRiskActivity.class);
					intent.putExtra("report_type", report_type);
					startActivity(intent);
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			builder.create().show();
		}else{
//			bean.get(0);
			Intent intent=new Intent(this,TcmPhysiqueResultActivity.class);
//			intent.putExtra("report_url", bean.get(0).getReport_url());
			intent.putExtra("report_url", str);
			intent.putExtra("report_type", report_type);
			startActivity(intent);
		}
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

}
