package com.pangff.wjw;

import com.pangff.wjw.autowire.AndroidView;
import com.pangff.wjw.event.ApplySuccessEvent;
import com.pangff.wjw.event.IEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegistSuccessActivity extends BaseActivity {

	@AndroidView(R.id.registSuccessT)
	TextView registSuccessT;

	@AndroidView(R.id.registSureB)
	Button registSureB;

	String registAccount;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_success);
		initData();
		registSuccessT.setText("注册帐号为:[" + registAccount + "]");
		registSureB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BaseApplication.self.controlBus.post(new ApplySuccessEvent());
				finish();
			}
		});
	}

	private void initData() {
		registAccount = this.getIntent().getStringExtra("registAccount");
	}

	public static void invoteToRegistSuccess(BaseActivity context,
			String registAccount) {
		Intent intent = new Intent();
		intent.setClass(context, RegistSuccessActivity.class);
		intent.putExtra("registAccount", registAccount);
		context.startActivity(intent);
	}
}
