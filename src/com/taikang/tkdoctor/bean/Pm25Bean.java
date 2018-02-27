package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class Pm25Bean extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Pm25> resultlist;
	
	public ArrayList<Pm25> getResultlist() {
		return resultlist;
	}



	public void setResultlist(ArrayList<Pm25> resultlist) {
		this.resultlist = resultlist;
	}


	public class Pm25 extends Response{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String Pm25;
		
		private String quality;

		public String getPm25() {
			return Pm25;
		}

		public void setPm25(String pm25) {
			Pm25 = pm25;
		}

		public String getQuality() {
			return quality;
		}

		public void setQuality(String quality) {
			this.quality = quality;
		}
	}

	
	

}
