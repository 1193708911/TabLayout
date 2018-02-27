package com.taikang.tkdoctor.db;

import java.util.ArrayList;
import java.util.List;
import com.taikang.tkdoctor.bean.AlarmClockItem;
import com.taikang.tkdoctor.bean.MyHealthBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Yvan on 2015/7/6.
 */
public class DBDaoImp implements DBDao<MyHealthBean> {
    private static final String INSERT_SQL = "insert into alarm_clock" +
            "(alarm_id,alarm_time,alarm_days,alarm_voice,alarm_content,alarm_title,is_on,is_vibrated) " +
            " values(?,?,?,?,?,?,?,?)";
    private Context context;

    public DBDaoImp(Context context) {
        this.context = context;
    }

    @Override
    public void insert(MyHealthBean item) {
    	Log.i("bean", "insert.."+item.getId());
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(INSERT_SQL, new Object[]
                {item.getId(), item.getTime(), item.getRepeat(),
                        item.getVoicPath(), item.getContent(),item.getTitle(), boolToInt(item.isCheckState()), boolToInt(item.isVibrated())});
        db.close();
    }

    @Override
    public void delete(int alarm_id) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("alarm_clock", "alarm_id=?", new String[]{alarm_id + ""});
        db.close();
    }

    @Override
    public List<MyHealthBean> queryAll() {
        String sql = "select * from alarm_clock";
        return queryBySQL(sql, null);
    }

    @Override
    public List<MyHealthBean> query(boolean is_on) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<MyHealthBean> items = new ArrayList<MyHealthBean>();
        //查询最近的时间点的闹钟
        Cursor cursor = db.rawQuery("select * from alarm_clock where is_on=?", new String[]{boolToInt(is_on) + ""});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MyHealthBean item = new MyHealthBean();
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
    public void update(MyHealthBean item) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("alarm_time", item.getTime());
        values.put("alarm_days", item.getRepeat());
        values.put("alarm_voice", item.getVoicPath());
        values.put("alarm_content", item.getContent());
        values.put("alarm_title", item.getTitle());
        values.put("is_on", boolToInt(item.isCheckState()));
        values.put("is_vibrated", boolToInt(item.isVibrated()));
        int isSuccess= db.update("alarm_clock", values, "alarm_id=?", new String[]{item.getId() + ""});
        Log.i("success", isSuccess+"");
        db.close();
    }

    @Override
    public void update(String alarm_id, boolean isOn) {
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_on", boolToInt(isOn));
        db.update("alarm_clock", values, "alarm_id=?", new String[]{alarm_id + ""});
        db.close();
    }

    @Override
    public List<MyHealthBean> queryBySQL(String s, String[] selectionArgs) {
        List<MyHealthBean> items = new ArrayList<MyHealthBean>();
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(s, selectionArgs);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MyHealthBean item = new MyHealthBean();
                item.setId(cursor.getString(cursor.getColumnIndex("alarm_id")));
                item.setTime(cursor.getString(cursor.getColumnIndex("alarm_time")));
                item.setRepeat(cursor.getString(cursor.getColumnIndex("alarm_days")));
                item.setVoicPath(cursor.getString(cursor.getColumnIndex("alarm_voice")));
                item.setContent(cursor.getString(cursor.getColumnIndex("alarm_content")));
                item.setTitle(cursor.getString(cursor.getColumnIndex("alarm_title")));
                item.setCheckState(intToBool(cursor.getInt(cursor.getColumnIndex("is_on"))));
                item.setVibrated(intToBool(cursor.getInt(cursor.getColumnIndex("is_vibrated"))));
                items.add(item);
            }
            cursor.close();
        }
        db.close();
        return items;
    }

    private int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    private boolean intToBool(int i) {
        return i == 1;
    }

}

