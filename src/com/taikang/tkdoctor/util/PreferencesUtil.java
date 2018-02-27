package com.taikang.tkdoctor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.base.MainApplication;

public class PreferencesUtil {

	private PreferencesUtil() {
		throw new AssertionError();
	}

	public static boolean putString(String key, String value) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		return editor.commit();
	}

	public static String getString(String key) {
		return getString(key, null);
	}

	public static String getString(String key, String defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getString(key, defaultValue);
	}

	public static boolean putSerializable(String key, Serializable value) {
		//先将序列化结果写到byte缓存中，其实就分配一个内存空�?
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			// 创建对象输出流，并封装字节流  
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			//将对象序列化写入byte缓存
			oos.writeObject(value);
			if (oos != null)
				oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			LogUtils.e(e.toString(), e);
			return false;
		}
		// 将字节流编码成base64的字符窜  
		String str = new String(Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
		return putString(key, str);
	}

	public static Serializable getSerializable(String key) {
		return getSerializable(key, null);
	}

	public static Serializable getSerializable(String key, Serializable defaultValue) {
		String str = getString(key);
		if (TextUtils.isEmpty(str))
			return defaultValue;
		byte[] bytes = Base64.decode(str, Base64.DEFAULT);
		//封装到字节流  
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Serializable value = defaultValue;
		try {
			//再次封装  
			ObjectInputStream ois = new ObjectInputStream(bais);
			//返回反序列化得到的对�?
			value = (Serializable) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.e(e.toString(), e);
			return defaultValue;
		}
		return value;
	}

	public static boolean putInt(String key, int value) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	public static int getInt(String key) {
		return getInt(key, -1);
	}

	public static int getInt(String key, int defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getInt(key, defaultValue);
	}

	public static boolean putLong(String key, long value) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static long getLong(String key) {
		return getLong(key, -1);
	}

	public static long getLong(String key, long defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getLong(key, defaultValue);
	}

	public static boolean putFloat(String key, float value) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	public static float getFloat(String key) {
		return getFloat(key, -1);
	}

	public static float getFloat(String key, float defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getFloat(key, defaultValue);
	}

	public static boolean putBoolean(String key, boolean value) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}

	public static boolean getBoolean(String key) {
		return getBoolean(key, false);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getBoolean(key, defaultValue);
	}

	public static void removeKey(String key) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(key);
		editor.commit();
	}
	
	public static Map<String,?> getAll() {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
		return settings.getAll();
	}
	
	public static void clearPref() {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance()).edit();
		editor.clear();
		editor.commit();

	}
}
