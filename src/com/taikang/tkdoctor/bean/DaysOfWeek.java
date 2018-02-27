package com.taikang.tkdoctor.bean;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.util.Log;

public final class DaysOfWeek implements Serializable{
	private static final long serialVersionUID = 1L;

	//利用daysofWeek来处理对应的数据
	//这块最后做到的是只需要和repeate对应起来就可以了
	private static int[] DAY_MAP = new int[] { Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY,Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY, };

	// Bitmask of all repeating days
	private int mDays;
    
	public DaysOfWeek(int days) {
		mDays = days;
//		Log.e("look", "mDays...." + mDays);
	}

	public String toString(boolean showNever) {
		StringBuilder ret = new StringBuilder();
		// no days
		if (mDays == 0) {
//			Log.e("look", "toString()....0" + mDays);
			return showNever ? "永不" : "";
		}
		LogUtils.e("0x7f.."+0x7f);
		// every day
		if (mDays == 0x7f) {
//			Log.e("look", "toString().....7" + mDays);
			return "每天";
		}
		// count selected days
		int dayCount = 0; 
//		int days = mDays;
		int days = mDays;
		while (days > 0) {
//			LogUtils.e("days & 1..."+(days & 1));
			if ((days & 1) == 1)
				dayCount++;
			    days>>= 1;
		}
		// short or long form?
		DateFormatSymbols dfs = new DateFormatSymbols();
//		for(String s:dfs.getShortWeekdays()){
//			LogUtils.e("shortWeekDays..."+s);
//		}
//		
//		for(String ss:dfs.getWeekdays()){
//			LogUtils.e("weekDays..."+ss);
//		}
		String[] dayList = (dayCount > 1) ? dfs.getShortWeekdays() : dfs.getWeekdays();
		for (String str : dayList) {
//			Log.e("look", "str..." + str);
		}
		// selected days
		for (int i = 0; i < 7; i++) {
			if ((mDays & (1 << i)) != 0) {
				ret.append(dayList[DAY_MAP[i]]);
				dayCount -= 1;
				if (dayCount > 0)
					ret.append(",");
			}
		}
//		Log.e("look", "...result..." + ret.toString());
		return ret.toString();
	}

	private boolean isSet(int day) {
		return ((mDays & (1 << day)) > 0);
	}

	public void set(int day, boolean set) {
		if (set) {
			mDays |= (1 << day);
		} else {
			mDays &= ~(1 << day);
		}
	}

	public void set(DaysOfWeek dow) {
		mDays = dow.mDays;
	}

	public int getCoded() {
		return mDays;
	}

	public boolean[] getBooleanArray() {
		boolean[] ret = new boolean[7];
		for (int i = 0; i < 7; i++) {
			 ret[i] = isSet(i);
		}
		return ret;
	}

	public boolean isRepeatSet() {
		return mDays != 0;
	}

	public int getNextAlarm(Calendar c) {
		LogUtils.e("getNextAlaram..."+mDays);
		if (mDays == 0) {
			return -1;
		}
		int today = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
		int day = 0;
		int dayCount = 0;
		for (; dayCount < 7; dayCount++) {
			day = (today + dayCount) % 7;
			if (isSet(day)) {
				break;
			}
		}
		return dayCount;
	}

}
