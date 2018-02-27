package com.taikang.tkdoctor.customview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseView;
import com.taikang.tkdoctor.bean.ClassContent;
import com.taikang.tkdoctor.bean.ClassroomBean;
import com.taikang.tkdoctor.bean.InfoResponseModel;
import com.taikang.tkdoctor.bean.InfoTop;
import com.taikang.tkdoctor.bean.InfoTopResponseModel;
import com.taikang.tkdoctor.bean.InfoTopResponseModel.InfoTops;
import com.taikang.tkdoctor.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @ClassName: ClassNavigaPhotoView
 * @Description: 资讯大图view
 * @author helongfei
 * @date
 * 
 */
public class ClassNavigaPhotoView extends BaseView {

//	private List<ClassContent> currentContentList;
	private List<InfoTop> currentInfoTopList;
//	private ClassroomBean currentClassroom;
    private InfoTops currentInfoTop;
	private ViewPager mViewPager;
	private ImageView[] mImages;
	private ViewGroup circleLinear = null;
	private SlideImageLayout mSlideLayout;
	private ArrayList<View> mImagePageViewList;
	private TextView titleText;

	public SlideImageLayout.ImgOncLick mImgclick = new SlideImageLayout.ImgOncLick() {
		@Override
		public void onclickIndex(int index) {
			// TODO Auto-generated method stub
//			HomepageDataManager.getInstance().getmClick().addVitalityValue(2);
//			HomepageDataManager.getInstance().getmClick()
//					.addParticipationValue(1);
//			ClassContent mClassContent = currentContentList.get(index);
//			Intent intent = new Intent(act, ClassInfoDetailAct.class);
//			intent.putExtra("classDteail", (Serializable) mClassContent);
//			act.startActivity(intent);
		}
	};

//	public ClassNavigaPhotoView(Activity act, ClassroomBean currentClassroom) {
//		super(act, R.layout.class_photo_view);
//		this.currentClassroom = currentClassroom;
//		initWidget();
//		initData();
//	}

	
	public ClassNavigaPhotoView(Activity act, InfoTops currentInfoTop) {
		super(act, R.layout.class_photo_view);
		this.currentInfoTop = currentInfoTop;
		initWidget();
		initData();
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		boolean isLarger = false;
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeigh = dm.heightPixels;
		int densityDpi = dm.densityDpi;
		int actualWeight = screenWidth;
		int actualHeight = 170;
		LogUtils.d("屏幕的dpi:" + densityDpi + "width:" + screenWidth+ "height:" + screenHeigh);
		if (densityDpi > 240) {
			actualHeight = 210;
			isLarger = true;
		}
		ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
		params.width = actualWeight;
		params.height = Util.dpToPx(act, actualHeight);
		mViewPager.setLayoutParams(params);
		if(currentInfoTop==null){
		   currentInfoTop=new InfoTopResponseModel().new InfoTops();
		}
		currentInfoTopList = currentInfoTop.getResultlist();
         
		
		// 只显示前6条
		if (currentInfoTopList != null && currentInfoTopList.size() > 6) {
			currentInfoTopList = currentInfoTopList.subList(0, 5);
		}

		// 只显示一条
//		if (currentContentList != null && currentContentList.size() >= 1) {
//			currentContentList = currentContentList.subList(0, 1);
//			circleLinear.setVisibility(View.GONE);
//		}

		int length = currentInfoTopList.size();
		mImages = new ImageView[length];

		mSlideLayout.setCircleImageLayout(length);
		mImagePageViewList = new ArrayList<View>();
		for (int i = 0; i < length; i++) {
			titleText.setText(currentInfoTopList.get(i).getTitle());
			String url = null;
			if (isLarger) {
				url = currentInfoTopList.get(i).getPicurl();// 加载大图
			} else {
				url = currentInfoTopList.get(i).getPicurl();
			}
			if (url != null) {
				// 加载网络大图
				mImagePageViewList.add(mSlideLayout.getSlideImageLayout(url,
						actualWeight, Util.dpToPx(act, actualHeight)));
			} else {
				// 加载本地图片
				mImagePageViewList.add(mSlideLayout
						.getSlideImageLayout(R.drawable.pic_load));
			}
			// 加载圆点
			mImages[i] = mSlideLayout.getCircleImageLayout(i);
			circleLinear.addView(mSlideLayout.getLinearLayout(mImages[i], 10,
					10));
		}
		mViewPager.setAdapter(new SlideImageAdapter());
		mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
	}

	private void initWidget() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) view.findViewById(R.id.photoViewpage);
		circleLinear = (ViewGroup) view.findViewById(R.id.circleLinear);
		titleText = (TextView) view.findViewById(R.id.titleText);
		mSlideLayout = new SlideImageLayout(act);
		mSlideLayout.setOnImageClick(mImgclick);
	}

	// 滑动图片数据适配器
	private class SlideImageAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return mImagePageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View view, int arg1, Object arg2) {
			((ViewPager) view).removeView(mImagePageViewList.get(arg1));
		}

		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPager) view).addView(mImagePageViewList.get(position));

			return mImagePageViewList.get(position);
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

	// 滑动页面更改事件监听器
	private class ImagePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
			mSlideLayout.setPageIndex(index);
			InfoTop content = currentInfoTopList.get(index);
			titleText.setText(content.getTitle());
			for (int i = 0; i < mImages.length; i++) {
				mImages[index].setBackgroundResource(R.drawable.dot_selected1);

				if (index != i) {
					mImages[i].setBackgroundResource(R.drawable.dot_none1);
				}
			}
		}
	}
}
