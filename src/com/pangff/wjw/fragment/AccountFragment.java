package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pangff.wjw.R;
import com.pangff.wjw.WithDrawalsApplyActivity;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.view.OnOneOffClickListener;

/**
 * fragment基类
 * 
 * @author pangff
 */
public class AccountFragment extends PagerFragment {


	@AndroidView(R.id.withdrawalsApplyT)
	TextView withdrawalsApplyT;
	
	@AndroidView(R.id.withdrawalsDetailT)
	TextView withdrawalsDetailT;
	
	@AndroidView(R.id.vipTransferT)
	TextView vipTransferT;
	
	@AndroidView(R.id.transferDetailT)
	TextView transferDetailT;
	
	@AndroidView(R.id.exchangeT)
	TextView exchangeT;
	
	@AndroidView(R.id.exchangeDtailT)
	TextView exchangeDtailT;
	
	@AndroidView(R.id.awardDtailT)
	TextView awardDtailT;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	/*	withdrawalsApplyT.setOnClickListener(onOneOffClickListener);
		withdrawalsDetailT.setOnClickListener(onOneOffClickListener);
		vipTransferT.setOnClickListener(onOneOffClickListener);
		transferDetailT.setOnClickListener(onOneOffClickListener);
		exchangeT.setOnClickListener(onOneOffClickListener);
		exchangeDtailT.setOnClickListener(onOneOffClickListener);
		awardDtailT.setOnClickListener(onOneOffClickListener);
	*/	
		return inflater.inflate(R.layout.fragment_account, container, false);
		

	}
	protected void onMyClick(){
	//	WithDrawalsApplyActivity.invoteToWithDrawalsApply(this);
	}
}
