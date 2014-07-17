package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class AcountChangeActivity extends BaseActivity {
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_change);
	}
	public static void invotoacountchange(BaseActivity context){
		Intent intent = new Intent();
        intent.setClass(context, AcountChangeActivity.class);  
        context.startActivity(intent);
	}
	
}
