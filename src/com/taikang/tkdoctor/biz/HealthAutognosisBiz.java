package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.requestback.GetSymptonListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class HealthAutognosisBiz {
		
	public static NetCallback netCallback;
		
//	public void setCallback(NetCallback netCallback) { 
//		this.netCallback = netCallback; 
//	}
	/**
	 * 获取症状列表
	 */
	public void getSymptonList(String sex,String age, String around,String bodyRegion,final NetCallback netCallback) {
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sex", sex);
		paramsMap.put("age", age);
		paramsMap.put("around", around);
		paramsMap.put("bodyRegion", bodyRegion);
		
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_HEALTH_AUTOGNOSIS, paramsMap, "getSymptomList", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return GetSymptonListBean.class;
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
