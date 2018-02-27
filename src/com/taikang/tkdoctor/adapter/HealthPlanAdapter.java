package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;

public class HealthPlanAdapter extends BaseAdapter {

	protected List<HealthPlanBean> mListHealthBeans;
	protected Context mContext;

	public HealthPlanAdapter() {
		// TODO Auto-generated constructor stub
	}

	public HealthPlanAdapter(Context context, ArrayList<HealthPlanBean> healtBeans) {
		Log.i("collection", "HealthPlanAdapter");
		this.mListHealthBeans = healtBeans;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListHealthBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mListHealthBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_healthplans, null);
		LinearLayout llLine=(LinearLayout) convertView.findViewById(R.id.ll_line);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
		TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
		ImageView imgTop = (ImageView) convertView.findViewById(R.id.top_short_line);
		ImageView imgBottom = (ImageView) convertView.findViewById(R.id.bottom_short_line);
		ImageView imgMid=(ImageView) convertView.findViewById(R.id.mid_short_line);
		ImageView imgfinish=(ImageView) convertView.findViewById(R.id.imgfinish);
		if (position == 0) {
			llLine.setGravity(Gravity.BOTTOM);
			imgTop.setVisibility(View.VISIBLE);
			imgMid.setVisibility(View.GONE);
			imgBottom.setVisibility(View.GONE);
		} else if (position == (mListHealthBeans.size() - 1)) {
            llLine.setGravity(Gravity.TOP);
			imgBottom.setVisibility(View.VISIBLE);
            imgTop.setVisibility(View.GONE);			
			imgMid.setVisibility(View.GONE);
		}else{
			imgTop.setVisibility(View.GONE);
			imgMid.setVisibility(View.VISIBLE);
			imgBottom.setVisibility(View.GONE);
		}
		HealthPlanBean bean = mListHealthBeans.get(position);
		tvTitle.setText(bean.getTitle());
		tvContent.setText(bean.getContent());
		if(bean.getStatus().equals("1")){
			imgfinish.setVisibility(View.VISIBLE);
		}else if(bean.getStatus().equals("")){
			imgfinish.setVisibility(View.GONE);
		}
		return convertView;
	}

}
