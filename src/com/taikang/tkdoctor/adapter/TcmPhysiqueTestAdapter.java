package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.bean.Question;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class TcmPhysiqueTestAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Question> mQuestions;

	public TcmPhysiqueTestAdapter() {
	}

	public TcmPhysiqueTestAdapter(Context context, ArrayList<Question> questions) {
		this.mContext = context;
		this.mQuestions = questions;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mQuestions.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mQuestions.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.expandable_list_item, null);
		try {
			TextView tv = (TextView) view.findViewById(R.id.tv_questions);
			CheckBox checkboxa = (CheckBox) view.findViewById(R.id.checkboxa);
			CheckBox checkboxb = (CheckBox) view.findViewById(R.id.checkboxb);
			CheckBox checkboxc = (CheckBox) view.findViewById(R.id.checkboxc);
			CheckBox checkboxd = (CheckBox) view.findViewById(R.id.checkboxd);
			CheckBox checkboxe = (CheckBox) view.findViewById(R.id.checkboxe);
			String question = mQuestions.get(position).getQuestionname();
			int chooseIndex = mQuestions.get(position).getChooseindex();
			int isShowItem  = Integer.parseInt(mQuestions.get(position).getSex());//0 都显示 1 男  2 女
			//判断题目是否显示需要判断性别
			tv.setText(question);
			if (chooseIndex != -1) {
				switch (chooseIndex) {
				case 0:
					checkboxa.setChecked(true);
					break;
				case 1:
					checkboxb.setChecked(true);
					break;
				case 2:
					checkboxc.setChecked(true);
					break;
				case 3:
					checkboxd.setChecked(true);
					break;
				case 4:
					checkboxe.setChecked(true);
					break;	
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return view;
	}

}
