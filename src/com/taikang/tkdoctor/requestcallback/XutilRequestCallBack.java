package com.taikang.tkdoctor.requestcallback;

public interface XutilRequestCallBack<T> {

	void onCallBackSuccess(T t,int taskId);
	void onCallBackError(Exception  e,int taskId);
	void onCallBackLoading(int progress,int taskId);
}
