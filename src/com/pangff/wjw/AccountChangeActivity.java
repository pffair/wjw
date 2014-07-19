package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.util.UserInfoUtil;

public class AccountChangeActivity extends BaseActivity {

	@AndroidView(R.id.loginPasswordChangeR)
	RelativeLayout loginPwd;

	@AndroidView(R.id.catchPasswordChangeR)
	RelativeLayout catchPwd;

	@AndroidView(R.id.payPasswordChangeR)
	RelativeLayout payPwd;

	String style;

	@AndroidView(R.id.usernameChangeT)
	TextView usernameChangeT;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_change);

		usernameChangeT.setText(UserInfoUtil.getInstanse().getUserName());
		loginPwd.setOnClickListener(onOneOffClickListener);
		catchPwd.setOnClickListener(onOneOffClickListener);
		payPwd.setOnClickListener(onOneOffClickListener);
	}

	public static void invotoacountchange(BaseActivity context) {
		Intent intent = new Intent();
		intent.setClass(context, AccountChangeActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onMyClick(View v) {
		switch (v.getId()) {
		case R.id.loginPasswordChangeR:
			goChangePwd(ResivePasswordActivity.CHANGETYPE_LOGIN);
			break;

		case R.id.catchPasswordChangeR:
			goChangePwd(ResivePasswordActivity.CHANGETYPE_LOOK);
			break;

		case R.id.payPasswordChangeR:
			goChangePwd(ResivePasswordActivity.CHANGETYPE_PAY);
			break;

		default:
			break;
		}
	}

	private void goChangePwd(String type) {
		ResivePasswordActivity.invoteToResivePassword(this, type);
	}

}
