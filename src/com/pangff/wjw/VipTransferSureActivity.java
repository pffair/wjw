package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferRequest;
import com.pangff.wjw.model.TransferResponse;
import com.pangff.wjw.util.LogUtil;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.view.OnOneOffClickListener;

public class VipTransferSureActivity extends BaseActivity {

	@AndroidView(R.id.transferStyleT)
	TextView transferStyleT;
	
	@AndroidView(R.id.recipientAccountT)
	TextView recipientAccountT;
	
	@AndroidView(R.id.menoyT)
	TextView menoyT;
	
	@AndroidView(R.id.transferMoneyT)
	TextView transferMoneyT;
	
	@AndroidView(R.id.sureTransferB)
	Button sureTransferB;
	
	@AndroidView(R.id.cancelB)
	Button cancelB;
	
	TransferRequest transferRequest;
	
	public static final String METHOD_ZHUANZHANGOK = "zhuanzhangok";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer_sure);
		initConfig();
		initData();
	}
	
	private void initConfig() {
		sureTransferB.setOnClickListener(onOneOffClickListener);
		cancelB.setOnClickListener(onOneOffClickListener);
	}
	
	OnOneOffClickListener onOneOffClickListener  = new OnOneOffClickListener(500) {
		
		@Override
		public void onOneClick(View v) {
			switch (v.getId()) {
			case R.id.sureTransferB:
				doRequestCommitTransfer();
				break;
			case R.id.cancelB:
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	private void doRequestCommitTransfer(){
		String xml = transferRequest.getParams(METHOD_ZHUANZHANGOK,transferRequest.body);
		new HttpRequest<TransferResponse>().postDataXml(METHOD_ZHUANZHANGOK, xml, this,TransferResponse.class);
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_ZHUANZHANGOK)){
			TransferResponse transferResponse =  (TransferResponse) result ;
			if(transferResponse.body.returns.equals(ResponseState.SUCCESS)){
				ApplyCompleteActivity.invoteApplyComplete(VipTransferSureActivity.this);
				finish();
			}else{
				ToastUtil.show(transferResponse.body.message);
				finish();
			}
		}
	}

	private void initData(){
		Intent intent = this.getIntent();
		String type = intent.getStringExtra("type");
		String receiver = intent.getStringExtra("receiver");
		String balance = intent.getStringExtra("balance");
		String transferMoney = intent.getStringExtra("transferMoney");
		String password = intent.getStringExtra("password");
		
		if(type.equals(VipTransferActivity.TRANSFER_TYPE_MONEY)){
			transferStyleT.setText("钱包");
		}else{
			transferStyleT.setText("积分");
		}
		recipientAccountT.setText(receiver);
		menoyT.setText(balance);
		transferMoneyT.setText(transferMoney);
		
		transferRequest = new TransferRequest();
		TransferRequest.Body body= new TransferRequest.Body();
		body.leixing = type;
		body.password = ParseMD5.parseStrToMd5L16(password);
		body.jin = transferMoney;
		body.tomem = receiver;
		transferRequest.body = body;
	}

	public static void  invoteToVipTransferSure(BaseActivity context,String balance,TransferRequest transferRequest){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferSureActivity.class);  
        intent.putExtra("type", transferRequest.body.leixing);
        intent.putExtra("receiver", transferRequest.body.tomem);
        intent.putExtra("balance", balance);
        intent.putExtra("password", transferRequest.body.password);
        intent.putExtra("transferMoney", transferRequest.body.jin);
        context.startActivity(intent); 
	}
}
