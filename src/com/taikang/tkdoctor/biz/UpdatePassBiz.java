package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UpDateHealthPlanBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;
import com.taikang.tkdoctor.util.MyUtil;
import com.taikang.tkdoctor.util.Util;

public class UpdatePassBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	/**
	 * 更改密码
	 * @param pass
	 * @param newPass
	 */
	public void updatePass(String pass,String newPass) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("password", Util.stringMD5(pass));
		paramsMap.put("newpassword",  Util.stringMD5(newPass));
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_USER, paramsMap, "upatePassword", new RequestCallback() {

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
