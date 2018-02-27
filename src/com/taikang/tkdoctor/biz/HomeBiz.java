package com.taikang.tkdoctor.biz;

import java.util.HashMap;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.AdvertisementList;
import com.taikang.tkdoctor.bean.Meals;
import com.taikang.tkdoctor.bean.Pm25Bean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.Sleeps;
import com.taikang.tkdoctor.bean.Sports;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class HomeBiz {

	public static NetCallback netCallBack;
	
	public void setCallBack(NetCallback netCallBack){
		    this.netCallBack=netCallBack;
	}
	
	/**
	 * 获取节气养生数据
	 * @param phone
	 */
	public void getSeasonalThreapyData(final String searchtype,String solarterm,String constitution){
        HashMap<String, String> params=new HashMap<String, String>();
        params.put("type", searchtype);//0：运动；1：膳食；2：睡眠
        params.put("name", solarterm);
        params.put("constitution", constitution);
        LogUtils.d("type.."+searchtype+"solarterm.."+solarterm);
        NetUtil.httpPostCommon(ServerConfig.URL_PATH_SEASONTHREAPY, params, "healthConsultation", new RequestCallback() {
        	@Override
        	public Class<? extends Response> getResultType() {
        		// TODO Auto-generated method stub
        		if(searchtype.equals("0")){
                   return Sports.class;        			
        		}else if(searchtype.equals("1")){
        		   return Meals.class; 
        		}else if(searchtype.equals("2")){
        		   return Sleeps.class; 
        		}
				return null;
        	}
			
			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskSuccess(response);
				}
			}
			
			
			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskError(response);
				}
			}
		});
		
		
	}
	
	
	
		
	//这块也业务上的数据写到
	public void getPm25Data(String cityname,String phone) {//这块是处理返回的数据
        HashMap<String, String> params=new HashMap<String, String>();
        params.put("cityname", cityname);
        params.put("token", "");
        params.put("mobile", phone);
        params.put("client", "android");
        NetUtil.httpPostCommon(ServerConfig.URL_PATH_PM25, params, "getPm", new RequestCallback() {
        	@Override
        	public Class<? extends Response> getResultType() {
        		// TODO Auto-generated method stub
        		return Pm25Bean.class;
        	}
			
			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskSuccess(response);
				}
			}
			
			
			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskError(response);
				}
			}
		});
	}
	
	//Banner路径
	public void getBannerImageUrls() {//这块是处理返回的数据
        HashMap<String, String> params=new HashMap<String, String>();
        NetUtil.httpPostCommon(ServerConfig.URL_PATH_SEASONTHREAPY, params, "getBanner", new RequestCallback() {
        	@Override
        	public Class<? extends Response> getResultType() {
        		// TODO Auto-generated method stub
        		return AdvertisementList.class;
        	}
			
			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskSuccess(response);
				}
			}
			
			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if(netCallBack!=null){
					netCallBack.taskError(response);
				}
			}
		});
	}

	

}