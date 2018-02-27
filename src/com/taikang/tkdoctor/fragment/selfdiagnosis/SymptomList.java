package com.taikang.tkdoctor.fragment.selfdiagnosis;

import java.util.ArrayList;

import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.selfdiagnosis.SelfDiagnosisActivity;
import com.taikang.tkdoctor.adapter.SymptomListAdapter;
import com.taikang.tkdoctor.base.requestback.GetSymptonListBean;
import com.taikang.tkdoctor.base.requestback.SymptonListBean.SymptonBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.HealthAutognosisBiz;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.ShowLogin;
import com.taikang.tkdoctor.util.Util;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SymptomList implements OnClickListener, OnItemClickListener{

	private Context mContext;
	private LinearLayout mLayout;
	private ListView mListView;
	private ArrayList<String> mListNames;
	private SymptomListAdapter symptomListAdapter;
	public String position;
	private String sex,age,bodyRegion,around;
	private HealthAutognosisBiz netList;
	private BodySelectCallback callback;  //定义： 0： 全身，1:头部 ，2:颈部 ， 3：手臂， 4：躯干 ， 5：生殖器官 ， 6：臀部 ，7:腿部

	private ImageView mIvAll,mIvHead,mIvNeck,mIvHand,mIvHorso,mIvOrgan,mIvLeg,mIvHip;
	private ArrayList<SymptonBean> symptonList;
	public SymptomList( ){

	}

	public SymptomList(Context context, LinearLayout ll ,ListView listView, BodySelectCallback callback){
		this.mContext = context;
		this.mLayout = ll;
		this.mListView = listView;
		this.callback = callback;
		//		this.sex = sex;
		//		this.mian = mian;
		initView();
	}

	public void showList(String position,int currentState, String age){
		this.position = position;
		this.age = age;
		getManOrWoman(currentState);
		if (position.equals("头部")) {
			mIvHead.performClick();
		}else if (position.equals("颈部")) {
			mIvNeck.performClick();
		}else if (position.equals("躯干")) {
			mIvHorso.performClick();
		}else if (position.equals("手臂")) {
			mIvHand.performClick();
		}else if (position.equals("生殖器官")) {
			mIvOrgan.performClick();
		}else if (position.equals("臀部")) {
			mIvHip.performClick();
		}else if (position.equals("腿部")) {
			mIvLeg.performClick();
		}

	}

	private void getManOrWoman(int currentState) {
		switch (currentState) {
		case 1:
			sex = "1";//男
			around = "0";//正面
			break;
		case 2:
			sex = "1";//男
			around = "1";//背面	
			break;
		case 3:
			sex = "0";//女
			around = "0";//正面
			break;
		case 4:
			sex = "0";//女
			around = "1";//背面 
			break;

		default:
			break;
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		mIvAll = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_all);
		mIvHand = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_hand);
		mIvHead = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_head);
		mIvHip = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_hip);
		mIvHorso = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_horso);
		mIvLeg = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_leg);
		mIvNeck = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_neck);
		mIvOrgan = (ImageView) mLayout.findViewById(R.id.iv_zhengzhuang_list_organ);

		mIvAll.setOnClickListener(this);
		mIvHand.setOnClickListener(this);
		mIvHead.setOnClickListener(this);
		mIvHip.setOnClickListener(this);
		mIvHorso.setOnClickListener(this);
		mIvLeg.setOnClickListener(this);
		mIvNeck.setOnClickListener(this);
		mIvOrgan.setOnClickListener(this);

		netList = new HealthAutognosisBiz();
		//		mListNames = new ArrayList<String>();
		symptonList=new ArrayList<SymptonBean>();

	}

	@Override
	public void onClick(View v) {
		resetImageView();
		switch (v.getId()) {
		case R.id.iv_zhengzhuang_list_all:
			mIvAll.setImageResource(R.drawable.item_all_selected);
			callback.selector(0);
			bodyRegion = "全身症状";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_hand:
			mIvHand.setImageResource(R.drawable.item_hand_selected);
			callback.selector(3);
			bodyRegion = "手臂";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_head:
			mIvHead.setImageResource(R.drawable.item_head_selected);
			callback.selector(1);
			bodyRegion = "头部";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_hip:
			mIvHip.setImageResource(R.drawable.item_hip_selected);
			callback.selector(6);
			bodyRegion = "臀部";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_horso:
			mIvHorso.setImageResource(R.drawable.item_horso_selected);
			callback.selector(4);
			bodyRegion = "躯干";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_leg:
			mIvLeg.setImageResource(R.drawable.item_leg_selected);
			callback.selector(7);
			bodyRegion = "腿部";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_neck:
			mIvNeck.setImageResource(R.drawable.item_neck_selected);
			callback.selector(2);
			bodyRegion = "颈部";
			requestNet(bodyRegion);
			break;
		case R.id.iv_zhengzhuang_list_organ:
			mIvOrgan.setImageResource(R.drawable.item_organ_selected);
			callback.selector(5);
			bodyRegion = "生殖区";
			requestNet(bodyRegion);
			break;
		default:
			break;
		}
	}

	private void requestNet(String bodyRegion) {
		LogUtils.d(sex + "|" + age + "|" + bodyRegion);
		netList.getSymptonList(sex,age.replace("岁", ""), around, bodyRegion, new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				if(response!=null){
//					Util.showToast("成功了");
					GetSymptonListBean symptonListBean=(GetSymptonListBean) response;
					if(symptonListBean!=null){
						symptonList.clear();
						symptonList=symptonListBean.getResultlist().get(0).getResultlist();
						symptomListAdapter = new SymptomListAdapter(mContext, symptonList);
						mListView.setAdapter(symptomListAdapter);
						symptomListAdapter.notifyDataSetChanged();
						setListenner();
					}

				}
			}
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				Util.showToast("失败了!");
			}
		});
	}
	private void setListenner() {
		// TODO Auto-generated method stub
		mListView.setOnItemClickListener(this);
	}
	private void resetImageView() {
		mIvAll.setImageResource(R.drawable.item_all_default);
		mIvHand.setImageResource(R.drawable.item_hand_default);
		mIvHead.setImageResource(R.drawable.item_head_default);
		mIvHip.setImageResource(R.drawable.item_hip_default);
		mIvHorso.setImageResource(R.drawable.item_horso_default);
		mIvLeg.setImageResource(R.drawable.item_leg_default);
		mIvNeck.setImageResource(R.drawable.item_neck_default);
		mIvOrgan.setImageResource(R.drawable.item_organ_default);
	}
	/**
	 * 点击症状跳转到症状页面
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		boolean isLogin = PreferencesUtil.getBoolean("isLogin");
		boolean isActive = PreferencesUtil.getBoolean("isActive");
		if (!isLogin) {
			ShowLogin.showLoginWindow(mContext, (View)mLayout.getParent());
			return;
		}
//		if (!isActive) {
//			Util.showToast("此功能只对激活用户开放！");
//			return;
//		}
		SymptonBean symptonBean=symptonList.get(arg2);
		Intent intent=new Intent(mContext,SelfDiagnosisActivity.class);
		intent.putExtra("bean", symptonBean);
		PreferencesUtil.putString("sex", sex);
		PreferencesUtil.putString("age", age.replace("岁", ""));
		mContext.startActivity(intent);

	}
}
