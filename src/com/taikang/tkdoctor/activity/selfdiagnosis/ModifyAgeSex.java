package com.taikang.tkdoctor.activity.selfdiagnosis;

import java.util.Calendar;
import java.util.Date;

import android.R.integer;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.R.color;
import com.taikang.tkdoctor.R.drawable;
import com.taikang.tkdoctor.base.BaseActivity;

@ContentView(R.layout.activity_modify_ageorsax)
public class ModifyAgeSex extends BaseActivity{
	
	@ViewInject(R.id.txtTitleText)
	private TextView mTvTitle;//标题
	
	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight;// 返回按钮
	
	@ViewInject(R.id.btn_zizhen_xiugai_man)
	private Button mBtnSelectMan;//选择男
	@ViewInject(R.id.btn_zizhen_xiugai_woman)
	private Button mBtnSelectWoman;//选择女
	@ViewInject(R.id.rl_zizhen_xiugai_date)
	private RelativeLayout mRlXiugaiDate;//选择修改生日
	@ViewInject(R.id.tv_zizhen_xiugai_date)
	private TextView mTvDate;
	
	private static  int  DATE_PICKER=0; 
	private String sex;
	private int age = 0;
	
	@Override
	protected void afterViews() {
		super.afterViews();
		mTvTitle.setText("修改性别年龄");
		initData();
	}
	
	private void initData() {
		Intent intent = getIntent();  
        sex = intent.getStringExtra("sex");
        if (sex.equals("男")) {
			mBtnSelectMan.performClick();
		}else{
			mBtnSelectWoman.performClick();
		}
	}

	@OnClick(R.id.imgTitleBack)
	private void backTop(View view){
//		onBackPressed();
		Intent intent = getIntent();
//        Bundle data = new Bundle();  
		intent.putExtra("sex",  sex);
		intent.putExtra("age", age);
		Log.e("返回结果11:", intent.getStringExtra("sex") + "||" + intent.getIntExtra("age", 0));
//        intent.putExtras(data); 
        this.setResult(0, intent);
        this.finish();
	}
	
	@OnClick(R.id.btn_zizhen_xiugai_man)
	private void selectMan(View v){
		mBtnSelectMan.setTextColor(getResources().getColor(color.white));
		mBtnSelectMan.setBackground(getResources().getDrawable(drawable.rounded_left));
		mBtnSelectWoman.setTextColor(getResources().getColor(color.zizhen_xiugai_text));
		mBtnSelectWoman.setBackground(getResources().getDrawable(drawable.rounded_unselector));
		
		sex = "男";
	}
	
	@OnClick(R.id.btn_zizhen_xiugai_woman)
	private void selectWoman(View v){
		mBtnSelectWoman.setTextColor(getResources().getColor(color.white));
		mBtnSelectWoman.setBackground(getResources().getDrawable(drawable.rounded_right));
		mBtnSelectMan.setTextColor(getResources().getColor(color.zizhen_xiugai_text));
		mBtnSelectMan.setBackground(getResources().getDrawable(drawable.rounded_unselector));
		
		sex = "女";
	}
	
	@OnClick(R.id.rl_zizhen_xiugai_date)
	private void selectDate(View v){
		showDialog(DATE_PICKER);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {  
        // TODO Auto-generated method stub  
          
        Calendar calendar=Calendar.getInstance();  
        Dialog dateDialog=new DatePickerDialog(this,   
                new DatePickerDialog.OnDateSetListener() {  
                      
                    @Override  
                    public void onDateSet(DatePicker view, int year, int monthOfYear,  
                            int dayOfMonth) {  
                    	mTvDate.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
                    	Calendar temp = Calendar.getInstance();
                    	temp.set(Calendar.YEAR, year);
                    	temp.set(Calendar.MONTH, monthOfYear+1);  
                    	temp.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        try {
							age = getAge(temp.getTime());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }  
                },   
                calendar.get(Calendar.YEAR),  
                calendar.get(Calendar.MONTH),   
                calendar.get(Calendar.DAY_OF_MONTH));  
                return dateDialog;  
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("返回结果:", sex + "||" + age);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	        // 监控返回键
	    	Intent intent = getIntent();
//	        Bundle data = new Bundle();  
			intent.putExtra("sex",  sex);
			intent.putExtra("age", age);
			Log.e("返回结果11:", intent.getStringExtra("sex") + "||" + intent.getIntExtra("age", 0));
//	        intent.putExtras(data); 
	        this.setResult(0, intent);
	    } 
	    return super.onKeyDown(keyCode, event);
	}
	
	public static int getAge(Date birthDay) throws Exception { 
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        int age = 0;
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) { 
//            throw new IllegalArgumentException( 
//                "The birthDay is before Now.It's unbelievable!"); 
        	age = 0;
        } 
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH) + 1; 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
         Log.e("系统年月日：", yearNow + "||" + monthNow + "||" + dayOfMonthNow);
        //将日期设置为出生日期
        cal.setTime(birthDay); 
        //取出出生日期的年、月、日部分  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
        Log.e("出生年月日：", yearBirth + "||" + monthBirth + "||" + dayOfMonthBirth);
        //当前年份与出生年份相减，初步计算年龄
        age = yearNow - yearBirth; 
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) { 
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        System.out.println("age:"+age); 
        return age; 
    }
}
