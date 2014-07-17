package com.pangff.wjw.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pangff.wjw.AcountChangeActivity;
import com.pangff.wjw.BaseActivity;
import com.pangff.wjw.ExchangeDetailActivity;
import com.pangff.wjw.ExchangeIntegrationActivity;
import com.pangff.wjw.R;
import com.pangff.wjw.RewardActivity;
import com.pangff.wjw.VipTransferActivity;
import com.pangff.wjw.VipTransferDetailActivity;
import com.pangff.wjw.WithDrawalsApplyActivity;
import com.pangff.wjw.WithDrawalsDetailActivity;
import com.pangff.wjw.adapter.WithDrawalsDetailAdapter;
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

	@AndroidView(R.id.myAccount)
	LinearLayout myAccount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_account, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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


	
	protected void onMyClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.myAccount:
			AcountChangeActivity.invotoacountchange((BaseActivity) this
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
