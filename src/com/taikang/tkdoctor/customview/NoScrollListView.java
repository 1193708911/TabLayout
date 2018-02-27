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
public class NoScrollListView extends ListView {
	public ScrollView scrollView;
	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
		// TODO Auto-generated method stub  
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
				MeasureSpec.AT_MOST);  
		super.onMeasure(widthMeasureSpec, expandSpec);  
	}  
}  
