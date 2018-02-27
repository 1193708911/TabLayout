package com.taikang.tkdoctor.activity.mycenter;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.adapter.MyCollectionsAdapter;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.MyCollectionBean;
import com.taikang.tkdoctor.bean.MyListCollectionBean;
import com.taikang.tkdoctor.bean.MyListCollectionBean.MyCollectionListBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.CancelCollectionBiz;
import com.taikang.tkdoctor.biz.MyCollectionBiz;
import com.taikang.tkdoctor.request.NetCallback;
@ContentView(R.layout.activity_mycollections)
public class MyCollectionsActivity extends BaseActivity implements OnItemClickListener, NetCallback {

	@ViewInject(R.id.imgTitleBack)
	private ImageView imgBack;

	@ViewInject(R.id.imgTitleRight)
	private ImageView imgRight;

	@ViewInject(R.id.txtTitleText)
	private TextView  tvTitle;

	@ViewInject(R.id.tvTitleRight)
	private TextView  tvRight;
	@ViewInject(R.id.txtTitleText)
	private TextView txtTitleText;

	@ViewInject(R.id.lv_mycollections)
	private SwipeMenuListView lvMyCollections;


	private MyListCollectionBean listBean;
	private ArrayList<MyCollectionBean> collectionList;

	private MyCollectionsAdapter mAdapter;

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		imgRight.setVisibility(View.INVISIBLE);
		tvRight.setVisibility(View.INVISIBLE);
		tvTitle.setText(getResources().getString(R.string.my_collections));
		txtTitleText.setText("我的收藏");
		initData();
	}


	private void initData() {
		// TODO Auto-generated method stub
		listBean=new MyListCollectionBean();
		collectionList=new ArrayList<MyCollectionBean>();
		// 暂时不清楚是否需要上拉加载 下拉刷新
		MyCollectionBiz collectionBiz=new MyCollectionBiz();
		collectionBiz.setCallback(this);
		collectionBiz.getMyCollection();
		setListenner();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
	private void setListenner() {
		// TODO Auto-generated method stub
		lvMyCollections.setOnItemClickListener(this);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
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
		lvMyCollections.setMenuCreator(creator);

		// step 2. listener item click event
		lvMyCollections.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				MyCollectionBean item = collectionList.get(position);
				switch (index) {
				case 0:
					//取消收藏处理
					deleteCollection(position);
					break;
				}

			}
		});

		// set SwipeListener
		lvMyCollections.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});

	}


	protected void deleteCollection(int position) {
		// TODO Auto-generated method stub
		CancelCollectionBiz cancelBiz=new CancelCollectionBiz();
		cancelBiz.setCallback(new NetCallback() {
			
			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				ShowLongToast("删除成功");
				initData();
				
			}
			
			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				ShowLongToast("删除失败");
				
			}
		} );
		cancelBiz.cancelCollection(collectionList.get(position).getInformation_id());
	}


	@OnClick(R.id.imgTitleBack)
	private void goback(View view){
		finish();

	}
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	/**
	 * listview点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,MyCollectionInfoActivity.class);
		intent.putExtra("bean",collectionList.get(position) );
		startActivity(intent);

	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}

	@Override
	public void taskSuccess(Response response) {
		// TODO Auto-generated method stub
		listBean=(MyListCollectionBean) response;
		ArrayList<MyCollectionListBean> collectionListBeans=listBean.getResultlist();
		if(collectionListBeans!=null){
			MyCollectionListBean collectionListBean=collectionListBeans.get(0);
			collectionList=collectionListBean.getResultlist();
			mAdapter=new MyCollectionsAdapter(this,collectionList);
			lvMyCollections.setAdapter(mAdapter);
		}else {
			ShowLongToast("亲，您还没有收藏");
		}




	}


	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		ShowLongToast("获取收藏列表失败");

	}
}
