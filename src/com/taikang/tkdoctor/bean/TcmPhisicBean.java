package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public  class TcmPhisicBean extends Response{
	private static final long serialVersionUID = 1L;
	private ArrayList<MyTcmPhicBean> resultlist;
	public ArrayList<MyTcmPhicBean> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<MyTcmPhicBean> resultlist) {
		this.resultlist = resultlist;
	}
	public class MyTcmPhicBean extends Response{
		private static final long serialVersionUID = 1L;
		private String type;
		private String url;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	}

}
