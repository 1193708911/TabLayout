package com.taikang.tkdoctor.base.requestback;

import java.util.List;

import com.taikang.tkdoctor.bean.Response;

public class LoginGetCodeListBean extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 454541L;
	
	private List<LoginGetCodeBean> resultlist;
	
	public List<LoginGetCodeBean> getResultList() {
		return resultlist;
	}

	public void setResultList(List<LoginGetCodeBean> resultlist) {
		this.resultlist = resultlist;
	}
}
