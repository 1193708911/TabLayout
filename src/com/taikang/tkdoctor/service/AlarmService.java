package com.taikang.tkdoctor.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.taikang.tkdoctor.activity.alarmclock.AlarmActivity;
import com.taikang.tkdoctor.bean.AlarmClockItem;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.db.DBDao;
import com.taikang.tkdoctor.db.DBDaoImp;
import com.taikang.tkdoctor.util.TimeUtil;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


/**
 * 这个服务是做什么用的
 */
public class AlarmService extends Service {
    public static final String START_ACTION ="com.yvan.alarmclock.service.action.RESTART_SERVICE";
    private static final int IS_SHOULD_ALARM = 0X100;
    private static final int AFTER_ALARM = 0x101;
    private static DBDao<MyHealthBean> dbDao;
    private static List<MyHealthBean> items;
    private static MyHealthBean afterItem;

    public static volatile int minutes = -1;
    public static int alarmAfterMinutes = 10;
    private static Thread alarmThread;

    private SharedPreferences spf;
    private String interval;

    private boolean isShouldCancel;

    public AlarmService() {
    	
    }

    @Override
    public void onCreate() {
        spf = PreferenceManager.getDefaultSharedPreferences(AlarmService.this);
        dbDao = new DBDaoImp(AlarmService.this);
        sortListByNearTime();
        alarmThread = new Thread(new AlarmThread());
        alarmThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            isShouldCancel = intent.getBooleanExtra("is_should_cancel", false);
        }
        if (isShouldCancel) {
            Toast.makeText(AlarmService.this, "已取消该闹钟", Toast.LENGTH_SHORT).show();
        }

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    //    private AlarmClockItem getNearItem() {
//        int size = items.size();
//        int[] nearTimes = new int[size];
//        for (int i = 0; i < size; i++) {
//            String time = items.get(i).getAlarm_time();
//            String days = items.get(i).getAlarm_day();
//            nearTimes[i] = TimeUtil.getAfterMinutes(time, days);
//
//        }
//
//        return null;
//    }

    public static void sortListByNearTime() {
        items = dbDao.query(true);
        Collections.sort(items, new Comparator<MyHealthBean>() {
            @Override
            public int compare(MyHealthBean lhs, MyHealthBean rhs) {
                int lm = TimeUtil.getAfterMinutes(lhs.getTime(), lhs.getRepeat());
                int rm = TimeUtil.getAfterMinutes(rhs.getTime(), rhs.getRepeat());
                return lm - rm;
            }
        });
        if (items != null && items.size() > 0) {
            minutes = TimeUtil.getAfterMinutes(items.get(0).getTime(), items.get(0).getRepeat());
            Log.i("resetMinutes", minutes + "");
        } else {
            minutes = -1;
        }
        if (alarmThread != null) {
            alarmThread.interrupt();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在销毁service的时候重新启动service
        Intent intent = new Intent(START_ACTION);
        sendBroadcast(intent);
    }

    public class MyBinder extends Binder {
        public void alarmAfter() {
            try {
            	//返回剩余分钟数
                alarmAfterMinutes = Integer.parseInt(interval.substring(0, interval.indexOf("分")));
            } catch (NumberFormatException e) {
                alarmAfterMinutes = 10;
            }
            new Thread(new AfterThread()).start();
        }

//        public void cancleAfterAlarm() {
//            isShouldCancel = true;
//        }
    }

    class AfterThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(alarmAfterMinutes * 60 * 1000);
                if (!isShouldCancel) {
                    mHandler.sendEmptyMessage(AFTER_ALARM);
                } else {
                    isShouldCancel = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

   
    //这个线程用来计数处理 什么时候闹钟铃声启动
    class AlarmThread implements Runnable {

        @Override
        public void run() {
            if (items != null && items.size() > 0) {
                minutes = TimeUtil.getAfterMinutes(items.get(0).getTime(), items.get(0).getRepeat());
                Log.i("minutes", minutes + "");
                while (true) {
                    if (minutes > 80) {
                        try {
                            Thread.sleep(60 * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (items != null && items.size() > 0)
                            minutes = TimeUtil.getAfterMinutes(items.get(0).getTime(), items.get(0).getRepeat());
                    } else if (minutes > 30) {
                        try {
                            Thread.sleep(20 * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (items != null && items.size() > 0)
                            minutes = TimeUtil.getAfterMinutes(items.get(0).getTime(), items.get(0).getRepeat());
                    } else if (minutes > 5) {
                        try {
                            Thread.sleep(5 * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        minutes -= 5;
                    } else if (minutes > 0) {
                        try {
                            Thread.sleep(60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        minutes--;
                    } else if (minutes == 0) {
                        minutes = -1;
                        mHandler.sendEmptyMessage(IS_SHOULD_ALARM);
                        try {
                            Thread.sleep(60 * 1000);
                            sortListByNearTime();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_SHOULD_ALARM:
                    afterItem = items.get(0);
                    startAlarmAty();
                    break;
                case AFTER_ALARM:
                    clearNotification();
                    startAlarmAty();
                    break;
            }
        }
    };

    private void clearNotification() {
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
    }
    
    //执行这块目的是响铃
    private void startAlarmAty() {
        if (afterItem.getRepeat().equals(TimeUtil.ONLY_ONCE)) {
            dbDao.update(afterItem.getId(), false);
        }
        Intent intent = new Intent(AlarmService.this, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String ring_time = spf.getString("alarm_all_time", "20分钟");
        String keyDownOperation = spf.getString("alarm_key_down", "稍后再响");
        boolean is_silence_ring = spf.getBoolean("alarm_at_silence", true);
        interval = spf.getString("alarm_interval", "10分钟");
        intent.putExtra("ringtone_uri", afterItem.getVoicPath());//铃声uri
        intent.putExtra("is_vibrated", afterItem.isVibrated());//是否震动
        intent.putExtra("alarm_content", afterItem.getContent());//提醒内容
        intent.putExtra("alarm_title", afterItem.getTitle());
        intent.putExtra("ring_time", ring_time);//响铃时间
        intent.putExtra("alarm_key_down", keyDownOperation);//
        intent.putExtra("is_silence_ring", is_silence_ring);//
        intent.putExtra("interval", interval);
        startActivity(intent);
    }
}
