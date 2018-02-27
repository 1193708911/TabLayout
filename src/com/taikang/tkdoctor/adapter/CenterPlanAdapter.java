package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;

public class CenterPlanAdapter extends BaseAdapter  {
	/**
	 * healthPlan dialog
	 */
	private Context context;
	private static ArrayList<HealthPlanBean> list;

	public CenterPlanAdapter(Context context,ArrayList<HealthPlanBean> list){
		this.context=context;
		this.list=list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.mycenter_healthplan, null);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.tv_title);
			viewHolder.msgInfo= (TextView) convertView
					.findViewById(R.id.tv_msginfo);
			viewHolder.imageHeader= (ImageView) convertView
					.findViewById(R.id.iv_direction);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}

		HealthPlanBean bean=list.get(position);
		viewHolder.title.setText(bean.getTitle());
		viewHolder.msgInfo.setText(bean.getContent());
		//图片是否需要根据跳转情况添加有待商量
		if(bean.getTag().equals("1")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_water);
		}else if(bean.getTag().equals("2")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_yinliao);
		}else if(bean.getTag().equals("3")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_time);
		}else if(bean.getTag().equals("4")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_sleep);
		} else if(bean.getTag().equals("5")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_break);
		}else if(bean.getTag().equals("6")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_shuiguo);
		}
		else if(bean.getTag().equals("7")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_shucai);
		}
		else if(bean.getTag().equals("13")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_run);
		}
		else if(bean.getTag().equals("9")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_fish);
		}else if(bean.getTag().equals("10")){
			viewHolder.imageHeader.setImageResource(R.drawable.icon_milk);
		}
		return convertView;

	}
	private class ViewHolder{
		private TextView title;
		private TextView msgInfo;
		private ImageView imageHeader;
	}

}
