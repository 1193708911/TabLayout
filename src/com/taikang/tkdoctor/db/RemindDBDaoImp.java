package com.taikang.tkdoctor.db;

import java.util.ArrayList;
import java.util.List;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.alarmclock.AlarmClock;
import com.taikang.tkdoctor.bean.DaysOfWeek;
import com.taikang.tkdoctor.bean.SetRemindBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 这个类进行相关的查询等工作
 */
public class RemindDBDaoImp implements DBDao<SetRemindBean> {
    private Context context;
    public RemindDBDaoImp(Context context) {
        this.context = context;
    }

    //添加一个闹钟
    @Override
    public void insert(SetRemindBean item) {
        LogUtils.e("插入数据..."+item.toString());
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        long success=db.insert(AlarmClock.TABLE_NAME, null, createValues(item));
        LogUtils.e("插入成功..."+success);
        db.close();
    }
    
    //删除一个闹钟
    @Override
    public void delete(int _id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AlarmClock.TABLE_NAME, "_id=?", new String[]{_id+""});
        db.close();
    }

    
    //删除所有闹钟
    public void deleteAllAlarmClock(){
    	DBHelper helper=new DBHelper(context);
    	SQLiteDatabase db=helper.getWritableDatabase();
    	db.delete(AlarmClock.TABLE_NAME, null, null);
    }
    
    //修改一条闹钟信息
    @Override
    public void update(SetRemindBean item) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int isSuccess= db.update(AlarmClock.TABLE_NAME, createValues(item), "_id=?", new String[]{item.getId() + ""});
        Log.e("修改数据...", isSuccess+""+"...修改ID....."+item.getId());
        db.close();
    }

    //修改一个闹钟的属性
    @Override
    public void update(String _id, boolean isOn) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_on", boolToInt(isOn));
        db.update(AlarmClock.TABLE_NAME, values, "_id=?", new String[]{_id + ""});
        db.close();
    }
    
    //通过_id来查找对应的闹钟信息
    public List<SetRemindBean> getAlarmClock(String _id){
    	DBHelper helper= new DBHelper(context);
    	SQLiteDatabase db=helper.getWritableDatabase();
    	String sql="select * from alarm where _id=?";
    	String[] selectionArgs=new String[]{_id};
    	return queryBySQL(sql, selectionArgs);
    }
    
    //通过_id来查找对应的闹钟信息
    public SetRemindBean getAlarmClockById(String _id){
    	DBHelper helper= new DBHelper(context);
    	SQLiteDatabase db= helper.getWritableDatabase();
    	String sql="select * from alarm where _id=?";
    	Cursor cursor=db.rawQuery(sql, new String[]{_id});
    	SetRemindBean item=null;
    	if(cursor!=null){
    		if(cursor.moveToFirst()){
    			 item=new SetRemindBean();
    			 item.setId(cursor.getString(cursor.getColumnIndex(AlarmClock._ID)));
                 item.setPlanid(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_ID)));//健康计划ID
                 item.setTime(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TIME)));
                 item.setRepeat(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_DAYS)));
                 item.setVoicPath(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_VOICE)));
                 item.setContent(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_CONTENT)));
                 item.setTitle(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TTILE)));
                 item.setCheckState(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_ON))));
                 item.setVibrated(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_VIBRATED))));
                 item.setHour(cursor.getString(cursor.getColumnIndex(AlarmClock.HOUR)));
                 item.setMinutes(cursor.getString(cursor.getColumnIndex(AlarmClock.MINUTE)));
                 item.setNextMillis(cursor.getLong(cursor.getColumnIndex(AlarmClock.NEXTMILLIS)));
                 item.setIconRes(cursor.getInt(cursor.getColumnIndex(AlarmClock.ICON_RES)));
                 DaysOfWeek daysofWeek=new DaysOfWeek(cursor.getInt(cursor.getColumnIndex(AlarmClock.DAYSOFWEEK)));
                 item.setDaysOfWeek(daysofWeek);
    	    }		
    	}
    	db.close();
    	LogUtils.e("闹钟信息...."+_id);
    	return item;
    }
    
    
    //查询出所有闹钟
    @Override
    public List<SetRemindBean> queryAll() {
        String sql = "select * from alarm";
        return queryBySQL(sql, null);
    }
    
    /**
     * 获取最近时间的闹钟
     * @return
     */
    public SetRemindBean getNextAlarmClock(){
    	String sql = "select * from alarm where is_on='1' order by nextmillis asc";
    	DBHelper helper= new DBHelper(context);
    	SQLiteDatabase db=helper.getWritableDatabase();
    	Cursor cursor=db.rawQuery(sql, null);
    	SetRemindBean item=null;
    	if(cursor!=null){
    		if(cursor.moveToFirst()){
    			 item=new SetRemindBean();
    			 item.setId(cursor.getString(cursor.getColumnIndex(AlarmClock._ID)));
                 item.setPlanid(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_ID)));//健康计划ID
                 item.setTime(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TIME)));
                 item.setRepeat(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_DAYS)));
                 item.setVoicPath(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_VOICE)));
                 item.setContent(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_CONTENT)));
                 item.setTitle(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TTILE)));
                 item.setCheckState(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_ON))));
                 item.setVibrated(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_VIBRATED))));
                 item.setHour(cursor.getString(cursor.getColumnIndex(AlarmClock.HOUR)));
                 item.setMinutes(cursor.getString(cursor.getColumnIndex(AlarmClock.MINUTE)));
                 item.setNextMillis(cursor.getLong(cursor.getColumnIndex(AlarmClock.NEXTMILLIS)));
                 item.setIconRes(cursor.getInt(cursor.getColumnIndex(AlarmClock.ICON_RES)));
                 DaysOfWeek daysofWeek=new DaysOfWeek(cursor.getInt(cursor.getColumnIndex(AlarmClock.DAYSOFWEEK)));
                 item.setDaysOfWeek(daysofWeek);
