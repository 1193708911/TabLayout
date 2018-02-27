package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class MyListCollectionBean extends Response implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<MyCollectionListBean> resultlist;
	public ArrayList<MyCollectionListBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<MyCollectionListBean> resultlist) {
		this.resultlist = resultlist;
	}
	public class MyCollectionListBean extends Response {
		private static final long serialVersionUID = 1L;
		public ArrayList<MyCollectionBean> resultlist;
		public ArrayList<MyCollectionBean> getResultlist() {
			return resultlist;
		}
		public void setResultlist(ArrayList<MyCollectionBean> resultlist) {
			this.resultlist = resultlist;
		}


	}
	
}
