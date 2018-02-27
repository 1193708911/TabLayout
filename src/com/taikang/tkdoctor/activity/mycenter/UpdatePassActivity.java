package com.taikang.tkdoctor.activity.mycenter;

import android.R.bool;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.UpdatePassBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
@ContentView(R.layout.activity_update_pass)
public class UpdatePassActivity extends BaseActivity implements NetCallback {
	@ViewInject(R.id.oldPass)
	private EditText oldPass;
	@ViewInject(R.id.newPass)
	private EditText newPass;
	@ViewInject(R.id.surePass)
	private EditText surePass;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		txtTitleText.setText("修改密码");
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	@OnClick(R.id.btnsure)
	private void sure(View view){
		//发送修改密码接口请求
		sendUpdatePass();

	}
	private void sendUpdatePass() {
		// TODO Auto-generated method stub
		if(checkInput()){
			UpdatePassBiz passbiz=new UpdatePassBiz();
			passbiz.setCallback(this);
			passbiz.updatePass(oldPass.getText().toString(), newPass.getText().toString());
			this.finish();
		}



	}
	public boolean checkInput(){
		if(TextUtils.isEmpty(oldPass.getText().toString())){
			ShowLongToast("请输入原密码");
			return  false;
		}else if(!PreferencesUtil.getString("USER_PWD").equals(oldPass.getText().toString())){
			ShowLongToast("原密码输入错误");
			return false;
		}
		if(TextUtils.isEmpty(newPass.getText().toString())){
			ShowLongToast("请输入新密码");
			return  false;
		}else if(newPass.getText().toString().length()<6||newPass.getText().toString().length()>20){
			ShowLongToast("密码长度为6-20位");
			return false;
		}
		if(TextUtils.isEmpty(surePass.getText().toString())){
			ShowLongToast("请输入确认新密码");
			return  false;
		}
		if(!newPass.getText().toString().equals(surePass.getText().toString())){
			ShowLongToast("新密码与确认新密码不一致");
			return false;
		}
		return true;

	}
	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		ShowLongToast("密码修改成功");
		resetEdit();

	}
	private void resetEdit() {
		// TODO Auto-generated method stub
		oldPass.setText("");
		surePass.setText("");
		newPass.setText("");
	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		ShowLongToast("密码修改失败");

	}
}
