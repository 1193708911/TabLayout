package com.taikang.tkdoctor.bean;


public interface IContent {
	final static String FACE_IMAGE_NAME = "face.jpg";
	
	final static String WAITE = "请稍后 ...";
	final static String SUCCESSFUL = "successful";
	//登录模块
	final static String EMAIL_EXAMPLE = "email@example.com";
	final static String EMAIL_ERROR = "请输入正确的邮箱";
	final static String GET_CAPTCHA = "获取验证码";
	final static String CAPTCHA_EMPTY = "请输入验证码";
	final static String CAPTCHA_ERROR = "请输入正确的验证码";
	final static String LOGIN_FAILED = "登录失败";
	final static String EMAIL_NOT_EXIST_CH = "此邮箱不存在";
	final static String EMAIL_NOT_EXIST_EN = "The user does not hava email.";
	final static String EMAIL_OR_CAPTCHA_ERROR_CH = "邮箱或者验证码错误";
	final static String EMAIL_OR_CAPTCHA_ERROR_EN = "Wrong username or captcha.";
	final static String CAPTHA_EXPIRED_CN="验证码已过期，请重新获取验证码";
	final static String CAPTHA_EXPIRED_EN="The captcha has expired.";

	//个人信息编辑
	final static String EDIT_SUCCESSFUL = "修改成功";
	final static String EDIT_FAILED = "修改失败";
	final static String DEPARTMENT_NOT_EXIST_CH = "此部门不存在";
	final static String DEPARTMENT_NOT_EXIST_EN = "The department does not exist.";
	final static String ERROR_PHONE = "请输入正确的电话号码";
	final static String ERROR_DEPARTMENT = "请输入正确的部门";
	
	static final String SAVE_SUCCESSFUL = "保存成功";
	static final String SAVE_FAILED = "保存失败";
	
	static final String NO_NET_MESSAGE = "请检查网络是否正常";
	
}
