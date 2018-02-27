package com.taikang.tkdoctor.activity.alarmclock;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.service.AlarmService;
import com.taikang.tkdoctor.util.AlarmPlay;

public class AlarmActivity extends Activity {
    private static final int IS_SHOULD_END_RINGTONE = 0x101;
    private AlarmPlay ap;
    private AlarmService.MyBinder mBinder;
    private TextView tv_up_slide_to_shut_down;
    private String ring_time;
    private String keyDownOperation;
    private String alarm_content;
    private Vibrator vibrator;//震动管理
    private boolean silence_ring = true;//静音时响铃

    private Timer mTimer;//计时器，用以判断响铃时间

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (AlarmService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private RelativeLayout rl;
    private TranslateAnimation tranAnim;
    private String interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        rl = (RelativeLayout) findViewById(R.id.rl_alarm);
        tv_up_slide_to_shut_down = (TextView) findViewById(R.id.tv_up_slide_to_shut_down);
    }

    private void initData() {
        Intent i = getIntent();

        boolean isVibrated = i.getBooleanExtra("is_vibrated", false);//是否开启启震
        if (isVibrated) {
            startVibrate();
        }

        boolean is_silence_ring = i.getBooleanExtra("is_silence_ring", true);//静音是否响铃
        if (!is_silence_ring) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            int system_volume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
            //静音状�??
            if (system_volume == 0) {
                silence_ring = false;
            }
        }

        if (silence_ring) {
            String url = i.getStringExtra("ringtone_uri");
            if (url != null) {
                Uri uri = Uri.parse(url);
                ap = new AlarmPlay(this, uri);
                ap.startAlarm();
            }
        }

        alarm_content = i.getStringExtra("alarm_content");//显示提醒内容
        if (alarm_content != null) {
            ((TextView) findViewById(R.id.tv_show_content)).setText(alarm_content);
        }
        ring_time = i.getStringExtra("ring_time");
        keyDownOperation = i.getStringExtra("alarm_key_down");
        interval = i.getStringExtra("interval");
    }

    private void initEvent() {

        Intent intent = new Intent(this, AlarmService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);//绑定服务

        TextView tv_alarm_after = (TextView) findViewById(R.id.tv_alarm_after);
        if (interval == null) {
            interval = "10分钟";
        }
        tv_alarm_after.setText(interval + "后\n再提醒");
        tv_alarm_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmAfter();
            }
        });

       /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            findViewById(R.id.digitalClock).setVisibility(View.VISIBLE);
        }
*/
        animForTV();//向上滑动提示动画
        endAlarmTask();

        registerReceiver(keyDownReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    /**
     * �?启震�?
     */
    private void startVibrate() {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(
                new long[]{50, 100, 75, 200, 100, 300, 200, 400}, 0);
    }

    private void animForTV() {
        AnimationSet anim = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(false);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        Animation tranAnimation = new TranslateAnimation(0, 0, 0, -50f);
        tranAnimation.setDuration(1500);
        tranAnimation.setFillAfter(false);
        tranAnimation.setRepeatCount(Animation.INFINITE);
        tranAnimation.setRepeatMode(Animation.REVERSE);
        anim.addAnimation(alphaAnimation);
        anim.addAnimation(tranAnimation);
        anim.setDuration(1500);
        anim.setFillAfter(false);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        tv_up_slide_to_shut_down.setAnimation(anim);
        tv_up_slide_to_shut_down.startAnimation(anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vibrator != null) {
            vibrator.cancel();
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (ap != null) {
            ap.stopAlarm();
        }
        unregisterReceiver(keyDownReceiver);
        unbindService(conn);
    }

    private float startY;
    private float space;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastSpace = space;
                space = startY - event.getY();
                animForWhole();
                break;
            case MotionEvent.ACTION_UP:
                if (space > 300) {
                    cancelAlarm();
                    space = 0;
                } else {
                    rl.setAlpha(1);
                    tranAnim.setFillAfter(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private float lastSpace;

    private void animForWhole() {
        tranAnim = new TranslateAnimation(0, 0, -lastSpace, -space);
        tranAnim.setFillAfter(true);
        tranAnim.setDuration(200);

        rl.setAlpha(1 - space / 600);
        rl.setAnimation(tranAnim);
        rl.startAnimation(tranAnim);
        if (space > 300) {
            tv_up_slide_to_shut_down.setText("松开手指关闭闹钟");
        } else {
            tv_up_slide_to_shut_down.setText("∧\n向上滑动关闭闹钟");
        }
    }

    /**
     * 稍后再响
     */

    private void alarmAfter() {
        Toast.makeText(this, interval + "后再提醒", Toast.LENGTH_SHORT).show();
        mBinder.alarmAfter();
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra("is_should_cancel", true);
        PendingIntent pIntent = PendingIntent.getService(this, 0, intent, 0);
        showOnNotification(interval + "后再提醒,点击可取消该闹钟", "稍后再响闹钟", pIntent);
        this.finish();
    }

    /**
     * 关闭闹钟
     */
    private void cancelAlarm() {
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU:
            case KeyEvent.KEYCODE_POWER:
            case KeyEvent.KEYCODE_SEARCH:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return handleKeyEvent(keyCode);
        }
        return true;
    }

    /**
     * 处理按键事件
     */
    private boolean handleKeyEvent(int keyCode) {
        //Log.i("handleKeyEvent", "keyCode:" + keyCode);
        if (keyDownOperation == null) {
            keyDownOperation = "稍后再响";
        }
        Log.i("keyDownOperation", keyDownOperation);
        if ("稍后再响".equals(keyDownOperation)) {
            alarmAfter();

        } else if ("关闭".equals(keyDownOperation)) {
            cancelAlarm();

        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP ||
                keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return false;
        }
        return true;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_SHOULD_END_RINGTONE:
                	//Notification点击跳转之后页面
//                    Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
//                    PendingIntent pIntent = PendingIntent.getActivity(AlarmActivity.this, 0, intent, 0);
//                    showOnNotification("响铃总时间：" + ring_time, "您错过了闹钟", pIntent);
//                    cancelAlarm();
                    break;
            }
        }
    };

    private void showOnNotification(String content, String title, PendingIntent intent) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentText(content);
        builder.setContentTitle(title);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setTicker("闹钟提示");
        builder.setContentIntent(intent);
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = builder.getNotification();
        }
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        manager.notify(0, notification);
    }

    private void endAlarmTask() {
        if (ring_time == null) {
            ring_time = "20分钟";
        }
        final int time = stringToInt(ring_time);
        if (time == -1) {
            return;
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(IS_SHOULD_END_RINGTONE);
            }
        }, time);
    }

    private int stringToInt(String ring_time) {
        if (ring_time.equals("永不停止")) {
            return -1;
        } else {
            int minutes = Integer.parseInt(ring_time.substring(0, ring_time.indexOf("分")));
            return minutes * 60 * 1000;
        }
    }

    private BroadcastReceiver keyDownReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (reason != null) {

                    if (reason.equals(SYSTEM_HOME_KEY)) {
                        //监听home键
                        handleKeyEvent(0);
                    } else if (reason.equals(SYSTEM_HOME_KEY_LONG)) {
                        //监听长按home
                        handleKeyEvent(0);
                    }
                }
            }
        }
    };
}
