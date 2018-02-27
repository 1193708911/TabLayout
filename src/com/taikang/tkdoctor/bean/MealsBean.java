package com.taikang.tkdoctor.bean;

import com.lidroid.xutils.db.annotation.Table;

/**
 *膳食
 */

@Table(name="meals")
public class MealsBean {

	private String solarterm;
	
	private String meal_yichi;
	
	private String meal_shaochi;
	
	private String meal_jichi;
	
	private String meal_physique;

	public String getMeal_physique() {
		return meal_physique;
	}

	public void setMeal_physique(String meal_physique) {
		this.meal_physique = meal_physique;
	}

	public String getSolarterm() {
		return solarterm;
	}

	public void setSolarterm(String solarterm) {
		this.solarterm = solarterm;
	}

	public String getMeal_yichi() {
		return meal_yichi;
	}

	public void setMeal_yichi(String meal_yichi) {
		this.meal_yichi = meal_yichi;
	}

	public String getMeal_shaochi() {
		return meal_shaochi;
	}

	public void setMeal_shaochi(String meal_shaochi) {
		this.meal_shaochi = meal_shaochi;
	}

	public String getMeal_jichi() {
		return meal_jichi;
	}

	public void setMeal_jichi(String meal_jichi) {
		this.meal_jichi = meal_jichi;
	}
	
	
	
}
