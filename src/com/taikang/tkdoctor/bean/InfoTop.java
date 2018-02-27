package com.taikang.tkdoctor.bean;

public class InfoTop extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String picurl;
	
    private String title;
	
    private String informationid;
	
    private String position;

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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
    

}
