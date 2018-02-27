package com.taikang.tkdoctor.request;

import com.taikang.tkdoctor.bean.Response;

public interface NetCallback {
	public void taskSuccess(Response response);

	public void taskError(Response response);
}
