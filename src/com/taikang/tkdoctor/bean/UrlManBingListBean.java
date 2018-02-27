package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 */
public class UrlManBingListBean extends Response implements Serializable {

	private static final long serialVersionUID = 188L;
	private ArrayList<UrlBean> resultlist;

	public ArrayList<UrlBean> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<UrlBean> resultlist) {
		this.resultlist = resultlist;
	}

	public class UrlBean extends Response{

		private static final long serialVersionUID = 188L;
		private String url;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}


	}

}
