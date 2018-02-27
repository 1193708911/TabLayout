package com.taikang.tkdoctor.activity.mycenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.ChrDiseaseInfoActivity;
import com.taikang.tkdoctor.activity.main.HealthRiskActivity;
import com.taikang.tkdoctor.activity.main.SeasonThreapyActivityNew;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.util.Constants;

@ContentView(R.layout.activity_tcmphysique_result)
public class TcmPhysiqueResultActivity extends BaseActivity {

	@ViewInject(R.id.webview_tcmphysique_result)
	private WebView webView_tcmphysique_result;

	@ViewInject(R.id.imgTitleBack)
	private ImageView imgTitleBack;

	@ViewInject(R.id.imgTitleRight)
	private ImageView imgTitleRight;

	@ViewInject(R.id.txtTitleText)
	private TextView  tvTitle;

	@ViewInject(R.id.tvTitleRight)
	private TextView  tvTitleRight;

	private String url_tcmphysique_result;

	private String type;
	@ViewInject(R.id.myProgressBar)
	private ProgressBar myProgressBar;

	//	private MyTcmPhicBean myTcmPhicBean;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initData();
		initTitle();
	}

	private void initTitle() {
		// TODO Auto-generated method stubi
		Intent intent=getIntent();
		type=intent.getStringExtra("report_type");
		if(type.equals(Constants.METABOLISM)){
			tvTitle.setText("代谢综合症评估报告");
		}
		if(type.equals(Constants.HEALTH)){
			tvTitle.setText("健康风险评估报告");
		}
		if(type.equals(Constants.TCMPHYSIQUE)){
			tvTitle.setText("中医体质评测报告");
		}

		if(type.equals(Constants.DIABETE)){
			tvTitle.setText("糖尿病风险评估报告");
		}
		if(type.equals(Constants.HIGHT_BLOOD_PRESSURE)){
			tvTitle.setText("高血压风险评估报告");
		}
		if(type.equals(Constants.HEART_DISEASE)){
			tvTitle.setText("心血管风险评估报告");
		}
	}

	@OnClick(R.id.imgTitleBack)
	public void goBack(View v){
		finish();
	}

	@OnClick(R.id.imgTitleBack)
	public void back(View v) {
		finish();
	}




	@SuppressLint("JavascriptInterface")
	private void initData() {
		// TODO Auto-generated method stub
		tvTitleRight.setTextSize(15);
		initWebView();

	}
	private void initWebView() {
		myProgressBar.setVisibility(View.VISIBLE);
		url_tcmphysique_result= getIntent().getStringExtra("report_url");
		imgTitleRight.setVisibility(View.GONE);
		tvTitleRight.setVisibility(View.VISIBLE);
		tvTitle.setText(getResources().getString(R.string.tcmphysique_report));
		tvTitleRight.setText(getResources().getString(R.string.re_evaluate));
		//		url_tcmphysique_result=getIntent().getStringExtra("url_tcmphysique");
		webView_tcmphysique_result.getSettings().setJavaScriptEnabled(true);
		//		String url = "http://www.baidu.com/";
		webView_tcmphysique_result.loadUrl(url_tcmphysique_result);
		webView_tcmphysique_result.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				myProgressBar.setVisibility(View.GONE);
			}
			
		});

//		webView_tcmphysique_result.addJavascriptInterface(new JavascriptYjwy(), "yjwy");一键问医
//		webView_tcmphysique_result.addJavascriptInterface(new JavascriptYangsheng(), "yangsheng");养生
//		webView_tcmphysique_result.addJavascriptInterface(new JavascriptPinggu(), "pinggu");亚健康风险评估
		
		webView_tcmphysique_result.addJavascriptInterface(new JavascriptInterface(), "jsInterface");
	}
	
	class JavascriptInterface {

		@SuppressWarnings("unused")
		@android.webkit.JavascriptInterface /** 解决Android 17（包括17）之后js无法调用Android方法*/
		
		public void invokeMethod(String type) {
			if (type.equals("yjwy")) {//处理一键问医
				Intent  intent=new Intent();
				//激活源代码,添加intent对象
				intent.setAction("android.intent.action.CALL");
				//intent.addCategory("android.intent.category.DEFAULT");内部会自动添加类别，
				intent.setData(Uri.parse("tel:"+"100011"));
				//激活Intent
				startActivity(intent);
			}else if (type.equals("yangsheng")) {//处理养生
				Intent intent=new Intent(TcmPhysiqueResultActivity.this, SeasonThreapyActivityNew.class);
				startActivity(intent);
			}else if (type.equals("pinggu")) {//处理亚健康风险评估
				Intent intent=new Intent(TcmPhysiqueResultActivity.this,HealthRiskActivity.class);
				startActivity(intent);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return false;
	}

	@OnClick(R.id.tvTitleRight)
	public void reEvaluate(View v){
		Intent intent=null;
		//进入评估界面
//		Intent  intent =new Intent(getApplication(),ChrDiseaseInfoActivity.class);
//		intent.putExtra("value", "0");
//		startActivity(intent);
		if(type.equals(Constants.METABOLISM)){
			intent=new Intent(this,ChrDiseaseInfoActivity.class);
			intent.putExtra("value", "3");
			
		}
		if(type.equals(Constants.HEALTH)){
			intent=new Intent(this,HealthRiskActivity.class);
		}
		if(type.equals(Constants.TCMPHYSIQUE)){
			intent=new Intent(this,TcmPhysiqueActivity.class);
		}

		if(type.equals(Constants.DIABETE)){
			intent=new Intent(this,ChrDiseaseInfoActivity.class);
			intent.putExtra("value", "1");
		}
		if(type.equals(Constants.HIGHT_BLOOD_PRESSURE)){
			intent=new Intent(this,ChrDiseaseInfoActivity.class);
			intent.putExtra("value", "2");
		}
		if(type.equals(Constants.HEART_DISEASE)){
			intent=new Intent(this,ChrDiseaseInfoActivity.class);
			intent.putExtra("value", "0");
		}
		startActivity(intent);
	}

}
