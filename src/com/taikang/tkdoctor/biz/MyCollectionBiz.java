package com.taikang.tkdoctor.biz;

import java.util.HashMap;

import com.taikang.tkdoctor.bean.MyCollectionBean;
import com.taikang.tkdoctor.bean.MyListCollectionBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class MyCollectionBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	public void getMyCollection() {
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, new HashMap<String, String>(), "getMyCollectionList", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return MyListCollectionBean.class;
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
