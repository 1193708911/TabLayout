package com.taikang.tkdoctor.activity.main;

import android.view.KeyEvent;

import com.lidroid.xutils.view.annotation.ContentView;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.fragment.main.HealthRiskFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskHeightFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesDuanLianFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesEightFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesElevenFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesFiftyFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesFiveFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesFortyFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesFourFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesGaoxueyaFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesGaoxuezhiFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesResultFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesNineFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesTangniaoFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesSevenFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesSeventyFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesSixFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesSixtyFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesTenFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesTengfengXueniaosuanFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesThirtyFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesThreeFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesTwelveFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesTwoFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskQuesWaterFragment;
import com.taikang.tkdoctor.fragment.main.HealthRiskWeightFragment;
import com.taikang.tkdoctor.global.ManBingQuestCache;

/**
 * 健康风险评估问卷
 */
@ContentView(R.layout.activity_health_risk)
public class HealthRiskActivity extends BaseActivity {
	private HealthRiskFragment mRiskFragment;
	private HealthRiskWeightFragment mWeightFragment;
	private HealthRiskHeightFragment mHealthFragment;
	private HealthRiskQuesTangniaoFragment mOneFragment;
	private HealthRiskQuesTangniaoFragment mTangniaoFragment;
	private HealthRiskQuesGaoxueyaFragment mGaoxueyaFragment;
	private HealthRiskQuesGaoxuezhiFragment mGaoxuezhiFragment;
	private HealthRiskQuesTengfengXueniaosuanFragment mTengfengXueniaosuanFragment;
	private HealthRiskQuesTwoFragment mTwoFragment;
	private HealthRiskQuesThreeFragment mThreeFragment;
	private HealthRiskQuesFourFragment mFourFragment;
	private HealthRiskQuesFiveFragment mFiveFragment;
	private HealthRiskQuesSixFragment mSixFragment;
	private HealthRiskQuesSevenFragment mSevenFragment;
	private HealthRiskQuesEightFragment mEightFragment;
	private HealthRiskQuesNineFragment mNineFragment;
	private HealthRiskQuesTenFragment mTenFragment;
	private HealthRiskQuesElevenFragment mElevenFragment;
	private HealthRiskQuesTwelveFragment mTwelveFragment;
	private HealthRiskQuesThirtyFragment mThirtyFragment;
	private HealthRiskQuesFortyFragment mFortyFragment;
	private HealthRiskQuesFiftyFragment mFiftyFragment;
	private HealthRiskQuesSixtyFragment mSixtyFragment;
	private HealthRiskQuesSeventyFragment mSeventyFragment;
	private HealthRiskQuesResultFragment mItentyFragment;
	private HealthRiskQuesDuanLianFragment mDuanlianFragment;
	private HealthRiskQuesWaterFragment mWaterFragment;

	@Override
	protected void afterViews() {
		super.afterViews();
		replaceHealthRiskFragment();// 啟動性別頁面
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ManBingQuestCache.removeData();
	}

	// 性別
	public void replaceHealthRiskFragment() {
		if (mRiskFragment == null) {
			mRiskFragment = new HealthRiskFragment();
		}
		replaceFragment(R.id.content_frame, mRiskFragment);
		deatroyFragment();
	}

	// 身高
	public void replaceHealthRiskHealthFragment() {
		if (mHealthFragment == null) {
			mHealthFragment = new HealthRiskHeightFragment();
		}
		replaceFragment(R.id.content_frame, mHealthFragment);
		deatroyFragment();
	}

	// 体重
	public void replaceHealthRiskWeightFragment() {
		if (mWeightFragment == null) {
			mWeightFragment = new HealthRiskWeightFragment();
		}
		replaceFragment(R.id.content_frame, mWeightFragment);
		deatroyFragment();
	}
	
	//高血压
	public void replaceGaoxueyaFragment() {
		if (mGaoxueyaFragment == null) {
			mGaoxueyaFragment = new HealthRiskQuesGaoxueyaFragment();
		}
		replaceFragment(R.id.content_frame, mGaoxueyaFragment);
		deatroyFragment();
	}
	
