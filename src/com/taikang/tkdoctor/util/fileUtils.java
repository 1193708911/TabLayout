package com.taikang.tkdoctor.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Environment;

public class fileUtils {

	private final static String FILE_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/kjd/net/";

	/**
	 * 资讯离线数据缓存地址
	 */
	private final static String CLASS_CACHE_PAHR = "/kjd/class/";

	@SuppressLint("SimpleDateFormat")
	public static String DataStringToString(String time) {
		Date date = new Date(Long.parseLong(time));
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		return formater.format(date);
	}

	public static String getSystemTime() {
		return DataStringToString(System.currentTimeMillis() + "");
	}

	public static void writeDataToLocalFile(String data, String fileName) {
		try {
			String filename = FILE_DIR + "/" + fileName + ".txt";
			File dir = new File(FILE_DIR);
			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(filename);
			fos.write(data.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static String findOffLine(String filename) {
		String value = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().toString()
					+ CLASS_CACHE_PAHR;

			File file = new File(path + filename);

			if (!file.exists()) {

			} else {
				try {
					StringBuilder sb = new StringBuilder();
					String s = "";
					BufferedReader br = new BufferedReader(new FileReader(file));

					while ((s = br.readLine()) != null) {
						sb.append(s + "\n");
					}

					br.close();
					value = sb.toString();
				} catch (Exception l) {

				}
			}

		}
		return value;
	}

	public static void saveOffLine(String filename, String data) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().toString()
					+ CLASS_CACHE_PAHR;
			File f = new File(path);
			if (!f.exists()) {
				f.mkdir();
			}

			File file = new File(path + filename);
			if (!file.exists()) {

			} else {
				file.delete();
			}

			try {
				file.createNewFile();

				FileOutputStream myOutput = new FileOutputStream(path
						+ filename);
				BufferedWriter bw = new BufferedWriter(new FileWriter(path
						+ filename));
				char[] buffer = new char[2048];
				BufferedReader br = new BufferedReader(new StringReader(data));
				int length = 0;
				while ((length = br.read(buffer)) != -1) {
					bw.write(buffer, 0, length);
				}

				bw.close();
			} catch (Exception l) {

			}
		}

	}
}
