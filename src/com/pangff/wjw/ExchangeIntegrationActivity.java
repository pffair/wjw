package com.pangff.wjw;



import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.ApplySuccessEvent;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ExchangeRequest;
import com.pangff.wjw.model.ExchangeResponse;
import com.pangff.wjw.model.MyAccountRequest;
import com.pangff.wjw.model.MyAccountResponse;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferRequest;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;

public class ExchangeIntegrationActivity extends BaseActivity{
	
	public static final String METHOD_DUIHUAN = "duihuan";
	
	@AndroidView(R.id.exchangeNameT)
	TextView exchangeNameT;
	
	@AndroidView(R.id.exchangeB)
	Button exchangeB;
	
	@AndroidView(R.id.integrationRemainT)
	TextView integrationRemainT;
	
	@AndroidView(R.id.menoyRemainT)
	TextView menoyRemainT;
	
	@AndroidView(R.id.exchangeMoneyE)
	EditText exchangeMoneyE;
	
	@AndroidView(R.id.payPasswordE)
	EditText payPasswordE;
	
	@AndroidView(R.id.exchangeLoadingFrame)
	FrameLayout exchangeLoadingFrame;
	
	@AndroidView(R.id.eMoneyRemainT)
	TextView eMoneyRemainT;
	
	@AndroidView(R.id.eInteralReaminT)
	TextView eInteralReaminT;
	
	LoadingView exchangeLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exchange_integration);
		eMoneyRemainT.setText(Html.fromHtml("<font color=#CD7F32 >$</font>钱包余额:"));
		eInteralReaminT.setText(Html.fromHtml("<font color=#CD7F32 >$</font>积分余额:"));
		exchangeB.setOnClickListener(onOneOffClickListener);
		doRequestAccount();
		exchangeNameT.setText(UserInfoUtil.getInstanse().getUserName());
	}

	
	private void goExchange(){
		ExchangeRequest exchangeRequest=new ExchangeRequest();
		exchangeRequest.userid= UserInfoUtil.getInstanse().getUserId();
		exchangeRequest.body=new ExchangeRequest.Body();
		exchangeRequest.body.jin=exchangeMoneyE.getText().toString();
		exchangeRequest.body.password=payPasswordE.getText().toString();
		if(verify(exchangeRequest.body)){
			ExchangeSureAvtivity.invoteToExchangeSure(this,integrationRemainT.getText().toString(),exchangeRequest.userid,exchangeRequest);
		}
	}
	
	private boolean verify(ExchangeRequest.Body body){
		if(StringUtil.isEmpty(integrationRemainT.getText().toString())){
			ToastUtil.show("请等待帐户信息初始化");
			return false;
		}
		if(StringUtil.isEmpty(body.jin)){
			ToastUtil.show("转帐金额不能为空");
			return false;
		}
		if(StringUtil.isEmpty(body.password)){
			ToastUtil.show("密码不能为空");
			return false;
		}
		return true;
	}
	@Override
	protected void onMyClick(View v) {
		switch(v.getId()){
		case R.id.exchangeB:
			goExchange();
			break;
		}
	}
	
	private void doRequestAccount(){
		exchangeLoading=new LoadingView(this);
		exchangeLoading.addLoadingTo(exchangeLoadingFrame);
		MyAccountRequest myAccountRequest = new MyAccountRequest();
		String xml = myAccountRequest.getParams(METHOD_DUIHUAN);
		new HttpRequest<MyAccountResponse>().postDataXml(METHOD_DUIHUAN, xml, this,MyAccountResponse.class,false);
	}

	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);

		if(method.equals(METHOD_DUIHUAN)){
			exchangeLoading.removeLoadingFrom(exchangeLoadingFrame);
			MyAccountResponse myAccountResponse =  (MyAccountResponse) result ;
			if(myAccountResponse!=null){
				menoyRemainT.setText(myAccountResponse.body.money);
				integrationRemainT.setText(myAccountResponse.body.jifen);
			}else{
				ToastUtil.show("获取用户信息失败");
			}
		}
	}

	
	
	@Override
	public void onFailure(String method, String errorMsg) {
		super.onFailure(method, errorMsg);
		exchangeLoading.removeLoadingFrom(exchangeLoadingFrame);
	}


	public static void  invoteToExchangeIntegration(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ExchangeIntegrationActivity.class);  
        context.startActivity(intent); 
	}
	
	@Override
	public void onEvent(IEvent event) {
		super.onEvent(event);
		if(event instanceof ApplySuccessEvent){
			finish();
		}
	}
}
