package com.taikang.tkdoctor.request;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.taikang.tkdoctor.util.StringUtil;

@SuppressLint("NewApi")
public class HttpPostTools {

	public static String sessionId = "";

	public static final int CONNECTION_OUT_TIME = 2 * 60 * 1000;

	public static String getSessionId() {
		if (StringUtil.isEmpty(sessionId)) {
			resetLogin();
		}
		return sessionId;
	}
	/**
	 * python服务器
	 * @param path
	 * @param dataJson
	 * @return
	 */
	public static String sendOldPost(String path, String dataJson) {
		OutputStream outStream = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		String responseStr = null;
		try {
			System.out.println("All path:" + path + dataJson);
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(CONNECTION_OUT_TIME);
			conn.setReadTimeout(CONNECTION_OUT_TIME);
			conn.setUseCaches(false);
			byte[] data = dataJson.getBytes();
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try {
				if (!StringUtil.isEmpty(sessionId)) {
					conn.setRequestProperty("Cookie", sessionId);
				}
				outStream = conn.getOutputStream();
				outStream.write(data);
				String cookieValue = conn.getHeaderField("Set-Cookie");
				if (cookieValue != null) {
					sessionId = cookieValue.substring(0,
							cookieValue.indexOf(";"));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("连接失败");
			} finally{
				System.out.println("登陆返回码..."+conn.getResponseCode());
			}
			if (conn.getResponseCode() == 200) {
				inputStream = conn.getInputStream();
			} else {
				inputStream = conn.getErrorStream();
			}
			System.out.println("登录结果码:"+conn.getResponseCode());
			byte[] res = getByteArrFromInputStream(inputStream);
			responseStr = new String(res, "utf-8");
		    System.out.println("返回的数据据..."+responseStr);
			// responseStr = inputStream2String(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return responseStr;
	}
	public static ConnectionResponseModel postOld(CommunicatorModel communModel) {
		ConnectionResponseModel model = null;
		try {
			if (StringUtil.isEmpty(sessionId)) {
				resetLogin();
			}
			HttpPost request = new HttpPost(communModel.getBizAdd());
			request.addHeader("Cookie", HttpPostTools.sessionId);
			BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params,
					CONNECTION_OUT_TIME);
			HttpConnectionParams.setSoTimeout(params, CONNECTION_OUT_TIME);
			request.setParams(params);
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			String value = dataToJsonObj(communModel).toString();
			value=communModel.getKey()+":{"+value+"}";
			System.out.println("jsonData:" + value);
			nameValuePair.add(new BasicNameValuePair("jsonStr" ,
					value));
			request.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = new DefaultHttpClient().execute(request);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("postCode= " + code);
			if (code == HttpStatus.SC_OK) {
				model = getResponseInfo(response);
			} else if (code == 499) {
				// Cookie过期需重新登录
				sessionId = "";
				resetLogin();
				if (!HttpPostTools.sessionId.isEmpty()) {
					model = post(communModel);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	public static String sendPost(String path, String params) {
		OutputStream outStream = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		String responseStr = null;
		try {

			final String dataJson = String.format("jsonStr=%s",params);
			System.out.println("登录上传数据..."+dataJson.toString());
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(CONNECTION_OUT_TIME);
			conn.setReadTimeout(CONNECTION_OUT_TIME);

			conn.setUseCaches(false);
			byte[] data = dataJson.getBytes();
			conn.setRequestProperty("Content-Length",String.valueOf(data.length));
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try {
				if (!StringUtil.isEmpty(sessionId)) {
					conn.setRequestProperty("Cookie", sessionId);
				}
				outStream = conn.getOutputStream();
				outStream.write(data);
				/*
				 * String cookieValue = conn.getHeaderField("Set-Cookie"); if
				 * (cookieValue != null) { sessionId = cookieValue.substring(0,
				 * cookieValue.indexOf(";")); }
				 */
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("连接失败");
			}
			if (conn.getResponseCode() == 200) {
				inputStream = conn.getInputStream();
			} else {
				inputStream = conn.getErrorStream();
			}
			System.out.println("登录结果码:" + conn.getResponseCode());
			byte[] res = getByteArrFromInputStream(inputStream);
			responseStr = new String(res, "utf-8");
			System.out.println(responseStr);
			// responseStr = inputStream2String(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
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

	public static String sendPost(String path, Map<String, String> parms,
			String uploadFilePath) {
		HttpClient httpClient = null;
		HttpResponse response = null;
		String result = "";
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(path);
			if (StringUtil.isEmpty(HttpPostTools.sessionId)) {
				resetLogin();
			}
			httppost.addHeader("Cookie", HttpPostTools.sessionId);
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
					20000);

			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			if (!StringUtil.isEmpty(uploadFilePath)) {
				File file = new File(uploadFilePath);
				if (file.exists()) {
					multipartEntity.addPart("icon", new FileBody(file));
				}
			}
			for (String key : parms.keySet()) {
				multipartEntity.addPart(key, new StringBody(parms.get(key),
						"text/plain", Charset.forName("UTF-8")));
			}
			// multipartEntity.addPart("jsonStr", multipartEntity.);
			System.out.println("mutipartEntity"+multipartEntity.toString());
			httppost.setEntity(multipartEntity);
			response = httpClient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			result = EntityUtils.toString(response.getEntity());
			System.out.println("上传结果"+result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	public static String sendPost(String path, String params,Map<String, String> prams,
			String uploadFilePath) {
		HttpClient httpClient = null;
		HttpResponse response = null;
		String result = "";
		try {
			httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(path);
			if (StringUtil.isEmpty(HttpPostTools.sessionId)) {
				System.out.println("sessionId为空，需要重新登录");
				resetLogin();
			}
			httppost.addHeader("Cookie", HttpPostTools.sessionId);
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
					20000);

			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			if (!StringUtil.isEmpty(uploadFilePath)) {
				File file = new File(uploadFilePath);
				if (file.exists()) {
					multipartEntity.addPart("icon", new FileBody(file));
				}
			}
			multipartEntity.addPart("jsonStr", new StringBody(params.toString()));
			for (String key : prams.keySet()) {
				multipartEntity.addPart(key, new StringBody(prams.get(key),
						"text/plain", Charset.forName("UTF-8")));
			}
			// multipartEntity.addPart("jsonStr", multipartEntity.);
			httppost.setEntity(multipartEntity);
			response = httpClient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			result = EntityUtils.toString(response.getEntity());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	/**
	 * 
	 * @param CommunicatorModel
	 *            封装了请求参数和URl地址的的对象
	 * @return ConnectionResponseModel 封装了服务器返回的数据
	 */
	public static ConnectionResponseModel post(CommunicatorModel communModel) {
		String responseStr = null;
		ConnectionResponseModel model = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(communModel.getBizAdd());
		System.out.println("sessionId..."+HttpPostTools.sessionId);
		post.addHeader("Cookie", HttpPostTools.sessionId);
		try {
			if (StringUtil.isEmpty(sessionId)) {
				resetLogin();
			}
			JSONObject value = dataToJsonObj(communModel);
			JSONObject dataObj = new JSONObject();
			dataObj.put(communModel.getKey(), value);
			dataObj.put("sessionId", HttpPostTools.sessionId);

			BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params,
					CONNECTION_OUT_TIME);
			HttpConnectionParams.setSoTimeout(params, CONNECTION_OUT_TIME);
			post.setParams(params);
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			// TODO 打印语句
			System.out.println("post flag");

			nameValuePair.add(new BasicNameValuePair("jsonStr", dataObj.toString()));
			post.setEntity(new UrlEncodedFormEntity(nameValuePair));

			HttpResponse response = client.execute(post);
			// responseStr = inputStream2String(inputStream);
			int code = response.getStatusLine().getStatusCode();
			System.out.println("postCode= " + code);
			if (code == HttpStatus.SC_OK) {
				model = getResponseInfo(response);
				System.out.println("返回成功..."+model.getBodyInfo()+"..code.."+model.getStatusCode());
			} else if (code == 499) {
				// Cookie过期需重新登录
				sessionId = "";
				resetLogin();
				if (!HttpPostTools.sessionId.isEmpty()) {
					model = post(communModel);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 重新登录
	 */
	public static void resetLogin() {
//		Person person = DBManager.getInstance().getPersonInfo();
//        System.out.println("用户名："+person.getEmail()+"..密码："+person.getPassword()); 
//		JSONObject personStr = new JSONObject();
//		try {
//			personStr.put("username", person.getEmail());
//			personStr.put("password", person.getPassword());
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		System.out.println("重新登录的数据...."+personStr.toString());
//		String result = sendPost(RequestUrl.info_user_login,personStr.toString());
//		System.out.println(result.toString());
//		try {
//			JSONTokener jsonParser = new JSONTokener(result);
//			JSONObject jperson = (JSONObject) jsonParser.nextValue();
//			if (jperson.getInt("status") == 1) {
//				HttpPostTools.sessionId = jperson.getString("sessionId");
//				person.setPassword(jperson.getString("password"));
//				DBManager.getInstance().addOrUpdatePersonInfo(person);
//				CurrentUserInfo.getInstance().setmPerson(person);
//			}
//		} catch (Exception e) {
//
//		}
	}

	public static String dataToAdd(CommunicatorModel data) {
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		if (data.getBodyData().get("JUST_AN_ADD").equals(null)) {
			Hashtable<String, Object> htData = data.getBodyData();
			Iterator<Entry<String, Object>> iterator = htData.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> each = (Map.Entry<String, Object>) iterator
						.next();
				sb.append(each.getKey()).append("=").append(each.getValue())
						.append("&");
			}
			sb.setLength(sb.length() - 1);
		} else {
			sb.append(data.getBodyData().get("JUST_AN_ADD"));
		}

		return sb.toString();
	}

	public static JSONObject dataToJsonObj(CommunicatorModel data)
			throws JSONException {

		JSONObject objRtn = new JSONObject();
		Hashtable<String, Object> htData = data.getBodyData();
		Iterator<Entry<String, Object>> iterator = htData.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Object> each = (Map.Entry<String, Object>) iterator
					.next();
			objRtn.put(each.getKey(), each.getValue());
		}
		return objRtn;
	}

	public static ConnectionResponseModel getResponseInfo(
			HttpResponse httpResponse) throws IllegalStateException,
			IOException {
		ConnectionResponseModel modelRtn = new ConnectionResponseModel();
		Header[] header = httpResponse.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			modelRtn.getHeaderInfo().put(header[i].getName(),
					header[i].getValue());
		}

		int statusCode = httpResponse.getStatusLine().getStatusCode();
		modelRtn.setStatusCode(statusCode);

		String gzipInfo = modelRtn.getHeaderInfo().get("Content-Encoding");
		if (gzipInfo != null && gzipInfo.equals("gzip")) {
			InputStream is = httpResponse.getEntity().getContent();
			is = new GZIPInputStream(is);
			modelRtn.setBodyInfo(inputStream2String(is));
		} else {
			modelRtn.setBodyInfo(EntityUtils.toString(httpResponse.getEntity()));
		}
		return modelRtn;
	}

	public static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuilder buffer = new StringBuilder();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

}
