package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ServiceListBean extends Response implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<ServiceBean> resultlist;
	public ArrayList<ServiceBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<ServiceBean> resultlist) {
		this.resultlist = resultlist;
	}
public class ServiceBean extends Response{
	private static final long serialVersionUID = 1L;
	private String startdate;
	private String status;
	private String enddate;
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}}