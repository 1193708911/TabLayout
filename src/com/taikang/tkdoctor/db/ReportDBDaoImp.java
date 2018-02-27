package com.taikang.tkdoctor.db;

import java.util.ArrayList;
import java.util.List;
import com.taikang.tkdoctor.bean.ReportInfoBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ReportDBDaoImp implements DBDao<ReportInfoBean> {
    private static final  String  INSERT_SQL="insert into report (report_type,report_url) values(?,?)";
	
    private Context mContext;
	
    public ReportDBDaoImp() {
		// TODO Auto-generated constructor stub
	}
    
    public ReportDBDaoImp(Context context){
    	this.mContext=context;
    }
    
	@Override
	public void insert(ReportInfoBean item) {
		// TODO Auto-generated method stub
		DBHelper helper = new DBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(INSERT_SQL, new Object[]{ item.getReport_type(),item.getReport_url()});
        db.close();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReportInfoBean> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReportInfoBean> query(boolean is_on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ReportInfoBean report) {
		// TODO Auto-generated method stub
		DBHelper helper=new DBHelper(mContext);
		SQLiteDatabase db=helper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("report_type", report.getReport_type());
		values.put("report_url", report.getReport_url());
		db.update("report", values, "report_type=?", new String[]{report.getReport_type()});
	}

	@Override
	public void update(String alarm_id, boolean isOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReportInfoBean> queryBySQL(String sql, String[] selectionArgs) {
		ArrayList<ReportInfoBean> items=new ArrayList<ReportInfoBean>();
		DBHelper helper=new DBHelper(mContext);
		SQLiteDatabase db=helper.getWritableDatabase();
		Cursor cursor=db.rawQuery(sql, selectionArgs);
		if(cursor!=null){
			while(cursor.moveToNext()){
				ReportInfoBean bean=new ReportInfoBean();
				String report_type=cursor.getString(cursor.getColumnIndex("report_type"));
				String report_url=cursor.getString(cursor.getColumnIndex("report_url"));
				bean.setReport_type(report_type);
				bean.setReport_url(report_url);
				items.add(bean);
    		}
			cursor.close();
		}
		db.close();
		return items;
	}

}
