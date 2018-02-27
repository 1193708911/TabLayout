package com.taikang.tkdoctor.alarmclock;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
import com.taikang.tkdoctor.util.TimeUtil;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class AlarmService extends Service{
    public Vibrator mVibrator;
    private MediaPlayer mMediaPlayer;
    private SetRemindBean mCurrentAlarm;
    private TelephonyManager mTelephonyManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	// TODO Auto-generated method stub
    	mCurrentAlarm = (SetRemindBean) intent.getSerializableExtra("alarm");
		if(mCurrentAlarm != null){
			play(mCurrentAlarm);
		}
    	return super.onStartCommand(intent, flags, startId);
    }
    
	//开始播放
	private void play(final SetRemindBean alarm) {
		//播放前先停止
		stop();
		mMediaPlayer = new MediaPlayer();
		try {
			LogUtils.e("开始播放闹铃声音...."+alarm.voicPath);
			mMediaPlayer.setDataSource(alarm.voicPath);
			mMediaPlayer.prepare();
			mMediaPlayer.setLooping(true);
			mMediaPlayer.start();
			//震动
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					alarmFinish(alarm);
					stop();
				}
			}, 30*1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
		mMediaPlayer.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				mp.stop();
				mp.reset();
				mp.release();
				mMediaPlayer = null;
				return true;
			}
		});
	}

	private void alarmFinish(SetRemindBean alarm) {
		long timeMillis = AlarmClockManager.calculateAlarm(Integer.parseInt(alarm.hour), Integer.parseInt(alarm.minutes), alarm.getDaysOfWeek()).getTimeInMillis();
		LogUtils.e("alarmFinish...." + "hour.." + Integer.parseInt(alarm.hour) + "..minutes.."+ Integer.parseInt(alarm.minutes));
		LogUtils.e("周期...." + alarm.getDaysOfWeek().toString(true));
		LogUtils.e("下个该闹钟的时间inMillis..."+timeMillis);
		// 将下次响铃时间的毫秒数存到数据库
		alarm.setNextMillis(timeMillis);
		RemindDBDaoImp dbImpl = new RemindDBDaoImp(this);
		dbImpl.update(alarm);
		LogUtils.e("下次该闹钟响铃时间..." + TimeUtil.getDateTimeByLong(timeMillis));
		// 修改完成之后再去设置下一个闹钟信息
		AlarmClockManager.setNextAlarm(this);
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//初始化震动器
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // 监听来电
        mTelephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(
                mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	//停止播放
	public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        // 停止震动
        mVibrator.cancel();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stop();
		mTelephonyManager.listen(mPhoneStateListener,0);
	}
	
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String ignored) {
        	switch(state){
        	case TelephonyManager.CALL_STATE_IDLE://无任何状态
        		System.out.println("没电话");
        		if(mCurrentAlarm != null && mMediaPlayer != null && ! mMediaPlayer.isPlaying()){
        			play(mCurrentAlarm);
        		}
        		break;
        	case TelephonyManager.CALL_STATE_OFFHOOK://接起电话时
        		System.out.println("接电话");
        		stop();
        		break;
        	case TelephonyManager.CALL_STATE_RINGING://电话进来时
        		System.out.println("电话"+ignored);
        		stop();
        		break;
        	}
        }
    };
}
