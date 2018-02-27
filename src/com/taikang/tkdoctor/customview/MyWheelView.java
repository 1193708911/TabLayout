package com.taikang.tkdoctor.customview;


import java.util.Calendar;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taikang.tkdoctor.R;
/**
 * 本类主要实现弹出时间选择框
 * 该时间选择框具有联动性
 * @author fuqiang
 *
 */
public class MyWheelView  {

	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private OnTimeChangeClickListenner listenner;
	private int mYear=1996;
	private int mMonth=0;
	private int mDay=1;

	LinearLayout ll;
	private TextView tv1,tv2;

	private View view=null;

	boolean isMonthSetted=false,isDaySetted=false;
	private Context context;
	private int n_year;
	private int n_month;
	public MyWheelView(Context context){
		this.context=context;

	}

	public  View getDataPick() {
		Calendar c = Calendar.getInstance();
		int norYear = c.get(Calendar.YEAR);
		int curYear = mYear;
		int curMonth =mMonth+1;
		int curDate = mDay;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_libwheel, null);
		year = (WheelView) view.findViewById(R.id.wheelYear);
		NumericWheelAdapter numericWheelAdapter1=new NumericWheelAdapter(context,1950, norYear); 
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);//是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.wheelMonth);
		NumericWheelAdapter numericWheelAdapter2=new NumericWheelAdapter(context,1, 12, "%02d"); 
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.wheelDay);
		initDay(curYear,curMonth);
		day.setCyclic(true);
		year.setVisibleItems(5);//设置显示行数
		month.setVisibleItems(5);
		day.setVisibleItems(5);

		//		year.setCurrentItem(curYear - 1950);
		//		month.setCurrentItem(curMonth - 1);
		//		day.setCurrentItem(curDate - 1);
		year.setCurrentItem(30);
		month.setCurrentItem(30);
		day.setCurrentItem(30);
		//		n_year = year.getCurrentItem() + 1950;//年
		//		n_month = month.getCurrentItem() + 1;//月
		updateTime();
		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			updateTime();
		}
	};
	private void updateTime() {
		n_year = year.getCurrentItem() + 1950;//年
		n_month = month.getCurrentItem() + 1;//月
		initDay(n_year,n_month);
		String birthday=new StringBuilder().append((year.getCurrentItem()+1950)).append("年")
				.append((month.getCurrentItem() + 1) < 10 ? "0" + (month.getCurrentItem() + 1) : (month.getCurrentItem() + 1))
				.append("月").append(((day.getCurrentItem()+1) < 10) ? "0" + (day.getCurrentItem()+1) : (day.getCurrentItem()+1)).append("日").toString();
		//			tv1.setText("年龄             "+calculateDatePoor(birthday)+"岁");
		if(listenner!=null){
			listenner.onTimeChangeClick(birthday);
		}
	}
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	public void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter=new NumericWheelAdapter(context,1, getDay(arg1, arg2), "%02d");
		day.setViewAdapter(numericWheelAdapter);
	}


	//	/**
	//	 * 根据日期计算年龄
	//	 * @param birthday
	//	 * @return
	//	 */
	//	public static final String calculateDatePoor(String birthday) {
	//		try {
	//			if (TextUtils.isEmpty(birthday))
	//				return "0";
	//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	//			Date birthdayDate = sdf.parse(birthday);
	//			String currTimeStr = sdf.format(new Date());
	//			Date currDate = sdf.parse(currTimeStr);
	//			if (birthdayDate.getTime() > currDate.getTime()) {
	//				return "0";
	//			}
	//			long age = (currDate.getTime() - birthdayDate.getTime())
	//					/ (24 * 60 * 60 * 1000) + 1;
	//			String year = new DecimalFormat("0.00").format(age / 365f);
	//			if (TextUtils.isEmpty(year))
	//				return "0";
	//			return String.valueOf(new Double(year).intValue());
	//		} catch (ParseException e) {
	//			e.printStackTrace();
	//		}
	//		return "0";
	//	}
	public interface OnTimeChangeClickListenner{
		public void onTimeChangeClick(String content);
	}
	public void setOnTimeChangeClickListenner(OnTimeChangeClickListenner listenner){
		this.listenner=listenner;
	}
}

