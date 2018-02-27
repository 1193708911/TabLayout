package com.taikang.tkdoctor.fragment.main;

import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.ChrDiseaseRiskActivity;
import com.taikang.tkdoctor.activity.main.HealthRiskActivity;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.activity.main.PingCheTangniaoResultActivity;
import com.taikang.tkdoctor.activity.mycenter.HealthPlansActivity;
import com.taikang.tkdoctor.activity.mycenter.TcmPhysiqueResultActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UrlManBingListBean;
import com.taikang.tkdoctor.bean.UrlManBingListBean.UrlBean;
import com.taikang.tkdoctor.biz.HealthPingguBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.global.ManBingQuestCache;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.AppManager;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;

/**
 * 健康评估问卷(结果)*/
public class HealthRiskQuesResultFragment extends BaseFragment implements NetCallback{
	
	private HashMap<String, String> params;
	
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
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initWidget();
		initData();
		loadData();
		requestNet();
	}
	
	@OnClick(R.id.imgTitleBack)
	public void back(View v) {
		getActivity().finish();
	}

	private void requestNet() {
		HealthPingguBiz healthPingguBiz = new HealthPingguBiz();
		healthPingguBiz.setCallback(this);
		healthPingguBiz.postHealthPinggu(params);
	}

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_question_itenty;
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		WebSettings webSettings = webView.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		
//		webSettings.setAppCacheEnabled(false);  
//		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		// 设置支持缩放
//		webSettings.setBuiltInZoomControls(false);
//		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//		webSettings.setSupportZoom(true);
//		webSettings.setUseWideViewPort(true);
//		webSettings.setLoadWithOverviewMode(true);
		//start
		//解决Uncaught TypeError: Cannot call method 'getItem' of null异常
//		webSettings.setDomStorageEnabled(true);     
//		webSettings.setAppCacheMaxSize(1024*1024*8);    
//		String appCachePath = getActivity().getCacheDir().getAbsolutePath();    
//		webSettings.setAppCachePath(appCachePath);  
//		webSettings.setAppCacheEnabled(true); 
		//end
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);  
				return true;
			}
		});
		
		webView.setWebChromeClient(new WebChromeClient());
		
//        webView.setOnKeyListener(new WebView.OnKeyListener() {
//			
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if(keyCode==KeyEvent.KEYCODE_BACK){
//					Log.i("webview", "back");
//					finish();
//				}
//				return false;
//			}
//		});
        
//		webView.loadUrl("file:///android_asset/hbp_questions.html");window.yjwy.invokeMethod();
        
//		webView.addJavascriptInterface(new JavascriptPinggu(), "pinggu");
//		webView.addJavascriptInterface(new JavascriptJkjh(), "plan");
//		webView.addJavascriptInterface(new JavascriptYjwy(), "yjwy");
		webView.addJavascriptInterface(new JavascriptInterface(), "jsInterface");
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
			}else if (type.equals("plan")) {//处理健康计划
				Intent intent=new Intent(getActivity(),HealthPlansActivity.class);
				startActivity(intent);
			}else if (type.equals("pinggu")) {//处理慢病风险评估
				Intent intent=new Intent(getActivity(),HealthRiskActivity.class);
				startActivity(intent);
			}
			
		}
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub
		tvTitle.setText("慢病风险评估");
	}


	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		ManBingQuestCache cache = new ManBingQuestCache();
		params=new HashMap<String, String>();
		params.put("3", cache.ans_3);
		params.put("4", cache.ans_4);
		params.put("5", cache.ans_5);
		params.put("6", cache.ans_6);
		params.put("7", cache.ans_7);
		params.put("8", cache.ans_8);
		params.put("9", cache.ans_9);
		params.put("10", cache.ans_10);
		params.put("11", cache.ans_11);
		params.put("12", cache.ans_12);
		params.put("13", cache.ans_13);
		params.put("14", cache.ans_14);
		params.put("15", cache.ans_15);
		params.put("16", cache.ans_16);
		params.put("17", cache.ans_17);
		params.put("18", cache.ans_18);
		params.put("19", cache.ans_19);
		params.put("20", cache.ans_20);
		params.put("tnb", cache.tnb);
		params.put("gxy", cache.gxy);
		params.put("gxz", cache.gxz);
		params.put("tf", cache.tf);
		params.put("sex", cache.sex);
		params.put("height", cache.height);
		params.put("weight", cache.weight);
		params.put("birthday", "1989-12-08");
		LogUtils.i("结果：  " + JSON.toJSONString(params));
	}

	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		UrlManBingListBean listBean = (UrlManBingListBean) response;
		
		if (listBean != null) {
			UrlBean urlBean = listBean.getResultlist().get(0);
			String url = ServerConfig.SERVER_PATH + urlBean.getUrl() + ServerConfig.evalNumberId_yajiankang;
			HdBsAuthUser user = MainApplication.getInstance().getUser();
			if (user != null) {
				url = url + user.getUserId();
			}
			LogUtils.d("URL: " + url);
			webView.loadUrl(url);
			PreferencesUtil.putString("jiankang", url);
		}
	}

	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		Util.showToast("提交失败");
	}
}