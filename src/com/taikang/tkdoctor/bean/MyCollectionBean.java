package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.List;

public class MyCollectionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String content;
	private String imageurl;
	private String dates;
	private String 	bannerUrl;
	private String information_id;
	private String record_id;
	private String summary;
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
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getInformation_id() {
		return information_id;
	}
	public void setInformation_id(String information_id) {
		this.information_id = information_id;
	}
	public String getRecord_id() {
		return record_id;
	}
	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}
	public String getImageurl() {
		return imageurl;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	

}
