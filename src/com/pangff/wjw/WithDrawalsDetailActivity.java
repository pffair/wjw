package com.pangff.wjw;

import android.os.Bundle;
import android.widget.ListView;

import com.pangff.wjw.adapter.WithDrawalsDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.WithdrawalsDetailRequest;
import com.pangff.wjw.model.WithdrawalsDetailResponse;

public class WithDrawalsDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.listView)
	ListView listView;
	
	WithDrawalsDetailAdapter adapter;
	public static final String METHOD_TXLIST = "txlist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_detail);
		initConfig();
		initData();
	}
	
	private void initConfig(){
		adapter = new WithDrawalsDetailAdapter(this);
		listView.setAdapter(adapter);
	}
	
	private void initData(){
		String xml = new WithdrawalsDetailRequest().getParams(METHOD_TXLIST);
		new HttpRequest<WithdrawalsDetailResponse>().postDataXml(METHOD_TXLIST, xml, this,WithdrawalsDetailResponse.class);
	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_TXLIST)){
			WithdrawalsDetailResponse withdrawalsDetailResponse = (WithdrawalsDetailResponse) result;
			if(withdrawalsDetailResponse!=null){
				adapter.refresh(withdrawalsDetailResponse.body.list);
			}
		}
	}

}
