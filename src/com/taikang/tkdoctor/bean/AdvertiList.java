package com.taikang.tkdoctor.bean;

import java.util.ArrayList;

public class AdvertiList extends Response{
	private static final long serialVersionUID = 1L;
	private ArrayList<Adavertisement> resultlist;
	public ArrayList<Adavertisement> getResultlist() {
		return resultlist;
	}
	public void setResultlist(ArrayList<Adavertisement> resultlist) {
		this.resultlist = resultlist;
	}
	public class Adavertisement extends Response{
		private static final long serialVersionUID = 1L;
		private String adid;
		private String title;
		private String imageUrl;
		private String bannerUrl;


		public String getImageUrl() {
			return imageUrl;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public String getBannerUrl() {
			return bannerUrl;
		}
		public void setBannerUrl(String bannerUrl) {
			this.bannerUrl = bannerUrl;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAdid() {
			return adid;
		}
		public void setAdid(String adid) {
			this.adid = adid;
		}

	}}