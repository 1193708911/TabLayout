package com.taikang.tkdoctor.util;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ShowLogin {
//	private Context context;
	private static PopupWindow popup;
	public ShowLogin(){
		
	}
	
//	public static void showLoginWindow(final Context context, String title,
//			String content, View v) {
	public static void showLoginWindow(final Context context, View v) {

		View root = LayoutInflater.from(context).inflate(
				R.layout.popupwindow_layout, null);
//		root.setFocusable(false); // 这个很重要
//		root.setFocusableInTouchMode(false);
		Button okBt = (Button) root.findViewById(R.id.pop_ok);
		Button cancleBt = (Button) root.findViewById(R.id.pop_cancle);

//		TextView titleTv = (TextView) root.findViewById(R.id.title);
//		TextView contentTv = (TextView) root.findViewById(R.id.content);
//		titleTv.setText(title);
//		contentTv.setText(content);
		popup = new PopupWindow(root, 560, 370, true);
		popup.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.register_new_account));
		popup.showAtLocation(v, Gravity.TOP, 1, 400);
		popup.setFocusable(true);
		popup.setOutsideTouchable(false);
		okBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup.dismiss();
				
				changeWindowBackgroud(context, 1.0f);
				Intent intent = new Intent(context,LoginActivity.class);
				context.startActivity(intent);
			}
		});
		
		cancleBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popup.dismiss();
				
				changeWindowBackgroud(context, 1.0f);
			}
		});

	}
	
	public static void changeWindowBackgroud(Context context, float index) {

		WindowManager.LayoutParams lp = ((Activity) context).getWindow()
				.getAttributes();
		lp.alpha = index;
		((Activity) context).getWindow().setAttributes(lp);

	}
}
