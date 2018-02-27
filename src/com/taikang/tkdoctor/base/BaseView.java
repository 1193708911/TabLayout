package com.taikang.tkdoctor.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseView implements OnClickListener {

	public Activity act;
	public View view;
	public int rId;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public BaseView(Activity act, int rId) {
		this.act = act;
		this.rId = rId;
		initBaseWidget();
	}

	private void initBaseWidget() {
		// TODO Auto-generated method stub
		view = act.getLayoutInflater().inflate(rId, null);
	}

	public View getView() {
		return view;
	}

}
