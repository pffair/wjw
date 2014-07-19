package com.pangff.wjw;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferResponse;
import com.pangff.wjw.model.WithdrawalsCommitRequest;
import com.pangff.wjw.model.WithdrawalsCommitResponse;
import com.pangff.wjw.model.WithdrawalsDetailResponse;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;

public class WithDrawalsApplySureActivity extends BaseActivity {

	public static final String METHOD_TIXIANOK = "tixianok";

	@AndroidView(R.id.sureWithdrawalCashB)
	Button sureWithdrawalCashB;

	@AndroidView(R.id.withdrawalCancelB)
	Button withdrawalCancelB;

	@AndroidView(R.id.feeT)
	TextView feeT;

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
		String balance = intent.getStringExtra("balance");
		String money = intent.getStringExtra("money");
		String password = intent.getStringExtra("password");
		String bankAccount = intent.getStringExtra("bankAccountT");
		String branchName = intent.getStringExtra("branchNameT");
		String oAccountBank = intent.getStringExtra("oAccountBankT");
		String accountName = intent.getStringExtra("accountNameT");
		String sxf = intent.getStringExtra("sxf");

		feeT.setText(String.valueOf(Double.parseDouble(sxf)*100)+"%");
		withdrawalCashT.setText(money);
		bankAccountT.setText(bankAccount);
		branchNameT.setText(branchName);
		oAccountBankT.setText(oAccountBank);
		accountNameT.setText(accountName);


		withdrawalsCommitRequest = new WithdrawalsCommitRequest();
		withdrawalsCommitRequest.body = new WithdrawalsCommitRequest.Body();
		withdrawalsCommitRequest.body.bank = oAccountBank;
		withdrawalsCommitRequest.body.zhihang = branchName;
		withdrawalsCommitRequest.body.jin = money;
		withdrawalsCommitRequest.body.password = ParseMD5.parseStrToMd5L16(password);
		withdrawalsCommitRequest.body.zxname = accountName;
		withdrawalsCommitRequest.body.sxf = sxf;
		withdrawalsCommitRequest.body.zhanghao = bankAccount;
	}

	@Override
	protected void onMyClick(View v) {
		switch(v.getId()){
		case R.id.sureWithdrawalCashB:
			doRequestCommitWithDrawals();
			break;
		case R.id.withdrawalCancelB:
			finish();
			break;
		}
	}

	private void doRequestCommitWithDrawals(){
		String xml = withdrawalsCommitRequest.getParams(METHOD_TIXIANOK,withdrawalsCommitRequest.body);
		new HttpRequest<WithdrawalsCommitResponse>().postDataXml(METHOD_TIXIANOK, xml, this,WithdrawalsCommitResponse.class,false);
	}


	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_TIXIANOK)){
			WithdrawalsCommitResponse withdrawalsCommitResponse =  (WithdrawalsCommitResponse) result ;
			if(withdrawalsCommitResponse.body.returns.equals(ResponseState.SUCCESS)){
				ApplyCompleteActivity.invoteApplyComplete(WithDrawalsApplySureActivity.this);
				finish();
			}else{
				ToastUtil.show(withdrawalsCommitResponse.body.message);
				finish();
			}
		}
	}

	public static void  invoteToWithDrawalsApplySure(BaseActivity context,String userid,String balance,WithdrawalsCommitRequest withdrawalsCommitRequest){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplySureActivity.class);  
        intent.putExtra("receiver", withdrawalsCommitRequest.userid);
        intent.putExtra("balance", balance);
        intent.putExtra("password", withdrawalsCommitRequest.body.password);
        intent.putExtra("money", withdrawalsCommitRequest.body.jin);
        intent.putExtra("sxf", withdrawalsCommitRequest.body.sxf);
        intent.putExtra("bankAccountT", withdrawalsCommitRequest.body.zhanghao);
        intent.putExtra("branchNameT", withdrawalsCommitRequest.body.zhihang);
        intent.putExtra("oAccountBankT", withdrawalsCommitRequest.body.bank);
        intent.putExtra("accountNameT", withdrawalsCommitRequest.body.zxname);
        intent.putExtra("sxf", withdrawalsCommitRequest.body.sxf);
        context.startActivity(intent); 
	}
}