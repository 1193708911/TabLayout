package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "TK_BS_AUTH_USER")
public class HdBsAuthUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3271553563680038933L;
	@Id(column = "USER_ID")
	private String userId;//用户ID
//	@Column(column = "USER_PWD")
//	private String userPwd;//密码
	@Column(column = "SESSION_ID")
	private String sessionId;//sessionId
	@Column(column = "USER_PHONE")
	private String userPhone;//用户手机号
	/*
	 * 合并字段
	 */
	//添加基本信息
	@Column(column = "USER_NAME")
	private String userName;//用户名
	@Column(column="USER_HEIGHT")
	private String height;
	@Column(column="USER_WEIGHT")
	private String weight;
	@Column(column="USER_BIRTHDAY")
	private String birthday;
	@Column(column="USER_SEX")
	private String sex;


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public String getUserPwd() {
//		return userPwd;
//	}
//
//	public void setUserPwd(String userPwd) {
//		this.userPwd = userPwd;
//	}


	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
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

}
