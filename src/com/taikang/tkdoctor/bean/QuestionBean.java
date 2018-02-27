package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class QuestionBean extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArrayList<Questions> resultlist;

	public ArrayList<Questions> getResultlist() {
		return resultlist;
	}

	public void setResultlist(ArrayList<Questions> resultlist) {
		this.resultlist = resultlist;
	}

	public  class Questions {
		public ArrayList<Question> resultlist;

		public ArrayList<Question> getQuestions() {
			return resultlist;
		}

		public void setQuestions(ArrayList<Question> questions) {
			this.resultlist = questions;
		}

	}
}
