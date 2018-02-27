package com.taikang.tkdoctor.enums;

/**
 * 资讯类别 enum
 */
/**
 * @author fengzhou
 * 
 *  
最新:new 
饮食:food
运动:sport
心理:heart
两性:sex
慢病:disease
养生:health
育儿:child
 * 
 * 
 *
 */
public enum ClassroomDef {

	/**
	 * 最新
	 * */
	zuixin("new"),

	/**
	 * 饮食
	 */
	yinshi("food"),
	/**
	 * 运动
	 * */
	yundong("sport"),
	/**
	 * 心理
	 */
	xinli("heart"),
	/**
	 * 两性
	 */
	liangxing("sex"),
	/**
	 * 慢病
	 */
	manbing("disease"),
	/**
	 * 养生
	 */
	yangsheng("health"),
	/**
	 * 育儿
	 */
	yuer("child");

	private String classType;

	public String getBgTypeDfId() {
		return classType;
	}

	private ClassroomDef(String classType) {
		this.classType = classType;
	}

}
