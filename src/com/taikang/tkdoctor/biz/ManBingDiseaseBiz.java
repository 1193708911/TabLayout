package com.taikang.tkdoctor.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UpDateHealthPlanBean;
import com.taikang.tkdoctor.bean.UrlManBingListBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;
/**
 * 糖尿病
 * @author Administrator
 *
 */
public class ManBingDiseaseBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) { 
		this.netCallback = netCallback; 
	}

	//	sex
	//	age
	//	height
	//	weight
	//	waist
	//	hip
	//	 问题 7---12 直接传值。未测量直接为0
	//	第3题
	//	bc都是0,a是1
	//	第5题
	//	只选肢端溃疡，多饮多尿，其他不管
	//	1,2,3  分别对应 选1，选2， 还是都选 
	//
	//	第一题已患疾病
	//	A@B@C



	//	birthday
	public void getDiseaseResult(String sex,String age,String height,String weight,String waist,String hip,String birthday,HashMap<String, String> parm) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("sex", sex);
		paramsMap.put("age", age);
		paramsMap.put("hip", hip);
		paramsMap.put("waist", waist);
		paramsMap.put("birthday", birthday);
		paramsMap.put("height", height);
		paramsMap.put("weight", weight);
		for(Map.Entry<String, String> entry:parm.entrySet()){
			paramsMap.put(entry.getKey(), entry.getValue());
		}
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_ASSESS, paramsMap, "submittnb", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return UrlManBingListBean.class;
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
