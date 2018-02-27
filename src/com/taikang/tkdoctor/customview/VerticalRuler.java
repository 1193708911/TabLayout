package com.taikang.tkdoctor.customview;


import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.customview.VerticalRulerScrollView.ScrollType;
import com.taikang.tkdoctor.customview.VerticalRulerScrollView.ScrollViewListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 竖直方向的尺子
 * 
 * @author Joey <lma_ma@163.com>
 * 
 */
public class VerticalRuler extends RelativeLayout {

	/**
	 * 标记，采用红线标示，标记当前刻度
	 */
	private ImageView mark;

	private Bitmap markBgBmp;
	/**
	 * 绘制的几个刻度，有三种刻度，最大刻度，中等刻度，和最小刻度
	 */
	private Drawable minDrawable;
	private Drawable maxDrawable;
	private Drawable midDrawable;

	private float bmpMaxHeight = 20.0f;

	// private float maxTextSize = 10.0f;
	private float maxTextSize = 14.0f;
	private float resultTextSize = 15.0f;

	/**
	 * unit size of the ruler
	 */
	private float minUnitSize = 20.0f;
	/**
	 * 最大单位的个数
	 */
	private int maxUnitCount = 24;
	/**
	 * 最大单位包含的每个单位数
	 */
	private int perUnitCount = 10;

	private int unitColor;
	private int markColor;

	/**
	 * Padding on the left,
	 */
	private float unitPadding = 10.0f;
	/**
	 * 这个刻度的起始位置部分偏移，这部分偏移，可以使scrollView滑动到刻度尺的最前面或者是最后面
	 */
	private int padding = 0;
	/**
	 * 刻度的宽度
	 */
	private final int UNIT_ITEM_WIDTH = 2;
	/**
	 * 刻度容器
	 */
	private LinearLayout unitContainer;
	/**
	 * 单位文字容器
	 */
	private LinearLayout textContainer;
	private RelativeLayout rulerContainer;
	/**
	 * 显示结果的容器
	 */
	private LinearLayout resultContainer;
	/**
	 * 左边的一个修饰文字
	 */
	private TextView resultTagView;
	/**
	 * 显示结果显示
	 */
//	private TextView resultView;
	/**
	 * 整个刻度尺
	 */
	private LinearLayout rootContainer;
	/**
	 * 纵向滑动的scrollView
	 */
	private VerticalRulerScrollView verticalScrollerView;

	private RulerHandler rulerHandler;

	private int mode;

	/**
	 * 标记刻度尺的类型，一种是一般的刻度尺， 另一种为时间刻度尺
	 */
	public final static int MODE_RULER = 0;
	public final static int MODE_TIMELINE = 1;

	private int unitVisible;
	/**
	 * 标记3中刻度图标的可见性
	 */
	public final static int MID_VISIBLE = 0x4;
	public final static int MIN_VISIBLE = 0x2;
	public final static int MAX_VISIBLE = 0x1;

