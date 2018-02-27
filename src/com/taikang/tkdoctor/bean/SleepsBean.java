package com.taikang.tkdoctor.bean;

import com.lidroid.xutils.db.annotation.Table;

@Table(name="sleeps")
public class SleepsBean {

	private String solarterm;
	
	private String sleep_recommend;
	
	private String sleep_popcontent;
	
	private String sleep_physique;

	public String getSleep_physique() {
		return sleep_physique;
	}

	public void setSleep_physique(String sleep_physique) {
		this.sleep_physique = sleep_physique;
	}

	public String getSolarterm() {
		return solarterm;
	}

	public void setSolarterm(String solarterm) {
		this.solarterm = solarterm;
	}

	public String getSleep_recommend() {
		return sleep_recommend;
	}

	public void setSleep_recommend(String sleep_recommend) {
		this.sleep_recommend = sleep_recommend;
	}

	public String getSleep_popcontent() {
		return sleep_popcontent;
	}

	public void setSleep_popcontent(String sleep_popcontent) {
		this.sleep_popcontent = sleep_popcontent;
	}
	
	
	
	
}
