package com.taikang.tkdoctor.customview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseView;
import com.taikang.tkdoctor.global.ClassroomType;
import com.taikang.tkdoctor.util.Util;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;



/**
 * @ClassName: HealthClassFragment
 * @Description: 
 * @date
 * 
 */
public class ClassNavigaTitleView extends BaseView {

	private HorizontalScrollView mHorScrollView;
	private LinearLayout titleLinear;
	private ArrayList<TextView> textViews = new ArrayList<TextView>();
	private ArrayList<ImageView> imageviews = new ArrayList<ImageView>();
	private int mCurrentIndex = 0;
	private ClassTitileSelect mSellect;
	HorizontalScrollView hsv;
	LinearLayout titles;
	private int mMaxScrollY = 0;
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor;// 动画图片
	private TextView currentText;

	public void setmSellect(ClassTitileSelect mSellect) {
		this.mSellect = mSellect;
	}

	public ClassNavigaTitleView(Activity act) {
		super(act, R.layout.class_title_view);
		initWidget();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		bmpW = BitmapFactory.decodeResource(act.getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度

		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		// Toast.makeText(this, "" + screenW, Toast.LENGTH_LONG).show();
		// bmpW = screenW / 4;
		offset = (getPx(55) - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset + (offset * 2 + bmpW) * 0, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
		currIndex = 0;
		LinearLayout.LayoutParams layoutParams = new LayoutParams(getPx(55),
				LayoutParams.FILL_PARENT);
		for (int i = 0; i < ClassroomType.title_Cha_Arr.length; i++) {
			LinearLayout linear = new LinearLayout(act);
			linear.setOrientation(LinearLayout.VERTICAL);
			TextView textView = new TextView(act);
			// layoutParams.setMargins(getPx(10), getPx(2), getPx(10),
			// getPx(2));
			textView.setText(ClassroomType.title_Cha_Arr[i]);
			textView.setTag(i);
			textView.setPadding(getPx(10), getPx(6), getPx(10), getPx(6));
			textView.setTextSize(17);
			if (i == currIndex) {
				currentText = textView;
				textView.setTextColor(act.getResources().getColor(
						R.color.color_dd));
			} else {
				textView.setTextColor(act.getResources().getColor(
						R.color.color_63));

			}
			// if (i % 2 == 0)
			// textView.setBackgroundColor(Color.GRAY);
			// else
			// textView.setBackgroundColor(Color.GREEN);

			// textView.setBackgroundColor(Color.GRAY);
			textView.setGravity(Gravity.CENTER);
			textView.setId(i);
			textView.setLayoutParams(layoutParams);
			textView.setOnClickListener(this);

			textView.setGravity(Gravity.CENTER);
			textView.setTag(i);
			// if (i == 0) {
			// textView.setTextColor(act.getResources().getColor(
			// R.color.color_dd));
			// } else {
			// }
			linear.setGravity(Gravity.CENTER_HORIZONTAL);

			linear.addView(textView);
			textViews.add(textView);
			titles.addView(linear);
		}
	}

	private void initWidget() {
		// TODO Auto-generated method stub
		hsv = (HorizontalScrollView) view.findViewById(R.id.hsv);
		titles = (LinearLayout) view.findViewById(R.id.titles);
		cursor = (ImageView) view.findViewById(R.id.cursor);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		int index = (Integer) v.getTag();

		setSelector(index);

	}

	/***
	 * 选中效果
	 */
	public void setSelector(int index) {
		mCurrentIndex = index;
		if (index != currIndex) {

			sliderSetting(index);
			// setSelector(currIndex);
			currIndex = index;
			currentText.setTextColor(act.getResources().getColor(
					R.color.color_63));
			textViews.get(index).setTextColor(
					act.getResources().getColor(R.color.color_dd));

            // ((TextView) v).setTextColor(act.getResources().getColor(
			// R.color.color_dd));
			currentText = textViews.get(index);
			moveToText(currIndex);
			mSellect.titleSelect(currIndex);
		}
	}

	private void moveToText(int i) {
		// TODO Auto-generated method stub
		int width = 0;
		for (int j = 0; j < i; j++) {
			width += textViews.get(i).getWidth();
		}
		if (i > 0) {

			hsv.smoothScrollTo((i * getPx(55) - getPx(55) * 3), 0);

		}
		 
	}

	public interface ClassTitileSelect {
		public void titleSelect(int currentIndex);
	}

	public int getPx(int dp) {
		return Util.dpToPx(act, dp);
	}

	public void sliderSetting(int index) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		int a1 = offset * 2 + bmpW + 1;// 页卡1 -> 页卡2 偏移量
		map.put("a0", 0);
		map.put("a1", a1);
		for(int i=2; i <19; i ++){
			map.put(String.format("a%d", i), a1 * i);
		}
		
		Animation animation = new TranslateAnimation(map.get("a" + currIndex),
				map.get("a" + index), 0, 0);

		try {
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		} catch (Exception e) {
		}
	}
}
