package com.taikang.tkdoctor.alarmclock;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.mycenter.HealthPlansActivity;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.util.TimeUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmNotificationManager {

	private static NotificationManager notificationManager;
	
	/*
	 * 显示状态栏通知图标
	 */
	public static void showNotification(Context context, SetRemindBean alarm){
		notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		//设置图标
		notification.icon = R.drawable.icon;
		// 表明在点击了通知栏中的"清除通知"后，此通知不清除， 经常与FLAG_ONGOING_EVENT一起使用 
		notification.flags |= Notification.FLAG_NO_CLEAR;
		// 将此通知放到通知栏的"Ongoing"即"正在运行"组中  
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		//设置通知出现的时间
		notification.when=alarm.getNextMillis();
		LogUtils.e("通知栏显示时间 when..."+alarm.getNextMillis()+"....");
		LogUtils.e("通知具体出现的时间...."+TimeUtil.getDateTimeByLong(alarm.getNextMillis()));
		Intent intent = new Intent(context, HealthPlansActivity.class);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		String title = context.getResources().getString(R.string.app_name);
		String hourStr = (alarm.hour+"").length() == 1 ? "0" + alarm.hour : alarm.hour + "";
		String minutesStr = (alarm.minutes+"").length() == 1 ? "0" + alarm.minutes : alarm.minutes + "";
		String str = hourStr + ":" + minutesStr + "\t" + alarm.title + "\t" + alarm.repeat;
		notification.setLatestEventInfo(context, title, str, pi);
//		notificationManager.notify(0, notification);
		StringBuilder sb=new StringBuilder(alarm.id);
		int size=sb.length();
		String sub=sb.substring(size-4);
		int id=Integer.parseInt(sub);
		notificationManager.notify(id, notification);
	}
	
	
	/*
	 * 取消状态栏通知图标
	 */
	public static void cancelNotification(Context context){
//		notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		if(notificationManager != null){
			notificationManager.cancelAll();
		}
	}
	
}
