package com.taikang.tkdoctor.bean;

import java.io.Serializable;

public class UpDateHealthPlanBean extends Response implements Serializable {

	/**
	 * 状态和执行id
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String doplanid;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDoplanid() {
		return doplanid;
	}
	public void setDoplanid(String doplanid) {
		this.doplanid = doplanid;
	}
}
