package com.taikang.tkdoctor.bean;

import java.io.Serializable;

/**
 * Created by Yvan on 2015/7/6.
 */
public class AlarmClockItem implements Serializable {
    private Integer alarm_id;//闹钟ID
    private String alarm_time;//闹钟提醒时间
    private String alarm_day;//闹钟提醒重复频率
    private boolean isOn;//是否启用提醒
    private boolean isVibrated;//是否震动
    private String voicePath;//闹铃声音
    
    public String getAlarm_subject() {
		return alarm_subject;
	}

	public void setAlarm_subject(String alarm_subject) {
		this.alarm_subject = alarm_subject;
	}

	private String alarm_content;
    private String alarm_subject;
    
    
    public Integer getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(Integer alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    public String getAlarm_day() {
        return alarm_day;
    }

    public void setAlarm_day(String alarm_day) {
        this.alarm_day = alarm_day;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isVibrated() {
        return isVibrated;
    }

    public void setIsVibrated(boolean isVibrated) {
        this.isVibrated = isVibrated;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }

    public String getAlarm_content() {
        return alarm_content;
    }

    public void setAlarm_content(String alarm_content) {
        this.alarm_content = alarm_content;
    }
}
