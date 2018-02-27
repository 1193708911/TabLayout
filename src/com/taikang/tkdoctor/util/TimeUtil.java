package com.taikang.tkdoctor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	public static String[] WEEK = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
    public static final String ONLY_ONCE = "只响一声";
    public static final String EVERYDAY = "每天";
    public static final String WEEKDAY = "周一至周五";
    public static final String MONDAY ="每周一";
    public static final String TUESDAY ="每周二";
    public static final String WEDNESDAY ="每周三";
    public static final String THURSDAY ="每周四";
    public static final String FRIDAY ="每周五";
    public static final String SATURDAY ="每周六";
    public static final String SUNDAY ="每周日";
  
	public static final long weekTime = 7 * 24 * 60 * 60 * 1000l;
	public static final long oneDayTime = 1 * 24 * 60 * 60 * 1000l;
    
	public static final String PATTERN_YYYY_MM_DD="yyyy-MM-dd";
	/**
	 * 将毫秒转化为为yyyy年M月dd日
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatTodayString(long c) {
		// final DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 月份前面有0
		final DateFormat format = new SimpleDateFormat("yyyy年M月dd日");
		return format.format(new Date(c));
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatToday(long c) {
		final DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		return format1.format(new Date(c));
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatTodToday(long c) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		try {
			calendar.setTime(new Date(c));// 把当前时间赋给日历
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, 1); // 设置为后一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		return df.format(dBefore);
	}

	/**
	 * yyyy-MM-dd
	 * 
	 * @param SleepYesDate
	 * @return
	 */
	public static String FormatYesToday(long c) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		try {
			calendar.setTime(new Date(c));// 把当前时间赋给日历
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		return df.format(dBefore);
	}

	/**
	 * 将格式为yyyy年M月dd日的时间转化为毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long FormatDateToLong(String date) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyy年M月dd日").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c.getTimeInMillis();
	}

	/**
	 * 将格式为yyyy-MM-dd hh:mm的时间转化为毫秒数
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long FormatDateForHourToLong(String date)
			throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(new SimpleDateFormat("yyyy-M-dd hh:mm").parse(date));
		return c.getTimeInMillis();
	}

	public static String FormatDateToString(long c, String formatstr) {
		final DateFormat format1 = new SimpleDateFormat(formatstr);
		return format1.format(new Date(c));
	}

	public static String FormatDateToYear(long c) {
		final DateFormat format1 = new SimpleDateFormat("yyyy-M-dd");
		return format1.format(new Date(c));
	}

	public static long FormatDateToLong(String sdt, String formatstr)
			throws ParseException {
		final DateFormat format1 = new SimpleDateFormat(formatstr);
		return format1.parse(sdt).getTime();
	}

	public static String DataStringToString(String time) {
		Date date = new Date(Long.parseLong(time));
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return formater.format(date);
	}

	public static String DataStringToString(long time) {
		Date date = new Date(time);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return formater.format(date);
	}

	/**
	 * 格式化时间MM-dd hh:mm
	 * 
	 * @param time
	 * @return
	 */
	public static String FomartTimeForXX(long time) {
		Date date = new Date(time);
		SimpleDateFormat formater = new SimpleDateFormat("MM-dd hh:mm");
		return formater.format(date);
	}

	/**
	 * 获取年月日数组
	 * 
	 * @param temp
	 *            yyyy年mm月dd日
	 * @return string[]
	 */
	public static String[] getTimeArray(String temp) {
		if (temp.contains("年") && temp.contains("月") && temp.contains("日")) {
			temp = temp.replace("年", "#").replace("月", "#").replace("日", "#");
		}
		if (temp.contains("#")) {
			String[] time = temp.split("#");
			return time;
		}
		return null;
	}

	/**
	 * 获取当前时间的大写时间[三月十二日]
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeForCapital(long time) {
		String strTime = null;
		try {
			String str = FormatTodayString(time);
			String[] strTimeArr = getTimeArray(str);
			String month = getCapitalTime(Integer.valueOf(strTimeArr[1]));
			String day = getCapitalTime(Integer.valueOf(strTimeArr[2]));
			strTime = month + "月" + day + "日";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return strTime;
	}

	/**
	 * 根据小写数字时间获取大写数字时间
	 * 
	 * @param time
	 * @return
	 */
	private static String getCapitalTime(Integer time) {
		// TODO Auto-generated method stub
		String timeStr = null;
		switch (time) {
		case 0:
			timeStr = "零";
			break;
		case 1:
			timeStr = "一";
			break;
		case 2:
			timeStr = "二";
			break;
		case 3:
			timeStr = "三";
			break;
		case 4:
			timeStr = "四";
			break;
		case 5:
			timeStr = "五";
			break;
		case 6:
			timeStr = "六";
			break;
		case 7:
			timeStr = "七";
			break;
		case 8:
			timeStr = "八";
			break;
		case 9:
			timeStr = "九";
			break;
		case 10:
			timeStr = "十";
			break;
		case 11:
			timeStr = "十一";
			break;
		case 12:
			timeStr = "十二";
			break;
		case 13:
			timeStr = "十三";
			break;
		case 14:
			timeStr = "十四";
			break;
		case 15:
			timeStr = "十五";
			break;
		case 16:
			timeStr = "十六";
			break;
		case 17:
			timeStr = "十七";
			break;
		case 18:
			timeStr = "十八";
			break;
		case 19:
			timeStr = "十九";
			break;
		case 20:
			timeStr = "二十";
			break;
		case 21:
			timeStr = "二十一";
			break;
		case 22:
			timeStr = "二十二";
			break;
		case 23:
			timeStr = "二十三";
			break;
		case 24:
			timeStr = "二十四";
			break;
		case 25:
			timeStr = "二十五";
			break;
		case 26:
			timeStr = "二十六";
			break;
		case 27:
			timeStr = "二十七";
			break;
		case 28:
			timeStr = "二十八";
			break;
		case 29:
			timeStr = "二十九";
			break;
		case 30:
			timeStr = "三十";
			break;
		case 31:
			timeStr = "三十一";
			break;
		}
		return timeStr;
	}

	public static String getTimeForHourMin(long time) {
		String currentTimeStr = TimeUtil.FormatDateToString(time, "HH:mm");
		return currentTimeStr;
	}

	public static int getTimeForHour(long time) {
		String currentTimeStr = TimeUtil.FormatDateToString(time, "HH");
		String[] str = currentTimeStr.split(":");
		return Integer.valueOf(str[0]);
	}

	/**
	 * 将系统当前时间转化为星期
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatTodayToWeek(long c) {
		Date date = new Date(c);
		return WEEK[date.getDay()];
	}

	/**
	 * 判断时间是否是一周的开始(星期天为一周的开始)
	 * 
	 * @param longTime
	 *            需要判断的时间
	 * @return [true:是;false:不是]
	 */
	public static boolean checkIsStartWeek(long longTime) {
		boolean isStartWeek = false;
		String startTime = null;
		try {
			startTime = FormatTodayToWeek(longTime);
			if (startTime != null && startTime.equals(WEEK[0])) {
				isStartWeek = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return isStartWeek;
	}

	/**
	 * 将系统当前时间转化为星期
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatTodTodayToWeek(long c) {
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		try {
			calendar.setTime(new Date(c));// 把当前时间赋给日历
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, 1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		// return df.format(dBefore);

		return WEEK[dBefore.getDay()];
	}

	/**
	 * 将系统当前时间转化为星期前一天
	 * 
	 * @param c
	 * @return
	 */
	public static String FormatYesTodayToWeek(long c) {
		// Date date = new Date(c);
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		try {
			calendar.setTime(new Date(c));// 把当前时间赋给日历
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		// return df.format(dBefore);

		return WEEK[dBefore.getDay()];
	}

	/**
	 * 获得当天0点时间
	 * 
	 * @return
	 */
	public static int getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * 判断时间是否在一周以内(以今天为临界点)
	 * 
	 * @param dbTime
	 * @param isContainToday
	 *            是否包含今天 [false:不包含今天,true:包含今天]
	 * @return
	 */
	public static boolean checkTimeContainWeek(long dbTime,
			boolean isContainToday) {
		boolean isPass = true;
		long currentTime = System.currentTimeMillis();
		long timePoor = currentTime - dbTime;
		long timeValue = 0l;
		timeValue = weekTime;
		if (isContainToday
				&& FormatToday(currentTime).equals(FormatToday(dbTime))) {
			// 时间是今天
			return isPass;
		}
		if (!isContainToday) {
			// 如果是不按包含今天的一周数据
			timeValue += oneDayTime;
		}
		if (timePoor < 0) {
			// 时间超过今天
			isPass = false;
		} else if (timePoor > timeValue) {
			isPass = false;
		}
		return isPass;
	}

	/**
	 * 将小时数转化为：mm月dd天hh小时
	 * 
	 * @param totalHour
	 * @return
	 */
	public static String formatTimeToMonth(double totalHour) {
		String strTime = "";
		int month = 30 * 24;
		int day = 24;
		double curentMonth = 0;
		double currentDay = 0;
		double currenthour = 0;
		if (totalHour >= month) {
			curentMonth = totalHour / month;
			double poorTime = totalHour % month;
			if (poorTime >= day) {
				currentDay = poorTime / day;
				currenthour = poorTime % day;
			} else {
				currenthour = poorTime;
			}
			strTime = (int) curentMonth + "月" + (int) currentDay + "天"
					+ (int) currenthour + "小时";
		} else if (totalHour >= day && totalHour < month) {
			String dayStr = "";
			String hourStr = "";
			currentDay = totalHour / day;
			currenthour = totalHour % day;
			dayStr = (int) currentDay + "天";
			hourStr = (int) currenthour + "小时";
			if (currentDay < 10) {
				// dayStr = "0" + (int) currentDay + "天";
			}
			if (currenthour < 10) {
				// hourStr = "0" + (int) currenthour + "小时";
			}
			strTime = dayStr + hourStr;
		} else {
			currenthour = totalHour;
			strTime = (int) currenthour + "小时";
		}
		return strTime;
	}
	
	public static String getDateAndTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
				System.currentTimeMillis()));
	}
		
    public static String intToWeekday(final int i) {
        String result = "";
        switch (i) {
            case 0:
                result = "周一";
                break;
            case 1:
                result = "周二";
                break;
            case 2:
                result = "周三";
                break;
            case 3:
                result = "周四";
                break;
            case 4:
                result = "周五";
                break;
            case 5:
                result = "周六";
                break;
            case 6:
                result = "周日";
                break;
        }
        return result;
    }

    public static int weekdayToInt(String weekday) {
        int result = 0;
        if ("周一".equals(weekday)) {
            result = 0;
        } else if ("周二".equals(weekday)) {
            result = 1;
        } else if ("周三".equals(weekday)) {
            result = 2;
        } else if ("周四".equals(weekday)) {
            result = 3;
        } else if ("周五".equals(weekday)) {
            result = 4;
        } else if ("周六".equals(weekday)) {
            result = 5;
        } else if ("周日".equals(weekday)) {
            result = 6;
        }
        return result;
    }

    
    /**
     * 返回的剩余时间
     * @param time ex 14:12 
     * @param days 重复类型
     * @return
     */
    private static int[] getAfterTime(String time, String days) {
        Calendar calendar = Calendar.getInstance();
        int nowDay = (calendar.getTime().getDay() + 6) % 7;
        int nowHour = calendar.getTime().getHours();
        int nowMinute = calendar.getTime().getMinutes();
        Log.i("nowTime", "weekday:" + nowDay + ",hour:" + nowHour + ",minute:" + nowMinute);
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        int spaceHour = hour - nowHour;//剩余小时数
        int spaceMinute = minute - nowMinute;//剩余分钟数
        int spaceDay = 0;
        //这块分情况讨论
        if (days.equals(ONLY_ONCE) || days.equals(EVERYDAY)) {
            spaceDay = 0;
        } else if (days.equals(WEEKDAY)) {
            if (spaceHour < 0 || spaceMinute < 0 && spaceHour == 0) {
                if (nowDay == 5 || nowDay == 6) {
                    spaceDay = 6 - nowDay;
                } else {
                    spaceDay = 0;
                }
            } else {
                if (nowDay == 5 || nowDay == 6) {
                    spaceDay = 7 - nowDay;
                } else {
                    spaceDay = 0;
                }
            }
        } else {
            String[] str_days = days.split(" ");
            int length = str_days.length;
            int[] int_days = new int[length];

            for (int i = 0; i < length; i++) {
                int_days[i] = weekdayToInt(str_days[i]);
            }
            int lastedDayNum = 0;
            for (int i = 0; i < length; i++) {
                if (int_days[i] >= nowDay) {
                    lastedDayNum = i;
                    break;
                }
            }
            if (spaceHour < 0 || (spaceMinute < 0 && spaceHour == 0)) {
                if (lastedDayNum + 1 < length && int_days[lastedDayNum] - nowDay == 0) {
                    spaceDay = int_days[lastedDayNum + 1] - nowDay - 1;
                } else {
                    spaceDay = int_days[lastedDayNum] - nowDay - 1;
                }

            } else {
                spaceDay = int_days[lastedDayNum] - nowDay;
            }
        }
        if (spaceMinute < 0) {
            spaceMinute += 60;
            spaceHour--;
        }
        if (spaceHour < 0) {
            spaceHour += 24;
        }
        spaceDay = (spaceDay + 7) % 7;

        return new int[]{spaceDay, spaceHour, spaceMinute};

    }


    /**
     * 返回剩余的分钟数
     * @param time
     * @param days
     * @return
     */
    public static int getAfterMinutes(String time, String days) {
        int[] times = getAfterTime(time, days);
        int minutes = times[0] * 24 * 60 + times[1] * 60 + times[2]; //日  时 分
        return minutes;
    }

    public static String getAfterString(String time, String days) {
        int[] times = getAfterTime(time, days);
        int spaceDay = times[0];
        int spaceHour = times[1];
        int spaceMinute = times[2];
        String result = "";
        if (spaceDay == 0 && spaceHour != 0) {
            result = String.format("%1$d个小时%2$d分钟后响铃", spaceHour, spaceMinute);
        } else if (spaceDay == 0 && spaceHour == 0) {
            result = String.format("%1$d分钟后响铃", spaceMinute);
        } else {
            result = String.format("%1$d天%2$d个小时%3$d分钟后响铃", spaceDay, spaceHour, spaceMinute);
        }
        return result;
    }
	
	/**
	 * @param pattern
	 * @param pushDays
	 * @return
	 */
	public static String getSpecificDate(String pattern,int pushDays){
		Calendar calendar=Calendar.getInstance();
		Calendar currentCalendar=Calendar.getInstance();
		currentCalendar.setTime(new Date());
		calendar.roll(Calendar.DAY_OF_YEAR, pushDays);
		SimpleDateFormat sdf=new SimpleDateFormat(pattern, Locale.CHINA);
		String str=sdf.format(calendar.getTime());
		if(calendar.getTimeInMillis() > currentCalendar.getTimeInMillis()){
			return Constants.DATE_FORMAT.format(new Date());
		}else {
			return str;
		}
		
	}
	
	
	public static String getDateTimeByLong(long time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date(time);
		String ss=sdf.format(date);
		return ss;
	}
	
	
	
	
	
	
	
	
}
