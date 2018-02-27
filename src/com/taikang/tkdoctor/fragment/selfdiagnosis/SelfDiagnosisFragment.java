package com.taikang.tkdoctor.fragment.selfdiagnosis;

import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.selfdiagnosis.ModifyAgeSex;
import com.taikang.tkdoctor.adapter.SymptomListAdapter;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.HdBsAuthUser;
import com.taikang.tkdoctor.customview.ButtonM;
import com.taikang.tkdoctor.util.Util;

public class SelfDiagnosisFragment extends BaseFragment implements
OnCheckedChangeListener, OnClickListener, OnPageChangeListener, OnItemClickListener, BodySelectCallback{
	
	private MyAdapter adapter;
	private int currentItem = 0;
	private ArrayList<View> list_images;
	private SymptomListAdapter symptomListAdapter;
	private ArrayList<String> mListNames;
	private int parentWidth, parentHeight;
	private BodySelect bodySelect;
	private SymptomList symptomList;
	private int backResultModify = 100;
//	private boolean currentFront = true;
	private int currentState = 1; //默认男士正面 ; //1：男正   2：男背   3：女正   4：女背
	private int selectPosition = 0;
	
	@ViewInject(R.id.txtTitleText)
	private TextView mTvTitle;//标题
	
	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight;
	
	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgBack;// 返回按钮
	
	@ViewInject(R.id.radiogroup)
	private RadioGroup radioGroup;
	
	@ViewInject(R.id.radio_on_body_pic)
	private RadioButton radioBodyPic;//人体图
	
	@ViewInject(R.id.radio_on_zhengzhuang_list)
	private RadioButton radioSymptomList;//症状列表

	@ViewInject(R.id.viewpager_self_body_pic)
	private ViewPager viewPager;
	
	@ViewInject(R.id.view_left)
	private View view_left;
	
	@ViewInject(R.id.view_right)
	private View view_right;
	
	@ViewInject(R.id.lv_symptom_list)
	private ListView mLvSymptom;
	
	private View view_BodyPic;//人体图View
	private View view_ZhengzhuangList;//症状列表View
	public RelativeLayout mRentiLayout;
	public LinearLayout mRentiLayoutParent;
	private TextView mTvModifyAgeASax;//修改
	private TextView mTvSex;//性别
	private TextView mTvAge;//年龄
	private ImageView mIvSex;//性别图片
	private ImageView mIvRefresh;//翻身
	private LinearLayout mListLayout;//列表title

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_selfdiagnosis;
	}
	
	@Override
	protected void afterViews() {
		super.afterViews();
		mImgRight.setVisibility(View.GONE);
		mImgBack.setVisibility(View.GONE);
		mTvTitle.setText(getActivity().getResources().getString(R.string.selfdiagnosis_top));
		initView();
		addListener();
		initData();
	}

	@Override
	public void onResume() {
		super.onResume();
//		initData();
	}

	private void addListener() {
		// TODO Auto-generated method stub
		radioGroup.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);
		
		mTvModifyAgeASax.setOnClickListener(this);
		mIvRefresh.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		view_BodyPic = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_body_pic, null);
		view_ZhengzhuangList = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zhengzhuang_list, null);
		mRentiLayout = (RelativeLayout) view_BodyPic.findViewById(R.id.rl_zizhen_renti);
		mRentiLayoutParent = (LinearLayout) view_BodyPic.findViewById(R.id.ll_zizhen_renti);//
		mTvModifyAgeASax = (TextView) view_BodyPic.findViewById(R.id.tv_zizhen_bodypic_xiugai);
		mTvSex = (TextView) view_BodyPic.findViewById(R.id.tv_zizhen_renti_sex);
		mTvAge =(TextView) view_BodyPic.findViewById(R.id.tv_zizhen_renti_age);
		mIvSex = (ImageView) view_BodyPic.findViewById(R.id.iv_zizhen_renti_sex);
		mIvRefresh = (ImageView) view_BodyPic.findViewById(R.id.iv_zizhen_refresh);
		mListLayout = (LinearLayout) view_ZhengzhuangList.findViewById(R.id.ll_zhengzhang_list);
		
		list_images = new ArrayList<View>();
		list_images.add(view_BodyPic);
		list_images.add(view_ZhengzhuangList);
		adapter = new MyAdapter();
		viewPager.setAdapter(adapter);
		
		mLvSymptom = (ListView) view_ZhengzhuangList.findViewById(R.id.lv_symptom_list);
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0://人体图
			radioBodyPic.performClick();
