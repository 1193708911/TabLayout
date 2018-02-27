package com.taikang.tkdoctor.bean;

public class Question extends Response {
	private static final long serialVersionUID = 1L;
	private String detailid;
	private String detailname;
	private String questionid;
	private String questionname;
	private String questionvalue;
	private String sex;
	private String bz;
	private int chooseindex=-1;//题目选中的下标
	
	public int getChooseindex() {
		return chooseindex;
	}

	public void setChooseindex(int chooseindex) {
		this.chooseindex = chooseindex;
	}

	public String getDetailid() {
		return detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	public String getDetailname() {
		return detailname;
	}

	public void setDetailname(String detailname) {
		this.detailname = detailname;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getQuestionname() {
		return questionname;
	}

	public void setQuestionname(String questionname) {
		this.questionname = questionname;
	}

	public String getQuestionvalue() {
		return questionvalue;
	}

	public void setQuestionvalue(String questionvalue) {
		this.questionvalue = questionvalue;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}
