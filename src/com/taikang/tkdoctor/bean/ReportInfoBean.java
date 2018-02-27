package com.taikang.tkdoctor.bean;

import java.io.Serializable;

public class ReportInfoBean implements Serializable{

	/**
	 * 报告信息
	 */
	private static final long serialVersionUID = 1L;
	private String report_type;// tcmphysique disease health
	private String report_url;
	public String getReport_type() {
		return report_type;
	}
	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	public String getReport_url() {
		return report_url;
	}
	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}
	
	
	

}
