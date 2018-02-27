package com.taikang.tkdoctor.requestcallback;

import com.taikang.tkdoctor.bean.LocationBean;

public interface BaiduLocationCallBack {
	
	void onLocatedCityCallBack(LocationBean location);

}
