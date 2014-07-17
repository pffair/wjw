package com.pangff.wjw;

import com.pangff.wjw.adapter.ExchangeDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ExchangeDetailRequest;
import com.pangff.wjw.model.ExchangeDetailResponse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ExchangeDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.exchangeListView)
	ListView exchangeListView;
	
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
		String xml = new ExchangeDetailRequest().getParams(METHOD_DUIHUANLIST);
		new HttpRequest<ExchangeDetailResponse>().postDataXml(METHOD_DUIHUANLIST,xml, this,ExchangeDetailResponse.class);

	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_DUIHUANLIST)){
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
}
