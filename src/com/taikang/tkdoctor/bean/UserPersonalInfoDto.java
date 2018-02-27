package com.taikang.tkdoctor.bean;

public class UserPersonalInfoDto extends Response{

	/**
	 * 返回值说明：
	 */
	private static final long serialVersionUID = 1L;
	private String mobile;
	private String name;
	private String birthday;
	private String  sex;
	private String height;
	private String weight;
	private String	usertype;
	private String	breakfirst;	
	private String	Lunch;	
	private	String	dinner;	
	private	String	sleep;	
	private	String	sugar;	
	private	String	hemoglobin;
	private String sugarAfter;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getBreakfirst() {
		return breakfirst;
	}
	public void setBreakfirst(String breakfirst) {
		this.breakfirst = breakfirst;
	}
	public String getLunch() {
		return Lunch;
	}
	public void setLunch(String lunch) {
		Lunch = lunch;
	}
	public String getDinner() {
		return dinner;
	}
	public void setDinner(String dinner) {
		this.dinner = dinner;
	}
	public String getSleep() {
		return sleep;
	}
	public void setSleep(String sleep) {
		this.sleep = sleep;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public String getHemoglobin() {
		return hemoglobin;
	}
	public void setHemoglobin(String hemoglobin) {
		this.hemoglobin = hemoglobin;
	}
	public String getSugarAfter() {
		return sugarAfter;
	}
	public void setSugarAfter(String sugarAfter) {
		this.sugarAfter = sugarAfter;
	}	

}
