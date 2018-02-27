package com.taikang.tkdoctor.request;

import com.taikang.tkdoctor.bean.Response;

public interface RequestCallback {

	Class<? extends Response> getResultType();

	void success(Response response);

	void error(Response response);

}
