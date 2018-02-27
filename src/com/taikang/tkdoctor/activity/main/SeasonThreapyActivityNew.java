package com.taikang.tkdoctor.activity.main;

import java.util.ArrayList;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.MealInfo;
import com.taikang.tkdoctor.bean.Meals;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.SleepInfo;
import com.taikang.tkdoctor.bean.Sleeps;
import com.taikang.tkdoctor.bean.SportInfo;
import com.taikang.tkdoctor.bean.Sports;
import com.taikang.tkdoctor.biz.HomeBiz;
import com.taikang.tkdoctor.config.SeasonalImageConstants;
import com.taikang.tkdoctor.global.SolarTermInfo;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.TimeUtil;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@ContentView(R.layout.activity_seasonalthreapy_new)
public class SeasonThreapyActivityNew extends BaseActivity{
	
	@ViewInject(R.id.txtTitleText)
	private TextView tvTitle;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.imgTitleBack)
	private ImageView ivBack;
	@ViewInject(R.id.imgTitleRight)
	private ImageView ivTitleRight;
    @ViewInject(R.id.dateText)
	private TextView tvDate;
	@ViewInject(R.id.solarText)
	private TextView tvSolarTerm;
	@ViewInject(R.id.solartermMusic)
	private ImageView imgSolarTerm;
	@ViewInject(R.id.tv_seasonaltherapy_yichi)
	private TextView tvSeasonalTherapyMealsYiChi;
	@ViewInject(R.id.tv_seasonaltherapy_shaochi)
	private TextView tvSeasonalTherapyMealsShaoChi;
	@ViewInject(R.id.tv_seasonaltherapy_jichi)
	private TextView tvSeasonalTherapyMealsJiChi;
    @ViewInject(R.id.tv_seasonaltherapy_sports)
	private TextView tvSeasonalTherapySports;
	@ViewInject(R.id.tv_seasonaltherapy_sleeps)
	private TextView tvSeasonalTherapySleeps;
	@ViewInject(R.id.topTitleLinear)
	private RelativeLayout rl_seasonaltherapy_top;
	
	private String constitution;//体质
	private String solarterm;//节气
	//节气养生本地化处理
	public static final int GET_SEASONALTHERAPY_MEALS=1;
	public static final int GET_SEASONALTHERAPY_SPORTS=2;
	public static final int GET_SEASONALTHERAPY_SLEEPS=3;
	public static String success="success";
	public static String fail="fail";
	public static String error="error";
	private Handler mHandler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			int choose=msg.what;
			String resultCode=(String) msg.obj;
			switch (choose) {
			case GET_SEASONALTHERAPY_MEALS:
				if(success.equals(resultCode)){
					getSeasonalTherapySports();
				}else if(fail.equals(resultCode)){
					
				}else if(error.equals(resultCode)){
					
				}
				break;
			case GET_SEASONALTHERAPY_SPORTS:
				if(success.equals(resultCode)){
					getSeasonalThereapySleeps();
				}else if(fail.equals(resultCode)){
					
				}else if(error.equals(resultCode)){
					
				}
				break;
			case GET_SEASONALTHERAPY_SLEEPS:
				if(success.equals(resultCode)){
					//加载成功之后返回数据
				}else if(fail.equals(resultCode)){
					
				}else if(error.equals(resultCode)){
					
				}
				break;
			default:
				break;
			}
			
			
		};
		
	};
	
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		tvTitle.setText(getResources().getString(R.string.seasonalthreapy));
		tvTitleRight.setVisibility(View.INVISIBLE);
		ivBack.setVisibility(View.VISIBLE);
		ivTitleRight.setVisibility(View.INVISIBLE);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		// 在加载数据之前 通过 节气和人来获取到对应的url 然后来加载数据
	    constitution=PreferencesUtil.getString("constitution", "平和");	
		SolarTermInfo info=new SolarTermInfo(System.currentTimeMillis());
		String solarTerms=info.getSoralTerms();
		Long solarDate=info.getSolarTermTime();
		int index=info.getIndex();
		int resid=SeasonalImageConstants.seasonalImage[index];
		rl_seasonaltherapy_top.setBackground(getResources().getDrawable(resid));
		String capitalSolarDate=TimeUtil.getTimeForCapital(solarDate);
		Log.i("solarterm", solarTerms+"...solarDate.."+capitalSolarDate);
		solarterm=solarTerms;
		tvDate.setText(capitalSolarDate);
		tvSolarTerm.setText(solarTerms);
		imgSolarTerm.setImageDrawable(getResources().getDrawable(SeasonalImageConstants.solarTermTimeMusicIds[index]));
		getSeasonalTherapyMeals();
	}
	
	public void getSeasonalTherapyMeals(){
		//节气养生接口
        HomeBiz biz=new HomeBiz();
        biz.setCallBack(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				Meals meals=(Meals) response;
				MealInfo info=meals.getResultlist().get(0).getResultlist().get(0);
				String jichi=info.getJichi();
				String shaochi=info.getShaochi();
				String yichi=info.getYichi();
				tvSeasonalTherapyMealsJiChi.setText(jichi);
				tvSeasonalTherapyMealsShaoChi.setText(shaochi);
				tvSeasonalTherapyMealsYiChi.setText(yichi);
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_MEALS;
				msg.obj=success;
				mHandler.sendMessage(msg);
			}
			
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_MEALS;
				msg.obj=error;
				mHandler.sendMessage(msg);
			}
		});
        biz.getSeasonalThreapyData("1", solarterm, constitution);
		
	}
	
	public void getSeasonalTherapySports(){
		//节气养生接口
        HomeBiz biz=new HomeBiz();
        biz.setCallBack(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				Sports sports=(Sports) response;
				ArrayList<SportInfo> infos=sports.getResultlist().get(0).getResultlist();
			    SportInfo info=infos.get(0);
			    String qiju=info.getQiju();
				String tanchu=info.getTanchu();
				tvSeasonalTherapySports.setText(tanchu);
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_SPORTS;
				msg.obj=success;
				mHandler.sendMessage(msg);
			}
			
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_SPORTS;
				msg.obj=error;
				mHandler.sendMessage(msg);
			}
		});
        biz.getSeasonalThreapyData("0", solarterm, constitution);
	}
	
	public void getSeasonalThereapySleeps(){
		//节气养生接口
        HomeBiz biz=new HomeBiz();
        biz.setCallBack(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
			    Sleeps sports=(Sleeps) response;
				SleepInfo sportinfo=sports.getResultlist().get(0).getResultlist().get(0);
				tvSeasonalTherapySleeps.setText(sportinfo.getTanchu());
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_SLEEPS;
				msg.obj=success;
				mHandler.sendMessage(msg);
			}
			
			@Override
			public void taskError(Response response) {
				Message msg=new Message();
				msg.what=GET_SEASONALTHERAPY_SLEEPS;
				msg.obj=error;
				mHandler.sendMessage(msg);
			}
		});
        biz.getSeasonalThreapyData("2", solarterm, constitution);
	}
	
	
	@OnClick(R.id.imgTitleBack)
	public void doBack(View v){
		 finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
        finish();
		return false;
	}

	 
}
