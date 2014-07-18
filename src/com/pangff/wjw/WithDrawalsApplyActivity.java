package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.pangff.wjw.VipTransferActivity.SpinnerSelectedListener;
import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.http.HttpRequest;
import com.pangff.wjw.model.ResponseState;
import com.pangff.wjw.model.TransferRequest;
import com.pangff.wjw.model.WithdrawalsCommitRequest;
import com.pangff.wjw.model.WithdrawalsCommitResponse;
import com.pangff.wjw.model.WithdrawalsRequest;
import com.pangff.wjw.model.WithdrawalsResponse;
import com.pangff.wjw.util.ParseMD5;
import com.pangff.wjw.util.StringUtil;
import com.pangff.wjw.util.ToastUtil;
import com.pangff.wjw.util.UserInfoUtil;
import com.pangff.wjw.view.LoadingView;
import com.pangff.wjw.view.TitleBar;

public class WithDrawalsApplyActivity extends BaseActivity {

	public static final String METHOD_TIXIAN = "tixian";

	@AndroidView(R.id.withdrawalB)
	Button withdrawalB;

	@AndroidView(R.id.accountNameT)
	TextView accountNameT;

	@AndroidView(R.id.accountRemainT)
	TextView accountRemainT;

	@AndroidView(R.id.branchNameE)
	EditText branchNameE;

	@AndroidView(R.id.bankAccountE)
	EditText bankAccountE;

	@AndroidView(R.id.withdrawalCashE)
	EditText withdrawalCashE;

	@AndroidView(R.id.payPasswordE)
	EditText payPasswordE;

	@AndroidView(R.id.titleBar)
	TitleBar titleBar;
	
	@AndroidView(R.id.withidrawalsApplyLoadingFrame)
	FrameLayout withidrawalsApplyLoadingFrame;
	
	LoadingView withidrawalsLoadingFrame;
	
	String sxf = "";
	String bank = "";

	private static final String[] banks = {"农业银行","建设银行","工商银行"};
	@AndroidView(R.id.bankSpinner)
	Spinner bankSpinner;
	ArrayAdapter banksAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply);
		initConfig();
		doRequestAccount();
	}

	private void initConfig() {
		// 将可选内容与ArrayAdapter连接起来
		banksAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, banks);

		// 设置下拉列表的风格
		banksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		bankSpinner.setAdapter(banksAdapter);

		// 添加事件Spinner事件监听
		bankSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		withdrawalB.setOnClickListener(onOneOffClickListener);
		titleBar.rightT.setOnClickListener(onOneOffClickListener);
	}

	class SpinnerSelectedListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			bank = bankSpinner.getSelectedItem().toString();
		}
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

	protected void onMyClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.withdrawalB:
			doRequestCommittWithDrawals();
			// WithDrawalsApplySureActivity.invoteToWithDrawalsApplySure(this);
			break;
		case R.id.titleBar:
			ExplainWithdrawalsActivity.invoteToExplainWithdrawals(this);
			break;
		}

	}

	private void doRequestCommittWithDrawals() {
		WithdrawalsCommitRequest withdrawalsCommitRequest = new WithdrawalsCommitRequest();

		withdrawalsCommitRequest.userid = UserInfoUtil.getInstanse().getUserId();
		withdrawalsCommitRequest.body = new WithdrawalsCommitRequest.Body();
		withdrawalsCommitRequest.body.zxname = accountNameT.getText().toString();
		withdrawalsCommitRequest.body.bank = bank;
		withdrawalsCommitRequest.body.zhihang = branchNameE.getText().toString();
		withdrawalsCommitRequest.body.zhanghao = bankAccountE.getText().toString();
		withdrawalsCommitRequest.body.jin = withdrawalCashE.getText().toString();
		withdrawalsCommitRequest.body.password = payPasswordE.getText().toString();
		withdrawalsCommitRequest.body.sxf = sxf;

		if(verify(withdrawalsCommitRequest.body)){
			WithDrawalsApplySureActivity.invoteToWithDrawalsApplySure(this,
					withdrawalsCommitRequest.userid, accountRemainT.getText()
							.toString(), withdrawalsCommitRequest);
		}

	}

	private boolean verify(WithdrawalsCommitRequest.Body body){
		if(StringUtil.isEmpty(accountNameT.getText().toString())){
			ToastUtil.show("请等待帐户信息初始化");
			return false;
		}
		if(StringUtil.isEmpty(body.bank)){
			ToastUtil.show("银行不能为空");
			return false;
		}
		if(StringUtil.isEmpty(body.zhihang)){
			ToastUtil.show("支行不能为空");
			return false;
		}
		if(StringUtil.isEmpty(body.zhanghao)){
			ToastUtil.show("卡号不能为空");
			return false;
		}
		if(StringUtil.isEmpty(body.jin)){
			ToastUtil.show("体现金额不能为空");
			return false;
		}
		if(StringUtil.isEmpty(body.password)){
			ToastUtil.show("密码不能为空");
			return false;
		}
		return true;
	}

	private void doRequestAccount() {
		withidrawalsLoadingFrame=new LoadingView(this);
		withidrawalsLoadingFrame.addLoadingTo(withidrawalsApplyLoadingFrame);
		WithdrawalsRequest withdrawalsRequest = new WithdrawalsRequest();
		String xml = withdrawalsRequest.getParams(METHOD_TIXIAN);
		new HttpRequest<WithdrawalsResponse>().postDataXml(METHOD_TIXIAN, xml,
				this, WithdrawalsResponse.class);
	}

	@Override
	public void onSuccess(String method, Object result) {
		super.onSuccess(method, result);
		if (method.equals(METHOD_TIXIAN)) {
			withidrawalsLoadingFrame.removeLoadingFrom(withidrawalsApplyLoadingFrame);
			WithdrawalsResponse withdrawalsResponse = (WithdrawalsResponse) result;
			if (withdrawalsResponse != null) {
				setData(withdrawalsResponse);
			}
		}
	}

	private void setData(WithdrawalsResponse withdrawalsResponse) {
		accountNameT.setText(withdrawalsResponse.body.zxname);
		accountRemainT.setText(withdrawalsResponse.body.money);
		sxf = withdrawalsResponse.body.sxf;
	}

	public static void invoteToWithDrawalsApply(BaseActivity context) {
		Intent intent = new Intent();
		intent.setClass(context, WithDrawalsApplyActivity.class);
		context.startActivity(intent);
	}
}