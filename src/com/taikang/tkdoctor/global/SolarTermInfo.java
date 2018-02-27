package com.taikang.tkdoctor.global;

import java.io.Serializable;
import com.taikang.tkdoctor.util.SolarTermUtil;
import android.content.Context;


/**
 * @ClassName: SolarTermInfo
 * @Description: 气节工具类
 * @author helongfei
 * @date
 * 
 */
public class SolarTermInfo implements Serializable{

	private String solarTerm;

	private int index;
	
	private Long solarTermTime;
	
	private int solarTermMusicId;
	
//	private HomeTaskDef taskDef;
//	
//	private HomeTaskConstitution taskConstitution;
//	
//	private List<SolarTermsBean> solarList;
	
	private int id;
	
	private void initData(){
		index = SolarTermUtil.getIndex();
		solarTerm = SolarTermUtil.getSolarTerm(index);
		solarTermTime = SolarTermUtil.getSolarTermTime(index);
		solarTermMusicId = SolarTermUtil.getSolarTermMusicId(index);
		id = SolarTermUtil.getSolarTermId(index);
	}
	
	/**
	 * 
	 * @param currentTime
	 *            系统当前时间[type默认为膳食]
	 */
	public SolarTermInfo(long currentTime) {
         initData();
	}

	/**
	 * 根据当前时间获取节气
	 * 
	 * @param currentTime
	 * @return
	 */
	public String getSoralTerms() {
		return solarTerm;
	}

	/**
	 * 
	 * 
	 * 获取节气背景图
	 * 
	 */
	public int getBackgroundId() {
		int id = -1;
//		if (HomeTaskDef.DIET.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.dietImage[index];
//		} else if (HomeTaskDef.Sport.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.sportImage[index];
//		} else if (HomeTaskDef.Drinking.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.drinkingImage[index];
//		} else if (HomeTaskDef.Emotion.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.pressureImage[index];
//		} else if (HomeTaskDef.weight.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.weightImage[index];
//		} else if (HomeTaskDef.Sleep.getHomeTaskDef().equals(taskDef.getHomeTaskDef())) {
//			id = LoadLocalImge.sleepImage[index];
//		}
		return id;
	}
	
	
	public String getRecommendation(Context context){
		String recommendation = "";
//		List<SolarTermsBean> solarTerms = getSolarTermRecommendations(context);
//		if(null != solarTerms && solarTerms.size() > 0){
//			SolarTermsBean item = solarTerms.get(0);
//			if(null != item){
//				recommendation = item.getRecommendation();
//				recommendation = Utils.lengthFormat(recommendation,15);
//			}
//		}
		return recommendation;
	}

	/**
	 * 获取24节气对应的下标
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	
	public Long getSolarTermTime() {
		return solarTermTime;
	}
	
	public int getSolarTermMusicId(){
		return solarTermMusicId; 
	}
	
	public int getId(){
		return id;
	}
}