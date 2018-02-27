package com.taikang.tkdoctor.biz;

import java.util.HashMap;

import com.taikang.tkdoctor.bean.AssessResultUrlBean;
import com.taikang.tkdoctor.bean.Pm25Bean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.TestResultBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class MyTestBiz {

	public static NetCallback netCallBack;

	public void setNetCallBack(NetCallback netCallBack) {
		this.netCallBack = netCallBack;
	}

	/**
	 * 这个方法返回的是这个人
	 * 中医体质（体质类型标记） 健康风险评估 糖尿病 代谢综合征 高血压 缺血性心血管疾病 对应的ID
	 * @param phone
	 */
	public void getMyTestResult(String phone) {
		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("token", phone);
//		params.put("client", "android");
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_MYASSESS, params, "getAssessResultlist", new RequestCallback() {
			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return TestResultBean.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallBack != null) {
					netCallBack.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallBack != null) {
					netCallBack.taskError(response);
				}
			}
		});
	}
	
	/**
	 * 这个方法是通过getMyTestResult方法拿到的类别来获取不同评估项目
	 * @param phone
	 * @param assessType
	 */
	public void getAssessResultByType(String phone,String assessType){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", phone);
		params.put("client", "android");
		params.put("assessType", assessType);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_EVAL_TCM, params, "getQuestionrResult", new RequestCallback() {
			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return AssessResultUrlBean.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallBack != null) {
					netCallBack.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallBack != null) {
					netCallBack.taskError(response);
				}
			}
		});
	}
	
	

}
