package com.taikang.tkdoctor.config;

/**
 * 
 * @ClassName: ServerConfig
 * @Description: 服务器地址
 * @author dongchunlin
 * @date 2015年10月16日
 *
 */
public class ServerConfig {
	public static final String SERVER_PATH = "http://office.aragoncs.com:7001/hdhealth";
	public static final String SERVER_PATH_INFO = "http://office.aragoncs.com:7001/";
//	public static final String SERVER_PATH = "http://192.168.1.133:7001/hdhealth";
//	public static final String SERVER_PATH_S = "http://192.168.1.133:7001/hdhealth";
//	public static final String SERVER_PATH = "http://192.168.2.176:8080/hdhealth";
//	public static final String SERVER_PATH = "http://192.168.191.2:8080/hdhealth";
	public static final String URL_PATH = SERVER_PATH + "/app/doctor/doctor/";
//	public static final String URL_PATH_S = SERVER_PATH_S + "app/doctor/doctor/";
//	public static final String URL_PATH_HTML = SERVER_PATH + "html/diabetes/";
	public static final String URL_VERSION = "1.0";
	public static final String SERVER_SUCCESS_FLAG = "0";//成功
	public static final String SERVER_FAIL_FLAG = "1";  //失败
	public static final String SERVER_SESSION_EXPIRED_FLAG = "2"; //session过期
	public static final String SERVER_PASSWORD_ERROR_FLAG = "3"; //密码错误
	public static final String SERVER_PERSONAL_NO_FLAG = "4"; //未填写用户信息
	
	public static final String evalNumberId_Tangniao = "?evalNumberId=2&uid=";
	public static final String evalNumberId_Daixie = "?evalNumberId=1&uid=";
	public static final String evalNumberId_Gaoxueya = "?evalNumberId=3&uid=";
	public static final String evalNumberId_Xinxueguan = "?evalNumberId=4&uid=";
	public static final String evalNumberId_yajiankang = "?evalNumberId=5&uid=";
	
	public static final String URL_PATH_LOGIN = "login.do";
	public static final String URL_PATH_USER = "userbase.do";
	public static final String URL_PATH_MYCOLLECTION = "myCollection.do";

	public static final String URL_PATH_INFO="healthmessage.do";//资讯信息请求

	public static final String URL_PATH_EVAL_TCM="evaOfTraChiMedicine.do";//中医体质评测问题列表数据
	public static final String URL_PATH_UPDATE_HEALTHOPLAN = "healthPlan.do";
	
	public static final String URL_PATH_PM25="pm.do";//获取PM25
	
	public static final String URL_PATH_BANNER="banner.do";//首页广告

	public static final String URL_PATH_HEALTH_AUTOGNOSIS="healthAutognosis.do";//自诊症状

	public static final String URL_PATH_MYASSESS="myAssess.do";//我的评测结果 
	public static final String URL_PATH_ASSESS="assess.do";//健康风险评估
	
	public static final String URL_PATH_SEASONTHREAPY="healthmessage.do";//节气养生功能
	
	public static final String URL_INFO_DETAIL="hdhealth/message/messagedetail.html?";
	public static final String HTMLURL_INFO_DETAIL="/message/messagedetail.html?";
	//首页广告链接单独处理
	public static final String HTMLSERVER_PATH = "http://192.168.1.133:7001/";
	public static final String HTML_XIEYI="/common/service/agreement.html";
	
	
}
