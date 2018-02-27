package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HealthPlanListBean extends Response  implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<MyHealthPlanListBean> resultlist;
	public ArrayList<MyHealthPlanListBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<MyHealthPlanListBean> resultlist) {
		this.resultlist = resultlist;
	}
	
}