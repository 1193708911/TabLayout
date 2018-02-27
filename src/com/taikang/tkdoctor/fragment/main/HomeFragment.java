package com.taikang.tkdoctor.fragment.main;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.sword.dialog.MyAlertDialog;

import com.android.CustomGallery.BitmapScaleDownUtil;
import com.android.CustomGallery.GalleryFlow;
import com.android.CustomGallery.ImageAdapter;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.ChooseCitysActivity2;
import com.taikang.tkdoctor.activity.main.ChrDiseaseRiskActivity;
import com.taikang.tkdoctor.activity.main.HealthRiskActivity;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.activity.main.HomeWebInfoActivity;
import com.taikang.tkdoctor.activity.main.LoginActivity;
import com.taikang.tkdoctor.activity.main.SeasonThreapyActivityNew;
import com.taikang.tkdoctor.activity.mycenter.MyCenterActivity;
import com.taikang.tkdoctor.activity.mycenter.TcmPhysiqueActivity;
import com.taikang.tkdoctor.adapter.HealthPlanAdapter;
import com.taikang.tkdoctor.base.BaseFragment;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.AdvertiList.Adavertisement;
import com.taikang.tkdoctor.bean.AdvertisementList;
import com.taikang.tkdoctor.bean.CharacterParser;
import com.taikang.tkdoctor.bean.HealthPlanListBean;
import com.taikang.tkdoctor.bean.LocationBean;
import com.taikang.tkdoctor.bean.MyHealthPlanListBean.HealthPlanBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetHealthPlanBiz;
import com.taikang.tkdoctor.biz.HomeBiz;
import com.taikang.tkdoctor.biz.UpdateHealthPlanBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.customview.HealthNoScrollListView;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.requestcallback.BaiduLocationCallBack;
import com.taikang.tkdoctor.util.Constants;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.TimeUtil;
import com.taikang.tkdoctor.util.Util;

