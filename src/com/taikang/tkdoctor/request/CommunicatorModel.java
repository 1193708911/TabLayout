package com.taikang.tkdoctor.request;

import java.util.Hashtable;

/**
 * @ClassName: CommunicatorModel
 * @Description: 封装请求参数
 * @author helongfei
 * @date
 * 
 */
public class CommunicatorModel {

	// 方法地址
	private String bizAdd;

	// 发送信息键值对
	private Hashtable<String, Object> bodyData = new Hashtable<String, Object>();

	// json参数所对应的Key
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBizAdd() {
		return bizAdd;
	}

	public void setBizAdd(String bizAdd) {
		this.bizAdd = bizAdd;
	}

	public Hashtable<String, Object> getBodyData() {
		return bodyData;
	}

	public void setBodyData(Hashtable<String, Object> bodyData) {
		this.bodyData = bodyData;
	}
}
