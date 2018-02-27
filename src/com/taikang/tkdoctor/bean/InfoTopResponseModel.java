package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class InfoTopResponseModel extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayList<InfoTops> resultlist;
	

	public ArrayList<InfoTops> getResultlist() {
		return resultlist;
	}



	public void setResultlist(ArrayList<InfoTops> resultlist) {
		this.resultlist = resultlist;
	}


	public class InfoTops extends Response {

		private static final long serialVersionUID = 1L;
		
		private ArrayList<InfoTop> resultlist;

		public ArrayList<InfoTop> getResultlist() {
			return resultlist;
		}

		public void setResultlist(ArrayList<InfoTop> resultlist) {
			this.resultlist = resultlist;
		}

	}

}
