package com.taikang.tkdoctor.util;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class Constants {

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_S = new SimpleDateFormat("yy-MM-dd");
	public static final SimpleDateFormat DATE_FORMAT_CN = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat DATE_FORMAT_MD_CN = new SimpleDateFormat("MM月dd日");
	public static final SimpleDateFormat DATE_FORMAT_E = new SimpleDateFormat("yyyy/MM/dd E");
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_TIME_FORMAT_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat DATE_TIME_FORMAT_CN = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	public static final SimpleDateFormat DATE_TIME_FORMAT_YMDHM_CN = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	public static final SimpleDateFormat DATE_TIME_FORMAT_MDHM = new SimpleDateFormat("MM月dd日 HH:mm");
	public static final SimpleDateFormat DATE_TIME_FORMAT_MDHM2 = new SimpleDateFormat("MM月dd日 HH时mm分");
	public static final SimpleDateFormat TIME_FORMAT_HM = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat TIME_FORMAT_HMA = new SimpleDateFormat("h:mm a");
    
	
	public static final String TCMPHYSIQUE="tcmphysique";//中医体质评估
	
	public static final String DISEASE="disease";//慢病评估
	
	public static final String HEALTH="health";//健康评估
	
	public static final String HEART_DISEASE="heart_disease";//心血管疾病  report_detail 一种
	
	public static final String DIABETE="diabete";//糖尿病 //心血管疾病  report_detail 一种
	
	public static final String HIGHT_BLOOD_PRESSURE="high_blood_pressure";//高血压 //心血管疾病  report_detail 一种

	public static final String METABOLISM="metabolism";//代谢综合征 //心血管疾病  report_detail 一种
	
	public static final String[] PM25_50 =new String[]{"空气质量令人满意，基本无空气污染","各类人群可正常活动"};
	public static final String[] PM25_100=new String[]{"空气质量可接受，但某些污染物可能对极少异常敏感人群健康有较弱影响","极少数异常敏感人群应减少户外活动"};
	public static final String[] PM25_150=new String[]{"易感染人群症状有轻度加剧，健康人群出现刺激症状","儿童，老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼"};
	public static final String[] PM25_200=new String[]{"进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响","儿童、老年人及心脏病、呼吸系统疾病患者避免长时间、高强度的户外锻炼，一般人群适量减少户外运动"};
	public static final String[] PM25_250=new String[]{"心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状","儿童、老年人及心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动"};
	public static final String[] PM25_300=new String[]{"健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病","儿童、老年人和病人应停留在室内，避免体力消耗，一般人群避免户外活动"};

	
	
}
