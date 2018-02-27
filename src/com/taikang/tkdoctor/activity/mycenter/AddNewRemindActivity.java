package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.DialogAdapter;
import com.taikang.tkdoctor.adapter.DialogAdapter.ViewHolder;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.DaysOfWeek;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.MyUtil;
import com.umeng.socialize.utils.Log;
@ContentView(R.layout.activity_add_new_remind)
public class AddNewRemindActivity extends BaseActivity implements OnTimeChangedListener {
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.hp_rlrepeat)
	private RelativeLayout rlrepeat;
	@ViewInject(R.id.hp_repeat)
	private TextView hpRepeat;
	@ViewInject(R.id.hpRemind)
	private CheckBox cbRemind;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText1;
	private int count=0;
	private Calendar currentTime=Calendar.getInstance();
	private AlertDialog builder;
	private ListView listView;
	private DialogAdapter adapter;
	private TextView txtSelectAll;
	private View txtOk;
	private Calendar dayTime=Calendar.getInstance();
	private List<String> allList;
	private List<String> stringList;
	private WheelView min;
	private WheelView sec;
	private String date;
	private String title;
	private String content;
	private HealthPlanBean bean;
	private RemindDBDaoImp remindDao;
    private DaysOfWeek mDaysOfWeek = new DaysOfWeek(0x7f);//0x7f表示每天
    private DaysOfWeek mNewDaysOfWeek = new DaysOfWeek(0x7f);//0x7f表示每天
	
	@Override
	protected void afterViews() {
		super.afterViews();
		DialogAdapter.init();
		remindDao=new RemindDBDaoImp(this);
		bean=(HealthPlanBean) getIntent().getSerializableExtra("bean");
		title=bean.getTitle();
		txtTitleText.setText(bean.getTitle());
		init();
	}
	private void init() {

		min = (WheelView) findViewById(R.id.min);
		NumericWheelAdapter hourWheelAdapter=new NumericWheelAdapter(this,00, 23, "%02d"); 
		//ArrayWheelAdapter<String> minAdapter=new ArrayWheelAdapter<String>(this, items)
		min.setViewAdapter(hourWheelAdapter);
		min.setCyclic(true);
		min.setCurrentItem(12);
		min.addChangingListener(minutelistener);
		sec = (WheelView)findViewById(R.id.sec);
		NumericWheelAdapter minuteWheelAdapter=new NumericWheelAdapter(this,00, 59, "%02d"); 
		sec.setViewAdapter(minuteWheelAdapter);
		sec.setCyclic(true);
		sec.setCurrentItem(30);
		sec.addChangingListener(hourlistener);
		min.setVisibleItems(7);
		sec.setVisibleItems(7);
		allList=new ArrayList<String>();
		allList.add("每天");
		stringList=new ArrayList<String>();
		stringList.add("每天");
	}

	OnWheelChangedListener hourlistener=new OnWheelChangedListener() {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {


		}
	};
	OnWheelChangedListener minutelistener=new OnWheelChangedListener() {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// TODO Auto-generated method stub

		}
	};
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	@OnClick(R.id.hp_repeat)
	private void repeat(View view){
		builder=new AlertDialog.Builder(this).create();
		View dialog = layoutInflater(R.layout.date_dialogitem, null);
		initView(dialog);
		builder.setView(dialog);
		builder.show();
	}

	//初始化listview
	private void initView(View dialogView) {
		// TODO Auto-generated method stub
		listView = (ListView) dialogView.findViewById(R.id.lv_date);
		adapter=new DialogAdapter(this, MyUtil.getDateList());
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		//选中模式
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				ViewHolder vHolder = (ViewHolder)view.getTag();
				//在每次获取点击的item时将对应的checkBox状态改变，同时修改map的值
				//				vHolder.imageState.toggle();
				if(DialogAdapter.isSelected.get(position)){
					vHolder.imageState.setImageResource(0);
				}else {
					vHolder.imageState.setImageResource(R.drawable.cb_press);
				}
				//2015年11月5日06:50:04
				mNewDaysOfWeek.set(position,!DialogAdapter.isSelected.get(position));
				DialogAdapter.isSelected.put(position, !DialogAdapter.isSelected.get(position));
				adapter.notifyDataSetChanged();
			}
		});
		txtSelectAll=(TextView) dialogView.findViewById(R.id.tv_selectall);
		txtOk=(TextView) dialogView.findViewById(R.id.tv_ok);
		txtOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				allList.clear();
				stringList.clear();
				for(Map.Entry<Integer, Boolean> entry:DialogAdapter.getIsSelected().entrySet()){
					if(entry.getValue()){
						String name=MyUtil.getWhichDay(entry.getKey());
						if(count>=5){
							allList.add(name);
							if(count==5){
								stringList.add("...");
							}

						}else {
							allList.add(name);
							stringList.add(name);
						}
						count++;

					}
				}

				if(allList.size()>=6){
					hpRepeat.setText("每天");
				}else {
					//hpRepeat.setText(stringBuilder.toString());
					StringBuilder builder=new StringBuilder();
					for(String str:stringList){
						builder.append(str).append(" ");
					}
					builder.deleteCharAt(builder.length()-1);
					hpRepeat.setText(builder.toString());
				}
				count=0;
				if(builder.isShowing()){
					builder.dismiss();
				}
			}
		});
		txtSelectAll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Map<Integer, Boolean> initMap=	DialogAdapter.getIsSelected();
				for(int i=0;i<7;i++){
					initMap.put(i, true);
					mNewDaysOfWeek.set(i, true);
				}
				DialogAdapter.setIsSelected(initMap);
				adapter.notifyDataSetChanged();
			}
		});
	}

	@OnClick(R.id.hpRemind)
	private void selectState(View view){

	}

	@OnClick(R.id.hpfinish)
	private void finish(View view){
		//11-05 15:46:21.137: E/RemindDBDaoImp.insert(L:27)(17680): 插入数据...SetRemindBean [id=1446709581098, planid=33, iconRes=2130837640, title=每天酒精摄入量不超过25克, content=null, time=15:50, repeat=每天, voicPath=/system/media/audio/alarms/Beep.ogg, hour=15, minutes=50, checkState=true, vibrated=false, nextMillis=0]

		SetRemindBean remindBean=new SetRemindBean();
		LogUtils.e("完成计划ID..."+bean.getPlanid());
		remindBean.setId(System.currentTimeMillis()+"");//将插入的毫秒值作为主键
		remindBean.setPlanid(bean.getPlanid());//添加健康计划ID
		remindBean.setIconRes(R.drawable.center_icondefalt);
		remindBean.setTitle(title);
		remindBean.setContent(content);
		remindBean.setCheckState(cbRemind.isChecked());
		remindBean.setRepeat(hpRepeat.getText().toString());
		remindBean.setTime(String.format("%02d",min.getCurrentItem())+":"+String.format("%02d",sec.getCurrentItem()));
		remindBean.setVibrated(false);
		remindBean.setVoicPath(getDefaultbell());
		remindBean.setHour(min.getCurrentItem()+"");
		remindBean.setMinutes(sec.getCurrentItem()+"");
		remindBean.setNextMillis(0);
//		remindBean.setDaysOfWeek(getDaysOfWeek());
		remindBean.setDaysOfWeek(getDaysOfWeek());//默认情况下是每天
		Log.e("插入daysofWeek....."+getDaysOfWeek().toString(true));
		remindDao.insert(remindBean);
		Intent intent=new Intent();
		intent.putExtra("remindBean", remindBean);
		setResult(RESULT_OK, intent);
		finish();
	}

	
	private String getDefaultbell() {
		String ret = "";
		Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
				null, null, null, null);
		if(cursor != null){
			if(cursor.moveToFirst()){
				ret = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
			}
			cursor.close();
		}
		return ret;
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentTime.set(Calendar.MINUTE, minute);

	}
	
	
	public DaysOfWeek getDaysOfWeek(){
		mDaysOfWeek.set(mNewDaysOfWeek);
		return mDaysOfWeek;
	}
	
}
