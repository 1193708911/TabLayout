package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.CenterPlanAdapter;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.HealthPlanListBean;
import com.taikang.tkdoctor.bean.MyHealthBean;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.SetRemindBean;
import com.taikang.tkdoctor.biz.DeleteHealthPlanBiz;
import com.taikang.tkdoctor.biz.GetHealthPlanBiz;
import com.taikang.tkdoctor.db.DBDao;
import com.taikang.tkdoctor.db.DBDaoImp;
import com.taikang.tkdoctor.db.RemindDBDaoImp;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.Constants;
import com.umeng.socialize.utils.Log;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

@ContentView(R.layout.activity_healthplan)
public class HealthPlansActivity extends BaseActivity implements OnItemClickListener, NetCallback {
	@ViewInject(R.id.lv_healthPlan)
	private SwipeMenuListView lv_healthPlan;
	@ViewInject(R.id.tvTitleRight)
	private TextView tvTitleRight;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;
	public static final int REQUESTCODE_ADD = 1;
	public static final int REQUESTCODE_UPDATE = 2;
	private ArrayList<MyHealthBean> healthList;
	private MyHealthBean planBean;
	private CenterPlanAdapter adapter;
	private DBDao<MyHealthBean> mDbDao;
	private ArrayList<HealthPlanBean> MyhealthList;
	@Override
	protected void afterViews() {
		super.afterViews();
		mDbDao = new DBDaoImp(this);
		tvTitleRight.setVisibility(View.VISIBLE);
		tvTitleRight.setText("添加计划");
		txtTitleText.setText("健康计划");
		initAdapter();
		//查询数据库
		doQuery();
	}
   
	public void doQuery(){
		RemindDBDaoImp db=new RemindDBDaoImp(this);
		List<SetRemindBean> list=db.queryAll();
		for(SetRemindBean bean:list){
			Log.e("db..planid"+bean.getPlanid()+"..id"+bean.getId());
		}
	}


	// 初始化adapter listview

	private void initAdapter() {
		sendGetHealthPlanRequest();
		MyhealthList = new ArrayList<HealthPlanBean>();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sendGetHealthPlanRequest();
	}

	/**
	 * 发送获取健康计划列表请求
	 */
	private void sendGetHealthPlanRequest() {
		// TODO Auto-generated method stub
		GetHealthPlanBiz healthPlanBiz = new GetHealthPlanBiz();
		healthPlanBiz.setCallback(this);
		healthPlanBiz.getHealthPlans(Constants.DATE_FORMAT.format(new Date()));

	}

	int i = 0;

	private void setListenner() {
		// TODO Auto-generated method stub
		lv_healthPlan.setOnItemClickListener(this);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				i++;
				if (i < 4) {

					return;
				}
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set item title
				deleteItem.setTitle("删除");
				// set item title fontsize
				deleteItem.setTitleSize(18);
				// set item title font color
				deleteItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		lv_healthPlan.setMenuCreator(creator);

		// step 2. listener item click event
		lv_healthPlan.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					// 调用删除接口
					DeleteHealthPlanBiz healthPlanBiz = new DeleteHealthPlanBiz();
					healthPlanBiz.setCallback(new NetCallback() {

						@Override
						public void taskSuccess(Response response) {
							// TODO Auto-generated method stub
							sendGetHealthPlanRequest();
						}

						@Override
						public void taskError(Response response) {
							// TODO Auto-generated method stub

						}
					});
					healthPlanBiz.deleteHealthPlan(MyhealthList.get(position).getPlanid());

					break;
				}
			}
		});
	}

	@OnClick(R.id.imgTitleBack)
	private void goback(View view) {
		this.finish();

	}

	@OnClick(R.id.tvTitleRight)
	private void addPlan(View view) {
		Intent intent = new Intent(HealthPlansActivity.this, AddPlanActivity.class);
		startActivityForResult(intent, REQUESTCODE_ADD);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUESTCODE_ADD && resultCode == RESULT_OK) {
			// MyHealthBean bean=(MyHealthBean)
			// data.getSerializableExtra("planBean");
			// setListAdapter(data);
		}
		if (requestCode == REQUESTCODE_UPDATE && resultCode == RESULT_OK) {

		}

	}

	/**
	 * 监听item点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 这块查询的数据是指定主题的闹钟提醒列表数据
		HealthPlanBean bean = MyhealthList.get(position);
		Intent intent = new Intent(HealthPlansActivity.this, HealthRemindActivity.class);
		intent.putExtra("bean", bean);
		startActivity(intent);
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
	}

	/**
	 * 获取健康计划列表
	 * 
	 * @param response
	 */
	@Override
	public void taskSuccess(Response response) {
		if (response != null) {
			HealthPlanListBean healthPlanListBean = (HealthPlanListBean) response;
			if (healthPlanListBean != null) {
				MyhealthList.clear();
				MyhealthList = healthPlanListBean.getResultlist().get(0).getResultlist();
				if (MyhealthList != null) {
					for(HealthPlanBean bean:MyhealthList){
						Log.e("健康计划ID...."+bean.getPlanid()+"...doPlanId.."+bean.getDoplanid());
					}
					adapter = new CenterPlanAdapter(this, MyhealthList);
					lv_healthPlan.setAdapter(adapter);
					setListenner();
				}
			}
		}

	}

	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub

	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
