package com.taikang.tkdoctor.alarmclock;

import java.util.Calendar;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.DaysOfWeek;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
import com.taikang.tkdoctor.util.TimeUtil;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmClockManager {
    //这个类对闹钟进行一系列的管理操作
	private final static String TAG = "AlarmClockManager";
	//日历
	private static Calendar calendar = Calendar.getInstance();
	//闹铃管理
	private static AlarmManager am;
	
	/**
	 * 设置提示信息
	 * @param context 上下文
	 * @param hour 小时
	 * @param minute 分钟
	 */
	public static void setAlarm(Context context , SetRemindBean alarm){
		int hour=Integer.parseInt(alarm.hour);
		int minutes=Integer.parseInt(alarm.minutes);
		//这块计算至关重要 确定闹钟何时响起
		//重新设计了算法来处理对应的问题
		long timeMillis = calculateAlarm(hour, minutes, alarm.getDaysOfWeek()).getTimeInMillis();
		LogUtils.e("当前时间.."+System.currentTimeMillis());
		LogUtils.e("闹钟将于"+timeMillis+"..后提醒");
		//将下次响铃时间的毫秒数存到数据库
		alarm.setNextMillis(timeMillis);
//		AlarmHandle.updateAlarm(context, values, alarm.id);
		RemindDBDaoImp remindDb=new RemindDBDaoImp(context);
		remindDb.update(alarm);
		Toast.makeText(context, fomatTip(timeMillis) , Toast.LENGTH_SHORT).show();
		LogUtils.e("闹钟提醒的时间...."+fomatTip(timeMillis));
		//在此之前设置闹钟即将提醒的时间
		//设置闹钟
		setNextAlarm(context);
	}
	
	/**
	 * 设置下一个闹钟信息
	 * @param context 上下文
	 */
	public static void setNextAlarm(Context context){
		RemindDBDaoImp reminDb=new RemindDBDaoImp(context);
		SetRemindBean bean=reminDb.getNextAlarmClock();
		reminDb.queryAll();
		if(bean != null){
			LogUtils.e("alarmclock_id.."+bean.id+"...nextMillis.."+bean.nextMillis);
			LogUtils.e("闹钟提醒的时间.setNextAlarm====="+fomatTip(bean.nextMillis));
			LogUtils.e(TimeUtil.getDateTimeByLong(bean.nextMillis));
			Intent intent = new Intent("android.intent.action.ALARM_RECEIVER");
			intent.putExtra(AlarmClock._ID, bean.id);
			//这块的ID进行缩短处理 区后四位
			StringBuilder sb=new StringBuilder(bean.id);
			int size=sb.length();
			String sub=sb.substring(size-4);
			LogUtils.e("截取后的数据..."+sub);
			PendingIntent pi = PendingIntent.getBroadcast(context, Integer.parseInt(sub), intent, PendingIntent.FLAG_UPDATE_CURRENT);
			am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			am.set(AlarmManager.RTC_WAKEUP, bean.nextMillis, pi);
			//显示通知
			AlarmNotificationManager.showNotification(context,bean);
		}else{
			AlarmNotificationManager.cancelNotification(context);
		}
	}
	
	public static void cancelAlarm(Context context , int id ){
		Log.v(TAG, "cancelAlarm");
		Intent intent = new Intent("android.intent.action.ALARM_RECEIVER");
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pi);
		setNextAlarm(context);
	}
	
	private static String fomatTip(long timeMillis) {
	    long delta = timeMillis - System.currentTimeMillis();
        long hours = delta / (1000 * 60 * 60);
        long minutes = delta / (1000 * 60) % 60;
        long days = hours / 24;
        hours = hours % 24;
        String daySeq = (days == 0) ? "" : days+"天";
        String hourSeq = (hours == 0) ? "" : hours + "小时";
        String minSeq = (minutes == 0) ? "1分钟" : minutes + "分钟";
		return "已将闹钟设置为从现在起"+daySeq+hourSeq+minSeq+"后提醒";
	}

	public static Calendar calculateAlarm(int hour, int minute, DaysOfWeek daysOfWeek) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		int nowMinute = c.get(Calendar.MINUTE);
		if (hour < nowHour || hour == nowHour && minute <= nowMinute) {
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		int addDays = daysOfWeek.getNextAlarm(c);
		LogUtils.e("设置闹钟下次提醒的日历....calculateAlarm...."+addDays);
		if (addDays > 0)
			c.add(Calendar.DAY_OF_WEEK, addDays);
		return c;
	}
	
	
	//将时间转换成毫秒数值
	public static Long time2Millis(int hour , int minute , String repeat , String[] repeats){
		//这块需要计算不同周期获取的时间
		//repeat 可以是每天 周一
		//如果不做任何计算则认为是当天这个点提醒
		calendar.setTimeInMillis(System.currentTimeMillis()); 
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		//闹钟重复模式为 只响一次或每天
		if(repeat.equals(repeats[Alarm.ALARM_ONCE]) || repeat.equals(repeats[Alarm.ALARM_EVERYDAY])){
			//若时间已经过去，则推迟一天
			if(calendar.getTimeInMillis() - System.currentTimeMillis()<0){
				System.out.println("过时延迟一天");
				calendar.roll(Calendar.DATE, 1);
			}
		}else if(repeat.equals(repeats[Alarm.ALARM_MON_FRI])){
			//闹钟重复模式为 周一到周五
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
				//周五若时间已经过去，则推迟3天
				if(calendar.getTimeInMillis() - System.currentTimeMillis() < 0){
					calendar.roll(Calendar.DATE, 3);
				}
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				//周六
				calendar.roll(Calendar.DATE, 2);
			} else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				//周日
				calendar.roll(Calendar.DATE, 1);
			}else{
				//这块日期这块日期是周一 周二 周三 周四  
				//若时间已经过去，则推迟一天
				if(calendar.getTimeInMillis() - System.currentTimeMillis() < 0){
					System.out.println("过时延迟一天");
					calendar.roll(Calendar.DATE, 1);
				}
			}
		}
		//判断当前的repeat是什么东西
		
		
		return calendar.getTimeInMillis();
	}
	
}
