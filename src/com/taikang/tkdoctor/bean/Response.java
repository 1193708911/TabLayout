package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7323842972662100473L;
	protected String resultCode;//操作结果说明： 0：表示操作成功；1：表示操作失败;2：session过期
	protected String resultMsg;//	错误信息
	protected String debugMsg;//	调试信息
	protected String Uid;
	protected String SessiongId;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getDebugMsg() {
		return debugMsg;
	}

	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}

	
	public String getUId() {
		return Uid;
	}

	public void setUId(String uid) {
		this.Uid = uid;
	}
	
	public String getSessionId() {
		return SessiongId;
	}

	public void setSessionId(String SessiongId) {
		this.SessiongId = SessiongId;
	}

}
