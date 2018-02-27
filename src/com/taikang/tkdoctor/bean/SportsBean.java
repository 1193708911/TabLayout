package com.taikang.tkdoctor.bean;

import com.lidroid.xutils.db.annotation.Table;

/**
 *运动
 */
@Table(name="sports")
public class SportsBean {

	private String solarterm;
	
	private String sport_recommend;
	
	private String sport_popcontent;
	
	private String sport_physique;

	public String getSport_physique() {
		return sport_physique;
	}

	public void setSport_physique(String sport_physique) {
		this.sport_physique = sport_physique;
	}

	public String getSolarterm() {
		return solarterm;
	}

	public void setSolarterm(String solarterm) {
		this.solarterm = solarterm;
	}

	public String getSport_recommend() {
		return sport_recommend;
	}

	public void setSport_recommend(String sport_recommend) {
		this.sport_recommend = sport_recommend;
	}

	public String getSport_popcontent() {
		return sport_popcontent;
	}

	public void setSport_popcontent(String sport_popcontent) {
		this.sport_popcontent = sport_popcontent;
	}
	
	
	
	
}
