package com.taikang.tkdoctor.activity.main;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.ReportInfoBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UrlManBingListBean;
import com.taikang.tkdoctor.bean.UrlManBingListBean.UrlBean;
import com.taikang.tkdoctor.biz.ManBingXinXueGuanBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.db.ReportDBDaoImp;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;
/**
 * 铁血性心血管疾病风险评估
 * @author Administrator
 *
 */
@ContentView(R.layout.activity_ping_che_result)
public class PingCheQuexueResultActivity extends BaseActivity implements NetCallback{
	
	private String age;
	private String sex;
	private String birthday;
	private String height;
	private String weight;
	private String waist;
	private String hipline;
	private String NAME;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.txtTitleText)
	private TextView tvTitle;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.imgTitleBack)
	private ImageView ivBack;
	@ViewInject(R.id.imgTitleRight)
	private ImageView ivTitleRight;
	@ViewInject(R.id.webView1)
	private WebView webView;
	@ViewInject(R.id.myProgressBar)
	private ProgressBar myProgressBar;
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		
		initData();
		loadData();
	}
	
	private void initData() {
		txtTitleText.setText("缺血性心血管疾病风险评估");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		sex = bundle.getString("sex");
		age = bundle.getString("age");
		birthday = bundle.getString("birthday");
		height = bundle.getString("height");
		weight = bundle.getString("weight");
		waist = bundle.getString("waist");
		hipline = bundle.getString("hipline");
		NAME=bundle.getString("NAME");
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void loadData() {
		myProgressBar.setVisibility(View.VISIBLE);
		WebSettings webSettings = webView.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(false);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
        webView.setOnKeyListener(new WebView.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_BACK){
					Log.i("webview", "back");
					finish();
				}
				return false;
			}
		});
        
		webView.loadUrl("file:///android_asset/icd_questions.html");
        
		webView.addJavascriptInterface(new JavascriptInterface(), "jsInterface");
		
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
	
	class JavascriptInterface {

		@SuppressWarnings("unused")
		@android.webkit.JavascriptInterface /** 解决Android 17（包括17）之后js无法调用Android方法*/
		
		public void invokeMethod(String [] a, String [] b) {
			String ss = "||";
			String s1 = "";
			for (int i = 0; i < a.length; i++) {
				s1 = s1 + a[i] + "@";
			}
			s1 = s1.substring(0, s1.length()-1);
			for (int i = 0; i < b.length; i++) {
				ss = ss + b[i] + "*";
			}
			
			Log.e("缺血性心血管", s1 + "||" + ss);
			
			HashMap<String, String> params=new HashMap<String, String>();
			params.put("1", s1);
//			params.put("2", "1");
			params.put("3", b[0]);
			params.put("4", b[1]);
			params.put("5", b[2]);
			params.put("6", b[3]);
//			params.put("7", "70");
//			params.put("8", "7.0");
//			params.put("9", "11.1");
//			params.put("10", "6.0");
			
			postData(params);
		}
		
	}
	
	private void postData(HashMap<String, String> parmMap) {
		// TODO Auto-generated method stub
		ManBingXinXueGuanBiz xinXueGuanBiz = new ManBingXinXueGuanBiz();
		xinXueGuanBiz.setCallback(this);
		xinXueGuanBiz.getXinXueGuan(sex, height, age, birthday, parmMap);
	}

	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		UrlManBingListBean listBean = (UrlManBingListBean) response;
		
		if (listBean != null) {
			UrlBean urlBean = listBean.getResultlist().get(0);
			String url = ServerConfig.SERVER_PATH + urlBean.getUrl() + ServerConfig.evalNumberId_Xinxueguan;
			HdBsAuthUser user = MainApplication.getInstance().getUser();
			if (user != null) {
				url = url + user.getUserId();
			}
			LogUtils.d("URL: " + url);
			myProgressBar.setVisibility(View.VISIBLE);
			webView.loadUrl(url);
			inserSQL(url,user);
		}
	}
	//向数据库中添加数据
		private void inserSQL(String url, HdBsAuthUser user) {
			// TODO Auto-generated method stub
//			ReportDBDaoImp reportImp=new ReportDBDaoImp();
//			ReportInfoBean infoBean=new ReportInfoBean();
//			infoBean.setReport_type(Constants.HEART_DISEASE);
//			infoBean.setReport_url(url);
//			reportImp.insert(infoBean);
			PreferencesUtil.putString("quexue", url);

		}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		Util.showToast("提交失败");
	}
}