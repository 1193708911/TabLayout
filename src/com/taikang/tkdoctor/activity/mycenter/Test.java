package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.ManBingDaiXieBiz;
import com.taikang.tkdoctor.biz.ManBingDiseaseBiz;
import com.taikang.tkdoctor.biz.ManBingGaoXueYaBiz;
import com.taikang.tkdoctor.biz.ManBingXinXueGuanBiz;
import com.taikang.tkdoctor.request.NetCallback;
public class Test extends BaseActivity implements NetCallback{
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
//		sendRequetDisease();
//		sendRequestGaoXueYa();
//		sendRequestDaiXie();
	     sendRequestXinXueGuan();
	}
	/**
	 * 缺血性心血管
	 */
	private void sendRequestXinXueGuan() {
		// TODO Auto-generated method stub
//		  "1":"a@c",
		//	    "3":"0",
		//	"2":"1"
		//	    "4":"125",
		//  	"5":"85",
		//	    "6":"5.7",
		//	    "birthday":"1998-08-10",
		//	    "height":"100",
		//	    "sex":"0",
		//	    "weight":"50"
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("1", "A@C");
		params.put("2", "1");
		params.put("3", "0");
		params.put("4", "125");
		params.put("5", "85");
		params.put("6", "5.7");
		params.put("7", "70");
		params.put("8", "7.0");
		params.put("9", "11.1");
		params.put("10", "6.0");
		
		ManBingXinXueGuanBiz xinXueGuanBiz=new ManBingXinXueGuanBiz();
		xinXueGuanBiz.setCallback(this);
		xinXueGuanBiz.getXinXueGuan("1", "170", "55", "1991-06-23", params);
	}
	/**
	 * 代谢综合征
	 */
	private void sendRequestDaiXie() {
		// TODO Auto-generated method stub
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("1", "A@C");
		params.put("2", "1");
		params.put("3", "0");
		params.put("4", "A@C");
		params.put("5", "125");
		params.put("6", "85");
		params.put("7", "70");
		params.put("8", "7.0");
		params.put("9", "11.1");
		
		ManBingDaiXieBiz daiXieBiz=new ManBingDaiXieBiz();
		daiXieBiz.setCallback(this);
		daiXieBiz.getDaiXie("1", "170", "55", "1991-06-23", params);
		
	}
	/**
	 * 发送高血压压请求
	 */
	private void sendRequestGaoXueYa() {
		// TODO Auto-generated method stub
		ManBingGaoXueYaBiz gaoXueYaBiz=new ManBingGaoXueYaBiz();
		gaoXueYaBiz.setCallback(this);
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("1", "A@C");
		params.put("2", "1");
		params.put("3", "0");
		params.put("4", "A@C");
		params.put("5", "125");
		params.put("6", "85");
		params.put("7", "A");
		params.put("8", "A");
		params.put("9", "A");
		
		gaoXueYaBiz.getGaoXueYa("1", "170", "55", "1991-06-23", params);
	}
	/**
	 * 发送糖尿请求
	 */
	private void sendRequetDisease() {
		// TODO Auto-generated method stub
		ManBingDiseaseBiz maBingDiseaseBiz=new ManBingDiseaseBiz();
		maBingDiseaseBiz.setCallback(this);
		HashMap<String, String> parmMap=new HashMap<String, String>();
		parmMap.put("1", "B");
		parmMap.put("10", "0");
		parmMap.put("11", "0");
		parmMap.put("12", "0");
		parmMap.put("2", "1");
		parmMap.put("3", "0");
		parmMap.put("5", "5");
		parmMap.put("7", "190");
		parmMap.put("8", "0");
		parmMap.put("9", "0");
		
		maBingDiseaseBiz.getDiseaseResult("1", "25", "189", "66", "70", "70", "1991-6-23", parmMap);

	}
	//返回结果
	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub

	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub

	}

}
