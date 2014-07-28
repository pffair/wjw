package com.pangff.wjw;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.IEvent;
import com.pangff.wjw.fragment.MoreFragment;
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
	
	
	@AndroidView(R.id.logoutB)
	Button logoutB;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_change);

		usernameChangeT.setText(UserInfoUtil.getInstanse().getUserName());
		loginPwd.setOnClickListener(onOneOffClickListener);
		catchPwd.setOnClickListener(onOneOffClickListener);
		payPwd.setOnClickListener(onOneOffClickListener);
		logoutB.setOnClickListener(onOneOffClickListener);
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
		case R.id.logoutB:
			logout();
			break;
		default:
			break;
		}
	}
	
	private void logout() {
		try {
			AlertDialog.Builder builder = new Builder(AccountChangeActivity.this);
			builder.setTitle("退出");
			builder.setMessage("退出将清空登录信息，确定退出?");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							UserInfoUtil.getInstanse().clearUserInfo();
							Intent intent = new Intent(AccountChangeActivity.this, LoginActivity.class);
							AccountChangeActivity.this.startActivity(
									intent);
							AccountChangeActivity.this.finish();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			builder.create().show();
		} catch (Exception e) {
			Log.e("AlertDialog", "", e);
		}
	}

	private void goChangePwd(String type) {
		ResivePasswordActivity.invoteToResivePassword(this, type);
	}

}
