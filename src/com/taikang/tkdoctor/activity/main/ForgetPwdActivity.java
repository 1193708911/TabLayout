package com.taikang.tkdoctor.activity.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.requestback.LoginGetCodeBean;
import com.taikang.tkdoctor.base.requestback.LoginGetCodeListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.LoginBiz;
import com.taikang.tkdoctor.biz.RegisterBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Util;

@ContentView(R.layout.activity_forgetpassword)
public class ForgetPwdActivity extends BaseActivity implements NetCallback {
	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgBack; //返回

	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight; //右边图标

	@ViewInject(R.id.txtTitleText)
	private TextView mTvText;  //标题

	@ViewInject(R.id.et_forgetpwd_phone)
	private EditText mEtPhone; //手机号

	@ViewInject(R.id.et_forgetpwd_checkCod)
	private EditText mEtCheckCode; //验证码

	@ViewInject(R.id.et_forgetpwd_pwd)
	private EditText mEtPassword; //当前密码

	@ViewInject(R.id.et_forgetpwd_pwd_sure)
	private EditText mEtPasswordSure; //当前密码

	@ViewInject(R.id.btn_frogetpwd_go)
	private Button mBtnFrogetpwd; //注册

	@ViewInject(R.id.btn_forgetpwd_getcheckcode)
	private Button mBtnforgetcheckcode; //获取验证码

	private String mPhone;
	private String mPassword;
	private String mCheckCode;
	private String mPasswordSure;
	private MyCount mc;//短信验证码计时器
	private int NET_REQUEST_TASK=0;//用于判断是获取验证码还是确认请求方法 

	@Override
	protected void afterViews() {
		super.afterViews();
		mImgRight.setVisibility(View.GONE);//隐藏右侧按钮
		mTvText.setText("忘记密码");
	}

	//确认按钮
	@OnClick(R.id.btn_frogetpwd_go)
	public void ForgetPwdGoButton(View v){
		NET_REQUEST_TASK = 2;
		if (checkInput()) {
			//网络请求
			LoginBiz loginBiz = new LoginBiz();
			loginBiz.setCallback(this);
			loginBiz.getPassreset(mPhone,Util.stringMD5(mPassword));
		}

	}
	//获取验证码
	@OnClick(R.id.btn_forgetpwd_getcheckcode)
	public void ForgetresultCodeButton(View v){
		mPhone = mEtPhone.getText().toString().trim();
		if (TextUtils.equals(mPhone, "")) {
			ShowShortToast("手机号未输入！");
			return;
		}
		if (!Util.isMobile(mPhone)) {
			ShowShortToast("手机号输入错误！");
			return;
		}else {
			NET_REQUEST_TASK = 1;
			//网络请求
			RegisterBiz getCodeBiz = new RegisterBiz();
			getCodeBiz.setCallback(this);
			getCodeBiz.getVerificationCode(mPhone);
			//获取短信验证码
			if (mc != null) {
				mc.cancel();
			}
			mBtnforgetcheckcode.setText("60秒后重新获取");
			mc = new MyCount(60 * 1000,
					1000);// 计时60秒
			mc.start();
			mBtnforgetcheckcode.setClickable(false);
		}
	}
	//註冊按钮
	@OnClick(R.id.imgTitleBack)
	public void ImgBack(View v){
		startActivity(new Intent(this,LoginActivity.class));//测试数据
		finish();
	}
	private boolean checkInput() {
		mPhone = mEtPhone.getText().toString().trim();
		mCheckCode= mEtCheckCode.getText().toString().trim();
		mPassword= mEtPassword.getText().toString().trim();
		mPasswordSure= mEtPasswordSure.getText().toString().trim();
		if (TextUtils.equals(mPhone, "")) {
			ShowShortToast("手机号未输入！");
			return false;
		}else if (!Util.isTelephone(mPhone)) {
			ShowShortToast("手机号输入错误！");
			return false;
		}else if (TextUtils.equals(mCheckCode, "")) {
			ShowShortToast("验证码不能为空");
			return false;
		}else if (mCheckCode.length()!=6) {
			ShowShortToast("验证码的长度为6位");
			return false;
		}else if (TextUtils.equals(mPassword, "")) {
			ShowShortToast("密码不能为空");
			return false;
		}else if (mPassword.length()>20||mPassword.length()<6) {
			ShowShortToast("密码长度在6-20位之间");
			return false;
		}else if (TextUtils.equals(mPasswordSure, "")) {
			ShowShortToast("确认密码不能为空");
			return false;
		}else if (!mPassword.equals(mPasswordSure)) {
			ShowShortToast("密码不一致，请重新输入");
			return false;
		}
		return true;
	}

	//计时器内部类
	class MyCount extends CountDownTimer{

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			mBtnforgetcheckcode.setText("获取验证码");
			mBtnforgetcheckcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			Date date = new Date(millisUntilFinished);
			SimpleDateFormat sdf = new SimpleDateFormat("ss");
			mBtnforgetcheckcode.setText((Integer.parseInt(sdf.format(date)))+"秒后重新获取");//动态显示剩余时间
		}
	}

	@Override
	public void taskSuccess(Response response) {
		if (NET_REQUEST_TASK == 1) {
			LoginGetCodeListBean getCodeBeanlist =(LoginGetCodeListBean) response;
			LoginGetCodeBean codeBean =getCodeBeanlist.getResultList().get(0);
			mCheckCode =codeBean.getIdentity();
			//			Log.i("验证码", mCheckCode);
			mEtCheckCode.setText(mCheckCode);
		}
		if (NET_REQUEST_TASK == 2) {
			startActivity(new Intent(this,LoginActivity.class));
			Util.showToast("密码重置成功！");
		}

	}

	@Override
	public void taskError(Response response) {
		if (NET_REQUEST_TASK == 1) {
			ShowShortToast("验证码获取失败");
			mBtnforgetcheckcode.setClickable(true);
			mBtnforgetcheckcode.setText("获取短信验证码");
			mc.cancel();
		}
		if (NET_REQUEST_TASK == 2) {
			startActivity(new Intent(this,LoginActivity.class));
			Util.showToast("密码重置失败！");
		}

	}
}
