package com.taikang.tkdoctor.biz;

import java.util.ArrayList;
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

public class AddHealthPlanBiz {
	private StringBuilder builder;
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	public void addHealthPlan(String title,String content,String date,ArrayList<String> param) {
		builder=new StringBuilder();
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("title", title);
		paramsMap.put("content", content);
		paramsMap.put("bdate", date);
		if(param.size()==7){
			paramsMap.put("repeat", "1,2,3,4,5,6,7");
		}else if(param.size()==1){
			for(String str:param){
				if("每天".equals(str)){
					paramsMap.put("repeat", "1,2,3,4,5,6,7");
				}else {
					paramsMap.put("repeat", str);
				}
			}
		}else {
			for (String str:param) {
				builder.append(str).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
			paramsMap.put("repeat", builder.toString());
		}
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_UPDATE_HEALTHOPLAN, paramsMap, "addHealthPlan", new RequestCallback() {

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
