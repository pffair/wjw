package com.pangff.wjw;



import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferResponse;
import com.pangff.wjw.model.WithdrawalsCommitRequest;
import com.pangff.wjw.model.WithdrawalsCommitResponse;
import com.pangff.wjw.model.WithdrawalsDetailRequest;
import com.pangff.wjw.model.WithdrawalsRequest;
import com.pangff.wjw.model.WithdrawalsResponse;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.view.OnOneOffClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WithDrawalsApplySureActivity extends BaseActivity {

	public static final String METHOD_TIXIANOK = "tixianok";
	
	@AndroidView(R.id.sureWithdrawalCashB)
	Button sureWithdrawalCashB;
	
	@AndroidView(R.id.withdrawalCancelB)
	Button withdrawalCancelB;
	
	@AndroidView(R.id.accountNameT)
	TextView accountNameT;
	
	@AndroidView(R.id.OaccountBankT)
	TextView OaccountBankT;
	
	@AndroidView(R.id.branchNameT)
	TextView branchNameT;
	
	@AndroidView(R.id.bankAccountT)
	TextView bankAccountT;
	
	@AndroidView(R.id.withdrawalCashT)
	TextView withdrawalCashT;
	
	@AndroidView(R.id.feeT)
	TextView feeT;
	
	WithdrawalsCommitRequest withdrawalsCommitRequest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_sure);
		initConfig();
		initData();
		
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		
	}

	private void initConfig(){
		sureWithdrawalCashB.setOnClickListener(onOneOffClickListener);
		withdrawalCancelB.setOnClickListener(onOneOffClickListener);
	}
	
	OnOneOffClickListener onOneOffClickListener  = new OnOneOffClickListener(500) {
		
		@Override
		public void onOneClick(View v) {
			switch(v.getId()){
			case R.id.sureWithdrawalCashB:
				doRequestCommitWithDrawals();
				break;
			case R.id.withdrawalCancelB:
				finish();
				break;
			default:
				break;
			}
		}
	};

	private void doRequestCommitWithDrawals(){
		String xml = withdrawalsCommitRequest.getParams(METHOD_TIXIANOK,withdrawalsCommitRequest.body);
		new HttpRequest<WithdrawalsCommitResponse>().postDataXml(METHOD_TIXIANOK, xml, this,WithdrawalsCommitResponse.class);
	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_TIXIANOK)){
			WithdrawalsResponse transferResponse =  (WithdrawalsResponse) result ;
		}
	}


	public static void  invoteToWithDrawalsApplySure(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplySureActivity.class);  
        context.startActivity(intent); 
	}
}