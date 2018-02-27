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

public class UpdateUserInfoBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	/**
	 * @param 姓名
	 * @param 性别
	 * @param 生日
	 * @param身高
	 * @param体重
	 */
	public void updateUserInfo(String name,String sex,String birthday,String height,String weight) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("name", name);
		paramsMap.put("sex", sex);
		paramsMap.put("birthday", birthday);
		paramsMap.put("height", height);
//		paramsMap.put("mobile", "13001129011");
		paramsMap.put("weight", weight);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_USER, paramsMap, "updateUserBaseInformation", new RequestCallback() {

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
