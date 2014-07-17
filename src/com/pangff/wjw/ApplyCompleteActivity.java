package com.pangff.wjw;

import android.content.Intent;
import android.os.Bundle;

public class ApplyCompleteActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawals_apply_complete);
	}
	
	public static void  invoteApplyComplete(BaseActivity context){
		Intent intent = new Intent();  
        intent.setClass(context, ApplyCompleteActivity.class);  
        context.startActivity(intent); 
	}
}
