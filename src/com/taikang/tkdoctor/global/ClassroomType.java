package com.taikang.tkdoctor.global;

import com.taikang.tkdoctor.enums.ClassroomDef;

public class ClassroomType {
	public static String[] title_Eng_Arr = {
			ClassroomDef.zuixin.getBgTypeDfId(),
			ClassroomDef.yinshi.getBgTypeDfId(),
			ClassroomDef.yundong.getBgTypeDfId(),
			ClassroomDef.xinli.getBgTypeDfId(),
			ClassroomDef.liangxing.getBgTypeDfId(),
			ClassroomDef.manbing.getBgTypeDfId(),
			ClassroomDef.yangsheng.getBgTypeDfId(),
			ClassroomDef.yuer.getBgTypeDfId()
			};
	public static String[] title_Cha_Arr = { "最新","饮食", "运动", "心理", "两性", "慢病",
			"养生", "育儿"};

	public static String getTypeForEn(int index) {
		return title_Eng_Arr[index];
	}
	
	public static String getTypeForHan(int index){
		return title_Cha_Arr[index]; 
	}

}