public class HomeFragment extends BaseFragment implements OnItemClickListener, BaiduLocationCallBack, 
			BaseSliderView.OnSliderClickListener{

	@ViewInject(R.id.imgTitleBack)
	private ImageView mImgback;// 返回按钮

	@ViewInject(R.id.imgTitleRight)
	private ImageView mImgLogin;// 登陆按钮

	@ViewInject(R.id.iv_season)
	private ImageView mImgSeason;// 节气养生

	@ViewInject(R.id.tvTitleRight)
	private TextView mTvLogin;// 登录字样

	@ViewInject(R.id.gl_flow)
	private GalleryFlow mGalleryFlow;

	@ViewInject(R.id.lv_plans)
	private HealthNoScrollListView mListViewHealthPlans;

	@ViewInject(R.id.iv_weather)
	private ImageView mIvWeather;

	@ViewInject(R.id.iv_left)
	private ImageView mIvDateLeft;

	@ViewInject(R.id.iv_right)
	private ImageView mIvDateRight;

	@ViewInject(R.id.tv_date)
	private TextView mTvDate;

	@ViewInject(R.id.tv_weather)
	private TextView mTvWeather;
	
	//start
	@ViewInject(R.id.slider)
	private SliderLayout mAdvertSlider;
	private boolean isAdShow = false;
	//end

	private ImageAdapter mImageAdapter;

	private HealthPlanAdapter mHealthPlanAdapter;

	private ArrayList<HealthPlanListBean> mListHealthBeans;
	private ArrayList<HealthPlanBean> healtBeans;
	private ArrayList<Adavertisement> resultlist;
	private int[] mGallerys = { R.drawable.banner01, R.drawable.banner02, R.drawable.banner03, R.drawable.banner04,
			R.drawable.banner05 };

	private LocationClient mLocationClient;

	private boolean mLogined = false;
	// 图片缩放倍率（相对屏幕尺寸的缩小倍率）
	public static final int SCALE_FACTOR = 8;
	// 图片间距（控制各图片之间的距离）
	private final int GALLERY_SPACING = -50;

	// 请求的常数
	private static final int CHANGE_CURRENT_IMAGEVIEW = 0;
	private static final int GET_PM25 = 1;
	private static final int GET_BANNER_IMAGES = 2;

	private static int push_days = 0;

	private static final int HEALTH_RISK_VALUATE = 0;
	private static final int SLOWDISEASE_RISK_VALUATE = 1;
	private static final int TCM_RISK_VALUATE = 2;
	private static final int HEALTH_SELFDIAGONSIS = 3;
	private static final int HEALTH_INFORMATIONS = 4;

	private String quality;

	private String pm25;

	private String locatedCityPinyin;
	private String locatedCityHan;
	private boolean isStartLogin = false;
	@ViewInject(R.id.scroolView)
	private ScrollView scroolView;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case CHANGE_CURRENT_IMAGEVIEW:
				break;
			case GET_PM25:
				// PM25指数显示

				break;
			case GET_BANNER_IMAGES:
				if (!isAdShow) {
					setAdvert();//加载广告Top
				}
				isAdShow = true;
				break;
			default:
				break;
			}
		};
	};
	
	private String date;

	private ProgressDialog progressDialog;

	@Override
	protected int getResource() {
		// TODO Auto-generated method stub
		return R.layout.fragment_home;
	}

	protected void setAdvert() {
		
		for (int index = 0; index < resultlist.size(); index++) {
			Adavertisement adavert = resultlist.get(index);
			TextSliderView textSliderView = new TextSliderView(getActivity());
	        // initialize a SliderLayout
	        textSliderView
//                  .description(name)//显示标题
	                .image(adavert.getImageUrl())
	                .setScaleType(BaseSliderView.ScaleType.Fit)
	                .setOnSliderClickListener(this);
	
	        //add your extra information
	        textSliderView.getBundle()
	                .putString("extra",adavert.getAdid());
	
	        mAdvertSlider.addSlider(textSliderView);
		}
		
		mAdvertSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//		mAdvertSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mAdvertSlider.setCustomAnimation(new DescriptionAnimation());
		mAdvertSlider.setDuration(2000);
	}

	@Override
	protected void afterViews() {
		super.afterViews();
		mImgback.setVisibility(View.GONE);
		date = Constants.DATE_FORMAT.format(new Date());
		mTvDate.setText(date);
		//		initData();
	}

	@Override
	protected void initData() {
		initLogin();
		initLocation();
		setViewPagerAdapter();
//		setViewPagerDots();
		setGalleyAdapter();
		setListViewAdapter();
		getHealthPlanList();
		// setPm25Data("baoding");
	}

	// 获取健康计划列表
	private void getHealthPlanList() {
		if (checkEmpty()) {
			progressDialog=Util.showProgressDialog(getActivity());
			GetHealthPlanBiz healthPlanBiz = new GetHealthPlanBiz();
			healthPlanBiz.setCallback(new MyCallBack());
			healthPlanBiz.getHealthPlans(mTvDate.getText().toString());
		}
	}

	// 判断是否为空
	private boolean checkEmpty() {
		if (TextUtils.isEmpty(mTvDate.getText().toString())) {
			Util.showToast("日期不能为空");
			return false;
		}
		return true;

	}

	/**
	 * 获取健康计划列表的回调
	 * 
	 * @author Administrator
	 *
	 */
	private final class MyCallBack implements NetCallback {

		@Override
		public void taskSuccess(Response response) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			if (response != null) {
				HealthPlanListBean listBean = (HealthPlanListBean) response;
				if (listBean != null) {
					healtBeans = listBean.getResultlist().get(0).getResultlist();
					setDataList();

				}
			}

		}

		@Override
		public void taskError(Response response) {
			// TODO Auto-generated method stub
			Util.showToast("获取列表失败");
			progressDialog.dismiss();

		}

	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		resultlist.clear();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		initLogin();
		initData();

		if (isStartLogin) {
			HomeActivity.getBaseInfo();
			isStartLogin = false;
		}
	}

	private void initLogin() {
		// TODO Auto-generated method stub
		// 首先判断是否已经登陆
		mLogined = PreferencesUtil.getBoolean("isLogin");
		if (mLogined) {
			mTvLogin.setVisibility(View.GONE);
			mImgLogin.setVisibility(View.VISIBLE);
		} else {
			mTvLogin.setVisibility(View.VISIBLE);
			mImgLogin.setVisibility(View.GONE);
		}

	}

	private void setPm25Data(String cityName) {
		// 获取PM2.5的数据
		// 访问接口然后获取对应的数据
		// mTvDate.setText(TimeUtil.getSpecificDate(TimeUtil.PATTERN_YYYY_MM_DD,0));
		HomeBiz biz = new HomeBiz();
		biz.setCallBack(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				try {

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub

			}
		});
		biz.getPm25Data(cityName, PreferencesUtil.getString("USER_PHONE"));

	}

	private void initLocation() {
		// TODO Auto-generated method stub
		mLocationClient = MainApplication.getInstance().getBaiduLocationClient();
		// 然后开始设置参数
		initLocationParams();
		mLocationClient.start();
		MainApplication.getInstance().setBaiduLocationCallBack(this);
	}

	private void initLocationParams() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;
		try {
			span = Integer.valueOf(1000 * 60);
		} catch (Exception e) {
			// TODO: handle exception
		}
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocationClient.stop();
		mGalleryFlow.setAdapter(null);//
	}

	private void setListViewAdapter() {
		// TODO Auto-generated method stub
		mListHealthBeans = new ArrayList<HealthPlanListBean>();
		healtBeans = new ArrayList<HealthPlanBean>();
		setDataList();

	}

	private void setDataList() {
		mHealthPlanAdapter = new HealthPlanAdapter(homeActivity, healtBeans);
		mListViewHealthPlans.setAdapter(mHealthPlanAdapter);
		mListViewHealthPlans.setParentScrollView(scroolView);
		mListViewHealthPlans.setOnItemClickListener(this);
	}

	@SuppressWarnings("deprecation")
	private void setGalleyAdapter() {
		mImageAdapter = new ImageAdapter(homeActivity, mGallerys);
		int[] dimension = BitmapScaleDownUtil.getScreenDimension(getActivity().getWindowManager().getDefaultDisplay());
		int imageWidth = dimension[0] / SCALE_FACTOR;
		int imageHeight = dimension[1] / SCALE_FACTOR;
		mImageAdapter.createImages(imageWidth, imageHeight);
//		mImageAdapter.getItemViewType(position);
		mGalleryFlow.setSpacing(GALLERY_SPACING);
		mGalleryFlow.setAdapter(mImageAdapter);
//		mGalleryFlow.setSelection(mImageAdapter.getCount() / 2);
		mGalleryFlow.setSelection(200);
		mGalleryFlow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				int nowposition = (position % mGallerys.length);// 算出真正的位置
				switch (nowposition) {
				case HEALTH_RISK_VALUATE:
					// 健康风险评估问卷
					// startActivity(new
					// Intent(getActivity(),HealthRiskFragment.class));
					startActivity(new Intent(getActivity(), HealthRiskActivity.class));
					break;
				case SLOWDISEASE_RISK_VALUATE:
					startActivity(new Intent(getActivity(), ChrDiseaseRiskActivity.class));// 慢病评估
					break;
				case TCM_RISK_VALUATE:
					new Handler().post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							// 中医体质评估
							Intent intent = new Intent();
							intent.setClass(getActivity(), TcmPhysiqueActivity.class);
							startActivity(intent);
						}
					});
					break;
				case HEALTH_SELFDIAGONSIS:
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							getActivity().findViewById(R.id.iv_tabSelfDiagnosis).performClick();
						}
					}, 800);
					break;
				case HEALTH_INFORMATIONS:
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							getActivity().findViewById(R.id.iv_tabInformation).performClick();
						}
					}, 800);
					break;
				default:
					break;
				}
			}
		});
		// mGalleryFlow.setSelection(Integer.MAX_VALUE/2);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private void setViewPagerAdapter() {
		resultlist = new ArrayList<Adavertisement>();
		HomeBiz biz = new HomeBiz();
		biz.setCallBack(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				AdvertisementList advertisement = (AdvertisementList) response;
				if (advertisement != null) {
					resultlist = advertisement.getResultList().get(0).getResultlist();
				}
				mHandler.sendEmptyMessage(GET_BANNER_IMAGES);
			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(GET_BANNER_IMAGES);
			}
		});
		biz.getBannerImageUrls();
	}

	// 进入我的中心
	@OnClick(R.id.imgTitleRight)
	public void doRedirctMyCenter(View v) {
		// 进入测试页面查看是否与Fragment有关
		if (mLogined) {
			startActivity(new Intent(getActivity(), MyCenterActivity.class));
		}else {
			//			startActivity(new Intent(getActivity(), LoginActivity.class));
			StartLogin();
		}
	}

	// 进入登陆界面
	@OnClick(R.id.tvTitleRight)
	public void doLogin(View v) {
		//		startActivity(new Intent(getActivity(), LoginActivity.class));
		StartLogin();
	}

	private void StartLogin(){
		startActivity(new Intent(getActivity(),LoginActivity.class));
		isStartLogin = true;
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		// TODO Auto-generated method stub
		super.startActivityForResult(intent, requestCode);
	}

	// 进入节气养生
	@OnClick(R.id.iv_season)
	public void doSeaonalTherapy(View v) {
		// startActivity(new Intent(getActivity(),
		// SeasonThreapyActivity.class));
		startActivity(new Intent(getActivity(), SeasonThreapyActivityNew.class));
	}

	// 进入城市选择
	@OnClick(R.id.iv_weather)
	public void doGetCity(View v) {
		if(locatedCityHan!=null && locatedCityPinyin!=null){
			Intent intent = new Intent(getActivity(), ChooseCitysActivity2.class);
			intent.putExtra("citynamePinyin", locatedCityPinyin);
			intent.putExtra("citynameHan", locatedCityHan);
			startActivity(intent);			
		}else{
			Util.showToast("定位城市失败");
		}
	}

	// 日期向前推
	@OnClick(R.id.iv_left)
	public void doGetPreDate(View v) {
		push_days = push_days - 1;
		date = TimeUtil.getSpecificDate(TimeUtil.PATTERN_YYYY_MM_DD, push_days);
		mTvDate.setText(date);
		// 调用接口
		getHealthPlanList();
	}

	// 日期向后推
	@OnClick(R.id.iv_right)
	public void doGetNextDate(View v) {
		push_days = push_days + 1;
		date = TimeUtil.getSpecificDate(TimeUtil.PATTERN_YYYY_MM_DD, push_days);
		mTvDate.setText(date);
		if (date.equals(Constants.DATE_FORMAT.format(new Date()))) {
			push_days = 0;
		}
		getHealthPlanList();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i("onDestroy", "onDestroyView");
		isAdShow = false;
	}

	@Override
	protected void initWidget() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (!mLogined) {
			return;
		}
		if (date.equals(Constants.DATE_FORMAT.format(new Date()))) {
			HealthPlanBean bean = healtBeans.get(position);
			if (!"1".equals(bean.getStatus())) {
				showAlertDialog(position);
			}
		}

	}

	private void showAlertDialog(final int position) {
		new MyAlertDialog(baseActivity).builder().setMsg("是否完成此任务").setNegativeButton("取消", null)
		.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				UpdateHealthPlanBiz healthPlanBiz = new UpdateHealthPlanBiz();
				healthPlanBiz.setCallback(new NetCallback() {

					@Override
					public void taskSuccess(Response response) {
						Util.showToast("更改成功");
						// 重新获取数据
						getHealthPlanList();
					}

					@Override
					public void taskError(Response response) {
						// TODO Auto-generated method stub
						Util.showToast("修改失败");

					}
				});
				HealthPlanBean planBean = healtBeans.get(position);
				healthPlanBiz.updateHealthPlans(planBean.getPlanid(), "1", planBean.getTag());
			}
		}).show();
	}

	@Override
	public void onLocatedCityCallBack(LocationBean location) {
		// TODO Auto-generated method stub
		// 当城市定位成功之后获取数据
		LogUtils.d("callbackcity.." + location.getCity());
		String cityHan = location.getCity().substring(0, location.getCity().length() - 1);
		String cityPinyin = CharacterParser.getInstance().getSelling(cityHan);
		LogUtils.d("cityHan.." + cityHan + "..cityPinyin.." + cityPinyin);
		// 在这块获取城市即可 在我们进入PM界面之后再去获取数据
		locatedCityPinyin = cityPinyin;
		locatedCityHan=cityHan;
		// setPm25Data(cityPinyin);
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		// TODO Auto-generated method stub
		String ad = slider.getBundle().get("extra").toString();
		for (int index = 0; index < resultlist.size(); index++) {
			Adavertisement adavert = resultlist.get(index);
			if (ad.equals(adavert.getAdid())) {
				Intent intent=new Intent(getActivity(),HomeWebInfoActivity.class);
				intent.putExtra("url", ServerConfig.HTMLSERVER_PATH+adavert.getBannerUrl()+"adid="+adavert.getAdid());
				intent.putExtra("title", adavert.getTitle());
				getActivity().startActivity(intent);
				LogUtils.e("广告URL：   " + ServerConfig.HTMLSERVER_PATH+adavert.getBannerUrl()+"adid="+adavert.getAdid());
			}
		}
		
	}
}
