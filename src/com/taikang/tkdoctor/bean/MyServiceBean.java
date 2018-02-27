package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 我的服务
 * @author Administrator
 *
 */
public class MyServiceBean extends Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<ServiceListBean> resultlist;
	public ArrayList<ServiceListBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<ServiceListBean> resultlist) {
		this.resultlist = resultlist;
	}



}
