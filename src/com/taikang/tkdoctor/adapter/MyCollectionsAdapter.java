package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.MyCollectionBean;

public class MyCollectionsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<MyCollectionBean> list;
	public MyCollectionsAdapter(Context context,ArrayList<MyCollectionBean> list){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if(convertView==null){
			viewHolder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.list_item_collections, null);
			convertView.setTag(viewHolder);
			viewHolder.txtTitle=(TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.txtContent=(TextView) convertView.findViewById(R.id.tv_content);
			viewHolder.txtDate=(TextView) convertView.findViewById(R.id.tv_whichday);
			viewHolder.imgHeader=(ImageView) convertView.findViewById(R.id.iv_collection);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		MyCollectionBean collectionBean=list.get(position);
		viewHolder.txtTitle.setText(collectionBean.getTitle());
		viewHolder.txtContent.setText(collectionBean.getSummary());
		viewHolder.txtDate.setText(collectionBean.getDates());
		//另有一个图片没有处理
		BitmapUtils bitmapUtils = new BitmapUtils(context);
		// 加载网络图片
		bitmapUtils.display(viewHolder.imgHeader, collectionBean.getImageurl());
		return convertView;
	}
	public class ViewHolder{
		private TextView txtTitle,txtContent,txtDate;
		private ImageView imgHeader;
	}


}
