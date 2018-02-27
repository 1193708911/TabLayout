package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoResponseModel extends Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<Infos> resultlist;// 出参列表

	public ArrayList<Infos> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<Infos> resultlist) {
		this.resultlist = resultlist;
	}

	public class Infos extends Response {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ArrayList<Info> resultlist;
        public String count;
        
		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public ArrayList<Info> getResultlist() {
			return resultlist;
		}

		public void setResultlist(ArrayList<Info> resultlist) {
			this.resultlist = resultlist;
		}

	}

}
