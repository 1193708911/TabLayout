package com.taikang.tkdoctor.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.LocationBean;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;
import com.taikang.tkdoctor.bean.UserPersonalInfoDto;
import com.taikang.tkdoctor.db.DBManager;
import com.taikang.tkdoctor.db.MyDbUpgradeListener;
import com.taikang.tkdoctor.db.SQLiteDBManager;
import com.taikang.tkdoctor.requestcallback.BaiduLocationCallBack;
import com.taikang.tkdoctor.util.Config;
import com.taikang.tkdoctor.util.PreferencesUtil;

public class MainApplication extends Application{
	private static MainApplication mThis;
	private HdBsAuthUser user;
	private UserBaseInfoBean baseInfo;
	private DBManager dbManager;
	private UserPersonalInfoDto userBaseInfo;
	public List<Map<String, String>> listparamsMap=new ArrayList<Map<String,String>>();
	public Map<String, String> params_manbing = new HashMap<String, String>();
	
    public  LocationClient mLocationClient;
    public  MyLocationListener mLocationListener;
    public  LocationBean mLocationBean;
    public  BaiduLocationCallBack mLocatioCallBack;
    
    public  String chooseCity;
    public  String chooseCityHan;
    
	public String getChooseCityHan() {
		return chooseCityHan;
	}

	public void setChooseCityHan(String chooseCityHan) {
		this.chooseCityHan = chooseCityHan;
	}

	public String getChooseCity() {
		return chooseCity;
	}

	public void setChooseCity(String chooseCity) {
		this.chooseCity = chooseCity;
	}

	public static MainApplication getInstance() {
		return mThis;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mThis = this;
		mLocationClient=new LocationClient(this);
		mLocationListener=new MyLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		DbUtils db = DbUtils.create(mThis, Config.DB_NAME, Config.DB_VERSION, new MyDbUpgradeListener()).configDebug(Config.DB_DEBUG);
		if (DBManager.getInstance() == null) {
			dbManager = new SQLiteDBManager(db);
		}
	}
	
	public DBManager getDbManager() {
		return dbManager;
	}
	
	public HdBsAuthUser getUser() {
		return user;
	}

	public void setUser(HdBsAuthUser user) {
		this.user = user;
	}
	
	public UserPersonalInfoDto getUserBaseInfo() {
		return userBaseInfo;
	}

	public void setUserBaseInfo(UserPersonalInfoDto userBaseInfo) {
		this.userBaseInfo = userBaseInfo;
	}
	
	public LocationClient getBaiduLocationClient(){
		return mLocationClient;
	}
	
	public LocationBean getLocationBean(){
		return mLocationBean;
	}
	
	public void setBaiduLocationCallBack(BaiduLocationCallBack callBack){
		this.mLocatioCallBack=callBack;
	}
	
	
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			String city=location.getCity();
			String cityCode=location.getCityCode();
			double lat=location.getLatitude();
			double lng=location.getLongitude();
			mLocationBean=new LocationBean();
			mLocationBean.setCity(city);
			mLocationBean.setCityCode(cityCode);
			mLocationBean.setLat(String.valueOf(lat));
			mLocationBean.setLng(String.valueOf(lng));
			mLocationClient.stop();
			mLocatioCallBack.onLocatedCityCallBack(mLocationBean);
		}
   }

	public UserBaseInfoBean getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfoBean baseInfo) {
		this.baseInfo = baseInfo;
	}
	

}
