package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.pangff.wjw.AdvDetailActivity;
import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.adapter.AvdListAdapter;
import com.pangff.wjw.adapter.ImagePagerAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.db.AdvImgDBManager;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.AdvRequest;
import com.pangff.wjw.model.AdvResponse;
import com.pangff.wjw.model.Img;
import com.pangff.wjw.model.TopGalleryRequest;
import com.pangff.wjw.model.TopGalleryResponse;
import com.pangff.wjw.view.LoadingView;
import com.pangff.wjw.vindicator.CirclePageIndicator;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class HomeFragment extends PagerFragment {

	@AndroidView(R.id.listView)
	ListView listView;

	
	@AndroidView(R.id.homeRootView)
	ViewGroup homeRootView;
	
	
	AutoScrollViewPager viewPager;

	ViewGroup headerView;

	ImagePagerAdapter topGalleryAdapter;
	AvdListAdapter avdAdapter;
	CirclePageIndicator indicator;
	TextView runText;
	public static final String METHOD_TOPGALLERY = "sygg";
	public static final String METHOD_ADVLIST = "guanggao";
	
	LoadingView listLoadingView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		headerView = (ViewGroup) LayoutInflater.from(this.getActivity()).inflate(
				R.layout.home_list_header, null);
		listView.addHeaderView(headerView);
		viewPager = (AutoScrollViewPager) headerView
				.findViewById(R.id.view_pager);
		indicator = (CirclePageIndicator) headerView
				.findViewById(R.id.indicator);
		runText = (TextView) headerView.findViewById(R.id.runText);
		topGalleryAdapter = new ImagePagerAdapter(this.getActivity());
		avdAdapter = new AvdListAdapter(this.getActivity());
		listView.setAdapter(avdAdapter);
		viewPager.setAdapter(topGalleryAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Img img = (Img) parent.getAdapter().getItem(position);
				AdvDetailActivity.invotoToAdvDetail((BaseActivity) HomeFragment.this.getActivity(),img.id);
			}
		});
	}

	protected void initData() {
		listLoadingView = new LoadingView(this.getActivity());
		listLoadingView.addLoadingTo(homeRootView);
		runText.setVisibility(View.GONE);
		requestTopGallery();
	}
	
	private void requestTopGallery(){
		String xml = new TopGalleryRequest().getParams(METHOD_TOPGALLERY);
		new HttpRequest<TopGalleryResponse>().postDataXml(METHOD_TOPGALLERY, xml, this,TopGalleryResponse.class,true);
	}
	
	private void requestAdvList(){
		String xml = new AdvRequest().getParams(METHOD_ADVLIST);
		new HttpRequest<AdvResponse>().postDataXml(METHOD_ADVLIST, xml, this,AdvResponse.class,true);
	}

	private void showAdvList(AdvResponse advResponse) {
		avdAdapter.refresh(advResponse.body.img);
		AdvImgDBManager.getInstance().addImgs(advResponse.body.img);
	}

	private void showGalleryData(TopGalleryResponse topGallery) {
		topGalleryAdapter.refresh(topGallery);
		viewPager.setInterval(2000);
		indicator.setViewPager(viewPager);
		viewPager.startAutoScroll();
		runText.setText(Html.fromHtml(topGallery.body.gundong.p));
		runText.setSelected(true);
		runText.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPause() {
		super.onPause();
		viewPager.stopAutoScroll();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(topGalleryAdapter.getCount()>0){
			viewPager.startAutoScroll();
		}
	}
	
	@Override
	public void onFailure(String mothod,String errorMsg) {
		super.onFailure(mothod,errorMsg);
		//根据返回的method进行相应操作
		hideLoadingView();
	}
	
	@Override
	public void onSuccess(String mothod,Object result) {
		super.onSuccess(mothod,result);
		//根据返回的method进行相应操作
		if(METHOD_TOPGALLERY.equals(mothod)){
			TopGalleryResponse topGallery = (TopGalleryResponse) result;
			showGalleryData(topGallery);
			requestAdvList();
		}
		if(METHOD_ADVLIST.equals(mothod)){
			hideLoadingView();
			AdvResponse advResponse = (AdvResponse) result;
			showAdvList(advResponse);
		}
	}
	
	private void hideLoadingView(){
			listLoadingView.removeLoadingFrom(homeRootView);
	}
}
