package com.taikang.tkdoctor.bean;

public class Info extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String picurl;

	private String title;

	private String informationid;

	private String content;

	private String summary;

	private String type;

	private String count;
	private String dates;

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInformationid() {
		return informationid;
	}

	public void setInformationid(String informationid) {
		this.informationid = informationid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}






}
