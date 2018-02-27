package com.taikang.tkdoctor.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * <pre>
 * 功能描述    解决ListView与ScrollView冲突的问题
 * Author:XXX
 * Create:2015-8-13
 * Modify:
 * 
 * </pre>
 */
public class HealthNoScrollListView extends ListView {
	public ScrollView scrollView;
	public HealthNoScrollListView(Context context) {
		super(context);
	}

	public HealthNoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HealthNoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public void setParentScrollView(ScrollView scrollView){
		this.scrollView=scrollView;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setParentScrollViewScrollAble(true);
			break;
		case MotionEvent.ACTION_UP:			
			break;
		case MotionEvent.ACTION_CANCEL:
			setParentScrollViewScrollAble(true);
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	public void setParentScrollViewScrollAble(boolean scrollAble){
		scrollView.requestDisallowInterceptTouchEvent(!scrollAble);
	}
	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
		// TODO Auto-generated method stub  
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
				MeasureSpec.AT_MOST);  
		super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
}  
