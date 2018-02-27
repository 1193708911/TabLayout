package com.taikang.tkdoctor.customview;

import java.io.Serializable;
import java.util.ArrayList;

import com.cn.afinal.FinalBitmap;
import com.lidroid.xutils.util.LogUtils;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.information.ClassInfoDetailAct;
import com.taikang.tkdoctor.base.BaseView;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.ClassContent;
import com.taikang.tkdoctor.bean.ClassroomBean;
import com.taikang.tkdoctor.bean.Info;
import com.taikang.tkdoctor.bean.InfoResponseModel;
import com.taikang.tkdoctor.bean.InfoResponseModel.Infos;
import com.taikang.tkdoctor.bean.InfoTop;
import com.taikang.tkdoctor.bean.InfoTopResponseModel;
import com.taikang.tkdoctor.bean.InfoTopResponseModel.InfoTops;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetInformationBiz;
import com.taikang.tkdoctor.customview.XListView.IXListViewListener;
import com.taikang.tkdoctor.global.ClassroomType;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.LoadNetPhoto;
import com.taikang.tkdoctor.util.NetUtil;
import com.taikang.tkdoctor.util.Util;
import com.taikang.tkdoctor.util.fileUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: ClassInformationView
 * @Description: 最新资讯view
 * @author helongfei
 * @date
 * 
 */
public class ClassInformationView extends BaseView implements IXListViewListener, OnItemClickListener {

	private final int CACHESIZE = 30;
	private LinearLayout photoLinear, listLinear, errorLinear;
	private TextView errorText;
	private ArrayList<Info> mClassInfo = new ArrayList<Info>();// 新的数据
	private Infos mClassInfoBean;
	private ArrayList<InfoTop> mInfoTop = new ArrayList<InfoTop>();
	private InfoTops mInfoTopBean;
	private int currentPageIndex = 0;
	private ProgressDialog pd;
	private XListView mListview;;
	private ClassContentAdapter mClassAapter;
	private Handler mHandler;
	private static final int START_LOAD = 0;
	private static final int FIAL_LOAD = 2;
	private static final int MORE_LOAD = 3;
	private static final int ERROR = 4;
	private static final int NO_NET = 5;
	private static final int NETWORK_UNAVAILABLE = 6;
	private static final int UPDATE_TOP_PICS=7;

	private boolean isLoadMore = true;
	private boolean isFlush = true;
	private int total_data_number;// 总条数
	private int data_number = 10;;// 每次加载的条数
	private FinalBitmap fb;

	@SuppressLint("HandlerLeak")
	private void initHandler() {
		mHandler = new Handler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case UPDATE_TOP_PICS:
					if(mClassAapter!=null){
						initAdapter();
					}else{						
						mClassAapter.notifyDataSetChanged();
					}
					break;
				case START_LOAD:
					cancelDialog();
					mListview.stopRefresh();
					ArrayList<Info> flushClassContent = (ArrayList<Info>) msg.obj;
					mClassInfo = flushClassContent;
					Info info=new Info();//在首位加载一个空数据
					mClassInfo.add(0, info);
					mListview.setVisibility(View.VISIBLE);
					errorLinear.setVisibility(View.GONE);
					if (mClassAapter == null) {
						Log.i("ee", "initAdapter()");
						initAdapter();
					} else {
						Log.i("ee", "notifyDataSetChanged()");
						mClassAapter.notifyDataSetChanged();
					}
					if (mClassInfo.size() < total_data_number) {
						isLoadMore = true;
					} else {
						isLoadMore = false;
					}
					if (!NetUtil.isNetworkAvailable(act)) {
						isLoadMore = false;
					}
					controlLoad();
					ruflushData();
					break;
				case MORE_LOAD:
					cancelDialog();
					mListview.stopLoadMore();
					ArrayList<Info> moreClassContent = (ArrayList<Info>) msg.obj;
					mClassInfo.addAll(moreClassContent);
					if (mClassInfo.size() < total_data_number) {
						isLoadMore = true;
					} else {
						isLoadMore = false;
					}
					if (!NetUtil.isNetworkAvailable(act)) {
						isLoadMore = false;
					}
					controlLoad();
					mClassAapter.notifyDataSetChanged();
					break;
				case FIAL_LOAD:
					cancelDialog();
					if (mClassInfo.size() == 0) {
						mListview.setVisibility(View.GONE);
						errorLinear.setVisibility(View.VISIBLE);
						errorText.setText("暂无数据");
						Toast.makeText(act, "暂无数据", 0).show();
					}
					break;
				case ERROR:
					cancelDialog();
					if (mClassInfo.size() == 0) {
						mListview.setVisibility(View.GONE);
						errorLinear.setVisibility(View.VISIBLE);
						errorText.setText("加载数据出错");
						Toast.makeText(act, "加载数据出错", 0).show();
					}
					break;
				case NO_NET:
					cancelDialog();
					Toast.makeText(act, "未发现存储卡，无法使用离线功能", Toast.LENGTH_SHORT).show();
					break;
				case NETWORK_UNAVAILABLE:
					cancelDialog();
					Toast.makeText(act, "网络无连接，请检查网络", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};
	}


