package com.taikang.tkdoctor.activity.main;

import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.LoginBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.NetUtil;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.ReturnCallback;
import com.taikang.tkdoctor.util.Util;

public class AutoLogin {
	
	private String mPhone = "11111111111";//逛逛角色帐号
	private String mPassword = "111111";//逛逛角色密码
	private Context mContext;
	
	public AutoLogin(Context context){
		this.mContext = context;
	}
	
	/**
	 * 逛逛角色登录
	 * @param callback
	 */
	public void playLogin(final ReturnCallback callback){
		
		if(NetUtil.checkNetAndAlert(mContext)){
			LoginBiz loginBiz = new LoginBiz();
			loginBiz.setCallback(new NetCallback() {
				@Override
				public void taskSuccess(Response response) {
					// TODO Auto-generated method stub
					PreferencesUtil.putString("uid", response.getUId());
					PreferencesUtil.putString("sessionid", response.getSessionId());
					MyUtil.saveUser(response);
					LogUtils.i("逛逛用户登录成功！");
					callback.taskSuccess();
				}
				
				@Override
				public void taskError(Response response) {
					// TODO Auto-generated method stub
					Util.showToast("登录信息过期，请重新登录");
					LogUtils.i("逛逛用户登录失败！");
					callback.taskError();
				}
			});
			loginBiz.login(mPhone, Util.stringMD5(mPassword));
			
		}else {
			callback.taskError();
		}
	}
	
	/**
	 *  正式用户登录
	 * @param callback
	 */
	public void autoLogin(final ReturnCallback callback){
		
		if(NetUtil.checkNetAndAlert(mContext)){
			LoginBiz loginBiz = new LoginBiz();
			loginBiz.setCallback(new NetCallback() {
				@Override
				public void taskSuccess(Response response) {
					// TODO Auto-generated method stub
					//保存用户信息
					PreferencesUtil.putBoolean("isLogin", true);
					PreferencesUtil.putString("uid", response.getUId());
					PreferencesUtil.putString("USER_PHONE",mPhone);
					PreferencesUtil.putString("USER_PWD",mPassword);
					PreferencesUtil.putString("sessionid", response.getSessionId());
					MyUtil.saveUser(response);
					LogUtils.i("正式用户登录成功！");
					callback.taskSuccess();
				}
				
				@Override
				public void taskError(Response response) {
					// TODO Auto-generated method stub
					Util.showToast("登录信息过期，请重新登录");
					LogUtils.i("正式用户登录失败！");
					callback.taskError();
				}
			});
			loginBiz.login(PreferencesUtil.getString("USER_PHONE"), Util.stringMD5(PreferencesUtil.getString("USER_PWD")));
			
		}else {
			callback.taskError();
		}
	}

}