	public VerticalRuler(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ruler, defStyleAttr, 0);
		minUnitSize  = a.getDimension(R.styleable.Ruler_min_unit_size, 20.0f);
		maxUnitCount = a.getInteger(R.styleable.Ruler_max_unit_count, 24);
		perUnitCount = a.getInteger(R.styleable.Ruler_per_unit_count, 10);
		bmpMaxHeight = a.getDimension(R.styleable.Ruler_unit_bmp_height, 100.0f);
		mode = a.getInt(R.styleable.Ruler_ruler_mode, MODE_TIMELINE);
		unitPadding = minUnitSize / 2;
		unitVisible = a.getInt(R.styleable.Ruler_unit_visible, MID_VISIBLE | MIN_VISIBLE | MAX_VISIBLE);
		unitColor = a.getColor(R.styleable.Ruler_unit_color, Color.BLACK);
		markColor = a.getColor(R.styleable.Ruler_mark_color, Color.RED);
		a.recycle();
		init();
	}

	public VerticalRuler(Context context, AttributeSet attrs) {
		super(context, attrs, R.attr.ruler_style);
		// 定义了尺子的相关信息
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ruler, R.attr.ruler_style, 0);
		minUnitSize  = a.getDimension(R.styleable.Ruler_min_unit_size, 20.0f);//横线之间的间隔大小
		maxUnitCount = a.getInteger(R.styleable.Ruler_max_unit_count, 24);
		perUnitCount = a.getInteger(R.styleable.Ruler_per_unit_count, 10);
		bmpMaxHeight = a.getDimension(R.styleable.Ruler_unit_bmp_height, 60.0f);// 竖线的的长度
		mode = a.getInt(R.styleable.Ruler_ruler_mode, MODE_TIMELINE);
		unitPadding  = minUnitSize / 2;
		unitVisible  = a.getInt(R.styleable.Ruler_unit_visible, MID_VISIBLE | MIN_VISIBLE | MAX_VISIBLE);
		unitColor    = a.getColor(R.styleable.Ruler_unit_color, Color.BLACK);
		markColor    = a.getColor(R.styleable.Ruler_mark_color, Color.RED);
		a.recycle();
		init();
	}

	public VerticalRuler(Context context) {
		super(context, null);
		init();
	}

	private void init() {
		initDrawable();
		initParentContainer();
		initUnit();
		verticalScrollerView.setOnScrollStateChangedListener(scrollListener);
		postDelayed(measurePaddingRunnable, 100);
	}

	/**
	 * 初始化刻度尺的第一级容器 控制各个部分的位置
	 */
	@SuppressLint({ "InlinedApi", "RtlHardcoded" })
	private void initParentContainer() {
		//将整个自定义View作为父RelativeLayout
		//然后设置各个子控件的布局配置
		verticalScrollerView = new VerticalRulerScrollView(getContext());
		verticalScrollerView.setHorizontalScrollBarEnabled(false);
		verticalScrollerView.setVerticalScrollBarEnabled(false);
		
		mark = new ImageView(getContext());
		RelativeLayout.LayoutParams paramsMark = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
	    RelativeLayout.LayoutParams.WRAP_CONTENT);
//		paramsMark.addRule(RelativeLayout.CENTER_VERTICAL);
		paramsMark.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		paramsMark.topMargin=60;
		mark.setId(R.id.finger_container_id);
		mark.setLayoutParams(paramsMark);
		mark.setImageBitmap(markBgBmp);
		addView(mark);
		
		//将手指放在尺子左侧处理
		RelativeLayout.LayoutParams params_scrollView=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
	    RelativeLayout.LayoutParams.WRAP_CONTENT);
		verticalScrollerView.setLayoutParams(params_scrollView);
        params_scrollView.addRule(RelativeLayout.RIGHT_OF, R.id.finger_container_id);
		addView(verticalScrollerView);		
		
		//将根容器加载到自定义view
		rootContainer = new LinearLayout(getContext());
		rootContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT));
//		rootContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
		verticalScrollerView.addView(rootContainer);

		// 尺子布局
		// 将尺子布局加载到rooterContainer
		rulerContainer = new RelativeLayout(getContext());
//		rulerContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));
		rulerContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
	    LinearLayout.LayoutParams.WRAP_CONTENT));
		rootContainer.addView(rulerContainer);
		
		// 首先控制尺子刻度
		// 尺子刻度与父容器rulerContainer做对齐
		// 初始化刻尺图标容器
		RelativeLayout.LayoutParams params_kedu = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		RelativeLayout.LayoutParams.WRAP_CONTENT);
		params_kedu.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		unitContainer = new LinearLayout(getContext());
		unitContainer.setLayoutParams(params_kedu);
		unitContainer.setId(R.id.unit_container_id);
		unitContainer.setOrientation(LinearLayout.VERTICAL);
