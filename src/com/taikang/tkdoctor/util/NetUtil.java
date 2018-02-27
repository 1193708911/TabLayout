package com.taikang.tkdoctor.util;

import com.taikang.tkdoctor.bean.IContent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetUtil {

	public static boolean checkNetAndAlert(Context context){
		if(!checkNet(context)){
			Toast.makeText(context, IContent.NO_NET_MESSAGE, Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}

	public static boolean checkNet(Context context) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		return mNetworkInfo == null ? false : true;
	}
	
	public static boolean isNetworkAvailable(Context ContextObj) {
		ConnectivityManager ConnMgr = (ConnectivityManager) ContextObj
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo NetInfo = ConnMgr.getActiveNetworkInfo();
        
		if (NetInfo == null || !NetInfo.isConnected()) {
			return false;
		}
		return true;
	}
	
}
