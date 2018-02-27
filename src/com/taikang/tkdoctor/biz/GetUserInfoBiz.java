package com.taikang.tkdoctor.biz;

import java.util.HashMap;

import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class GetUserInfoBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	public void getUserBaseInfo() {
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_USER, new HashMap<String, String>(), "getUserBaseInformation", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return UserBaseInfoBean.class;
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
