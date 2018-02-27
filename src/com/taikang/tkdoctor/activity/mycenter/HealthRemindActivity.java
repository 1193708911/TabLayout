package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.RemindAdapter;
import com.taikang.tkdoctor.adapter.RemindAdapter.AdapterCallBack;
import com.taikang.tkdoctor.alarmclock.AlarmClockManager;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.customview.NoScrollListView;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
@ContentView(R.layout.activity_health_remind)
public class HealthRemindActivity extends BaseActivity {
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.listView)
	private NoScrollListView listRemindView;
	@ViewInject(R.id.txtTitle)
	private TextView txtTitle;
	@ViewInject(R.id.txtContent)
	private TextView txtContent;
	private List<SetRemindBean> remindList;
	private RemindAdapter adapter;
	private SetRemindBean remindBean;
	private HealthPlanBean bean;
	private RemindDBDaoImp remindDao;
	@ViewInject(R.id.scroolView)
	private ScrollView scroolView;
	@Override
	protected void afterViews() {
		super.afterViews();
		txtTitleText.setText("设置提醒");
		bean=(HealthPlanBean) getIntent().getSerializableExtra("bean");
		initData();
	}

	private void initData() {
		remindDao=new RemindDBDaoImp(this);
		remindList=	remindDao.queryBySQL("select * from alarm where alarm_id = ?", new String[]{bean.getPlanid()});
		if(remindList!=null){
			setAdapter();
		}else {
			MySetRemindUtil.insert(remindDao,bean,getDefaultbell());
			initData();
		}
		setData();
	}
	
	private String getDefaultbell() {
		String ret = "";
		Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				ret = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
			}
			cursor.close();
		}
		return ret;
	}
	
	//设置适配器
	private void setAdapter() {
		adapter=new RemindAdapter(remindList,this);
		remindList=new ArrayList<SetRemindBean>();
		listRemindView.setAdapter(adapter);
		adapter.setAdapterCallBack(new AdapterCallBack() {
			@Override
			public void onIsToggleButtonChanged(SetRemindBean bean,boolean isChecked) {
				remindDao.update(bean);
				//在修改数据之后仍旧需要处理
				if(isChecked){
				   AlarmClockManager.setAlarm(HealthRemindActivity.this,bean);
				}else{
					StringBuilder sb=new StringBuilder(bean.id);
					int size=sb.length();
					String sub=sb.substring(size-4);
				    AlarmClockManager.cancelAlarm(HealthRemindActivity.this,Integer.parseInt(sub));
				}
			}
		});
	}

	private void setData() {
		txtTitle.setText(bean.getTitle());
		txtContent.setText(bean.getContent());
	}

	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	@OnClick(R.id.txtaddRemind)
	private void addRemind(View view){
		Intent intent=new Intent(HealthRemindActivity.this,AddNewRemindActivity.class);
		intent.putExtra("bean", bean);
		startActivityForResult(intent, HealthPlansActivity.REQUESTCODE_ADD);
	}


	/**
	 * 启动页面返回
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == HealthPlansActivity.REQUESTCODE_ADD && resultCode == RESULT_OK) {
			SetRemindBean bean=(SetRemindBean) data.getSerializableExtra("remindBean");
			setListAdapter(data);
			//在这块操作
			if(bean!=null){
				AlarmClockManager.setAlarm(this, bean);
			}
		}
	}

	private void setListAdapter(Intent data) {
		remindList=	remindDao.queryBySQL("select * from alarm where alarm_id = ?", new String[]{bean.getPlanid()});
		if (remindList != null) {
			setAdapter();
		}
	}


}
