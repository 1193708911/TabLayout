package com.taikang.tkdoctor.util;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.base.MainApplication;

public class Util {

	public final static File SD_CARD_PATH = Environment.getExternalStorageDirectory();

	public static boolean isBlank(CharSequence str) {
		if (str == null || str.toString().trim().length() == 0)
			return true;
		else
			return false;
	}

	public static boolean isEmpty(Collection<?> c) {
		if (c == null || c.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * SD card path
	 */

	public static String querySDCardPath() {
		File file = SD_CARD_PATH;
		return file.getAbsolutePath();
	}

	/**
	 * Check whether there is SDCard
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMobile(String str) {
		if (str == null)
			return false;
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static boolean isTelephone(String str) {
		if (str == null)
			return false;
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^\\d{3}-?\\d{8}|\\d{4}-?\\d{8}$"); // 验证手机号  
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 检测邮箱地址是否合法
	 * @param email
	 * @return true合法 false不合法
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
//        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //�?单匹�?
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void showToast(int resId) {
		Toast.makeText(MainApplication.getInstance(), resId, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(CharSequence text) {
		Toast.makeText(MainApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
	}

	public static ProgressDialog showProgressDialog(Context context) {
		return showProgressDialog(context, true);
	}

	//检测当前有无可用网络
	public static boolean isNetworkConnected() {
		// 
		try {
			Context context = MainApplication.getInstance();
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接 
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			LogUtils.v(e.toString());
		}
		return false;
	}

	public static ProgressDialog showProgressDialog(Context context, boolean cancelable) {
		ProgressDialog pd = ProgressDialog.show(context, null, "数据拼命加载中，请耐心等待...", true, cancelable);
		if (!isNetworkConnected()) {
			Util.showToast("网络连接失败，请检查网络连接！");
			pd.dismiss();
		}
		return pd;
	}

	public static String getDeviceId() {
		Context context = MainApplication.getInstance();
		String deviceId = null;
		//IMEI(imei)，只对Android手机有效，需要权限android.permission.READ_PHONE_STATE
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//序列号(sn) ，需要权限android.permission.READ_PHONE_STATE
		deviceId = tm.getSimSerialNumber();
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//IMSI
		deviceId = tm.getSubscriberId();
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//MAC地址，需要权限android.permission.ACCESS_WIFI_STATE和android.permission.ACCESS_NETWORK_STATE
		//有些手机的mac地址会变，这个不受程序控制，每开关一个wifi，mac地址都会变一�?
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		deviceId = wifi.getConnectionInfo().getMacAddress();
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//蓝牙MAC地址。只在有蓝牙的设备上运行。并且要加入android.permission.BLUETOOTH 权限.蓝牙没有必要打开，也能读取
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter(); // Local Bluetooth adapter  
		deviceId = ba.getAddress();
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//we make this look like a valid IMEI 
		deviceId = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
				+ Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
				+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; //13 digits
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//Android ID，通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置
		//并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变
		deviceId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		if (!TextUtils.isEmpty(deviceId))
			return deviceId;
		//缓存UUID
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		String prefUniqueDeviceId = "PrefUniqueDeviceId";
//		deviceId = preferences.getString(prefUniqueDeviceId, null);
//		if (deviceId == null) {
//			deviceId = UUID.randomUUID().toString();
//			Editor editor = preferences.edit();
//			editor.putString(prefUniqueDeviceId, deviceId);
//			editor.commit();
//		}
		String prefUniqueDeviceId = "PrefUniqueDeviceId";
		deviceId = PreferencesUtil.getString(prefUniqueDeviceId, null);
		if (deviceId == null) {
			deviceId = UUID.randomUUID().toString();
			PreferencesUtil.putString(prefUniqueDeviceId, deviceId);
		}
		return deviceId;
	}

	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getAppVersion() {
		try {
			PackageManager manager = MainApplication.getInstance().getPackageManager();
			PackageInfo info = manager.getPackageInfo(MainApplication.getInstance().getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static long parseLong(String num) {
		try {
			return Long.parseLong(num);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static double parseDouble(String num) {
		try {
			return Double.parseDouble(num);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static void hideSoftInput(Activity act) {
		try {
			((InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(act
					.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	public static Calendar clearCalendarHMSS(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date;
	}

	/**
	* 获取加密后的字符
	* @param input
	* @return
	*/
	public static String stringMD5(String pw) {
		try {

			// 拿到�?个MD5转换器（如果想要SHA1参数换成”SHA1”） 
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 输入的字符串转换成字节数�? 
			byte[] inputByteArray = pw.getBytes();
			// inputByteArray是输入字符串转换得到的字节数�? 
			messageDigest.update(inputByteArray);
			// 转换并返回结果，也是字节数组，包�?16个元�? 
			byte[] resultByteArray = messageDigest.digest();
			// 字符数组转换成字符串返回 
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符 
		char[] hexDigits = { '9', '8', '7', '6', '5', '4', '3', '2', '1', '0', 'A', 'B', 'C', 'D', 'E', 'F' };
		// new�?个字符数组，这个就是用来组成结果字符串的（解释一下：�?个byte是八位二进制，也就是2位十六进制字符（2�?8次方等于16�?2次方）） 
		char[] resultCharArray = new char[byteArray.length * 2];
		// 遍历字节数组，�?�过位运算（位运算效率高），转换成字符放到字符数组中�? 
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		// 字符数组组合成字符串返回 
		return new String(resultCharArray);
	}

	public static int dpToPx(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
	
	
}
