package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.pangff.wjw.adapter.RewardDetailAdapter;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.RewardDetailRequest;
import com.pangff.wjw.model.RewardDetailResponse;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;

public class RewardActivity extends BaseActivity{
	
	@AndroidView(R.id.rewardListView)
	ListView rewardListView;
	
	@AndroidView(R.id.rewardLoadingFrame)
	FrameLayout rewardLoadingFrame;
	
	LoadingView listLoadingView;
	
	RewardDetailAdapter adapter;
	public static final String METHOD_PRIXLIST = "prixlist";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reward_detail);
		initConfig();
		initData();
	}

	private void initConfig(){
		adapter = new RewardDetailAdapter(this);
		rewardListView.setAdapter(adapter);
	}
	
	private void initData(){
		listLoadingView = new LoadingView(this);
		listLoadingView.addLoadingTo(rewardLoadingFrame);
		String xml = new RewardDetailRequest().getParams(METHOD_PRIXLIST);
		new HttpRequest<RewardDetailResponse>().postDataXml(METHOD_PRIXLIST, xml, UserInfoUtil.getInstanse().getUserId(), this,RewardDetailResponse.class,true);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_PRIXLIST)){
			listLoadingView.removeLoadingFrom(rewardLoadingFrame);
			RewardDetailResponse rewardDetailResponse = (RewardDetailResponse) result;
			if(rewardDetailResponse!=null){
				adapter.refresh(rewardDetailResponse.body.list);
			}
		}
	}
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		listLoadingView.removeLoadingFrom(rewardLoadingFrame);
	}
	
	public static void  invoteToReward(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, RewardActivity.class);  
        context.startActivity(intent); 
	}
	
}
