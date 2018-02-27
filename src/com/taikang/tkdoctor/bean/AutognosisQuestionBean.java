package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AutognosisQuestionBean extends Response implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<MyQuestionList> resultlist;
	public ArrayList<MyQuestionList> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<MyQuestionList> resultlist) {
		this.resultlist = resultlist;
	}
}
