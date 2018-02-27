package com.taikang.tkdoctor.bean;

import java.io.Serializable;
public class SetRemindBean implements Serializable {

	/**
	 * 设置提醒页面bean
	 */
	private static final long serialVersionUID = 1L;
	public String id;//每个闹钟的下标ID
	public String planid;// 闹钟下标 这个ID是健康计划的ID
	public int    iconRes;// 闹钟图片资源
	public String title;// 闹钟主题
	public String content;// 闹钟内容
	public String time;// 闹钟具体时间
	public String repeat;// 闹钟重复周期
	public String voicPath;// 歌曲路径
	public String hour;// 存放的具体小时
	public String minutes;// 存放的具体分钟
	public boolean checkState;// 是否选中提醒
	public boolean vibrated;// 是否震动
	public long nextMillis;// 下次提醒的时间
    public DaysOfWeek daysOfWeek;//重复周期

	@Override
	public String toString() {
		return "SetRemindBean [id=" + id + ", planid=" + planid + ", iconRes=" + iconRes + ", title=" + title
				+ ", content=" + content + ", time=" + time + ", repeat=" + repeat + ", voicPath=" + voicPath
				+ ", hour=" + hour + ", minutes=" + minutes + ", checkState=" + checkState + ", vibrated=" + vibrated
				+ ", nextMillis=" + nextMillis + "]";
	}

	
	public DaysOfWeek getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(DaysOfWeek daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}

	
	
	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public long getNextMillis() {
		return nextMillis;
	}

	public void setNextMillis(long nextMillis) {
		this.nextMillis = nextMillis;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getVoicPath() {
		return voicPath;
	}

	public void setVoicPath(String voicPath) {
		this.voicPath = voicPath;
	}

	public boolean isCheckState() {
		return checkState;
	}

	public void setCheckState(boolean checkState) {
		this.checkState = checkState;
	}

	public boolean isVibrated() {
		return vibrated;
	}

	public void setVibrated(boolean vibrated) {
		this.vibrated = vibrated;
	}

}
