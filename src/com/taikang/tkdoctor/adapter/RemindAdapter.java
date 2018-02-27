package com.taikang.tkdoctor.adapter;

import java.util.List;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.util.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

//内部适配器
public class RemindAdapter extends BaseAdapter{
	private List<SetRemindBean> remindsList;
	private Context context;
	private AdapterCallBack callback;
	public RemindAdapter(List<SetRemindBean> remindList,Context context) {
		// TODO Auto-generated constructor stub
		this.remindsList=remindList;
		this.context=context;
	}
    
	public void setAdapterCallBack(AdapterCallBack adapterCallBack){
		this.callback=adapterCallBack;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return remindsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return remindsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView == null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.list_setremind, null);
			convertView.setTag(viewHolder);
			viewHolder.imgTitle=(ImageView) convertView.findViewById(R.id.imgTitle);
			viewHolder.txtTime=(TextView) convertView.findViewById(R.id.txtTime);
			viewHolder.txtDate=(TextView) convertView.findViewById(R.id.txtDate);
			viewHolder.mcheckBox=(CheckBox) convertView.findViewById(R.id.mCheckBox);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final SetRemindBean bean=remindsList.get(position);
		viewHolder.txtTime.setText(bean.getTime());
		viewHolder.txtDate.setText(bean.getRepeat());
		viewHolder.imgTitle.setImageResource(R.drawable.icon_asyn);
		viewHolder.mcheckBox.setChecked(bean.isCheckState());
		viewHolder.mcheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				bean.setCheckState(isChecked);
				callback.onIsToggleButtonChanged(bean,isChecked);
			}
		});
		return convertView;
	}
	
	public  class ViewHolder{
		private ImageView imgTitle;
		private TextView txtDate;
		private TextView txtTime;
		private CheckBox mcheckBox;
	}
	
	public interface AdapterCallBack{
		public void onIsToggleButtonChanged(SetRemindBean bean,boolean isChecked);
	}
	
}