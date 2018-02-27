package com.taikang.tkdoctor.activity.mycenter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taikang.tkdoctor.R;
import com.taikang.tkdoctor.activity.main.HomeActivity;
import com.taikang.tkdoctor.base.BaseActivity;
import com.taikang.tkdoctor.bean.MyServiceBean;
import com.taikang.tkdoctor.bean.Response;
import com.taikang.tkdoctor.bean.ServiceListBean;
import com.taikang.tkdoctor.bean.ServiceListBean.ServiceBean;
import com.taikang.tkdoctor.biz.MyServceBiz;
import com.taikang.tkdoctor.request.NetCallback;
@ContentView(R.layout.activity_myservices)
public class MyServicesActivity extends BaseActivity implements NetCallback {
	@ViewInject(R.id.iv_icon)
	private ImageView iv_icon;
	@ViewInject(R.id.tv_line2)
	private TextView tv_line2;
	@ViewInject(R.id.tv_startdate)
	private TextView tv_startdate;
	@ViewInject(R.id.tv_enddate)
	private TextView tv_enddate;
	@Override
	protected void afterViews() {
		// TODO Auto-generated method stub
		super.afterViews();
		sendGetServiceRequest();
	}
	//发送获取当前服务的请求
	private void sendGetServiceRequest() {
		MyServceBiz servceBiz=new MyServceBiz();
		servceBiz.setCallback(this);
		servceBiz.myServceRequest();
	}
	@OnClick(R.id.imgTitleBack)
	private void goBack(View view){
		finish();
	}
	@OnClick(R.id.imgTitleRight)
	private void goHome(View view){
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		this.finish();

	}
	@Override
	public void taskSuccess(Response response) {
		if(response!=null){
			MyServiceBean serviceBean=(MyServiceBean) response;
			if(serviceBean!=null){
				ServiceListBean serviceListBeans=serviceBean.getResultlist().get(0);
				if(serviceListBeans!=null){
					ServiceBean bean=serviceListBeans.getResultlist().get(0);
					if(bean!=null){
						tv_startdate.setText(bean.getStartdate());
						tv_enddate.setText(bean.getEnddate());
					}
				}

			}
		}else {
			ShowLongToast("服务信息获取失败");
		}

	}
	@Override
	public void taskError(Response response) {
		// TODO Auto-generated method stub
		ShowLongToast("服务信息获取失败");

	}

}
