package com.taikang.tkdoctor.activity.mycenter;

import java.util.List;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.base.MainApplication;
import com.taikang.tkdoctor.bean.CollectionUrlBean;
import com.taikang.tkdoctor.bean.MyCollectionBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.biz.AddMyCollectionBiz;
import com.taikang.tkdoctor.biz.CancelCollectionBiz;
import com.taikang.tkdoctor.biz.GetMessageUrlBiz;
import com.taikang.tkdoctor.config.ServerConfig;
import com.taikang.tkdoctor.request.NetCallback;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
@ContentView(R.layout.activity_my_collection_info)
public class MyCollectionInfoActivity extends BaseActivity implements NetCallback {
	@ViewInject(R.id.progressBar1)
	private ProgressBar mProgressBar;
	@ViewInject(R.id.webview)
	private WebView mWebView;
	private MyCollectionBean bean;
	@ViewInject(R.id.imgTitleCollection)
	private ImageView imgTitleCollection;
	@ViewInject(R.id.imgTitleShare)
	private ImageView imgTitleShare;
	private boolean isCollection=true;
	private PopupWindow popupWindow;
	private ImageView imgFriend;
	private ImageView imgWeiXin;
	private TextView txtCancel;
	private AlertDialog dialog;
	private int MTYPE=0;
	private String url;
	private static final String DESCRIPTOR="com.umeng.share";
	private UMSocialService mController=UMServiceFactory.getUMSocialService(DESCRIPTOR);
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		bean=(MyCollectionBean) getIntent().getSerializableExtra("bean");
		imgTitleCollection.setVisibility(View.VISIBLE);
		imgTitleShare.setVisibility(View.VISIBLE);
		initWebView(bean);
		sendGetUrlRequest();

	}
	//发送请求获取url
	private void sendGetUrlRequest() {
		// TODO Auto-generated method stub
		GetMessageUrlBiz messageUrlBiz=new GetMessageUrlBiz();
		messageUrlBiz.setCallback(new MyCallBack());
		messageUrlBiz.getMessageUrl(bean.getInformation_id());


	}
	/**
	 * 获取url接口回调
	 * @author fuqiang
	 *
	 */
	private final class MyCallBack implements NetCallback{

		@Override
		public void taskSuccess(Response response) {
			// TODO Auto-generated method stub
			if(response!=null){
				CollectionUrlBean urlBean=(CollectionUrlBean) response;	
				List<String> list=urlBean.getResultlist();
				if(list!=null&&list.size()>0){
					url=list.get(0);
				}
			}



		}

		@Override
		public void taskError(Response response) {
			// TODO Auto-generated method stub
			ShowLongToast("获取url地址失败");

		}

	}
	private void initWebView(MyCollectionBean bean2) {
		// TODO Auto-generated method stub
		WebSettings webSettings = mWebView.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		mProgressBar.setVisibility(View.VISIBLE);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mProgressBar.setVisibility(view.GONE);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.loadUrl(ServerConfig.SERVER_PATH+ServerConfig.HTMLURL_INFO_DETAIL+"informationid="+bean.getInformation_id()+"&uid="+MainApplication.getInstance().getUser().getUserId());
		LogUtils.e("收藏路径...."+ServerConfig.SERVER_PATH+ServerConfig.HTMLURL_INFO_DETAIL+"informationid="+bean.getInformation_id());
	}
	@OnClick(R.id.imgTitleCollection)
	private void collection(View view){
		if(isCollection){
			imgTitleCollection.setImageResource(R.drawable.icon_collpress);
			isCollection=false;
			MTYPE=0;
			//取消收藏处理
			CancelCollectionBiz cancelBiz=new CancelCollectionBiz();
			cancelBiz.setCallback(this);
			cancelBiz.cancelCollection(bean.getInformation_id());

		}else {
			imgTitleCollection.setImageResource(R.drawable.icon_colldefalt);
			isCollection=true;
			MTYPE=1;
			AddMyCollectionBiz addMyColletion=new AddMyCollectionBiz();
			addMyColletion.setCallback(this);
			addMyColletion.addMyCollection(bean.getInformation_id());
		}

	}

	@OnClick(R.id.imgTitleShare)
	private void share(View view){
		showPopWindow();
	}

	private void showPopWindow() {
		if (popupWindow == null) {
			dialog=new AlertDialog.Builder(this).create();
			View popView = layoutInflater(R.layout.pop_share, null);
			initDialogView(popView);
			dialog.setView(popView);
			dialog.show();

		}
	}
	private void initShare() {
		// TODO Auto-generated method stub
		setShareContent();
		configPlatforms();
		closeToast();
	}

	private class MyShareClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			initShare();
			switch (v.getId()) {
			case R.id.imgFriend:
				performShare(SHARE_MEDIA.WEIXIN);
				dialog.cancel();
				break;
			case R.id.imgWeiXin:
				performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
				dialog.cancel();
				break;
			case R.id.txtCancel:
				if(dialog!=null) dialog.cancel();
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
				Toast.makeText(MyCollectionInfoActivity.this, showText, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void setShareContent(){
		//	    UMImage urlImage = new UMImage(this,"http://www.umeng.com/images/pic/social/integrated_3.png");
		UMImage LocalImage = new UMImage(this, R.drawable.wechat_icon);
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setShareContent(bean.getSummary());
		weixinContent.setTitle(bean.getTitle());
		weixinContent.setTargetUrl(ServerConfig.HTMLURL_INFO_DETAIL+bean.getInformation_id());
		weixinContent.setShareMedia(LocalImage);
		mController.setShareMedia(weixinContent);

		// 设置朋友圈分享的内容
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setShareContent(bean.getContent());
		circleMedia.setTitle(bean.getTitle());
		circleMedia.setShareMedia(LocalImage);
		circleMedia.setTargetUrl(ServerConfig.HTMLURL_INFO_DETAIL+bean.getInformation_id());
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
	public void closeToast(){
		mController.getConfig().closeToast();
	}


	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		this.finish();
	}
	private void initDialogView(View popView) {
		// TODO Auto-generated method stub
		imgFriend=(ImageView) popView.findViewById(R.id.imgFriend);
		imgWeiXin=(ImageView) popView.findViewById(R.id.imgWeiXin);
		txtCancel=(TextView) popView.findViewById(R.id.txtCancel);
		imgFriend.setOnClickListener(new MyShareClickListener());
		imgWeiXin.setOnClickListener(new MyShareClickListener());
		txtCancel.setOnClickListener(new MyShareClickListener());

	}
	@Override
	public void taskSuccess(Response response) {
		if(MTYPE==0){
			ShowLongToast("取消收藏成功");
		}else if(MTYPE==1){
			ShowLongToast("收藏成功");
		}
	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		if(MTYPE==0){
			ShowLongToast("取消收藏失败");
		}else if(MTYPE==1){
			ShowLongToast("收藏成功");
		}

	}
}