package com.taikang.tkdoctor.bean;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

public class MyHealthBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;//闹钟下标
	private int iconRes;//闹钟图片资源
	private String title;//闹钟主题
	private String content;//闹钟内容
	private String time;//闹钟具体时间
	private String repeat;//闹钟重复周期
	private String voicPath;//歌曲路径
	private boolean checkState;//是否选中提醒
	private boolean vibrated;//是否震动
	
	public String getVoicPath() {
		return voicPath;
	}
	public void setVoicPath(String voicPath) {
		this.voicPath = voicPath;
	}
	public boolean isVibrated() {
		return vibrated;
	}
	public void setVibrated(boolean vibrated) {
		this.vibrated = vibrated;
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
	public boolean isCheckState() {
		return checkState;
	}
	public void setCheckState(boolean checkState) {
		this.checkState = checkState;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
