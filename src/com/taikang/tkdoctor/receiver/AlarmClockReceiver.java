package com.taikang.tkdoctor.receiver;

import com.taikang.tkdoctor.service.AlarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmClockReceiver extends BroadcastReceiver {
	public AlarmClockReceiver() {

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.i("test", action);
		if (Intent.ACTION_BOOT_COMPLETED.equals(action) || AlarmService.START_ACTION.equals(action)
				|| Intent.ACTION_PACKAGE_RESTARTED.equals(action)) {
			Intent i = new Intent(context, AlarmService.class);
			context.startService(i);
		}
	}
}
