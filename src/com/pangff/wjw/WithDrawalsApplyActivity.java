package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.WithdrawalsCommitRequest;
import com.pangff.wjw.model.WithdrawalsCommitResponse;
import com.pangff.wjw.model.WithdrawalsRequest;
import com.pangff.wjw.model.WithdrawalsResponse;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

public class WithDrawalsApplyActivity extends BaseActivity {

	public static final String METHOD_TIXIAN ="tixian";
	
	@AndroidView(R.id.withdrawalB)
	Button withdrawalB;
	
	@AndroidView(R.id.accountNameT)
	TextView accountNameT;
	
	@AndroidView(R.id.accountRemainT)
	TextView accountRemainT;
	
	@AndroidView(R.id.OaccountBankE)
	EditText OaccountBankE;
	
	@AndroidView(R.id.branchNameE)
	EditText branchNameE;
	
	@AndroidView(R.id.bankAccountE)
	EditText bankAccountE;
	
	@AndroidView(R.id.withdrawalCashE)
	EditText withdrawalCashE;
	
	@AndroidView(R.id.payPasswordE)
	EditText payPasswordE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply);
		
		withdrawalB.setOnClickListener(onOneOffClickListener);
		doRequestAccount();
	}

	
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.withdrawalB:
				doRequestCommittWithDrawals();
				//WithDrawalsApplySureActivity.invoteToWithDrawalsApplySure(this);
			}
			
	}

	private void doRequestCommittWithDrawals(){
		WithdrawalsCommitRequest withdrawalsCommitRequest=new WithdrawalsCommitRequest();
		
		withdrawalsCommitRequest.userid=UserInfoUtil.getInstanse().getUserId();
		withdrawalsCommitRequest.body=new WithdrawalsCommitRequest.Body();
		withdrawalsCommitRequest.body.zxname=accountNameT.getText().toString();
		withdrawalsCommitRequest.body.bank=OaccountBankE.getText().toString();
		withdrawalsCommitRequest.body.zhihang=branchNameE.getText().toString();
		withdrawalsCommitRequest.body.zhanghao=bankAccountE.getText().toString();
		withdrawalsCommitRequest.body.jin=withdrawalCashE.getText().toString();
		
		withdrawalsCommitRequest.body.password = ParseMD5.parseStrToMd5L16(payPasswordE.getText().toString());
		
		String xml = withdrawalsCommitRequest.getParams(METHOD_TIXIANOK,withdrawalsCommitRequest.body);
		new HttpRequest<WithdrawalsCommitResponse>().postDataXml(METHOD_TIXIANOK, xml, this,WithdrawalsCommitResponse.class);
	
		
		

	}

	private void doRequestAccount(){
		WithdrawalsResponse withdrawalsResponse=new WithdrawalsResponse();
		withdrawalsResponse.body.money=accountRemainT.getText().toString();
		
		String xml =new WithdrawalsRequest().getParams(METHOD_TIXIAN);
		new HttpRequest<WithdrawalsResponse>().postDataXml(METHOD_TIXIAN, xml, this,WithdrawalsResponse.class);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_TIXIAN)){
			
			WithdrawalsResponse withdrawalsResponse = (WithdrawalsResponse) result;
			if(withdrawalsResponse!=null){
				setData(withdrawalsResponse);
			}
		}else if(method.equals(METHOD_TIXIANOK)){
				WithdrawalsCommitResponse withdrawalsCommitResponse = (WithdrawalsCommitResponse) result;
			if(withdrawalsCommitResponse.body.returns.equals(ResponseState.SUCCESS)){
				withdrawalB.setText("成功:["+withdrawalsCommitResponse.body.message+"]");
				WithDrawalsApplySureActivity.invoteToWithDrawalsApplySure(this);
			}else{
				ToastUtil.show(withdrawalsCommitResponse.body.message);
			}
			
		}
	}
	
	private void setData(WithdrawalsResponse withdrawalsResponse){
		accountNameT.setText(withdrawalsResponse.body.zxname);
		accountRemainT.setText(withdrawalsResponse.body.money);
	}

	public static void  invoteToWithDrawalsApply(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, WithDrawalsApplyActivity.class);  
        context.startActivity(intent); 
	}
}
