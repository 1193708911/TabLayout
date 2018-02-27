package com.taikang.tkdoctor.activity.mycenter;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.DaysOfWeek;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.db.RemindDBDaoImp;

public class MySetRemindUtil {
	//	private String id;//闹钟下标
	//	private int iconRes;//闹钟图片资源
	//	private String title;//闹钟主题
	//	private String content;//闹钟内容
	//	private String time;//闹钟具体时间
	//	private String repeat;//闹钟重复周期
	//	private String voicPath;//歌曲路径
	//	private boolean checkState;//是否选中提醒
	//	private boolean vibrated;//是否震动
	public static void insert(RemindDBDaoImp remindDao,HealthPlanBean bean,String voicePath) {
		// TODO Auto-generated method stub
		for(int i=0;i<=6;i++){
			SetRemindBean remindBean=new SetRemindBean();
//			remindBean.setId(bean.getPlanid());
			remindBean.setId(System.currentTimeMillis()+"");
			remindBean.setPlanid(bean.getPlanid());
			remindBean.setIconRes(R.drawable.center_icondefalt);
			remindBean.setTitle(bean.getTitle());
			remindBean.setContent(bean.getContent());
			remindBean.setTime(getTime(i));
			remindBean.setRepeat("每天");
			remindBean.setVoicPath(voicePath);
			remindBean.setCheckState(false);
			remindBean.setVibrated(false);
			remindBean.setHour("");
			remindBean.setMinutes("");
			remindBean.setNextMillis(0);
			remindBean.setDaysOfWeek(new DaysOfWeek(7));//每天
			//这块后期需要处理
			remindDao.insert(remindBean);
		}
	}
	
	
	private static String getTime(int i){
		String time="";
		switch (i) {
		case 0:
			time="06:30";
			break;
		case 1:
			time="08:30";
			break;

		case 2:
			time="11:30";
			break;

		case 3:
			time="12:50";
			break;

		case 4:
			time="15:30";
			break;

		case 5:
			time="17:30";
			break;

		case 6:
			time="22:00";
			break;

		default:
			break;
		}
		return time;
	}

}
