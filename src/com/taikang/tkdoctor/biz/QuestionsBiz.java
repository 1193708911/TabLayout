package com.taikang.tkdoctor.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.AssessResultUrlBean;
import com.taikang.tkdoctor.bean.Question;
import com.taikang.tkdoctor.bean.QuestionBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.TcmPhisicListBean;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class QuestionsBiz {
	public static NetCallback netCallback;

	public void setCallback(NetCallback netCallback) {
		this.netCallback = netCallback;
	}

	// 获取中医体质测评问卷信息结果
	public void getTcmPhysiqueQuestions(String templateid,String phoneNumber) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("templateid", templateid);
		paramsMap.put("client", "android");
		paramsMap.put("token", phoneNumber);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_EVAL_TCM, paramsMap, "getQuestion", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return QuestionBean.class;
			}

			@Override
			public void success(Response response) {
				if (netCallback != null){					
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				if (netCallback != null)
					netCallback.taskError(response);
			}
		});
	}
	
	// 提交中医体质问题答卷并获取结果
	public void submitTcmPhysiqueQuestions(ArrayList<Question> questions) {
		ArrayList<HashMap<String, String>> listParamsMap=new ArrayList<HashMap<String,String>>();
		try {
	        for(Question s:questions){
	        	String bz=s.getBz();
	        	String score="";
	        	int chooseIndex=s.getChooseindex();//题目下标
	        	String value=s.getQuestionvalue();
	        	JSONObject jsonObj=new JSONObject(value);
	        	String choose_value_a=jsonObj.optString("没有");
	        	String choose_value_b=jsonObj.optString("很少");
	        	String choose_value_c=jsonObj.optString("有时");
	        	String choose_value_d=jsonObj.optString("经常");
	        	String choose_value_e=jsonObj.optString("总是");
	            switch (chooseIndex) {
				case 0: score=choose_value_a;break;
				case 1: score=choose_value_b;break;
				case 2: score=choose_value_c;break;
				case 3: score=choose_value_d;break;
				case 4: score=choose_value_e;break;
				default:break;
				}
	        	String questionno=s.getQuestionid();
	        	HashMap<String, String> paramsMap = new HashMap<String, String>();
	        	paramsMap.put("bz", bz);
	        	paramsMap.put("score", score);//
	        	paramsMap.put("questionno", questionno);
	        	listParamsMap.add(paramsMap);
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        //测试
		
		NetUtil.httpPostUnStandard(ServerConfig.URL_PATH_EVAL_TCM, listParamsMap, "saveQuestionrResult", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				return TcmPhisicListBean.class;
			}

			@Override
			public void success(Response response) {
				if (netCallback != null){		
					//后台JSON返回：
					//{"resultcode":"0","uid":"4e668afd-0674-4593-b5e2-17724b89d6fb",
					//"resultlist":[{"resultlist":[{"type":9,"url":"\/zytzpg\/pinghe.html?index=19"}]}],"sessionid":"201511011610375033612013","resultmsg":"success","requestid":"201511011611168940618436"}
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				if (netCallback != null)
					netCallback.taskError(response);
			}
		});
	}
	
	
	

}
