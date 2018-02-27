package com.taikang.tkdoctor.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.taikang.tkdoctor.R;

public class DialogAdapter extends BaseAdapter  {
	/**
	 * healthPlan dialog
	 */
	private Context context;
	private static List<String> list;
	
	
	public static Map<Integer, Boolean> isSelected;
	private int index;
	public static Map<Integer, Boolean> getIsSelected() {
		return isSelected;
	}
	public static void setIsSelected(Map<Integer, Boolean> isSelected) {
		DialogAdapter.isSelected = isSelected;
	}
	public DialogAdapter(Context context,List<String> list){
		this.context=context;
		this.list=list;
	}
	public static  void init() {
		// TODO Auto-generated method stub
		isSelected = new HashMap<Integer, Boolean>();  
	    for (int i = 0; i < 7; i++) {  
	        isSelected.put(i, true);  
	    }  
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
		final ViewHolder viewHolder;
		this.index=position;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.dialog_item, null);
			convertView.setTag(viewHolder);
			viewHolder.txtWhichDay = (TextView) convertView
					.findViewById(R.id.txtdate);
			viewHolder.imageState= (ImageView) convertView
					.findViewById(R.id.imageState);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.txtWhichDay.setText(list.get(position));
		if(isSelected.get(position)){
			viewHolder.imageState.setImageResource(R.drawable.cb_press);
		}else {
			viewHolder.imageState.setImageResource(0);
		}
		return convertView;
	}
	public final class ViewHolder{
		public TextView txtWhichDay;
		public ImageView imageState;
	}


}
