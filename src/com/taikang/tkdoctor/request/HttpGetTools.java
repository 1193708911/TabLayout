package com.taikang.tkdoctor.request;

import android.annotation.SuppressLint;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.taikang.tkdoctor.util.StringUtil;

@SuppressLint("NewApi")
public class HttpGetTools {

	/**
	 * http协议Get请求
	 * 
	 * @param path
	 *            请求地址
	 * @param parameter
	 *            参数
	 * @return String
	 */
	public static String sendGetRequest(String path, String parameter) {
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		String responseStr = null;
		try {
			if(StringUtil.isEmpty(HttpPostTools.sessionId)){
				HttpPostTools.resetLogin();
				if(StringUtil.isEmpty(HttpPostTools.sessionId)){
					return "";
				}
			}
			System.out.println("request:" + path + parameter);
			URL url = new URL(path + parameter);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setConnectTimeout(30 * 1000);
			conn.setRequestProperty("Cookie", HttpPostTools.sessionId);
			int code = conn.getResponseCode();
			if (conn.getResponseCode() == 200) {
				inputStream = conn.getInputStream();
			}else{
				inputStream = conn.getErrorStream();	
			}
				byte[] res = getByteArrFromInputStream(inputStream);
				responseStr = new String(res, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return responseStr;
	}

	public static byte[] getByteArrFromInputStream(InputStream in) {
		ByteArrayOutputStream out = new ByteArrayOutputStream(8192);
		byte[] buffer = new byte[8192];
		BufferedInputStream bis = new BufferedInputStream(in, 8192);
		int len = -1;
		try {
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toByteArray();
	}

}
