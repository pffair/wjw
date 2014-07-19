package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pangff.wjw.adapter.WithDrawalsDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.WithdrawalsDetailRequest;
import com.pangff.wjw.model.WithdrawalsDetailResponse;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;

public class WithDrawalsDetailActivity extends BaseActivity{
	
	@AndroidView(R.id.listView)
	ListView listView;
	
	@AndroidView(R.id.withdrawalsDetailLoadingFrame)
	FrameLayout withdrawalsDetailLoadingFrame;
	
	LoadingView listLoadingView;
	
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
		listLoadingView = new LoadingView(this);
		listLoadingView.addLoadingTo(withdrawalsDetailLoadingFrame);
		String xml = new WithdrawalsDetailRequest().getParams(METHOD_TXLIST);
		new HttpRequest<WithdrawalsDetailResponse>().postDataXml(METHOD_TXLIST, xml, UserInfoUtil.getInstanse().getUserId(), this,WithdrawalsDetailResponse.class,true);
	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_TXLIST)){
			listLoadingView.removeLoadingFrom(withdrawalsDetailLoadingFrame);
			WithdrawalsDetailResponse withdrawalsDetailResponse = (WithdrawalsDetailResponse) result;
			if(withdrawalsDetailResponse!=null){
				adapter.refresh(withdrawalsDetailResponse.body.list);
			}
		}
	}
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		listLoadingView.removeLoadingFrom(withdrawalsDetailLoadingFrame);
	}
	
	public static void  invoteToWithDrawalsDetailApply(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsDetailActivity.class);  
        context.startActivity(intent); 
	}

}
