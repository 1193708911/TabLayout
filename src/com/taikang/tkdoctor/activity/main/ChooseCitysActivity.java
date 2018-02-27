package com.taikang.tkdoctor.activity.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.SortAdapter;
import com.taikang.tkdoctor.adapter.SortCitysAdapter;
import com.taikang.tkdoctor.adapter.SortCitysAdapter.CallBack;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.CharacterParser;
import com.taikang.tkdoctor.bean.PinyinComparator;
import com.taikang.tkdoctor.bean.SortModel;
import com.taikang.tkdoctor.bean.SortModelCitys;
import com.taikang.tkdoctor.customview.ClearEditText;
import com.taikang.tkdoctor.customview.SideBar;
import com.taikang.tkdoctor.customview.SideBar.OnTouchingLetterChangedListener;
import com.taikang.tkdoctor.util.Util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 */
@ContentView(R.layout.activity_choosecitys)
public class ChooseCitysActivity extends BaseActivity {

	@ViewInject(R.id.imgTitleBack)
	private ImageView imgBack;
	
	@ViewInject(R.id.txtTitleText)
	private TextView  tvTitle;
	
	@ViewInject(R.id.gd_citys)
	private GridView  gridCitys;
	
	@ViewInject(R.id.filter_edit)
	private ClearEditText mClearEidtText;

	@ViewInject(R.id.ll_citys)
	private LinearLayout mLinearCitys;
	
	@ViewInject(R.id.ll_searchcitys)
	private FrameLayout mLinearSearchCitys;
	
	@ViewInject(R.id.lv_searchcitys)
	private ListView mListViewCitys;
	
	@ViewInject(R.id.dialog)
	private TextView mTvDialog;
	
	@ViewInject(R.id.sidrbar)
	private SideBar  mSideBar;
 	

	private PinyinComparator pinyinComparator;
	
	private CharacterParser characterParser;
	
	private List<SortModel> SourceDateList;
	
	private List<SortModelCitys> SourceSortModelList;
	
	private SortAdapter adapter;
	
	private SortCitysAdapter cityAdapter;
	
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		tvTitle.setText(getResources().getString(R.string.air_quality_test));
		initData();
	}

	private void initData() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		
		mSideBar.setTextView(mTvDialog);
		mSideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub
			    int position=cityAdapter.getPositionForSection(s.charAt(0));
			    mListViewCitys.setSelection(position);
			}
		});
		
		String[] citys=getResources().getStringArray(R.array.citys);
		// FillData
		SourceDateList=filledData(citys);
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		View headerView=LayoutInflater.from(this).inflate(R.layout.list_item_hotcitys, null);
		GridView gdview=(GridView) headerView.findViewById(R.id.gd_hotcitys);
		ArrayAdapter<String> gridAdapter=new ArrayAdapter<String>(this, R.layout.grid_item_city, R.id.tv_city, citys);
		gdview.setAdapter(gridAdapter);
		mListViewCitys.addHeaderView(headerView);
		
		gdview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
//				Util.showToast(city);
				String city=(String) parent.getItemAtPosition(position);
				MainApplication.getInstance().setChooseCityHan(city);
				MainApplication.getInstance().setChooseCity(CharacterParser.getInstance().getSelling(city));
				ChooseCitysActivity.this.finish();
			}
		});
		// 经过排序之后的数据
//		adapter = new SortAdapter(this, SourceDateList);
//		mListViewCitys.setAdapter(adapter);
		// 把同一首字母城市放到同一集合中
		SourceSortModelList=filledSortModelCitysData(SourceDateList);
		for(SortModelCitys cc:SourceSortModelList){
//			System.out.println("letter..."+cc.getSortCitysLetter());
//			for(SortModel model:cc.getListSortModels()){
//				System.out.println("model..."+model.getName());
//			}
		}
		cityAdapter=new SortCitysAdapter(this, SourceSortModelList);
		mListViewCitys.setAdapter(cityAdapter);
		cityAdapter.setCallBack(new CallBack() {
			
			@Override
			public void setChooseCity(String city) {
				// TODO Auto-generated method stub
//				Util.showToast("回调城市..."+city);
				MainApplication.getInstance().setChooseCityHan(city);
				MainApplication.getInstance().setChooseCity(CharacterParser.getInstance().getSelling(city));
				ChooseCitysActivity.this.finish();
			}
		});
		
//	    ArrayAdapter<String> gridAdapter=new ArrayAdapter<String>(this, R.layout.grid_item_city, R.id.tv_city, citys);
//		gridCitys.setAdapter(gridAdapter);
		
		mClearEidtText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
     	    public void afterTextChanged(Editable s) {
				
			}
		});
		
//		mListViewCitys.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
//				
//			}
//		});
		
		
		
	}
	
//	@OnItemClick(R.id.lv_searchcitys)
//	public void onCityItemClickListener(){
//		
//		
//		
//	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		cityAdapter.updateListView(filledSortModelCitysData(filterDateList));
	}
	
	
	/**
	 * 处理同一首字母城市集合
	 * @param models
	 * @return
	 */
	private List<SortModelCitys> filledSortModelCitysData(List<SortModel> models){
		ArrayList<SortModelCitys> citys=new ArrayList<SortModelCitys>();
		HashMap<String, SortModelCitys> map=new HashMap<String, SortModelCitys>();
		SortModelCitys citymodels;
		ArrayList<SortModel> listSortModel = null;
		for(SortModel model:models){
			String letter=model.getSortLetters();
			if(!map.containsKey(letter)){
				citymodels=new SortModelCitys();
				listSortModel=new ArrayList<SortModel>();
				citymodels.setSortCitysLetter(letter);
				listSortModel.add(model);
				citymodels.setListSortModels(listSortModel);
				map.put(letter, citymodels);
				citys.add(citymodels);
			}else{
				listSortModel.add(model);
			}
			
		}
		Log.i("citys", citys.size()+"");
		return citys;
	}
	
	
	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	@OnClick(R.id.imgTitleBack)
	private void back(View v){
		finish();
	}
	
	
}
