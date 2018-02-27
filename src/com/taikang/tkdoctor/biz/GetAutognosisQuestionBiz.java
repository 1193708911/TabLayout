package com.taikang.tkdoctor.biz;

import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.bean.AutognosisQuestionBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class GetAutognosisQuestionBiz {
public static NetCallback netCallback;
	
	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}
	
	/**
	 * 完成健康计划Net
	 * @param 执行状态id
	 * @param 执行状态
	 */
	public void getAutognosisQuestion(String sex,String age,String id,String symptom,String root) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("symptom", symptom);
		paramsMap.put("root", root);
		if(!("").equals(sex)&&!("").equals(age)&&!("").equals(id)){
			paramsMap.put("sex", sex);
			paramsMap.put("age", age);
			paramsMap.put("id", id);
		}
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_HEALTH_AUTOGNOSIS, paramsMap, "getAutognosisQuestion", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return AutognosisQuestionBean.class;
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
