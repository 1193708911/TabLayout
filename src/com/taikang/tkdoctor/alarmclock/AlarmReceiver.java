package com.taikang.tkdoctor.alarmclock;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.activity.mycenter.HealthPlansActivity;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
import com.taikang.tkdoctor.util.TimeUtil;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

public class AlarmReceiver extends BroadcastReceiver {

	// 服务
	private AlarmService alarmService;
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		LogUtils.e("定时发送广播  广播接收器..." + intent.getAction());
		this.mContext=context;
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			AlarmClockManager.setNextAlarm(context);
		} else {
			// 进入处置页面
			// 进入健康计划列表页面
			// Intent deal = new Intent(context, HealthPlansActivity.class);
			// deal.putExtra(AlarmClock._ID,
			// intent.getStringExtra(AlarmClock._ID));
			// deal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// context.startActivity(deal);
			// 在这块进行处理操作
            String  id=intent.getStringExtra(AlarmClock._ID);
            initAlarmClock(id);
		}
	}

	// 新的信息
	private void initAlarmClock(String id) {
		LogUtils.e("initAlaramClock()....alarmid....." + id);
		if (id != null && !"0".equals(id)) {
			LogUtils.e("定点进入健康计划页面..");
			RemindDBDaoImp reminDb = new RemindDBDaoImp(mContext);
			final SetRemindBean bean = reminDb.getAlarmClockById(id);
			// 开启服务，监听电话状态和音乐播放
			Intent intent = new Intent(mContext, AlarmService.class);
			intent.putExtra("alarm", bean);
			mContext.startService(intent);
		}
	}


}
