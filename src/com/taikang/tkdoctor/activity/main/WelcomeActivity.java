package com.taikang.tkdoctor.activity.main;

import java.util.Map;

import android.content.Intent;
import android.os.Handler;

import com.lidroid.xutils.view.annotation.ContentView;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.PreferencesService;

@ContentView(R.layout.activity_base)
public class WelcomeActivity extends BaseActivity{
	private PreferencesService service;
	private String pwd, phone;
	
	@Override
	protected void afterViews() {
		super.afterViews();
		// 延迟几秒，然后跳转到登录页面
		new Handler().postDelayed(r, 2000);
		service = new PreferencesService(this);
		Map<String, String> params = service.getPerferences();  
		phone=params.get("phone");
		pwd=params.get("pwd");
	}
	Runnable r = new Runnable() {
		@Override
		public void run() {
			//自动登录
			AutoLogin();
		}

		private void AutoLogin() {
			if (!phone.equals("")&&!pwd.equals("")) {
				startActivity(new Intent(mThis, HomeActivity.class));
				finish();
			}else {
				startActivity(new Intent(mThis, LoginActivity.class));
				finish();
			}
			
		}
	};


}