//		unitContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		unitContainer.setPadding(0, dp2px((int) unitPadding), 0, dp2px((int) unitPadding));
		rulerContainer.addView(unitContainer);
		// 初始化刻尺文字容器
		RelativeLayout.LayoutParams params_text = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
		RelativeLayout.LayoutParams.WRAP_CONTENT);
		params_text.leftMargin = dp2px((int) (bmpMaxHeight / 2 + resultTextSize));
		params_text.addRule(RelativeLayout.RIGHT_OF, R.id.unit_container_id);
		textContainer = new LinearLayout(getContext());
		textContainer.setLayoutParams(params_text);
		textContainer.setOrientation(LinearLayout.VERTICAL);
//		textContainer.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
		rulerContainer.addView(textContainer);
		

	}

	/**
	 * 目的是让刻尺滑动到最前段，或者最后部分
	 */
	private Runnable measurePaddingRunnable = new Runnable() {

		@Override
		public void run() {
			if (padding == 0) {
				// 这部分padding计算的总规则是，宽度的一半减去unitContainer左边的padding，再减去unitContainer第一个刻度的一半宽度。
				padding = getHeight() / 2 - dp2px((int) unitPadding) - unitContainer.getChildAt(0).getHeight() / 2;
//				rootContainer.setPadding(0, padding, 0, padding);
				//计算出手指的高度
				int mark_height=mark.getHeight();
				int verticalScrollViewPadding=60+mark_height/2-10;
//				rootContainer.setPadding(0, padding, 0, padding);
				int padding_bottom=getHeight()-verticalScrollViewPadding;
				System.out.println("padding_bottom.."+padding_bottom);
				rootContainer.setPadding(0, verticalScrollViewPadding, 0, padding_bottom);
				return;
			}
		}
	};

	public int getMinUnitSize() {
		return dp2px((int) minUnitSize);
	}

	public int getPerUnitCount() {
		return perUnitCount;
	}

	public int getMaxUnitCount() {
		return maxUnitCount;
	}

	/**
	 * 初始化刻度与刻度标记部分
	 */
	private void initUnit() {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dp2px((int) minUnitSize));
		for (int i = 0; i < maxUnitCount; i++) {
			for (int j = 0; j < perUnitCount; j++) {
				TextView minUnitView = new TextView(getContext());
				minUnitView.setLayoutParams(params);
				minUnitView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//				minUnitView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
				minUnitView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
				if (j == 0) {
					minUnitView.setCompoundDrawables(null, null, null, maxDrawable);
				} else if (j == perUnitCount / 2) {
					if ((unitVisible & (byte) MID_VISIBLE) == MID_VISIBLE)
						minUnitView.setCompoundDrawables(null, null, null, midDrawable);
				} else {
					if ((unitVisible & (byte) MIN_VISIBLE) == MIN_VISIBLE)
						minUnitView.setCompoundDrawables(null, null, null, minDrawable);
				}
				unitContainer.addView(minUnitView);
			}
		}
		TextView maxUnitView = new TextView(getContext());
		maxUnitView.setTextSize(.1f);
		maxUnitView.setLayoutParams(params);
//		maxUnitView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
		maxUnitView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		maxUnitView.setCompoundDrawables(null, null, null, maxDrawable);
		unitContainer.addView(maxUnitView);

		LinearLayout.LayoutParams maxParams = new LinearLayout.LayoutParams(-2,dp2px((int) minUnitSize) * perUnitCount / 2);
		
