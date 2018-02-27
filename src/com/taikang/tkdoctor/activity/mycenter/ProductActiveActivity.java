package com.taikang.tkdoctor.activity.mycenter;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

@ContentView(R.layout.activity_productactive)
public class ProductActiveActivity extends BaseActivity {

	@ViewInject(R.id.imgTitleBack)
	private ImageView ivBack;
	
	@ViewInject(R.id.imgTitleRight)
	private ImageView ivRight;
	
	@ViewInject(R.id.tvTitleRight)
	private TextView tvRight;
	
	@ViewInject(R.id.txtTitleText)
	private TextView tvTitle;
	
	@ViewInject(R.id.tv_active)
	private TextView tvActive;

	@ViewInject(R.id.et_name)
	private EditText etName;
	
	@ViewInject(R.id.et_activecode)
	private EditText etActiveCode;
 	
	@Override
	protected void afterViews() {
		super.afterViews();
		ivRight.setVisibility(View.GONE);
		tvRight.setVisibility(View.GONE);
		tvTitle.setText(getResources().getString(R.string.active));
	}
	
	@OnClick(R.id.imgTitleBack)
	public void onclick(View v){
	    finish();	
	}
	
	@OnClick(R.id.tv_active)
	public void onActive(View v){
	    //激活操作	
		//点击激活之后弹出一自定义Dialog
		//然后又进度条
	}
	
	
}
