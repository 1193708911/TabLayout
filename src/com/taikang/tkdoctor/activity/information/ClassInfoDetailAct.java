package com.taikang.tkdoctor.activity.information;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.Info;
import com.taikang.tkdoctor.bean.InfoDetailBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.GetInformationBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.taikang.tkdoctor.util.PreferencesUtil;
import com.taikang.tkdoctor.util.ShowLogin;
import com.taikang.tkdoctor.util.Util;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.instagram.controller.UMInstagramHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.LabeledIntent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.activity_share)
public class ClassInfoDetailAct extends BaseActivity {

	@ViewInject(R.id.ivback)
	private ImageView ivBack;
	@ViewInject(R.id.ivfav)
	private ImageView ivFav;
	@ViewInject(R.id.ivshare)
	private ImageView ivShare;
	@ViewInject(R.id.webshare)
	private WebView webView;
	private Info   classContent;
	private String informaitonId;
	private String uid;
	private static final int GET_INFORMATION_URL = 0;
	private static final int ADD_INFORMATION_DETAIL=1;
	private static final int DELETE_INFORMATION_DETAIL=2;
	private InfoDetailBean infoDetailBean;
    private boolean isCollected;
    private AlertDialog alertDialog;
	private static final String DESCRIPTOR="com.umeng.share";
	private String shareTitle;
	private String shareContent;
	private String shareUrl;
	private String shareImagUrl;
	
