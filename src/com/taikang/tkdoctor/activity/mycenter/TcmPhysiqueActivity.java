package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.TcmPhysiqueTestAdapter;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.Question;
import com.taikang.tkdoctor.bean.QuestionBean;
import com.taikang.tkdoctor.bean.ReportInfoBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.QuestionBean.Questions;
import com.taikang.tkdoctor.bean.TcmPhisicBean.MyTcmPhicBean;
import com.taikang.tkdoctor.bean.TcmPhisicListBean;
import com.taikang.tkdoctor.biz.QuestionsBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.db.ReportDBDaoImp;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.Util;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.IChooseResult;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

@ContentView(R.layout.activity_tcmphysique)
public class TcmPhysiqueActivity extends BaseActivity implements IChooseResult {

	@ViewInject(R.id.imgTitleBack)
	private ImageView imgBack;
	@ViewInject(R.id.imgTitleRight)
	private ImageView imgRight;
	@ViewInject(R.id.txtTitleText)
	private TextView tvTitle;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvRight;
	@ViewInject(R.id.lv_questions)
	private ActionSlideExpandableListView mExpandableListView;
	private MyTcmPhicBean myTcmPhicBean;

	@ViewInject(R.id.btn_submit)
	private Button btnSubmit;

	private ArrayList<Question> mQuestions;
	private TcmPhysiqueTestAdapter mAdapter;
	private static final int UPADTE_TCM_QUESTIONS = 0; 
	private String mSex="1";//默认是男
	private String mDefault="0";
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPADTE_TCM_QUESTIONS:
				//过滤掉不合法的数据
				filterData();
				setAdapter();
				break;
			default:
				break;
			}

		};

	};

	public void filterData(){
		int size=mQuestions.size();
		LogUtils.d("mQuestion.size()..1.."+mQuestions.size());
		int count=0;
		for(int index=0;index<size;index++){
			Question question=mQuestions.get(index);
			String sex=question.getSex();
			if(!mSex.equals(mDefault) && !mSex.equals(sex)){
				mQuestions.remove(question);
				size--;
				LogUtils.d("nv.."+(count++));
			}
		}
		LogUtils.d("mQuestion.size()..2.."+mQuestions.size());
	}


	public void setAdapter() {
		mExpandableListView.setAdapter(buildMyData(), this);
	}

	@Override
	protected void afterViews() {
		super.afterViews();
		imgRight.setVisibility(View.INVISIBLE);
		tvRight.setVisibility(View.INVISIBLE);
		tvTitle.setText(getResources().getString(R.string.tcmphysique_test));
		initData();
	}


	@OnClick(R.id.imgTitleBack)
	public void back(View v) {
		finish();
	}

	private void initData() {
		QuestionsBiz biz = new QuestionsBiz();
		biz.setCallback(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				QuestionBean bean = (QuestionBean) response;
				ArrayList<QuestionBean.Questions> questions = bean.getResultlist();
				Questions qustion = questions.get(0);
				ArrayList<Question> list = qustion.getQuestions();
				mQuestions = list;
				mHandler.sendEmptyMessage(UPADTE_TCM_QUESTIONS);
			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub

			}
		});
		biz.getTcmPhysiqueQuestions("1", PreferencesUtil.getString("USER_PHONE"));

	}

	public ListAdapter buildMyData() {
		mAdapter = new TcmPhysiqueTestAdapter(this, mQuestions);
		return mAdapter;
	}

	@Override
	public void setChooseResult(int position, int chooseIndex) {
		// TODO Auto-generated method stub
		Question bean = mQuestions.get(position);
		bean.setChooseindex(chooseIndex);
		mAdapter.notifyDataSetChanged();
		mExpandableListView.setSelection(position);

	}

	@OnClick(R.id.btn_submit)
	public void submitTestResult(View v) {
		//首先检查下每个问题是否都完成了选择
		boolean isLeftQuestion=false;
		for(Question s:mQuestions){
			if(s.getChooseindex()==-1){
				isLeftQuestion=true;
				break;
			}
		}
		if(isLeftQuestion){
			Util.showToast("您还有问题尚未选择，不能提交");
			return;
		}

		QuestionsBiz biz=new QuestionsBiz();
		biz.setCallback(new NetCallback() {



			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				if(response!=null){
					TcmPhisicListBean  resultlist=(TcmPhisicListBean) response;
					if(resultlist!=null){
						//在中医体质获取数据之后跳转之前先保存一份数据到本地数据库中
						myTcmPhicBean=resultlist.getResultlist().get(0).getResultlist().get(0);
						String url=ServerConfig.SERVER_PATH+myTcmPhicBean.getUrl()+"&tz="+myTcmPhicBean.getType()+"&uid="+MainApplication.getInstance().getUser().getUserId();
//						ReportDBDaoImp reportdb=new ReportDBDaoImp(TcmPhysiqueActivity.this);
//						ReportInfoBean item=new ReportInfoBean();
//						item.setReport_type(getIntent().getStringExtra("report_type")==null?Constants.TCMPHYSIQUE:getIntent().getStringExtra("report_type"));
//						item.setReport_url(url);
//						reportdb.insert(item);
//						LogUtils.d("tcmphysiqueUrl.."+url);
						PreferencesUtil.putString("tizhi", url);
						Intent intent=new Intent(TcmPhysiqueActivity.this,TcmPhysiqueResultActivity.class);
						intent.putExtra("report_physique", url);
						intent.putExtra("report_type",Constants.TCMPHYSIQUE);
						startActivity(intent);
						finish();
					}
				}

			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub

			}
		});
		biz.submitTcmPhysiqueQuestions(mQuestions);
	}

}
