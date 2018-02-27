package com.taikang.tkdoctor.base.requestback;

import java.util.List;

import com.taikang.tkdoctor.bean.Response;

public class GetSymptonListBean extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6565739737027044850L;
	
	private List<SymptonListBean> resultlist;

	public List<SymptonListBean> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<SymptonListBean> resultlist) {
		this.resultlist = resultlist;
	}
	
}
