package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.requestback.SymptonListBean.SymptonBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SymptomListAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<SymptonBean> listNames;
	
    public SymptomListAdapter(Context context, ArrayList<SymptonBean> listNames)  
    {  
        this.context = context;  
        this.listNames = listNames;
    }

    @Override
	public int getCount() {
		return listNames.size();
	}

	@Override
	public Object getItem(int position) {
		return listNames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;  
        if(convertView == null)  
        {  
            holder = new ViewHolder();  
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_symptom, null);  
            holder.mTvSymptom = (TextView)convertView.findViewById(R.id.tv_item_zhengzhang);
            convertView.setTag(holder);  
        }else  
        {  
            holder = (ViewHolder)convertView.getTag();
        }  
        
        holder.mTvSymptom.setText(listNames.get(position).getSymptom());
          
        return convertView;
	}
	
	static class ViewHolder  
    {   
        public TextView mTvSymptom;
    }

}
