package com.pangff.wjw.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.pangff.wjw.R;
import com.pangff.wjw.adapter.AvdListAdapter;
import com.pangff.wjw.adapter.ImagePagerAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.Adv;
import com.pangff.wjw.model.AdvRequest;
import com.pangff.wjw.model.AdvResponse;
import com.pangff.wjw.model.TopGalleryRequest;
import com.pangff.wjw.model.TopGalleryResponse;
import com.pangff.wjw.util.XStreamTranslator;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class HomeFragment extends PagerFragment {

	@AndroidView(R.id.listView)
	ListView listView;

	AutoScrollViewPager viewPager;

	View headerView;
	//private List<TopGallery> topGallerys = new ArrayList<TopGallery>();
	//private List<Adv> advList = new ArrayList<Adv>();

	ImagePagerAdapter topGalleryAdapter;
	AvdListAdapter avdAdapter;
	
	
	public static final String METHOD_TOPGALLERY = "sygg";
	public static final String METHOD_ADVLIST = "guanggao";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		headerView = LayoutInflater.from(this.getActivity()).inflate(
				R.layout.home_list_header, null);
		listView.addHeaderView(headerView);
		viewPager = (AutoScrollViewPager) headerView
				.findViewById(R.id.view_pager);
		topGalleryAdapter = new ImagePagerAdapter(this.getActivity());
		avdAdapter = new AvdListAdapter(this.getActivity());
		listView.setAdapter(avdAdapter);
		viewPager.setAdapter(topGalleryAdapter.setInfiniteLoop(true));
	}

	protected void initData() {
		requestTopGallery();
		requestAdvList();
	}
	
	private void requestTopGallery(){
		String xml = new TopGalleryRequest().getParams(METHOD_TOPGALLERY, "1");
		Log.e("ddd", "xml:"+xml);
		new HttpRequest<TopGalleryRequest>().postDataXml(METHOD_TOPGALLERY, xml, this,TopGalleryRequest.class);
	}
	
	private void requestAdvList(){
		String xml = new AdvRequest().getParams(METHOD_ADVLIST, "1");
		Log.e("ddd", "xml:"+xml);
		new HttpRequest<AdvRequest>().postDataXml(METHOD_ADVLIST, xml, this,AdvRequest.class);
	}

	private void showAdvList(AdvResponse advResponse) {
		avdAdapter.refresh(advResponse.body.img);
	}

	private void showGalleryData(TopGalleryResponse topGallery) {
		topGalleryAdapter.refresh(topGallery);
		viewPager.setInterval(2000);
		viewPager.startAutoScroll();
	}

	@Override
	public void onPause() {
		super.onPause();
		viewPager.stopAutoScroll();
	}

	@Override
	public void onResume() {
		super.onResume();
		viewPager.startAutoScroll();
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