	//高血脂
	public void replaceGaoxuezhiFragment() {
		if (mGaoxuezhiFragment == null) {
			mGaoxuezhiFragment = new HealthRiskQuesGaoxuezhiFragment();
		}
		replaceFragment(R.id.content_frame, mGaoxuezhiFragment);
		deatroyFragment();
	}
		
	//疼风/血尿酸
	public void replaceTengfengXueniaoFragment() {
		if (mTengfengXueniaosuanFragment == null) {
			mTengfengXueniaosuanFragment = new HealthRiskQuesTengfengXueniaosuanFragment();
		}
		replaceFragment(R.id.content_frame, mTengfengXueniaosuanFragment);
		deatroyFragment();
	}
	
	//糖尿病
	public void replaceTangniaoFragment() {
		if (mOneFragment == null) {
			mOneFragment = new HealthRiskQuesTangniaoFragment();
		}
		replaceFragment(R.id.content_frame, mOneFragment);
		deatroyFragment();
	}

	// 主食量
	public void replaceTwoFragment() {
		if (mTwoFragment == null) {
			mTwoFragment = new HealthRiskQuesTwoFragment();
		}
		replaceFragment(R.id.content_frame, mTwoFragment);
		deatroyFragment();
	}

	// 发呆
	public void replaceThreeFragment() {
		if (mThreeFragment == null) {
			mThreeFragment = new HealthRiskQuesThreeFragment();
		}
		replaceFragment(R.id.content_frame, mThreeFragment);
		deatroyFragment();
	}

	// 上班恐惧
	public void replaceFourFragment() {
		if (mFourFragment == null) {
			mFourFragment = new HealthRiskQuesFourFragment();
		}
		replaceFragment(R.id.content_frame, mFourFragment);
		deatroyFragment();
	}

	// 健忘
	public void replaceFiveFragment() {
		if (mFiveFragment == null) {
			mFiveFragment = new HealthRiskQuesFiveFragment();
		}
		replaceFragment(R.id.content_frame, mFiveFragment);
		deatroyFragment();
	}

	// 经常做饭
	public void replaceSixFragment() {
		if (mSixFragment == null) {
			mSixFragment = new HealthRiskQuesSixFragment();
		}
		replaceFragment(R.id.content_frame, mSixFragment);
		deatroyFragment();
	}

	// 体重升降
	public void replaceSevenFragment() {
		if (mSevenFragment == null) {
			mSevenFragment = new HealthRiskQuesSevenFragment();
		}
		replaceFragment(R.id.content_frame, mSevenFragment);
		deatroyFragment();
	}

	// 早睡早起
	public void replaceEightFragment() {
		if (mEightFragment == null) {
			mEightFragment = new HealthRiskQuesEightFragment();
		}
		replaceFragment(R.id.content_frame, mEightFragment);
		deatroyFragment();
	}

	// 抽烟
	public void replaceNineFragment() {
		if (mNineFragment == null) {
			mNineFragment = new HealthRiskQuesNineFragment();
		}
		replaceFragment(R.id.content_frame, mNineFragment);
		deatroyFragment();
	}

	// 喝酒
	public void replaceTenFragment() {
		if (mTenFragment == null) {
			mTenFragment = new HealthRiskQuesTenFragment();
		}
		replaceFragment(R.id.content_frame, mTenFragment);
		deatroyFragment();
	}

	// 蔬菜水果
	public void replaceElevenFragment() {
		if (mElevenFragment == null) {
			mElevenFragment = new HealthRiskQuesElevenFragment();
		}
		replaceFragment(R.id.content_frame, mElevenFragment);
		deatroyFragment();
	}

	// 肉摄入量
	public void replaceTwelveFragment() {
		if (mTwelveFragment == null) {
			mTwelveFragment = new HealthRiskQuesTwelveFragment();
		}
		replaceFragment(R.id.content_frame, mTwelveFragment);
		deatroyFragment();
	}

