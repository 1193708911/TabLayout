package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class Sleeps extends Response{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	public ArrayList<SleepInfos> resultlist;
	
	
	
	public ArrayList<SleepInfos> getResultlist() {
		return resultlist;
	}



	public void setResultlist(ArrayList<SleepInfos> resultlist) {
		this.resultlist = resultlist;
	}



	public class SleepInfos extends Response{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ArrayList<SleepInfo> resultlist;

		public ArrayList<SleepInfo> getResultlist() {
			return resultlist;
		}

		public void setResultlist(ArrayList<SleepInfo> resultlist) {
			this.resultlist = resultlist;
		}
		
		
		
	}
	
	
}
