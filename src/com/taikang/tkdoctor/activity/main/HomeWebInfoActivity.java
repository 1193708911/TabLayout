package com.taikang.tkdoctor.activity.main;

import android.app.ProgressDialog;
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
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.util.Util;
@ContentView(R.layout.activity_home_web_info)
public class HomeWebInfoActivity extends BaseActivity {
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.imgTitleRight)
	private ImageView imgTitleRight;
	@ViewInject(R.id.MyWebView)
	private WebView MyWebView;
	private Intent intent;
	private String imgurl;
	@ViewInject(R.id.myProgressBar)
	private ProgressBar mProgressbar;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		imgTitleRight.setVisibility(View.GONE);
		intent=getIntent();
		initTitle();
	}

	private void initTitle() {
		// TODO Auto-generated method stub
		String title=intent.getStringExtra("title");
		imgurl=intent.getStringExtra("url");
		txtTitleText.setText(title);
		initWebView();
	}

	private void initWebView() {
		mProgressbar.setVisibility(View.VISIBLE);
		WebSettings webSettings = MyWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setBuiltInZoomControls(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(false);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		MyWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				mProgressbar.setVisibility(View.GONE);
			}
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
		MyWebView.loadUrl(imgurl);


	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
}
