package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pangff.wjw.adapter.TransferDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.TransferDetailRequest;
import com.pangff.wjw.model.TransferDetailResponse;
import com.pangff.wjw.view.LoadingView;

public class VipTransferDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.transferListView)
	ListView transferListView;
	
	@AndroidView(R.id.transferDetailLoadingFrame)
	FrameLayout transferDetailLoadingFrame;
	
	LoadingView listLoadingView;
	
	TransferDetailAdapter adapter;
	public static final String METHOD_ZHUANZHANGLIST ="zhuanzhanglist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_detail);
		initConfig();
		initData();
	}

	private void initConfig(){
		adapter = new TransferDetailAdapter(this);
		transferListView.setAdapter(adapter);
	}
	
	private void initData(){
		listLoadingView = new LoadingView(this);
		listLoadingView.addLoadingTo(transferDetailLoadingFrame);
		String xml = new TransferDetailRequest().getParams(METHOD_ZHUANZHANGLIST);
		new HttpRequest<TransferDetailResponse>().postDataXml(METHOD_ZHUANZHANGLIST,xml, this,TransferDetailResponse.class);
	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_ZHUANZHANGLIST)){
			listLoadingView.removeLoadingFrom(transferDetailLoadingFrame);
			TransferDetailResponse transferDetailResponse = (TransferDetailResponse) result;
			if(transferDetailResponse!=null){
				adapter.refresh(transferDetailResponse.body.list);
			}
		}
	}
	
	
	public static void  invoteToVipTransferDetail(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferDetailActivity.class);  
        context.startActivity(intent); 
	}
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		listLoadingView.removeLoadingFrom(transferDetailLoadingFrame);
	}
}
