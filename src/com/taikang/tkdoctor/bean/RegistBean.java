package com.taikang.tkdoctor.bean;

public class RegistBean {
	
	private String phone; //手机号
	private String pwd; //密码
	private String checkCod;//验证码
	private String requestCode;//邀请码
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCheckCod() {
		return checkCod;
	}
	public void setCheckCod(String checkCod) {
		this.checkCod = checkCod;
	}
	public String getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

}
