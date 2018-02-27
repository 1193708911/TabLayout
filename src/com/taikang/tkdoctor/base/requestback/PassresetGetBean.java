package com.taikang.tkdoctor.base.requestback;

import com.taikang.tkdoctor.bean.Response;

public class PassresetGetBean extends Response{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5616222261L;
	private String mobile;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
