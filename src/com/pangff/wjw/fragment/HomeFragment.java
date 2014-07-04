package com.pangff.wjw.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.pangff.wjw.R;
import com.pangff.wjw.adapter.AvdListAdapter;
import com.pangff.wjw.adapter.ImagePagerAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.model.Adv;
import com.pangff.wjw.model.TopGallery;

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
	private List<TopGallery> topGallerys = new ArrayList<TopGallery>();
	private List<Adv> advList = new ArrayList<Adv>();

	ImagePagerAdapter topGalleryAdapter;
	AvdListAdapter avdAdapter;

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
	}

	protected void initData() {
		initGalleryData();
		initAvdList();
	}

	private void initAvdList() {
		advList.add(new Adv(
				"http://farm8.staticflickr.com/7409/9148527822_36fa37d7ca_z.jpg",
				"哈哈哈"));
		advList.add(new Adv(
				"http://farm4.staticflickr.com/3755/9148527824_6c156185ea.jpg",
				"哈哈哈"));
		advList.add(new Adv(
				"http://farm8.staticflickr.com/7318/9148527808_e804baef0b.jpg",
				"哈哈哈"));
		advList.add(new Adv(
				"http://farm8.staticflickr.com/7318/9146300275_5fe995d123.jpg",
				"哈哈哈"));
		listView.setAdapter(avdAdapter);
		avdAdapter.refresh(advList);
	}

	private void initGalleryData() {
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7409/9148527822_36fa37d7ca_z.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm4.staticflickr.com/3755/9148527824_6c156185ea.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7318/9148527808_e804baef0b.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7318/9146300275_5fe995d123.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7288/9146300469_bd3420c75b_z.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7351/9148527976_8a4e75ae87.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm3.staticflickr.com/2888/9148527996_f05118d7de_o.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7310/9148528008_8e8f51997a.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm8.staticflickr.com/7409/9148527822_36fa37d7ca_z.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm3.staticflickr.com/2849/9148528108_dfcda19507.jpg"));
		topGallerys
				.add(new TopGallery(
						"http://farm4.staticflickr.com/3739/9148528022_e9bf03058f.jpg"));
		viewPager.setAdapter(topGalleryAdapter.setInfiniteLoop(true));
		topGalleryAdapter.refresh(topGallerys);
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
}
