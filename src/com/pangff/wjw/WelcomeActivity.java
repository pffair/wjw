package com.pangff.wjw;

import android.os.Bundle;

public class WelcomeActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		BaseApplication.self.handlerCommon.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				LoginActivity.invotoLogin(WelcomeActivity.this);
			}
		}, 1000);
	}


}
