package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pangff.wjw.R;
import com.pangff.wjw.adapter.AvdListAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.AdvRequest;
import com.pangff.wjw.model.AdvResponse;
import com.pangff.wjw.view.LoadingView;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class AdvFragment extends PagerFragment {

	@AndroidView(R.id.listViewAdv)
	ListView listViewAdv;
	
	@AndroidView(R.id.container)
	FrameLayout container;
	
	AvdListAdapter avdAdapter;
	
	LoadingView listLoadingView;
	
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
		requestAdvList();
	}
	
	private void requestAdvList(){
		listLoadingView = new LoadingView(this.getActivity());
		listLoadingView.addLoadingTo(container);
		String xml = new AdvRequest().getParams(METHOD_ADVLIST);
		new HttpRequest<AdvResponse>().postDataXml(METHOD_ADVLIST, xml, this,AdvResponse.class);
	}

	private void showAdvList(AdvResponse advResponse) {
		avdAdapter.refresh(advResponse.body.img);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();

	}
	
	@Override
	public void onFailure(String mothod,String errorMsg) {
		super.onFailure(mothod,errorMsg);
	}
	
	@Override
	public void onSuccess(String mothod,Object result) {
		super.onSuccess(mothod,result);
		if(METHOD_ADVLIST.equals(mothod)){
			listLoadingView.removeLoadingFrom(container);
			AdvResponse advResponse = (AdvResponse) result;
			showAdvList(advResponse);
		}
	}

}
