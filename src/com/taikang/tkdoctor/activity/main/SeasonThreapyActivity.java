package com.taikang.tkdoctor.activity.main;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.HomeBiz;
import com.taikang.tkdoctor.config.SeasonalImageConstants;
import com.taikang.tkdoctor.global.SolarTermInfo;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.TimeUtil;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.activity_seasonalthreapy)
public class SeasonThreapyActivity extends BaseActivity{
	
	private static final int SEASONAL_THREAPY=1;
	@ViewInject(R.id.txtTitleText)
	private TextView tvTitle;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.imgTitleBack)
	private ImageView ivBack;
	@ViewInject(R.id.imgTitleRight)
	private ImageView ivTitleRight;
	@ViewInject(R.id.webview)
	private WebView webView;

	//节气养生本地化处理
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		tvTitle.setText(getResources().getString(R.string.seasonalthreapy));
		tvTitleRight.setVisibility(View.INVISIBLE);
		ivBack.setVisibility(View.VISIBLE);
		ivTitleRight.setVisibility(View.INVISIBLE);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		SolarTermInfo info=new SolarTermInfo(System.currentTimeMillis());
		String solarTerms=info.getSoralTerms();
		Long solarDate=info.getSolarTermTime();
		String capitalSolarDate=TimeUtil.getTimeForCapital(solarDate);
		Log.i("solarterm", solarTerms+"...solarDate.."+capitalSolarDate);
		//节气养生接口
        HomeBiz biz=new HomeBiz();
        biz.setCallBack(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				
			}
		});
//       biz.getSeasonalThreapyData(MainApplication.getInstance().getUser().getUserPhone());
       
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.loadUrl("http://www.sina.com/");
		webView.loadUrl("file:///android_asset/health_guide.html");
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);  
				return true;
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
	public void doBack(View v){
		 finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
			finish();
			return true;
		}
		return false;
	}

	 
}
