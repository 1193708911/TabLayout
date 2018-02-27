package com.taikang.tkdoctor.activity.mycenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.LoginActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.UpdateActivationBiz;
import com.taikang.tkdoctor.db.DBManager;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.AppManager;
import com.taikang.tkdoctor.util.PreferencesUtil;

@ContentView(R.layout.activity_mycenter)
public class MyCenterActivity extends BaseActivity {
	@ViewInject(R.id.usercenter_productjihuo)
	private RelativeLayout productJiHuo;
	@ViewInject(R.id.usercenter_mycollection)
	private RelativeLayout mycollection;
	@ViewInject(R.id.usercenter_healthplan)
	private RelativeLayout healthPlan;
	@ViewInject(R.id.usercenter_mypingce)
	private RelativeLayout myPingCe;
	@ViewInject(R.id.usercenter_myservice)
	private RelativeLayout myService;
	@ViewInject(R.id.txtNoJiHuo)
	private TextView txtNoJiHuo;
	@ViewInject(R.id.tv_name)
	private TextView txtName;
	@ViewInject(R.id.tv_phone)
	private TextView txtPhone;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.iv_tocenter)
	private ImageView iv_tocenter;
	private View activeView;
	private EditText et_name;
	private EditText et_activeCode;
	private static int ACTIVEINDEX=0;//默认未执行
	@ViewInject(R.id.btnExit)
	private Button btnExit;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("个人中心");
		tvTitleRight.setVisibility(View.GONE);
		setBaseInfo();

	}
	/**
	 * 设置个人基本信息
	 */
	private void setBaseInfo() {
		// TODO Auto-generated method stub
		HdBsAuthUser user=MainApplication.getInstance().getUser();
		if(user!=null){
			txtName.setText(user.getUserName()==null||user.getUserName().equals("")?"泰康人寿":user.getUserName());
			txtPhone.setText(user.getUserPhone());
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setBaseInfo();
		setState();

	}
	private void setState() {
		// TODO Auto-generated method stub
		if(PreferencesUtil.getBoolean("isActive", false)){
			txtNoJiHuo.setText("已激活");
		};
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		finish();
	}


	@OnClick(R.id.usercenter_productjihuo)
	private void goProductJiHuo(View view) {
		activeView=layoutInflater(R.layout.dialog_productactive, null);
		initView();
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setView(activeView);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("激活", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(final DialogInterface dialog, int which) {
				//发送相关请求
				if(checkInput()){
					UpdateActivationBiz activationBiz=new UpdateActivationBiz();
					activationBiz.setCallback(new NetCallback() {

						@Override
						public void taskSuccess(Response response) {
							// TODO Auto-generated method stub
							ShowLongToast("激活成功");
							txtNoJiHuo.setText("已激活");
							PreferencesUtil.putBoolean("isActive", true);
							dialog.dismiss();
						}

						@Override
						public void taskError(Response response) {
							// TODO Auto-generated method stub
							ShowLongToast("失败");
							dialog.dismiss();
						}

					});
					activationBiz.updateActivation(et_name.getText().toString(), et_activeCode.getText().toString());
				}


			}
		});
		builder.create().show();

	}
	public boolean checkInput(){
		if(TextUtils.isEmpty(et_name.getText().toString())){
			ShowLongToast("姓名不能为空");
			return false;
		}
		if(TextUtils.isEmpty(et_activeCode.getText().toString())){
			ShowLongToast("激活码不能为空");
			return false;
		}
		return true;
	}
	/**
	 * 初始化绑定相关的控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		et_name=(EditText) activeView.findViewById(R.id.et_name);
		et_activeCode=(EditText) activeView.findViewById(R.id.et_activecode);
	}
	@OnClick(R.id.usercenter_mycollection)
	private void goMyCollection(View view) {
		Intent intent=new Intent(this,MyCollectionsActivity.class);
		startActivity(intent);
	}



	@OnClick(R.id.usercenter_healthplan)
	private void goHealthPlan(View view) {
		Intent intent=new Intent(this,HealthPlansActivity.class);
		startActivity(intent);
	}
	@OnClick(R.id.usercenter_mypingce)
	private void goMyPingCe(View view) {

		Intent intent=new Intent(this,MyTestsActivity.class);
		startActivity(intent);
	}


	@OnClick(R.id.usercenter_myservice)
	private void goMyService(View view) {
		if( PreferencesUtil.getBoolean("isActive")){
			Intent intent=new Intent(this,MyServicesActivity.class);
			startActivity(intent);
		}else {
			ShowLongToast("您尚未激活，请先激活");
		}
	}

	//跳转到个人中心页面
	@OnClick(R.id.iv_tocenter)
	private void goCenter(View view){
		Intent intent=new Intent(this,CenterInfoActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.btnExit)
	private void Exit(View view) {
//		AppManager.getInstance().finishAllActivity();//结束所有Activity 同时清除缓存
		PreferencesUtil.clearPref();
		HdBsAuthUser user = MainApplication.getInstance().getUser();
		DBManager.getInstance().deleteUser(user);
		startActivity(new Intent(this,LoginActivity.class));
		finish();
	}
}
