package com.taikang.tkdoctor.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UpDateHealthPlanBean;
import com.taikang.tkdoctor.bean.UrlManBingListBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;
/**
 * 糖尿病
 * @author Administrator
 *
 */
public class ManBingGaoXueYaBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

//	String	smoke
//	String	5,6 直接赋值
//	String	birthday
//	String	sex
//	String	age
//	String	height
//	String	weight
//	String	smoke
	//	birthday
	public void getGaoXueYa(String sex,String height,String weight,String birthday,HashMap<String, String> parm) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sex", sex);
		paramsMap.put("birthday", birthday);
		paramsMap.put("height", height);
		paramsMap.put("weight", weight);
		for(Map.Entry<String, String> entry:parm.entrySet()){
			paramsMap.put(entry.getKey(), entry.getValue());
		}
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_ASSESS, paramsMap, "submitgxy", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return UrlManBingListBean.class;
			}

			@Override
			public void success(Response response) {
				if (netCallback != null)
					netCallback.taskSuccess(response);
			}

			@Override
			public void error(Response response) {
				if (netCallback != null)
					netCallback.taskError(response);
			}
		});
	}
}