	// 鱼海鲜摄入量
	public void replaceThirtyFragment() {
		if (mThirtyFragment == null) {
			mThirtyFragment = new HealthRiskQuesThirtyFragment();
		}
		replaceFragment(R.id.content_frame, mThirtyFragment);
		deatroyFragment();
	}
	
	// 每天锻炼
	public void replaceDuanLianFragment() {
		if (mDuanlianFragment == null) {
			mDuanlianFragment = new HealthRiskQuesDuanLianFragment();
		}
		replaceFragment(R.id.content_frame, mDuanlianFragment);
		deatroyFragment();
	}

	// 爬楼梯
	public void replaceFortyFragment() {
		if (mFortyFragment == null) {
			mFortyFragment = new HealthRiskQuesFortyFragment();
		}
		replaceFragment(R.id.content_frame, mFortyFragment);
		deatroyFragment();
	}

	// 便秘肚子
	public void replaceFiftyFragment() {
		if (mFiftyFragment == null) {
			mFiftyFragment = new HealthRiskQuesFiftyFragment();
		}
		replaceFragment(R.id.content_frame, mFiftyFragment);
		deatroyFragment();
	}

	// 腿放高处
	public void replaceSixtyFragment() {
		if (mSixtyFragment == null) {
			mSixtyFragment = new HealthRiskQuesSixtyFragment();
		}
		replaceFragment(R.id.content_frame, mSixtyFragment);
		deatroyFragment();
	}

	// 不想见面
	public void replaceSeventyFragment() {
		if (mSeventyFragment == null) {
			mSeventyFragment = new HealthRiskQuesSeventyFragment();
		}
		replaceFragment(R.id.content_frame, mSeventyFragment);
		deatroyFragment();
	}
	
	// 口渴喝水
	public void replaceWaterFragment() {
		if (mWaterFragment == null) {
			mWaterFragment = new HealthRiskQuesWaterFragment();
		}
		replaceFragment(R.id.content_frame, mWaterFragment);
		deatroyFragment();
	}

	// 评估结果
	public void replaceItentyFragment() {
		if (mItentyFragment == null) {
			mItentyFragment = new HealthRiskQuesResultFragment();
		}
		replaceFragment(R.id.content_frame, mItentyFragment);
		deatroyFragment();
	}
	
	public void deatroyFragment(){
		if (mRiskFragment != null) {
			mRiskFragment = null;
		}else if (mWeightFragment != null) {
			mWeightFragment = null;
		}else if (mDuanlianFragment != null) {
			mDuanlianFragment = null;
		}else if (mEightFragment != null) {
			mEightFragment = null;
		}else if (mElevenFragment != null) {
			mElevenFragment = null;
		}else if (mFiftyFragment != null) {
			mFiftyFragment = null;
		}else if (mFiveFragment != null) {
			mFiveFragment = null;
		}else if (mFortyFragment != null) {
			mFortyFragment = null;
		}else if (mFourFragment != null) {
			mFourFragment = null;
		}else if (mHealthFragment != null) {
			mHealthFragment = null;
		}else if (mItentyFragment != null) {
			mItentyFragment = null;
		}else if (mNineFragment != null) {
			mNineFragment = null;
		}else if (mOneFragment != null) {
			mOneFragment = null;
		}else if (mSevenFragment != null) {
			mSevenFragment = null;
		}else if (mSeventyFragment != null) {
			mSeventyFragment = null;
		}else if (mSixFragment != null) {
			mSixFragment = null;
		}else if (mSixtyFragment != null) {
			mSixtyFragment = null;
		}else if (mTenFragment != null) {
			mTenFragment = null;
		}else if (mThirtyFragment != null) {
			mThirtyFragment = null;
		}else if (mThreeFragment != null) {
			mThreeFragment = null;
		}else if (mTwelveFragment != null) {
			mTwelveFragment = null;
		}else if (mTwoFragment != null) {
			mTwoFragment = null;
		}else if (mWaterFragment != null) {
			mWaterFragment = null;
		}
	}
	
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	// TODO Auto-generated method stub  
	if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){  
	    finish();
	}  
	    return false;  
	}  

}
