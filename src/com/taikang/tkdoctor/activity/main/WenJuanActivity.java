package com.taikang.tkdoctor.activity.main;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
@ContentView(R.layout.activity_wen_juan)
public class WenJuanActivity extends BaseActivity {
	@ViewInject(R.id.webView)
	private WebView webView;
	@ViewInject(R.id.progressbar)
	private ProgressBar progressbar;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("慢病评估问卷");
		initWebView();

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {

		WebSettings webSettings = webView.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		progressbar.setVisibility(View.VISIBLE);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				progressbar.setVisibility(view.GONE);
			}
		});
		webView.setWebChromeClient(new WebChromeClient());
		//		mWebView.loadUrl(bean.getBannerUrl());
		webView.getSettings().setJavaScriptEnabled(true);  
		//	webView.addJavascriptInterface(object, name);
		webView.loadUrl("file:///android_asset/msd_questions.html");
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
}
