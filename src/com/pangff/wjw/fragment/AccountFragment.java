package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pangff.wjw.AccountChangeActivity;
import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.ExchangeDetailActivity;
import com.pangff.wjw.ExchangeIntegrationActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RewardActivity;
import com.pangff.wjw.VipTransferActivity;
import com.pangff.wjw.VipTransferDetailActivity;
import com.pangff.wjw.WithDrawalsApplyActivity;
import com.pangff.wjw.WithDrawalsDetailActivity;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.MyAccountRequest;
import com.pangff.wjw.model.MyAccountResponse;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.view.LoadingView;

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

	@AndroidView(R.id.myAccount)
	LinearLayout myAccount;

	@AndroidView(R.id.showIdT)
	TextView showIdT;

	@AndroidView(R.id.showNameT)
	TextView showNameT;

	@AndroidView(R.id.showLevelT)
	TextView showLevelT;

	@AndroidView(R.id.showRemainT)
	TextView showRemainT;

	@AndroidView(R.id.showIntegralT)
	TextView showIntegralT;

	@AndroidView(R.id.accountLoadingFrame)
	FrameLayout accountLoadingFrame;
	
	LoadingView accountLoading;
	
	public static final String METHOD_ZHANGHU = "zhanghu";


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_account, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		myAccount.setOnClickListener(onOneOffClickListener);
		withdrawalsApplyT.setOnClickListener(onOneOffClickListener);
		withdrawalsDetailT.setOnClickListener(onOneOffClickListener);
		vipTransferT.setOnClickListener(onOneOffClickListener);
		transferDetailT.setOnClickListener(onOneOffClickListener);
		exchangeT.setOnClickListener(onOneOffClickListener);
		exchangeDtailT.setOnClickListener(onOneOffClickListener);
		awardDtailT.setOnClickListener(onOneOffClickListener);
	}


	private void doRequestAccount(){
		accountLoading=new LoadingView(this.getActivity());
		accountLoading.addLoadingTo(accountLoadingFrame);
		MyAccountRequest myAccountRequest = new MyAccountRequest();
		String xml = myAccountRequest.getParams(METHOD_ZHANGHU);
		new HttpRequest<MyAccountResponse>().postDataXml(METHOD_ZHANGHU, xml, this,MyAccountResponse.class,false);
	}

	@Override
	public void onSuccess(String mothod, Object result) {
		super.onSuccess(mothod, result);
		if(mothod.equals(METHOD_ZHANGHU)){
			accountLoading.removeLoadingFrom(accountLoadingFrame);
			MyAccountResponse myAccountResponse = (MyAccountResponse) result;
			if(myAccountResponse!=null){
				showIdT.setText(myAccountResponse.userid);
				showNameT.setText(myAccountResponse.body.username);
				showLevelT.setText(myAccountResponse.body.userleve);
				showRemainT.setText(myAccountResponse.body.money);
				showIntegralT.setText(myAccountResponse.body.jifen);
			}
		}
	}
	
	@Override
	public void onFailure(String mothod, String errorMsg) {
		super.onFailure(mothod, errorMsg);
		accountLoading.removeLoadingFrom(accountLoadingFrame);
	}

	@Override
	protected void initData() {
		super.initData();
		if(StringUtil.isEmpty(showIdT.getText().toString())){
			doRequestAccount();
		}
	}


	protected void onMyClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.myAccount:
			AccountChangeActivity.invotoacountchange((BaseActivity) this
					.getActivity());break;
		case R.id.withdrawalsApplyT:
			WithDrawalsApplyActivity
					.invoteToWithDrawalsApply((BaseActivity) this.getActivity());
			break;
		case R.id.withdrawalsDetailT:
			WithDrawalsDetailActivity
					.invoteToWithDrawalsDetailApply((BaseActivity) this
							.getActivity());
			break;
		case R.id.vipTransferT:
			VipTransferActivity.invoteToVipTransfer((BaseActivity) this
					.getActivity());
			break;
		case R.id.transferDetailT:
			VipTransferDetailActivity
					.invoteToVipTransferDetail((BaseActivity) this
							.getActivity());
			break;
		case R.id.exchangeT:
			ExchangeIntegrationActivity
					.invoteToExchangeIntegration((BaseActivity) this
							.getActivity());
			break;
		case R.id.exchangeDtailT:
			ExchangeDetailActivity
					.invoteToVipTransferDetail((BaseActivity) this
							.getActivity());
			break;
		case R.id.awardDtailT:
			RewardActivity.invoteToReward((BaseActivity) this.getActivity());
			break;
		}
	}
}