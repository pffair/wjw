package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.MyAccountRequest;
import com.pangff.wjw.model.MyAccountResponse;
import com.pangff.wjw.model.TransferRequest;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;

public class VipTransferActivity extends BaseActivity{
	
	public static final int TRANSFER_TYPE_MONEY=1;
	public static final int TRANSFER_TYPE_SCORE=2;
	
	public static final String METHOD_ZHUANZHANG = "zhuanzhang";
	
	
	private static final String[] style = { "钱包", "积分"};
	private ArrayAdapter<String> adapterTransferStyle;
	
	@AndroidView(R.id.transferStyle)
	Spinner transferStyle;
	
	@AndroidView(R.id.transferStyleT)
	TextView transferStyleT;
	
	
	@AndroidView(R.id.transferRegisterB)
	Button transferRegisterB;
	
	int transfer_type = 1;
	
	
	@AndroidView(R.id.receiverNumE)
	EditText receiverNumE;
	
	@AndroidView(R.id.transferMoneyE)
	EditText transferMoneyE;
	
	@AndroidView(R.id.transferPasswordE)
	EditText transferPasswordE;
	
	@AndroidView(R.id.usernameT)
	TextView usernameT;
	
	@AndroidView(R.id.menoyRemainT)
	TextView menoyRemainT;
	
	@AndroidView(R.id.integraticRemainT)
	TextView integraticRemainT;
	
	@AndroidView(R.id.transferLoadingFrame)
	FrameLayout transferLoadingFrame;
	
	LoadingView transferLoading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transfer);
		initConfig();
		doRequestAccount();
	}
	
	@Override
	protected void onMyClick(View v) {
		switch(v.getId()){
		case R.id.transferRegisterB:
			doRequestCommitTransfer();
			break;
		}
	}
	
	private void doRequestAccount(){
		transferLoading=new LoadingView(this);
		transferLoading.addLoadingTo(transferLoadingFrame);
		MyAccountRequest myAccountRequest = new MyAccountRequest();
		String xml = myAccountRequest.getParams(METHOD_ZHUANZHANG);
		new HttpRequest<MyAccountResponse>().postDataXml(METHOD_ZHUANZHANG, xml, this,MyAccountResponse.class);
	}
	
	private void doRequestCommitTransfer(){
		TransferRequest transferRequest = new TransferRequest();
		TransferRequest.Body body= new TransferRequest.Body();
		body.leixing = String.valueOf(transfer_type);
		body.password = transferPasswordE.getText().toString();
		body.jin = transferMoneyE.getText().toString();
		body.tomem = receiverNumE.getText().toString();
		transferRequest.body = body;
		if(verify(body)){
			if(transfer_type == TRANSFER_TYPE_MONEY){
				VipTransferSureActivity.invoteToVipTransferSure(this,menoyRemainT.getText().toString(),transferRequest);
			}else{
				VipTransferSureActivity.invoteToVipTransferSure(this,integraticRemainT.getText().toString(),transferRequest);
			}
		}
	}
	
	private boolean verify(TransferRequest.Body body){
		if(StringUtil.isEmpty(menoyRemainT.getText().toString())){
			ToastUtil.show("请等待帐户信息初始化");
			return false;
		}
		
		if(StringUtil.isEmpty(body.tomem)){
			ToastUtil.show("接收人编号不能为空");
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

	private void initConfig() {
		// 将可选内容与ArrayAdapter连接起来
		adapterTransferStyle = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, style);

		// 设置下拉列表的风格
		adapterTransferStyle
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		transferStyle.setAdapter(adapterTransferStyle);

		// 添加事件Spinner事件监听
		transferStyle.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		transferStyle.setVisibility(View.VISIBLE);
		transferRegisterB.setOnClickListener(onOneOffClickListener);
		usernameT.setText(UserInfoUtil.getInstanse().getUserName());
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String type = transferStyle.getSelectedItem().toString();
			if(type.equals("钱包")){
				transfer_type = 1;
			}else{
				transfer_type = 2;
			}
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
	}
	
	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if(method.equals(METHOD_ZHUANZHANG)){
			transferLoading.removeLoadingFrom(transferLoadingFrame);
			MyAccountResponse myAccountResponse =  (MyAccountResponse) result ;
			if(myAccountResponse!=null){
				menoyRemainT.setText(myAccountResponse.body.money);
				integraticRemainT.setText(myAccountResponse.body.jifen);
			}else{
				ToastUtil.show("获取用户信息失败");
			}
		}
	}

	public static void  invoteToVipTransfer(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, VipTransferActivity.class);  
        context.startActivity(intent); 
	}


}
