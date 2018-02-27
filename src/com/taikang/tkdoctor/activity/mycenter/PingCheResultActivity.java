package com.taikang.tkdoctor.activity.mycenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.requestcallback.XutilRequestCallBack;
/**
 * 根据不同索引判断是什么评测
 * @author Administrator
 *
 */
@ContentView(R.layout.activity_ping_che_result)
public class PingCheResultActivity extends BaseActivity {
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
		webView.loadUrl("file:///android_asset/diabetes_questions.html");
		webView.addJavascriptInterface(new JavascriptInterface(), "jsInterface");
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
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	class JavascriptInterface {

		@SuppressWarnings("unused")
		@android.webkit.JavascriptInterface /** 解决Android 17（包括17）之后js无法调用Android方法*/

		public void invokeMethod(String [] a, String [] b, String [] c) {
			String ss = "||";
			for (int i = 0; i < a.length; i++) {
				ss = ss + a[i];
			}
			ss = ss + "||";
			for (int i = 0; i < b.length; i++) {
				ss =  ss + b[i];
			}
			ss = ss + "||";
			for (int i = 0; i < c.length; i++) {
				ss = ss + c[i] + "*";
			}
			Toast.makeText(getApplicationContext(), ss, Toast.LENGTH_LONG).show();
			Log.e("ssss", ss);
		}

	}
}
