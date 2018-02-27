package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class Sports extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<SportInfos> resultlist;
	
	public ArrayList<SportInfos> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<SportInfos> resultlist) {
		this.resultlist = resultlist;
	}

	public class SportInfos{
		public ArrayList<SportInfo> resultlist;

		public ArrayList<SportInfo> getResultlist() {
			return resultlist;
		}

		public void setResultlist(ArrayList<SportInfo> resultlist) {
			this.resultlist = resultlist;
		}
		
	}

}
