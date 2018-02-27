package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AdvertisementList extends Response implements Serializable{
	/**
	 *首页头条广告信息
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<AdvertiList> resultlist;
	public ArrayList<AdvertiList> getResultList() {
		return resultlist;
	}
	public void setResultList(ArrayList<AdvertiList> resultlist) {
		this.resultlist = resultlist;
	}
	



}
