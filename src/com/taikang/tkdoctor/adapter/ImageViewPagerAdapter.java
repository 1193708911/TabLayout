package com.taikang.tkdoctor.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.taikang.tkdoctor.activity.main.HomeWebInfoActivity;
import com.taikang.tkdoctor.bean.AdvertiList.Adavertisement;
import com.taikang.tkdoctor.config.ServerConfig;

public class ImageViewPagerAdapter extends PagerAdapter {

	private ArrayList<ImageView> mListImages;
	private Context mContext;
	private ArrayList<Adavertisement> resultlist;
	private Adavertisement adavertisement;
	public ImageViewPagerAdapter() {
		// TODO Auto-generated constructor stub
	}

	public ImageViewPagerAdapter(ArrayList<ImageView> mlist){
		this.mListImages=mlist;
	}

	public ImageViewPagerAdapter(ArrayList<ImageView> mlist,Context context,ArrayList<Adavertisement> resultlist){
		this.mListImages=mlist;
		this.mContext=context;
		this.resultlist=resultlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListImages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView((View)object);
	}

	@Override
	public void finishUpdate(View container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(mListImages.get(position));
		ImageView iv=mListImages.get(position);
		adavertisement=resultlist.get(position);
		
		iv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,HomeWebInfoActivity.class);
				try {
						intent.putExtra("url", ServerConfig.HTMLSERVER_PATH+adavertisement.getBannerUrl()+"adid="+adavertisement.getAdid());
						intent.putExtra("title", adavertisement.getTitle());
						mContext.startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		return mListImages.get(position);
	}

	@Override
	public void startUpdate(View container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

}
