package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.DialogAdapter;
import com.taikang.tkdoctor.adapter.DialogAdapter.ViewHolder;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.ItemDto;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.AddHealthPlanBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.MyUtil;

@ContentView(R.layout.activity_add_plan)
public class AddPlanActivity extends BaseActivity implements NetCallback   {
	public static final String RECEIVERACTION="CHECKEDACTION";
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	@ViewInject(R.id.hp_content)
	private TextView hpContent;
	@ViewInject(R.id.hp_repeat)
	private TextView hpRepeat;
	@ViewInject(R.id.hp_title)
	private TextView hpTitle;
	@ViewInject(R.id.hpRemind)
	private CheckBox hpRemind;
	public static Map<Integer, Boolean> intMap;
	private DialogAdapter adapter;
	private AlertDialog builder;
	private ListView listView;
	private Calendar currentTime=Calendar.getInstance();
	private Calendar dayTime=Calendar.getInstance();
	private TextView txtSelectAll;
	private TextView txtOk;
	private int count=0;
	private static StringBuilder allBuilder=new StringBuilder();
	private WheelView min;
	private WheelView sec;
	private ArrayList<String> allList;
	private ArrayList<String> stringList;
	private List<ItemDto> itemList;
	private HealthPlanBean currentBean;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		currentBean=(HealthPlanBean) getIntent().getSerializableExtra("bean");
		txtTitleText.setText("添加计划");
		DialogAdapter.init();
		init();
	}
	private void init() {
		itemList=new ArrayList<ItemDto>();
		itemList=MyUtil.getDateHour();
		min = (WheelView) findViewById(R.id.min);
		NumericWheelAdapter hourWheelAdapter=new NumericWheelAdapter(this,00, 23, "%02d");
		min.setViewAdapter(hourWheelAdapter);
		min.setCyclic(true);
		min.setCurrentItem(12);
		min.addChangingListener(minutelistener);
		sec = (WheelView)findViewById(R.id.sec);
		NumericWheelAdapter minuteWheelAdapter=new NumericWheelAdapter(this,00, 59, "%02d"); 
		sec.setViewAdapter(minuteWheelAdapter);
		sec.setCyclic(true);
		sec.addChangingListener(hourlistener);
		sec.setCurrentItem(30);
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
			// TODO Auto-generated method stub


		}
	};
	OnWheelChangedListener minutelistener=new OnWheelChangedListener() {

		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			// TODO Auto-generated method stub

		}
	};
	private HashMap<String, String> parMap;
	@OnClick(R.id.hp_rlrepeat)
	private void repeatRate(View view){
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
				DialogAdapter.isSelected.put(position, !DialogAdapter.isSelected.get(position));
				adapter.notifyDataSetChanged();
			}
		});
		txtSelectAll=(TextView) dialogView.findViewById(R.id.tv_selectall);
		txtOk=(TextView) dialogView.findViewById(R.id.tv_ok);
		txtOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				allList.clear();
				stringList.clear();
				for(Map.Entry<Integer, Boolean> entry:DialogAdapter.getIsSelected().entrySet()){
					if(entry.getValue()){
//						String name=MyUtil.getWhichDay(entry.getKey());
						String name=entry.getKey()+"";
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

				if(allList.size()>6){
					hpRepeat.setText("每天");
				}else {
				  //hpRepeat.setText(stringBuilder.toString());
					StringBuilder builder=new StringBuilder();
					for(String str:allList){
						builder.append(MyUtil.getWhichDay(Integer.valueOf(str))).append(" ");
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
				}
				DialogAdapter.setIsSelected(initMap);
				adapter.notifyDataSetChanged();
			}
		});
	}

	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	@OnClick(R.id.hpfinish)
	private void finish(View view){
		if(checkInput()){
			sendAddHealthRequest();
		}
	}
	private void sendAddHealthRequest() {
		// TODO Auto-generated method stub
		AddHealthPlanBiz addHealthPlanBiz=new AddHealthPlanBiz();
		addHealthPlanBiz.setCallback(this);
		addHealthPlanBiz.addHealthPlan(
	    hpTitle.getText().toString(), 
	    hpContent.getText().toString(),String.format("%02d",min.getCurrentItem())+":"+String.format("%02d",sec.getCurrentItem()), 
	    allList);

	}
	public boolean checkInput(){
		if(TextUtils.isEmpty(hpTitle.getText().toString())){
			ShowLongToast("主题不能为空");
			return false;
		}
		if(TextUtils.isEmpty(hpContent.getText().toString())){
			ShowLongToast("内容不能为空");
			return false;
		}

		return true;

	}
	/**
	 * 判断为哪一天
	 * @param type
	 * @return
	 */
	private String getWhichDay(String type){
		String index="";
		if("星期一".equals(type)){
			index="1";
		}
		if("星期二".equals(type)){
			index="2";
		}
		if("星期三".equals(type)){
			index="3";
		}
		if("星期四".equals(type)){
			index="4";
		}
		if("星期五".equals(type)){
			index="5";
		}
		if("星期六".equals(type)){
			index="6";
		}
		if("星期天".equals(type)){
			index="0";
		}
		return type;
	}
	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		this.finish();
		
	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		
	}

}
