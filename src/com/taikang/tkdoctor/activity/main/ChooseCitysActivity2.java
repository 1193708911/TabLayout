package com.taikang.tkdoctor.activity.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.Pm25Bean;
import com.taikang.tkdoctor.bean.Pm25Bean.Pm25;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.HomeBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *PM25的显示处理
 */
@ContentView(R.layout.activity_airqualitytest)
public class ChooseCitysActivity2 extends BaseActivity {

	@ViewInject(R.id.imgTitleBack)
	private ImageView ivBack;
	@ViewInject(R.id.imgTitleRight)
	private ImageView ivChooseCity;
	@ViewInject(R.id.txtTitleText)
	private TextView  tvTitle;
	@ViewInject(R.id.tvTitleRight)
	private TextView  tvRight;
	@ViewInject(R.id.tv_current_city)
	private TextView  tvCurrentCity;
	@ViewInject(R.id.tv_last_update_time)
	private TextView tvLastUpdateTime;
	@ViewInject(R.id.tv_airquality)
	private TextView tvAirQuality;
	@ViewInject(R.id.tv_pm25)
    private TextView tvPM25;
	@ViewInject(R.id.tv_aqi)
	private TextView tvAQI;
	@ViewInject(R.id.tv_aircondition)
	private TextView tvAirConditon;
	@ViewInject(R.id.tv_measurstake)
	private TextView tvMeasureTake;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	
	private String locatedCityPinyin;//定位城市拼音
	
	private String locatedCityHan;//定位城市汉字
	
	private String quality;
	
	private String pm25;
	
	
	@Override
	protected void afterViews() {
		super.afterViews();
		txtTitleText.setText("空气质量检测");
		locatedCityPinyin=getIntent().getStringExtra("citynamePinyin");
		locatedCityHan=getIntent().getStringExtra("citynameHan");
		//此处应有一个加载页面
		MainApplication.getInstance().setChooseCity(locatedCityPinyin);
		MainApplication.getInstance().setChooseCityHan(locatedCityHan);
//		setPm25Data(locatedCityPinyin);
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locatedCityPinyin=MainApplication.getInstance().getChooseCity();
		locatedCityHan=MainApplication.getInstance().getChooseCityHan();
		setPm25Data(MainApplication.getInstance().getChooseCity());		
	}
	

	private void intiData() {
		// TODO Auto-generated method stub
		try {
			String[] effectAndAdvices=getEffectsAndAdvices(Integer.parseInt(pm25));
			tvCurrentCity.setText(locatedCityHan);//这块保存最近选择的城市
			String lastUpdateTime=Constants.DATE_TIME_FORMAT.format(new Date());
			tvLastUpdateTime.setText(lastUpdateTime);
			tvRight.setText(locatedCityHan);
			tvAirQuality.setText("空气质量"+quality);
			tvPM25.setText("PM2.5\n\t"+pm25);
			tvAQI.setText("AQI\n 48");
			tvAirConditon.setText(effectAndAdvices[0]+"。");
			tvMeasureTake.setText(effectAndAdvices[1]+"。");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@OnClick(R.id.imgTitleRight)
	public void doChooseCity(View v){
		//跳转到城市选择界面
		Intent intent=new Intent();
		intent.setClass(this, ChooseCitysActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.imgTitleBack)
	public void goBack(View v){
		this.finish();
	}
	

	
	private void setPm25Data(String cityName) {
		// 获取PM2.5的数据
		// 访问接口然后获取对应的数据
		// mTvDate.setText(TimeUtil.getSpecificDate(TimeUtil.PATTERN_YYYY_MM_DD,0));
		HomeBiz biz = new HomeBiz();
		biz.setCallBack(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				try {
					Pm25Bean bean = (Pm25Bean) response;
					Pm25 resultlist = bean.getResultlist().get(0);
					pm25 = resultlist.getPm25();
					quality = resultlist.getQuality();
					LogUtils.d("pm25.."+pm25+"..quality.."+quality);
					intiData();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub

			}
		});
		biz.getPm25Data(cityName, PreferencesUtil.getString("USER_PHONE"));
	}

	public String[] getEffectsAndAdvices(int pm25){
	  String[] effectsAndAdvices=new String[2];
      if(pm25>=0 && pm25<=50){
    	  effectsAndAdvices[0]=Constants.PM25_50[0];
    	  effectsAndAdvices[1]=Constants.PM25_50[1];
      }else if(pm25>=51 && pm25<=100){
    	  effectsAndAdvices[0]=Constants.PM25_100[0];
    	  effectsAndAdvices[1]=Constants.PM25_100[1];
      }else if(pm25>=101 && pm25<=150){
    	  effectsAndAdvices[0]=Constants.PM25_150[0];
    	  effectsAndAdvices[1]=Constants.PM25_150[1];
      }else if(pm25>=151 && pm25<=200){
    	  effectsAndAdvices[0]=Constants.PM25_200[0];
    	  effectsAndAdvices[1]=Constants.PM25_200[1];
      }else if(pm25>=201 && pm25<=300){
    	  effectsAndAdvices[0]=Constants.PM25_250[0];
    	  effectsAndAdvices[1]=Constants.PM25_250[1];
      }else{
    	  effectsAndAdvices[0]=Constants.PM25_300[0];
    	  effectsAndAdvices[1]=Constants.PM25_300[1];
      }
      return effectsAndAdvices;
	}
	
}