//               LogUtils.e("getNextAlarmClock()..."+item.toString());
    		}
    	}
    	return item;
    }
    
   
    @Override
    public List<SetRemindBean> query(boolean is_on) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<SetRemindBean> items = new ArrayList<SetRemindBean>();
        Cursor cursor = db.rawQuery("select * from alarm where is_on=?", new String[]{boolToInt(is_on) + ""});
        if (cursor != null) {
            while (cursor.moveToNext()) {
            	SetRemindBean item = new SetRemindBean();
                item.setId(cursor.getString(cursor.getColumnIndex("alarm_id")));
                item.setTime(cursor.getString(cursor.getColumnIndex("alarm_time")));
                item.setRepeat(cursor.getString(cursor.getColumnIndex("alarm_days")));
                item.setVoicPath(cursor.getString(cursor.getColumnIndex("alarm_voice")));
                item.setContent(cursor.getString(cursor.getColumnIndex("alarm_content")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("alarm_title")));
                item.setCheckState(is_on);
                item.setVibrated(intToBool(cursor.getInt(cursor.getColumnIndex("is_vibrated"))));
                items.add(item);
            }
            cursor.close();
        }
        db.close();
        return items;
    }

    @Override
    public List<SetRemindBean> queryBySQL(String s, String[] selectionArgs) {
        List<SetRemindBean> items = new ArrayList<SetRemindBean>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(s, selectionArgs);
        if (cursor != null) {
            while(cursor.moveToNext()) {
            	SetRemindBean item = new SetRemindBean();
                item.setId(cursor.getString(cursor.getColumnIndex(AlarmClock._ID)));
                item.setPlanid(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_ID)));//健康计划ID
                item.setTime(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TIME)));
                item.setRepeat(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_DAYS)));
                item.setVoicPath(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_VOICE)));
                item.setContent(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_CONTENT)));
                item.setTitle(cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TTILE)));
                item.setCheckState(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_ON))));
                item.setVibrated(intToBool(cursor.getInt(cursor.getColumnIndex(AlarmClock.IS_VIBRATED))));
                item.setHour(cursor.getString(cursor.getColumnIndex(AlarmClock.HOUR)));
                item.setMinutes(cursor.getString(cursor.getColumnIndex(AlarmClock.MINUTE)));
                item.setNextMillis(cursor.getLong(cursor.getColumnIndex(AlarmClock.NEXTMILLIS)));
                DaysOfWeek daysofWeek=new DaysOfWeek(cursor.getInt(cursor.getColumnIndex(AlarmClock.DAYSOFWEEK)));
                item.setDaysOfWeek(daysofWeek);
//                LogUtils.e("查询所有Alarm..id"+cursor.getString(cursor.getColumnIndex(AlarmClock._ID)));
//                LogUtils.e("查询所有Alarm..days"+cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_DAYS)));
//                LogUtils.e("查询所有Alarm..time"+cursor.getString(cursor.getColumnIndex(AlarmClock.ALARM_TIME)));
//                LogUtils.e("查询所有Alarm..millis"+cursor.getString(cursor.getColumnIndex(AlarmClock.NEXTMILLIS)));
//                LogUtils.e("查询所有Alarm...计划频次..."+daysofWeek.toString(false));
//                LogUtils.e("=============================================");
                items.add(item);
            }
            cursor.close();
        }
        db.close();
        return items;
    }
    
    
    
    public ContentValues createValues(SetRemindBean bean){
    	ContentValues values=new ContentValues();
    	values.put(AlarmClock._ID, bean.getId());
    	values.put(AlarmClock.ALARM_ID, bean.getPlanid());
    	values.put(AlarmClock.ALARM_TIME, bean.getTime());
    	values.put(AlarmClock.ALARM_DAYS, bean.getRepeat());
    	values.put(AlarmClock.ALARM_CONTENT, bean.getContent());
    	values.put(AlarmClock.ALARM_TTILE, bean.getTitle());
    	values.put(AlarmClock.ALARM_VOICE, bean.getVoicPath());
    	values.put(AlarmClock.IS_ON, boolToInt(bean.isCheckState()));
    	values.put(AlarmClock.IS_VIBRATED, boolToInt(bean.isVibrated()));
    	values.put(AlarmClock.NEXTMILLIS, bean.getNextMillis());
    	values.put(AlarmClock.HOUR, bean.getHour());
    	values.put(AlarmClock.MINUTE, bean.getMinutes());
    	values.put(AlarmClock.ICON_RES, bean.getIconRes());
    	values.put(AlarmClock.DAYSOFWEEK, bean.getDaysOfWeek().getCoded());//存放daysofweek 放的是一个整数值 后面计算用到
    	return values;
    }
    

    private int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    private boolean intToBool(int i) {
        return i == 1;
    }

}

