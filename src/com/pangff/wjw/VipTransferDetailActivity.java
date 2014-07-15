package com.pangff.wjw;

import com.pangff.wjw.adapter.WithDrawalsDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.TransferDetailRequest;
import com.pangff.wjw.model.TransferDetailResponse;
import com.pangff.wjw.model.WithdrawalsDetailRequest;
import com.pangff.wjw.model.WithdrawalsDetailResponse;

import android.os.Bundle;
import android.widget.ListView;

public class VipTransferDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.transferListView)
	ListView transferListView;
	
	WithDrawalsDetailAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_detail);
		initConfig();
		initData();
	}

	private void initConfig(){
		adapter = new WithDrawalsDetailAdapter(this);
		transferListView.setAdapter(adapter);
	}
	
	private void initData(){
	//	String xml = new TransferDetailRequest().getParams();
	//	new HttpRequest<TransferDetailResponse>().postDataXml( xml, this,WithdrawalsDetailResponse.class);
	}


}
