package com.taikang.tkdoctor.activity.selfdiagnosis;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.requestback.SymptonListBean.SymptonBean;
import com.taikang.tkdoctor.bean.MyQuestionList.Questions;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetAutognosisQuestionBiz;
import com.taikang.tkdoctor.fragment.selfdiagnosis.SelfDiagnosisQuesFragment;
import com.taikang.tkdoctor.fragment.selfdiagnosis.SelfDiagnosisResultFragment;
import com.taikang.tkdoctor.request.NetCallback;

@ContentView(R.layout.activity_selfdiagnosis)
public class SelfDiagnosisActivity extends BaseActivity {

	private SelfDiagnosisQuesFragment questFragment;
	private SelfDiagnosisResultFragment resultFragment;
	private SymptonBean symptonBean;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initData();
		replaceQuestFragment();
	}
	private void initData() {
		// TODO Auto-generated method stub
		symptonBean=(SymptonBean) getIntent().getSerializableExtra("bean");
	}

	public void replaceQuestFragment() {
		if (questFragment == null) {
			questFragment = new SelfDiagnosisQuesFragment();

		}
		Bundle bundle=new Bundle();
		bundle.putSerializable("bean", symptonBean);
		questFragment.setArguments(bundle);
		replaceFragment(R.id.content_frame_self, questFragment);
	}

	public void replaceResltFragment(Questions questions) {
		if (resultFragment == null) {
			resultFragment = new SelfDiagnosisResultFragment();
		}
		Bundle bundle=new Bundle();
		bundle.putSerializable("bean", questions);
		resultFragment.setArguments(bundle);
		replaceFragment(R.id.content_frame_self, resultFragment);
	}
	
}
