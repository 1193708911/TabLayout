package com.taikang.tkdoctor.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {

	private boolean isCanScroll=true;
	public CustomViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	public void setIsCanScroll(boolean isCanScroll){
		this.isCanScroll=isCanScroll;
	}
	
	public boolean isCanScroll(){
		return isCanScroll;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	protected boolean canScroll(View arg0, boolean arg1, int arg2, int arg3, int arg4) {
		return super.canScroll(arg0, arg1, arg2, arg3, arg4);
	}
	
}
