package com.taikang.tkdoctor.fragment.askdoctor;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.mycenter.MyCenterActivity;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.util.PreferencesUtil;

public class AskDoctorFragment extends BaseFragment{
	
	@ViewInject(R.id.txtTitleText)
	private TextView mTvTitle;//标题
	@ViewInject(R.id.tv_askdoctor_wenyi)
	private TextView mTvJihuo;//
	@ViewInject(R.id.btnCall)
	private Button btnCall;
	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight;// 返回按钮
	@ViewInject(R.id.imgTitleBack)
	private ImageView imgTitleBack;// 返回按钮
	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_askdoctor;
	}
	
	@Override
	protected void afterViews() {
		super.afterViews();
		mImgRight.setVisibility(View.GONE);
		imgTitleBack.setVisibility(View.GONE);
		mTvTitle.setText(getActivity().getResources().getString(R.string.tab_askdoctor));
		initData();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if (PreferencesUtil.getBoolean("isActive")) {
			mTvJihuo.setVisibility(View.INVISIBLE);
			btnCall.setBackgroundColor(getActivity().getResources().getColor(R.color.main));
		}else {
			btnCall.setClickable(false);
		}
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	@OnClick(R.id.btnCall)
	private void call(View view){
		Intent  intent=new Intent();
		//激活源代码,添加intent对象
		intent.setAction("android.intent.action.CALL");
		//intent.addCategory("android.intent.category.DEFAULT");内部会自动添加类别，
		intent.setData(Uri.parse("tel:"+"4009105522"));
		//激活Intent
		startActivity(intent);
	}
	@OnClick(R.id.imgTitleRight)
	private void ToCenterAc(View view){
//		Intent intent=new Intent(this,MyCenterActivity.class);
//		startActivity(intent);

	}

}
