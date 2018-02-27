package com.taikang.tkdoctor.activity.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.taikang.tkdoctor.biz.RegisterBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;

@ContentView(R.layout.activity_regist)
public class RegistActivity extends BaseActivity implements NetCallback{

	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgBack; //返回

	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight; //右边图标

	@ViewInject(R.id.txtTitleText)
	private TextView mTvText;  //标题

	@ViewInject(R.id.et_regist_phone)
	private EditText mEtPhone; //手机号

	@ViewInject(R.id.et_regist_pwd)
	private EditText mEtPassword; //密码

	@ViewInject(R.id.btn_regist_getRegist)
	private Button mBtnRegist; //注册

	@ViewInject(R.id.btn_regist_forgetresult_getcheckcode)
	private Button mBtnforgetresult; //获取验证码

	@ViewInject(R.id.et_regist_checkCod)
	private EditText mEtCheckCode; //验证码

	@ViewInject(R.id.et_regist_requestCode)
	private EditText mEtRequestCode; //邀请码

	@ViewInject(R.id.cb_regist)
	private CheckBox mCbRegistGuize;  //同意条款

	private String mPhone;
	private String mPassword;
	private String mCheckCode;
	private String mRequestCode;
	private MyCount mc;//短信验证码计时器
	private int NET_REQUEST_TASK=0;//用于判断是获取验证码还是注册请求方法 
	private String IsChexBox;//是否同意条款的标示

	@Override
	protected void afterViews() {
		super.afterViews();
		mImgBack.setVisibility(View.GONE);//隐藏
		mImgRight.setVisibility(View.VISIBLE);
		mTvText.setText("注册");
	}

	//获取验证码
	@OnClick(R.id.btn_regist_forgetresult_getcheckcode)
	public void ForgetresultCodeButton(View v){
		mPhone = mEtPhone.getText().toString().trim();
		if (TextUtils.equals(mPhone, "")) {
			ShowShortToast("手机号未输入！");
			return;
		} 
		if (!Util.isMobile(mPhone)) {
			ShowShortToast("手机号输入错误！");
			return;
		}
		NET_REQUEST_TASK = 1;
		RegisterBiz getCodeBiz = new RegisterBiz();
		getCodeBiz.setCallback(this);
		getCodeBiz.getVerificationCode(mPhone);
		if (mc != null) {
			mc.cancel();
		}
		mBtnforgetresult.setText("60秒后重新获取");
		mc = new MyCount(60 * 1000,
				1000);// 计时60秒
		mc.start();
		mBtnforgetresult.setClickable(false);

	}
	@OnClick(R.id.txtXieYi)
	private void txtXieYi(View view){
		Intent intent=new Intent(this,RegistRegularActivity.class);
		startActivity(intent);
	}
	//註冊按钮
	@OnClick(R.id.btn_regist_getRegist)
	public void RegistButton(View v){
		NET_REQUEST_TASK = 2;
		if (checkInput()) {
			//注册网络请求
			RegisterBiz registNet = new RegisterBiz();
			registNet.setCallback(this);
			registNet.getRegist(mPhone,Util.stringMD5(mPassword),mRequestCode);
		}
	}
	@OnClick(R.id.cb_regist)
	public void IsCheckBoxButton(View v){
		IsChexBox="1";
	}

	//计时器内部类
	class MyCount extends CountDownTimer{

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			mBtnforgetresult.setText("获取验证码");
			mBtnforgetresult.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			Date date = new Date(millisUntilFinished);
			SimpleDateFormat sdf = new SimpleDateFormat("ss");
			mBtnforgetresult.setText((Integer.parseInt(sdf.format(date)))+"秒后重新获取");//动态显示剩余时间
		}
	}

	private boolean checkInput() {
		mPhone = mEtPhone.getText().toString().trim();
		mCheckCode= mEtCheckCode.getText().toString().trim();
		mPassword= mEtPassword.getText().toString().trim();
		mRequestCode= mEtRequestCode.getText().toString().trim();
		if (TextUtils.equals(mPhone, "")) {
			ShowShortToast("手机号未输入！");
			return false;
		}else if (!Util.isMobile(mPhone)) {
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
		}else if (TextUtils.equals(mRequestCode, "")) {
			ShowShortToast("邀请码不能为空");
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mc != null) {
			mc.cancel();
		}
	}

	@Override
	public void taskSuccess(Response response) {
		if (NET_REQUEST_TASK == 1) {
			LoginGetCodeListBean getCodeBeanlist =(LoginGetCodeListBean) response;
			LoginGetCodeBean codeBean =getCodeBeanlist.getResultList().get(0);
			mCheckCode =codeBean.getIdentity();
			mEtCheckCode.setText(mCheckCode);
		}
		if (NET_REQUEST_TASK == 2) {
			//保存用户信息
			PreferencesUtil.putString("USER_UID", response.getUId());
			PreferencesUtil.putString("USER_PHONE",mPhone);
			PreferencesUtil.putString("USER_PWD",mPassword);
			startActivity(new Intent(getApplication(),HomeActivity.class)); //测试
		}
		MyUtil.saveUser(response);
	}
	@Override
	public void taskError(Response response) {
		if (NET_REQUEST_TASK == 1) {
			ShowShortToast("验证码获取失败");
			mBtnforgetresult.setText("获取验证码");
			mc.cancel();
			mBtnforgetresult.setClickable(true);

		}
		if (NET_REQUEST_TASK == 2) {
			Util.showToast("注册失败，请稍后再试！");
		}

	}

}
