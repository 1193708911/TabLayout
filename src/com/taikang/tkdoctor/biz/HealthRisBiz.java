package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.requestback.HealthRiskBeanList;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class HealthRisBiz {
public static NetCallback netCallback;
	
	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	
	/**
	 * 健康风险评估Net
	 */
	public void setHealthRisk(String requestlist) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("requestlist", requestlist); 
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_LOGIN, paramsMap, "submitResulthealthRisks", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return HealthRiskBeanList.class;
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
