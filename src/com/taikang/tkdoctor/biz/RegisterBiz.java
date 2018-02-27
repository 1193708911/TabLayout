package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.taikang.tkdoctor.base.requestback.LoginGetCodeListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class RegisterBiz {
	public static NetCallback netCallback;
	
	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	
	/**
	 * 获取验证码Net
	 * @param start_date
	 * @param end_date
	 */
	public void getVerificationCode(String phoneNumber) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("mobile", phoneNumber);
		System.out.println(JSON.toJSON(paramsMap));//
		NetUtil.httpPostBase(ServerConfig.URL_PATH_LOGIN, paramsMap, "identify", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return LoginGetCodeListBean.class;
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
	 * 注册Net
	 * @param start_date
	 * @param end_date
	 */
	public void getRegist(String phoneNumber,String passWord,String incode) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("mobile", phoneNumber);
		paramsMap.put("password", passWord);
		paramsMap.put("invitation_code", incode);
		paramsMap.put("client", "andro");
		paramsMap.put("token", phoneNumber);
		NetUtil.httpPostBase(ServerConfig.URL_PATH_LOGIN, paramsMap, "regist", new RequestCallback() {

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
