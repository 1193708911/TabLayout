package com.taikang.tkdoctor.db;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.bean.SetRemindBean;

public class MyDbUpgradeListener implements DbUpgradeListener {

	@Override
	public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
		LogUtils.d(newVersion + "");
		try {
			db.dropTable(MyHealthBean.class);
			db.dropTable(SetRemindBean.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
