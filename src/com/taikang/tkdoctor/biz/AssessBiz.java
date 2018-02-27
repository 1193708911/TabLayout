package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class AssessBiz {
public static NetCallback netCallback;
	
	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	
	/**
	 * 慢病风险Net
	 * 1.14 代谢综合症
	 */
	public void getSubmitdxzhz(String phoneNumber, String psw) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_LOGIN, paramsMap, "submitdxzhz", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return Response.class;
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
