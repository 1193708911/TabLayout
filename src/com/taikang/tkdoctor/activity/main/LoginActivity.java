package com.taikang.tkdoctor.activity.main;

import java.util.Map;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.PreferencesService;
import com.taikang.tkdoctor.bean.LoginListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.LoginBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.NetUtil;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	
	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgBack; //返回
	
	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight; //右边图标
	
	@ViewInject(R.id.txtTitleText)
	private TextView mTvText;  //标题

	@ViewInject(R.id.et_login_phone)
	private EditText mEtPhone; //手机号
	
	@ViewInject(R.id.et_login_pwd)
	private EditText mEtPassword; //密码
	
	@ViewInject(R.id.cb_login_redme)
	private CheckBox mCbRedme;  //记住信息
	
	private String mPhone;
	private String mPassword;
	private boolean IsRemeber=true;
	
	private PreferencesService service; 
	private Map<String, String> params;
	
	@Override
	protected void afterViews() {
		super.afterViews();
		mImgBack.setVisibility(View.GONE);//隐藏
		mImgRight.setVisibility(View.VISIBLE);
		mTvText.setText("登录");
		service = new PreferencesService(this);
		params = service.getPerferences();  
		mEtPhone.setText(params.get("phone"));
		mEtPassword.setText(params.get("pwd"));
		mCbRedme.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					IsRemeber = true;
				} else {
					IsRemeber = false;
				}
			}
		});
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	//登录按钮
	@OnClick(R.id.btn_login_getlogin)
	public void LoginButton(View v){
		initData();
		if (checkInput()) {
			//网络请求
			if(NetUtil.checkNetAndAlert(this)){
				LoginBiz loginBiz = new LoginBiz();
				loginBiz.setCallback(new NetCallback() {
					
					@Override
					public void taskSuccess(Response response) {
						// TODO Auto-generated method stub
						ShowShortToast("登录成功！");
						LoginListBean loginListBean=(LoginListBean) response;
						//保存用户信息
						PreferencesUtil.putBoolean("isActive", loginListBean.getResultlist().get(0).getIs_act().equals("0")?true:false);
						LogUtils.d(loginListBean.getResultlist().get(0).getIs_act());
						PreferencesUtil.putString("uid", response.getUId());
						PreferencesUtil.putString("USER_PHONE",mPhone);
						PreferencesUtil.putString("USER_PWD",mPassword);
						PreferencesUtil.putString("sessionid", response.getSessionId());
						if (IsRemeber==true) {
							service.save(mPhone, mPassword); //保存用户信息
							PreferencesUtil.putBoolean("isLogin", true);
						}
						MyUtil.saveUser(response);
						
						finish();
//						startActivity(new Intent(getApplication(),HomeActivity.class)); //测试
						
					}
					

					@Override
					public void taskError(Response response) {
						// TODO Auto-generated method stub
						if ("1".equals(response.getResultCode())) {
							startActivity( new Intent(mThis,LoginActivity.class));
							Util.showToast("操作失败！请重新登录");
						} else if ("3".equals(response.getResultCode())) {
							startActivity(new Intent(mThis,LoginActivity.class));
							Util.showToast("密码错误！请重新输入");
						}
						LogUtils.e("Login(()失败原因" + response.getResultCode());
//						startActivity(new Intent(getApplication(),HomeActivity.class)); //测试
					}
				});
				loginBiz.login(mPhone, Util.stringMD5(mPassword));
			}
		}
	}
	//去注册
	@OnClick(R.id.tv_login_GoRegist)
	public void GoRegistButton(View v){
			startActivity(new Intent(this,RegistActivity.class));
	}
	//忘记密码
	@OnClick(R.id.btn_login_forgetpsw_getcheckcode)
	public void ForgetpswButton(View v){
			startActivity(new Intent(this,ForgetPwdActivity.class));
	}
	
	private void initData() {
		mPhone=mEtPhone.getText().toString().trim();
		mPassword=mEtPassword.getText().toString().trim();
	}

	private boolean checkInput() {
		if (TextUtils.equals(mPhone, "")) {
			ShowShortToast("手机号不能为空");
			return false;
		}else if (!Util.isTelephone(mPhone)) {
			ShowShortToast("手机号码不正确");
			return false;
		}else if (TextUtils.equals(mPassword,"")) {
			ShowShortToast("密码不能为空");
			return false;
		}
		return true;
	}

}
