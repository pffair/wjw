package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.pangff.wjw.R;
import com.pangff.wjw.adapter.AvdListAdapter;
import com.pangff.wjw.adapter.ImagePagerAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.AdvRequest;
import com.pangff.wjw.model.AdvResponse;
import com.pangff.wjw.model.TopGalleryRequest;
import com.pangff.wjw.model.TopGalleryResponse;
import com.pangff.wjw.vindicator.CirclePageIndicator;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class AdvFragment extends PagerFragment {

	@AndroidView(R.id.listViewAdv)
	ListView listViewAdv;

	AutoScrollViewPager viewPager;
	
	AvdListAdapter avdAdapter;
	
	public static final String METHOD_TOPGALLERY = "sygg";
	public static final String METHOD_ADVLIST = "guanggao";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_adv, container, false);
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		avdAdapter = new AvdListAdapter(this.getActivity());
		listViewAdv.setAdapter(avdAdapter);
	}

	protected void initData() {
		
		requestTopGallery();
		requestAdvList();
	}
	
	private void requestTopGallery(){
		String xml = new TopGalleryRequest().getParams(METHOD_TOPGALLERY);
		new HttpRequest<TopGalleryResponse>().postDataXml(METHOD_TOPGALLERY, xml, this,TopGalleryResponse.class);
	}
	
	private void requestAdvList(){
		String xml = new AdvRequest().getParams(METHOD_ADVLIST);
		new HttpRequest<AdvResponse>().postDataXml(METHOD_ADVLIST, xml, this,AdvResponse.class);
	}

	private void showAdvList(AdvResponse advResponse) {
		avdAdapter.refresh(advResponse.body.img);
	}

	private void showGalleryData(TopGalleryResponse topGallery) {
		viewPager.setInterval(2000);
		//indicator.setViewPager(viewPager);
		viewPager.startAutoScroll();
		//runText.setText(Html.fromHtml(topGallery.body.gundong));
		//runText.setSelected(true);
	}

	@Override
	public void onPause() {
		super.onPause();
		viewPager.stopAutoScroll();
	}
	
	@Override
	public void onResume() {
		super.onResume();

	}
	
	@Override
	public void onFailure(String mothod,String errorMsg) {
		super.onFailure(mothod,errorMsg);
		//根据返回的method进行相应操作
	}
	
	@Override
	public void onSuccess(String mothod,Object result) {
		super.onSuccess(mothod,result);
		//根据返回的method进行相应操作
		if(METHOD_TOPGALLERY.equals(mothod)){
			TopGalleryResponse topGallery = (TopGalleryResponse) result;
			showGalleryData(topGallery);
		}
		if(METHOD_ADVLIST.equals(mothod)){
			AdvResponse advResponse = (AdvResponse) result;
			showAdvList(advResponse);
		}
	}

}
