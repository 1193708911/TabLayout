package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class TcmPhisicListBean extends Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<TcmPhisicBean> resultlist;
	public ArrayList<TcmPhisicBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<TcmPhisicBean> resultlist) {
		this.resultlist = resultlist;
	}
	
}