//			String curPosition = symptomList.position;
			break;
		case 1://症状列表
			radioSymptomList.performClick();
			break;
		default:
			break;
		}
		currentItem = arg0;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == backResultModify) {
			String sex = data.getStringExtra("sex");
			int age = data.getIntExtra("age", 0);
			mTvSex.setText(sex);
			if (age != 0) {
				mTvAge.setText(age+"岁");
			}
			if (sex.equals("男")) {
				mIvSex.setImageResource(R.drawable.ico_man);
				mIvRefresh.setImageResource(R.drawable.icon_man_refresh);
				currentState = 1;
				bodySelect.modifyBody(currentState);
			}else {
				mIvSex.setImageResource(R.drawable.ico_woman);
				mIvRefresh.setImageResource(R.drawable.icon_woman_refresh);
				currentState = 3;
				bodySelect.modifyBody(currentState);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_zizhen_bodypic_xiugai:
			Intent intent = new Intent(getActivity(),ModifyAgeSex.class); 
	        String sex = mTvSex.getText().toString();  
	        intent.putExtra("sex", sex);
			startActivityForResult(intent, backResultModify);
//			startActivity(new Intent(getActivity(),ModifyAgeSex.class));
			break;
		case R.id.iv_zizhen_refresh://转身处理
			switch (currentState) {
			case 1:
				currentState = 2;
				break;
			case 2:
				currentState = 1;
				break;	
			case 3:
				currentState = 4;
				break;
			case 4:
				currentState = 3;	
				break;
			default:
				break;
			}
			bodySelect.modifyBody(currentState);
			bodySelect.currentState = this.currentState;
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		setRadionButtonChecked();
		switch (checkedId) {
		case R.id.radio_on_body_pic:
			currentItem = 0;
			radioBodyPic.setTextColor(getResources().getColor(R.color.tab_tv_renti_press));
			view_left.setBackgroundColor(getResources().getColor(R.color.tab_tv_renti_press));
			break;
		case R.id.radio_on_zhengzhuang_list:
			currentItem = 1;
			radioSymptomList.setTextColor(getResources().getColor(R.color.tab_tv_renti_press));
			view_right.setBackgroundColor(getResources().getColor(R.color.tab_tv_renti_press));
			//告知选择
			selectInform(selectPosition);
			break;
		default:
			break;
		}
		viewPager.setCurrentItem(currentItem);
	}
	
	private void setRadionButtonChecked() {
		// TODO Auto-generated method stub
		radioBodyPic.setTextColor(getResources().getColor(R.color.tab_tv_renti_unpress));
		radioSymptomList.setTextColor(getResources().getColor(R.color.tab_tv_renti_unpress));
		view_left.setBackgroundColor(getResources().getColor(R.color.radioGroup));
		view_right.setBackgroundColor(getResources().getColor(R.color.radioGroup));
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return list_images.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(list_images.get(arg1));
			return list_images.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		
		symptomList = new SymptomList(getActivity(), mListLayout,mLvSymptom, this);
//		int[] location = new  int[2] ;
//	    view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//	    view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//	    System.out.println("view--->x坐标:"+location [0]+"view--->y坐标:"+location [1]);
		
		//获取屏幕宽高
		WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
		final int width = wm.getDefaultDisplay().getWidth();
		final int height = wm.getDefaultDisplay().getHeight();
		LogUtils.e("width宽度：" + width + "|height高度：" + height);
		
		ViewTreeObserver vto2 = mRentiLayout.getViewTreeObserver();   
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
		    @Override   
		    public void onGlobalLayout() { 
		    	mRentiLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		    	parentWidth = mRentiLayout.getWidth();
		    	parentHeight = mRentiLayout.getHeight();
		    	LogUtils.e("宽度：" + mRentiLayout.getWidth() + "||高度：" + mRentiLayout.getHeight());
		    	bodySelect = new BodySelect(getActivity(), mRentiLayoutParent, parentWidth, parentHeight, new BodySelectCallback() {
					
					@Override
					public void selector(int part) {
//						bodySelect.removeAllSelect();//清除全部选择
						selectPosition = part;
						radioSymptomList.performClick();//自动跳转到症状列表
					}
				});
		    	bodySelect.loadView(currentState,width,height);//
		    }   
		});
	    
	}
	
	private void selectInform(int i){
		String ageNow = mTvAge.getText().toString();
		switch (i) {
		case 0:
			symptomList.showList("全身症状", currentState, ageNow);
			break;
		case 1:
			symptomList.showList("头部", currentState, ageNow);
			break;
		case 2:
			symptomList.showList("颈部", currentState, ageNow);
			break;
		case 3:
			symptomList.showList("躯干", currentState, ageNow);
			break;
		case 4:
			if (currentState == 1 || currentState == 3) {
				symptomList.showList("生殖器官", currentState, ageNow);
			}else if (currentState == 2 || currentState == 4) {
				symptomList.showList("臀部", currentState, ageNow);
			}
			break;
		case 5:
			symptomList.showList("腿部", currentState, ageNow);
			break;
		case 6:
			symptomList.showList("手臂", currentState, ageNow);
			break;
		case 7:
			symptomList.showList("手臂", currentState, ageNow);
			break;
		default:
			break;
		}
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selector(int part) {
		bodySelect.selector(part);
	}

}
