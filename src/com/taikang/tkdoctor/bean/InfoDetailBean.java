package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class InfoDetailBean extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<InfoDetails> resultlist;
	
	public ArrayList<InfoDetails> getResultlist() {
		return resultlist;
	}


	public void setResultlist(ArrayList<InfoDetails> resultlist) {
		this.resultlist = resultlist;
	}

	public class InfoDetails extends Response{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public InfoDetail resultlist;

		public InfoDetail getResultlist() {
			return resultlist;
		}

		public void setResultlist(InfoDetail resultlist) {
			this.resultlist = resultlist;
		}
		
	}
	

}
