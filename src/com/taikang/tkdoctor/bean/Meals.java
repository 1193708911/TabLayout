package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class Meals extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayList<MealInfos> resultlist;

	public ArrayList<MealInfos> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<MealInfos> resultlist) {
		this.resultlist = resultlist;
	}

	public class MealInfos {
		public ArrayList<MealInfo> resultlist;

		public ArrayList<MealInfo> getResultlist() {
			return resultlist;
		}

		public void setResultlist(ArrayList<MealInfo> resultlist) {
			this.resultlist = resultlist;
		}

	}

}
