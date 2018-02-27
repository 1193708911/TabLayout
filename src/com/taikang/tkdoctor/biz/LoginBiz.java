package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.requestback.PassresetGetListBean;
import com.taikang.tkdoctor.bean.LoginListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class LoginBiz {
	
	public static NetCallback netCallback;
	
	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	
	/**
	 * 登录Net
	 * @param phoneNumber
	 * @param psw
	 */
	public void login(String phoneNumber, String psw) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("mobile", phoneNumber);
		paramsMap.put("password", psw);
		paramsMap.put("client", "android");
		paramsMap.put("token", phoneNumber);
		NetUtil.httpPostBase(ServerConfig.URL_PATH_LOGIN, paramsMap, "login", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return LoginListBean.class;
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
	
	/**
	 * 忘记密码Net
	 * @param phoneNumber
	 * @param psw
	 */
	public void getPassreset(String phoneNumber, String psw) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("mobile", phoneNumber);
		paramsMap.put("password", psw);
		NetUtil.httpPostBase(ServerConfig.URL_PATH_LOGIN, paramsMap, "passreset", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return PassresetGetListBean.class;
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
