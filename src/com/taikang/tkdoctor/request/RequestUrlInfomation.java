package com.taikang.tkdoctor.request;

public class RequestUrlInfomation {
	public static String type = "http://";
	public static String type_info = "http://";
	public static String oldIp = "kaiwuhealth.com";
	public static String ip = "kdj.kaiwuhealth.com/kwhealth/server";// 外网
	public static String ip_info = "192.168.1.133:7001/hdhealth/app/doctor/doctor";// 泰康医生
	public static String oldServer = type + oldIp;
	public static String server = type + ip;
	public static String server_info=type_info+ip_info;
	
	public static final String SERVER_SUCCESS_FLAG = "0";
	
	
	/**
	 * 最新的资讯列表请求地址
	 * */
	public static String info_healthmessage="/healthmessage.do";
	
	

	/**
	 * 资讯列表请求地址
	 */
	public static String info_address = oldServer
			+ "/android/android_get_list_news/";

	public static String info_retrieve_captcha = server + "/retrieveCaptcha.do";

	public static String info_use_captcha_login = server + "/userFirstLogin.do";

	public static String info_user_edit = server + "/updateUserInfomation.do";

	public static String chat_login = oldServer + "/chat/login/";

	public static String chat_chat = oldServer + "/chat/chat/";

	public static String info_user_login = server + "/userLogin.do";

	public static String paihang = server + "/getRank.do";

	public static String get_user_info = server + "/queryUserInformation.do";

	public static String upload_data = server + "/saveMobileEvaluationInfo.do";

	public static String get_departinfo = oldServer + "/auth/get_department_list/";
	
	public static String get_yanyuan_rank = server + "/getyanyuanrank.do";
	
	public static String post_scan_code = "/dimensional.do";
	
	public static String get_measurement_record = "/YiTiKangHistory.do";//测量记录
}
