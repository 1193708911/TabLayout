package com.taikang.tkdoctor.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.mycenter.Test;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.config.ServerConfig;
@ContentView(R.layout.activity_regist_regular)
public class RegistRegularActivity extends BaseActivity {
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
	@ViewInject(R.id.progressBar1)
	private ProgressBar myProgressBar;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("我的评测结果");
		//测试跳转
		getStartIntent();
		initData();
	}
	private void getStartIntent() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,Test.class);
		startActivity(intent);

	}
	@SuppressLint("SetJavaScriptEnabled")
	private void initData() {
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
		webView.loadUrl(ServerConfig.SERVER_PATH+ServerConfig.HTML_XIEYI);
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
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}


}
