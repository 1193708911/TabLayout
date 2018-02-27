package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginListBean extends Response implements Serializable{
	private ArrayList<LoginBean> resultlist;
	
	public ArrayList<LoginBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<LoginBean> resultlist) {
		this.resultlist = resultlist;
	}

	
}
