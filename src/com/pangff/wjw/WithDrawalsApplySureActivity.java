package com.pangff.wjw;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.WithdrawalsCommitRequest;
import com.pangff.wjw.model.WithdrawalsDetailResponse;

public class WithDrawalsApplySureActivity extends BaseActivity {

	public static final String METHOD_TIXIANOK = "tixianok";

	@AndroidView(R.id.sureWithdrawalCashB)
	Button sureWithdrawalCashB;

	@AndroidView(R.id.withdrawalCancelB)
	Button withdrawalCancelB;

	@AndroidView(R.id.balanceT)
	TextView balanceT;

	@AndroidView(R.id.withdrawalCashT)
	TextView withdrawalCashT;

	@AndroidView(R.id.bankAccountT)
	TextView bankAccountT;

	@AndroidView(R.id.branchNameT)
	TextView branchNameT;

	@AndroidView(R.id.oAccountBankT)
	TextView oAccountBankT;

	@AndroidView(R.id.accountNameT)
	TextView accountNameT;

	WithdrawalsCommitRequest withdrawalsCommitRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_sure);

		sureWithdrawalCashB.setOnClickListener(onOneOffClickListener);
		withdrawalCancelB.setOnClickListener(onOneOffClickListener);

		initData();
	}

	private void initData(){

		Intent intent = this.getIntent();
		String type = intent.getStringExtra("type");
		String receiver = intent.getStringExtra("receiver");
		String balance = intent.getStringExtra("balance");
		String transferMoney = intent.getStringExtra("transferMoney");
		String password = intent.getStringExtra("password");

	//	balanceT
	//	withdrawalCashT
	//	bankAccountT
	//	branchNameT
	//	OAccountBankT
	//	accountNameT
	}

	@Override
	protected void onMyClick(View v) {
		switch(v.getId()){
		case R.id.sureWithdrawalCashB:
			doRequestCommitWithDrawals();
		case R.id.withdrawalCancelB:
			finish();
		}
	}

	private void doRequestCommitWithDrawals(){
		String xml = withdrawalsCommitRequest.getParams(METHOD_TIXIANOK,withdrawalsCommitRequest.body);
		new HttpRequest<WithdrawalsDetailResponse>().postDataXml(METHOD_TIXIANOK, xml, this,WithdrawalsDetailResponse.class);
	}


	public static void  invoteToWithDrawalsApplySure(BaseActivity context,String userid,String balance,WithdrawalsCommitRequest withdrawalsCommitRequest){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferSureActivity.class);  
        intent.putExtra("receiver", withdrawalsCommitRequest.userid);
        intent.putExtra("balance", balance);
        intent.putExtra("password", withdrawalsCommitRequest.body.password);
        intent.putExtra("money", withdrawalsCommitRequest.body.jin);
        
        intent.putExtra("bankAccountT", withdrawalsCommitRequest.body.bank);
        intent.putExtra("money", withdrawalsCommitRequest.body.jin);
        intent.putExtra("money", withdrawalsCommitRequest.body.jin);
        
        context.startActivity(intent); 
	}
}