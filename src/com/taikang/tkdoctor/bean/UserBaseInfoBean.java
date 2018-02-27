package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

public class UserBaseInfoBean extends Response implements Serializable {
 
	private ArrayList<UserBaseInfo> resultlist;
	
	private static final long serialVersionUID = 1L;
	
	public ArrayList<UserBaseInfo> getResultlist() {
		return resultlist;
	}



	public void setResultlist(ArrayList<UserBaseInfo> resultlist) {
		this.resultlist = resultlist;
	}
	
	public class UserBaseInfo extends Response{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String height;
		private String weight;
		private String birthday;
		private String sex;
		private String name;
		private String mobile;
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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
	}









}
