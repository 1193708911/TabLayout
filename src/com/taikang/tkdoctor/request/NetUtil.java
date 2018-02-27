package com.taikang.tkdoctor.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;

public class NetUtil {

	@SuppressWarnings("deprecation")
	private static void httpPost(String urlType, Map<String, String> paramsMap, Map<String, String> paraMapList,
			RequestCallBack<String> callback) {

		if (!Util.isNetworkConnected()) {
			Util.showToast("网络连接失败，请检查网络连接！");
			return;
		}
		HttpUtils client = new HttpUtils();
		client.configResponseTextCharset("UTF-8");//
		// 设置当前请求的缓存时间
		client.configCurrentHttpCacheExpiry(0);
		// 设置默认请求的缓存时间
		client.configDefaultHttpCacheExpiry(0);
		RequestParams params = new RequestParams();

		JSONObject jsonObject = new JSONObject();
		Set<Entry<String, String>> entryListSet = paramsMap.entrySet();
		for (Entry<String, String> entry : entryListSet) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		if (paraMapList != null) {
			JSONObject jsonList = new JSONObject();
			Set<Entry<String, String>> entrySet = paraMapList.entrySet();
			for (Entry<String, String> entry : entrySet) {
				jsonList.put(entry.getKey(), entry.getValue());
			}
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(jsonList);

			jsonObject.put("requestlist", jsonArray);
		} else {
			jsonObject.put("requestlist", "");
		}
		// end
		String jsonStr = jsonObject.toString();
		String jsonStrMD5 = Util.stringMD5(jsonStr);
		// params.addBodyParameter("jsonStr", jsonStr);
		NameValuePair nvp1 = new BasicNameValuePair("jsonStr", jsonStr);
		NameValuePair nvp2 = new BasicNameValuePair("checkStr", jsonStrMD5);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(nvp1);
		nameValuePairs.add(nvp2);
		params.addBodyParameter(nameValuePairs);
		LogUtils.e("发送数据:" + jsonStr);
		// params.addBodyParameter("jsonStr", jsonStr);
		String url = ServerConfig.URL_PATH + urlType;
		LogUtils.e("请求url:" + url);
		client.send(HttpRequest.HttpMethod.POST, url, params, callback);
	}

	@SuppressWarnings("deprecation")
	private static void httpPostMapList(String urlType, Map<String, String> paramsMap,
			List<HashMap<String, String>> paraMapList, RequestCallBack callback) {

		if (!Util.isNetworkConnected()) {
			Util.showToast("网络连接失败，请检查网络连接！");
			return;
		}
		HttpUtils client = new HttpUtils();
		client.configResponseTextCharset("UTF-8");//
		// 设置当前请求的缓存时间
		client.configCurrentHttpCacheExpiry(0);
		// 设置默认请求的缓存时间
		client.configDefaultHttpCacheExpiry(0);
		RequestParams params = new RequestParams();

		JSONObject jsonObject = new JSONObject();
		Set<Entry<String, String>> entryListSet = paramsMap.entrySet();
		for (Entry<String, String> entry : entryListSet) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		if (paraMapList != null) {
			JSONArray jsonArray = new JSONArray();
			for (HashMap<String, String> map : paraMapList) {
				JSONObject jsonList = new JSONObject();
				Set<Entry<String, String>> entrySet = map.entrySet();
				for (Entry<String, String> entry : entrySet) {
					jsonList.put(entry.getKey(), entry.getValue());
				}
				jsonArray.add(jsonList);
			}
			
			JSONArray array=new JSONArray();
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("requestlist", jsonArray);
			array.add(jsonObj);
//			jsonObject.put("requestlist", jsonArray);
			jsonObject.put("requestlist", array);
		} else {
			jsonObject.put("requestlist", "");
		}
		// end
		String jsonStr = jsonObject.toString();
		String jsonStrMD5 = Util.stringMD5(jsonStr);
		// params.addBodyParameter("jsonStr", jsonStr);
		NameValuePair nvp1 = new BasicNameValuePair("jsonStr", jsonStr);
		NameValuePair nvp2 = new BasicNameValuePair("checkStr", jsonStrMD5);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(nvp1);
		nameValuePairs.add(nvp2);
		params.addBodyParameter(nameValuePairs);
		LogUtils.e("发送数据:" + jsonStr);
		// params.addBodyParameter("jsonStr", jsonStr);
		String url = ServerConfig.URL_PATH + urlType;
		LogUtils.e("请求url:" + url);
		client.send(HttpRequest.HttpMethod.POST, url, params, callback);
	}

	/**
	 * 
	 * @param urlType
	 *            url类型，如login.do
	 * @param paramsMapList
	 *            参数
	 * @param callback
	 *            回调
	 */
	public static void httpPostBase(String urlType, Map<String, String> paraMapList, String method,
			final RequestCallback callback) {

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("method", method);
		paramsMap.put("version", ServerConfig.URL_VERSION);
		paramsMap.put("requestsource", "doctor");
		httpPost(urlType, paramsMap, paraMapList, callback);
	}

