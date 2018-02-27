package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.List;

public class CollectionUrlBean extends Response implements Serializable{
	/**
	 * 获取html5收藏界面URL
	 */
	private static final long serialVersionUID = 1L;
	private List<String> resultlist;
	public List<String> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<String> resultlist) {
		this.resultlist = resultlist;
	}

	public  class MessageUrlBean extends Response{
		private static final long serialVersionUID = 1L;
		private  String url;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

	}

}
