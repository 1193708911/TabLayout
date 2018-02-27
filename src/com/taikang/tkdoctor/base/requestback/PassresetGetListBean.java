package com.taikang.tkdoctor.base.requestback;

import java.util.List;

import com.taikang.tkdoctor.bean.Response;

public class PassresetGetListBean extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4545222241L;
	
	private List<PassresetGetBean> resultlist;
	
	public List<PassresetGetBean> getResultList() {
		return resultlist;
	}

	public void setResultList(List<PassresetGetBean> resultlist) {
		this.resultlist = resultlist;
	}
}
