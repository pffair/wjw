package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ExchangeRequest;
import com.pangff.wjw.model.ExchangeResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferResponse;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.view.OnOneOffClickListener;

public class ExchangeSureAvtivity extends BaseActivity{
	
	@AndroidView(R.id.sureExchangeB)
	Button sureExchangeB;
	
	@AndroidView(R.id.cancelB)
	Button cancelB;
	
	@AndroidView(R.id.accountT)
	TextView accountT;
	
	@AndroidView(R.id.balanceT)
	TextView balanceT;
	
	@AndroidView(R.id.exchangeMoneyT)
	TextView exchangeMoneyT;
	
	
	ExchangeRequest exchangeRequest;
	
	public static final String METHOD_DUIHUANOK = "duihuanok";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_sure);
		initConfig();
		initData();
	}
	
	private void initData(){
		Intent intent = this.getIntent();
		String receiver = intent.getStringExtra("receiver");
		String balance = intent.getStringExtra("balance");
		String exchangeMoney = intent.getStringExtra("money");
		String password = intent.getStringExtra("password");
		
		accountT.setText(receiver);
		balanceT.setText(balance);
		exchangeMoneyT.setText(exchangeMoney);
		
		
		exchangeRequest = new ExchangeRequest();
		ExchangeRequest.Body body= new ExchangeRequest.Body();
		body.password = ParseMD5.parseStrToMd5L16(password);
		body.jin = exchangeMoney;
		exchangeRequest.body = body;
	}
	
	
	private void initConfig() {
		sureExchangeB.setOnClickListener(onOneOffClickListener);
		cancelB.setOnClickListener(onOneOffClickListener);
	}
	
	OnOneOffClickListener onOneOffClickListener  = new OnOneOffClickListener(500) {
		
		@Override
		public void onOneClick(View v) {
			switch (v.getId()) {
			case R.id.sureExchangeB:
				doRequestCommitExchange();
				break;
			case R.id.cancelB:
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	
	protected void doRequestCommitExchange() {
		String xml = exchangeRequest.getParams(METHOD_DUIHUANOK,exchangeRequest.body);
		new HttpRequest<ExchangeResponse>().postDataXml(METHOD_DUIHUANOK, xml, this,ExchangeResponse.class,false);		
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_DUIHUANOK)){
			ExchangeResponse exchangeResponse =  (ExchangeResponse) result ;
			if(exchangeResponse.body.returns.equals(ResponseState.SUCCESS)){
				ApplyCompleteActivity.invoteApplyComplete(ExchangeSureAvtivity.this);
				finish();
			}else{
				ToastUtil.show(exchangeResponse.body.message);
				finish();
			}
		}
	}


	public static void  invoteToExchangeSure(BaseActivity context,String balance,String userName,ExchangeRequest exchangeRequest){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeSureAvtivity.class);  
        intent.putExtra("money", exchangeRequest.body.jin);
        intent.putExtra("receiver", userName);
        intent.putExtra("balance", balance);
        intent.putExtra("password", exchangeRequest.body.password);
        context.startActivity(intent); 
	}
}
