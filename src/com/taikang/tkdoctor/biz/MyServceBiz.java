package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.MyServiceBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UpDateHealthPlanBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class MyServceBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	public void myServceRequest() {
		Map<String, String> paraMap=new HashMap<String, String>();
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_USER, paraMap, "getDate", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return MyServiceBean.class;
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
