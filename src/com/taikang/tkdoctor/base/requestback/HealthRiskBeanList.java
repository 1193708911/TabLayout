package com.taikang.tkdoctor.base.requestback;

import java.util.List;

import com.taikang.tkdoctor.bean.Response;

public class HealthRiskBeanList extends Response{
	/**
	 * 健康风险的list
	 */
	private static final long serialVersionUID = 45221324541L;
	
	private List<HealthRiskBean> resultlist;

	public List<HealthRiskBean> getResultlist() {
		return resultlist;
	}

	public void setResultlist(List<HealthRiskBean> resultlist) {
		this.resultlist = resultlist;
	}
}
	
	
