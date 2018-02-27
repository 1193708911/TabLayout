package com.taikang.tkdoctor.activity.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.customview.MyWheelView;
import com.taikang.tkdoctor.customview.MyWheelView.OnTimeChangeClickListenner;

@ContentView(R.layout.activity_manbing_info)
public class ChrDiseaseInfoActivity extends BaseActivity implements OnTimeChangeClickListenner{
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.my_info_waist)
	private RelativeLayout mWaistLayout; //腰围的布局是否显示 默认隐藏
	@ViewInject(R.id.my_info_hip)
	private RelativeLayout mHipLayout; //臀围的布局是否显示 默认隐藏

	@ViewInject(R.id.txtmail)
	private TextView txtmail; //女
	@ViewInject(R.id.txtfemail)
	private TextView txtfemail; //男

	@ViewInject(R.id.etbirthday)
	private EditText etbirthday; //生日
	@ViewInject(R.id.etheight)
	private EditText etheight;//身高
	@ViewInject(R.id.etweight)
	private EditText etweight; //体重

	@ViewInject(R.id.etwaist)
	private EditText etwaist;//腰围
	@ViewInject(R.id.ethip)
	private EditText ethip; //臀围

	@ViewInject(R.id.rl_birth)
	private RelativeLayout RealbirS; //生日弹框

	@ViewInject(R.id.btnreset)
	private Button btnreset;
	private String mtextName;//上个页面传下来的

	private String sex;
	private String birthday;
	private String height;
	private String weight;
	private String waist;
	private String hip;

	private String Cmvalue="cm";
	private String KGvalue="kg";
	private String tnb; //是否为糖尿病标示
	private View myview;
	private AlertDialog dialog;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mtextName=bundle.getString("value");
		loadData();
		initDate();
		initView();
	}
	//弹框选择年月日
	@OnClick(R.id.rl_birth)
	private void showTime(View view){
		showMyAlertDialog();
	}
	public void showMyAlertDialog(){
		MyWheelView myWheelView=new MyWheelView(this);
		myWheelView.setOnTimeChangeClickListenner(this);
		myview=myWheelView.getDataPick();
		initWheelView();
		dialog=new AlertDialog.Builder(this).create();
		dialog.setView(myview);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	//设置相应的数据以及点击事件
	private void setDataAndListenner(TextView txtCancel, TextView txtSure) {
		txtCancel.setOnClickListener(new MyOnClickListenner());
		txtSure.setOnClickListener(new MyOnClickListenner());
	}
	private void initWheelView() {
		// TODO Auto-generated method stub
		TextView txtCancel=(TextView) myview.findViewById(R.id.txtCancel);
		TextView txtSure=(TextView) myview.findViewById(R.id.txtSure);
		setDataAndListenner(txtCancel,txtSure);
	}
	//时间发生变话的时候的回调
	@Override
	public void onTimeChangeClick(String content) {
		// TODO Auto-generated method stub
		if(content!=null){
			birthday=content;
		}

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
	//结束
	private void loadData() {
		if (mtextName.equals("0")) {
			txtTitleText.setText("缺血性心血管疾病风险评估");
		}else if(mtextName.equals("1")){
			txtTitleText.setText("糖尿病风险评估");
		}else if(mtextName.equals("2")){
			txtTitleText.setText("高血压风险评估");
		}else if(mtextName.equals("3")){
			txtTitleText.setText("代谢综合征风险评估");
		}
		tvTitleRight.setVisibility(View.GONE);
	}

	private void initView() {
		etbirthday.setText(birthday);
		etheight.setText(height);
		etweight.setText(weight);
		if (mtextName.equals(tnb)) {
			etwaist.setText(waist);
			ethip.setText(hip);
		}
	}
	private void initDate() {
		sex="0";
		tnb="1";
		birthday="1989-06-23";
		height="175";
		weight="65";
		if (mtextName.equals(tnb)) {
			mWaistLayout.setVisibility(View.VISIBLE);
			mHipLayout.setVisibility(View.VISIBLE);
			waist="85";
			hip="95";
		}
	}

	private boolean checkInput() {
		birthday= etbirthday.getText().toString().trim();
		height= etheight.getText().toString().trim();
		weight= etweight.getText().toString().trim();
		waist= etwaist.getText().toString().trim();
		hip= ethip.getText().toString().trim();
		return true;
	}

	@OnClick(R.id.btn_next)
	private void GoNext(View view){
		if (checkInput()) {
			if (mtextName.equals("0")) {
				Intent  intent =new Intent(getApplication(),PingCheQuexueResultActivity.class);
				intent.putExtra("sex", "1");
				intent.putExtra("age", "25");
				intent.putExtra("birthday", "1989-12-08");
				intent.putExtra("height", "175");
				intent.putExtra("weight", "70");
				intent.putExtra("waist", "60");
				intent.putExtra("hipline", "50");
				intent.putExtra("NAME", mtextName);
				startActivity(intent);
			}else if(mtextName.equals("1")){
				Intent  intent =new Intent(getApplication(),PingCheTangniaoResultActivity.class);
				intent.putExtra("sex", "1");
				intent.putExtra("age", "25");
				intent.putExtra("birthday", "1989-12-08");
				intent.putExtra("height", "175");
				intent.putExtra("weight", "70");
				intent.putExtra("waist", "60");
				intent.putExtra("hipline", "50");
				//根据该值得不同向数据库中添加数据
				intent.putExtra("NAME", mtextName);
				startActivity(intent);
			}else if(mtextName.equals("2")){
				Intent  intent =new Intent(getApplication(),PingCheGaoxueyaResultActivity.class);
				intent.putExtra("sex", "1");
				intent.putExtra("age", "25");
				intent.putExtra("birthday", "1989-12-08");
				intent.putExtra("height", "175");
				intent.putExtra("weight", "70");
				intent.putExtra("waist", "60");
				intent.putExtra("hipline", "50");
				intent.putExtra("NAME", mtextName);
				startActivity(intent);
			}else if(mtextName.equals("3")){
				Intent  intent =new Intent(getApplication(),PingCheDaixieResultActivity.class);
				intent.putExtra("sex", "1");
				intent.putExtra("age", "25");
				intent.putExtra("birthday", "1989-12-08");
				intent.putExtra("height", "175");
				intent.putExtra("weight", "70");
				intent.putExtra("waist", "60");
				intent.putExtra("hipline", "50");
				intent.putExtra("NAME", mtextName);
				startActivity(intent);
			}
			this.finish();
		}
	}
	//设置性别
	@OnClick(R.id.txtmail)
	private void setMail(View view){
		resetBack();
		txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_enter));
		txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_normal));
		txtmail.setTextColor(getResources().getColor(R.color.white));
		txtfemail.setTextColor(getResources().getColor(R.color.main));
		sex="0";

	}
	@OnClick(R.id.txtfemail)
	private void setfeMail(View view){
		resetBack();
		txtmail.setBackground(getResources().getDrawable(R.drawable.segment_left_normal));
		txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_enter));
		txtmail.setTextColor(getResources().getColor(R.color.main));
		txtfemail.setTextColor(getResources().getColor(R.color.white));
		sex="1";
	}

	//更换背景
	public void resetBack(){
		txtmail.setBackground(this.getResources().getDrawable(R.drawable.segment_left_normal));
		txtfemail.setBackground(getResources().getDrawable(R.drawable.segment_right_normal));
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view) {
		finish();
	}

}
