package com.taikang.tkdoctor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String  DB_NAME = "alarm_clock.db";
    private static final int     DB_VERSION = 2;
    public  static final String  TABLE_ALARM = "alarm";
    public  static final String  TABLE_REPORTS="report";
    //alarm_days 可能是一周中的某一天 也可能是启动的某几天
    //新加字段 hour minute nextMillis
    private static final String SQL_CREATEALARM =
            "create table " + TABLE_ALARM +
                    "(_id text primary key,alarm_id text," +
                    "alarm_time text,alarm_days text,alarm_voice text," +
                    "alarm_content text,alarm_title text,is_on integer,is_vibrated integer,hour text,minute text,nextmillis integer,icon_res integer,daysofweek integer)";
    //需要保存中医体质 健康评估 慢病评估 共六种报告
    private static final String SQL_REPORTS="create table " + TABLE_REPORTS +
            "(_id integer primary key autoincrement,report_type text,report_url text)";
    
    private static final String SQL_UPDATEALARM =
            "alter table " + TABLE_ALARM + " add alarm_content text";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATEALARM);
        db.execSQL(SQL_REPORTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_UPDATEALARM);
    }
}