//		for (int i = 0; i <= maxUnitCount * 2; i++) {
//			TextView textUnitView = new TextView(getContext());
//			textUnitView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//			if (i < maxUnitCount * 2 - 1) {
//				textUnitView.setLayoutParams(maxParams);
//				textUnitView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
//			} else if (i < maxUnitCount * 2) {
//				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2,dp2px((int) minUnitSize) * 4);
//				textUnitView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//				textUnitView.setLayoutParams(param);
//			} else {
//				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2,dp2px((int) minUnitSize) * 3);
//				textUnitView.setLayoutParams(param);
//				textUnitView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
//			}
//			textUnitView.setGravity(Gravity.TOP | Gravity.LEFT);
//			if (i % 2 == 0) {
//				textUnitView.setText(String.format("%d  ", (i / 2 + 100)));
//			}
//			textContainer.addView(textUnitView);
//		}
		
		//数值从大到小
		//maxUnitCount 140
        for(int j=maxUnitCount*2;j>=0;j--){
        	TextView textUnitView = new TextView(getContext());
			textUnitView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        	if(j==0){
        		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2,dp2px((int) minUnitSize) * 3);
				textUnitView.setLayoutParams(param);
        	}else if(j==1){
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-2,dp2px((int) minUnitSize) * 4);
				textUnitView.setLayoutParams(param);
        	}else{
        		textUnitView.setLayoutParams(maxParams);
        	}
			textUnitView.setGravity(Gravity.TOP | Gravity.LEFT);
			if (j % 2 == 0) {
				textUnitView.setText(String.format("%d  ", (j/ 2 + 100)));
			}
			textContainer.addView(textUnitView);
        }
		
	}

	/**
	 * 初始化单位的背景图 这个画出东西都是独立的
	 */
	private void initDrawable() {
		int maxHeight = dp2px((int) bmpMaxHeight);//最长横线的长度
		maxHeight=120;
		int midHeight = maxHeight * 2 / 3;// 中等横线的长度
		int minHeight = maxHeight * 3 / 4;// 最短横线的长度		
		Bitmap bmp1   = Bitmap.createBitmap(maxHeight, dp2px(UNIT_ITEM_WIDTH),Config.ARGB_8888);
		Bitmap bmp2   = Bitmap.createBitmap(maxHeight, dp2px(UNIT_ITEM_WIDTH),Config.ARGB_8888);
		Bitmap bmp3   = Bitmap.createBitmap(maxHeight, dp2px(UNIT_ITEM_WIDTH),Config.ARGB_8888);
		
		Paint  paint = new Paint();
		paint.setColor(unitColor);
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.STROKE);

		Canvas canvas1 = new Canvas(bmp1);
		canvas1.drawLine(0, 0, maxHeight, 0, paint);
       
//		Log.e("max", maxHeight+"");
		
		Canvas canvas2 = new Canvas(bmp2);
//		canvas2.drawLine(maxHeight - midHeight, 0, maxHeight, 0, paint);
		canvas2.drawLine(0, 0, maxHeight-midHeight,0, paint);

//		Log.e("mid", (maxHeight-midHeight)+"");
		
		Canvas canvas3 = new Canvas(bmp3);
		paint.setAlpha(80);
//		canvas3.drawLine(maxHeight - minHeight, 0, maxHeight,  0, paint);
		canvas3.drawLine(0, 0, maxHeight-minHeight,0, paint);
        
//		Log.e("min", (maxHeight-minHeight)+"");
		
		maxDrawable = new BitmapDrawable(bmp1);
		maxDrawable.setBounds(0, 0, maxDrawable.getMinimumWidth(),maxDrawable.getMinimumHeight());
		
		midDrawable = new BitmapDrawable(bmp2);
		midDrawable.setBounds(0, 0, midDrawable.getMinimumWidth(),midDrawable.getMinimumHeight());
		
		minDrawable = new BitmapDrawable(bmp3);
		minDrawable.setBounds(0, 0, minDrawable.getMinimumWidth(),minDrawable.getMinimumHeight());
	
//		markBgBmp = Bitmap.createBitmap( maxHeight * 2 + dp2px((int) maxTextSize),2 * dp2px(UNIT_ITEM_WIDTH),Config.ARGB_8888);
		
