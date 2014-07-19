package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pangff.wjw.adapter.ExchangeDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ExchangeDetailRequest;
import com.pangff.wjw.model.ExchangeDetailResponse;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;

public class ExchangeDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.exchangeListView)
	ListView exchangeListView;
	
	@AndroidView(R.id.exchangeDetailLoadingFrame)
	FrameLayout exchangeDetailLoadingFrame;
	
	LoadingView listLoadingView; 
	
	ExchangeDetailAdapter adapter;
	public static final String METHOD_DUIHUANLIST = "duihuanlist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_detail);
		initConfig();
		initData();
	}

	private void initConfig() {
		adapter=new ExchangeDetailAdapter(this);
		exchangeListView.setAdapter(adapter);
	}
	
	private void initData(){
		listLoadingView = new LoadingView(this);
		listLoadingView.addLoadingTo(exchangeDetailLoadingFrame);
		String xml = new ExchangeDetailRequest().getParams(METHOD_DUIHUANLIST);
		new HttpRequest<ExchangeDetailResponse>().postDataXml(METHOD_DUIHUANLIST,xml, UserInfoUtil.getInstanse().getUserId(),this,ExchangeDetailResponse.class,true);

	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_DUIHUANLIST)){
			listLoadingView.removeLoadingFrom(exchangeDetailLoadingFrame);
			ExchangeDetailResponse exchangeDetailResponse = (ExchangeDetailResponse) result;
			if(exchangeDetailResponse!=null){
				adapter.refresh(exchangeDetailResponse.body.list);
			}
		}
	}
	
	
	public static void  invoteToVipTransferDetail(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeDetailActivity.class);  
        context.startActivity(intent); 
	}
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		listLoadingView.removeLoadingFrom(exchangeDetailLoadingFrame);
	}
}