	protected void initAdapter() {
		// TODO Auto-generated method stub
		mClassAapter = new ClassContentAdapter();
		mListview.setAdapter(mClassAapter);
		mListview.setXListViewListener(ClassInformationView.this);
		Log.i("cc", "adapter..");
	}

	public ClassInformationView(Activity act, int index) {
		super(act, R.layout.class_infomation_view);
		// super(act, R.layout.class_infomation_view_latest);
		currentPageIndex = index;
		initWidget();
		initHandler();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (currentPageIndex == 0) {
			startLoadData();
		}
	}

	public void ruflushData() {
		loadNetPhotoData();
	}

	public void loadData() {
		// 在切换之前就清空数据
		mClassInfo.clear();
		if (mClassInfo.size() == 0) {
			controlLoad();
			startLoadData();
		}
	}

	public void controlLoad() {
		mListview.setPullLoadEnable(isLoadMore);
		mListview.setPullRefreshEnable(isFlush);
	}

	private void startLoadData() {
		// TODO Auto-generated method stub
		showDialog("正在获取数据...");
		errorText.setText("正在加载数据...");
		// loadNetData(mClassContent.size() / data_number, data_number,
		// START_LOAD);
		loadNetData(1, data_number, START_LOAD);
	}


	/**
	 * 加载ListView第一项图片
	 */
	public void loadNetPhotoData(){
		new Thread() {
			public void run() {
				try {
					if (NetUtil.isNetworkAvailable(act)) {
						GetInformationBiz infoTopBiz = new GetInformationBiz();
						infoTopBiz.setNetCallBack(new NetCallback() {
							@Override
							public void taskSuccess(Response response) {
								InfoTopResponseModel model = (InfoTopResponseModel) response;
								mInfoTopBean = model.getResultlist().get(0);
								ArrayList<InfoTop> listTop = mInfoTopBean.getResultlist();
								for (InfoTop top : listTop) {
									LogUtils.d("url" + top.getPicurl() + ".." + top.getTitle());
								}
								mHandler.sendEmptyMessage(UPDATE_TOP_PICS);
							}

							@Override
							public void taskError(Response response) {
								// TODO Auto-generated method stub

							}
						});
						infoTopBiz.GetInformationTop("2015-10-26", ClassroomType.getTypeForHan(currentPageIndex));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}



	/**
	 * 
	 * @param page_number
	 *            当前的页数
	 * @param data_number
	 *            每次加载的条数
	 * @param type
	 *            资讯类型
	 */
	private void loadNetData(final int page_number, final int data_number, final int type) {
		new Thread() {
			public void run() {
				try {
					if (NetUtil.isNetworkAvailable(act)) {
						GetInformationBiz infoBiz = new GetInformationBiz();
						infoBiz.setNetCallBack(new NetCallback() {
							@Override
							public void taskSuccess(Response response) {
								InfoResponseModel model = (InfoResponseModel) response;
								mClassInfoBean = model.getResultlist().get(0);
								mClassInfoBean.setResultCode(model.getResultCode());
								mClassInfo = mClassInfoBean.getResultlist();
								for(Info info:mClassInfo){
									info.setUId(model.getUId());
								}
								if (mClassInfo != null) {
									switchType(mClassInfoBean, type);
								} else {
									mHandler.sendEmptyMessage(ERROR);
								}
							}

							@Override
							public void taskError(Response response) {
								// TODO Auto-generated method stub

							}
						});
						infoBiz.GetInformations(MainApplication.getInstance().getUser().getUserPhone(),
								ClassroomType.getTypeForHan(currentPageIndex), 
								page_number, data_number);
					} else {
						// 暂时不用本地缓存数据
						mHandler.sendEmptyMessage(NETWORK_UNAVAILABLE);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(ERROR);
				}
			};
		}.start();
	}

	protected void switchType(Infos currentClassroom, int type) {
		// TODO Auto-generated method stub
		switch (type) {
		case START_LOAD:
			if ("0".equals(currentClassroom.getResultCode())) {
				if (currentClassroom.getResultlist().size() == 0) {
					mHandler.sendEmptyMessage(FIAL_LOAD);
				} else {
					total_data_number = Integer.valueOf(currentClassroom.getCount());// 此分类下所有咨询的条数
					LogUtils.d("total_data_number..."+total_data_number);
					Message msg = mHandler.obtainMessage(START_LOAD, currentClassroom.getResultlist());
					mHandler.sendMessage(msg);
				}
			} else {
				mHandler.sendEmptyMessage(ERROR);
			}
			break;
		case MORE_LOAD:
			if ("0".equals(currentClassroom.getResultCode())) {
				if (currentClassroom.getResultlist().size() == 0) {
					mHandler.sendEmptyMessage(FIAL_LOAD);
				} else {
					total_data_number = Integer.valueOf(currentClassroom.getCount());
					Message msg = mHandler.obtainMessage(MORE_LOAD, currentClassroom.getResultlist());
					mHandler.sendMessage(msg);
				}
			} else {
				mHandler.sendEmptyMessage(ERROR);
			}
			break;
		}
	}

	private void initWidget() {
		// TODO Auto-generated method stub
		errorLinear = (LinearLayout) view.findViewById(R.id.errorLinear);
		photoLinear = (LinearLayout) view.findViewById(R.id.photoLinear);
		listLinear = (LinearLayout) view.findViewById(R.id.listLinear);
		mListview = (XListView) view.findViewById(R.id.listView);
		mListview.setOnItemClickListener(this);
		errorText = (TextView) view.findViewById(R.id.errorText);
		initDialog();
		fb = LoadNetPhoto.getInstance().getFinalBit(act);
	}

	class ClassContentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mClassInfo.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mClassInfo.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = act.getLayoutInflater().inflate(R.layout.class_info_list_item, null);
			LinearLayout ll = (LinearLayout) act.getLayoutInflater().inflate(R.layout.list_item_topimages, null);
			if (position == 0) {
				if (mInfoTopBean != null) {
					ll.removeAllViews();
					ClassNavigaPhotoView photoView = new ClassNavigaPhotoView(act, mInfoTopBean);
					ll.addView(photoView.getView());
				}
				return ll;
			} else {
				//				position = position - 1;
				Info classContent = mClassInfo.get(position);
				LogUtils.d("informationId..."+classContent.getInformationid()+"..pos.."+position);
				Button labelButton = (Button) view.findViewById(R.id.labelButton);
				ImageView contentImage = (ImageView) view.findViewById(R.id.contentImage);
				TextView titleText = (TextView) view.findViewById(R.id.titleText);
				TextView contentText = (TextView) view.findViewById(R.id.contentText);
				TextView txtDate=(TextView) view.findViewById(R.id.txtDate);
				TextView txtTitle=(TextView) view.findViewById(R.id.txtTitle);
				labelButton.setText(classContent.getType());
				labelButton.setFocusable(false);
				titleText.setText(classContent.getTitle());
				txtTitle.setText(classContent.getType());
				contentText.setText(classContent.getSummary());
				txtDate.setText(classContent.getDates());
				if (classContent.getPicurl() != null) {
					fb.configLoadingImage(R.drawable.pic_load);
					fb.configLoadfailImage(R.drawable.pic_load);
					fb.configBitmapMaxHeight(Util.dpToPx(act, 70));
					fb.configBitmapMaxWidth(Util.dpToPx(act, 100));
					contentImage.setTag(classContent.getPicurl());
					fb.display(contentImage, classContent.getPicurl());
				} else {
					contentImage.setVisibility(View.INVISIBLE);
				}
			}
			return view;
			// ViewHold viewHould = null;
			// ClassContent classContent = mClassContent.get(position);
			// if (convertView == null) {
			// viewHould = new ViewHold();
			// convertView = act.getLayoutInflater().inflate(
			// R.layout.class_info_list_item, null);
			// viewHould.labelButton = (Button) convertView
			// .findViewById(R.id.labelButton);
			// viewHould.contentImage = (ImageView) convertView
			// .findViewById(R.id.contentImage);
			// viewHould.titleText = (TextView) convertView
			// .findViewById(R.id.titleText);
			// viewHould.contentText = (TextView) convertView
			// .findViewById(R.id.contentText);
			// convertView.setTag(viewHould);
			// } else {
			// viewHould = (ViewHold) convertView.getTag();
			// }
			// viewHould.labelButton.setText(classContent.getCategory());
			// viewHould.labelButton.setFocusable(false);
			// viewHould.titleText.setText(classContent.getTitle());
			// viewHould.contentText.setText(classContent.getSummary());
			// Log.i("cc",
			// classContent.getCategory()+"...."+classContent.getTitle());
			// if (classContent.getImgUrl() != null) {
			// fb.configLoadingImage(R.drawable.pic_load);
			// fb.configLoadfailImage(R.drawable.pic_load);
			// fb.configBitmapMaxHeight(Util.dpToPx(act, 70));
			// fb.configBitmapMaxWidth(Util.dpToPx(act, 100));
			// viewHould.contentImage.setTag(classContent.getImgUrl());
			// fb.display(viewHould.contentImage, classContent.getImgUrl());
			// } else {
			// viewHould.contentImage.setVisibility(View.INVISIBLE);
			// }
			// return convertView;

		}
	}

	static class ViewHold {
		private Button labelButton;
		private ImageView contentImage;
		private TextView titleText, contentText;
		private TextView txtDate;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		flushLoadData();
	}

	/**
	 * 下拉刷新数据
	 */
	private void flushLoadData() {
		// TODO Auto-generated method stub
		showDialog("正在获取数据...");
		// mClassContent.clear();
		loadNetData(1, data_number, START_LOAD);
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		moreLoadDada();
	}

	/**
	 * 上拉加载更多数据
	 */
	private void moreLoadDada() {
		// TODO Auto-generated method stub
		showDialog("正在获取数据...");
		loadNetData(mClassInfo.size() / data_number, data_number, MORE_LOAD);
	}

	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}

	public void setFlush(boolean isFlush) {
		this.isFlush = isFlush;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		// 点击进入那块要重新查看
		// HomepageDataManager.getInstance().getmClick().addVitalityValue(2);
		// HomepageDataManager.getInstance().getmClick().addParticipationValue(1);
		Info mClassContent = (Info) arg0.getItemAtPosition(arg2);
		LogUtils.d("informationid.."+mClassContent.getInformationid());
		Intent intent = new Intent(act, ClassInfoDetailAct.class);
		intent.putExtra("classDteail", (Serializable) mClassContent);
		act.startActivity(intent);
	}

	/**
	 * 组装请求参数
	 * 
	 * @param page_number
	 *            当前条目数
	 * @param data_number
	 *            总条目数
	 * @return
	 */
	public String assemblyRequest(int page_number, int data_number) {
		StringBuilder sb = new StringBuilder();
		sb.append("category_slug_name").append("=").append(ClassroomType.getTypeForEn(currentPageIndex)).append("&")
		.append("page_number").append("=").append(page_number).append("&").append("data_number").append("=")
		.append(data_number);
		return sb.toString();
	}

	public void showDialog(String message) {
		initDialog();
		pd.setMessage(message);
		pd.show();
	}

	public void initDialog() {
		// TODO Auto-generated method stub
		if (pd == null) {
			pd = new ProgressDialog(act);
		}
	}

	public void showDialog() {
		initDialog();
		pd.show();
	}

	public void cancelDialog() {
		if (pd != null && pd.isShowing()) {
			pd.cancel();
		}
		if (mListview != null) {
			mListview.stopLoadMore();
			mListview.stopRefresh();
		}
	}

}