//		Canvas canvas4 = new Canvas(markBgBmp);
//		paint.setColor(markColor);
//		canvas4.drawLine(0, 0, markBgBmp.getHeight(), 0, paint);
		
		markBgBmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_point);
		
		
	}

	/**
	 * 设置刻尺的标签，比如品牌什么的
	 * 
	 * @param textTag
	 */
	public void setRulerTag(String textTag) {
		if (textTag == null)
			return;
		resultTagView.setText(textTag);
	}

	public void setRulerHandler(RulerHandler rulerHandler) {
		this.rulerHandler = rulerHandler;
	}

	public void setDefaultExtra(int extra) {
		
	}

	/**
	 * time format is HH:MM 跳转到时间刻度尺的部分，只有在时间轴模式下条件下才能使用
	 * 
	 * @param formatTime
	 */
	public void scrollToTime(String formatTime) {
		Log.i(getClass().getName(), "formatTime = " + formatTime);
		if (mode == MODE_RULER)
			return;
		if (formatTime == null || formatTime.isEmpty())
			return;
		String value[] = formatTime.split(":");
		if (value.length < 2)
			return;
		int minVal = 0;
		Log.i(getClass().getName(), "minVal = " + minVal);
		int hour = Integer.parseInt(value[0]) % 24;
		int minute = Integer.parseInt(value[1]) % 60;
		Log.i(getClass().getName(), "hour is " + hour + ",minute is " + minute);
		float val = hour * 10 + (float) minute / 6;
		Log.i(getClass().getName(), "val = " + val);
		if (val < minVal) {
			verticalScrollerView.smoothScrollTo(0, 0);
			return;
		}
		verticalScrollerView.smoothScrollTo((int) ((val - minVal) * dp2px((int) minUnitSize)), 0);
//		resultView.setText(formatTime);
	}

	/**
	 * 跳转到刻度尺的某个位置
	 * 
	 * @param max
	 *            最大刻度
	 * @param min
	 *            最小刻度
	 * @param val
	 *            最小刻度的浮点部分
	 */
	public void scrollTo(int max, int min, float val) {
		if (min > perUnitCount)
			return;
		int minVal = 0;
		int total = max * 10 + min;
		if (total < minVal) {
			verticalScrollerView.smoothScrollTo(0, 0);
			return;
		}
		int scrollY=(int) ((total - minVal + val) * dp2px((int) 3)*2);
		verticalScrollerView.smoothScrollTo(0, scrollY);
	}

	ScrollViewListener scrollListener = new ScrollViewListener() {

		@Override
		public void onScrollChanged(ScrollType scrollType) {

			switch (scrollType) {
			case IDLE:
			case TOUCH_SCROLL:
			case FLING:
				int newScrollY = verticalScrollerView.getScrollY();
				int bigUnitSize = (dp2px((int) minUnitSize) * perUnitCount);
				int smallUnitSize = dp2px((int) minUnitSize);
				int max = newScrollY / bigUnitSize;
				int min = newScrollY / smallUnitSize % perUnitCount;
				float val = (float) (newScrollY - (max * bigUnitSize) - (min * smallUnitSize)) / (float) smallUnitSize;
				LogUtils.e("scrollY.."+newScrollY);
				showResult(max, min, val);
				break;
			}
		}

	};

	private void showResult(int max, int min, float val) {
		if (max == maxUnitCount) {
			min = 0;
			val = 0f;
		}
		if (mode == MODE_TIMELINE) {
			int hour = max;
			int minute = (int) ((min + val) * 60 / perUnitCount);
			
		}
		if (mode == MODE_RULER) {
			Log.e("ruler", String.format("%.01f", ((float) max + ((float) min + val) / 10) + 20));
		}
		if (rulerHandler != null) {
			rulerHandler.markScrollto(max, min, val);
		}
	}

	public int dp2px(int dp) {
		float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dp * 2 + 0.5f);
	}

	public int px2dp(int px) {
		float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

}
