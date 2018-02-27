package com.taikang.tkdoctor.bean;

public class InfoDetail extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String picurl;
	private String title;
	private String content;
	private String summary;
	private String dates;
	private String html5;
	private String readcount;
	private String type;
	
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getHtml5() {
		return html5;
	}
	public void setHtml5(String html5) {
		this.html5 = html5;
	}
	public String getReadcount() {
		return readcount;
	}
	public void setReadcount(String readcount) {
		this.readcount = readcount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
