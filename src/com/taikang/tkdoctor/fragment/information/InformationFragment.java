package com.taikang.tkdoctor.fragment.information;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.bean.ClassroomBean;
import com.taikang.tkdoctor.customview.ClassInformationView;
import com.taikang.tkdoctor.customview.ClassNavigaTitleView;
import com.taikang.tkdoctor.customview.CustomViewPager;
import com.taikang.tkdoctor.global.ClassTestData;
import com.taikang.tkdoctor.global.ClassroomType;

public class InformationFragment extends BaseFragment{
	
	//饮食 运动 心理 两性 慢病  养生 育儿
	@ViewInject(R.id.txtTitleText)
	private TextView mTvTitle;//标题
	
	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgRight;//右侧按钮
	
	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgBack;//返回按钮
	
	@ViewInject(R.id.tvTitleRight)
	private TextView mTvTitleRight;//
	
	@ViewInject(R.id.navigationlinear)
	private LinearLayout mLinearNavigation;

	@ViewInject(R.id.infoviewpager)
	private CustomViewPager mCustomViewPager;
	
	private ArrayList<View> mListViews;
	
	private ArrayList<ClassroomBean> mClassroom;
	private ClassPagerAdapter mclassAdapter;
	private ClassNavigaTitleView mClassNaviTitle;
	private int currentPageIndex=0;
	private ArrayList<ClassInformationView> infoLists = new ArrayList<ClassInformationView>();
	@Override
	protected int getResource() {
		return R.layout.fragment_information;
	}
	
	@Override
	protected void afterViews() {
		super.afterViews();
		mImgRight.setVisibility(View.GONE);
		mTvTitleRight.setVisibility(View.GONE);
		mImgBack.setVisibility(View.GONE);
		mTvTitle.setText(getActivity().getResources().getString(R.string.information_top));
		initData();
	}


	@OnClick(R.id.imgTitleBack)
	public  void onBack(View v){
		
	}

	private ClassNavigaTitleView.ClassTitileSelect mSelect = new ClassNavigaTitleView.ClassTitileSelect() {

		@Override
		public void titleSelect(int currentIndex) {
			// TODO Auto-generated method stub
			currentPageIndex = currentIndex;
			mCustomViewPager.setCurrentItem(currentIndex);
		}
	};
	
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mClassroom = ClassTestData.getClassData();
		mClassNaviTitle = new ClassNavigaTitleView(homeActivity);
		mClassNaviTitle.setmSellect(mSelect);
		mLinearNavigation.addView(mClassNaviTitle.getView());
		mListViews=new ArrayList<View>();
		for (int i = 0; i < ClassroomType.title_Cha_Arr.length; i++) {
			ClassInformationView mClassImfo = new ClassInformationView(homeActivity, i);
			mListViews.add(mClassImfo.getView());
			infoLists.add(mClassImfo);
		}
		mCustomViewPager.setCurrentItem(currentPageIndex);
		mclassAdapter = new ClassPagerAdapter();
		mCustomViewPager.setAdapter(mclassAdapter);
		mCustomViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Log.d("k", "onPageSelected - " + arg0);
				// activity从1到2滑动，2被加载后掉用此方法
				System.out.println("onPageSelected..."+arg0);
				switchInterface(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				Log.d("k", "onPageScrolled - " + arg0);
				// 从1到2滑动，在1滑动前调用
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				Log.d("k", "onPageScrollStateChanged - " + arg0);
				// 状态有三个0空闲，1是增在滑行中，2目标加载完毕

			}

		});
	}
	
	protected void switchInterface(int index) {
		// TODO Auto-generated method stub
		currentPageIndex = index;
		if (mClassNaviTitle != null) {
			mClassNaviTitle.setSelector(index);
		}
		ClassInformationView currentInfo = infoLists.get(index);
		currentInfo.loadData();
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	

	private class ClassPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			Log.d("k", "destroyItem");
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			Log.d("k", "finishUpdate");
		}

		@Override
		public int getCount() {
			Log.d("k", "getCount");
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			Log.d("k", "instantiateItem");
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			Log.d("k", "isViewFromObject");
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			Log.d("k", "restoreState");
		}

		@Override
		public Parcelable saveState() {
			Log.d("k", "saveState");
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			Log.d("k", "startUpdate");
		}

	}

}