	private UMSocialService mController=UMServiceFactory.getUMSocialService(DESCRIPTOR);
    
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_INFORMATION_URL:
				setInformationDetailData();
				initShare();
				break;
			case ADD_INFORMATION_DETAIL:
				String result_add=(String) msg.obj;
				if("success".equals(result_add)){
					ivFav.setImageDrawable(getResources().getDrawable(R.drawable.btn_fav_selected));
					Util.showToast("添加收藏成功");
					isCollected=true;
				}else if("error".equals(result_add)){
					ivFav.setImageDrawable(getResources().getDrawable(R.drawable.btn_fav));
					Util.showToast("添加收藏失败");
					isCollected=false;
				}
				break;
			case DELETE_INFORMATION_DETAIL:
				String result_cancel=(String) msg.obj;
				if("success".equals(result_cancel)){
					ivFav.setImageDrawable(getResources().getDrawable(R.drawable.btn_fav));
					Util.showToast("取消收藏成功");
					isCollected=false;
				}else if("error".equals(result_cancel)){
					ivFav.setImageDrawable(getResources().getDrawable(R.drawable.btn_fav_selected));
					Util.showToast("取消收藏失败");
					isCollected=true;
				}
				break;
			default:
				break;
			}

		};

	};

	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		initData();
	
	}

	private void initShare() {
		// TODO Auto-generated method stub
		setShareContent();
		configPlatforms();
		closeToast();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initData() {
		// TODO Auto-generated method stub
		classContent = (Info) getIntent().getSerializableExtra("classDteail");
		informaitonId = classContent.getInformationid();
		uid=classContent.getUId();
		shareTitle=classContent.getTitle();
		shareContent=classContent.getContent();
		// 通过InformationId来获取执行的页面数据
		GetInformationBiz biz = new GetInformationBiz();
		biz.setNetCallBack(new NetCallback() {

			@Override
			public void taskSuccess(Response response) {
				// TODO Auto-generated method stub
				infoDetailBean = (InfoDetailBean) response;
				mHandler.sendEmptyMessage(GET_INFORMATION_URL);
			}

			@Override
			public void taskError(Response response) {
				// TODO Auto-generated method stub
                
			}
		});
		biz.GetInformationDetails(informaitonId);
	}

	public void setInformationDetailData() {
		LogUtils.d("html5..." + infoDetailBean.getResultlist().get(0).getResultlist().getHtml5());
		LogUtils.d("url..." + infoDetailBean.getResultlist().get(0).getResultlist().getPicurl());
		String url_html5=infoDetailBean.getResultlist().get(0).getResultlist().getHtml5();
		String url_info_detail=ServerConfig.SERVER_PATH_INFO+url_html5+"informationid="+informaitonId+"&"+"uid="+uid;
//		String url_info_detail="http://192.168.1.133:7001/hdhealth/message/messagedetail.html?informationid=3&uid=676dd96d-76dd-4702-ae62-a0e100d98f04";
		LogUtils.d("详情页面url..."+url_info_detail);
		shareUrl=url_info_detail;
		webView.getSettings().setJavaScriptEnabled(true);
//		String url = "http://www.baidu.com/";
		webView.loadUrl(url_info_detail);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
	}

	@OnClick(R.id.ivback)
	public void back(View v) {
		finish();
	}

	@OnClick(R.id.ivshare)
	public void share(View v) {// 分享
         if(alertDialog==null){
        	alertDialog= new AlertDialog.Builder(this).create();
        	View view=LayoutInflater.from(this).inflate(R.layout.pop_share, null);
        	ImageView imgWeChat=(ImageView) view.findViewById(R.id.imgFriend);
        	ImageView imgWeiXinCricle=(ImageView) view.findViewById(R.id.imgWeiXin);
        	TextView  tvCancel=(TextView) view.findViewById(R.id.txtCancel);
        	imgWeChat.setOnClickListener(new MyShareClickListener());
        	imgWeiXinCricle.setOnClickListener(new MyShareClickListener());
        	tvCancel.setOnClickListener(new MyShareClickListener());
        	alertDialog.setView(view);
        	alertDialog.setCanceledOnTouchOutside(true);
         }
         alertDialog.show();
	}
	
	
	private class MyShareClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.imgFriend:
				performShare(SHARE_MEDIA.WEIXIN);
				alertDialog.cancel();
				break;
			case R.id.imgWeiXin:
				performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				alertDialog.cancel();
				break;
			case R.id.txtCancel:
				if(alertDialog!=null) alertDialog.cancel();
				break;	
			default:
				break;
			}
		}
		
	}
	
    private void performShare(SHARE_MEDIA platform) {
        mController.postShare(this, platform, new SnsPostListener() {
        	
            @Override
            public void onStart() {
            	
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                String showText = "";
                if (eCode == StatusCode.ST_CODE_SUCCESSED) {
                	if(platform==SHARE_MEDIA.WEIXIN){
                		showText+="微信分享成功";
                	}else if(platform==SHARE_MEDIA.WEIXIN_CIRCLE){
                		showText+="微信朋友圈分享成功";
                	}
                } else {
                	if(platform==SHARE_MEDIA.WEIXIN){
                		showText+="微信分享失败";
                	}else if(platform==SHARE_MEDIA.WEIXIN_CIRCLE){
                	    showText+="微信朋友圈分享失败";	
                	}
                }
                Toast.makeText(ClassInfoDetailAct.this, showText, Toast.LENGTH_SHORT).show();
            }
        });
    }

	@OnClick(R.id.ivfav)
	public void fav(View v) {// 收藏
		boolean isLogin = PreferencesUtil.getBoolean("isLogin");
		if (!isLogin) {
			ShowLogin.showLoginWindow(mThis, (View)webView.getParent());
			return;
		}
		if(!isCollected){			
			GetInformationBiz biz = new GetInformationBiz();
			biz.setNetCallBack(new NetCallback() {
				
				@Override
				public void taskSuccess(Response response) {
					// TODO Auto-generated method stub
					Message msg=new Message();
					msg.what=ADD_INFORMATION_DETAIL;
					msg.obj="success";
					mHandler.sendMessage(msg);
				}
				
				@Override
				public void taskError(Response response) {
					Message msg=new Message();
					msg.what=ADD_INFORMATION_DETAIL;
					msg.obj="error";
					mHandler.sendMessage(msg);
				}
			});
			biz.addInformationDetails(informaitonId);
		}else{
			GetInformationBiz biz = new GetInformationBiz();
			biz.setNetCallBack(new NetCallback() {
				
				@Override
				public void taskSuccess(Response response) {
					Message msg=new Message();
					msg.what=DELETE_INFORMATION_DETAIL;
					msg.obj="success";
					mHandler.sendMessage(msg);
				}
				
				@Override
				public void taskError(Response response) {
					Message msg=new Message();
					msg.what=DELETE_INFORMATION_DETAIL;
					msg.obj="error";
					mHandler.sendMessage(msg);
				}
			});
			biz.cancelInformationDetails(informaitonId);
		}
	}
	
	public void closeToast(){
		mController.getConfig().closeToast();
	}
	
	
	public void setShareContent(){
//	    UMImage urlImage = new UMImage(this,"http://www.umeng.com/images/pic/social/integrated_3.png");
	    UMImage LocalImage = new UMImage(this, R.drawable.wechat_icon);
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(shareContent);
        weixinContent.setTitle(shareTitle);
        weixinContent.setTargetUrl(shareUrl);
        weixinContent.setShareMedia(LocalImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(shareContent);
        circleMedia.setTitle(shareTitle);
        circleMedia.setShareMedia(LocalImage);
        circleMedia.setTargetUrl(shareUrl);
        mController.setShareMedia(circleMedia);
	}
	
	
	public void configPlatforms(){
		addWXPlatform();
	}
	
    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private void addWXPlatform() {
        String appId = "wx095bb1fe5dcc2f7c";//wx967daebe835fbeac
        String appSecret = "07694533285221e8e98ee661711d60d2";//5bb696d9ccd75a38c8a0bfe0675559b3
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, appId, appSecret);
        wxHandler.addToSocialSDK();
        wxHandler.showCompressToast(false);
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        wxCircleHandler.showCompressToast(false);
    }
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return false;
	}
}
