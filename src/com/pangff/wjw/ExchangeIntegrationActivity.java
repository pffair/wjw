package com.pangff.wjw;



import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ExchangeRequest;
import com.pangff.wjw.model.ExchangeResponse;
import com.pangff.wjw.model.RegistResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExchangeIntegrationActivity extends BaseActivity{
	
	public static final String METHOD_DUIHUAN = "duihuan";
	
	@AndroidView(R.id.exchangeNameT)
	Button exchangeNameT;
	
	@AndroidView(R.id.exchangeB)
	Button exchangeB;
	
	@AndroidView(R.id.integrationRemainT)
	TextView integrationRemainT;
	
	@AndroidView(R.id.menoyRemainT)
	TextView menoyRemainT;
	
	@AndroidView(R.id.exchangeMoneyE)
	TextView exchangeMoneyE;
	
	
	@AndroidView(R.id.payPasswordE)
	TextView payPasswordE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_integration);
		
		exchangeB.setOnClickListener(onOneOffClickListener);
		
	}

	
	private void doExchange(){
		ExchangeRequest exchangeRequest=new ExchangeRequest();
		exchangeRequest.userid= UserInfoUtil.getInstanse().getUserId();
		exchangeRequest.body=new ExchangeRequest.Body();
		exchangeRequest.body.jin=exchangeMoneyE.getText().toString();
		exchangeRequest.body.password=ParseMD5.parseStrToMd5L16(payPasswordE.getText().toString());
		
		String xml = exchangeRequest.getParams(METHOD_DUIHUAN);
		new HttpRequest<ExchangeResponse>().postDataXml(METHOD_DUIHUAN, xml, this,ExchangeResponse.class);
	}
	
	@Override
	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.exchangeB:
			ExchangeSureAvtivity.invoteToExchangeSure(this);break;
		}
	}

	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);

	}

	public static void  invoteToExchangeIntegration(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeIntegrationActivity.class);  
        context.startActivity(intent); 
	}
}
