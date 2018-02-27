package com.taikang.tkdoctor.request;

import java.util.Hashtable;

/**
 * @ClassName: CommunicatorModel
 * @Description: 封装服务器返回过来的数据
 * @author helongfei
 * @date
 * 
 */
public class ConnectionResponseModel {

	private Hashtable<String, String> headerInfo = new Hashtable<String, String>();
	private String bodyInfo;
	private int statusCode;

	public Hashtable<String, String> getHeaderInfo() {
		return headerInfo;
	}

	public void setHeaderInfo(Hashtable<String, String> headerInfo) {
		this.headerInfo = headerInfo;
	}

	public String getBodyInfo() {
		return bodyInfo;
	}

	public void setBodyInfo(String bodyInfo) {
		this.bodyInfo = bodyInfo;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
