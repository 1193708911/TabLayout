package com.taikang.tkdoctor.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.taikang.tkdoctor.activity.main.LoginActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.bean.ItemDto;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.UserBaseInfoBean;
import com.taikang.tkdoctor.bean.UserBaseInfoBean.UserBaseInfo;

public class MyUtil {
	public static void saveUser(Response response) {

		HdBsAuthUser user = new HdBsAuthUser();
		user.setUserId(response.getUId());
		user.setSessionId(response.getSessionId());
		if(response instanceof UserBaseInfoBean){
			UserBaseInfoBean userBean=(UserBaseInfoBean) response;
			UserBaseInfo info=  userBean.getResultlist().get(0);
			user.setUserPhone(info.getMobile());
			user.setBirthday(info.getBirthday());
			user.setHeight(info.getHeight());
			user.setSex(info.getSex());
			user.setUserName(info.getName());
			user.setWeight(info.getWeight());
		}

		MainApplication app = MainApplication.getInstance();
		app.setUser(user);
		app.getDbManager().saveOrUpdateUser(user);
	}

	public static void findUserById(Response response) {
		MainApplication app = MainApplication.getInstance();
		HdBsAuthUser user = new HdBsAuthUser();
		user=app.getDbManager().getUserById(response.getUId());
		app.setUser(user);
		PreferencesUtil.putString("USER_ID", response.getUId());
	}

	public static boolean isLogin() {
		return MainApplication.getInstance().getUser() != null;
	}


	public static boolean checkLogin(final Activity activity) {
		boolean isLogin = isLogin();
		if (!isLogin) {
			//			new MyAlertDialog(activity).builder().setMsg("您还未登录，请登录")
			//					.setNegativeButton("取消", null)
			//					.setPositiveButton("确定", new OnClickListener() {
			//						@Override
			//						public void onClick(View v) {
			//							activity.startActivity(new Intent(activity,
			//									LoginActivity.class));
			//						}
			//					}).show();
		}
		return isLogin;
	}

	public static List<String> getDateList() {
		List<String> list = new ArrayList<String>();
		list.add("每周一");
		list.add("每周二");
		list.add("每周三");
		list.add("每周四");
		list.add("每周五");
		list.add("每周六");
		list.add("每周日");
		return list;

	}

	public static String getWhichDay(int index) {
		String date = "";
		switch (index) {
		case 6:
			date = "每周日";
			break;
		case 0:
			date = "每周一";
			break;
		case 1:
			date = "每周二";
			break;
		case 2:
			date = "每周三";
			break;
		case 3:
			date = "每周四";
			break;
		case 4:
			date = "每周五";
			break;
		case 5:
			date = "每周六";
			break;
		}
		return date;
	}

	// 获取修改的血压值
	public static List<ItemDto> getDateHour() {
		List<ItemDto> item = new ArrayList<ItemDto>();
		for (int i = 00; i < 24; i++) {
			String code = i + "";
			item.add(new ItemDto(code, i + ""));
		}
		return item;
	}

	// 获取修改的血压值
	public static List<ItemDto> getMinute() {
		List<ItemDto> item = new ArrayList<ItemDto>();
		for (int i = 00; i < 59; i++) {
			String code = i + "";
			item.add(new ItemDto(code, i + ""));
		}
		return item;
	}
}
