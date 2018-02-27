package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;
import java.util.List;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.SortModel;
import com.taikang.tkdoctor.bean.SortModelCitys;
import com.taikang.tkdoctor.util.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortCitysAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModelCitys> list = null;
	private Context mContext;
	
	public SortCitysAdapter(Context mContext, List<SortModelCitys> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModelCitys> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModelCitys citys = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.list_item_sortcitys, null);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_letter);
			viewHolder.gridCitys =(GridView) view.findViewById(R.id.gd_hotcitys);
			viewHolder.viewLeft =view.findViewById(R.id.view_left);
			viewHolder.viewRight =view.findViewById(R.id.view_right);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(citys.getSortCitysLetter());
			viewHolder.viewLeft.setVisibility(View.VISIBLE);
			ArrayList<String> listCitys=new ArrayList<String>();
			for(SortModel model:citys.getListSortModels()){
				listCitys.add(model.getName());
			}
			ArrayAdapter<String> gridAdapter=new ArrayAdapter<String>(mContext, R.layout.grid_item_city, R.id.tv_city, listCitys);
			viewHolder.gridCitys.setAdapter(gridAdapter);
			viewHolder.gridCitys.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					String city=(String) parent.getItemAtPosition(position);
					Util.showToast(city);
					callback.setChooseCity(city);
				}
			});
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;//首字母
		GridView gridCitys;//城市列表数据
		View viewLeft;
		View viewRight;
	}


	/**
	 * 获取指定数据的集合
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortCitysLetter().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortCitysLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
	
	private CallBack callback;
	
	public void setCallBack(CallBack callback){
		this.callback=callback;
	}
	
	public interface CallBack{
		void setChooseCity(String city);
	}
	
}
