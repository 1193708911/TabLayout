package com.taikang.tkdoctor.activity.mycenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.sword.dialog.MyAlertDialog;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HealthRiskActivity;
import com.taikang.tkdoctor.activity.main.PingCheTangniaoResultActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.ReportInfoBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.MyTestBiz;
import com.taikang.tkdoctor.db.ReportDBDaoImp;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;
@ContentView(R.layout.activity_mytest)
public class MyTestsActivity extends BaseActivity {

	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("我的评测");
		//		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		MyTestBiz biz=new MyTestBiz();
		biz.setNetCallBack(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub

			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub

			}
		});
		biz.getMyTestResult(MainApplication.getInstance().getUser().getUserPhone());
	}

	@OnClick(R.id.imgManBing)
	private void imgManBing(View view){
		Intent intent=new Intent(this,TcmPhisiqueManBingActivity.class);
		startActivity(intent);

	}
	@OnClick(R.id.imgJianKang)
	private void imgJianKang(View view){
		showAlertDialog(PreferencesUtil.getString("jiankang"),excuteSQL("health"),"您还尚未进行健康风险评测","立即评测","health");
	}

	@OnClick(R.id.imgTiZhi)
	private void imgTiZhi(View view){
		//		Intent intent=new Intent(this,PingCheResultActivity.class);
		//		startActivity(intent);

		//这块进入的直接是中医体质评估报告页面
		//同时也传递过去对应的Url
		// getQuestionrResult
		// 跳转时加载进入条
		// 跳转之前作出判断 判断是否已经有过评估
		//重新规范提高解耦行
		showAlertDialog(PreferencesUtil.getString("tizhi"),excuteSQL("tcmphysique"),"您还尚未进行中医体质评测","立即评测","tcmphysique");

	}

	private List<ReportInfoBean> excuteSQL(String report_type) {
		ReportDBDaoImp reportDb=new ReportDBDaoImp(this);
		String sql="select * from report where report_type=?";
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
					if(report_type.equals(Constants.TCMPHYSIQUE)){
						Intent intent=new Intent(MyTestsActivity.this,TcmPhysiqueActivity.class);
						intent.putExtra("report_type", report_type);
						startActivity(intent);
					}
					if(report_type.equals(Constants.HEALTH)){
						Intent intent=new Intent(MyTestsActivity.this,HealthRiskActivity.class);
						intent.putExtra("report_type", report_type);
						startActivity(intent);}
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
