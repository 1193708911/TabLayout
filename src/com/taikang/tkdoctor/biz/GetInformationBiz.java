package com.taikang.tkdoctor.biz;

import java.util.HashMap;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.InfoDetailBean;
import com.taikang.tkdoctor.bean.InfoResponseModel;
import com.taikang.tkdoctor.bean.InfoTopResponseModel;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.request.NetUtil;
import com.taikang.tkdoctor.request.RequestCallback;

public class GetInformationBiz {

	public static NetCallback netCallback;

	public void setNetCallBack(NetCallback netCallBack) {
		this.netCallback = netCallBack;
	}

	/**
	 * 获取资讯列表接口
	 * 
	 * @param phone
	 * @param type
	 * @param pageno
	 * @param pagesize
	 */
	public void GetInformations(String phone, String type, int pageno, int pagesize) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("type", type);
		params.put("pageNo", pageno + "");
		params.put("pagesize", pagesize + "");
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, params, "getMessageList", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return InfoResponseModel.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskError(response);
				}
			}
		});

	}

	/**
	 * 获取资讯头条信息
	 * 
	 * @param date
	 * @param type
	 */
	public void GetInformationTop(String date, String type) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("date", date);
		params.put("type", type);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, params, "getMessageTop", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return InfoTopResponseModel.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskError(response);
				}
			}
		});
	}

	/**
	 * 获取资讯详情页面
	 * 
	 * @param informationId
	 */
	public void GetInformationDetails(String informationId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("informationid", informationId);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, params, "getMessage", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return InfoDetailBean.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskError(response);
				}
			}
		});
	}

	/**
	 * 添加健康咨询数据到收藏列表
	 * 
	 * @param informationId
	 */
	public void addInformationDetails(String informationId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("informationid", informationId);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, params, "addCollectMessage", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return Response.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskError(response);
				}
			}
		});
	}

	/**
	 * 取消收藏
	 * @param informationId
	 */
	public void cancelInformationDetails(String informationId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("informationid", informationId);
		NetUtil.httpPostCommon(ServerConfig.URL_PATH_INFO, params, "deleteCollectMessage", new RequestCallback() {

			@Override
			public Class<? extends Response> getResultType() {
				// TODO Auto-generated method stub
				return Response.class;
			}

			@Override
			public void success(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskSuccess(response);
				}
			}

			@Override
			public void error(Response response) {
				// TODO Auto-generated method stub
				if (netCallback != null) {
					netCallback.taskError(response);
				}
			}
		});
	}

}
