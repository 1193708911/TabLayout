package com.taikang.tkdoctor.util;

import java.util.Map;

import android.R.string;


public class StringUtil {

	public static boolean isEmpty(String value){
		if(value == null){
			return true;
		}else if (value.equals("")){
			return true;
		}
		return false;
	}
	
	public static String getShowMessage(Object message, String expectMessage, String showMessage){
		if(null == message){
			message = "";
		} else {
			message = getShowMessage(message.toString(), expectMessage, showMessage); 
		}
		return message.toString();
	}
	
	public static String getShowMessage(String message, String expectMessage, String showMessage){
		if (!isEmpty(message) && message.trim().equals(expectMessage.trim())){
			message = showMessage;
		} else {
			message = "";
		}
		return message;
	}
}
