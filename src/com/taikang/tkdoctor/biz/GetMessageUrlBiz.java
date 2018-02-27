package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.CollectionUrlBean;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UpDateHealthPlanBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class GetMessageUrlBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	public void getMessageUrl(String informationid) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("informationid", informationid);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, paramsMap, "getMessage", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return CollectionUrlBean.class;
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
