package com.taikang.tkdoctor.alarmclock;

public class AlarmClock {
	
	public static final String TABLE_NAME = "alarm";
	// 所有列名
	public static final String _ID = "_id";// 闹钟下标
	public static final String ALARM_ID = "alarm_id";// 健康计划ID
	public static final String ALARM_TIME = "alarm_time";//闹钟提醒时间
	public static final String ALARM_DAYS = "alarm_days";//闹钟提醒周期
	public static final String ALARM_VOICE = "alarm_voice";//闹钟声音
	public static final String ALARM_CONTENT = "alarm_content";//闹钟提醒声音
	public static final String ALARM_TTILE = "alarm_title";
	public static final String IS_ON = "is_on";
	public static final String IS_VIBRATED = "is_vibrated";
	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";
	public static final String NEXTMILLIS = "nextmillis";
	public static final String ICON_RES = "icon_res";
	public static final String DAYSOFWEEK="daysofweek";
	// 解题
	public final static int CANCEL_NUM_MODE = 0;
	// 摇晃手机
	public final static int CANCEL_SHAKE_MODE = 1;
	// 闹铃重复方式
	// 响一次
	public final static int ALARM_ONCE = 0;
	// 周一到周五
	public final static int ALARM_MON_FRI = 1;
	// 每天
	public final static int ALARM_EVERYDAY = 2;
	// 周一
	public final static int ALARM_MONDAY = 3;
	// 周二
	public final static int ALARM_TUESDAY = 4;
	// 周三
	public final static int ALARM_WEDNESDAY = 5;
	// 周四
	public final static int ALARM_THURSDAY = 6;
	// 周五
	public final static int ALARM_FRIDAY = 7;
	// 周六
	public final static int ALARM_SATURDAY = 8;
	// 周日
	public final static int ALARM_SUNDAY = 9;
	

}