	/**
	 * 
	 * @param urlType
	 *            url类型，
	 * @param paramsMapList
	 *            参数，version、Sessionid、uid不需要传了
	 * @param callback
	 *            回调
	 */
	public static void httpPostCommon(String urlType, Map<String, String> paraMapList, String method,
			final RequestCallback callback) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("method", method);
		paramsMap.put("version", ServerConfig.URL_VERSION);
		paramsMap.put("requestsource", "doctor");
		HdBsAuthUser user = MainApplication.getInstance().getUser();
		if (user != null) {
			paramsMap.put("sessionid", user.getSessionId());
			paramsMap.put("uid", user.getUserId());
		}
		
//		paramsMap.put("sessionid", PreferencesUtil.getString("uid"));
//		paramsMap.put("uid", PreferencesUtil.getString("sessionid"));
		
		httpPost(urlType, paramsMap, paraMapList, callback);
	}

	public static void httpPostUnStandard(String urlType, List<HashMap<String, String>> paraMapList, String method,
			final RequestCallback callback) {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("method", method);
		paramsMap.put("version", ServerConfig.URL_VERSION);
		paramsMap.put("requestsource", "doctor");
		HdBsAuthUser user = MainApplication.getInstance().getUser();
		if (user != null) {
			paramsMap.put("sessionid", user.getSessionId());
			paramsMap.put("uid", user.getUserId());
		}
		httpPostSpecial(urlType, paramsMap, paraMapList, callback);
	}

	/**
	 * 
	 * @param urlType
	 *            url类型，如login.do
	 * @param paramsMap
	 *            参数
	 * @param callback
	 *            回调
	 */
	public static void httpPost(String urlType, Map<String, String> paramsMap, Map<String, String> paraMapList,
			final RequestCallback callback) {

		httpPost(urlType, paramsMap, paraMapList, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (callback != null) {
					Class<? extends Response> resultType = callback.getResultType();
					if (resultType == null)
						resultType = Response.class;
					String result = responseInfo.result;

					LogUtils.d("后台JSON返回：" + result);
					Response error = new Response();
					error.setResultCode("1");
					if (TextUtils.isEmpty(result)) {
						callback.error(error);
						return;
					}
					Response respObj = null;
					try {
						LogUtils.d("name.." + resultType.getName());
						respObj = JSON.parseObject(result, resultType);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (respObj == null) {
						// Util.showToast("数据加载失败");
						callback.error(error);
						return;
					}
                       LogUtils.d("resultCode.."+respObj.getResultCode());
					if (ServerConfig.SERVER_SUCCESS_FLAG.equals(respObj.getResultCode())) {
						// Util.showToast(respObj.getResultMsg());
						callback.success(respObj);
					} else {
						String resultMsg = respObj.getResultMsg();
						if (!TextUtils.isEmpty(resultMsg))
							Util.showToast(resultMsg);
						callback.error(respObj);
					}
				}
			}

			@Override
			public void onFailure(HttpException e, String msg) {
				LogUtils.e(msg, e);
				LogUtils.d("发生异常错误情况...."+msg);
				if (callback != null) {
					Response error = new Response();
					error.setResultCode("1");
					callback.error(error);
				}
			}
		});
	}

	public static void httpPostSpecial(String urlType, Map<String, String> paramsMap,
			List<HashMap<String, String>> paraMapList, final RequestCallback callback) {

		httpPostMapList(urlType, paramsMap, paraMapList, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if (callback != null) {
					Class<? extends Response> resultType = callback.getResultType();
					if (resultType == null)
						resultType = Response.class;
					String result = responseInfo.result;
					LogUtils.d("后台JSON返回：" + result);
					Response error = new Response();
					error.setResultCode("1");
					if (TextUtils.isEmpty(result)) {
						callback.error(error);
						return;
					}
					Response respObj = null;
					try {
						LogUtils.d("name.." + resultType.getName());
						respObj = JSON.parseObject(result, resultType);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (respObj == null) {
						// Util.showToast("数据加载失败");
						callback.error(error);
						return;
					}
					if (ServerConfig.SERVER_SUCCESS_FLAG.equals(respObj.getResultCode())) {
						// Util.showToast(respObj.getResultMsg());
						callback.success(respObj);
					} else {
						String resultMsg = respObj.getResultMsg();
						if (!TextUtils.isEmpty(resultMsg))
							Util.showToast(resultMsg);
						callback.error(respObj);
					}
				}
			}

			@Override
			public void onFailure(HttpException e, String msg) {
				LogUtils.e(msg, e);
				if (callback != null) {
					Response error = new Response();
					error.setResultCode("1");
					callback.error(error);
				}
			}
		});
	}
}
