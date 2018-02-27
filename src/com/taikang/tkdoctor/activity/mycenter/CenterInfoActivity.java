package com.taikang.tkdoctor.activity.mycenter;

import kankan.wheel.widget.WheelView;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;
import com.taikang.tkdoctor.bean.UserBaseInfoBean.UserBaseInfo;
import com.taikang.tkdoctor.biz.GetUserInfoBiz;
import com.taikang.tkdoctor.biz.UpdateUserInfoBiz;
import com.taikang.tkdoctor.customview.MyWheelView;
import com.taikang.tkdoctor.customview.MyWheelView.OnTimeChangeClickListenner;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;
@ContentView(R.layout.activity_center_info)
public class CenterInfoActivity extends BaseActivity implements NetCallback, OnTimeChangeClickListenner {
	@ViewInject(R.id.txtmail)
	private TextView txtmail;
	@ViewInject(R.id.txtfemail)
	private TextView txtfemail;
	@ViewInject(R.id.btnreset)
	private Button btnreset;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView txtEditSave;
	@ViewInject(R.id.etname)
	private EditText etname;
	@ViewInject(R.id.etbirthday)
	private EditText etbirthday;
	@ViewInject(R.id.etheight)
	private EditText etheight;
	@ViewInject(R.id.etweight)
	private EditText etweight;
	@ViewInject(R.id.etPhone)
	private EditText etPhone;
	//默认为男
	private int SEXNUMBER=1;
	private boolean isEdit=true;
	private int CURRENTINDEX=0;
	private WheelView wheelYear;
	private WheelView wheelDay;
	private WheelView wheelMonth;
	private View view;
	private String birthday;
	private AlertDialog dialog;
	/** 输入框小数的位数*/
	private static final int DECIMAL_DIGITS = 5;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("个人信息");
		txtEditSave.setVisibility(View.VISIBLE);
		txtEditSave.setText("编辑");
		etname.setFilters(new InputFilter[] { lengthfilter });
		HdBsAuthUser user=MainApplication.getInstance().getUser();
		if(user!=null){
			etPhone.setText(user.getUserPhone());
		}
		sendGetBaseInfoRequest();
		resetEditStateEnableUnEnable();



	}
	//获取基本信息
	private void sendGetBaseInfoRequest() {
		CURRENTINDEX=0;
		GetUserInfoBiz getUserInfoBiz=new GetUserInfoBiz();
		getUserInfoBiz.setCallback(this);
		getUserInfoBiz.getUserBaseInfo();


	}
	/**
	 * 发送请求获取基本信息
	 */
	private void sendBaseInfoRequests() {
		// TODO Auto-generated method stub
		LogUtils.d(checkInput()+"");
		if(checkInput()){
			UpdateUserInfoBiz userInfoBiz=new UpdateUserInfoBiz();
			userInfoBiz.setCallback(this);
			userInfoBiz.updateUserInfo( etname.getText().toString(), SEXNUMBER+"", etbirthday.getText().toString(), etheight.getText().toString(), etweight.getText().toString());
		}

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
	//编辑与保存
	@OnClick(R.id.tvTitleRight)
	private  void editOrSave(View view){
		if(isEdit){
			resetEditStateEnable();
			isEdit=false;
			txtEditSave.setText("保存");

		}else {
			CURRENTINDEX=1;
			resetEditStateEnableUnEnable();
			isEdit=true;
			txtEditSave.setText("编辑");
			sendBaseInfoRequests();

		}

	}
	//设置性别
	@OnClick(R.id.txtmail)
	private void setMail(View view){
		if(!isEdit){
			resetBack();
			txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
			txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_normal));
			txtmail.setTextColor(getResources().getColor(R.color.white));
			txtfemail.setTextColor(getResources().getColor(R.color.centergray));
			SEXNUMBER=1;
		}else {
		}


	}
	//判断是否为空
	public boolean checkInput(){
		//		LogUtils.d(SEXNUMBER+etPhone.getText().toString()+etname.getText().toString()+etbirthday.getText().toString()+etheight.getText().toString()+etweight.getText().toString());
		if(TextUtils.isEmpty(etname.getText().toString())){
			ShowLongToast("用户名不能为空");
			return false;
		}
		if(TextUtils.isEmpty(etbirthday.getText().toString())){
			ShowLongToast("生日不能为空");
			return false;
		}
		if(TextUtils.isEmpty(etheight.getText().toString())) {
			ShowLongToast("身高不能为空");
			return false;

		}
		if(TextUtils.isEmpty(etweight.getText().toString())){
			ShowLongToast("体重不能为空");
			return false;
		}
		if(SEXNUMBER!=0 && SEXNUMBER!=1){
			LogUtils.d((SEXNUMBER!=0 || SEXNUMBER!=1)+"jjjjjj");
			return false;
		}

		return true;
	}
	@OnClick(R.id.txtfemail)
	private void setfeMail(View view){
		if(!isEdit){
			resetBack();
			txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_normal));
			txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_enter));
			txtmail.setTextColor(getResources().getColor(R.color.main));
			txtfemail.setTextColor(getResources().getColor(R.color.white));
			SEXNUMBER=0;
		}
	}
	//更换背景
	public void resetBack(){
		txtmail.setBackground(this.getResources().getDrawable(R.drawable.segment_left_normal));
		txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_normal));
	}
	//设置为不可以编辑
	public void resetEditStateEnableUnEnable(){
		resetEditState(etheight, false);
		resetEditState(etname, false);
		resetEditState(etPhone, false);
		resetEditState(etweight, false);
		resetEditState(etbirthday, false);

	}
	//设置为可以编辑
	public void resetEditStateEnable(){
		resetEditState(etheight, true);
		resetEditState(etname, true);
		resetEditState(etweight, true);
	}
	//同意设置不可以更改状态
	public void resetEditState(EditText et,boolean flag){
		et.setEnabled(flag);
	}
	//重置密码
	@OnClick(R.id.btnreset)
	private void resetPass(View view){
		Intent intent=new Intent(this,UpdatePassActivity.class);
		startActivity(intent);

	}
	//弹框选择年月日
	public void showAlertDialog(){
		if(!isEdit){
			MyWheelView myWheelView=new MyWheelView(this);
			myWheelView.setOnTimeChangeClickListenner(this);
			view=myWheelView.getDataPick();
			initView();
			dialog=new AlertDialog.Builder(this).create();
			dialog.setView(view);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}
	@OnClick(R.id.rl_birth)
	private void setBirthday(View view){
		showAlertDialog();
	}
	private void initView() {
		// TODO Auto-generated method stub
		TextView txtCancel=(TextView) view.findViewById(R.id.txtCancel);
		TextView txtSure=(TextView) view.findViewById(R.id.txtSure);
		setDataAndListenner(txtCancel,txtSure);



	}
	//设置相应的数据以及点击事件
	private void setDataAndListenner(TextView txtCancel, TextView txtSure) {
		txtCancel.setOnClickListener(new MyOnClickListenner());
		txtSure.setOnClickListener(new MyOnClickListenner());



	}

	private final class MyOnClickListenner implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.txtSure:
				etbirthday.setText(birthday);
				dialog.dismiss();
				break;
			case R.id.txtCancel:
				dialog.dismiss();
				break;

			}

		}

	}
	@Override
	public void taskSuccess(Response response) {
		if(response!=null){
			//			loadUserInfoBiz();
			if(CURRENTINDEX==1){
				ShowLongToast("保存成功");
				sendGetBaseInfoRequest();
			}else if(CURRENTINDEX==0){
				//此时为基本信息
				MyUtil.saveUser(response);
				setBaseInfo(response);
			}

		}

	}

	private void setBaseInfo(Response response) {
		UserBaseInfoBean userBean=(UserBaseInfoBean) response;
		MyUtil.saveUser(response);
		if(userBean!=null){
			UserBaseInfo info=  userBean.getResultlist().get(0);
			etbirthday.setText(info.getBirthday()==null?"1989年06月23日":info.getBirthday());
			etheight.setText(info.getHeight()==null?"175":info.getHeight());
			etweight.setText(info.getWeight()==null?"55":info.getWeight());
			etname.setText(info.getName()==null?"泰康人寿":info.getName());
			
			if("1".equals(info.getSex())&&!info.getSex().equals("")){
				resetBack();
				txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
				txtmail.setTextColor(getResources().getColor(R.color.white));
				txtfemail.setTextColor(getResources().getColor(R.color.centergray));
			}else if(("0").equals(info.getSex())&&!info.getSex().equals("")){
				resetBack();
				txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_enter));
				txtmail.setTextColor(getResources().getColor(R.color.centergray));
				txtfemail.setTextColor(getResources().getColor(R.color.white));
			}else {
				resetBack();
				txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
				txtmail.setTextColor(getResources().getColor(R.color.white));
				txtfemail.setTextColor(getResources().getColor(R.color.centergray));
			}
		}



	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		if(CURRENTINDEX==1){
			setErroData();
		}
	}
	
	//設置还原用户信息
	private void setErroData() {
		ShowLongToast("保存失败");
		HdBsAuthUser user=MainApplication.getInstance().getUser();
		if(user!=null){
			etbirthday.setText(user.getBirthday()==null?"1989年06月23日":user.getBirthday());
			etheight.setText(user.getHeight()==null?"175":user.getHeight());
			etweight.setText(user.getWeight()==null?"55":user.getWeight());
			etname.setText(user.getUserName()==null?"泰康人寿":user.getUserName());
			
			if("1".equals(user.getSex())&&!user.getSex().equals("")){
				resetBack();
				txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
				txtmail.setTextColor(getResources().getColor(R.color.white));
				txtfemail.setTextColor(getResources().getColor(R.color.centergray));
			}else if(("0").equals(user.getSex())&&!user.getSex().equals("")){
				resetBack();
				txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_enter));
				txtmail.setTextColor(getResources().getColor(R.color.centergray));
				txtfemail.setTextColor(getResources().getColor(R.color.white));
			}else {
				resetBack();
				txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
				txtmail.setTextColor(getResources().getColor(R.color.white));
				txtfemail.setTextColor(getResources().getColor(R.color.centergray));
			}
		}

	}
	
	//时间发生变话的时候的回调
	@Override
	public void onTimeChangeClick(String content) {
		// TODO Auto-generated method stub
		if(content!=null){
			birthday=content;
		}

	}
	InputFilter lengthfilter = new InputFilter() {
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			// 删除等特殊字符，直接返回   
			if ("".equals(source.toString())) {
				return null;
			}
			String dValue = dest.toString();
			String[] splitArray = dValue.split("\\.");
			if (splitArray.length > 1) {
				String dotValue = splitArray[1];
				int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
				if (diff > 0) {
					return source.subSequence(start, end - diff);
				}
			}
			return null;
		}
	};

}
